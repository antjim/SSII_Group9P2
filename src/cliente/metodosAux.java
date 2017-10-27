package cliente;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class metodosAux {
	
	public static Integer generaPrimo() {
		SecureRandom r=new SecureRandom();
		Integer p=r.nextInt(100000000);	//max cantidad para int y así generar keys similares
		while(!esPrimo(p) || p<0) {
			p=r.nextInt(100000000);
		}
		return p;
	}
	
	public static Boolean esPrimo(Integer p) {
		Boolean res=true;
		
		for (int i=2;i<p;i++) {
			if(p%i==0) {
				res=false;
				break;
			}
		}
		
		return res;
	}
	
	public static Integer creaGenerador(Integer p) {
		
		SecureRandom r=new SecureRandom();
		Integer g=r.nextInt(p-2);
		
		while((g<2))
			g=r.nextInt(p-2);
		
		return g;
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
		
		String n=Integer.toBinaryString(x);
		
		for (int i=0;i<n.length();i++) {
			char c=n.charAt(i);
			
			if(c=='1') {
				res=(res*Double.parseDouble(y.toString()))%Double.parseDouble(p.toString());
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
