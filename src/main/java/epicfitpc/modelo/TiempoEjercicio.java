package epicfitpc.modelo;

import java.io.Serializable;
import java.util.Objects;

public class TiempoEjercicio implements Serializable {

	private static final long serialVersionUID = 3613403944239321918L;
	private Ejercicio ejercicio;
	private int tiempo;

	public Ejercicio getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Ejercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ejercicio, tiempo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TiempoEjercicio other = (TiempoEjercicio) obj;
		return Objects.equals(ejercicio, other.ejercicio) && tiempo == other.tiempo;
	}

	@Override
	public String toString() {
		return "TiempoEjercicio [ejercicio=" + ejercicio + ", tiempo=" + tiempo + "]";
	}

}
