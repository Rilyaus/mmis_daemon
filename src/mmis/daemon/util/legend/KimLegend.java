package mmis.daemon.util.legend;

import java.awt.Color;

public class KimLegend {
	
	public enum Legend {
		
		GDPS_WATER_TEMP,
		GDPS_ASTD,
		GDPS_WSD,
		GDPS_MSL,
		RTSM_SURG_HEIGHT,
		GWW3_SIG_WH,
		GWW3_WP,
        RWW3_SIG_WH,
        RWW3_WP,
        CWW3_SIG_WH,
		CWW3_WP,
	}

	private double[] thresholds;
	private Color[] colors;
	
	public KimLegend(double[] thresholds, Color[] colors) {
		
		this.thresholds = thresholds;
		this.colors = colors;
	}
	
	public static KimLegend getLegend(Legend legend) {

		double[] thresholds = null;
		Color[] colors = null;
		
		switch(legend) {
		
		case GDPS_WATER_TEMP:
			
			thresholds = new double[]{-5,-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5,24,24.5};
			colors = new Color[]{
						
				new Color(229,172,255),
				new Color(218,135,255),
				new Color(205,97,255),
				new Color(194,62,255),
				new Color(183,31,255),
				new Color(173,7,255),
				new Color(160,0,247),
				new Color(146,0,228),
				new Color(135,0,206),
				new Color(127,0,191),
				new Color(203,204,232),
				new Color(179,180,222),
				new Color(154,155,211),
				new Color(128,129,199),
				new Color(101,103,188),
				new Color(76,78,177),
				new Color(52,54,167),
				new Color(31,33,157),
				new Color(13,16,150),
				new Color(0,3,144),
				new Color(172,229,255),
				new Color(135,217,255),
				new Color(97,205,255),
				new Color(62,193,255),
				new Color(31,181,255),
				new Color(7,171,255),
				new Color(0,157,246),
				new Color(0,141,222),
				new Color(0,128,196),
				new Color(0,119,179),
				new Color(150,254,150),
				new Color(105,252,105),
				new Color(64,249,64),
				new Color(30,243,30),
				new Color(8,233,8),
				new Color(0,213,0),
				new Color(0,189,0),
				new Color(0,164,0),
				new Color(0,142,0),
				new Color(0,128,0),
				new Color(255,240,154),
				new Color(255,234,110),
				new Color(255,227,67),
				new Color(255,220,31),
				new Color(255,214,4),
				new Color(249,205,0),
				new Color(237,195,0),
				new Color(224,185,0),
				new Color(212,176,0),
				new Color(204,170,0),
				new Color(252,171,171),
				new Color(250,133,133),
				new Color(248,96,96),
				new Color(246,62,62),
				new Color(243,33,33),
				new Color(238,11,11),
				new Color(227,0,0),
				new Color(213,0,0),
				new Color(200,0,0),
				new Color(191,0,0)				
			};
			
			break;
			
		case GDPS_WSD:
			
			thresholds = new double[]{0,0.2,0.4,0.6,0.8,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,8,9,10,15,20,25,30,35,40,45,50,55};
			colors = new Color[]{
				new Color(255,234,110),
				new Color(255,220,31),
				new Color(249,205,0),
				new Color(224,185,0),
				new Color(204,170,0),
				new Color(105,252,105),
				new Color(30,243,30),
				new Color(0,213,0),
				new Color(0,164,0),
				new Color(0,128,0),
				new Color(135,217,255),
				new Color(62,193,255),
				new Color(7,171,255),
				new Color(0,141,222),
				new Color(0,119,179),
				new Color(179,180,222),
				new Color(128,129,199),
				new Color(76,78,177),
				new Color(31,33,157),
				new Color(57,0,144),
				new Color(218,135,255),
				new Color(194,62,255),
				new Color(173,7,255),
				new Color(146,0,0),
				new Color(127,0,191),
				new Color(250,133,133),
				new Color(246,62,62),
				new Color(238,11,11),
				new Color(213,0,0),
				new Color(191,0,0)
			};
			
			break;	
			
		case GDPS_ASTD:
			
			thresholds = new double[]{-16,-15,-14,-13,-12,-11,-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
			colors = new Color[]{
						
				new Color(0,3,144),
				new Color(31,33,157),
				new Color(76,78,177),
				new Color(128,129,199),
				new Color(179,180,222),
				new Color(0,119,179),
				new Color(0,141,222),
				new Color(7,171,255),
				new Color(62,193,255),
				new Color(135,217,255),
				new Color(0,128,0),
				new Color(0,164,0),
				new Color(0,213,0),
				new Color(30,243,30),
				new Color(105,252,105),
				new Color(230,230,230),
				new Color(255,234,110),
				new Color(255,220,31),
				new Color(249,205,0),
				new Color(224,185,0),
				new Color(204,170,0),
				new Color(250,133,133),
				new Color(246,62,62),
				new Color(238,11,11),
				new Color(213,0,0),
				new Color(191,0,0),
				new Color(218,135,255),
				new Color(194,62,255),
				new Color(173,7,255),
				new Color(146,0,0),
				new Color(127,0,191)
			};
			
			break;
			
		case GDPS_MSL:
			
			thresholds = new double[]{101000,101050,101100,101150,101200,101250,101300,101350,101400,101450,101500,101550,101600,101650,101700,101750,101800,101850,101900,101950,102000,102050,102100,102150,102200,102250,102300,102350,102400,102450,102500,102550,102600,102650,102700,102750,102800,102850,102900,102950,103000,103050,103100,103150,103200,103250,103300,103350,103400,103450,103500,103550,103600,103650,103700,103750,103800,103850,103900,103950};
			colors = new Color[]{
				new Color(229,172,255),
				new Color(218,135,255),
				new Color(205,97,255),
				new Color(194,62,255),
				new Color(183,31,255),
				new Color(173,7,255),
				new Color(160,0,247),
				new Color(146,0,228),
				new Color(135,0,206),
				new Color(127,0,191),
				new Color(203,204,232),
				new Color(179,180,222),
				new Color(154,155,211),
				new Color(128,129,199),
				new Color(101,103,188),
				new Color(76,78,177),
				new Color(52,54,167),
				new Color(31,33,157),
				new Color(13,16,150),
				new Color(0,3,144),
				new Color(172,229,255),
				new Color(135,217,255),
				new Color(97,205,255),
				new Color(62,193,255),
				new Color(31,181,255),
				new Color(7,171,255),
				new Color(0,157,246),
				new Color(0,141,222),
				new Color(0,128,196),
				new Color(0,119,179),
				new Color(150,254,150),
				new Color(105,252,105),
				new Color(64,249,64),
				new Color(30,243,30),
				new Color(8,233,8),
				new Color(0,213,0),
				new Color(0,189,0),
				new Color(0,164,0),
				new Color(0,142,0),
				new Color(0,128,0),
				new Color(255,240,154),
				new Color(255,234,110),
				new Color(255,227,67),
				new Color(255,220,31),
				new Color(255,214,4),
				new Color(249,205,0),
				new Color(237,195,0),
				new Color(224,185,0),
				new Color(212,176,0),
				new Color(204,170,0),
				new Color(252,171,171),
				new Color(250,133,133),
				new Color(248,96,96),
				new Color(246,62,62),
				new Color(243,33,33),
				new Color(238,11,11),
				new Color(227,0,0),
				new Color(213,0,0),
				new Color(200,0,0),
				new Color(191,0,0)
			};
			
			break;	
							
		case RTSM_SURG_HEIGHT:
			
			thresholds = new double[]{-1,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1,0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
			colors = new Color[]{
				new Color(40,27,114),
				new Color(27,18,161),
				new Color(13,9,208),
				new Color(0,0,255),
				new Color(0,85,170),
				new Color(0,170,85),
				new Color(0,255,0),
				new Color(85,255,85),
				new Color(170,255,170),
				new Color(255,255,255),
				new Color(255,255,255),
				new Color(251,253,184),
				new Color(247,251,112),
				new Color(243,249,41),
				new Color(245,179,39),
				new Color(246,110,36),
				new Color(248,40,34),
				new Color(223,35,45),
				new Color(199,29,56),
				new Color(174,24,67)
			};
			
			break;	
			
		case CWW3_WP:
		case RWW3_WP:
		case GWW3_WP:
          thresholds = new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
          colors = new Color[]{
    		  new Color(251,251,253),
    		  new Color(215,239,253),
    		  new Color(178,216,254),
    		  new Color(124,189,254),
    		  new Color(102,166,255),
    		  new Color(74,135,246),
    		  new Color(154,255,129),
    		  new Color(103,229,78),
    		  new Color(255,255,130),
    		  new Color(217,217,51),
    		  new Color(255,210,150),
    		  new Color(255,180,120),
    		  new Color(255,150,90),
    		  new Color(255,100,50),
    		  new Color(255,50,30),
    		  new Color(255,153,255),
    		  new Color(217,0,217),
    		  new Color(179,0,0),
    		  new Color(102,0,0),
    		  new Color(12,100,21)
          };
          
          break;
		case CWW3_SIG_WH:
		case RWW3_SIG_WH:
		case GWW3_SIG_WH:
			thresholds = new double[]{0.0,0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,4.5,5.0,5.5,6.0,6.5,7.0,7.5,8.0,8.5,9.0,9.5};
			colors = new Color[]{
				new Color(252,253,254),
				new Color(179,217,255),
				new Color(102,166,255),
				new Color(76,140,255),
				new Color(154,255,129),
				new Color(103,229,78),
				new Color(255,255,0),
				new Color(229,229,80),
				new Color(255,180,120),
				new Color(255,100,50),
				new Color(229,25,0),
				new Color(153,0,0),
				new Color(229,153,255),
				new Color(204,76,255),
				new Color(179,25,255),
				new Color(0,229,0),
				new Color(0,148,255),
				new Color(0,102,255),
				new Color(102,0,255),
				new Color(10,103,17)
			};
		default:
			break;
		}
		
		return new KimLegend(thresholds, colors);
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
}
