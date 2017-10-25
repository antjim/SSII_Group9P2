package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.SocketFactory;

public class tratamientoSecundarioCliente {
	
	public static void envioValorCliente() throws UnknownHostException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		SocketFactory socketFactory	= (SocketFactory)SocketFactory.getDefault();
		Socket	socket = (Socket)socketFactory.createSocket("localhost",7070);
		
		PrintWriter	output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		Integer valorCliente=metodosAux.valorTabla();
		
		output.println(valorCliente);
		output.flush();
		
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
		String tablaCifrada = input.readLine();
		
		String tablaDescifrada=metodosAux.descifraAES(tablaCifrada);	//esto lo usamos como clave de cifrado del token
		
		output.close();
		socket.close();
		input.close();
	}

}
