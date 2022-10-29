package mmis.daemon.util.legend;

import java.awt.Color;

public class DfsLegend {
	
	public enum Legend {
		REH,
		WAV,
		POP,
		WSD,
		PCP,
		SNO,
		TMP,
		SKY,
		PTY
	}

	private double[] thresholds;
	private Color[] colors;
	
	public DfsLegend(double[] thresholds, Color[] colors) {
		
		this.thresholds = thresholds;
		this.colors = colors;
	}
	
	public static DfsLegend getLegend(Legend legend) {

		double[] thresholds = null;
		Color[] colors = null;
		
		switch(legend) {
		
		  case REH:
        // 10.23
				thresholds = new double[]{0,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95};
				colors = new Color[]{
					hex2Rgb("#fa8585"),
					hex2Rgb("#f63e3e"),
					hex2Rgb("#ee0b0b"),
					hex2Rgb("#d50000"),
					hex2Rgb("#bf0000"),
					hex2Rgb("#ffd604"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#00bd00"),
					hex2Rgb("#008000"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#000390")
				};
				
				break;
			
		  case WAV:
				// 10.23
				thresholds = new double[]{0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5};
				colors = new Color[]{
					hex2Rgb("#fcfdfe"),
					hex2Rgb("#b3d9ff"),
					hex2Rgb("#66a6ff"),
					hex2Rgb("#4c8cff"),
					hex2Rgb("#9aff81"),
					hex2Rgb("#67e54e"),
					hex2Rgb("#ffff00"),
					hex2Rgb("#e5e550"),
					hex2Rgb("#ffb478"),
					hex2Rgb("#ff6432"),
					hex2Rgb("#e51900"),
					hex2Rgb("#990000"),
					hex2Rgb("#e599ff"),
					hex2Rgb("#cc4cff"),
					hex2Rgb("#b319ff"),
					hex2Rgb("#00e500"),
					hex2Rgb("#0094ff"),
					hex2Rgb("#0066ff"),
					hex2Rgb("#6600ff"),
					hex2Rgb("#0a6711")
				};
				
				break;	
			
		  case POP:
				// 10.23
				thresholds = new double[]{0,4,8,12,16,20,24,28,32,36,40,44,48,52,56,60,64,68,72,76,80,84,88,92,96};
				colors = new Color[]{
					hex2Rgb("#ffea6e"),
					hex2Rgb("#ffdc1f"),
					hex2Rgb("#f9cd00"),
					hex2Rgb("#e0b900"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#69fc69"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#00d500"),
					hex2Rgb("#00a400"),
					hex2Rgb("#008000"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#000390"),
					hex2Rgb("#da87ff"),
					hex2Rgb("#c23eff"),
					hex2Rgb("#ad07ff"),
					hex2Rgb("#9200e4"),
					hex2Rgb("#7f00bf")
				};
				
				break;
			
  		case WSD:
				// 10.23
				thresholds = new double[]{0,0.2,0.4,0.6,0.8,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,8,9,10,15,20,25,30,35,40,45,50,55};
				colors = new Color[]{
					hex2Rgb("#ffea6e"),
					hex2Rgb("#ffdc1f"),
					hex2Rgb("#f9cd00"),
					hex2Rgb("#e0b900"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#69fc69"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#00d500"),
					hex2Rgb("#00a400"),
					hex2Rgb("#008000"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#000390"),
					hex2Rgb("#da87ff"),
					hex2Rgb("#c23eff"),
					hex2Rgb("#ad07ff"),
					hex2Rgb("#9200e4"),
					hex2Rgb("#7f00bf"),
					hex2Rgb("#fa8585"),
					hex2Rgb("#f63e3e"),
					hex2Rgb("#ee0b0b"),
					hex2Rgb("#d50000"),
					hex2Rgb("#bf0000")
				};
				
				break;  
			
			case PCP:
				// 10.23
				thresholds = new double[]{0,0.2,0.4,0.6,0.8,1,1.5,2,3,4,5,6,7,8,9,10,12,14,16,18,20,25,30,35,40,50,60,70,80,90};
				colors = new Color[]{
					hex2Rgb("#ffea6e"),
					hex2Rgb("#ffdc1f"),
					hex2Rgb("#f9cd00"),
					hex2Rgb("#e0b900"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#69fc69"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#00d500"),
					hex2Rgb("#00a400"),
					hex2Rgb("#008000"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#000390"),
					hex2Rgb("#da87ff"),
					hex2Rgb("#c23eff"),
					hex2Rgb("#ad07ff"),
					hex2Rgb("#9200e4"),
					hex2Rgb("#7f00bf"),
					hex2Rgb("#fa8585"),
					hex2Rgb("#f63e3e"),
					hex2Rgb("#ee0b0b"),
					hex2Rgb("#d50000"),
					hex2Rgb("#bf0000")
				};
				
				break;
			
			case SNO:
				// 10.23
				thresholds = new double[]{0,0.2,0.4,0.6,0.8,1,1.5,2,3,4,5,6,7,8,9,10,12,14,16,18,20,25,30,35,40,50,60,70,80,90};
				colors = new Color[]{
					hex2Rgb("#ffea6e"),
					hex2Rgb("#ffdc1f"),
					hex2Rgb("#f9cd00"),
					hex2Rgb("#e0b900"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#69fc69"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#00d500"),
					hex2Rgb("#00a400"),
					hex2Rgb("#008000"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#000390"),
					hex2Rgb("#da87ff"),
					hex2Rgb("#c23eff"),
					hex2Rgb("#ad07ff"),
					hex2Rgb("#9200e4"),
					hex2Rgb("#7f00bf"),
					hex2Rgb("#fa8585"),
					hex2Rgb("#f63e3e"),
					hex2Rgb("#ee0b0b"),
					hex2Rgb("#d50000"),
					hex2Rgb("#bf0000")
				};
				
				break;	
			
			case TMP:
          
				thresholds = new double[]{-5,-4.5,-4,-3.5,-3,-2.5,-2,-1.5,-1,-0.5,0,0.5,1,1.5,2,2.5,3,3.5,4,4.5,5,5.5,6,6.5,7,7.5,8,8.5,9,9.5,10,10.5,11,11.5,12,12.5,13,13.5,14,14.5,15,15.5,16,16.5,17,17.5,18,18.5,19,19.5,20,20.5,21,21.5,22,22.5,23,23.5,24,24.5};
				colors = new Color[]{
					hex2Rgb("#e5acff"),
					hex2Rgb("#da87ff"),
					hex2Rgb("#cd61ff"),
					hex2Rgb("#c23eff"),
					hex2Rgb("#b71fff"),
					hex2Rgb("#ad07ff"),
					hex2Rgb("#a000f7"),
					hex2Rgb("#9200e4"),
					hex2Rgb("#8700ce"),
					hex2Rgb("#7f00bf"),
					hex2Rgb("#cbcce8"),
					hex2Rgb("#b3b4de"),
					hex2Rgb("#9a9bd3"),
					hex2Rgb("#8081c7"),
					hex2Rgb("#6567bc"),
					hex2Rgb("#4c4eb1"),
					hex2Rgb("#3436a7"),
					hex2Rgb("#1f219d"),
					hex2Rgb("#0d1096"),
					hex2Rgb("#000390"),
					hex2Rgb("#ace5ff"),
					hex2Rgb("#87d9ff"),
					hex2Rgb("#61cdff"),
					hex2Rgb("#3ec1ff"),
					hex2Rgb("#1fb5ff"),
					hex2Rgb("#07abff"),
					hex2Rgb("#009df6"),
					hex2Rgb("#008dde"),
					hex2Rgb("#0080c4"),
					hex2Rgb("#0077b3"),
					hex2Rgb("#96fe96"),
					hex2Rgb("#69fc69"),
					hex2Rgb("#40f940"),
					hex2Rgb("#1ef31e"),
					hex2Rgb("#08e908"),
					hex2Rgb("#00d500"),
					hex2Rgb("#00bd00"),
					hex2Rgb("#00a400"),
					hex2Rgb("#008e00"),
					hex2Rgb("#008000"),
					hex2Rgb("#fff09a"),
					hex2Rgb("#ffea6e"),
					hex2Rgb("#ffe343"),
					hex2Rgb("#ffdc1f"),
					hex2Rgb("#ffd604"),
					hex2Rgb("#f9cd00"),
					hex2Rgb("#edc300"),
					hex2Rgb("#e0b900"),
					hex2Rgb("#d4b000"),
					hex2Rgb("#ccaa00"),
					hex2Rgb("#fcabab"),
					hex2Rgb("#fa8585"),
					hex2Rgb("#f86060"),
					hex2Rgb("#f63e3e"),
					hex2Rgb("#f32121"),
					hex2Rgb("#ee0b0b"),
					hex2Rgb("#e30000"),
					hex2Rgb("#d50000"),
					hex2Rgb("#c80000"),
					hex2Rgb("#bf0000")
				};
					
				break;
		
			case SKY:
			
				thresholds = new double[]{1,3,4};
				colors = new Color[]{
					hex2Rgb("#fafafa"),
					hex2Rgb("#6fa2bd"),
					hex2Rgb("#3e7a9a")
				};
			
				break;
			
			case PTY:
			
				thresholds = new double[]{1,2,3,4};
				colors = new Color[]{
					new Color(96,212,126),
					new Color(61,196,230),
					new Color(142,142,230),
					new Color(255,192,0)
				};
				
				break;			
			
			default:
				break;
		}
		
		return new DfsLegend(thresholds, colors);
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

    public static Color hex2Rgb(String colorStr) {
        return new Color(
            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}
