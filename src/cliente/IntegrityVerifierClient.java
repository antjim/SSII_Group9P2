package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.swing.JOptionPane;

import servidor.calculaMac;

public	class	IntegrityVerifierClient	{
		//Constructor que abre una conexión Socket para enviar mensaje/MAC al servidor
		public	IntegrityVerifierClient(){					
			try	{	
					SocketFactory socketFactory	= (SocketFactory)SocketFactory.getDefault();
					Socket	socket = (Socket)socketFactory.createSocket("localhost",7070);	
					
					//Crea un PrintWriter para enviar mensaje/MAC al servidor
					PrintWriter	output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					String userName = JOptionPane.showInputDialog(null,"Introduzca su mensaje (Nombre de cuenta origen, Nombre de cuenta destino, cantidad transferida): " );
					
					//Envío	del	mensaje	al servidor
					output.println(userName);
					output.flush();
					
					//Habría que calcular el correspondiente MAC con la	clave compartida por servidor/cliente
					String macdelMensaje = calculaMac.performMACTest(userName,"HmacSHA256");
					output.println(macdelMensaje);
					output.flush();
					
					//Habría que enviar el algoritmo de encriptacion que usamos
					output.println("HmacSHA256");//HmacSHA256
					output.flush();
					
					//Importante para que el mensaje se	envíe
					output.flush();
					
					//Crea un objeto BufferedReader	para leer la respuesta del servidor
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//Lee la respuesta del servidor
					String respuesta = input.readLine();
					//Muestra la respuesta al cliente
					JOptionPane.showMessageDialog(null,respuesta);
					
					//Se cierra	la conexion
					output.close();
					input.close();
					socket.close();
					
			}catch(IOException ioException){	
				ioException.printStackTrace();	
			}	
		 //Salida de la	aplicacion	
			finally	{
				System.exit(0);
			}
		}
}
