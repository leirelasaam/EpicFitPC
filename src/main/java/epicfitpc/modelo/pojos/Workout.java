package epicfitpc.modelo.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Workout implements Serializable {

	private static final long serialVersionUID = -1761854948300958014L;
	private String id = null;
	private String nombre = null;
	private int nivel = 0;
	private int tiempo = 0;
	private String video = null;
	private ArrayList<String> ejercicios = null;

	public Workout() {

	}

	public Workout(String id, String nombre, int nivel, int tiempo, String video, ArrayList<String> ejercicios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nivel = nivel;
		this.tiempo = tiempo;
		this.video = video;
		this.ejercicios = ejercicios;
	}

	public Workout(String id, String nombre, double nivel, double tiempo, String video, ArrayList<String> ejercicios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nivel = (int) nivel;
		this.tiempo = (int) tiempo;
		this.video = video;
		this.ejercicios = ejercicios;
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

	public ArrayList<String> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(ArrayList<String> ejercicios) {
		this.ejercicios = ejercicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ejercicios, id, nivel, nombre, tiempo, video);
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
		return Objects.equals(ejercicios, other.ejercicios) && Objects.equals(id, other.id) && nivel == other.nivel
				&& Objects.equals(nombre, other.nombre) && tiempo == other.tiempo && Objects.equals(video, other.video);
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", tiempo=" + tiempo + ", video="
				+ video + ", ejercicios=" + ejercicios + "]";
	}

}
