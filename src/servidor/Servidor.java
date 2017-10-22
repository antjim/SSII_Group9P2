package servidor;

public class Servidor implements Runnable{

	public static void main(String[] args) throws Exception {
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		iniciaServidor server = null;
		try {
			server = new	iniciaServidor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		server.runServer();
	}

}
