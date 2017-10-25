package cliente;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class metodosAux {
	
	public static Integer valorTabla() {	//lo usamos para enviarlo al servidor y éste coger la tabla
		Integer res;
		Random r=new Random();
		res=r.nextInt(256);
		return res;
	}

	public static String descifraAES(String mensajeCifrado) throws NoSuchAlgorithmException, 
		NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(128);
	    Key key = keyGenerator.generateKey();
	    key = new SecretKeySpec("3q{hrCRX-Fu@3bD15*".getBytes(),  0, 16, "AES");
	    
	    Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    aes.init(Cipher.DECRYPT_MODE, key);
	    
	    byte[] descifra = aes.doFinal(mensajeCifrado.getBytes());
	    
	    String descifrado=new String(descifra);
	    
	    return descifrado;
	}
		

}
