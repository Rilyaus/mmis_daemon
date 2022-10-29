package mmis.daemon.util.legend;

import java.awt.Color;

public class KhopeLegend {
	
	public enum Legend {
		GSEA_SALT,
		GSEA_TEMP,
		GSEA_TW_DIFF, //수온 편차
		LDPS_VIS,
		GDPS_VIS,
		GLOSEA5_HEAT,
		GLOSEA5_TEMP,
		GLOSEA5_MIX,
		GLOSEA5_VELOCITY,
		HYDR_SALT,
		HYDR_TEMP,
		HYCOM_SALT,
		HYCOM_TEMP
	}

	private double[] thresholds;
	private Color[] colors;
	
	public KhopeLegend(double[] thresholds, Color[] colors) {
		
		this.thresholds = thresholds;
		this.colors = colors;
	}
	
	public static KhopeLegend getLegend(Legend legend) {

		double[] thresholds = null;
		Color[] colors = null;
		
		switch(legend) {
		
		case GSEA_SALT:
			
			thresholds = new double[]{0,25,25.1,25.2,25,25.4,26,25.6,26,25.8,26,26,26,26,26,26,27,27,26.7,26.8,26.9,27,27.1,27.2,27.3,27.4,28,28,28,28,28,28,28.1,28.2,28.3,28.4,28.5,28.6,28.7,28.8,28.9,29,29.1,29.2,29.3,29.4,29.5,29.6,29.7,29.8,29.9,30,30.1,30.2,30.3,30.4,30.5,30.6,30.7,30.8,30.9,31,31.1,31.2,31.3,31.4,31.5,31.6,31.7,31.8,31.9,32,32.1,32.2,32.3,32.4,32.5,32.6,32.7,32.8,32.9,33,33.1,33.2,33.3,33.4,33.5,33.6,33.7,33.8,33.9,34,34.1,34.2,34.3,34.4,34.5,34.6,34.7,34.8,34.9,40};
			colors = new Color[]{
				new Color(79,0,95),
				new Color(79,0,95),
				new Color(82,0,104),
				new Color(84,0,118),
				new Color(88,0,132),
				new Color(87,0,141),
				new Color(87,0,150),
				new Color(84,0,159),
				new Color(84,0,168),
				new Color(78,0,182),
				new Color(76,0,191),
				new Color(68,0,204),
				new Color(60,0,214),
				new Color(55,0,223),
				new Color(43,0,236),
				new Color(36,0,245),
				new Color(25,0,255),
				new Color(12,0,255),
				new Color(0,0,255),
				new Color(0,16,255),
				new Color(0,25,255),
				new Color(0,38,255),
				new Color(0,46,255),
				new Color(0,63,255),
				new Color(0,72,255),
				new Color(0,89,255),
				new Color(0,97,255),
				new Color(0,110,255),
				new Color(0,119,255),
				new Color(0,135,255),
				new Color(0,152,255),
				new Color(0,161,255),
				new Color(0,174,255),
				new Color(0,182,255),
				new Color(0,195,255),
				new Color(0,216,255),
				new Color(0,225,255),
				new Color(0,233,255),
				new Color(0,246,255),
				new Color(0,255,255),
				new Color(0,255,242),
				new Color(0,255,225),
				new Color(0,255,212),
				new Color(0,255,199),
				new Color(0,255,191),
				new Color(0,255,178),
				new Color(0,255,170),
				new Color(0,255,152),
				new Color(0,255,135),
				new Color(0,255,127),
				new Color(0,255,114),
				new Color(0,255,106),
				new Color(0,255,89),
				new Color(0,255,80),
				new Color(0,255,63),
				new Color(0,255,55),
				new Color(0,255,42),
				new Color(0,255,25),
				new Color(0,255,16),
				new Color(0,255,8),
				new Color(8,255,0),
				new Color(21,255,0),
				new Color(29,255,0),
				new Color(46,255,0),
				new Color(55,255,0),
				new Color(67,255,0),
				new Color(76,255,0),
				new Color(93,255,0),
				new Color(110,255,0),
				new Color(119,255,0),
				new Color(131,255,0),
				new Color(140,255,0),
				new Color(153,255,0),
				new Color(165,255,0),
				new Color(182,255,0),
				new Color(191,255,0),
				new Color(203,255,0),
				new Color(212,255,0),
				new Color(225,255,0),
				new Color(246,255,0),
				new Color(255,255,0),
				new Color(255,242,0),
				new Color(255,233,0),
				new Color(255,221,0),
				new Color(255,212,0),
				new Color(255,191,0),
				new Color(255,178,0),
				new Color(255,170,0),
				new Color(255,161,0),
				new Color(255,148,0),
				new Color(255,131,0),
				new Color(255,119,0),
				new Color(255,106,0),
				new Color(255,97,0),
				new Color(255,85,0),
				new Color(255,76,0),
				new Color(255,59,0),
				new Color(255,51,0),
				new Color(255,34,0),
				new Color(255,21,0),
				new Color(255,12,0),
				new Color(255,12,0)
			};
			
			break;
			
		case GSEA_TEMP:
			
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
			
		case GSEA_TW_DIFF:
			
			thresholds = new double[]{-10,-5,-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,10};
			colors = new Color[]{
				new Color(31,31,255),
				new Color(31,31,255),
				new Color(52,52,255),
				new Color(71,71,254),
				new Color(92,92,255),
				new Color(113,113,254),
				new Color(134,134,250),
				new Color(152,152,251),
				new Color(174,174,253),
				new Color(195,195,253),
				new Color(215,215,252),
				new Color(255,220,220),
				new Color(252,204,204),
				new Color(255,184,184),
				new Color(254,163,163),
				new Color(255,144,144),
				new Color(255,122,122),
				new Color(255,102,102),
				new Color(255,79,79),
				new Color(255,63,63),
				new Color(255,41,41),
				new Color(255,41,41)
			};
			
			break;
		
		case LDPS_VIS:
			
			thresholds = new double[]{0,100,200,400,600,800,1000,1500,2000,2500,3000,4000,5000,6000,7000,8000,10000,12000,14000,16000,18000,20000};
			colors = new Color[]{
						
				new Color(255,0,255),
				new Color(191,0,0),
				new Color(213,0,0),
				new Color(238,11,11),
				new Color(246,62,62),
				new Color(250,133,133),
				new Color(204,170,0),
				new Color(224,185,0),
				new Color(249,205,0),
				new Color(255,220,31),
				new Color(255,234,110),
				new Color(128,0,0),
				new Color(0,164,0),
				new Color(0,213,0),
				new Color(30,243,30),
				new Color(105,252,105),
				new Color(0,119,179),
				new Color(0,141,222),
				new Color(7,171,255),
				new Color(62,193,255),
				new Color(135,217,255),
				new Color(254,254,254)
				
			};
			
			break;	
			
		case GDPS_VIS:
			
			thresholds = new double[]{0,100,200,400,600,800,1000,1500,2000,2500,3000,4000,5000,6000,7000,8000,10000,12000,14000,16000,18000,20000};
			colors = new Color[]{
						
				new Color(255,0,255),
				new Color(191,0,0),
				new Color(213,0,0),
				new Color(238,11,11),
				new Color(246,62,62),
				new Color(250,133,133),
				new Color(204,170,0),
				new Color(224,185,0),
				new Color(249,205,0),
				new Color(255,220,31),
				new Color(255,234,110),
				new Color(128,0,0),
				new Color(0,164,0),
				new Color(0,213,0),
				new Color(30,243,30),
				new Color(105,252,105),
				new Color(0,119,179),
				new Color(0,141,222),
				new Color(7,171,255),
				new Color(62,193,255),
				new Color(135,217,255),
				new Color(254,254,254)
				
			};
			
			break;		
			
		case GLOSEA5_TEMP:
			
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
			
		case GLOSEA5_HEAT:
			
			thresholds = new double[]{0,3*10000000,6*10000000,9*10000000,12*10000000,15*10000000,18*10000000,20*10000000,23*10000000,26*10000000,29*10000000,32*10000000,35*10000000,38*10000000,39*10000000,40*10000000,43*10000000,46*10000000,49*10000000,52*10000000,55*10000000,58*10000000,60*10000000,63*10000000,66*10000000,69*10000000,72*10000000,75*10000000,78*10000000,79*10000000,80*10000000,83*10000000,86*10000000,89*10000000,92*10000000,95*10000000,98*10000000,100*10000000,103*10000000,106*10000000,109*10000000,112*10000000,115*10000000,118*10000000,119*10000000,120*10000000,123*10000000,126*10000000,129*10000000,132*10000000,135*10000000,138*10000000,140*10000000,143*10000000,146*10000000,149*10000000,152*10000000,155*10000000,158*10000000,159*10000000,160*10000000,163*10000000,166*10000000,169*10000000};
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
			
		case GLOSEA5_MIX:
			
			thresholds = new double[]{0,3,6,9,12,15,18,20,23,26,29,32,35,38,39,40,43,46,49,52,55,58,60,63,66,69,72,75,78,79,80,83,86,89,92,95,98,100,103,106,109,112,115,118,119,120,123,126,129,132,135,138,140,143,146,149,152,155,158,159,160,163,166,169};
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
			
		case GLOSEA5_VELOCITY:
			
			thresholds = new double[]{0,0.066,0.132,0.2,0.266,0.332,0.4,0.466,0.532,0.6,0.665,0.732,0.8,0.866,0.932,1,1.066,1.132,1.2,1.266,1.331,1.4,1.466,1.532,1.6,1.666,1.732,1.8,1.866,1.932};
			colors = new Color[]{
				new Color(229,255,255),
				new Color(211,255,255),
				new Color(193,255,255),
				new Color(175,243,255),
				new Color(164,225,255),
				new Color(157,207,255),
				new Color(255,255,180),
				new Color(255,255,160),
				new Color(255,255,140),
				new Color(255,255,120),
				new Color(255,255,100),
				new Color(255,255,50),
				new Color(255,255,0),
				new Color(255,202,108),
				new Color(255,184,90),
				new Color(255,166,72),
				new Color(255,148,54),
				new Color(255,130,36),
				new Color(255,112,18),
				new Color(255,110,0),
				new Color(237,94,0),
				new Color(255,74,74),
				new Color(255,54,54),
				new Color(255,19,19),
				new Color(239,1,1),
				new Color(213,0,0),
				new Color(199,0,0),
				new Color(165,0,0),
				new Color(8,0,8),
				new Color(121,17,5)
			};
			
			break;	
			
		case HYDR_SALT:
			
			thresholds = new double[]{0,25,25.1,25.2,25,25.4,26,25.6,26,25.8,26,26,26,26,26,26,27,27,26.7,26.8,26.9,27,27.1,27.2,27.3,27.4,28,28,28,28,28,28,28.1,28.2,28.3,28.4,28.5,28.6,28.7,28.8,28.9,29,29.1,29.2,29.3,29.4,29.5,29.6,29.7,29.8,29.9,30,30.1,30.2,30.3,30.4,30.5,30.6,30.7,30.8,30.9,31,31.1,31.2,31.3,31.4,31.5,31.6,31.7,31.8,31.9,32,32.1,32.2,32.3,32.4,32.5,32.6,32.7,32.8,32.9,33,33.1,33.2,33.3,33.4,33.5,33.6,33.7,33.8,33.9,34,34.1,34.2,34.3,34.4,34.5,34.6,34.7,34.8,34.9,40};
			colors = new Color[]{
				new Color(79,0,95),
				new Color(79,0,95),
				new Color(82,0,104),
				new Color(84,0,118),
				new Color(88,0,132),
				new Color(87,0,141),
				new Color(87,0,150),
				new Color(84,0,159),
				new Color(84,0,168),
				new Color(78,0,182),
				new Color(76,0,191),
				new Color(68,0,204),
				new Color(60,0,214),
				new Color(55,0,223),
				new Color(43,0,236),
				new Color(36,0,245),
				new Color(25,0,255),
				new Color(12,0,255),
				new Color(0,0,255),
				new Color(0,16,255),
				new Color(0,25,255),
				new Color(0,38,255),
				new Color(0,46,255),
				new Color(0,63,255),
				new Color(0,72,255),
				new Color(0,89,255),
				new Color(0,97,255),
				new Color(0,110,255),
				new Color(0,119,255),
				new Color(0,135,255),
				new Color(0,152,255),
				new Color(0,161,255),
				new Color(0,174,255),
				new Color(0,182,255),
				new Color(0,195,255),
				new Color(0,216,255),
				new Color(0,225,255),
				new Color(0,233,255),
				new Color(0,246,255),
				new Color(0,255,255),
				new Color(0,255,242),
				new Color(0,255,225),
				new Color(0,255,212),
				new Color(0,255,199),
				new Color(0,255,191),
				new Color(0,255,178),
				new Color(0,255,170),
				new Color(0,255,152),
				new Color(0,255,135),
				new Color(0,255,127),
				new Color(0,255,114),
				new Color(0,255,106),
				new Color(0,255,89),
				new Color(0,255,80),
				new Color(0,255,63),
				new Color(0,255,55),
				new Color(0,255,42),
				new Color(0,255,25),
				new Color(0,255,16),
				new Color(0,255,8),
				new Color(8,255,0),
				new Color(21,255,0),
				new Color(29,255,0),
				new Color(46,255,0),
				new Color(55,255,0),
				new Color(67,255,0),
				new Color(76,255,0),
				new Color(93,255,0),
				new Color(110,255,0),
				new Color(119,255,0),
				new Color(131,255,0),
				new Color(140,255,0),
				new Color(153,255,0),
				new Color(165,255,0),
				new Color(182,255,0),
				new Color(191,255,0),
				new Color(203,255,0),
				new Color(212,255,0),
				new Color(225,255,0),
				new Color(246,255,0),
				new Color(255,255,0),
				new Color(255,242,0),
				new Color(255,233,0),
				new Color(255,221,0),
				new Color(255,212,0),
				new Color(255,191,0),
				new Color(255,178,0),
				new Color(255,170,0),
				new Color(255,161,0),
				new Color(255,148,0),
				new Color(255,131,0),
				new Color(255,119,0),
				new Color(255,106,0),
				new Color(255,97,0),
				new Color(255,85,0),
				new Color(255,76,0),
				new Color(255,59,0),
				new Color(255,51,0),
				new Color(255,34,0),
				new Color(255,21,0),
				new Color(255,12,0),
				new Color(255,12,0)
			};
			
			break;
			
		case HYDR_TEMP:
			
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
			
		case HYCOM_SALT:
			
			thresholds = new double[]{0,25,25.1,25.2,25,25.4,26,25.6,26,25.8,26,26,26,26,26,26,27,27,26.7,26.8,26.9,27,27.1,27.2,27.3,27.4,28,28,28,28,28,28,28.1,28.2,28.3,28.4,28.5,28.6,28.7,28.8,28.9,29,29.1,29.2,29.3,29.4,29.5,29.6,29.7,29.8,29.9,30,30.1,30.2,30.3,30.4,30.5,30.6,30.7,30.8,30.9,31,31.1,31.2,31.3,31.4,31.5,31.6,31.7,31.8,31.9,32,32.1,32.2,32.3,32.4,32.5,32.6,32.7,32.8,32.9,33,33.1,33.2,33.3,33.4,33.5,33.6,33.7,33.8,33.9,34,34.1,34.2,34.3,34.4,34.5,34.6,34.7,34.8,34.9,40};
			colors = new Color[]{
				new Color(79,0,95),
				new Color(79,0,95),
				new Color(82,0,104),
				new Color(84,0,118),
				new Color(88,0,132),
				new Color(87,0,141),
				new Color(87,0,150),
				new Color(84,0,159),
				new Color(84,0,168),
				new Color(78,0,182),
				new Color(76,0,191),
				new Color(68,0,204),
				new Color(60,0,214),
				new Color(55,0,223),
				new Color(43,0,236),
				new Color(36,0,245),
				new Color(25,0,255),
				new Color(12,0,255),
				new Color(0,0,255),
				new Color(0,16,255),
				new Color(0,25,255),
				new Color(0,38,255),
				new Color(0,46,255),
				new Color(0,63,255),
				new Color(0,72,255),
				new Color(0,89,255),
				new Color(0,97,255),
				new Color(0,110,255),
				new Color(0,119,255),
				new Color(0,135,255),
				new Color(0,152,255),
				new Color(0,161,255),
				new Color(0,174,255),
				new Color(0,182,255),
				new Color(0,195,255),
				new Color(0,216,255),
				new Color(0,225,255),
				new Color(0,233,255),
				new Color(0,246,255),
				new Color(0,255,255),
				new Color(0,255,242),
				new Color(0,255,225),
				new Color(0,255,212),
				new Color(0,255,199),
				new Color(0,255,191),
				new Color(0,255,178),
				new Color(0,255,170),
				new Color(0,255,152),
				new Color(0,255,135),
				new Color(0,255,127),
				new Color(0,255,114),
				new Color(0,255,106),
				new Color(0,255,89),
				new Color(0,255,80),
				new Color(0,255,63),
				new Color(0,255,55),
				new Color(0,255,42),
				new Color(0,255,25),
				new Color(0,255,16),
				new Color(0,255,8),
				new Color(8,255,0),
				new Color(21,255,0),
				new Color(29,255,0),
				new Color(46,255,0),
				new Color(55,255,0),
				new Color(67,255,0),
				new Color(76,255,0),
				new Color(93,255,0),
				new Color(110,255,0),
				new Color(119,255,0),
				new Color(131,255,0),
				new Color(140,255,0),
				new Color(153,255,0),
				new Color(165,255,0),
				new Color(182,255,0),
				new Color(191,255,0),
				new Color(203,255,0),
				new Color(212,255,0),
				new Color(225,255,0),
				new Color(246,255,0),
				new Color(255,255,0),
				new Color(255,242,0),
				new Color(255,233,0),
				new Color(255,221,0),
				new Color(255,212,0),
				new Color(255,191,0),
				new Color(255,178,0),
				new Color(255,170,0),
				new Color(255,161,0),
				new Color(255,148,0),
				new Color(255,131,0),
				new Color(255,119,0),
				new Color(255,106,0),
				new Color(255,97,0),
				new Color(255,85,0),
				new Color(255,76,0),
				new Color(255,59,0),
				new Color(255,51,0),
				new Color(255,34,0),
				new Color(255,21,0),
				new Color(255,12,0),
				new Color(255,12,0)
			};
			
			break;
			
		case HYCOM_TEMP:
			
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
		
		}
		
		return new KhopeLegend(thresholds, colors);
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
