
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class InterfazDeUsuario<Reloj> extends JFrame implements ActionListener{
	
	 //---ESTO AQUI FUNCIONA DE MANERA QUE SOLO PUEDA EJECUTAR ESTA PARTE------
	 //---DEL PROGRAMA Y TRABAJE CON ESTA SESSION SOLAMENTE ---*MODULARIDAD*---
//	public static void main(String[]x){
//		InterfazDeUsuario<?> m = new InterfazDeUsuario<Object>();
//		m.setVisible(true);
//		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
	private Dimension tamanoPantalla = mipantalla.getScreenSize();
//	Image miIcono = mipantalla.getImage("img/iconBank.png");
	
	//---------------------------------------------------------------------------------
//	private URL ruta_externa = InterfazDeUsuario.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	
	//---------------------------------------------------------------------------------
	
	int alturaPantalla = tamanoPantalla.height;
	int anchoPantalla = tamanoPantalla.width;
	
	private JButton jbBalance, jbDeposito, jbRetiro, jbTeclaNum;
	private boolean retiro;
	private JTextField jtfPantallaNum;
	private double saldoEnBanco, saldoFire, saldoBancario;
	private String queHago;
	private JTextArea jtaPantallaPrincipal;
	private JTextField jtfPantallaInferior;
	private JMenuItem jmiSession, jmiAyuda, jmiAcerca_de, jmiAjustes;
	private String nombre;
	private String apellido;
	private String usuario;
	private String numCuenta;
	private String contrasena;
	private String tipoCuenta;
	
	public InterfazDeUsuario(String usuario){
		this(null,null,usuario,"",0,null,null);
		
		setTitle("Bank ATM");
//		Image miIcono = mipantalla.getImage("src/img/iconBank.png");
//		URL ruta_externa = MarcoAcceso.class.getResource("iconBank.png");
//		Image miIcono = mipantalla.getImage(ruta_externa);
//		setIconImage(miIcono);
		setBounds(alturaPantalla/3,anchoPantalla/4,650,300);
		//setBounds(300,150,650,300);			
		addBotonesWest();
		addPantallaCenter();
		addMenuBarNorth(null,null,usuario);	
	}
	
	public InterfazDeUsuario(String nombre, String apellido, String usuario, String numCuenta, double saldoInicial,
			String contrasena, String tipoCuenta){
		setTitle("DMG Bank");
		

		this.setResizable(false);
//		setIconImage(miIcono);
		setBounds(alturaPantalla/3,anchoPantalla/4,650,300);
		
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.numCuenta = numCuenta;		
		saldoEnBanco = saldoInicial;
		this.contrasena = contrasena;
		this.tipoCuenta = tipoCuenta;
		
		addBotonesWest();
		addPantallaCenter();
		addMenuBarNorth(nombre,apellido,usuario);	

	}	

	private void addBotonesWest(){
		
		JPanel paraBotones = new JPanel(new GridLayout(2,1));
		
		JPanel jpBDR = new JPanel(new GridLayout(3,1,5,5));
		
		jbBalance = new JButton("Balance de Cuenta");
		jbDeposito = new JButton("Hacer Deposito");
		jbRetiro = new JButton("Hacer Retiro");	
		
		jbBalance.addActionListener(this);
		jbDeposito.addActionListener(this);
		jbRetiro.addActionListener(this);
		
		jbBalance.setBackground(Color.CYAN);
		jbDeposito.setBackground(new Color(251,177,0));
		jbRetiro.setBackground(new Color(87,127,243));
		
		jbBalance.setActionCommand("");
		jbDeposito.setActionCommand("");
		jbRetiro.setActionCommand("");
		
		jpBDR.add(jbBalance);
		jpBDR.add(jbDeposito);
		jpBDR.add(jbRetiro);
			
		paraBotones.add(jpBDR);	
		
		JPanel jpPantallaNumeros = new JPanel(new BorderLayout());
		JPanel jpTeclaNum = new JPanel(new GridLayout(4,3,5,5));
		
		jtfPantallaNum = new JTextField(8);
		jtfPantallaNum.setHorizontalAlignment(JTextField.CENTER);
		
		for(byte i=0; i<12; i++){
			if(i<9){
				botones(""+(i+1), jpTeclaNum, this);
			}else if(i == 9){
				botones(".", jpTeclaNum, this);
			}else if(i == 10){
				botones("0", jpTeclaNum, this);
			}else if(i == 11){
				botones("Borrar", jpTeclaNum, this);
			}
		}
		
		jpPantallaNumeros.add(jtfPantallaNum,BorderLayout.NORTH);
		jpPantallaNumeros.add(jpTeclaNum,BorderLayout.CENTER);
		
		paraBotones.add(jpPantallaNumeros);
		
		add(paraBotones, BorderLayout.WEST);
	}
	
	private void addPantallaCenter(){
		JPanel paraPantallas = new JPanel(new BorderLayout());
		String lol = null;
		jtaPantallaPrincipal = new JTextArea();
		jtfPantallaInferior = new JTextField();
		
		lol = "\n\n\tBienvenido "+nombre+ " "+apellido+"\n\t\ta Bank DMG\n\n\t ... tu banco favorito :)";
	
		jtaPantallaPrincipal.setText(lol);
		jtaPantallaPrincipal.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
		
		jtaPantallaPrincipal.setEditable(false);
		jtfPantallaInferior.setEditable(false);

		ActionListener Reloj2 = new ActionListener(){
			public void actionPerformed(ActionEvent a){
				Date ahora = new Date();
				jtfPantallaInferior.setFont(new Font(Font.MONOSPACED,Font.ITALIC,15));
				jtfPantallaInferior.setText(""+ahora);
			}
		};
		new Timer(1000,Reloj2).start();
		
		JScrollPane scrollPP = new JScrollPane(jtaPantallaPrincipal);
//		paraPantallas.add(jtaPantallaPrincipal, BorderLayout.CENTER);
		paraPantallas.add(scrollPP, BorderLayout.CENTER);
		paraPantallas.add(jtfPantallaInferior, BorderLayout.SOUTH);
		
		add(paraPantallas, BorderLayout.CENTER);
	}
	
	private void addMenuBarNorth(String nombre, String apellido, String usuario){
		JPanel jpNorth = new JPanel(new BorderLayout());
		
		JMenuBar menuBarra = new JMenuBar();
//		System.out.println(usuario);
		JMenu jmUsuario = new JMenu(usuario);
		JMenu jmAyuda = new JMenu("Ayuda");
		
		jmiSession = new JMenuItem("Cerrar Sesi�n");
		jmiAjustes = new JMenuItem("Ajustes");
		
		jmiAyuda = new JMenuItem("Ayuda");
		jmiAcerca_de = new JMenuItem("Acerca de");	
		
		//_________limpiar el action comand______________
		jmiSession.setActionCommand("");
		jmiAjustes.setActionCommand("");
		jmiAyuda.setActionCommand("");
		jmiAcerca_de.setActionCommand("");
		
		//___________ponerlos a la escucha________________
		jmiSession.addActionListener(this);
		jmiAjustes.addActionListener(this);
		jmiAyuda.addActionListener(this);
		jmiAcerca_de.addActionListener(this);
		
		jmUsuario.add(jmiAjustes);
		jmUsuario.addSeparator();
		jmUsuario.add(jmiSession);		
		
		jmAyuda.add(jmiAyuda);
		jmAyuda.addSeparator();
		jmAyuda.add(jmiAcerca_de);
		
		
		menuBarra.add(jmUsuario);
		menuBarra.add(jmAyuda);
		menuBarra.setBackground(Color.green);
		
		jpNorth.add(menuBarra,BorderLayout.WEST);
		
		
		jpNorth.setBackground(Color.GREEN);
		add(jpNorth,BorderLayout.NORTH);
	}
	
	private void botones(String s,JPanel panel,ActionListener accionador){
		//if(s.equalsIgnoreCase(""))
		jbTeclaNum = new JButton(s);	
		jbTeclaNum.addActionListener(accionador);
		panel.add(jbTeclaNum);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String text = e.getActionCommand();
		if(text.equalsIgnoreCase("borrar")){
			jtfPantallaNum.setText("");
			text = "";
		}
		jtfPantallaNum.setText(jtfPantallaNum.getText() + text);
		
		if(e.getSource() == jbBalance){
			
			this.jtaPantallaPrincipal.setText("\n\n\tSu Balance Actual es de B/. "+saldoEnBanco);
						
			
			jtfPantallaNum.setBackground(Color.CYAN.brighter().brighter());
			
			jtfPantallaInferior.setBackground(Color.CYAN.brighter().brighter());
			new EditaSaldoUsuario(nombre, apellido, usuario, numCuenta, saldoEnBanco,
					 contrasena, tipoCuenta);
			
		}else if(e.getSource() == jbDeposito){
			queHago = "Depositar";
			
			this.transaccion(queHago);
			
			this.jtaPantallaPrincipal.setText("\n\n\tHas hecho un Deposito de B/. "+saldoFire);
			
			jtfPantallaNum.setText("");
			
			jtfPantallaNum.setBackground(new Color(251,177,0));
			jtfPantallaInferior.setBackground(new Color(251,177,0));
			new EditaSaldoUsuario(nombre, apellido, usuario, numCuenta, saldoEnBanco,
					 contrasena, tipoCuenta);
			
		}else if(e.getSource() == jbRetiro){
			queHago = "Retirar";
			
			this.transaccion(queHago);
			
			if(retiro){
				this.jtaPantallaPrincipal.setText("\n\n\tHas hecho un Retiro de B/. "+saldoFire);
			}else{
				this.jtaPantallaPrincipal.setText("\n\n\tSaldo a Retirar Superior a \n\t    su Balance Actual ");
			}	
			
			jtfPantallaNum.setText("");
			
			jtfPantallaNum.setBackground(new Color(87,127,243));
			jtfPantallaInferior.setBackground(new Color(87,127,243));
			new EditaSaldoUsuario(nombre, apellido, usuario, numCuenta, saldoEnBanco,
					 contrasena, tipoCuenta);
			
		}else if(jmiSession == e.getSource()){
			new EditaSaldoUsuario(nombre, apellido, usuario, numCuenta, saldoEnBanco,
					 contrasena, tipoCuenta);
			this.dispose();
			Login_main.main(null);
			
		}else if(jmiAyuda == e.getSource()){
			jtaPantallaPrincipal.setText(ayuda + ayuda1 + ayuda2);
			jtfPantallaNum.setBackground(Color.lightGray);
			jtfPantallaInferior.setBackground(Color.WHITE);
			
		}else if(jmiAcerca_de == e.getSource()){
			jtaPantallaPrincipal.setText(acerca_de);
			jtfPantallaNum.setBackground(Color.WHITE);
			jtfPantallaInferior.setBackground(Color.lightGray);
			
		}else if(jmiAjustes == e.getSource()){
			@SuppressWarnings("unused")
			EditarCliente ec = new EditarCliente(usuario, true);
			
		}

		
	}
	
	private String ayuda = "\t Yo te Ayudo!!.. :)\n\n  	Esta herramienta esta dise�ada para\n"
			     + " un facil y sensillo uso, en tan solos unos\n"
			     + " pasos podras haber hecho tu retiro o\n"
			     + " deposito y podras consultar tu saldo actual\n"
			     + " en la banca.",
		ayuda1 = "\n\n BALANCE DE CUENTA : esta opci�n te permite\n"
				 + " consultar el saldo Actual."
				 + "\n\n HACER DEPOSITO : esta opci�n permite \n"
				 + " depositar el saldo que desees a tu cuenta \n"
				 + " Bancaria."
				 + "\n\n HACER RETIRO : esta opci�n te permite \n"
				 + " hacer un retiro, no mas que tu saldo \n"
				 + " bancario.",
		ayuda2 = "\n\n\t **Al Digitar Saldo** \n"
				 + " Al digitar saldo deber tener en cuenta que\n"
				 + " no podras utilizar coma (,) si no solo un\n"
				 + " punto (.) para representar el valor centimo\n"
				 + " de tu transacci�n; de la siguiente manera:\n"
				 + "   >   280.14  \n"
				 + "   >  3957.95  \n"
				 + "   >    61.04  \n";
	
	private String acerca_de = "\n\n\t\t~~ DMG BANK  ~~\n\n"
			+ " Bank DMG es un sistema bancario dise�ado\n"
			+ " para transacciones de retiro, deposito y\n"
			+ " y consulta de saldo. ";	
	
	private double transaccion(String s){
		try{
			saldoFire = Double.parseDouble(jtfPantallaNum.getText());
			JOptionPane.showMessageDialog(this, "Estas por "+queHago+ " B/. "+saldoFire, "Bank ATM", JOptionPane.INFORMATION_MESSAGE);
			
		}catch(Exception x){
			JOptionPane.showMessageDialog(this, "Asegurate solo tener\n un punto y no usar coma", "Error de Valor", JOptionPane.ERROR_MESSAGE);
			saldoFire = 0;
			
		}
		
		if(s.equalsIgnoreCase("depositar")){
			saldoEnBanco += saldoFire;

		}else if(s.equalsIgnoreCase("retirar")){
			if(saldoEnBanco < saldoFire){
				JOptionPane.showMessageDialog(this, "Saldo insuficiente ", "Error al Retirar", JOptionPane.ERROR_MESSAGE);
				retiro = false;
				
			}else{
				saldoEnBanco -= saldoFire;
				retiro = true;
				
			}
		}
		/* le asigana a la variable saldoBancario el saldo actual del usuario
		 * para que pueda llegar a ser retornado si se le es invocado de alguna 
		 * otra parte del programa.
		 */
		saldoBancario = saldoEnBanco;
		return saldoEnBanco;
	}
	
	public double getSaldoBancario(){
		return saldoBancario;
	}
	
	class EditaSaldoUsuario{
		ArrayList <ClientesDB> lista = new ArrayList<ClientesDB>();
		String nombre, apellido, usuario;
		int numCuenta;
		double saldoInicial;
		String contrasena, tipoCuenta;
		int index;
//		private boolean encontrado = false;
		
		public EditaSaldoUsuario(String nombre, String apellido, String usuario, String numCuenta, double saldoInicial,
				String contrasena, String tipoCuenta){
			
			index = determinaUsuario(usuario);
			
			reemplazar(index, nombre, apellido, usuario, numCuenta, saldoInicial, contrasena, tipoCuenta );
		}
		
		private int determinaUsuario(String usuarioModificar){
			int i = 0;
			try {

				ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
				
				@SuppressWarnings("unchecked")
				ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();	
				
				leer_fichero.close();
				
				ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
				
				personal_Recuperado.toArray(listaNueva);
//				jtfNombre.setText("setendo desde usuariModfi");
				
				for(ClientesDB e: listaNueva){
//					System.out.println(e.getApellido()); //Solo para verificar 
//					lista.add(e);
					
					if(e.getUsuario().equals(usuarioModificar)){
						
//						encontrado = true;
						break; 
					} i++;
					
				}			
			}catch (Exception e1) { }
			return i;
		}
		
		@SuppressWarnings("unchecked")
		private void reemplazar(int index,String nombre, String apellido, String usuario, String numCuenta, double saldoInicial,
				String contrasena, String tipoCuenta){
			try {
				ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
				ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
				leer_fichero.close();
				
				personal_Recuperado.remove(index);
				
				ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
				personal_Recuperado.toArray(listaNueva);
				
				for(ClientesDB e: listaNueva){
					lista.add(e);
				}
				
				lista.add(new ClientesDB(nombre, apellido, usuario, numCuenta, saldoInicial, contrasena, tipoCuenta));
			}catch (Exception e1) {	}
			
			try{	
				ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("clientesBaseDatos.txt"));
				escribiendo_fichero.writeObject(lista);
				escribiendo_fichero.close();
				
			}catch(Exception e){ }	
			
		}

		
		
	}
	
	
}
