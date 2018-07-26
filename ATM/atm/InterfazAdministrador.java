

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class InterfazAdministrador extends JFrame implements ActionListener{
//	public static void main(String[] args) {
//	// TODO Auto-generated method stub
//		InterfazAdministrador m = new InterfazAdministrador();
//		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");
	
	URL ruta_externa = MarcoAcceso.class.getResource("iconBank.png");

	//---------------------------------------------------------------------------------
//	private URL ruta_externa = InterfazAdministrador.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	//---------------------------------------------------------------------------------
	
	private Dimension tamanoPantalla = mipantalla.getScreenSize();
	private int alturaPantalla = tamanoPantalla.height, anchoPantalla = tamanoPantalla.width;
	
	private JButton jbAgregarCliente, jbEditarCliente, jbReporteUsuarios, jbEliminarUsuario;
	private JPanel jpBotones, jpSouth;
	private JMenuItem jmiSession, jmiAyuda, jmiAcerca_de;
	private AgregarCliente agregar;
	private ReporteUsuarios ru;//= new ReporteUsuarios();
	public InterfazAdministrador() {
		// TODO Auto-generated constructor stub		
		super("DMG Bank - Sistema Administrador");

//		setBounds(alturaPantalla/2,anchoPantalla/4,480,240);
		setBounds(alturaPantalla/2,anchoPantalla/4,670,270); // con tabla activa
//		setIconImage(miIcono);	
		
		addBotones();
		add(new JLabel("Tabla de Usuarios Vacida"));
		add(ru = new ReporteUsuarios(), BorderLayout.CENTER);
		
		areaSouth();
		addMenuBarNorth();
			 
	}
	

	
	JTextField fecha = new JTextField(30);
	private void addMenuBarNorth(){
		JPanel jpNorth = new JPanel(new BorderLayout());
		
		JMenuBar menuBarra = new JMenuBar();
		
		JMenu jmUsuario = new JMenu("Administrador");
		JMenu jmAyuda = new JMenu("Ayuda");
		
		jmiSession = new JMenuItem("Cerrar Sesi�n");
		
		jmiAyuda = new JMenuItem("Ayuda");
		jmiAcerca_de = new JMenuItem("Acerca de");	
		
		//_________limpiar el action comand______________
		jmUsuario.setActionCommand("");
		jmiAcerca_de.setActionCommand("");
		
		//___________ponerlos a la escucha________________
		jmiSession.addActionListener(this);
		jmiAyuda.addActionListener(this);
		jmiAcerca_de.addActionListener(this);
		
		jmUsuario.add(jmiSession);
		
		jmAyuda.add(jmiAyuda);
		jmAyuda.addSeparator();
		jmAyuda.add(jmiAcerca_de);
		
		menuBarra.add(jmUsuario);
		menuBarra.add(jmAyuda);
		menuBarra.setBackground(Color.green);
		
		//-----------------------------Reloj Fecha------------------------------------
		
		ActionListener Reloj2 = new ActionListener(){
			public void actionPerformed(ActionEvent a){
				Date ahora = new Date();
				fecha.setText(""+ahora);
			}
		};
		new Timer(1000,Reloj2).start();
		fecha.setFont(new Font(Font.MONOSPACED,Font.ITALIC,13));
		fecha.setEditable(false);
		fecha.setBackground(Color.GREEN);
		//----------------------------------------------------------------------------
		jpNorth.add(fecha,BorderLayout.EAST);
		jpNorth.add(menuBarra,BorderLayout.WEST);	
		
		jpNorth.setBackground(Color.GREEN);
		add(jpNorth,BorderLayout.NORTH);
	}
	
	private void addBotones(){
		jpBotones = new JPanel(new GridLayout(4,1,5,5));
		
		jbAgregarCliente = new JButton("Agregar Clientes");
		jbEditarCliente = new JButton("Editar Clientes");
		jbReporteUsuarios = new JButton("Actualizar Tabla");
		jbEliminarUsuario = new JButton("Eliminar Usuarios");
		
		jbAgregarCliente.addActionListener(this);
		jbEditarCliente.addActionListener(this);
		jbReporteUsuarios.addActionListener(this);
		jbEliminarUsuario.addActionListener(this);
		
		jpBotones.add(jbAgregarCliente);
		jpBotones.add(jbEditarCliente);
		jpBotones.add(jbReporteUsuarios);
		jpBotones.add(jbEliminarUsuario);
		
		jpBotones.setBackground(Color.CYAN);
		add(jpBotones,BorderLayout.WEST);
		
	}

	private void areaSouth(){
		jpSouth = new JPanel();
		jpSouth.setBackground(Color.RED);
		
		add(jpSouth,BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		if(jbAgregarCliente == a.getSource()){
			agregar = new AgregarCliente();
			agregar.setVisible(true);
			setSize(670,271);			
			
		}else if(jbEditarCliente == a.getSource()){
			new EditarCliente(JOptionPane.showInputDialog("Usuario a Modificar"));
			setSize(670,272);
			
		}else if(jbReporteUsuarios == a.getSource()){	
			remove(ru);
			add(ru = new ReporteUsuarios(), BorderLayout.CENTER); // es mejor asi			
			setSize(this.getWidth(), this.getHeight()+1);	
			if(this.getHeight() > 273) setSize(670,272); 
//			setSize(670,271);
			
		}else if(jbEliminarUsuario == a.getSource()){
//			setSize(670,270);
			new EliminarClientes(JOptionPane.showInputDialog("Usuario a Eliminar"));
			//------auto actualizacion--------------------------------
			remove(ru);
			add(ru = new ReporteUsuarios(), BorderLayout.CENTER);
			setSize(670,269);
			
		}else if(jmiSession == a.getSource()){
			if(agregar instanceof AgregarCliente){
				agregar.dispose();
				
			}
			this.dispose();
			Login_main.main(null);
			
		}else if(jmiAyuda == a.getSource()){
			JOptionPane.showMessageDialog(this, ayuda, "Acerca de",JOptionPane.QUESTION_MESSAGE);
			
		}else if(jmiAcerca_de == a.getSource()){
			JOptionPane.showMessageDialog(this, acerca_de, "Acerca de",JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	private String ayuda = "Mira que...  \n\n"
			+ " En esta sesion se proporciona un control\n"
			+ " bantante responsable y ya que podremos\n"
			+ " editar los usuario a el antojo del\n"
			+ " administrador.\n"
			+ " La sesion con lleva cuatro botones\n"
			+ " principales:\n"
			+ " AGREGAR CLIENTES: aqui podremos a�adir un\n"
			+ "  cliente nuevo.\n"
			+ "	EDITAR CLIENTES: con esta opci�n podremos\n"
			+ "  editar un usuario/cliente ya existente.\n"
			+ " ACTUALIZAR TABLA: esta opci�n nos permite\n"
			+ "  refrescar la tabla, para poder visualizar\n"
			+ "  los usuarios que fueron editados, eliminados\n"
			+ "  o agregados.\n"
			+ " ELIMINAR USUARIOS: una de las opciones mas\n"
			+ "  delicadas de la administraci�n es la de\n"
			+ "  poder eliminar un cliente.\n";
	
	private String acerca_de = "\t\t~~ DMG BANK  ~~\n\n"
			+ " Bank DMG es un sistema bancario dise�ado\n"
			+ " para transacciones de retiro, deposito y\n"
			+ " y consulta de saldo. \n"
			+ " En esta session de administrador podra\n"
			+ " tener el control total de todas las\n"
			+ " cuentas y llevar acabo su correcta\n"
			+ " administraci�n.";
	
}
