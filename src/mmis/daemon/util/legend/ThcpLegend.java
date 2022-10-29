package mmis.daemon.util.legend;

import java.awt.Color;

public class ThcpLegend {
	
	public enum Legend {
		
		HEAT
	}

	private double[] thresholds;
	private Color[] colors;
	
	public ThcpLegend(double[] thresholds, Color[] colors) {
		
		this.thresholds = thresholds;
		this.colors = colors;
	}
	
	public static ThcpLegend getLegend(Legend legend) {

		double[] thresholds = null;
		Color[] colors = null;
		
		switch(legend) {
		
			
		case HEAT:
			
			thresholds = new double[]{0,0.3,0.6,0.9,1.2,1.5,1.8,2.0,2.3,2.6,2.9,3.2,3.5,3.8,3.9,4.0,4.3,4.6,4.9,5.2,5.5,5.8,6.0,6.3,6.6,6.9,7.2,7.5,7.8,7.9,8.0,8.3,8.6,8.9,9.2,9.5,9.8,10.0,10.3,10.6,10.9,11.2,11.5,11.8,11.9,12.0,12.3,12.6,12.9,13.2,13.5,13.8,14.0,14.3,14.6,14.9,15.2,15.5,15.8,15.9,16.0,16.3,16.6,16.9};
			colors = new Color[]{
						
				new Color(0,0,143),
				new Color(0,0,159),
				new Color(0,0,175),
				new Color(0,0,191),
				new Color(0,0,207),
				new Color(0,0,223),
				new Color(0,0,239),
				new Color(0,0,255),
				new Color(0,15,255),
				new Color(0,31,255),
				new Color(0,47,255),
				new Color(0,63,255),
				new Color(0,79,255),
				new Color(0,95,255),
				new Color(0,111,255),
				new Color(0,127,255),
				new Color(0,143,255),
				new Color(0,159,255),
				new Color(0,175,255),
				new Color(0,191,255),
				new Color(0,207,255),
				new Color(0,223,255),
				new Color(0,239,255),
				new Color(0,255,255),
				new Color(15,255,239),
				new Color(31,255,223),
				new Color(47,255,207),
				new Color(63,255,191),
				new Color(79,255,175),
				new Color(95,255,159),
				new Color(111,255,143),
				new Color(127,255,127),
				new Color(143,255,111),
				new Color(159,255,95),
				new Color(175,255,79),
				new Color(191,255,63),
				new Color(207,255,47),
				new Color(223,255,31),
				new Color(239,255,15),
				new Color(255,255,0),
				new Color(255,239,0),
				new Color(255,223,0),
				new Color(255,207,0),
				new Color(255,191,0),
				new Color(255,175,0),
				new Color(255,159,0),
				new Color(255,143,0),
				new Color(255,127,0),
				new Color(255,111,0),
				new Color(255,95,0),
				new Color(255,79,0),
				new Color(255,63,0),
				new Color(255,47,0),
				new Color(255,31,0),
				new Color(255,15,0),
				new Color(255,0,0),
				new Color(239,0,0),
				new Color(223,0,0),
				new Color(207,0,0),
				new Color(191,0,0),
				new Color(175,0,0),
				new Color(159,0,0),
				new Color(143,0,0),
				new Color(127,0,0)
			};
			
			break;		
	
		default:
			break;
		}
		
		return new ThcpLegend(thresholds, colors);
	}
	
	public double[] getThreshholds() {
		return this.thresholds;
	}
	
	public Color[] getColors() {
		return this.colors;
	}
	
	public Color getColor(float v) {
		
		for(int i=this.thresholds.length-1 ; i>=0 ; i--) {
			
			if(v >= this.thresholds[i]) {				
				return this.colors[i];
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		double min = 5.0000000E7;
		double max = 2.500000000E10;
		
		double term = (max-min)/(22-1);
		
		for(int i=0 ; i<22;i++) {
			System.out.print(min+ (i*term)+",");
		}
	}
}
