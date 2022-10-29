package mmis.daemon.test;

import java.io.File;
import java.util.List;
import java.util.Map;

import mmis.daemon.thcp.ThcpFileGenerator;
import mmis.daemon.thcp.ThcpImageGenerator;

public class thcpTest {
	
	public static void main(String[] args) {
		
		final String metaDirPath = "F:/data/mmis_sample/meta";
		final String thcpDirPath = "F:/data/mmis_sample/TCHP/";
		
		// KIM CWW3 모델 이미지 테스트
		ThcpFileGenerator thcp = new ThcpImageGenerator(metaDirPath);
		thcp.setTestMode(true);
		
		String[] thcpFileList = new String[] {
			"20210601.nc"
		};
		
		for(String thcpFile : thcpFileList) {
			List<Map<String, Object>> fileInfoList = thcp.generateFile(thcpDirPath + File.separator + thcpFile, thcpDirPath);
		}
	}
}
