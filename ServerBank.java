package bank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerBank implements Runnable{

	public static void main(String[] args) {
		new ServerBank("Servidor");
	}

	private ServerSocket serverS_SC;
	private Thread Thread_Principal;
	private Socket sConnectToClientBank;
	private byte bAccessClient;
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
			while(true){
				//1.-Seridor a la escucha para al, validacion del login
				System.out.println("while-true");
				serverS_SC = new ServerSocket(2828);
				Socket socket_SC = serverS_SC.accept();
				ipClient = socket_SC.getInetAddress().getHostAddress();
				
				DataInputStream disRecibiendo = new DataInputStream(socket_SC.getInputStream());
				
				bAccessClient = userAdmin(disRecibiendo.readUTF());
				System.out.println("ipClient --> "+ipClient);
				System.out.println("bAccessClient --> "+bAccessClient);
				
				disRecibiendo.close();
				socket_SC.close();
				serverS_SC.close();
				
				//2.Respuesta para el cliente
//				if(bAccessClient != 0) {
				try {
					sConnectToClientBank = new Socket(ipClient, 7070);
					
					DataOutputStream dosRespForClient = new DataOutputStream(sConnectToClientBank.getOutputStream());
					System.out.println(bAccessClient);
					dosRespForClient.writeByte(bAccessClient);
					
					dosRespForClient.close();
					sConnectToClientBank.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
//				}
			}    
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}

	
	private byte userAdmin(String acceptLogin) {
		System.out.println("Data Login user: "+acceptLogin);
		System.out.println("sub::: "+acceptLogin.substring(0, 5));
		if(acceptLogin.substring(0, 5).equalsIgnoreCase("harry")) {
			return 0;
		}else {
			return 1;
		}
		
		/*
		if(user.equals("admin") && pass.equals("keyadmin")){
			checkU = true;
			admin = 1;
			
		}else{
			for(int i = 0;i < listaNueva.length; i++){
				if(user.equals(listaNueva[i].getUsuario()) && passConv.equals(listaNueva[i].getContrasena())){
					usuario = listaNueva[i].getUsuario();
					contrasena = listaNueva[i].getContrasena();
					
					checkU = true;
					break;
				}else{
					checkU = false;
				}
			}
		}
		*/
	}
}
