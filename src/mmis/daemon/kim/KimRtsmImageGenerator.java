package mmis.daemon.kim;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;
import mmis.daemon.util.legend.KimLegend;
import mmis.daemon.util.marchingsquare.MarchingSquares;
import net.coobird.thumbnailator.Thumbnails;
import ucar.ma2.Range;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class KimRtsmImageGenerator extends KimFileGenerator {
	
	private final int imageExpandFactor = 100;
	private final int imageResizeFactor = 1;
	
	public KimRtsmImageGenerator(final String metaDirPath) {	
		
		this.setKimMetaFiles(metaDirPath, KimFileGenerator.KIM_MODEL.KIM_RTSM);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KIM_RTSM, ModelGridUtil.Position.MIDDLE_CENTER, this.kimLatFilePath, this.kimLonFilePath);
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		return null;
	}
		
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath) {
		
		System.out.println("-> Start Create Image [srcFilePath1=" + srcFilePath1 + ", srcFilePath2=" + srcFilePath2 + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		final Object[][] variableInfos = new Object[][]{
			{"ssh", "SURG_HEIGHT", KimLegend.Legend.RTSM_SURG_HEIGHT, 121, null, 0, 0}
		};
		
		SimpleDateFormat issuedTmFormat = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fcstTmFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		String issuedTmStr = new File(srcFilePath1).getName().split("\\.")[0].split("_")[3];
		
		try {
			
			NetcdfDataset ncFile1 = NetcdfDataset.acquireDataset(srcFilePath1, null);
			NetcdfDataset ncFile2 = NetcdfDataset.acquireDataset(srcFilePath2, null);
			
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{90, -90, 0, 360});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
		
			int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			for(Object[] variableInfo : variableInfos) {
				
				KimLegend kimLegend = KimLegend.getLegend((KimLegend.Legend)variableInfo[2]);
				
				Variable var1 = ncFile1.findVariable((String)variableInfo[0]);
				Variable var2 = ncFile1.findVariable((String)variableInfo[0]);
				
				Calendar cal = new GregorianCalendar();
				cal.setTime(issuedTmFormat.parse(issuedTmStr));
				
				int timeLength = Integer.valueOf(variableInfo[3].toString());
				
				double valueOffset = Double.valueOf(variableInfo[5].toString());
				
				double maskingValue = Double.valueOf(variableInfo[6].toString());
				
				for(int t=0 ; t<timeLength ; t++) {
					
					if(this.testMode && t > 0) {
						continue;
					}
					
					String fcstTmStr = fcstTmFormat.format(cal.getTime());
					
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					double[][] values1 = GridCalcUtil.convertStorageToValues(var1.read(rangeList).getStorage(), rows, cols, valueOffset, false);
					double[][] values2 = GridCalcUtil.convertStorageToValues(var2.read(rangeList).getStorage(), rows, cols, valueOffset, false);
					
					double[][] values = GridCalcUtil.subtractValues(values1, values2);
					
					double[] thresholds = kimLegend.getThreshholds();
					Color[] colors = kimLegend.getColors();
					
					MarchingSquares marchingSquares = new MarchingSquares();
				    GeneralPath[] isolines = marchingSquares.mkIsos(values, thresholds); // <== Just this to create isos!
				    
				    String imgFileName = "KIM_RTSM_" + issuedTmStr + "_" + variableInfo[1] + "_" + String.format("%02d", t+1) + ".png";
				    
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
	    		    
	    		    this.setImageMaskingGrid(this.modelGridUtil, graphic, values, imgWidth, imgHeight, this.imageExpandFactor, this.imageResizeFactor, maskingValue);
	    			
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
	    			destFileInfo.put("fcstTm", fcstTmStr);
	    			destFileInfo.put("type", variableInfo[0]);
	    			
	    			destFileInfoList.add(destFileInfo);
					
					cal.add(Calendar.HOUR_OF_DAY, 1);
				}
			}

			ncFile1.close();
			ncFile2.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
}
