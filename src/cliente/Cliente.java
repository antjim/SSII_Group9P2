package cliente;

public class Cliente implements Runnable{

	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new	IntegrityVerifierClient();
	}

}
