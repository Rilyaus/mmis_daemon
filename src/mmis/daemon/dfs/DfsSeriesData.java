package mmis.daemon.dfs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mmis.daemon.dfs.parser.DFS_GRB1_Callback;
import mmis.daemon.dfs.parser.DFS_GRB1_INF;
import mmis.daemon.dfs.parser.DFS_GRB1_Loader;
import mmis.daemon.dfs.parser.DFS_Type;

public class DfsSeriesData {
	
	public DfsSeriesData() {
		
	}
	
	public List<Map<String, Object>> getData(final String srcFilePath, final int x, final int y) {
		
		final List<Map<String, Object>> dfsDataList = new ArrayList<Map<String, Object>>();
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
						
			File dfsFile = new File(srcFilePath);
			
			DFS_Type dfsType = DFS_Type.valueOf(dfsFile.getName().split("\\.")[0].split("_")[4]);
			
			(new DFS_GRB1_Loader()).parse(dfsFile, new DFS_GRB1_Callback() {
				@Override
				public boolean callback(final DFS_GRB1_INF inf, final float[][] dfsData, int index) {

					try {
					
				        // 동네예보 발표시각(UTC)을 구한다.
		            	String issuedTmStr = String.format("%04d%02d%02d%02d%02d%02d", inf.s1.YY, inf.s1.MM, inf.s1.DD, inf.s1.HH, inf.s1.MI, 0);
		
		            	// 동네예보 예보시각(UTC)을 구한다.
		            	Calendar cal = Calendar.getInstance();
		            	cal.set(inf.s1.YY, inf.s1.MM - 1, inf.s1.DD, inf.s1.HH, inf.s1.MI, 0);
		            	cal.add(Calendar.HOUR, inf.s1.P1);
		            	String fcstTmStr = sdf.format(cal.getTime());
	    
		            	Map<String, Object> dataMap = new HashMap<String, Object>();
		            	
		            	dataMap.put("fcstTm", fcstTmStr);
		            	dataMap.put("value", dfsData[y][x]);
		            	dataMap.put("type", dfsType.name());
		    			
		            	dfsDataList.add(dataMap);
						
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					
					return true;
				}
			});
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dfsDataList;
	}
}
