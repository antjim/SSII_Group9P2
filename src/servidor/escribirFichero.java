package servidor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class escribirFichero {
	
	public static void escribeFallo(String m) throws IOException {
		File incidencias=new File("mensajesNoIntegros.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		bw.write("=================================================\n");
		bw.write(df.format(date)+"\n");
		bw.write("Mensaje no integro: "+m+"\n");
		bw.write("\n");
		bw.close();
	}

}
