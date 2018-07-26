

import java.io.Serializable;

/**
 * @author WorkStation-Harry
 *
 */
public class ClientesDB implements Serializable{
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	*/

	private static final long serialVersionUID = 28;
	private String nombre, apellido, usuario, contrasena, tipoCuenta;
	private double saldoInicial;
	private String numCuenta;
	
	public ClientesDB(String usuario, String numCuenta,
			String contrasena, String tipoCuenta) {
		
		this("anonimo ","", usuario, "",0.0,
			contrasena,tipoCuenta);
		
	}

	public ClientesDB(String nombre, String apellido, String usuario, String numCuenta, double saldoInicial,
			String contrasena, String tipoCuenta) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.usuario = usuario;
		this.numCuenta = numCuenta;
		this.saldoInicial = saldoInicial;
		this.contrasena = contrasena;
		this.tipoCuenta = tipoCuenta;
	}
	
	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getNumCuenta() {
		return numCuenta;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public String getContrasena() {
		return contrasena;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

}
