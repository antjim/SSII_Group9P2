package servidor;

import java.security.SecureRandom;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class calculaMac {
	
	public static String performMACTest(String s,String alg,Integer r){  
	 
		  try{
			  //byte[]key=new byte[2] ;	//0-255
			  
			  byte[]key=r.toString().getBytes();
			  
		      Mac mac=Mac.getInstance(alg);
		      
		      SecretKeySpec clave=new SecretKeySpec(key,alg);
		      
		      mac.init(clave);
		      mac.update(s.getBytes());
		      
		      // Indicamos que ya hemos acabado y recogemos el resultado
		      byte[] b=mac.doFinal();//ya nos la devuelve en byte el resultado
		      
		      return Conversor.aCadenaDeHexadecimales(b).toLowerCase();
		      
	    }catch(Exception e){
	      System.out.println(e.getMessage());
	    }
		  return "";
	  }
	  
	}

	class Conversor{
	  public static String[] digitos={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
	  public static String aCadenaDeHexadecimales(byte[] array){
	    String cadena="";
	    for(int i=0;i<array.length;i++)
	      cadena=cadena+convertirUno(array[i]);
	    return cadena;
	  }

	  private static String convertirUno(byte valor){
	    int n=valor;
	    if(n<0) n=256+n;
	    int d1=n/16;
	    int d2=n%16;
	    return digitos[d1]+digitos[d2];
	  }

}
