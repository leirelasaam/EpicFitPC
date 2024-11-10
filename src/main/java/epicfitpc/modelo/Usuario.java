package epicfitpc.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.google.cloud.Timestamp;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -4644552232570751500L;
	private String id = null;
	private String nombre = null;
	private String apellido = null;
	private String correo = null;
	public String usuario = null;
	private String pass = null;
	private Timestamp fechaNac = null;
	private Timestamp fechaAlt = null;
	private int nivel = 0;
	private boolean esEntrenador = false;

	// SUBCOLECCIÓN DE HISTÓRICOS
	private ArrayList<Historico> historicos = null;

	public Usuario() {
		// Constructor vacío
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Timestamp getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Timestamp fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Timestamp getFechaAlt() {
		return fechaAlt;
	}

	public void setFechaAlt(Timestamp fechaAlt) {
		this.fechaAlt = fechaAlt;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public boolean isEsEntrenador() {
		return esEntrenador;
	}

	public void setEsEntrenador(boolean esEntrenador) {
		this.esEntrenador = esEntrenador;
	}

	public ArrayList<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(ArrayList<Historico> historicos) {
		this.historicos = historicos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellido, correo, esEntrenador, fechaAlt, fechaNac, historicos, id, nivel, nombre, pass,
				usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(correo, other.correo)
				&& esEntrenador == other.esEntrenador && Objects.equals(fechaAlt, other.fechaAlt)
				&& Objects.equals(fechaNac, other.fechaNac) && Objects.equals(historicos, other.historicos)
				&& Objects.equals(id, other.id) && nivel == other.nivel && Objects.equals(nombre, other.nombre)
				&& Objects.equals(pass, other.pass) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo
				+ ", usuario=" + usuario + ", pass=" + pass + ", fechaNac=" + fechaNac + ", fechaAlt=" + fechaAlt
				+ ", nivel=" + nivel + ", esEntrenador=" + esEntrenador + ", historicos=" + historicos + "]";
	}

}
