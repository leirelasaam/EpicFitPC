package epicfitpc.modelo.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.google.cloud.firestore.DocumentReference;

public class Workout implements Serializable {

	private static final long serialVersionUID = -1761854948300958014L;
	private String id = null;
	private String nombre = null;
	private int nivel = 0;
	private int tiempo = 0;
	private String video = null;
	private String tipo = null;
	private ArrayList<DocumentReference> ejercicios = null;
	private ArrayList<Ejercicio> ejerciciosArray = null;

	public Workout() {

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

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public ArrayList<DocumentReference> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(ArrayList<DocumentReference> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public ArrayList<Ejercicio> getEjerciciosArray() {
		return ejerciciosArray;
	}

	public void setEjerciciosArray(ArrayList<Ejercicio> ejerciciosArray) {
		this.ejerciciosArray = ejerciciosArray;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ejercicios, ejerciciosArray, id, nivel, nombre, tiempo, tipo, video);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		return Objects.equals(ejercicios, other.ejercicios) && Objects.equals(ejerciciosArray, other.ejerciciosArray)
				&& Objects.equals(id, other.id) && nivel == other.nivel && Objects.equals(nombre, other.nombre)
				&& tiempo == other.tiempo && Objects.equals(tipo, other.tipo) && Objects.equals(video, other.video);
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", tiempo=" + tiempo + ", video="
				+ video + ", tipo=" + tipo + ", ejercicios=" + ejercicios + ", ejerciciosArray=" + ejerciciosArray
				+ "]";
	}

}
