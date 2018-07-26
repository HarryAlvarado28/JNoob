

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author WorkStation-Hackrry
 *
 */

public class ServidorChat extends JFrame implements Runnable, ActionListener{

	private static final long serialVersionUID = 28;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServidorChat p = new ServidorChat("Servidor Chat");
		p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private JTextArea jtChat = new JTextArea();
	private JScrollPane jspTextChat;
	private JTextField jtfPort;
	private JButton jbPort;
	private ServerSocket serverS_SC;
	private Thread Thread_Principal;
	
	public ServidorChat(String title){
		super(title);
		setBounds(200,300,360,200);
		Thread_Principal = new Thread(this);
		Thread_Principal.start();
		
		verPuerto();
		jtChat.setEditable(false);
		jspTextChat = new JScrollPane(jtChat);
		add(jspTextChat,BorderLayout.CENTER);
		setVisible(true);
	}
	
	private void verPuerto(){
		JPanel jpNorth = new JPanel();
		jtfPort = new JTextField(4);
		
		jtfPort.setEditable(false);
		jbPort = new JButton("Ver Puerto");
		jbPort.setEnabled(false);
		jbPort.addActionListener(this);
		
		jpNorth.add(jbPort);
		jpNorth.add(jtfPort); 
		
		add(jpNorth,BorderLayout.NORTH);
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ArrayList<String> alListaIp = new ArrayList<String>();
			ArrayList<String> alListaNombres = new ArrayList<String>();
			HashMap<String, String> hmListaUsuarios = new HashMap<String, String>();
			Mensaje mensajeRecibido;
			String cortoMensaje;
			
			while(true){
				serverS_SC = new ServerSocket(2828);
				Socket socket_SC = serverS_SC.accept();
				
				ObjectInputStream oisRecibiendo = new ObjectInputStream(socket_SC.getInputStream());

				mensajeRecibido = (Mensaje) oisRecibiendo.readObject();
				
				oisRecibiendo.close();
				socket_SC.close();
				serverS_SC.close();
				
				jbPort.setEnabled(true);
				
				String ipRemota = socket_SC.getInetAddress().getHostAddress();
				//----me indica la ip remota------
				mensajeRecibido.setMiIP(ipRemota);	
				
				
				
				if(mensajeRecibido.getMensaje().equalsIgnoreCase("Conectado")){
					cortoMensaje = "Se a "+ mensajeRecibido.getMensaje() + " " +mensajeRecibido.getNombre()+
							" (IP:"+mensajeRecibido.getMiIP()+")";
					
					alListaIp.add(ipRemota);
					alListaNombres.add(mensajeRecibido.getNombre());
					
					hmListaUsuarios.put(mensajeRecibido.getNombre(), ipRemota);	
					mensajeRecibido.setUsuario(hmListaUsuarios);
					
					mensajeRecibido.setListaIPs(alListaIp);
					mensajeRecibido.setListaNombres(alListaNombres);
					
					for(String ips: alListaIp){
						Socket socketReenvio = new Socket(ips,2882);
						mensajeRecibido.setListaIPs(alListaIp);
						mensajeRecibido.setListaNombres(alListaNombres);
						mensajeRecibido.setUsuario(hmListaUsuarios);
						
						ObjectOutputStream oosReenvio = new ObjectOutputStream(socketReenvio.getOutputStream());
						oosReenvio.writeObject(mensajeRecibido);
						
						oosReenvio.close();
						socketReenvio.close();
						
					}
					
				}else if(mensajeRecibido.getMensaje().equalsIgnoreCase("Desconectado")){
					cortoMensaje = "Se a "+ mensajeRecibido.getMensaje() + " " +mensajeRecibido.getNombre()+
							" (IP:"+mensajeRecibido.getMiIP()+")";
					
					alListaIp.remove(ipRemota);
					alListaNombres.remove(mensajeRecibido.getNombre());
					hmListaUsuarios.remove(mensajeRecibido.getNombre());	
					
					mensajeRecibido.setListaIPs(alListaIp);
					mensajeRecibido.setListaNombres(alListaNombres);
					mensajeRecibido.setUsuario(hmListaUsuarios);					
					
					for(String ips: alListaIp){
						Socket socketReenvio = new Socket(ips,2882);
						mensajeRecibido.setListaIPs(alListaIp);
						mensajeRecibido.setListaNombres(alListaNombres);
						mensajeRecibido.setUsuario(hmListaUsuarios);
						
						ObjectOutputStream oosReenvio = new ObjectOutputStream(socketReenvio.getOutputStream());
						oosReenvio.writeObject(mensajeRecibido);
						
						oosReenvio.close();
						socketReenvio.close();
						
					}
				}else{
					System.out.println("Para: "+mensajeRecibido.getDestinatario());			
					
						mensajeRecibido.setListaIPs(alListaIp);
						mensajeRecibido.setListaNombres(alListaNombres);
						mensajeRecibido.setUsuario(hmListaUsuarios);
						
						Socket socketReenvio = new Socket(mensajeRecibido.getDestinatario(),2882);
						
						ObjectOutputStream oosReenvio = new ObjectOutputStream(socketReenvio.getOutputStream());
						oosReenvio.writeObject(mensajeRecibido);
						
						oosReenvio.close();
						socketReenvio.close();

					cortoMensaje = mensajeRecibido.getNombre()+" para "+mensajeRecibido.getDestinatario()+" : "+mensajeRecibido.getMensaje();

				}
				if(!(mensajeRecibido.getMensaje() == null || mensajeRecibido.getMensaje().equalsIgnoreCase(""))){
					jtChat.setText( jtChat.getText()+"\n" +cortoMensaje);
				}
				
				
				socket_SC.close();
				serverS_SC.close();
			}    
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		
		jtfPort.setText(""+serverS_SC.getLocalPort());
		
	}

}
