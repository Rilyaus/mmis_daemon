package mmis.daemon.util.legend;

import java.awt.Color;

public class KimLegend {
	
	public enum Legend {
		
		GDPS_WATER_TEMP,	// 수온
		GDPS_ASTD, // 해기차
		GDPS_WSD,	// 풍속
		GDPS_MSL,	// 해면기압
		RTSM_APR,
		RTSM_SSH,	// 해일고
		RTSM_SURG_HEIGHT,	// 해일고 범례
		GWW3_WIND,	// 풍향
		GWW3_SIG_WH,	// 유의파고
		GWW3_WP,	// 파주기
		RWW3_WIND,
		RWW3_SIG_WH,
		RWW3_WP,
		CWW3_WIND,
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
			
		case GDPS_WSD:
			
			thresholds = new double[]{0,0.2,0.4,0.6,0.8,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,8,9,10,15,20,25,30,35,40,45,50,55};
			colors = new Color[]{
				Color.decode("#ffea6e"),
				Color.decode("#ffdc1f"),
				Color.decode("#f9cd00"),
				Color.decode("#e0b900"),
				Color.decode("#ccaa00"),
				Color.decode("#69fc69"),
				Color.decode("#1ef31e"),
				Color.decode("#00d500"),
				Color.decode("#00a400"),
				Color.decode("#008000"),
				Color.decode("#87d9ff"),
				Color.decode("#3ec1ff"),
				Color.decode("#07abff"),
				Color.decode("#008dde"),
				Color.decode("#0077b3"),
				Color.decode("#b3b4de"),
				Color.decode("#8081c7"),
				Color.decode("#4c4eb1"),
				Color.decode("#1f219d"),
				Color.decode("#000390"),
				Color.decode("#da87ff"),
				Color.decode("#c23eff"),
				Color.decode("#ad07ff"),
				Color.decode("#9200e4"),
				Color.decode("#7f00bf"),
				Color.decode("#fa8585"),
				Color.decode("#f63e3e"),
				Color.decode("#ee0b0b"),
				Color.decode("#d50000"),
				Color.decode("#bf0000")
			};
			
			break;	
			
		case GDPS_ASTD:
			// 10.23
			thresholds = new double[]{-16,-15,-14,-13,-12,-11,-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
			colors = new Color[]{
				Color.decode("#000390"),
				Color.decode("#1f219d"),
				Color.decode("#4c4eb1"),
				Color.decode("#8081c7"),
				Color.decode("#b3b4de"),
				Color.decode("#0077b3"),
				Color.decode("#008dde"),
				Color.decode("#07abff"),
				Color.decode("#3ec1ff"),
				Color.decode("#87d9ff"),
				Color.decode("#008000"),
				Color.decode("#00a400"),
				Color.decode("#00d500"),
				Color.decode("#1ef31e"),
				Color.decode("#69fc69"),
				Color.decode("#e6e6e6"),
				Color.decode("#ffea6e"),
				Color.decode("#ffdc1f"),
				Color.decode("#f9cd00"),
				Color.decode("#e0b900"),
				Color.decode("#ccaa00"),
				Color.decode("#fa8585"),
				Color.decode("#f63e3e"),
				Color.decode("#ee0b0b"),
				Color.decode("#d50000"),
				Color.decode("#bf0000"),
				Color.decode("#da87ff"),
				Color.decode("#c23eff"),
				Color.decode("#ad07ff"),
				Color.decode("#9200e4"),
				Color.decode("#7f00bf")
			};
			
			break;
			
		case GDPS_MSL:
			// 10.23
			thresholds = new double[]{101000,101050,101100,101150,101200,101250,101300,101350,101400,101450,101500,101550,101600,101650,101700,101750,101800,101850,101900,101950,102000,102050,102100,102150,102200,102250,102300,102350,102400,102450,102500,102550,102600,102650,102700,102750,102800,102850,102900,102950,103000,103050,103100,103150,103200,103250,103300,103350,103400,103450,103500,103550,103600,103650,103700,103750,103800,103850,103900,103950,};
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
			
		case RTSM_APR:
			
			thresholds = new double[]{99500,99610,99720,99830,99940,101050,100160,100270,100380,100490,100600,100710,100820,100930,101040,101150,101260,101370,101480,101590,101700,102000};
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
			
		case RTSM_SSH:
		case RTSM_SURG_HEIGHT:
			// 10.23
			thresholds = new double[]{-1,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1,0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
			colors = new Color[]{
				Color.decode("#281b72"),
				Color.decode("#1b12a1"),
				Color.decode("#0d09d0"),
				Color.decode("#0000ff"),
				Color.decode("#0055aa"),
				Color.decode("#00aa55"),
				Color.decode("#00ff00"),
				Color.decode("#55ff55"),
				Color.decode("#aaffaa"),
				Color.decode("#ffffff"),
				Color.decode("#ffffff"),
				Color.decode("#fbfdb8"),
				Color.decode("#f7fb70"),
				Color.decode("#f3f929"),
				Color.decode("#f5b327"),
				Color.decode("#f66e24"),
				Color.decode("#f82822"),
				Color.decode("#df232d"),
				Color.decode("#c71d38"),
				Color.decode("#ae1843")
			};
			
			break;	
			
		case GWW3_WIND:
		case RWW3_WIND:
		case CWW3_WIND:
			
			thresholds = new double[]{0,15,30,45,60,75,90,105,120,135,150,165,180,195,210,225,240,255,270,285,300,360};
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
			
		case CWW3_WP:
		case RWW3_WP:
		case GWW3_WP:
          thresholds = new double[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
          colors = new Color[]{
						Color.decode("#fbfbfd"),
						Color.decode("#d7effd"),
						Color.decode("#b2d8fe"),
						Color.decode("#7cbdfe"),
						Color.decode("#66a6ff"),
						Color.decode("#4a87f6"),
						Color.decode("#9aff81"),
						Color.decode("#67e54e"),
						Color.decode("#ffff82"),
						Color.decode("#d9d933"),
						Color.decode("#ffd296"),
						Color.decode("#ffb478"),
						Color.decode("#ff965a"),
						Color.decode("#ff6432"),
						Color.decode("#ff321e"),
						Color.decode("#ff99ff"),
						Color.decode("#d900d9"),
						Color.decode("#b30000"),
						Color.decode("#660000"),
						Color.decode("#0c6415")
          };
          
          break;
		case CWW3_SIG_WH:
		case RWW3_SIG_WH:
		case GWW3_SIG_WH:
		  thresholds = new double[] {0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5};
		  colors = new Color[] {
				Color.decode("#fcfdfe"),
				Color.decode("#b3d9ff"),
				Color.decode("#66a6ff"),
				Color.decode("#4c8cff"),
				Color.decode("#9aff81"),
				Color.decode("#67e54e"),
				Color.decode("#ffff00"),
				Color.decode("#e5e550"),
				Color.decode("#ffb478"),
				Color.decode("#ff6432"),
				Color.decode("#e51900"),
				Color.decode("#990000"),
				Color.decode("#e599ff"),
				Color.decode("#cc4cff"),
				Color.decode("#b319ff"),
				Color.decode("#00e500"),
				Color.decode("#0094ff"),
				Color.decode("#0066ff"),
				Color.decode("#6600ff"),
				Color.decode("#0a6711")
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
