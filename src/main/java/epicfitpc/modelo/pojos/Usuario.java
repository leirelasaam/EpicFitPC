package epicfitpc.modelo.pojos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import com.google.cloud.Timestamp;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -4644552232570751500L;
	private String id = null;
	private String nombre = null;
	private String apellido = null;
	private String correo = null;
	private String pass = null;
	private LocalDate fechaNac = null;
	private LocalDate fechaAlt = null;
	private int nivel = 0;
	private boolean esEntrenador = false;

	public Usuario() {
		// Constructor vacío
	}

	// Constructor sobrecargado
	public Usuario(String id, String nombre, String apellido, String correo, String pass, LocalDate fechaNac,
			LocalDate fechaAlt, int nivel, boolean esEntrenador) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.pass = pass;
		this.fechaNac = fechaNac;
		this.fechaAlt = fechaAlt;
		this.nivel = nivel;
		this.esEntrenador = esEntrenador;
	}

	// Constructor que recibe los valores crudos desde el documento
	public Usuario(String id, String nombre, String apellido, String correo, String pass, double nivel,
			Timestamp fechaNac, Timestamp fechaAlt, boolean esEntrenador) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.pass = pass;

		this.nivel = (int) nivel;

		this.fechaNac = convertirTimestampALocalDate(fechaNac);
		this.fechaAlt = convertirTimestampALocalDate(fechaAlt);

		this.esEntrenador = esEntrenador;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public LocalDate getFechaAlt() {
		return fechaAlt;
	}

	public void setFechaAlt(LocalDate fechaAlt) {
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

	@Override
	public int hashCode() {
		return Objects.hash(apellido, correo, esEntrenador, fechaAlt, fechaNac, id, nivel, nombre, pass);
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
				&& Objects.equals(fechaNac, other.fechaNac) && Objects.equals(id, other.id) && nivel == other.nivel
				&& Objects.equals(nombre, other.nombre) && Objects.equals(pass, other.pass);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", pass="
				+ pass + ", fechaNac=" + fechaNac + ", fechaAlt=" + fechaAlt + ", nivel=" + nivel + ", esEntrenador="
				+ esEntrenador + "]";
	}

	private LocalDate convertirTimestampALocalDate(Timestamp timestamp) {
		if (timestamp != null) {
			Date date = timestamp.toDate();
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}

}
