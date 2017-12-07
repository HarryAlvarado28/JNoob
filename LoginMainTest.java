package bank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;

public class LoginMainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoAccesoNewVersion loginMarco = new MarcoAccesoNewVersion("DMG Bank");
		loginMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


class MarcoAccesoNewVersion extends JFrame implements KeyListener, ActionListener, Runnable{
	private static final String ipServer = "127.0.0.1";
	
	private JButton jbEnter;
	private JTextField jtfUser;
	private JPasswordField jpfPass;
	private JLabel jlUser, jlPass;
	private JTextPane jtpMessage;
	private String alet = "Credenciales no correctas. Reintentalo.";
	private boolean acceso;
	
	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");

	//---------------------------------------------------------------------------------
//	private URL ruta_externa = MarcoAcceso.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	//---------------------------------------------------------------------------------
	
	private Dimension tamanoPantalla = mipantalla.getScreenSize();
	private int alturaPantalla = tamanoPantalla.height;
	private int anchoPantalla = tamanoPantalla.width ;
	private int admin = 0;
	
	private InterfazAdministrador GUI_Administrador;
	private InterfazDeUsuario<?> GUI_Cliente;

	private String nombre;
	private String apellido;
	private String usuario;
	private String numCuenta;
	private double saldoInicial;
	private String contrasena;
	private String tipoCuenta;
	private Thread thCheckLogin;
	//---------------------------------------------------------
	private Socket sConnectToServerBank;
	private ServerSocket yo;
	private Socket sAcceptConection;
	static private int accesoPermitido = -1;
	private ClientesDB dataLogin;
	//---------------------------------------------------------
	public MarcoAccesoNewVersion(String title){
		setTitle(title);
		//setBounds(300,150,260,300);
		setBounds(alturaPantalla/2,anchoPantalla/4,245,295);
//		setIconImage(miIcono);		
		     
		setResizable(false);
		setLayout(null);
		thCheckLogin = new Thread(this);
		thCheckLogin.start();
		
		jlUser = new JLabel("Usuario");
		jtfUser = new JTextField(10);
		jtfUser.addKeyListener(this);
		
		jlPass = new JLabel("Password");
		jpfPass = new JPasswordField(10);
		jpfPass.addKeyListener(this);
		jtpMessage = new JTextPane();
		
		jtpMessage.setEditable(false);
		jtpMessage.setBackground(getBackground());
		
		
		jbEnter = new JButton("Acceder");
		jbEnter.addActionListener(this);
		
		
		add(jlUser).setBounds(100, 25, 100, 20);
		add(jtfUser).setBounds(60, 50, 130, 25);
		add(jlPass).setBounds(90, 85, 100, 20);
		add(jpfPass).setBounds(60, 110, 130, 25);
		add(jbEnter).setBounds(75, 170, 100, 40);
		
//		add(new LaminaConImagen()).setBounds(0, 0, 245, 295);
//		this.setBackground(new Color(245,136,8));
		setVisible(true);
	}

	/**
	 * @return si existe acceso de usuario o no existe
	 */
	public boolean getAcceso(){
		return acceso;
	}

	private boolean checkUser(){
		if(!thCheckLogin.isAlive()) {
			System.out.println("Correr nuevamente el HILO");
			thCheckLogin = new Thread(this);
			thCheckLogin.start();
		}
		
		boolean checkU = false;
		String user = jtfUser.getText();
		char[] pass = jpfPass.getPassword();
		String passConv = new String(pass);
		dataLogin = new ClientesDB(user,passConv, -1);
		
		try {
			//1. Conectando al servidor para enviar enviar datos de login
			sConnectToServerBank = new Socket(ipServer,2800);
			
			ObjectOutputStream dosEnvioLogin = new ObjectOutputStream(sConnectToServerBank.getOutputStream());
			System.out.println("ObjectOutputStream[dataLogin.getUsuario()]----> "+ dataLogin.getUsuario());
			//dosEnvioLogin.writeUTF(dataLogin);
			dosEnvioLogin.writeObject(dataLogin);
			
			dosEnvioLogin.close();
			sConnectToServerBank.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Existe un inconveniente al conectarse al Servidor");
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(accesoPermitido == 0){
			checkU = true;
			nombre = user;
			usuario = user;
		}else if (accesoPermitido == 1){
			checkU = false;
		}else if(accesoPermitido == 2) {
			checkU = true;
			admin = 1;
		}
		System.out.println("accesoPermitido--> "+accesoPermitido);
		if(checkU){
			add(jtpMessage).setBounds(60, 140, 130, 10);
			jtpMessage.setText(null);
			jtpMessage.setBackground(getBackground());
			add(jbEnter).setBounds(75, 160, 100, 40);
		}else{			
			add(jtpMessage).setBounds(60, 135, 130, 50);
			jtpMessage.setText(alet);
			if(accesoPermitido==-1) {
				jtpMessage.setText("Error de Conexión");
			}else {
				jtpMessage.setText(alet);
			}
			jtpMessage.setBackground(getBackground());
			jtpMessage.setForeground(Color.RED.brighter());
			add(jbEnter).setBounds(75, 200, 100, 40);
		}
		System.out.println("checkU-->> "+checkU);
		return checkU;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub	
		
		if(jbEnter == e.getSource()){
			acceso = checkUser();
			if(acceso & admin == 1){
				this.dispose();
				GUI_Administrador = new InterfazAdministrador();
				GUI_Administrador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GUI_Administrador.setVisible(true);
				
			}else if(acceso){
				this.dispose();
				GUI_Cliente = new InterfazDeUsuario<>(dataLogin);
				//GUI_Cliente = new InterfazDeUsuario<Object>(nombre,apellido,usuario,numCuenta,saldoInicial,contrasena,tipoCuenta);
				GUI_Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				GUI_Cliente.setVisible(true);
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 10){
			acceso = checkUser();
			if(acceso & admin == 1){
				this.dispose();
				GUI_Administrador = new InterfazAdministrador();
				GUI_Administrador.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				GUI_Administrador.setVisible(true);
			}else if(acceso){
				this.dispose();
				GUI_Cliente = new InterfazDeUsuario<Object>(nombre,apellido,usuario,numCuenta,saldoInicial,contrasena,tipoCuenta);
				GUI_Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				GUI_Cliente.setVisible(true);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				System.out.println("Hilo Primario thCheckLogin en Ejecucion");
				//1. Conectando al servidor para enviar enviar datos de login
				yo = new ServerSocket(2801);
				sAcceptConection = yo.accept();
				
				//ObjectInputStream oisRecibiendo = new ObjectInputStream(sAcceptConection.getInputStream());
				
				//dataUser = (ClientesDB) oisRecibiendo.readObject(); 
				
				DataInputStream disRespServer = new DataInputStream(sAcceptConection.getInputStream());
				
				accesoPermitido = disRespServer.readInt();
				
				//System.out.println("disRespServer.readInt()--> "+disRespServer.readInt());
				
				disRespServer.close();
				sAcceptConection.close();
				yo.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
			e.printStackTrace();
		}
		
		
	}


}
