package mmis.daemon.kim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import mmis.daemon.util.DaemonUtil;
import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;
import mmis.daemon.util.legend.KhopeLegend;
import mmis.daemon.util.legend.KimLegend;
import mmis.daemon.util.marchingsquare.MarchingSquares;
import net.coobird.thumbnailator.Thumbnails;
import ucar.ma2.Range;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class KimRww3ImageGenerator extends KimFileGenerator {
	
	private final int imageExpandFactor = 50;
	private final int imageResizeFactor = 1;
	
	public KimRww3ImageGenerator(final String metaDirPath) {	
		
		this.setKimMetaFiles(metaDirPath, KimFileGenerator.KIM_MODEL.KIM_RWW3);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KIM_RWW3, ModelGridUtil.Position.TOP_LEFT, this.kimLatFilePath, this.kimLonFilePath);
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath) {
		return null;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Image [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		final Object[][] variableInfos = new Object[][]{
          {"Peak_wave_period_surface", "WP", KimLegend.Legend.RWW3_WP, 30, null},
          {"Significant_height_of_wind_waves_surface", "SIG_WH", KimLegend.Legend.RWW3_SIG_WH, 30, null}
		};
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[1];
		
		try {
			
			NetcdfDataset ncFile = NetcdfDataset.acquireDataset(srcFilePath, null);
			
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, 0, 360});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
		
			int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			for(Object[] variableInfo : variableInfos) {
				
				KimLegend kimLegend = KimLegend.getLegend((KimLegend.Legend)variableInfo[2]);
				
				Variable var = ncFile.findVariable((String)variableInfo[0]);
				
				int timeLength = Integer.valueOf(variableInfo[3].toString());
				
				for(int t=0 ; t<timeLength ; t++) {
					
					if(this.testMode && t > 0) {
						continue;
					}
					
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					double[][] values = GridCalcUtil.convertVerticalStorageToValues(var.read(rangeList).getStorage(), rows, cols, false);
						
					double[] thresholds = kimLegend.getThreshholds();
					Color[] colors = kimLegend.getColors();
					
					MarchingSquares marchingSquares = new MarchingSquares();
				    GeneralPath[] isolines = marchingSquares.mkIsos(values, thresholds); // <== Just this to create isos!
				    
				    String imgFileName = "KIM_RWW3_" + issuedTmStr + "_" +  variableInfo[1] + "_" + String.format("%02d", t+1) + ".png";
				    
					File imageFile = new File(destFilePath + File.separator + imgFileName);
					
					BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
					
					Graphics2D graphic = bi.createGraphics();
					
					AffineTransform xf = new AffineTransform();
//	    		    xf.scale((right - left) / (cols - 1), (top - bottom) / (rows - 1));
	    		    xf.scale(imgWidth/(float)modelGridUtil.getModelWidth(), imgHeight/(float)modelGridUtil.getModelHeight());
//	    		    xf.translate(-1, -1); // Because MxN data was padded to (M+2)x(N+2).
	    		    for (int i = 0; i < isolines.length; i++) {
	    		        isolines[i].transform(xf); // Permanent mapping to world coords.
	    		    }
	    			   
	    		    for(int i=0 ; i<isolines.length ; i++) {
	    		    	
	    		    	graphic.setStroke(new BasicStroke(1));
	    		    	graphic.setPaint(colors[i]);
	    		    	graphic.fill(isolines[i]);
	    		    	graphic.draw(isolines[i]);
	    		    }
	    			
	    		    bi = Thumbnails.of(bi).imageType(BufferedImage.TYPE_INT_ARGB).size(imgWidth / imageResizeFactor, imgHeight / imageResizeFactor).asBufferedImage();
	    			
	    			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	    			tx.translate(0, -bi.getHeight(null));
	    			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    			bi = op.filter(bi, null);
	    				
	    			ImageIO.write(bi, "PNG", imageFile);
					
					System.out.println("\t-> Write Image File [" + imageFile.getAbsolutePath() + "]");
	    			
	    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
	    			
	    			destFileInfo.put("filePath", imageFile.getAbsolutePath());
	    			destFileInfo.put("issuedTm", issuedTmStr);
	    			destFileInfo.put("type", variableInfo[0]);
	    			
	    			destFileInfoList.add(destFileInfo);
				}
			}

			ncFile.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
}
