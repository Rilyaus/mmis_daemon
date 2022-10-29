package mmis.daemon.dfs;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import mmis.daemon.util.grid.ModelGridUtil;

public class DfsRegridBinaryGenerator extends DfsFileGenerator {
	
	public DfsRegridBinaryGenerator(final String metaDirPath) {
		
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
		
		return true;
	}
	
	@Override
	public List<Map<String, Object>> generateFile(final String srcFilePath, final String destFilePath) {
		
		System.out.println("-> Start Create Regrid Binary [srcFilePath=" + srcFilePath + ", destFilePath=" + destFilePath + "]");
		
		final List<Map<String, Object>> destFileInfoList = new ArrayList<Map<String, Object>>();
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
			int modelHeight = this.modelGridUtil.getModelHeight();
			int modelWidth = this.modelGridUtil.getModelWidth();
			
			File dfsFile = new File(srcFilePath);
			
			DFS_Type dfsType = DFS_Type.valueOf(dfsFile.getName().split("\\.")[0].split("_")[4]);
			
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
		               
		            	String binaryFileName = dfsFile.getName().split("\\.")[0] + "_REGRID_" + String.format("%02d", index+1)+".bin";
		          				  
		            	String binaryFilePath = destFilePath + File.separator + binaryFileName;
		      			
		    			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(binaryFilePath)));
		    			
		    			for(int j=0 ; j<modelHeight ; j++) {					
		    				for(int k=0 ; k<modelWidth ; k++) {
		    					out.writeFloat((float)regridValues[j][k]);
		    				}
		    			}
		    			
		    			out.close();
		    			
		    			System.out.println("\t-> Write Binary File [" + binaryFilePath + "]");
		    			
		    			Map<String, Object> destFileInfo = new HashMap<String, Object>();
		    			
		    			destFileInfo.put("filePath", binaryFilePath);
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
		
		System.out.println("-> End Create Regrid Binary [File Count= " + destFileInfoList.size() + "]");
		
		return destFileInfoList;
	}
}
