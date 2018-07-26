
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * @author WorkStation-Hackrry
 *
 */

public class Chat {
	public static void main(String []z){
		marco m = new marco("Chat de Harry");
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class marco extends JFrame implements Runnable{
	/**
	 * la versión serial es 28
	 */
	private static final long serialVersionUID = 28;
	private static String ipServer = "192.168.1.33";
	private String nombre, textoCapturado_jtfChEs, contText= "";
	private byte l=0;
	private JButton jbEnviar,jbSalir;
	private JPanel jpConectadosSalir,jp_bl_PantallaChat;
	private JTextArea jtaPantUsuaConectados;
	private JTextField jtfChatEscribo;
	private JTextPane jtpChatLeer;
	private JScrollPane jspChatLeer;
	private JPanel jpNorth;
	private JComboBox<String> jcbAmigosEnLine = new JComboBox<String>();
	private Thread thread_EnviaMensaje;
	private HashMap<String, String> hmListaUsuarios = new HashMap<String, String>();
	private Mensaje mensajeEnviar;
	
	public marco(String titulo){
		super(titulo);
		//pedimos un nombre
		nombre = JOptionPane.showInputDialog("Dime tu Nombre");
		if(nombre.equals("")||nombre.equals(null)) System.exit(0);
		
		addWindowListener(new EventoEnLinea());
		
		setLayout(new BorderLayout());
		setBounds(500,150,450,300);
		setResizable(false);
		
		// subproceso para recibir los mensajes
		thread_EnviaMensaje = new Thread(this);
		//lo iniciamos inmediatamente 
		thread_EnviaMensaje.start();

		addNorth_MenuBar(nombre);
		addCenter_Pantalla();
		addEast_Botones();
		setVisible(true);
	}
	
	private void addNorth_MenuBar(String nombre){
		jpNorth = new JPanel(new BorderLayout());
		
		JMenuBar jmbNombre = new JMenuBar();
		JMenu jmNombre = new JMenu("|   " + nombre + "   |");
		JMenuItem jmiDesconectar = new JMenuItem("Desconectar");
		jmiDesconectar.addActionListener(new AccionBotonesTeclados());
		jmNombre.add(jmiDesconectar);
		jmbNombre.add(jmNombre);
		
		jpNorth.add(jmbNombre,BorderLayout.WEST);
		
		JMenuBar jmbMenu = new JMenuBar();
		JMenu jmConfiguraciones = new JMenu("Ajustes");
		
		JMenu jmContactos = new JMenu("Contacto");
		JMenu jmEstilos = new JMenu("Estilos");
		
		JMenu jmLetras = new JMenu("Letra");
		JMenu jmBotones = new JMenu("Botones");
		
		jmEstilos.add(jmLetras);
		jmEstilos.add(jmBotones);
		
		jmConfiguraciones.add(jmContactos);
		jmConfiguraciones.add(jmEstilos);
		
		jmbMenu.add(jmConfiguraciones);
		
		jpNorth.add(jmbMenu, BorderLayout.EAST);
		jpNorth.add(jcbAmigosEnLine,BorderLayout.CENTER);
		
		add(jpNorth,BorderLayout.NORTH);
	}
	
	/**
	 * Metodo que tendra que ver con los botones 
	 */
	private void addEast_Botones(){
		jpConectadosSalir = new JPanel(new BorderLayout());
		jtaPantUsuaConectados = new JTextArea();
		
		jbSalir = new JButton("Desconectar");
		
		jpConectadosSalir.add(jtaPantUsuaConectados, BorderLayout.CENTER);
		jpConectadosSalir.add(jbSalir, BorderLayout.SOUTH);
		
		jbSalir.addActionListener(new AccionBotonesTeclados());
		
		this.getContentPane().add(jpConectadosSalir,BorderLayout.EAST);
	}
	
	/**Aqui simplemente nos encargamos de 
	 * las pantralla de los mensajes de chat
	 */
	private void addCenter_Pantalla(){
		jp_bl_PantallaChat = new JPanel(new BorderLayout());
		JPanel jpChatEscribirEnviar = new JPanel(new BorderLayout());
		
		jtpChatLeer = new JTextPane();
		jspChatLeer = new JScrollPane(jtpChatLeer);
		
		jtfChatEscribo = new JTextField();
		jtfChatEscribo.addKeyListener(new AccionBotonesTeclados());
		jtfChatEscribo.setBackground(Color.YELLOW);
		
		jtpChatLeer.setEditable(false);
		jspChatLeer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		jbEnviar = new JButton("Enviar");
		
		jpChatEscribirEnviar.add(jtfChatEscribo, BorderLayout.CENTER);
		jpChatEscribirEnviar.add(jbEnviar, BorderLayout.EAST);
		
		jbEnviar.addActionListener(new AccionBotonesTeclados());

		jp_bl_PantallaChat.add(jspChatLeer, BorderLayout.CENTER);
		jp_bl_PantallaChat.add(jpChatEscribirEnviar, BorderLayout.SOUTH);
	
		add(jp_bl_PantallaChat,BorderLayout.CENTER);
		
	}
	
	@SuppressWarnings("unused")
	private void addSouth_BarraChat(){
		
	}
	
	private boolean checkTextVacio(String texto){
		if(texto == null || texto.equalsIgnoreCase("")){
			return false;
		}else{
			return true;
		}
	}
	
	private void concatena(boolean txtvacio){
		if(l<1 && txtvacio){
			contText = contText + textoCapturado_jtfChEs;
			jtpChatLeer.setText(" <] "+ contText +"\n");
			l++;
		}else if(l >= 1 && txtvacio){
			contText = contText +"\n <] "+ textoCapturado_jtfChEs;
			jtpChatLeer.setText(" <] "+ contText +"\n "); 
		}
		
		jtpChatLeer.setFont(new Font(Font.SERIF,Font.ITALIC,14));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socketChatIPRemota;
		ServerSocket serverSChat;
		try {
			// bucle infinito para que siempre este recibiendo los mensajes
			for(;;){
				// iniciamos un ServerSocket con el puerto indicado para su escucha
				serverSChat = new ServerSocket(2882);
				// acceptamos esa conexion y se la pasamos a un Socket
				socketChatIPRemota = serverSChat.accept();
				//creamos un flujo de entrada y al constructor
				ObjectInputStream oosRecibo = new ObjectInputStream(socketChatIPRemota.getInputStream());
				Mensaje mensajeDestinatario = (Mensaje) oosRecibo.readObject();
				
				//cerramos todos los distintos flujos
				oosRecibo.close();
				socketChatIPRemota.close();
				serverSChat.close();
				
				hmListaUsuarios = mensajeDestinatario.getUsuario();
				
				jcbAmigosEnLine.removeAllItems();
				
				for(String nombres: mensajeDestinatario.getListaNombres())
					jcbAmigosEnLine.addItem(nombres);
									
				jcbAmigosEnLine.removeItem(nombre);
				
				System.out.println(mensajeDestinatario.getNombre()+" : "+mensajeDestinatario.getMensaje());
				if(checkTextVacio(mensajeDestinatario.getMensaje())){
					if(mensajeDestinatario.getMensaje().equalsIgnoreCase("Conectado") || 
							mensajeDestinatario.getMensaje().equalsIgnoreCase("Desconectado")){
						String str = mensajeDestinatario.getNombre()+" : "+mensajeDestinatario.getMensaje();
						jtaPantUsuaConectados.append(str + "\n");
						
					}else{
						textoCapturado_jtfChEs = mensajeDestinatario.getNombre()+" : "+mensajeDestinatario.getMensaje();
						concatena(checkTextVacio(textoCapturado_jtfChEs));
					}
					
				}
				
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**Un solo metodo para que luego sea llamado en el evento 
	 * de los botones y en el de el teclado(enter)
	 */
	private void unoSolo(){
		textoCapturado_jtfChEs = jtfChatEscribo.getText();
		jtfChatEscribo.setText("");	
		concatena(checkTextVacio(textoCapturado_jtfChEs));
		
		try {
			//se crea la conexion con la direccion ip y puerto
			//esto es para conectarnos con el servidor
			Socket socketChat = new Socket(ipServer,2828);
			
			String ipDestino = null;
			
			for(Map.Entry<String, String> ipDesti: hmListaUsuarios.entrySet()){
				if(ipDesti.getValue().equalsIgnoreCase(hmListaUsuarios.get(jcbAmigosEnLine.getSelectedItem()))){
					ipDestino = hmListaUsuarios.get(jcbAmigosEnLine.getSelectedItem());
				}
				
			}
			
			mensajeEnviar = new Mensaje(nombre,textoCapturado_jtfChEs,ipDestino,null);
			
			ObjectOutputStream oosEnvio = new ObjectOutputStream(socketChat.getOutputStream());
			//escribimos el siguiente objeto "mensajeEnviar" (sin comilla)
			//para enviarlo por el flujo OOS que usaremos con la conexion
			//ip y puerto que hemos establecidos
			oosEnvio.writeObject(mensajeEnviar);
			
			//siempre cerrar los flujos que han abierto
			oosEnvio.close();
			socketChat.close();
			
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	
	private void estadoConexion(String esta){
		Socket socketChat;
		try {
			socketChat = new Socket(ipServer,2828);
			mensajeEnviar = new Mensaje(nombre,esta,null);
			ObjectOutputStream oosEnvio = new ObjectOutputStream(socketChat.getOutputStream());
			oosEnvio.writeObject(mensajeEnviar);
			oosEnvio.close();
			socketChat.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	private class AccionBotonesTeclados extends KeyAdapter implements ActionListener{

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == 10){
				unoSolo();
				
			}else if(e.getKeyCode() == 27){
				estadoConexion("Desconectado");
				System.exit(0);
				
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == jbEnviar){
				unoSolo();
				
			}else if(e.getActionCommand().equalsIgnoreCase(jbSalir.getText())){
				estadoConexion("Desconectado");
				System.exit(0);
				
			}
		}
	}
	
	private class EventoEnLinea extends WindowAdapter{
		public void windowOpened(WindowEvent e){
			estadoConexion("Conectado");
			
		}
		
		public void windowClosing(WindowEvent e){
			estadoConexion("Desconectado");
			
		}
		
	}
	
}      

class Mensaje implements Serializable{
	
	private static final long serialVersionUID = 28;
	private String nombre, destinatario, mensaje, miIP;
	private ArrayList<String> ListaIPs,nombres;
	private HashMap<String, String> usuario;
	
	public Mensaje(){	
	}
	
	public Mensaje(String nombre, String mensaje, String selectedItem){
		this(nombre,mensaje,selectedItem,"1.1.1.1");	
	}
	
	public Mensaje(String nombre, String mensaje, String selectedItem,String miIP) {
		// TODO Auto-generated constructor stub
		this(nombre,mensaje,selectedItem,miIP,null);		
	}
	
	public Mensaje(String nombre, String mensaje, String selectedItem,String miIP, ArrayList<String> IPs) {
		// TODO Auto-generated constructor stub
		setNombre(nombre);
		this.nombre = getNombre();
		
		setMensaje(mensaje);
		this.mensaje = getMensaje();
		
		setDestinatario(selectedItem);
		destinatario = getDestinatario();
		
		setMiIP(miIP);
		this.miIP = getMiIP();
		
		setListaIPs(IPs);
		this.ListaIPs = getListaIPs();

	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMiIP() {
		return miIP;
	}

	public void setMiIP(String miIP) {
		this.miIP = miIP;
	}

	public ArrayList<String> getListaIPs() {
		return ListaIPs;
	}

	public void setListaIPs(ArrayList<String> IPs) {
		ListaIPs = IPs;
	}

	public ArrayList<String> getListaNombres() {
		return nombres;
	}

	public void setListaNombres(ArrayList<String> nombres) {
		this.nombres = nombres;
	}
	
	public HashMap<String, String> getUsuario() {
		return usuario;
	}

	public void setUsuario(HashMap<String, String> usuario) {
		this.usuario = usuario;
	}

}