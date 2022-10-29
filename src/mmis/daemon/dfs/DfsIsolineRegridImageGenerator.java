package mmis.daemon.dfs;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
	private final int imageResizeFactor = 1;
	
	private ModelGridUtil modelRegridUtil;
	
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
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Isoline Regrid Image [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
			
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			
			File dfsFile = new File(srcFilePath);
			
			DFS_Type dfsType = DFS_Type.valueOf(dfsFile.getName().split("\\.")[0].split("_")[4]);
			
			BoundLonLat boundLonLat = this.modelRegridUtil.getBoundLonLat();			
        	
        	int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
     			
			DfsLegend dfsLegend = DfsLegend.getLegend(this.getDfsLegend(dfsType.toString()));
			
			(new DFS_GRB1_Loader()).parse(dfsFile, new DFS_GRB1_Callback() {
				@Override
				public boolean callback(final DFS_GRB1_INF inf, final float[][] dfsData, int index) {
		
					try {
						
						if(testMode && index > 0) {
							return true;
						}
						
						// 지워야할 격자를 세팅한다
						setMakingValues(modelGridUtil, dfsData);
					
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
		    			
		    			BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		    			
		    			Graphics2D graphic = bi.createGraphics();
		    			
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
		    			
		    		    bi = Thumbnails.of(bi).imageType(BufferedImage.TYPE_INT_ARGB).size(imgWidth / imageResizeFactor, imgHeight / imageResizeFactor).asBufferedImage();
		    			
		    			AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		    			tx.translate(0, -bi.getHeight(null));
		    			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    			bi = op.filter(bi, null);
		    				
		    			ImageIO.write(bi, "PNG", imageFile);
		    			
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
}
