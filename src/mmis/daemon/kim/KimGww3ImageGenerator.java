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

public class KimGww3ImageGenerator extends KimFileGenerator {
	
	private final int imageExpandFactor = 10;
	private final int imageResizeFactor = 1;
	
	public KimGww3ImageGenerator(final String metaDirPath) {	
		
		this.setKimMetaFiles(metaDirPath, KimFileGenerator.KIM_MODEL.KIM_GWW3);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KIM_GWW3, ModelGridUtil.Position.TOP_LEFT, this.kimLatFilePath, this.kimLonFilePath, 180);
		
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
			{"Peak_wave_period_surface", "WP", KimLegend.Legend.GWW3_WP, 49, null},
            {"Significant_height_of_wind_waves_surface", "SIG_WH", KimLegend.Legend.GWW3_SIG_WH, 49, null}
		};
		
		SimpleDateFormat issuedTmFormat = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fcstTmFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[1];
		
		try {
			
			NetcdfDataset ncFile = NetcdfDataset.acquireDataset(srcFilePath, null);
		
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, -180, 180});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
			
			int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			for(Object[] variableInfo : variableInfos) {
				
				KimLegend kimLegend = KimLegend.getLegend((KimLegend.Legend)variableInfo[2]);
				
				Variable var = ncFile.findVariable((String)variableInfo[0]);
				
				Calendar cal = new GregorianCalendar();
				cal.setTime(issuedTmFormat.parse(issuedTmStr));
				
				int timeLength = Integer.valueOf(variableInfo[3].toString());
				
				for(int t=0 ; t<timeLength ; t++) {
					
					if(this.testMode && t > 0) {
						continue;
					}
					
					String fcstTmStr = fcstTmFormat.format(cal.getTime());
					
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					float[] storage = this.swapLongitudeLine((float[])var.read(rangeList).getStorage());
					
					double[][] values = GridCalcUtil.convertVerticalStorageToValues(storage, rows, cols, true);
					double[][] maskValues = GridCalcUtil.convertVerticalStorageToValues(storage, rows, cols, false);
					
					double[] thresholds = kimLegend.getThreshholds();
					Color[] colors = kimLegend.getColors();
					
					MarchingSquares marchingSquares = new MarchingSquares();
				    GeneralPath[] isolines = marchingSquares.mkIsos(values, thresholds); // <== Just this to create isos!
				    
				    String imgFileName = "KIM_GWW3_" + issuedTmStr + "_" +  variableInfo[1] + "_" + String.format("%02d", t+1) + ".png";
				    
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
	    		    
	    		    this.setImageMaskingGrid(this.modelGridUtil, graphic, maskValues, imgWidth, imgHeight, this.imageExpandFactor, this.imageResizeFactor);
	    			
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

			ncFile.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
	
	private float[] swapLongitudeLine(float[] storage) {
		
		float[] _storage = new float[storage.length];
		
		int modelHeight = this.modelGridUtil.getModelHeight();
		int modelWidth = this.modelGridUtil.getModelWidth();
		
		for(int i=0 ; i<modelHeight ; i++) {
			
			for(int j=0 ; j<modelWidth ; j++) {
			
				float v = storage[i*modelWidth + j];
				
				// 180도선 이하에 있는 값을 밀어준다
				if(j < modelWidth / 2) {					
					_storage[i*modelWidth + j + modelWidth / 2] = v;
					
				// 180도선 이상의 값을 앞으로 땡긴다
				} else {
					_storage[i*modelWidth + j - modelWidth / 2] = v;
				}
			}
		}
		
		return _storage;
	}
}
