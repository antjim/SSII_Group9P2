package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class iniciaServidor {
	
		private	ServerSocket serverSocket;
		
		public void IntegrityVerifierServer() throws	Exception {				
			ServerSocketFactory	socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();		
			serverSocket=(ServerSocket)socketFactory.createServerSocket(7070);				
		}		
		
		void runServer() {
			
			while(true) {
				try	{	
					System.err.println(	"Esperando	conexiones	de	clientes...");	
					Socket socket =	(Socket)serverSocket.accept();
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter	output	= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));	
					
					//	Se	lee	del	cliente	el	mensaje	y	el	macdelMensajeEnviado
					String	mensaje	=	input.readLine();		
					//	A	continuación	habría	que	calcular	el	mac	del	MensajeEnviado	que	podría	ser											
					String	macdelMensajeEnviado	=	input.readLine();	
					//mac	del	MensajeCalculado	
					String macMensajeEnviado = null;
					String macdelMensajeCalculado = null;
					
					if	(macMensajeEnviado.equals(macdelMensajeCalculado))	{	
							output.println("Mensaje enviado integro");	
					}else{	
						output.println(	"Mensaje	enviado	no	integro."	);	
					}
					
					output.close();			
					input.close();			
					socket.close();	
			}catch (IOException	ioException){ioException.printStackTrace();}	
		}
	}
		
}
