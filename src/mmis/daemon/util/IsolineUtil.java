package mmis.daemon.util;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class IsolineUtil {

	public static double[] getMaxPoints(GeneralPath[] generalPaths) {

        double maxX = 0;
        double maxY = 0;
   
    	for(int i=0 ; i<generalPaths.length ; i++) {
    		
    		 PathIterator iter = generalPaths[i].getPathIterator(new AffineTransform());
    	     
	        while (!iter.isDone()) {
	        	
	            double[] coords = new double[2];
	            iter.currentSegment(coords);
	           
	            maxX = Math.max(maxX, coords[0]);
	            maxY = Math.max(maxY, coords[1]);
	            
	            iter.next();
	        }
    	}
        
        return new double[]{maxX, maxY};
    }
}
