package mmis.daemon.khope;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
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
import mmis.daemon.util.legend.KimLegend;
import mmis.daemon.util.marchingsquare.MarchingSquares;
import net.coobird.thumbnailator.Thumbnails;
import ucar.ma2.Range;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class Glosea5ProdfImageGenerator extends KhopeFileGenerator {
	
	private final int imageExpandFactor = 50;
	private final int imageResizeFactor = 1;
	
	public Glosea5ProdfImageGenerator(final String metaDirPath) {	
		
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
	
	public List<Map<String, Object>> generateFluidImageFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath) {
		
		System.out.println("-> Start Create Image [srcFilePath1=" + srcFilePath1 + ", srcFilePath2=" + srcFilePath2 + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		SimpleDateFormat issuedTmFormat = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fcstTmFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		String issuedTmStr = new File(srcFilePath1).getName().split("\\.")[0].split("_")[5];
		
		try {
			
			NetcdfDataset ncFile1 = NetcdfDataset.acquireDataset(srcFilePath1, null);
			NetcdfDataset ncFile2 = NetcdfDataset.acquireDataset(srcFilePath2, null);
			
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, 0, 360});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
		
			int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			KhopeLegend khopeLegend = KhopeLegend.getLegend(KhopeLegend.Legend.GLOSEA5_VELOCITY);
			
			Variable var1 = ncFile1.findVariable("vozocrtx");
			Variable var2 = ncFile2.findVariable("vomecrty");
			
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
					
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(d, d));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					double[][] values1 = GridCalcUtil.convertStorageToValues(var1.read(rangeList).getStorage(), rows, cols, true);
					double[][] values2 = GridCalcUtil.convertStorageToValues(var2.read(rangeList).getStorage(), rows, cols, true);
					
					double[][] values = GridCalcUtil.vectorToVelocity(values1, values2);
					
					double[][] maskValues = GridCalcUtil.convertStorageToValues(var1.read(rangeList).getStorage(), rows, cols, false);
					
					double[] thresholds = khopeLegend.getThreshholds();
					Color[] colors = khopeLegend.getColors();
					
					MarchingSquares marchingSquares = new MarchingSquares();
				    GeneralPath[] isolines = marchingSquares.mkIsos(values, thresholds); // <== Just this to create isos!
				    
				    String imgFileName = "GLOSEA5_" + issuedTmStr + "_FLUID_" + String.format("%02d", t+1) + "_" + String.format("%02d", d+1) + ".png";
				    
					File imageFile = new File(destFilePath + File.separator + imgFileName);
					
					BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
					
					Graphics2D graphic = bi.createGraphics();
					
					AffineTransform xf = new AffineTransform();
//					xf.scale((right - left) / (cols - 1), (top - bottom) / (rows - 1));
				    xf.scale(imgWidth/(float)modelGridUtil.getModelWidth(), imgHeight/(float)modelGridUtil.getModelHeight());
//					xf.translate(-1, -1); // Because MxN data was padded to (M+2)x(N+2).
				    for (int i = 0; i < isolines.length; i++) {
				        isolines[i].transform(xf); // Permanent mapping to world coords.
				    }
					   
				    for(int i=0 ; i<isolines.length ; i++) {
				    	
				    	graphic.setStroke(new BasicStroke(1));
				    	graphic.setPaint(colors[i]);
				    	graphic.fill(isolines[i]);
				    	graphic.draw(isolines[i]);
				    }
				    
				    this.setImageMaskingGrid(this.modelGridUtil, graphic, maskValues, imgWidth, imgHeight, this.imageExpandFactor, this.imageResizeFactor);
					
					bi = Thumbnails.of(bi).imageType(BufferedImage.TYPE_INT_ARGB).size(imgWidth / this.imageResizeFactor, imgHeight / this.imageResizeFactor).asBufferedImage();
					
					AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
					tx.translate(0, -bi.getHeight(null));
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
					bi = op.filter(bi, null);
					
					ImageIO.write(bi, "PNG", imageFile);
					
					System.out.println("\t-> Write Image File [" + imageFile.getAbsolutePath() + "]");
	    			
	    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
	    			
	    			destFileInfo.put("filePath", imageFile.getAbsolutePath());
	    			destFileInfo.put("issuedTm", issuedTmStr);
	    			destFileInfo.put("type", "velocity");
	    			
	    			destFileInfoList.add(destFileInfo);
				}
			}
			
			ncFile1.close();
			ncFile2.close();
			
		} catch (Exception e) {
			
		}
		
		System.out.println("-> End Create Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
		
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Image [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		final Object[][] variableInfos = new Object[][]{
			{"somxl010", "MIX", KhopeLegend.Legend.GLOSEA5_MIX, 62, null}
		};
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[0].split("_")[5];
		
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
				
				KhopeLegend khopeLegend = KhopeLegend.getLegend((KhopeLegend.Legend)variableInfo[2]);
				
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
					
					double[][] values = GridCalcUtil.convertStorageToValues(var.read(rangeList).getStorage(), rows, cols, true);
					double[][] maskValues = GridCalcUtil.convertStorageToValues(var.read(rangeList).getStorage(), rows, cols, false);
					
					double[] thresholds = khopeLegend.getThreshholds();
					Color[] colors = khopeLegend.getColors();
					
					MarchingSquares marchingSquares = new MarchingSquares();
				    GeneralPath[] isolines = marchingSquares.mkIsos(values, thresholds); // <== Just this to create isos!
				    
				    String imgFileName = "GLOSEA5_" + issuedTmStr + "_" +  variableInfo[1] + "_" + String.format("%02d", t+1) + ".png";
				    
					File imageFile = new File(destFilePath + File.separator + imgFileName);
					
					BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
					
					Graphics2D graphic = bi.createGraphics();
					
					AffineTransform xf = new AffineTransform();
//					xf.scale((right - left) / (cols - 1), (top - bottom) / (rows - 1));
				    xf.scale(imgWidth/(float)modelGridUtil.getModelWidth(), imgHeight/(float)modelGridUtil.getModelHeight());
//					xf.translate(-1, -1); // Because MxN data was padded to (M+2)x(N+2).
				    for (int i = 0; i < isolines.length; i++) {
				        isolines[i].transform(xf); // Permanent mapping to world coords.
				    }
					   
				    for(int i=0 ; i<isolines.length ; i++) {
				    	
				    	graphic.setStroke(new BasicStroke(1));
				    	graphic.setPaint(colors[i]);
				    	graphic.fill(isolines[i]);
				    	graphic.draw(isolines[i]);
				    }
				    
				    this.setImageMaskingGrid(this.modelGridUtil, graphic, maskValues, imgWidth, imgHeight, this.imageExpandFactor, this.imageResizeFactor);
					
					bi = Thumbnails.of(bi).imageType(BufferedImage.TYPE_INT_ARGB).size(imgWidth / this.imageResizeFactor, imgHeight / this.imageResizeFactor).asBufferedImage();
					
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
