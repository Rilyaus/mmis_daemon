package mmis.daemon.test;

import mmis.daemon.util.grid.ModelGridUtil;

public class ModelGridTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ModelGridUtil modelGridUtil = new ModelGridUtil(ModelGridUtil.Model.KIM_RWW3, ModelGridUtil.Position.MIDDLE_CENTER, 
				"F:/data/mmis_sample/meta/kim_rww3_lat.bin", "F:/data/mmis_sample/meta/kim_rww3_lon.bin");
		modelGridUtil.setMultipleGridBoundInfoforLatLonGrid(90, -90, 0, 360);
					  
		System.out.println(modelGridUtil.getBoundXY());
	}

}
