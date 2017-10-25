package servidor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Tablas {
	
	public static List<Integer> generadorTablas() {	//con esto generamos todas las posibilidades de nuestra tabla aleatoriamente
		List<Integer>res=new ArrayList<Integer>();
		SecureRandom r=new SecureRandom();
		for(int i=0;i<256;i++)
			res.add(r.nextInt());
		return res;
	}
	
	public static String seleccionaTablaYMezcla(List<Integer>l,Integer valorCliente) {
		return "3q{hrCRX-Fu@3bD15*" + l.get(valorCliente).toString();		//combinación privada
	}

}
