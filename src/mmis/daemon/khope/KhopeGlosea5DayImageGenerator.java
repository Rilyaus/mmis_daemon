package mmis.daemon.khope;

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

public class KhopeGlosea5DayImageGenerator extends KhopeFileGenerator {
	
	private final int imageExpandFactor = 50;
	private final int imageResizeFactor = 1;
	
	private ModelGridUtil modelRegridUtil;
	
	public KhopeGlosea5DayImageGenerator(final String metaDirPath) {	
		
		this.setKhopeMetaFiles(metaDirPath, KhopeFileGenerator.KHOPE_MODEL.KHOPE_GLOSEA5_DAY);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KHOPE_GLOSEA5_DAY, ModelGridUtil.Position.TOP_LEFT, this.khopeLatFilePath, this.khopeLonFilePath);
		
		this.latBuffer = this.modelGridUtil.getLatBuffer();
		this.lonBuffer = this.modelGridUtil.getLonBuffer();
		
		this.modelRegridUtil = new ModelGridUtil(ModelGridUtil.Model.KHOPE_GLOSEA5_DAY_REGRID, ModelGridUtil.Position.TOP_LEFT, this.khopeRegridLatFilePath, this.khopeRegridLonFilePath);
		
		this.modelRegridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{80, -80, 0, 360});
		
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
			{"heat_content", "HEAT", KhopeLegend.Legend.GLOSEA5_HEAT, 75, -1},
			{"temp", "TEMP", KhopeLegend.Legend.GLOSEA5_TEMP, 75, 6}
		};
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[0].split("_")[2];
		
		try {
			
			NetcdfDataset ncFile = NetcdfDataset.acquireDataset(srcFilePath, null);
			
			BoundLonLat boundLonLat = this.modelRegridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelRegridUtil.getBoundXY();
		
			int imgHeight = (int)Math.floor((boundLonLat.getTop() - boundLonLat.getBottom()) * this.imageExpandFactor * this.imageResizeFactor); 		    			
			int imgWidth = (int)Math.floor((boundLonLat.getRight() - boundLonLat.getLeft()) * this.imageExpandFactor * this.imageResizeFactor);
			
			int rows = this.modelRegridUtil.getRows();
			int cols = this.modelRegridUtil.getCols();
			
			for(Object[] variableInfo : variableInfos) {
				
				KhopeLegend khopeLegend = KhopeLegend.getLegend((KhopeLegend.Legend)variableInfo[2]);
				
				Variable var = ncFile.findVariable((String)variableInfo[0]);
				
				int timeLength = Integer.valueOf(variableInfo[3].toString());
				int depLength = Integer.valueOf(variableInfo[4].toString());
				
				for(int t=0 ; t<timeLength ; t++) {
					
					if(this.testMode && t > 0) {
						continue;
					}
					
					// 프로그램 구조상 해당 변수의 깊이가 없다고 하더라도 해당 구문을 실행시켜야 한다
					for(int d=0 ; d<(depLength<0?1:depLength) ; d++) {
					
						if(this.testMode && d > 0) {
							continue;
						}
						
						List<Range> rangeList = new ArrayList<Range>();
						rangeList.add(new Range(t, t));
						
						// 때문에 depLength 가 0보다 클때만 rangeList 에 범위를 추가한다
						if(depLength > 0) {
							rangeList.add(new Range(d, d));
						}
						
						rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
						rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
						
						double[][] regridValues = getRegridData(GridCalcUtil.convertStorageToValues(var.read(rangeList).getStorage(), rows, cols, false));			
						
						double[] thresholds = khopeLegend.getThreshholds();
						Color[] colors = khopeLegend.getColors();
						
						MarchingSquares marchingSquares = new MarchingSquares();
					    GeneralPath[] isolines = marchingSquares.mkIsos(regridValues, thresholds); // <== Just this to create isos!
					    
					    String imgFileName = "KHOPE_GLOSEA5_DAY_" + issuedTmStr + "_" +  variableInfo[1] + "_" + 
					    		String.format("%02d", t+1) + (depLength>0?"_"+String.format("%02d", d+1):"") + ".png";
					    
						File imageFile = new File(destFilePath + File.separator + imgFileName);
						
						BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
						
						Graphics2D graphic = bi.createGraphics();
						
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
		    		    
		    		    this.setImageMaskingGrid(this.modelRegridUtil, graphic, regridValues, imgWidth, imgHeight, this.imageExpandFactor, this.imageResizeFactor);
		    			
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
			}

			ncFile.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-> End Create Image [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
}
