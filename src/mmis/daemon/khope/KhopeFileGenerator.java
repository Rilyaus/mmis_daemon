package mmis.daemon.khope;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;

abstract public class KhopeFileGenerator {
	
	public enum KHOPE_MODEL {
		KHOPE_GSEA,
		KHOPE_LDPS,
		KHOPE_GDPS,
		KHOPE_GLOSEA5_DAY,
		KHOPE_GLOSEA5_DAY_NEW,
		KHOPE_GLOSEA5_SUN,
		KHOPE_HYDR,
		KHOPE_HYCOM
	}
	
	protected String khopeLatFilePath;
	protected String khopeLonFilePath;
	
	protected String khopeRegridLatFilePath;
	protected String khopeRegridLonFilePath;
	
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
		
		double[] latInterval = GridCalcUtil.calculateCumulativeArr(modelGridUtil.getLatInterval());
		double[] lonInterval = GridCalcUtil.calculateCumulativeArr(modelGridUtil.getLonInterval());
		
		BoundLonLat boundLonLat = modelGridUtil.getBoundLonLat();
		
		graphic.setComposite(AlphaComposite.Clear);
		
		for(int i=0 ; i<modelGridUtil.getModelHeight() ; i++) {
			
			for(int j=0 ; j<modelGridUtil.getModelWidth() ; j++) {
				
				double v = maskValues[modelGridUtil.getModelHeight()-1-i][j];
				
				if (v <= -999) {
					
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
	
	protected double[][] getRegridData(double[][] values) {
		
		this.modelGridUtil.setMultipleGridBoundInfoforDistanceGrid(new double[]{80, -80, 0, 360});
		
		BoundLonLat maxBoundLonLat = this.modelGridUtil.getBoundLonLat();
		
		double minLat = maxBoundLonLat.getBottom();
		double maxLat = maxBoundLonLat.getTop();
		double minLon = maxBoundLonLat.getLeft();
		double maxLon = maxBoundLonLat.getRight();
		
		int rows = this.modelGridUtil.getModelHeight();
		int cols = this.modelGridUtil.getModelWidth();
		
		double latTerm = (maxLat - minLat) / (rows-1);
		double lonTerm = (maxLon - minLon) / (cols-1);
		
		double[][] regridValues = new double[rows][];
		boolean[][] regridChecks = new boolean[rows][];
		
		for(int j=0 ; j<rows ; j++) {
			
			regridValues[j] = new double[cols];
			regridChecks[j] = new boolean[cols];
			
			for(int k=0 ; k<cols ; k++) {
				regridValues[j][k] = -999f;
				regridChecks[j][k] = false;
			}
		}
		
		for(int j=0 ; j<rows ; j++) {
			
			for(int k=0 ; k<cols ; k++) {
				
				float originLat = this.latBuffer.get(j * cols + k);
				float originLon = this.lonBuffer.get(j * cols + k);
				
				int y = (int)((originLat - minLat) / latTerm);
				int x = (int)((originLon - minLon) / lonTerm);
				
				regridValues[y][x] = values[j][k];
				regridChecks[y][x] = true;
			}
		}
		
		for(int j=0 ; j<rows ; j++) {
			
			for(int k=0 ; k<cols ; k++) {
				
				if(regridChecks[j][k] == false) {
					
					double regridLat = minLat + latTerm * j;
					double regridLon = minLon + lonTerm * k;
					
					this.modelGridUtil.setSingleGridBoundInfoforDistanceGrid(regridLon, regridLat);
					
					BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
					BoundXY boundXY = this.modelGridUtil.getBoundXY();
					
					float originLat = (float)boundLonLat.getTop();
					float originLon = (float)boundLonLat.getLeft();
					
					if(Math.abs(originLat - regridLat) < latTerm*3 && Math.abs(originLon - regridLon) < lonTerm*3) {
						regridValues[j][k] = values[boundXY.getTop()][boundXY.getLeft()];
					}
				}
			}
		}
		
		return regridValues;
	}
    
	protected void setKhopeMetaFiles(final String metaDirPath, KHOPE_MODEL khopeModelType) {
	
		switch(khopeModelType) {
		
		case KHOPE_GSEA:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_gsea_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_gsea_lon.bin";
			
			break;
		
		case KHOPE_LDPS:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_ldps_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_ldps_lon.bin";
			
			this.khopeRegridLatFilePath = metaDirPath + File.separator + "khope_ldps_regrid_lat.bin";
			this.khopeRegridLonFilePath = metaDirPath + File.separator + "khope_ldps_regrid_lon.bin";
		
			break;
			
		case KHOPE_GDPS:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_gdps_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_gdps_lon.bin";
		
			break;
			
		case KHOPE_GLOSEA5_DAY:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_glosea5_day_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_glosea5_day_lon.bin";
			
			this.khopeRegridLatFilePath = metaDirPath + File.separator + "khope_glosea5_day_regrid_lat.bin";
			this.khopeRegridLonFilePath = metaDirPath + File.separator + "khope_glosea5_day_regrid_lon.bin";
		
			break;	
			
		case KHOPE_GLOSEA5_DAY_NEW:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_glosea5_day_new_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_glosea5_day_new_lon.bin";
			
			this.khopeRegridLatFilePath = metaDirPath + File.separator + "khope_glosea5_day_new_regrid_lat.bin";
			this.khopeRegridLonFilePath = metaDirPath + File.separator + "khope_glosea5_day_new_regrid_lon.bin";
		
			break;		
			
		case KHOPE_GLOSEA5_SUN:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_glosea5_sun_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_glosea5_sun_lon.bin";
			
			this.khopeRegridLatFilePath = metaDirPath + File.separator + "khope_glosea5_sun_regrid_lat.bin";
			this.khopeRegridLonFilePath = metaDirPath + File.separator + "khope_glosea5_sun_regrid_lon.bin";
		
			break;	
			
		case KHOPE_HYDR:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_hydr_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_hydr_lon.bin";
			
			break;
			
		case KHOPE_HYCOM:
			
			this.khopeLatFilePath = metaDirPath + File.separator + "khope_hycom_lat.bin";
			this.khopeLonFilePath = metaDirPath + File.separator + "khope_hycom_lon.bin";
			
			break;
		}
	}
}
