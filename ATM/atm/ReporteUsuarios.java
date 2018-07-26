

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReporteUsuarios extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 28;

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ReporteUsuarios ur = new ReporteUsuarios();
//		ur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
//	private Toolkit mipantalla = Toolkit.getDefaultToolkit();
//	private Image miIcono = mipantalla.getImage("src/img/iconBank.png");
//	private Dimension tamanoPantalla = mipantalla.getScreenSize();
//	private int alturaPantalla = tamanoPantalla.height;
//	private int anchoPantalla = tamanoPantalla.width;
	
	
	public ReporteUsuarios(){
//		super("Reporte de usuario");
//		setBounds(alturaPantalla/2,anchoPantalla/4,550,250);
//		setIconImage(miIcono);

		setLayout(new BorderLayout());


		addTablaClientes();
		
	}
	
	@SuppressWarnings("unchecked")
	private void addTablaClientes(){
		int f =0,s =0;
		
		JPanel jpTablas = new JPanel(new BorderLayout());
		
		JTable jtTitulo = new JTable(1,7);
		
		JTable jtDatos = null;// = new JTable(9,6);

		JScrollPane datos = null;//= new JScrollPane(jtDatos);	
		String[] titulo = {"Nombre","Apellido","Usuario","N� Cuenta","Saldo","Contrase�a","Tipo Cuenta"};
		
		//------------recolectando datos---------------
		Object[][] tbBidimencional = null;
		try{
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();
			
			leer_fichero.close();
			
			s = personal_Recuperado.size();
//			jtDatos = new JTable(s,7);
//			datos = new JScrollPane(jtDatos);	
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			personal_Recuperado.toArray(listaNueva);
			
			tbBidimencional = new Object[s][7]; 
			
			for(ClientesDB e: listaNueva){
				
				tbBidimencional[f][0] = e.getNombre();
				tbBidimencional[f][1] = e.getApellido();
				tbBidimencional[f][2] = e.getUsuario();
				tbBidimencional[f][3] = e.getNumCuenta();
				tbBidimencional[f][4] = e.getSaldoInicial();
				tbBidimencional[f][5] = e.getContrasena();
				tbBidimencional[f][6] = e.getTipoCuenta();
				
//				jtDatos.setValueAt(e.getNombre(), f, 0);
//				jtDatos.setValueAt(e.getApellido(), f, 1);
//				jtDatos.setValueAt(e.getUsuario(), f, 2);
//				jtDatos.setValueAt(e.getNumCuenta(), f, 3);
//				jtDatos.setValueAt(e.getSaldoInicial(), f, 4);
//				jtDatos.setValueAt(e.getContrasena(), f, 5);
//				jtDatos.setValueAt(e.getTipoCuenta(), f, 6);
				f++;
			}
			
			jtDatos = new JTable(tbBidimencional,titulo);
			datos = new JScrollPane(jtDatos);

			//-----------------------Acomodo y detalles en particular----------------------
			jtTitulo.setEnabled(false);
			jtDatos.setEnabled(false);
			
			
			jpTablas.add(datos,BorderLayout.CENTER);
		}catch(Exception e){
//			System.out.println("fichero no encontrado");
		}		
		
//		jpTablas.add(jtTitulo,BorderLayout.NORTH);
//		jpTablas.add(datos,BorderLayout.CENTER);
//		jpTablas.add(jthTitulo, BorderLayout.SOUTH);
		
		add(jpTablas,BorderLayout.CENTER);
		
	}

	
}

