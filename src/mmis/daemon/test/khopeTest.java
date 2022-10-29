package mmis.daemon.test;

import java.io.File;
import java.util.List;
import java.util.Map;

import mmis.daemon.khope.Glosea5ProdfBinaryGenerator;
import mmis.daemon.khope.Glosea5ProdfImageGenerator;
import mmis.daemon.khope.KhopeFileGenerator;
import mmis.daemon.khope.KhopeGdpsImageGenerator;
import mmis.daemon.khope.KhopeGlosea5DayImageGenerator;
import mmis.daemon.khope.KhopeGlosea5DayNewImageGenerator;
import mmis.daemon.khope.KhopeGlosea5SunImageGenerator;
import mmis.daemon.khope.KhopeGseaImageGenerator;
import mmis.daemon.khope.KhopeHycomImageGenerator;
import mmis.daemon.khope.KhopeHydrImageGenerator;
import mmis.daemon.khope.KhopeLdpsImageGenerator;

public class khopeTest {
	
	public static void main(String[] args) {
		
		final String metaDirPath = "F:/data/mmis_sample/meta";
		final String gseaDirPath = "F:/data/mmis_sample/GSEA";
		final String glosea5ProdfDirPath = "F:/data/mmis_sample/Glosea5";
		final String ldpsDirPath = "F:/data/mmis_sample/MVIS";
		final String gdpsDirPath = "F:/data/mmis_sample/MVIS";
		final String glosea5DirPath = "F:/data/mmis_sample/OSMT";
		final String hydrDirPath = "F:/data/mmis_sample/HYDR";
		final String hycomDirPath = "F:/data/mmis_sample/HYCOM";
		
//		// GSEA 모델 이미지 테스트
//		{
//			KhopeFileGenerator gsea = new KhopeGseaImageGenerator(metaDirPath);
//			gsea.setTestMode(true);		
//			
//			String[] gseaFileList = new String[] {
//				"20221013_3D.nc"
//			};
//			
//			for(String gseaFile : gseaFileList) {
//				List<Map<String, Object>> fileInfoList = gsea.generateFile(gseaDirPath + File.separator + gseaFile, gseaDirPath);
//			}
//		}
//		
//		// GSEA 모델 수온편차 테스트
//		{
//			KhopeFileGenerator gsea = new KhopeGseaImageGenerator(metaDirPath);
//			gsea.setTestMode(true);	
//			
//			List<Map<String, Object>> fileInfoList = gsea.generateFile(
//				gseaDirPath + File.separator + "20221013_3D.nc",
//				gseaDirPath + File.separator + "20211013_3D.nc", gseaDirPath);
//		}
//		
//		// Glosea5 Prodf 모델 이미지 테스트
//		{
//			Glosea5ProdfImageGenerator glosea5Prodf1 = new Glosea5ProdfImageGenerator(metaDirPath);
//			glosea5Prodf1.setTestMode(true);		
//			
//			String[] glosea5ProdfFileList = new String[] {
//				"prodf_op_sfc_ond_22_20221013_mldep.nc"
//			};
//			
//			for(String glosea5ProdfFile : glosea5ProdfFileList) {
//				List<Map<String, Object>> fileInfoList = glosea5Prodf1.generateFile(glosea5ProdfDirPath + File.separator + glosea5ProdfFile, glosea5ProdfDirPath);
//			}
//			
//			glosea5Prodf1.generateFluidImageFile(
//					glosea5ProdfDirPath + File.separator + "prodf_op_sfc_ond_22_20221013_zcur.nc", 
//					glosea5ProdfDirPath + File.separator + "prodf_op_sfc_ond_22_20221013_mcur.nc", glosea5ProdfDirPath);
//		}
//		
//		// Glosea5 Prodf 모델 Binary 테스트
//		{
//			KhopeFileGenerator glosea5Prodf2 = new Glosea5ProdfBinaryGenerator(metaDirPath);
//			glosea5Prodf2.setTestMode(true);		
//			
//			String[] glosea5ProdfFileList = new String[] {
//				"prodf_op_sfc_ond_22_20221013_zcur.nc",
//				"prodf_op_sfc_ond_22_20221013_mcur.nc"
//			};
//			
//			for(String glosea5ProdfFile : glosea5ProdfFileList) {
//				List<Map<String, Object>> fileInfoList = glosea5Prodf2.generateFile(glosea5ProdfDirPath + File.separator + glosea5ProdfFile, glosea5ProdfDirPath);
//			}
//		}
//		
//		// MVIS LDPS 모델 이미지 테스트
//		{
//			KhopeFileGenerator mvisLdps = new KhopeLdpsImageGenerator(metaDirPath);
//			mvisLdps.setTestMode(true);
//			
//			String[] ldpsFileList = new String[] {
//				"l015_v070_vis_2020082600.nc"
//			};
//			
//			for(String ldpsFile : ldpsFileList) {
//				List<Map<String, Object>> fileInfoList = mvisLdps.generateFile(ldpsDirPath + File.separator + ldpsFile, ldpsDirPath);
//			}
//		}
//		
//		// MVIS GDPS 모델 이미지 테스트
//		{
//			KhopeFileGenerator mvisGdps = new KhopeGdpsImageGenerator(metaDirPath);
//			mvisGdps.setTestMode(true);
//			
//			String[] gdpsFileList = new String[] {
//				"g128_v070_vis_2020082600.nc"
//			};
//			
//			for(String gdpsFile : gdpsFileList) {
//				List<Map<String, Object>> fileInfoList = mvisGdps.generateFile(gdpsDirPath + File.separator + gdpsFile, gdpsDirPath);
//			}
//		}
//		
//		// OSMT Glosea5 Day 모델 이미지 테스트
//		{
//			KhopeFileGenerator osmtGlosea5Day = new KhopeGlosea5DayImageGenerator(metaDirPath);
//			osmtGlosea5Day.setTestMode(true);
//			
//			String[] glosea5DayFileList = new String[] {
//				"Glosea5_day_20210704.nc"
//			};
//			
//			for(String glosea5DayFile : glosea5DayFileList) {
//				List<Map<String, Object>> fileInfoList = osmtGlosea5Day.generateFile(glosea5DirPath + File.separator + glosea5DayFile, glosea5DirPath);
//			}
//		}
//		
//		// OSMT Glosea5 Day New 모델 이미지 테스트
//		{
//			KhopeFileGenerator osmtGlosea5DayNew = new KhopeGlosea5DayNewImageGenerator(metaDirPath);
//			osmtGlosea5DayNew.setTestMode(true);
//			
//			String[] glosea5DayNewFileList = new String[] {
//				"Glosea5_day_20210704_new.nc"
//			};
//			
//			for(String glosea5DayFile : glosea5DayNewFileList) {
//				List<Map<String, Object>> fileInfoList = osmtGlosea5DayNew.generateFile(glosea5DirPath + File.separator + glosea5DayFile, glosea5DirPath);
//			}
//		}
//		
//		// OSMT Glosea5 Day Sun 모델 이미지 테스트
//		{
//			KhopeFileGenerator osmtGlosea5Sun = new KhopeGlosea5SunImageGenerator(metaDirPath);
//			osmtGlosea5Sun.setTestMode(true);
//			
//			String[] glosea5SunFileList = new String[] {
//				"Glosea5_sun_20210704.nc"
//			};
//			
//			for(String glosea5SunFile : glosea5SunFileList) {
//				List<Map<String, Object>> fileInfoList = osmtGlosea5Sun.generateFile(glosea5DirPath + File.separator + glosea5SunFile, glosea5DirPath);
//			}
//		}
//		
//		// HYDR 모델 이미지 테스트
//		{
//			KhopeFileGenerator hydr = new KhopeHydrImageGenerator(metaDirPath);
//			hydr.setTestMode(true);
//			
//			String[] hydrFileList = new String[] {
//				"KO_MOHID_HYDR_2020020600.hdf5"
//			};
//			
//			for(String hydrFile : hydrFileList) {
//				List<Map<String, Object>> fileInfoList = hydr.generateFile(hydrDirPath + File.separator + hydrFile, hydrDirPath);
//			}
//		}
//		
//		// HYCOM 모델 이미지 테스트
//		{
//			KhopeFileGenerator hycom = new KhopeHycomImageGenerator(metaDirPath);
//			hycom.setTestMode(true);
//			
//			String[] hycomFileList = new String[] {
//				"20210707.nc"
//			};
//			
//			for(String hycomFile : hycomFileList) {
//				List<Map<String, Object>> fileInfoList = hycom.generateFile(hycomDirPath + File.separator + hycomFile, hycomDirPath);
//			}
//		}
	} 
}
