package mmis.daemon.kim;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.ModelGridUtil;

abstract public class KimFileGenerator {
	
	public enum KIM_MODEL {
		KIM_CWW3,
		KIM_RWW3,
		KIM_GWW3,
		KIM_GDPS,
		KIM_RTSM,
		KIM_RTSM_RESTART
	}
	
	protected String kimLatFilePath;
	protected String kimLonFilePath;
	
	protected String kimRegridLatFilePath;
	protected String kimRegridLonFilePath;
	
	protected ModelGridUtil modelGridUtil;
	
	protected FloatBuffer latBuffer;
	protected FloatBuffer lonBuffer;
	
	public abstract boolean initCoordinates();
	
	public abstract List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath);
	
	public abstract List<Map<String, Object>> generateFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath);
	
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
	
	protected void setKimMetaFiles(final String metaDirPath, KIM_MODEL kimModelType) {
	
		switch(kimModelType) {
		
		case KIM_CWW3:
			
			this.kimLatFilePath = metaDirPath + File.separator + "kim_cww3_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_cww3_lon.bin";
			
			break;
			
		case KIM_RWW3:
					
			this.kimLatFilePath = metaDirPath + File.separator + "kim_rww3_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_rww3_lon.bin";
			
			break;
			
		case KIM_GWW3:
			
			this.kimLatFilePath = metaDirPath + File.separator + "kim_gww3_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_gww3_lon.bin";
			
			break;	
			
		case KIM_GDPS:
			
			this.kimLatFilePath = metaDirPath + File.separator + "kim_gdps_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_gdps_lon.bin";
			
			break;	
			
		case KIM_RTSM:
			
			this.kimLatFilePath = metaDirPath + File.separator + "kim_rtsm_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_rtsm_lon.bin";
			
			break;
			
		case KIM_RTSM_RESTART:
		
			this.kimLatFilePath = metaDirPath + File.separator + "kim_rtsm_restart_lat.bin";
			this.kimLonFilePath = metaDirPath + File.separator + "kim_rtsm_restart_lon.bin";
		
		break;	
		}
	}
}
