package epicfitpc.modelo.pojos;

import java.io.Serializable;
import java.util.Objects;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;

public class Historico implements Serializable {

	private static final long serialVersionUID = -1532297358619837862L;
	private String id = null;
	private Timestamp fecha = null;
	private int porcentaje = 0;
	private int tiempo = 0;
	private DocumentReference usuario = null;
	private DocumentReference workout = null;
	private Usuario usuarioObj = null;
	private Workout workoutObj = null;

	public Historico() {
		// Constructor vacío
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public DocumentReference getUsuario() {
		return usuario;
	}

	public void setUsuario(DocumentReference usuario) {
		this.usuario = usuario;
	}

	public DocumentReference getWorkout() {
		return workout;
	}

	public void setWorkout(DocumentReference workout) {
		this.workout = workout;
	}

	public Usuario getUsuarioObj() {
		return usuarioObj;
	}

	public void setUsuarioObj(Usuario usuarioObj) {
		this.usuarioObj = usuarioObj;
	}

	public Workout getWorkoutObj() {
		return workoutObj;
	}

	public void setWorkoutObj(Workout workoutObj) {
		this.workoutObj = workoutObj;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, porcentaje, tiempo, usuario, usuarioObj, workout, workoutObj);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Historico other = (Historico) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id) && porcentaje == other.porcentaje
				&& tiempo == other.tiempo && Objects.equals(usuario, other.usuario)
				&& Objects.equals(usuarioObj, other.usuarioObj) && Objects.equals(workout, other.workout)
				&& Objects.equals(workoutObj, other.workoutObj);
	}

	@Override
	public String toString() {
		return "Historico [id=" + id + ", fecha=" + fecha + ", porcentaje=" + porcentaje + ", tiempo=" + tiempo
				+ ", usuario=" + usuario + ", workout=" + workout + ", usuarioObj=" + usuarioObj + ", workoutObj="
				+ workoutObj + "]";
	}

}
