package mmis.daemon.test;

import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationBilinear;

public class interpolationTest {
	
	public static void printArray(double[][] data) {
		
		for(int i=0 ; i<data.length ; i++) {
			
			for(int j=0 ; j<data[i].length ; j++) {
				
				System.out.print(data[i][j] + " ");
			}
			
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		
		//InterpolationBilinear interpolation = new InterpolationBilinear(Interpolation.INTERP_BICUBIC);
		
		InterpolationBilinear interpolation = new InterpolationBilinear();
		
		double[][] sample = new double[][]{
			{1.0, 2.0},
			{1.0, 2.0}
		};
		
		printArray(sample);
		
		double d = interpolation.interpolate(sample, 0.1f, 0.5f);
		
		System.out.println(d);
	}
}
