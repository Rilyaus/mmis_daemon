package mmis.daemon.util.legend;

import java.awt.Color;

public class KhopeLegend {
	
	public enum Legend {
		GSEA_SALT, 	// 염분
		GSEA_TEMP,	// 수온
		GSEA_TW_DIFF, //수온 편차
		LDPS_VIS,
		GDPS_VIS,
		GLOSEA5_HEAT, // 열용량
		GLOSEA5_TEMP,	// 수온
		GLOSEA5_MIX,	// 혼합층
		GLOSEA5_VELOCITY,	//
		HYDR_SALT,	// 염분
		HYDR_TEMP,	// 수온
		HYCOM_SALT,	// 염분
		HYCOM_TEMP	// 수온
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
		case HYDR_SALT:
		case HYCOM_SALT:
			// 10.23
			thresholds = new double[]{25,25.1,25.2,25.3,25.4,25.5,25.6,25.7,25.8,25.9,26,26.1,26.2,26.3,26.4,26.5,26.6,26.7,26.8,26.9,27,27.1,27.2,27.3,27.4,27.5,27.6,27.7,27.8,27.9,28,28.1,28.2,28.3,28.4,28.5,28.6,28.7,28.8,28.9,29,29.1,29.2,29.3,29.4,29.5,29.6,29.7,29.8,29.9,30,30.1,30.2,30.3,30.4,30.5,30.6,30.7,30.8,30.9,31,31.1,31.2,31.3,31.4,31.5,31.6,31.7,31.8,31.9,32,32.1,32.2,32.3,32.4,32.5,32.6,32.7,32.8,32.9,33,33.1,33.2,33.3,33.4,33.5,33.6,33.7,33.8,33.9,34,34.1,34.2,34.3,34.4,34.5,34.6,34.7,34.8,34.9};
			colors = new Color[]{
				Color.decode("#4F005F"),
				Color.decode("#520068"),
				Color.decode("#540076"),
				Color.decode("#580084"),
				Color.decode("#57008D"),
				Color.decode("#570096"),
				Color.decode("#54009F"),
				Color.decode("#5400A8"),
				Color.decode("#4E00B6"),
				Color.decode("#4C00BF"),
				Color.decode("#4400CC"),
				Color.decode("#3C00D6"),
				Color.decode("#3700DF"),
				Color.decode("#2B00EC"),
				Color.decode("#2400F5"),
				Color.decode("#1900FF"),
				Color.decode("#0C00FF"),
				Color.decode("#0000FF"),
				Color.decode("#0010FF"),
				Color.decode("#0019FF"),
				Color.decode("#0026FF"),
				Color.decode("#002EFF"),
				Color.decode("#003FFF"),
				Color.decode("#0048FF"),
				Color.decode("#0059FF"),
				Color.decode("#0061FF"),
				Color.decode("#006EFF"),
				Color.decode("#0077FF"),
				Color.decode("#0087FF"),
				Color.decode("#0098FF"),
				Color.decode("#00A1FF"),
				Color.decode("#00AEFF"),
				Color.decode("#00B6FF"),
				Color.decode("#00C3FF"),
				Color.decode("#00D8FF"),
				Color.decode("#00E1FF"),
				Color.decode("#00E9FF"),
				Color.decode("#00F6FF"),
				Color.decode("#00FFFF"),
				Color.decode("#00FFF2"),
				Color.decode("#00FFE1"),
				Color.decode("#00FFD4"),
				Color.decode("#00FFC7"),
				Color.decode("#00FFBF"),
				Color.decode("#00FFB2"),
				Color.decode("#00FFAA"),
				Color.decode("#00FF98"),
				Color.decode("#00FF87"),
				Color.decode("#00FF7F"),
				Color.decode("#00FF72"),
				Color.decode("#00FF6A"),
				Color.decode("#00FF59"),
				Color.decode("#00FF50"),
				Color.decode("#00FF3F"),
				Color.decode("#00FF37"),
				Color.decode("#00FF2A"),
				Color.decode("#00FF19"),
				Color.decode("#00FF10"),
				Color.decode("#00FF08"),
				Color.decode("#08FF00"),
				Color.decode("#15FF00"),
				Color.decode("#1DFF00"),
				Color.decode("#2EFF00"),
				Color.decode("#37FF00"),
				Color.decode("#43FF00"),
				Color.decode("#4CFF00"),
				Color.decode("#5DFF00"),
				Color.decode("#6EFF00"),
				Color.decode("#77FF00"),
				Color.decode("#83FF00"),
				Color.decode("#8CFF00"),
				Color.decode("#99FF00"),
				Color.decode("#A5FF00"),
				Color.decode("#B6FF00"),
				Color.decode("#BFFF00"),
				Color.decode("#CBFF00"),
				Color.decode("#D4FF00"),
				Color.decode("#E1FF00"),
				Color.decode("#F6FF00"),
				Color.decode("#FFFF00"),
				Color.decode("#FFF200"),
				Color.decode("#FFE900"),
				Color.decode("#FFDD00"),
				Color.decode("#FFD400"),
				Color.decode("#FFBF00"),
				Color.decode("#FFB200"),
				Color.decode("#FFAA00"),
				Color.decode("#FFA100"),
				Color.decode("#FF9400"),
				Color.decode("#FF8300"),
				Color.decode("#FF7700"),
				Color.decode("#FF6A00"),
				Color.decode("#FF6100"),
				Color.decode("#FF5500"),
				Color.decode("#FF4C00"),
				Color.decode("#FF3B00"),
				Color.decode("#FF3300"),
				Color.decode("#FF2200"),
				Color.decode("#FF1500"),
				Color.decode("#FF0C00")
			};
			
			break;
			
		case GSEA_TEMP:
		case GLOSEA5_TEMP:
		case HYDR_TEMP:
		case HYCOM_TEMP:
			// 10.23
			thresholds = new double[]{-5,-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5,24,24.5};
			colors = new Color[]{
				Color.decode("#e5acff"),
				Color.decode("#da87ff"),
				Color.decode("#cd61ff"),
				Color.decode("#c23eff"),
				Color.decode("#b71fff"),
				Color.decode("#ad07ff"),
				Color.decode("#a000f7"),
				Color.decode("#9200e4"),
				Color.decode("#8700ce"),
				Color.decode("#7f00bf"),
				Color.decode("#cbcce8"),
				Color.decode("#b3b4de"),
				Color.decode("#9a9bd3"),
				Color.decode("#8081c7"),
				Color.decode("#6567bc"),
				Color.decode("#4c4eb1"),
				Color.decode("#3436a7"),
				Color.decode("#1f219d"),
				Color.decode("#0d1096"),
				Color.decode("#000390"),
				Color.decode("#ace5ff"),
				Color.decode("#87d9ff"),
				Color.decode("#61cdff"),
				Color.decode("#3ec1ff"),
				Color.decode("#1fb5ff"),
				Color.decode("#07abff"),
				Color.decode("#009df6"),
				Color.decode("#008dde"),
				Color.decode("#0080c4"),
				Color.decode("#0077b3"),
				Color.decode("#96fe96"),
				Color.decode("#69fc69"),
				Color.decode("#40f940"),
				Color.decode("#1ef31e"),
				Color.decode("#08e908"),
				Color.decode("#00d500"),
				Color.decode("#00bd00"),
				Color.decode("#00a400"),
				Color.decode("#008e00"),
				Color.decode("#008000"),
				Color.decode("#fff09a"),
				Color.decode("#ffea6e"),
				Color.decode("#ffe343"),
				Color.decode("#ffdc1f"),
				Color.decode("#ffd604"),
				Color.decode("#f9cd00"),
				Color.decode("#edc300"),
				Color.decode("#e0b900"),
				Color.decode("#d4b000"),
				Color.decode("#ccaa00"),
				Color.decode("#fcabab"),
				Color.decode("#fa8585"),
				Color.decode("#f86060"),
				Color.decode("#f63e3e"),
				Color.decode("#f32121"),
				Color.decode("#ee0b0b"),
				Color.decode("#e30000"),
				Color.decode("#d50000"),
				Color.decode("#c80000"),
				Color.decode("#bf0000")
			};
			
			break;	
			
		case GSEA_TW_DIFF:
			// 10.23
			thresholds = new double[]{-5,-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,0.5,1,1.5,2,2.5,3,3.5,4,4.5};
			colors = new Color[]{
				Color.decode("#1f1fff"),
				Color.decode("#3434ff"),
				Color.decode("#4747fe"),
				Color.decode("#5c5cff"),
				Color.decode("#7171fe"),
				Color.decode("#8686fa"),
				Color.decode("#9898fb"),
				Color.decode("#aeaefd"),
				Color.decode("#c3c3fd"),
				Color.decode("#d7d7fc"),
				Color.decode("#ffdcdc"),
				Color.decode("#fccccc"),
				Color.decode("#ffb8b8"),
				Color.decode("#fea3a3"),
				Color.decode("#ff9090"),
				Color.decode("#ff7a7a"),
				Color.decode("#ff6666"),
				Color.decode("#ff4f4f"),
				Color.decode("#ff3f3f"),
				Color.decode("#ff2929")
			};
			
			break;
		
		case LDPS_VIS:
		case GDPS_VIS:
			// 10.23
			thresholds = new double[]{0,100,200,400,600,800,1000,1500,2000,2500,3000,4000,5000,6000,7000,8000,10000,12000,14000,16000,18000,20000};
			colors = new Color[]{
				Color.decode("#ff00ff"),
				Color.decode("#bf0000"),
				Color.decode("#d50000"),
				Color.decode("#ee0b0b"),
				Color.decode("#f63e3e"),
				Color.decode("#fa8585"),
				Color.decode("#ccaa00"),
				Color.decode("#e0b900"),
				Color.decode("#f9cd00"),
				Color.decode("#ffdc1f"),
				Color.decode("#ffea6e"),
				Color.decode("#008000"),
				Color.decode("#00a400"),
				Color.decode("#00d500"),
				Color.decode("#1ef31e"),
				Color.decode("#69fc69"),
				Color.decode("#0077b3"),
				Color.decode("#008dde"),
				Color.decode("#07abff"),
				Color.decode("#3ec1ff"),
				Color.decode("#87d9ff"),
				Color.decode("#fefefe")
			};
			
			break;
			
		case GLOSEA5_HEAT:
			
			thresholds = new double[]{5.0E7,1.2380952380952382E9,2.4261904761904764E9,3.6142857142857146E9,4.802380952380953E9,5.990476190476191E9,7.178571428571429E9,8.366666666666668E9,9.554761904761906E9,1.0742857142857143E10,1.1930952380952381E10,1.311904761904762E10,1.4307142857142859E10,1.5495238095238096E10,1.6683333333333336E10,1.7871428571428574E10,1.905952380952381E10,2.024761904761905E10,2.1435714285714287E10,2.2623809523809525E10,2.3811904761904762E10,2.5000000000000004E10};
			colors = new Color[]{
						
				new Color(51,51,51),
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
				new Color(0,189,0),
				new Color(30,243,30),
				new Color(204,170,0),
				new Color(255,214,4),
				new Color(191,0,0),
				new Color(213,0,0),
				new Color(238,11,11),
				new Color(246,62,62),
				new Color(250,133,133),
				new Color(238,238,238)
				
			};
			
			break;	
			
		case GLOSEA5_MIX:
			// 10.23
			thresholds = new double[]{0,3,6,9,12,15,18,20,23,26,29,32,35,38,39,40,43,46,49,52,55,58,60,63,66,69,72,75,78,79,80,83,86,89,92,95,98,100,103,106,109,112,115,118,119,120,123,126,129,132,135,138,140,143,146,149,152,155,158,159,160,163,166,169};
			colors = new Color[]{
				Color.decode("#00008F"),
				Color.decode("#00009F"),
				Color.decode("#0000AF"),
				Color.decode("#0000BF"),
				Color.decode("#0000CF"),
				Color.decode("#0000DF"),
				Color.decode("#0000EF"),
				Color.decode("#0000FF"),
				Color.decode("#000FFF"),
				Color.decode("#001FFF"),
				Color.decode("#002FFF"),
				Color.decode("#003FFF"),
				Color.decode("#004FFF"),
				Color.decode("#005FFF"),
				Color.decode("#006FFF"),
				Color.decode("#007FFF"),
				Color.decode("#008FFF"),
				Color.decode("#009FFF"),
				Color.decode("#00AFFF"),
				Color.decode("#00BFFF"),
				Color.decode("#00CFFF"),
				Color.decode("#00DFFF"),
				Color.decode("#00EFFF"),
				Color.decode("#00FFFF"),
				Color.decode("#0FFFEF"),
				Color.decode("#1FFFDF"),
				Color.decode("#2FFFCF"),
				Color.decode("#3FFFBF"),
				Color.decode("#4FFFAF"),
				Color.decode("#5FFF9F"),
				Color.decode("#6FFF8F"),
				Color.decode("#7FFF7F"),
				Color.decode("#8FFF6F"),
				Color.decode("#9FFF5F"),
				Color.decode("#AFFF4F"),
				Color.decode("#BFFF3F"),
				Color.decode("#CFFF2F"),
				Color.decode("#DFFF1F"),
				Color.decode("#EFFF0F"),
				Color.decode("#FFFF00"),
				Color.decode("#FFEF00"),
				Color.decode("#FFDF00"),
				Color.decode("#FFCF00"),
				Color.decode("#FFBF00"),
				Color.decode("#FFAF00"),
				Color.decode("#FF9F00"),
				Color.decode("#FF8F00"),
				Color.decode("#FF7F00"),
				Color.decode("#FF6F00"),
				Color.decode("#FF5F00"),
				Color.decode("#FF4F00"),
				Color.decode("#FF3F00"),
				Color.decode("#FF2F00"),
				Color.decode("#FF1F00"),
				Color.decode("#FF0F00"),
				Color.decode("#FF0000"),
				Color.decode("#EF0000"),
				Color.decode("#DF0000"),
				Color.decode("#CF0000"),
				Color.decode("#BF0000"),
				Color.decode("#AF0000"),
				Color.decode("#9F0000"),
				Color.decode("#8F0000"),
				Color.decode("#7F0000")
			};
			
			break;		
			
		case GLOSEA5_VELOCITY:
			
			thresholds = new double[]{0.01,0.02,0.04,0.06,0.08,0.1,0.15,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1,1.2,1.4,1.6,1.8,2,2.5,3,3.5,4,5,6,7,8,9,10,11,};
			colors = new Color[]{
				new Color(238,238,238),
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
				new Color(0,3,144),
				new Color(218,135,255),
				new Color(194,62,255),
				new Color(173,7,255),
				new Color(146,0,228),
				new Color(127,0,191),
				new Color(250,133,133),
				new Color(246,62,62),
				new Color(238,11,11),
				new Color(213,0,0),
				new Color(191,0,0),
				new Color(51,51,51)
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
