package servidor;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class metodosAuxServer {	//aquí es donde trabajará todos los tokens junto con la seguridad extra.
	
	public static byte[] cifraAES(String mensaje) throws InvalidKeyException, 
		NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		Key key = keyGenerator.generateKey();
		key = new SecretKeySpec("3q{hrCRX-Fu@3bD15*".getBytes(),  0, 16, "AES");
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	
		aes.init(Cipher.ENCRYPT_MODE, key);
		byte[] cifra = aes.doFinal(mensaje.getBytes());
		System.out.println("cifra: "+cifra.toString());
		
		aes.init(Cipher.DECRYPT_MODE, key);
	    
	    byte[] descifra = aes.doFinal(cifra.toString().getBytes());
	    
	    System.out.println(new String (descifra) + " mensaje original: "+mensaje);
		
	    return cifra;
	}
	
	public static List<Integer> generaValor(Integer p, Integer g) {
		SecureRandom r=new SecureRandom();
		List<Integer>l=new ArrayList<Integer>();
		Integer x=r.nextInt();
		Double res=1.0;
		
		while(x<0)
			x=r.nextInt(p-2);
		
		String n=Integer.toBinaryString(x);
		
		for (int i=0;i<n.length();i++) {
			char c=n.charAt(i);
			
			if(c=='1') {
				res=(res*Double.parseDouble(g.toString()))%Double.parseDouble(p.toString());
				if(!(i==n.length()-1) && n.charAt(i+1)!='0')
					res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
			}
			else {
				res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
				if(!(i==n.length()-1) && n.charAt(i+1) !='0')
					res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
			}
			
		}
		
		l.add((int)res.doubleValue());l.add(x);
		
		return l;
	}
	
	public static Integer generaKey(Integer y,Integer p, Integer x) {
		Double res=1.0;
		
		String n=Integer.toBinaryString(y);
		
		for (int i=0;i<n.length();i++) {
			char c=n.charAt(i);
			
			if(c=='1') {
				res=(res*Double.parseDouble(x.toString()))%Double.parseDouble(p.toString());
				if(!(i==n.length()-1) && n.charAt(i+1)!='0')
					res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
			}
			else {
				res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
				if(!(i==n.length()-1) && n.charAt(i+1) !='0')
					res= ( Math.pow(res, 2) ) % Double.parseDouble(p.toString());
			}
		}
		return (int)res.doubleValue();
	}

}
