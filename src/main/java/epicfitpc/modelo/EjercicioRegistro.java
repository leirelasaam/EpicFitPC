package epicfitpc.modelo;

public class EjercicioActivo extends Ejercicio {
	private static final long serialVersionUID = -1754598289056956737L;
	private boolean completado = false;
	
	public EjercicioActivo() {
		
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}
}
