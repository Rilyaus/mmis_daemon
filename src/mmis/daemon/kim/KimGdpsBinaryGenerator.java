package mmis.daemon.kim;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mmis.daemon.util.GridCalcUtil;
import mmis.daemon.util.grid.BoundLonLat;
import mmis.daemon.util.grid.BoundXY;
import mmis.daemon.util.grid.ModelGridUtil;
import ucar.ma2.Range;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class KimGdpsBinaryGenerator extends KimFileGenerator {
	
	private final int imageExpandFactor = 5;
	private final int imageResizeFactor = 1;
	
	public KimGdpsBinaryGenerator(final String metaDirPath) {	
		
		this.setKimMetaFiles(metaDirPath, KimFileGenerator.KIM_MODEL.KIM_GDPS);
		this.initCoordinates();
	}

	@Override
	public boolean initCoordinates() {
		
		this.modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KIM_GDPS, ModelGridUtil.Position.MIDDLE_CENTER, this.kimLatFilePath, this.kimLonFilePath, 180);
		
		return true;
	}
		
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath1, final String srcFilePath2, final String destFilePath) {
		return null;
	}
		
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Binary [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		final Object[][] variableInfos = new Object[][]{
			{"u-component_of_wind_height_above_ground", "UUU", 1, null},
			{"v-component_of_wind_height_above_ground", "VVV", 1, null}
		};
		
		SimpleDateFormat issuedTmFormat = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fcstTmFormat = new SimpleDateFormat("yyyyMMddHHmm");
		
		String issuedTmStr = new File(srcFilePath).getName().split("\\.")[1];
		int fcstHour = Integer.valueOf(new File(srcFilePath).getName().split("\\.")[0].split("_")[4].replace("h", ""));
		
		try {
			
			NetcdfDataset ncFile = NetcdfDataset.acquireDataset(srcFilePath, null);
			
			this.modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(new double[]{90, -90, -180, 180});
			
			BoundLonLat boundLonLat = this.modelGridUtil.getBoundLonLat();
			BoundXY boundXY = this.modelGridUtil.getBoundXY();
			
			int rows = this.modelGridUtil.getRows();
			int cols = this.modelGridUtil.getCols();
			
			for(Object[] variableInfo : variableInfos) {
				
				Variable var = ncFile.findVariable((String)variableInfo[0]);
				
				Calendar cal = new GregorianCalendar();
				cal.setTime(issuedTmFormat.parse(issuedTmStr));
				
				int timeLength = Integer.valueOf(variableInfo[2].toString());
				
				for(int t=0 ; t<timeLength ; t++) {
					
					if(this.testMode && t > 0) {
						continue;
					}
					
					String fcstTmStr = fcstTmFormat.format(cal.getTime());
					
					List<Range> rangeList = new ArrayList<Range>();
					rangeList.add(new Range(t, t));
					rangeList.add(new Range(0, 0));
					rangeList.add(new Range(modelGridUtil.getModelHeight() - boundXY.getTop() - 1, modelGridUtil.getModelHeight() - boundXY.getBottom() - 1));
					rangeList.add(new Range(boundXY.getLeft(), boundXY.getRight()));
					
					float[] storage = this.swapLongitudeLine((float[])var.read(rangeList).getStorage());
					
					double[][] values = GridCalcUtil.convertVerticalStorageToValues(storage, rows, cols, false);
								    
				    String binaryFileName = "KIM_GDPS_" + issuedTmStr + "_" + variableInfo[1] + "_" + String.format("%02d", fcstHour/3+1) + ".bin";
				    
				    String binaryFilePath = destFilePath + File.separator + binaryFileName;
				
				    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(binaryFilePath)));
	    			
	    			for(int j=0 ; j<rows ; j++) {					
	    				for(int k=0 ; k<cols ; k++) {
	    					if(values[j][k] <= -999) {
	    						out.writeFloat(0f);
	    					} else {
	    						out.writeFloat((float)values[j][k]);	
	    					}
	    				}
	    			}
	    			
	    			out.close();
					
					System.out.println("\t-> Write Binary File [" + binaryFilePath + "]");
	    			
	    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
	    			
	    			destFileInfo.put("filePath", binaryFilePath);
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
		
		System.out.println("-> End Create Binary [File Count= " + destFileInfoList.size() + "]");
		
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
