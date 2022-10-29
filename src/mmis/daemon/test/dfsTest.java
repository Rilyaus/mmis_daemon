package mmis.daemon.test;

import java.io.File;
import java.util.List;
import java.util.Map;

import mmis.daemon.dfs.DfsFileGenerator;
import mmis.daemon.dfs.DfsIsolineRegridImageGenerator;

public class dfsTest {
	
	public static void main(String[] args) {
		
		final String metaDirPath = "F:/data/mmis_sample/meta";
		final String dfsDirPath = "F:/data/mmis_sample/DFS";
		
		// 동네예보 이미지 테스트
		DfsFileGenerator dfs = new DfsIsolineRegridImageGenerator(metaDirPath);
		dfs.setTestMode(false);
		
		String[] dfsFileList = new String[] {
			"DFS_VSRT_GRD_GRB4_T1H.202210270530",
			"DFS_SHRT_GRD_GRB4_PCP.202107010200",
			"DFS_SHRT_GRD_GRB4_POP.202107010200",
			"DFS_SHRT_GRD_GRB4_PTY.202107010200",
			"DFS_SHRT_GRD_GRB4_REH.202107010200",
			"DFS_SHRT_GRD_GRB4_SKY.202107010200",
			"DFS_SHRT_GRD_GRB4_SNO.202107010200",
			"DFS_SHRT_GRD_GRB4_TMP.202107010200",
			"DFS_SHRT_GRD_GRB4_WAV.202107010200",
			"DFS_SHRT_GRD_GRB4_WSD.202107010200",
			"DFS_SHRT_GRD_GRB4_TMN.202209080800"
		};
		
		for(String dfsFile : dfsFileList) {
			List<Map<String, Object>> fileInfoList = dfs.generateFile(dfsDirPath + File.separator + dfsFile, dfsDirPath);
		}
		
//		
////		// 동네예보 바이너리 테스트
//		dfs = new DfsRegridBinaryGenerator(metaDirPath);
//		
//		dfsFileList = new String[] {
//			"DFS_SHRT_GRD_GRB4_UUU.202107010200",
//			"DFS_SHRT_GRD_GRB4_VVV.202107010200"
//		};
////		
//		for(String dfsFile : dfsFileList) {
//			List<Map<String, Object>> fileInfoList = dfs.generateFile(dfsDirPath + File.separator + dfsFile, dfsDirPath);
//		}
//		
//		// 동네예보 지점 데이터 추출 테스트		
//		dfsFileList = new String[] {
//			"DFS_SHRT_GRD_GRB4_TMP.202107010200"
//		};
//		
//		for(String dfsFile : dfsFileList) {
//			List<Map<String, Object>> dfsDataList = new DfsSeriesData().getData(dfsDirPath + File.separator + dfsFile, 100, 100);
//			
//			System.out.println(dfsDataList);
//		}
//		
		
	}
}
