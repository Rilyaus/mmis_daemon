package mmis.daemon.dfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

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
                	values[i][j] =-999f;
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
}
