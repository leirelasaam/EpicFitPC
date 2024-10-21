package epicfitpc.modelo.pojos;

import java.util.Objects;

public class Ejercicio {

	private String nombre = null;
	private int repeticiones = 0;
	private int tiempoSerie = 0;
	private int descanso = 0;
	private int series;

	public Ejercicio() {
		// Constructor vacío
	}

	public Ejercicio(String nombre, int repeticiones, int tiempoSerie, int descanso, int series) {
		super();
		this.nombre = nombre;
		this.repeticiones = repeticiones;
		this.tiempoSerie = tiempoSerie;
		this.descanso = descanso;
		this.series = series;
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
		return Objects.hash(descanso, nombre, repeticiones, series, tiempoSerie);
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
		return descanso == other.descanso && Objects.equals(nombre, other.nombre) && repeticiones == other.repeticiones
				&& series == other.series && tiempoSerie == other.tiempoSerie;
	}

	@Override
	public String toString() {
		return "Ejercicio [nombre=" + nombre + ", repeticiones=" + repeticiones + ", tiempoSerie=" + tiempoSerie
				+ ", descanso=" + descanso + ", series=" + series + "]";
	}

}