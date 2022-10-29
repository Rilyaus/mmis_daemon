package mmis.daemon.dfs;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;
import mmis.daemon.util.legend.DfsLegend;

abstract public class DfsFileGenerator {
	
	protected String dfsLatFilePath;
	protected String dfsLonFilePath;
	
	protected String dfsRegridLatFilePath;
	protected String dfsRegridLonFilePath;
	
	protected String dfsMaskingFilePath;
	
	protected String dfsClipCoordinateFilePath;
	
	protected ModelGridUtil modelGridUtil;
		
	protected byte[] maskingValues;
	
	protected FloatBuffer latBuffer;
	protected FloatBuffer lonBuffer;
	
	public abstract boolean initCoordinates();
	
	public abstract List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath);
	
	protected boolean testMode = false;
	
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
	
	protected void setDfsMetaFiles(final String metaDirPath) {
	
		this.dfsLatFilePath = metaDirPath + File.separator + "dfs_lat.bin";
		this.dfsLonFilePath = metaDirPath + File.separator + "dfs_lon.bin";
		this.dfsRegridLatFilePath = metaDirPath + File.separator + "dfs_regrid_lat.bin";
		this.dfsRegridLonFilePath = metaDirPath + File.separator + "dfs_regrid_lon.bin";
		this.dfsMaskingFilePath = metaDirPath + File.separator + "dfs_mask.bin";
		this.dfsClipCoordinateFilePath = metaDirPath + File.separator + "dfs_clip.json";
	}
	
	protected boolean loadMaskingFile() {
		
		try {
			
			FileInputStream fis = new FileInputStream(this.dfsMaskingFilePath);
            
			this.maskingValues = new byte[this.modelGridUtil.getModelHeight() * this.modelGridUtil.getModelWidth()];
			
			fis.read(this.maskingValues);
            
			fis.close();
			
			return true;
            
        } catch (IOException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	protected void setMakingValues(ModelGridUtil modelGridUtil, float[][] values) {
		
		for(int i=0 ; i<modelGridUtil.getModelHeight() ; i++) {
			
			for(int j=0 ; j<modelGridUtil.getModelWidth() ; j++) {
				
				byte m = this.maskingValues[i * modelGridUtil.getModelWidth() + j];
				
                if (false
                 //	  || m == 0 // 바다
                 //   || m == 1 // 남한
                 //   || m == 2 // 북한
                    || m == 3 // 중국
                    || m == 4 // 일본                    
                        )
                {
                	values[i][j] = -999f;
                }
			}
		}
	}
	
	protected double[][] getRegridData(float[][] values) {
		
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
	
	protected DfsLegend.Legend getDfsLegend(final String dfsType) {
		
		switch(dfsType) {
		
		case "REH":			
			return DfsLegend.Legend.REH;
		case "WAV":
			return DfsLegend.Legend.WAV;
		case "POP":
			return DfsLegend.Legend.POP;
		case "WSD":
			return DfsLegend.Legend.WSD;
		case "PCP":
			return DfsLegend.Legend.PCP;
		case "R06":
			return DfsLegend.Legend.PCP;
		case "SNO":
			return DfsLegend.Legend.SNO;
		case "S06":
			return DfsLegend.Legend.SNO;
		case "TMP":
			return DfsLegend.Legend.TMP;
		case "T1H":
			return DfsLegend.Legend.TMP;
		case "T3H":
			return DfsLegend.Legend.TMP;
		case "TMN":
			return DfsLegend.Legend.TMP;
		case "TMX":
			return DfsLegend.Legend.TMP;
		case "SKY":
			return DfsLegend.Legend.SKY;
		case "PTY":
			return DfsLegend.Legend.PTY;
		}
		
		return null;
	}
	
	protected float getDfsNaNValue(final String dfsType) {
		
		switch(dfsType) {
		
		case "REH":			
			return -1.0f;
		case "WAV":
			return -1.0f;
		case "POP":
			return -1.0f;
		case "WSD":
			return -1.0f;
		case "PCP":
			return -1.0f;
		case "R06":
			return -1.0f;
		case "SNO":
			return -1.0f;
		case "S06":
			return -1.0f;
		case "TMP":
			return -50f;
		case "T1H":
			return -50f;
		case "T3H":
			return -50f;
		case "TMN":
			return -50f;
		case "TMX":
			return -50f;
		case "SKY":
			return -1.0f;
		case "PTY":
			return -1.0f;
		}
		
		return 0f;
	}
	

	protected void setDfsNearestValue(float[][] values, float[][] origin, int rows, int cols, float nanValue) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3,-2,-1, 1, 2, 3};
    	int[] lonDir = new int[]{-3,-2,-1, 1, 2, 3};
    	
    	for(int i=0 ; i<rows ; i++) {
    		
    		for(int j=0 ; j<cols ; j++) {
    			
    			if(origin[i][j] != nanValue) {
    				continue;
    			}

    	    	int cnt = 0;
    	    	float v = 0;
    	    	
    			for(int ii=0 ; ii<latDir.length ; ii++) {
    	    		
    	    		for(int jj=0 ; jj<lonDir.length ; jj++) {
    	    			
    	    			int _y = i+latDir[ii];
    	    			int _x = j+lonDir[jj];
    	    			
    	    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    	    				continue;
    	    			}
    	    			
    	    			Float f = origin[_y][_x];
    	    			
    	    			if(f != nanValue) {
    	    				
    	    				v += f;
    	    				cnt++;
    					}
    	    		}
    	    	}
    	    	
    	    	if(cnt > 0) {
    	    		values[i][j] = v/cnt;
    	    	} else {
    	    		values[i][j] = nanValue;
    	    	}
    		}
    	}
    }
}
