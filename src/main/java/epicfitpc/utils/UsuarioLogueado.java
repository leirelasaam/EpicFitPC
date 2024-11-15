package epicfitpc.utils;

import epicfitpc.modelo.Usuario;

/**
 * Singleton para gestionar el usuario logueado.
 */
public class UsuarioLogueado {
    private static UsuarioLogueado instance;
    private Usuario usuario; 

    // Constructor privado
    private UsuarioLogueado() {}

    // Método para obtener la instancia del Singleton
    public static synchronized UsuarioLogueado getInstance() {
        if (instance == null) {
            instance = new UsuarioLogueado(); 
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
