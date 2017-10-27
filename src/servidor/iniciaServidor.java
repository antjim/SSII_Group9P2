package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ServerSocketFactory;

public class iniciaServidor {
	
		private	ServerSocket serverSocket;
		
		public iniciaServidor() throws	Exception {				
			ServerSocketFactory	socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();		
			serverSocket=(ServerSocket)socketFactory.createServerSocket(7070);				
		}		
		
		void runServer() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
			DateFormat diario=new SimpleDateFormat("12");
			DateFormat hourFormat;
			Date date=new Date();
			List<Integer>contieneR=new ArrayList<Integer>();
			
			Boolean completaD=true;
			Integer success=0;	Integer fails=0;  Integer contKPID=0;
			
			while(true) {
				try	{
					
					hourFormat=new SimpleDateFormat("HH");
					
					if(Integer.parseInt(hourFormat.format(date)) > Integer.parseInt(diario.format(date))) {
						completaD=true;
					}	//reseteo diario del KPI para no generar más de 1 al día.
					
				
					System.err.println("Esperando conexiones de clientes...");	
					Socket socket =	(Socket)serverSocket.accept();
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter	output	= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					//comunicación segura token-
					
					String p=input.readLine();
					String g=input.readLine();
					String x=input.readLine();
					
					List<Integer>valores=metodosAuxServer.generaValor(Integer.parseInt(p), Integer.parseInt(g));
					Integer y=valores.get(0);
					Integer y2=valores.get(1);
					
					output.println(y);
					output.flush();
					
					Integer key=metodosAuxServer.generaKey(y2, Integer.parseInt(p), Integer.parseInt(x));
					
					String macPrueba=calculaMac.performMACTest("comprueba integridad", "HmacSHA256", key);
					output.println(macPrueba);
					output.flush();
					
					output.println("comprueba integridad");
					output.flush();
					
					//--------------------------
					
					if(contieneR.contains(key)) {
						System.err.println("Token usado - Posible ataque de replay - Tirando mensaje ..."
								+ "");
						output.close();			
						input.close();			
						socket.close();	
						break;
					}
					
					contieneR.add(key);
					//-------
					
					//	Se	lee	del	cliente	el	mensaje	y	el	macdelMensajeEnviado
					String	mensaje	= input.readLine();
					System.out.println("Mensaje enviado por el cliente: "+mensaje);
					//	A	continuación	habría	que	calcular	el	mac	del	MensajeEnviado	que	podría	ser											
					String	macdelMensajeEnviado = input.readLine();	
					System.err.println("Mac del mensaje enviado: "+macdelMensajeEnviado+"\n");
					//especificacion del algoritmo mac- por defecto diremos macsha256
					String alg=input.readLine();
					System.out.println("Algoritmo Hmac utilizado: "+alg);
					//mac	del	MensajeCalculado -----
					
					//String macMensajeEnviado = null;
					String macdelMensajeCalculado = calculaMac.performMACTest(mensaje, alg,key);
					
					//tratamiento de errores hmac----
					if(macdelMensajeCalculado.equals("")) {
						System.err.println("Hmac No valido, estableciendo por defecto Hmac256");
					    macdelMensajeCalculado = calculaMac.performMACTest(mensaje, "HmacSHA256",key);
					}
					// ------------------------------
					
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
