package principal;

import cliente.Cliente;
import servidor.Servidor;

public class principal {
	static public void main( String args[] ) {
		Thread hiloA = new Thread( (Runnable) new Cliente(),"hiloA" );
        Thread hiloB = new Thread( (Runnable) new Servidor(),"hiloB" );

        // Se arrancan los dos hilos, para que comiencen su ejecución
        hiloA.start();
        hiloB.start();
	}
}
