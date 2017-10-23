package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;

import javax.net.SocketFactory;
import javax.swing.JOptionPane;

import servidor.Tokens;
import servidor.calculaMac;

public	class	IntegrityVerifierClient	{
		//Constructor que abre una conexión Socket para enviar mensaje/MAC al servidor
		public	IntegrityVerifierClient(){					
			Boolean nuevoMensaje=true;
			try	{	
				while(nuevoMensaje) {
					SocketFactory socketFactory	= (SocketFactory)SocketFactory.getDefault();
					Socket	socket = (Socket)socketFactory.createSocket("localhost",7070);	
					
					//Crea un PrintWriter para enviar mensaje/MAC al servidor
					PrintWriter	output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					
					//tokens ----
					SecureRandom random=Tokens.generaToken();
					output.println(random);
					output.flush();
					//-----------
					
					String userName = JOptionPane.showInputDialog(null,"Introduzca su mensaje que desea enviar al servidor: " );
					
					//Envío	del	mensaje	al servidor
					output.println(userName);
					output.flush();
					
					String alg = JOptionPane.showInputDialog("Algoritmo a usar (no poner nada implica HmacSHA256): ");
					if(alg.equals(""))
						alg="HmacSHA256";
					output.flush();
					
					//Habría que calcular el correspondiente MAC con la	clave compartida por servidor/cliente
					String macdelMensaje = calculaMac.performMACTest(userName,alg);
					output.println(macdelMensaje);
					output.flush();
					
					//Habría que enviar el algoritmo de encriptacion que usamos
					output.println(alg);//HmacSHA256
					output.flush();
					
					//Importante para que el mensaje se	envíe
					output.flush();
					
					//Crea un objeto BufferedReader	para leer la respuesta del servidor
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//Lee la respuesta del servidor
					String respuesta = input.readLine();
					//Muestra la respuesta al cliente
					JOptionPane.showMessageDialog(null,respuesta);
					
					String maux = JOptionPane.showInputDialog("¿Desea enviar otro mensaje? (y/n): ");
					
					if(maux.equals("n"))	//permite enviar más de un mensaje
						nuevoMensaje=false;
					
					//Se cierra	la conexion
					output.close();
					input.close();
					socket.close();
					
				}	//fin del while
				
			}catch(IOException ioException){	
				ioException.printStackTrace();	
			}	
		 //Salida de la	aplicacion	
			finally	{
				System.exit(0);
			}
		}
}
