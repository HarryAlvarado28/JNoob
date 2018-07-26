

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AgregarCliente extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 28;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		AgregarCliente h = new AgregarCliente();
//		h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");
	//---------------------------------------------------------------------------------
//	private URL ruta_externa = AgregarCliente.class.getResource("iconBank.png");
//	private Image miIcono = mipantalla.getImage(ruta_externa);
	
	//---------------------------------------------------------------------------------

	private Dimension tamanoPantalla = mipantalla.getScreenSize();
	private int alturaPantalla = tamanoPantalla.height, anchoPantalla = tamanoPantalla.width;
	
	private JLabel jlNombre, jlApellido, jlUsuario, jlNumCuenta, jlSaldoInicial, jlContrasena, jlTipoCuenta, jlMensaje, jlContrasenaConfirma;
	private JTextField jtfNombre, jtfApellido, jtfNumCuenta, jtfSaldoInicial, jtfContrasena, jtfContrasenaConfirma;
	private JButton jbAdd, jbCancelar, jbCerrar;
	private JPanel jpInformacion, jpDatos, jpBotones, jpBtMsj;	
	private String nombre, apellido, usuario, contrasena, tipoCuenta;
	private JComboBox<String> jcbTipoCuenta, jcbUsuario;
	private double saldoInicial;
	private String numCuenta;
	private ArrayList <ClientesDB> lista = new ArrayList<ClientesDB>();
	private boolean permitidoContrasena, permitidoNomApell;
	
	public AgregarCliente() {
		// TODO Auto-generated constructor stub
		setBounds(alturaPantalla/2,anchoPantalla/4,340,310);
//		setIconImage(miIcono);	
		setTitle("Agregar Cliente");
		addInformacion();
		setResizable(false);
	}
	
	@SuppressWarnings("deprecation")
	private void addInformacion(){
		jpInformacion = new JPanel(new GridLayout(8,1));
		jpDatos = new JPanel(new GridLayout(8,1,10,13));
		jpBotones = new JPanel();
		jpBtMsj = new JPanel(new BorderLayout());
		
		jbAdd = new JButton("add");
		jbCancelar = new JButton("Cancelar");
		jbCerrar = new JButton("Cerrar");
		
		jbAdd.addActionListener(this);
		jbCancelar.addActionListener(this);
		jbCerrar.addActionListener(this);
		
		jlMensaje = new JLabel("Se ha agregado Satisfactoriamente!!...");
		
		jlNombre = new JLabel(" Nombre: ");
		jlApellido = new JLabel(" Apellido: ");
		jlUsuario = new JLabel(" Usuario: ");
		jlNumCuenta = new JLabel(" Numero de Cuenta: ");
		jlSaldoInicial = new JLabel(" Saldo Inicial: ");
		jlContrasena = new JLabel(" Contrase�a: ");
		jlContrasenaConfirma = new JLabel(" Confirmar Contr.");
		jlTipoCuenta = new JLabel(" Tipo Cuenta: ");
		
		jtfNombre = new JTextField(15);
		jtfApellido = new JTextField(15);
		jcbUsuario = new JComboBox<String>();
		jtfNumCuenta = new JTextField(15);
		jtfSaldoInicial = new JTextField(12);
		jtfContrasena = new JTextField(12);
		jtfContrasenaConfirma = new JTextField(12);
		
		jtfNombre.getDocument().addDocumentListener(new DocumentNombre());
		jtfApellido.getDocument().addDocumentListener(new DocumentApellido());
		jtfContrasena.getDocument().addDocumentListener(new DocumentContrasena());	
		jtfContrasenaConfirma.getDocument().addDocumentListener(new DocumentContraConfirmar());
		
		Date d = new Date();
		jtfNumCuenta.setText(d.getYear()+"-"+d.getMonth()+d.getDay()+"-"+d.getHours()+d.getMinutes()+d.getSeconds());
//		jtfNumCuenta.setText("Asignacion Automatica");
		jtfNumCuenta.setHorizontalAlignment(JTextField.CENTER);
		jtfNumCuenta.setEditable(false);
		
		//------------------JComboBox-----------------
		jcbTipoCuenta = new JComboBox<String>();
		
			jcbTipoCuenta.addItem("Ahorro");
			jcbTipoCuenta.addItem("Corriente");
		//---------------------------------------------	
			
		
		jpInformacion.add(jlNombre);
		jpInformacion.add(jlApellido);
		jpInformacion.add(jlUsuario);
		jpInformacion.add(jlNumCuenta);
		jpInformacion.add(jlSaldoInicial);
		jpInformacion.add(jlContrasena);
		jpInformacion.add(jlContrasenaConfirma);
		jpInformacion.add(jlTipoCuenta);	
			
		jpDatos.add(jtfNombre);
		jpDatos.add(jtfApellido);
		jpDatos.add(jcbUsuario);
		jpDatos.add(jtfNumCuenta);
		jpDatos.add(jtfSaldoInicial);
		jpDatos.add(jtfContrasena);
		jpDatos.add(jtfContrasenaConfirma);
		jpDatos.add(jcbTipoCuenta);
		
		jpBotones.add(jbAdd);
		jpBotones.add(jbCancelar);
		
		jpBtMsj.add(jpBotones, BorderLayout.SOUTH);
		
		add(jpBtMsj,BorderLayout.SOUTH);
		add(jpDatos,BorderLayout.EAST);
		add(jpInformacion, BorderLayout.WEST);		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(permitidoContrasena & permitidoNomApell){
			if(jbAdd == e.getSource()){
				
				//-------Captura de los datos introducidos---------------
				nombre = jtfNombre.getText();
				apellido = jtfApellido.getText();
				usuario = (String) jcbUsuario.getSelectedItem();
				numCuenta = jtfNumCuenta.getText();
				contrasena = jtfContrasena.getText();
				tipoCuenta = (String)jcbTipoCuenta.getSelectedItem();	
				
				//------------------Datos int y double---------------------
				try{
					if((jtfSaldoInicial.getText() == null || jtfSaldoInicial.getText().equals(""))) 
						saldoInicial = Double.parseDouble("0"); 
					else
						saldoInicial = Double.parseDouble(jtfSaldoInicial.getText());
				}catch(Exception e1){
					saldoInicial = Double.parseDouble("0");
				}
				//---------------------------------------------------------		
				
				agregandoClientes(nombre, apellido, usuario, numCuenta, saldoInicial, contrasena, tipoCuenta);
				
				jpBotones.removeAll();  // remuevo los componentes anteriores 
				jpBotones.add(jbCerrar); // agrego el un boton nuevo de cerrar
				setSize(340,313);		// seteo el tamano del marco (para hacer un refresh)
				
				jtfNombre.setEditable(false);
				jtfApellido.setEditable(false);
				jtfNumCuenta.setEditable(false);
				jtfSaldoInicial.setEditable(false);
				jtfContrasena.setEditable(false);
				jlMensaje.setHorizontalAlignment(JLabel.CENTER);
				jlMensaje.setForeground(Color.GREEN.darker().darker().darker());
				jpBtMsj.add(jlMensaje, BorderLayout.CENTER);
				
				
				
			}
			if(jbCancelar == e.getSource() || jbCerrar == e.getSource()){
				dispose();
			}
		}
		if(jbCancelar == e.getSource() || jbCerrar == e.getSource()){
			dispose();
		}
		
	}	
	
	@SuppressWarnings("unchecked")
	private void agregandoClientes(String nombre, String apellido, String usuario, String numCuenta, double saldoInicial,
			String contrasena, String tipoCuenta){
		try {
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
			
			leer_fichero.close();
			
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			
			personal_Recuperado.toArray(listaNueva);
			
			for(ClientesDB e: listaNueva){
				lista.add(e);
			}
			
		}catch (Exception e1) { }
		
		lista.add(new ClientesDB(nombre, apellido, usuario, numCuenta, saldoInicial, contrasena, tipoCuenta));
		
		try{	
			ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("clientesBaseDatos.txt"));
			
			escribiendo_fichero.writeObject(lista);
			
			escribiendo_fichero.close();
			
		}catch(Exception e){ }	
		
	}	
		
	private class DocumentNombre implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			String nombre, item;
			nombre = jtfNombre.getText();
			item = nombre + jtfApellido.getText();
			if(nombre.length() < 3 || nombre.length() > 12){
				jtfNombre.setBackground(Color.RED);
				jcbUsuario.removeItem(item);
				permitidoNomApell = false;
			}else{
				jtfNombre.setBackground(Color.GREEN);
				jcbUsuario.addItem(item);
				permitidoNomApell = true;
			}
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			String item, nombre;
			nombre = jtfNombre.getText();
			item = nombre + jtfApellido.getText();
			if(nombre.length() < 3 || nombre.length() > 12){
				jtfNombre.setBackground(Color.RED);
				jcbUsuario.removeItem(item);
				permitidoNomApell = false;
			}else{
				jtfNombre.setBackground(Color.GREEN);
				jcbUsuario.addItem(item);
				permitidoNomApell = true;
			}
		}
		
	}
	
	private class DocumentApellido implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String apellido, item;
			apellido = jtfApellido.getText();
			item = apellido + jtfNombre.getText();
			if(apellido.length() < 5 || apellido.length() > 12){
				jtfApellido.setBackground(Color.RED);
				jcbUsuario.removeItem(item);
				permitidoNomApell = false;
			}else{
				jtfApellido.setBackground(Color.GREEN);
				jcbUsuario.addItem(item);
				jcbUsuario.addItem(jtfNombre.getText()+apellido);
				permitidoNomApell = true;
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			String item, apellido;
			apellido = jtfApellido.getText();
			item = apellido + jtfNombre.getText();
			
			if(apellido.length() < 5 || apellido.length() > 12){
				jtfApellido.setBackground(Color.RED);
				jcbUsuario.removeItem(item);
				permitidoNomApell = false;
			}else{
				jtfApellido.setBackground(Color.GREEN);
				jcbUsuario.addItem(item);
				permitidoNomApell = true;
			}
		}
	}
	
	private class DocumentContrasena implements DocumentListener{
		private String contrasena;
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			contrasena = jtfContrasena.getText();
			if(contrasena.length() < 8 || contrasena.length() > 18){
				jtfContrasena.setBackground(Color.RED);
			}else{
				jtfContrasena.setBackground(Color.GREEN);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			contrasena = jtfContrasena.getText();
			if(contrasena.length() < 8 || contrasena.length() > 18){
				jtfContrasena.setBackground(Color.RED);
			}else{
				jtfContrasena.setBackground(Color.GREEN);
			}
		}
		
	}
	
	private class DocumentContraConfirmar implements DocumentListener{
		private String contraConfirmar;
		private String contrasena;

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			contrasena = jtfContrasena.getText();
			contraConfirmar = jtfContrasenaConfirma.getText();
			if(!contraConfirmar.equals(contrasena)){
				jtfContrasenaConfirma.setBackground(Color.RED);
			}else{
				jtfContrasenaConfirma.setBackground(Color.GREEN);
				permitidoContrasena = true;
			}
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			contrasena = jtfContrasena.getText();
			contraConfirmar = jtfContrasenaConfirma.getText();
			if(!contraConfirmar.equals(contrasena)){
				jtfContrasenaConfirma.setBackground(Color.RED);
			}else{
				jtfContrasenaConfirma.setBackground(Color.GREEN);
				permitidoContrasena = true;
			}
		}
		
		
	}
	
}
