package mmis.daemon.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class GridCalcUtil {
    
    static public double[] getLatitudeRatioList(double top, double bottom, int y, int scaleFactorY) {
    	
    	double total = 0;
    	List<Double> ratioList = new ArrayList<Double>();
    	
    	for(int i=0 ; i<y ; i++) {
    		
    		ratioList.add(Math.abs(1 / Math.cos((top - (i * (top - bottom) / y)) * Math.PI / 180)));
    		total += ratioList.get(i);
    	}
    	
    	for(int i=0 ; i<ratioList.size() ; i++) {
    		ratioList.set(i, ratioList.get(i) / total * scaleFactorY);
    	}
    	
    	return ArrayUtils.toPrimitive(ratioList.toArray(new Double[ratioList.size()]));
    }
    
    static public double[] calculateCumulativeArr(double[] arr) {
    	
    	double[] cumulativeArr = new double[arr.length+1];
    	
    	cumulativeArr[0] = 0;
    	
    	double total = 0;
    	
    	for(int i=0 ; i<arr.length ; i++) {
    		total += arr[i];
    		cumulativeArr[i+1] = total;
    	}
    	
    	return cumulativeArr;
    }
    
    static public double[][] subtractValues(double[][] values1, double[][] values2) {
    	
    	if(values1 == null || values2 == null) {
    		return null;
    	}
    	
    	double[][] values = new double[values1.length][];
    	
    	for(int i=0 ; i<values1.length ; i++) {
    		
    		values[i] = new double[values1[i].length];
    		
    		for(int j=0 ; j<values1[i].length ; j++) {    			
    			values[i][j] = values1[i][j] - values2[i][j];
    		}
    	}
    	
    	return values;
    }
    
    static public double[][] vectorToVelocity(double[][] values1, double[][] values2) {
    	
    	if(values1 == null || values2 == null) {
    		return null;
    	}
    	
    	double[][] values = new double[values1.length][];
    	
    	for(int i=0 ; i<values1.length ; i++) {
    		
    		values[i] = new double[values1[i].length];
    		
    		for(int j=0 ; j<values1[i].length ; j++) {   		
    			
    			if(values1[i][j] == -999f || values2[i][j] == -999f) {
    				values[i][j] = 0;
    				continue;
    			}
    			
    			values[i][j] = Math.sqrt(values1[i][j]*values1[i][j] + values2[i][j]*values2[i][j]);
    		}
    	}
    	
    	return values;
    }

	static public void setNearestGridValue(double[][] values, Object storage, int rows, int cols, int y, int x) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[_y*cols + _x];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[_y*cols + _x];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt;
    	} else {
    		values[y][x] = -999;
    	}
    }
	
	static public void setNearestReverseGridValue(double[][] values, Object storage, int rows, int cols, int y, int x) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[_x*rows + _y];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[_x*rows + _y];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt;
    	} else {
    		values[y][x] = -999;
    	}
    }
	
	static public void setNearestVerticalGridValue(double[][] values, Object storage, int rows, int cols, int y, int x) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[(rows-1-_y)*cols + _x];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[(rows-1-_y)*cols + _x];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt;
    	} else {
    		values[y][x] = -999;
    	}
    }
		
	static public void setNearestGridValue(double[][] values, Object storage, int rows, int cols, int y, int x, double offset) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[_y*cols + _x];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[_y*cols + _x];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt+offset;
    	} else {
    		values[y][x] = -999;
    	}
    }
	
	static public void setNearestReverseGridValue(double[][] values, Object storage, int rows, int cols, int y, int x, double offset) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[_x*rows + _y];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[_x*rows + _y];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt+offset;
    	} else {
    		values[y][x] = -999;
    	}
    }
	
	static public void setNearestVerticalGridValue(double[][] values, Object storage, int rows, int cols, int y, int x, double offset) {
    	
    	// i, j 위치의 값이 NaN 일 경우 인접한 NaN 값이 아닌 격자의 값을 복제한다
    	
    	int[] latDir = new int[]{-3, -2, -1, 1, 2, 3};
    	int[] lonDir = new int[]{-3, -2, -1, 1, 2, 3};
    	
    	int cnt = 0;
    	double v = 0;
    	
    	for(int i=0 ; i<latDir.length ; i++) {
    		
    		for(int j=0 ; j<lonDir.length ; j++) {
    			
    			int _y = y+latDir[i];
    			int _x = x+lonDir[j];
    			
    			if(_y<0 || _y>rows-1 || _x<0 || _x>cols-1) {
    				continue;
    			}
    			
    			Double f = null;
    			
    			if(storage instanceof float[]) {    				
    				f = (double)((float[])storage)[(rows-1-_y)*cols + _x];    				
    			} else if(storage instanceof double[]) {    				
    				f = (double)((double[])storage)[(rows-1-_y)*cols + _x];    
    			}
    			
    			if(!f.isNaN()) {
    				v += f;
    				cnt++;
				}
    		}
    	}
    	
    	if(cnt > 0) {
    		values[y][x] = v/cnt+offset;
    	} else {
    		values[y][x] = -999;
    	}
    }
    
    static public double[][] convertStorageToValues(Object storage, int rows, int cols, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
    
    static public double[][] convertVerticalStorageToValues(Object storage, int rows, int cols, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[(rows-1-i)*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestVerticalGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestVerticalGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
    
    static public double[][] convertReverseStorageToValues(Object storage, int rows, int cols, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[j*rows + i];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestReverseGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[j*rows + i];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestReverseGridValue(values, storage, rows, cols, i, j);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
    
    
    static public double[][] convertStorageToValues(Object storage, int rows, int cols, double offset, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
    
    static public double[][] convertVerticalStorageToValues(Object storage, int rows, int cols, double offset, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[(rows-1-i)*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestVerticalGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[i*cols + j];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestVerticalGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
    
    static public double[][] convertReverseStorageToValues(Object storage, int rows, int cols, double offset, boolean correct) {
    	
    	double[][] values = new double[rows][cols];
    	
    	if(storage instanceof float[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((float[])storage)[j*rows + i];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestReverseGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    		
    	} else if(storage instanceof double[]) {
    		
    		for(int i=0 ; i<rows ; i++) {
    			for(int j=0 ; j<cols ; j++) {
    				
    				Double f = (double)((double[])storage)[j*rows + i];
    				
    				if(!f.isNaN()) {
    					values[i][j] = f+offset;	
    				} else {
    					
    					if(correct) {
    						GridCalcUtil.setNearestReverseGridValue(values, storage, rows, cols, i, j, offset);	
    					} else {
    						values[i][j] = -999;
    					}
    				}
    			}
    		}	
    	}
		
		return values;
    }
}