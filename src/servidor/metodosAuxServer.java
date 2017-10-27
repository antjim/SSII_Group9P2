package servidor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class metodosAuxServer {	//aquí es donde trabajará todos los tokens junto con la seguridad extra.
	
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
