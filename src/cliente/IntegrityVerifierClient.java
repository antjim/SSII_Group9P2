package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.SocketFactory;
import javax.swing.JOptionPane;
import servidor.calculaMac;

public	class	IntegrityVerifierClient	{
		//Constructor que abre una conexión Socket para enviar mensaje/MAC al servidor
		public	IntegrityVerifierClient() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{					
			Boolean nuevoMensaje=true;
			try	{	
				while(nuevoMensaje) {
					
					SocketFactory socketFactory	= (SocketFactory)SocketFactory.getDefault();
					Socket	socket = (Socket)socketFactory.createSocket("localhost",7070);	
					
					//Crea un PrintWriter para enviar mensaje/MAC al servidor
					PrintWriter	output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					//Crea un objeto BufferedReader	para leer la respuesta del servidor
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					//ObjectInputStream obj=new ObjectInputStream(socket.getInputStream());
					//antes de comenzar el envío del mensaje ...
					
					Integer p=metodosAux.generaPrimo();
					output.println(p);
					output.flush();
					
					Integer g=metodosAux.creaGenerador(p);
					output.println(g);
					output.flush();
					
					List<Integer>valores=metodosAux.generaValor(p, g);
					
					Integer x=valores.get(0);
					Integer x2=valores.get(1);
					
					output.println(x);
					output.flush();
					
					String y=input.readLine();
					//p=71;g=21;x=46;y="61";
					//p=23;g=5;x=6;y="19";
					Integer key=metodosAux.generaKey(Integer.parseInt(y), p, x2);
					System.out.println("key del cliente: "+key);
					
					String macServer=input.readLine();
					String mServer=input.readLine();
					String macCal=calculaMac.performMACTest(mServer,"HmacSHA256", key);
					
					if( !(macServer.equals(macCal)) ) {
						System.err.println("Detectado Man-in-the-middle --- Terminando proceso");
						output.close();
						input.close();
						socket.close();
						break;
					}
					//---------------------------------------------
					
					String userName = JOptionPane.showInputDialog(null,"Introduzca su mensaje que desea enviar al servidor: " );
					
					//Envío	del	mensaje	al servidor
					output.println(userName);
					output.flush();
					
					String alg = JOptionPane.showInputDialog("Algoritmo a usar (no poner nada implica HmacSHA256): ");
					if(alg.equals(""))
						alg="HmacSHA256";
					output.flush();
					
					//Habría que calcular el correspondiente MAC con la	clave compartida por servidor/cliente
					String macdelMensaje = calculaMac.performMACTest(userName,alg,key);
					output.println(macdelMensaje);
					output.flush();
					
					//Habría que enviar el algoritmo de encriptacion que usamos
					output.println(alg);//HmacSHA256
					output.flush();
					
					//Importante para que el mensaje se	envíe
					output.flush();
					
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
