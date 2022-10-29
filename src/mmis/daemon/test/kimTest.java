package mmis.daemon.test;

import java.io.File;
import java.util.List;
import java.util.Map;

import mmis.daemon.kim.KimFileGenerator;
import mmis.daemon.kim.KimRtsmImageGenerator;
import mmis.daemon.kim.KimRtsmRestartImageGenerator;

public class kimTest {
	
	public static void main(String[] args) {
		
		final String metaDirPath = "F:/data/mmis_sample/meta";
		final String cww3DirPath = "F:/data/mmis_sample/kim/kim_cww3";
		final String rww3DirPath = "F:/data/mmis_sample/kim/kim_rww3";
		final String gww3DirPath = "F:/data/mmis_sample/kim/kim_gww3";
		final String gdpsDirPath = "F:/data/mmis_sample/kim/kim_gdps";
		final String rtsmDirPath = "F:/data/mmis_sample/kim/kim_rtsm";
		
//		// KIM CWW3 모델 이미지 테스트
//		{
//			KimFileGenerator cww3 = new KimCww3ImageGenerator(metaDirPath);
//			cww3.setTestMode(true);
//			
//			String[] cww3FileList = new String[] {
//				"kim_cww3.2022070100.gb2"
//			};
//			
//			for(String cww3File : cww3FileList) {
//				List<Map<String, Object>> fileInfoList = cww3.generateFile(cww3DirPath + File.separator + cww3File, cww3DirPath);
//			}
//		}
//		
//		// KIM RWW3 모델 이미지 테스트
//		{
//			KimFileGenerator rww3 = new KimRww3ImageGenerator(metaDirPath);
//			rww3.setTestMode(true);
//			
//			String[] rww3FileList = new String[] {
//				"kim_rww3.2021080100.gb2"
//			};
//		
//			for(String rww3File : rww3FileList) {
//				List<Map<String, Object>> fileInfoList = rww3.generateFile(rww3DirPath + File.separator + rww3File, rww3DirPath);
//			}
//		}
//		
//		// KIM GWW3 모델 이미지 테스트
//		{
//			KimFileGenerator gww3 = new KimGww3ImageGenerator(metaDirPath);
//			gww3.setTestMode(true);
//			
//			String[] gww3FileList = new String[] {
//				"kim_gww3.2021080100.gb2"
//			};
//		
//			for(String gww3File : gww3FileList) {
//				List<Map<String, Object>> fileInfoList = gww3.generateFile(gww3DirPath + File.separator + gww3File, gww3DirPath);			
//			}
//		}
		
//		// KIM GDPS 모델 이미지 테스트
//		{
//			KimFileGenerator gdps = new KimGdpsImageGenerator(metaDirPath);
//			gdps.setTestMode(true);
//			
//			String[] gdpsFileList = new String[] {
//				"kim_g128_ne36_unis_h012.2021081900.gb2"
//			};
//			
//			for(String gdpsFile : gdpsFileList) {
//				List<Map<String, Object>> fileInfoList = gdps.generateFile(gdpsDirPath + File.separator + gdpsFile, gdpsDirPath);			
//			}
//		}
//		
//		// KIM GDPS 모델 Binary 테스트
//		{
//			KimFileGenerator gdps = new KimGdpsBinaryGenerator(metaDirPath);
//			gdps.setTestMode(true);
//			
//			String[] gdpsFileList = new String[] {
//				"kim_g128_ne36_unis_h012.2021081900.gb2"
//			};
//			
//			for(String gdpsFile : gdpsFileList) {
//				List<Map<String, Object>> fileInfoList = gdps.generateFile(gdpsDirPath + File.separator + gdpsFile, gdpsDirPath);			
//			}
//		}
//		
//		// KIM RTSM 모델 이미지 테스트
//		{
//			KimFileGenerator rtsm = new KimRtsmImageGenerator(metaDirPath);
//			rtsm.setTestMode(true);
//			
//			List<Map<String, Object>> fileInfoList = rtsm.generateFile(
//					rtsmDirPath + File.separator + "rtsm_surg_1h_2021080100.nc", 
//					rtsmDirPath + File.separator + "rtsm_tide_1h_2021080100.nc",
//					rtsmDirPath);
//		}
		
		// KIM RTSM RESTART 모델 이미지 테스트
		{
			KimFileGenerator rtsm = new KimRtsmRestartImageGenerator(metaDirPath);
			rtsm.setTestMode(true);
			
			List<Map<String, Object>> fileInfoList = rtsm.generateFile(
					rtsmDirPath + File.separator + "surg_restart_2022101212_0000.nc", 
					rtsmDirPath + File.separator + "tide_restart_2022101212_0000.nc",
					rtsmDirPath);
		}
	} 
}
