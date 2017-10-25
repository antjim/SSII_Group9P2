package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ServerSocketFactory;

public class tratamientoSecundarioServidor {	//aquí es donde trabajará todos los tokens junto con la seguridad extra.
	private static	ServerSocket serverSocket;
	
	public static void recibeValorClienteYEnvia() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		ServerSocketFactory	socketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();		
		serverSocket=(ServerSocket)socketFactory.createServerSocket(7070);
		
		
		Socket socket =	(Socket)serverSocket.accept();
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter	output	= new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		String valorCliente=input.readLine();
		List<Integer>l=Tablas.generadorTablas();
		String combinacion=Tablas.seleccionaTablaYMezcla(l, Integer.parseInt(valorCliente));
		
		String cifradoClaveTabla=cifraAES(combinacion);
		
		output.println(cifradoClaveTabla);
		output.flush();
		
		output.close();			
		input.close();			
		socket.close();
	}
	
	public static String cifraAES(String mensaje) throws InvalidKeyException, 
		NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		Key key = keyGenerator.generateKey();
		key = new SecretKeySpec("3q{hrCRX-Fu@3bD15*".getBytes(),  0, 16, "AES");
	
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	
		aes.init(Cipher.ENCRYPT_MODE, key);
		byte[] cifra = aes.doFinal(mensaje.getBytes());
		String cifrado=new String(cifra);
		return cifrado;
	}

}
