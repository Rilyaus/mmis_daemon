package mmis.daemon.thcp;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.ModelGridUtil;

abstract public class ThcpFileGenerator {
	
	public enum THCP_MODEL {
		THCP
	}
	
	protected String thcpLatFilePath;
	protected String thcpLonFilePath;
	
	protected String thcpRegridLatFilePath;
	protected String thcpRegridLonFilePath;
	
	protected ModelGridUtil modelGridUtil;
	
	protected FloatBuffer latBuffer;
	protected FloatBuffer lonBuffer;
	
	public abstract boolean initCoordinates();
	
	public abstract List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath);
	
	protected boolean testMode = false;
	
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
	
	protected void setImageMaskingGrid(ModelGridUtil modelGridUtil, Graphics2D graphic, double[][] maskValues, int imgWidth, int imgHeight, int imageExpandFactor, int imageResizeFactor) {
		this.setImageMaskingGrid(modelGridUtil, graphic, maskValues, imgWidth, imgHeight, imageExpandFactor, imageResizeFactor, -999);
	}
	
	protected void setImageMaskingGrid(ModelGridUtil modelGridUtil, Graphics2D graphic, double[][] maskValues, int imgWidth, int imgHeight, int imageExpandFactor, int imageResizeFactor, double maskingValue) {
		
		double[] latInterval = GridCalcUtil.calculateCumulativeArr(modelGridUtil.getLatInterval());
		double[] lonInterval = GridCalcUtil.calculateCumulativeArr(modelGridUtil.getLonInterval());
		
		BoundLonLat boundLonLat = modelGridUtil.getBoundLonLat();
		
		graphic.setComposite(AlphaComposite.Clear);
		
		for(int i=0 ; i<modelGridUtil.getModelHeight() ; i++) {
			
			for(int j=0 ; j<modelGridUtil.getModelWidth() ; j++) {
				
				double v = maskValues[modelGridUtil.getModelHeight()-1-i][j];
				
				if (v == maskingValue) {
					
		        	double[] xCoords = new double[]{
						boundLonLat.getLeft() + lonInterval[j],
						boundLonLat.getLeft() + lonInterval[j+1],
						boundLonLat.getLeft() + lonInterval[j+1],
						boundLonLat.getLeft() + lonInterval[j]
					};
					
					double[] yCoords = new double[]{
						boundLonLat.getTop() - latInterval[i],
						boundLonLat.getTop() - latInterval[i],
						boundLonLat.getTop() - latInterval[i+1],
						boundLonLat.getTop() - latInterval[i+1]
					};
				
					int[] xPoints = new int[4];
					int[] yPoints = new int[4];
					
					for(int k=0 ; k<4 ; k++) {
						xPoints[k] = (int)Math.floor((xCoords[k] - boundLonLat.getLeft()) * imageExpandFactor * imageResizeFactor);
						yPoints[k] = (int)Math.floor((int)Math.floor((yCoords[k] - boundLonLat.getBottom()) * imageExpandFactor * imageResizeFactor));
					}
					
					graphic.fillRect(xPoints[0], yPoints[2], xPoints[1] - xPoints[0], yPoints[0] - yPoints[2]);
		        }
			}
		}
		
		graphic.setComposite(AlphaComposite.Src);
	}
	
	protected void setThcpMetaFiles(final String metaDirPath, THCP_MODEL thcpModelType) {
	
		switch(thcpModelType) {
		
		case THCP:
			
			this.thcpLatFilePath = metaDirPath + File.separator + "thcp_lat.bin";
			this.thcpLonFilePath = metaDirPath + File.separator + "thcp_lon.bin";
			
			break;
		}
	}
}
