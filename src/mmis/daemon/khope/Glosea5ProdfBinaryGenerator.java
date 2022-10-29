package mmis.daemon.khope;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;
import mmis.daemon.util.legend.KhopeLegend;
import mmis.daemon.util.marchingsquare.MarchingSquares;
import net.coobird.thumbnailator.Thumbnails;
import ucar.ma2.Range;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class Glosea5ProdfBinaryGenerator extends KhopeFileGenerator {
	
	public Glosea5ProdfBinaryGenerator(final String metaDirPath) {	
		
		this.setKhopeMetaFiles(metaDirPath, KhopeFileGenerator.KHOPE_MODEL.KHOPE_GSEA);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KHOPE_GSEA, ModelGridUtil.Position.MIDDLE_CENTER, this.khopeLatFilePath, this.khopeLonFilePath);
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath) {
		return null;
	}
		
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Binary [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[0].split("_")[5];
		
		try {
			
			NetcdfDataset ncFile = NetcdfDataset.acquireDataset(srcFilePath, null);
			
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, 0, 360});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			Variable var = null;
			String binaryFileName = "";
				
			int timeLength = 62;
			int depLength = 15;
			
			for(int t=0 ; t<timeLength ; t++) {
				
				if(this.testMode && t > 0) {
					continue;
				}
				
				for(int d=0 ; d<depLength ; d++) {
					
					if(this.testMode && d > 0) {
						continue;
					}
					
					if(srcFilePath.contains("_zcur")) {
						
						var = ncFile.findVariable("vozocrtx");
						binaryFileName = "GLOSEA5_" + issuedTmStr + "_UUU";
						
					} else if(srcFilePath.contains("_mcur")) {
						
						var = ncFile.findVariable("vomecrty");
						binaryFileName = "GLOSEA5_" + issuedTmStr + "_VVV";
					}
				
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(d, d));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					double[][] values = GridCalcUtil.convertStorageToValues(var.read(rangeList).getStorage(), rows, cols, false);
				    
					binaryFileName += "_" + String.format("%02d", t+1) + "_" + String.format("%02d", d+1)+".bin";
					
					String binaryFilePath = destFilePath + File.separator + binaryFileName;
					
					DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(binaryFilePath)));
	    			
	    			for(int j=0 ; j<rows ; j++) {					
	    				for(int k=0 ; k<cols ; k++) {
	    					if(values[j][k] <= -999) {
	    						out.writeFloat(0f);
	    					} else {
	    						out.writeFloat((float)values[j][k]);	
	    					}
	    				}
	    			}
	    			
	    			out.close();
					
					System.out.println("\t-> Write Binary File [" + binaryFilePath + "]");
	    			
	    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
	    			
	    			destFileInfo.put("filePath", binaryFilePath);
	    			destFileInfo.put("issuedTm", issuedTmStr);
	    			destFileInfo.put("type", "velocity");
	    			
	    			destFileInfoList.add(destFileInfo);
				}
			}

			ncFile.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Binary [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
    
    
}
