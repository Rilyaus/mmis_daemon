package mmis.daemon.test;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class ReadBinaryTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DataInputStream dis = new DataInputStream(new FileInputStream("F:/data/mmis_sample/meta/kim_gust_lat.bin"));
		
		for(int i=0 ; i<100 ; i++) {
			System.out.println(dis.readFloat());	
		}
		
		
		
		dis.close();
		
		

	}

}
