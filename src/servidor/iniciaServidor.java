package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ServerSocketFactory;

public class iniciaServidor {
	
		private	ServerSocket serverSocket;
		
		public iniciaServidor() throws	Exception {				
			ServerSocketFactory	socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();		
			serverSocket=(ServerSocket)socketFactory.createServerSocket(7070);				
		}		
		
		void runServer() {
			DateFormat diario=new SimpleDateFormat("12");
			DateFormat hourFormat;
			Date date=new Date();
			List<SecureRandom>contieneR=new ArrayList<SecureRandom>();
			
			Boolean completaD=true;
			Integer success=0;	Integer fails=0;  Integer contKPID=0;
			
			while(true) {
				try	{
					hourFormat=new SimpleDateFormat("HH");
					
					if(Integer.parseInt(hourFormat.format(date)) > Integer.parseInt(diario.format(date))) {
						completaD=true;
					}	//reseteo diario del KPI para no generar más de 1 al día.
					
					//tratamiento de tokens----
					SecureRandom random=Tokens.generaToken();
					
					while(contieneR.contains(random))
						random=Tokens.generaToken();
					
					contieneR.add(random);
					//-------
					System.err.println(	"Esperando	conexiones	de	clientes...");	
					Socket socket =	(Socket)serverSocket.accept();
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter	output	= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
					
					//	Se	lee	del	cliente	el	mensaje	y	el	macdelMensajeEnviado
					String	mensaje	= input.readLine();		
					//	A	continuación	habría	que	calcular	el	mac	del	MensajeEnviado	que	podría	ser											
					String	macdelMensajeEnviado = input.readLine();	
					
					//especificacion del algoritmo mac- por defecto diremos macsha256
					String alg=input.readLine();
					
					//mac	del	MensajeCalculado -----
					
					//String macMensajeEnviado = null;
					String macdelMensajeCalculado = calculaMac.performMACTest(mensaje, alg);
					
					if	(macdelMensajeEnviado.equals(macdelMensajeCalculado))	{	
							output.println("Mensaje enviado integro.");	
							success++;
					}else{	
						output.println(	"Mensaje enviado no integro.");
						fails++;
						escribirFichero.escribeFallo(mensaje);
					}
					
					//KPI rutinario ----
					if( diario.equals(hourFormat) && completaD ) {
						Integer total=success+fails;
						contKPID++;
						Kpi.calculaKPI(success, total,contKPID);
						completaD=false;
					}
					// -----------------
					
					output.close();			
					input.close();			
					socket.close();	
					
			}catch (IOException	ioException){ioException.printStackTrace();}	
		}
	}
		
}
