package mmis.daemon.dfs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import mmis.daemon.dfs.parser.DFS_GRB1_Callback;
import mmis.daemon.dfs.parser.DFS_GRB1_INF;
import mmis.daemon.dfs.parser.DFS_GRB1_Loader;
import mmis.daemon.dfs.parser.DFS_Type;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.ModelGridUtil;
import mmis.daemon.util.legend.DfsLegend;
import mmis.daemon.util.marchingsquare.MarchingSquares;
import net.coobird.thumbnailator.Thumbnails;

public class DfsIsolineRegridImageGenerator extends DfsFileGenerator {
		
	private final int imageExpandFactor = 100;
	private final int imageResizeFactor = 5;
	
	private ModelGridUtil modelRegridUtil;
	
	private List<double[][]> clipCoordinateList = new ArrayList<double[][]>();
	
	public DfsIsolineRegridImageGenerator(final String metaDirPath) {
		
		this.setDfsMetaFiles(metaDirPath);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.DFS, ModelGridUtil.Position.TOP_LEFT, this.dfsLatFilePath, this.dfsLonFilePath);
		
		if(!this.loadMaskingFile()) {
			return false;
		}
		
		this.latBuffer = this.modelGridUtil.getLatBuffer();
		this.lonBuffer = this.modelGridUtil.getLonBuffer();
		
		this.modelRegridUtil = new ModelGridUtil(ModelGridUtil.Model.DFS_REGRID, ModelGridUtil.Position.TOP_LEFT, this.dfsRegridLatFilePath, this.dfsRegridLonFilePath);
		
		this.modelRegridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, 0, 360});
		
		this.readDfsClipCoordinates();
		
		return true;
	}
	
	private void readDfsClipCoordinates() {
		
		try {

			Reader reader = new FileReader(this.dfsClipCoordinateFilePath);        
			Gson gson = new Gson();        
			HashMap<String, Object> data = gson.fromJson(reader, HashMap.class);
			
			List<Object> clipCoordinateList = (List<Object>)data.get("coordinates");
			
			for(int i=0 ; i<clipCoordinateList.size() ; i++) {
				
				List<double[]> clip = new ArrayList<double[]>();
				
				List<Object> clipCoordinate = (List<Object>)clipCoordinateList.get(i);
				
				for(int j=0 ; j<clipCoordinate.size() ; j++) {
					
					List<Object> coordinate = (List<Object>)clipCoordinate.get(j);
					
					clip.add(new double[]{
						Double.valueOf(coordinate.get(0).toString()), Double.valueOf(coordinate.get(1).toString())	
					});
				}
				
				this.clipCoordinateList.add(clip.toArray(new double[clip.size()][]));
			}
			
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Isoline Regrid Image [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
			
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			
			File dfsFile = new File(srcFilePath);
			
			final DFS_Type dfsType = DFS_Type.valueOf(dfsFile.getName().split("\\.")[0].split("_")[4]);
			
			BoundLonLat boundLonLat = this.modelRegridUtil.getBoundLonLat();			
        	
        	int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
     			
			DfsLegend dfsLegend = DfsLegend.getLegend(this.getDfsLegend(dfsType.toString()));
			
			final float dfsNaNValue = this.getDfsNaNValue(dfsType.toString());
			
			final int dfsHeight = this.modelRegridUtil.getModelHeight();
			final int dfsWidth = this.modelRegridUtil.getModelWidth();
			
			(new DFS_GRB1_Loader()).parse(dfsFile, new DFS_GRB1_Callback() {
				@Override
				public boolean callback(final DFS_GRB1_INF inf, final float[][] dfsData, int index) {
		
					try {
						
						if(testMode && index > 0) {
							return true;
						}
						
						float [][] origin = new float[dfsData.length][];
						for(int i = 0; i < dfsData.length; i++) {
							origin[i] = dfsData[i].clone();
						}
						
						if(dfsType.equals(DFS_Type.REH) || 
						   dfsType.equals(DFS_Type.TMP) || 
						   dfsType.equals(DFS_Type.T3H) || 
						   dfsType.equals(DFS_Type.TMX) || 
						   dfsType.equals(DFS_Type.TMN)) {
						
							setDfsNearestValue(dfsData, origin, dfsHeight, dfsWidth, dfsNaNValue);
						}
						
						// 지워야할 격자를 세팅한다
						//setMakingValues(modelGridUtil, dfsData);
					
				        // 동네예보 발표시각(UTC)을 구한다.
		            	String issuedTmStr = String.format("%04d%02d%02d%02d%02d%02d", inf.s1.YY, inf.s1.MM, inf.s1.DD, inf.s1.HH, inf.s1.MI, 0);
		
		            	// 동네예보 예보시각(UTC)을 구한다.
		            	Calendar cal = Calendar.getInstance();
		            	cal.set(inf.s1.YY, inf.s1.MM - 1, inf.s1.DD, inf.s1.HH, inf.s1.MI, 0);
		            	cal.add(Calendar.HOUR, inf.s1.P1);
		            	String fcstTmStr = sdf.format(cal.getTime());
	
		            	double[][] regridValues = getRegridData(dfsData);
		            	
		            	double[] thresholds = dfsLegend.getThreshholds();
		    			Color[] colors = dfsLegend.getColors();
		    			
		    			MarchingSquares marchingSquares = new MarchingSquares();
		    		    GeneralPath[] isolines = marchingSquares.mkIsos(regridValues, thresholds); // <== Just this to create isos!
		               
		            	String imgFileName = dfsFile.getName().split("\\.")[0] + "_" + String.format("%02d", index+1)+".png";
		            	
		            	File imageFile = new File(destFilePath + File.separator + imgFileName);
		    			
		    			BufferedImage sourceImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		    			
		    			Graphics2D graphic = sourceImg.createGraphics();
		    			
		    			System.out.println("\t-> Write Image [" + imageFile.getAbsolutePath() + "]");
		    			
		    			AffineTransform xf = new AffineTransform();
//		    		    xf.scale((right - left) / (cols - 1), (top - bottom) / (rows - 1));
		    		    xf.scale(imgWidth/(float)modelRegridUtil.getModelWidth(), imgHeight/(float)modelRegridUtil.getModelHeight());
//		    		    xf.translate(-1, -1); // Because MxN data was padded to (M+2)x(N+2).
		    		    for (int i = 0; i < isolines.length; i++) {
		    		        isolines[i].transform(xf); // Permanent mapping to world coords.
		    		    }
		    			   
		    		    for(int i=0 ; i<isolines.length ; i++) {
		    		    	
		    		    	graphic.setStroke(new BasicStroke(1));
		    		    	graphic.setPaint(colors[i]);
		    		    	graphic.fill(isolines[i]);
		    		    	graphic.draw(isolines[i]);
		    		    }
		    			
		    			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		    			tx.translate(0, -sourceImg.getHeight(null));
		    			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    			sourceImg = op.filter(sourceImg, null);
		    			
		    			if(!dfsType.equals(DFS_Type.WAV) && !dfsType.equals(DFS_Type.WSD)) {
		    				sourceImg = getClipedImage(sourceImg, imgWidth, imgHeight);
		    			}
		    			
		    		    sourceImg = Thumbnails.of(sourceImg).imageType(BufferedImage.TYPE_INT_ARGB).size(imgWidth / imageResizeFactor, imgHeight / imageResizeFactor).asBufferedImage();
		    	
		    			ImageIO.write(sourceImg, "PNG", imageFile);
		    			
		    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
		    			
		    			destFileInfo.put("filePath", imageFile.getAbsolutePath());
		    			destFileInfo.put("issuedTm", issuedTmStr);
		    			destFileInfo.put("fcstTm", fcstTmStr);
		    			destFileInfo.put("type", dfsType.name());
		    			
		    			destFileInfoList.add(destFileInfo);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return true;
				}
			});
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Isoline Regrid Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;		
	}	
		
	private BufferedImage getClipedImage(BufferedImage sourceImg, double imgWidth, double imgHeight) {
		
		BoundLonLat boundLonLat = this.modelRegridUtil.getBoundLonLat();
		
		BufferedImage destImg = new BufferedImage((int)imgWidth, (int)imgHeight, BufferedImage.TYPE_INT_ARGB);
		
		try {
			
			for(int i=0 ; i<this.clipCoordinateList.size() ; i++) {
				
				double[][] clipCoordinates = this.clipCoordinateList.get(i);
				
				GeneralPath clip = new GeneralPath();
				
				double sx = 0;
				double sy = 0;
				
				for(int j=0 ; j<clipCoordinates.length ; j++) {
					
					double lon = clipCoordinates[j][0];
					double lat = clipCoordinates[j][1];
					
					double x = ((boundLonLat.getRight() - lon) / (boundLonLat.getRight() - boundLonLat.getLeft())) * imgWidth;
					double y = ((boundLonLat.getTop() - lat) / (boundLonLat.getTop() - boundLonLat.getBottom())) * imgHeight;
					
					if(x < 0) {
						x = 0;
					}
					
					if(x > imgWidth) {
						x = imgWidth;
					}
					
					if(y < 0) {
						y = 0;
					}
					
					if(y > imgHeight) {
						y = imgHeight;
					}
					
					if(j == 0) {
						sx = x;
						sy = y;
						clip.moveTo(imgWidth-sx, sy);
					} else {
						clip.lineTo(imgWidth-x, y);
					}
				}
				
				clip.lineTo(imgWidth-sx, sy);		
				clip.closePath();
				
				BufferedImage clipImg = new BufferedImage((int)imgWidth, (int)imgHeight, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = clipImg.createGraphics();
				clip.transform(AffineTransform.getTranslateInstance(0, 0));
				g2d.setClip(clip);
				g2d.drawImage(sourceImg, 0, 0, null);
				g2d.dispose();
				
				destImg.getGraphics().drawImage(clipImg, 0, 0, (int)imgWidth, (int)imgHeight, null);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return destImg;
	}
}
