package servidor;

import java.security.SecureRandom;
import java.util.List;

public class Tokens {
	
	public static SecureRandom generaToken() {
		 SecureRandom random = new SecureRandom();
	     byte bytes[] = new byte[16];
	     random.nextBytes(bytes);
	     return random;
	}
	
	public static Boolean compruebaToken(SecureRandom r,List<SecureRandom>consumidoTokens) {
		Boolean res=false;
		
		if(consumidoTokens.contains(r))
			res=true;
		
		return res;
	}

}
