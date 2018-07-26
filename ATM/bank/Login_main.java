
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Login_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MarcoBaseBankATM bankATM = new MarcoBaseBankATM();
		//bankATM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
//		MarcoBaseBankATM();
		MarcoAcceso loginMarco = new MarcoAcceso("DMG Bank");
		loginMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//generando un metodo estatico para crear el objeto de la clase MarcoAcceso
//	private static void MarcoBaseBankATM(){
//		MarcoAcceso loginMarco = new MarcoAcceso("Bank $.$");
//		loginMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	
}


@SuppressWarnings("serial")
class MarcoAcceso extends JFrame implements KeyListener, ActionListener{
	private JButton jbEnter;
	private JTextField jtfUser;
	private JPasswordField jpfPass;
	private JLabel jlUser, jlPass;
	private JTextPane jtpMessage;
	private String alet = "Credenciales no correctas. Reint�ntalo.";
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
	
	public MarcoAcceso(String title){
		setTitle(title);

		//setBounds(300,150,260,300);
		setBounds(alturaPantalla/2,anchoPantalla/4,245,295);
//		setIconImage(miIcono);		
		     
		setResizable(false);
		setLayout(null);
		
		jlUser = new JLabel("Usuario");
		jtfUser = new JTextField(10);
		jtfUser.addKeyListener(this);
		
		jlPass = new JLabel("Contrase�a");
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
		add(jbEnter).setBounds(75, 160, 100, 40);
		
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
	
	@SuppressWarnings("unchecked")
	private boolean checkUser(){
		boolean checkU = true;
		String user = jtfUser.getText();
		char[] pass = jpfPass.getPassword();

		ClientesDB []listaNueva = null;
		
		try{
			
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
			
			leer_fichero.close();
			
			listaNueva = new ClientesDB[personal_Recuperado.size()];
			
			personal_Recuperado.toArray(listaNueva);			
			
			
		}catch(Exception e){
//			System.out.println("fichero no encontrado");
//			JOptionPane.showMessageDialog(this, "No existe lista de clientes asociados");
		}		
		
		
		String passConv = new String(pass);
		
		if(user.equals("admin") && passConv.equals("keyadmin")){
			checkU = true;
			admin = 1;
			
		}else{
			for(int i = 0;i < listaNueva.length; i++){
				if(user.equals(listaNueva[i].getUsuario()) && passConv.equals(listaNueva[i].getContrasena())){
					nombre = listaNueva[i].getNombre();
					apellido = listaNueva[i].getApellido();
					usuario = listaNueva[i].getUsuario();
					numCuenta = listaNueva[i].getNumCuenta();
					saldoInicial = listaNueva[i].getSaldoInicial();
					contrasena = listaNueva[i].getContrasena();
					tipoCuenta = listaNueva[i].getTipoCuenta();
					
					checkU = true;
					break;
				}else{
					checkU = false;
				}
			}
		}


		if(checkU){
			add(jtpMessage).setBounds(60, 140, 130, 10);
			jtpMessage.setText(null);
			jtpMessage.setBackground(getBackground());
			add(jbEnter).setBounds(75, 160, 100, 40);
			
		}else{			
			add(jtpMessage).setBounds(60, 140, 130, 40);
			jtpMessage.setText(alet);
			jtpMessage.setBackground(getBackground());
			jtpMessage.setForeground(Color.RED.brighter());
			add(jbEnter).setBounds(75, 200, 100, 40);
		}
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
				GUI_Cliente = new InterfazDeUsuario<Object>(nombre,apellido,usuario,numCuenta,saldoInicial,contrasena,tipoCuenta);
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
	
	class LaminaConImagen extends JPanel{
		private Image imagen;
		URL ruta_externa = MarcoAcceso.LaminaConImagen.class.getResource("dise�oLogin5.png");

		public LaminaConImagen(){
//			URL ru = MarcoAcceso.class.getResource("dise�oLogin5.png");
//			File mimagen = new File("src/img/dise�oLogin5.png");
			
			try{
				imagen = ImageIO.read(ruta_externa);
			

//				imagen = ImageIO.read(ru);
//				imagen = ImageIO.read(mimagen);
			}
			catch(IOException e){
//				System.out.println("La imagen no se en cuentra");
			}
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			imagen.getWidth(this);
			imagen.getHeight(this);
			g.drawImage(imagen,0,0, null);

		}
		
	}


}


