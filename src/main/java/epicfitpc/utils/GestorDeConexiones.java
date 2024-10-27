package epicfitpc.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GestorDeConexiones {
    private static GestorDeConexiones instancia;
    private boolean conectado;

    private GestorDeConexiones() {
        this.conectado = verificarConexion();
    }

    public static synchronized GestorDeConexiones getInstance() {
        if (instancia == null) {
            instancia = new GestorDeConexiones();
        }
        return instancia;
    }

    private boolean verificarConexion() {
        boolean conexion = true;

        try {
            @SuppressWarnings("deprecation")
			URL url = new URL("https://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(1000);
            urlConnect.connect();
            conexion = (urlConnect.getResponseCode() == 200);
        } catch (IOException e) {
            conexion = false;
        }

        return conexion;
    }

    public boolean hayConexion() {
        return this.conectado;
    }

    public void actualizarConexion() {
        this.conectado = verificarConexion();
    }
}
