package bank;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerBank implements Runnable{

	public static void main(String[] args) {
		new ServerBank("Servidor");
	}
	private int acceso;
	private ServerSocket serverS_SC;
	private Thread Thread_Principal;
	private Socket sConnectToClientBank;
	private String ipClient;
	
	public ServerBank(String title){
		System.out.println("El servidor se ha ejecutado...");
		Thread_Principal = new Thread(this);
		Thread_Principal.start();
		
		System.out.println("Los Hilos se han ejecutados");

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				//1.-Seridor a la escucha para al, validacion del login
				System.out.println("Bucle--->>while(true)");
				serverS_SC = new ServerSocket(2800);
				Socket socket_SC = serverS_SC.accept();
				ipClient = socket_SC.getInetAddress().getHostAddress();
				
				ObjectInputStream disRecibiendo = new ObjectInputStream(socket_SC.getInputStream());
				acceso = validarAccesoUsuario((ClientesDB) disRecibiendo.readObject());
				
				System.out.println("ipClient --> "+ipClient);
				
				disRecibiendo.close();
				socket_SC.close();
				serverS_SC.close();
				
				// 2.Respuesta para el Cliente
				try {
					sConnectToClientBank = new Socket(ipClient, 2801);
					
					DataOutputStream dosRespForClient1 = new DataOutputStream(sConnectToClientBank.getOutputStream());
					System.out.println("acceso--> "+acceso);
					dosRespForClient1.writeInt(acceso);
		
					dosRespForClient1.close();
					sConnectToClientBank.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	ClientesDB u [] = new ClientesDB[5];
	
	private int validarAccesoUsuario(ClientesDB acceptLogin) {
		System.out.println("Data Login user: " + acceptLogin);
		
		System.out.println("User-->>::: " + acceptLogin.getUsuario());
		System.out.println("Pass-->>::: " + acceptLogin.getContrasena());
		
		u[0] = new ClientesDB("harry","1234",0);
		u[1] = new ClientesDB("User3","1234",0);
		u[2] = new ClientesDB("User4","1234",0);
		u[3] = new ClientesDB("User5","1234",0);
		u[4] = new ClientesDB("User6","1234",0);
		
		for (int i = 0; i < u.length; i++) {
			if(
					u[i].getUsuario().equalsIgnoreCase(acceptLogin.getUsuario())
				&&
					u[i].getContrasena().equalsIgnoreCase(acceptLogin.getContrasena())
				) {
					//dataUser_Svr = u[i];
					System.out.println("¡¡¡¡Fine!!!");
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return 0;
			}
		}
		System.out.println("No Fine");
		return 1;
	}
	
	
	 
}
