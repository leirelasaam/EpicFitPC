package epicfitpc.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GestorDeFicherosBinarios<T extends Serializable> {
	// Ruta relativa del fichero
	private String ruta = null;

	public GestorDeFicherosBinarios(String ruta) {
		this.ruta = ruta;
	}

	public void escribir(ArrayList<T> ts) throws FileNotFoundException, IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			File fichero = new File(ruta);
			fos = new FileOutputStream(fichero, false);
			oos = new ObjectOutputStream(fos);
			for (T t : ts) {
				oos.writeObject(t);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != oos)
					oos.close();
			} catch (IOException e) {
			}
			try {
				if (null != fos)
					fos.close();
			} catch (IOException e) {
			}
		}
	}

	public ArrayList<T> leer() throws FileNotFoundException, IOException, ClassNotFoundException {
		ArrayList<T> ts = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			File fichero = new File(ruta);
			fis = new FileInputStream(fichero);
			ois = new ObjectInputStream(fis);

			while (true) {
				try {
					@SuppressWarnings("unchecked")
					T t = (T) ois.readObject();
					if (null == ts)
						ts = new ArrayList<T>();
					ts.add(t);
				} catch (ClassNotFoundException e) {
					throw e;
				} catch (EOFException e) {
					// Fin del archivo alcanzado
					break;
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (null != ois)
					ois.close();
			} catch (IOException e) {
			}
			try {
				if (null != fis)
					fis.close();
			} catch (IOException e) {
			}
		}

		return ts;
	}
}