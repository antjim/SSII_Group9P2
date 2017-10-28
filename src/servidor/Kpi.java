package servidor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Kpi {
	
	public static void calculaKPI(Integer success, Integer total,Integer contKPID) throws IOException {
		Float res = (float) (success)/total;
		
		File incidencias=new File("DailyKPI.txt");	
		BufferedWriter bw = new BufferedWriter(new FileWriter(incidencias,true));
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
		
		bw.write("====================KPI-"+contKPID+"=============================\n");
		bw.write("Fecha y Hora: "+df.format(date)+"\n");
		bw.write("mensajes totales: "+total+"\n");
		bw.write("éxito: "+success+"\n");
		bw.write("Resultado: "+res+"\n");
		bw.write("\n");
		bw.close();
	}
	

}
