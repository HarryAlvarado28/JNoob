

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EliminarClientes{

	ArrayList <ClientesDB> lista = new ArrayList<ClientesDB>();
	private boolean encontrado = false;
	int i = 0;

	public EliminarClientes(String usuarioEliminar){
		// TODO Auto-generated constructor stub
		determinaUsuario(usuarioEliminar);
	}	
	
	@SuppressWarnings("unchecked")
	private void determinaUsuario(String usuarioModificar){
		try {
			ObjectInputStream leer_fichero = new ObjectInputStream(new FileInputStream("clientesBaseDatos.txt"));
			ArrayList<ClientesDB[]> personal_Recuperado = (ArrayList<ClientesDB[]>) leer_fichero.readObject();	
			leer_fichero.close();
			
			ClientesDB []listaNueva = new ClientesDB[personal_Recuperado.size()];
			
			personal_Recuperado.toArray(listaNueva);
			
			for(ClientesDB e: listaNueva){
				if(e.getUsuario().equals(usuarioModificar)){
					encontrado = true;
					break; 
				}
				i++;
			}
			if(!encontrado){
				JOptionPane.showMessageDialog(new JFrame(), "Usuario no encontrado");	
				
			}else{
				reemplazar(i);
	
			}
		
		}catch (Exception e1) { }
		
	}
	
	@SuppressWarnings("unchecked")
	private void reemplazar(int index){
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
			

		}catch (Exception e1) {	}
		
		try{	
			ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("clientesBaseDatos.txt"));
			escribiendo_fichero.writeObject(lista);
			escribiendo_fichero.close();
			
		}catch(Exception e){ }	
		
	}
	
}
