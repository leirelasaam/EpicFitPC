package epicfitpc.modelo.pojos;

import java.io.Serializable;
import java.util.Objects;

public class Ejercicio implements Serializable {
	private static final long serialVersionUID = 980898727046879368L;
	private String id = null;
	private String nombre = null;
	private int repeticiones = 0;
	private int tiempoSerie = 0;
	private int descanso = 0;
	private int series = 0;

	public Ejercicio() {
		// Constructor vacío
	}

	public Ejercicio(String id, String nombre, int repeticiones, int tiempoSerie, int descanso, int series) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.repeticiones = repeticiones;
		this.tiempoSerie = tiempoSerie;
		this.descanso = descanso;
		this.series = series;
	}
	
	public Ejercicio(String id, String nombre, double repeticiones, double tiempoSerie, double descanso, double series) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.repeticiones = (int) repeticiones;
		this.tiempoSerie = (int) tiempoSerie;
		this.descanso = (int) descanso;
		this.series = (int) series;
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

	public int getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}

	public int getTiempoSerie() {
		return tiempoSerie;
	}

	public void setTiempoSerie(int tiempoSerie) {
		this.tiempoSerie = tiempoSerie;
	}

	public int getDescanso() {
		return descanso;
	}

	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descanso, id, nombre, repeticiones, series, tiempoSerie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio other = (Ejercicio) obj;
		return descanso == other.descanso && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& repeticiones == other.repeticiones && series == other.series && tiempoSerie == other.tiempoSerie;
	}

	@Override
	public String toString() {
		return "Ejercicio [id=" + id + ", nombre=" + nombre + ", repeticiones=" + repeticiones + ", tiempoSerie="
				+ tiempoSerie + ", descanso=" + descanso + ", series=" + series + "]";
	}

}