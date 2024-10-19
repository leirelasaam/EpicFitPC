# EpicFitPC
## Protección de la rama main
La rama _main_ está protegida para que nadie pueda hacer un push o merge sin antes hacer un pull request. Además, debe pasar la validación incluída, que comprueba que el proyecto compila.
### Pasos para pull request
1. Hay que trabajar cada uno en su rama e ir subiendo las actualizaciones a la misma. Para esto no se comprueba la compilación, puede haber errores.
2. Una vez la rama esté lista para hacer merge al main, es necesario como siempre hacer merge de la rama actualizada del main a la tuya y gestionar conflictos.
3. Cuando los conflictos estén solucionados, se debe hacer push de tu rama.
4. Ahora que ya está todo actualizado, es necesario crear un pull request. Si intentas hacer merge o push al main, te saldrá un error porque está protegida.
5. Es necesario en GitHub ir al apartado de **Pull requests** y seleccionar _New pull request_.
6. Entonces, saldrán las ramas y debes seleccionar la tuya.
7. Al hacer el pull request, se comprueba si compila.
      - En caso de que compile, se puede aprobar el pull request y entonces, se hace merge al main.
        ![image](https://github.com/user-attachments/assets/e67f8ff1-a20b-4768-96da-5c366f3623fc)

      - En caso de que no compile, no se deja aprobar el pull request y entonces, se debe cerrar. Cuando tengas una versión sin fallos, podrás volver a repetir este proceso.
        ![image](https://github.com/user-attachments/assets/146300a4-3b95-4e8c-ab55-a6e27ab77fc7)
8. Cuando se intente subir algo con fallos, el administrador (_Leire_) del repositorio recibe un correo electrónico.

## Conexión
La conexión se establece mediante la clase **Conexion** dentro del paquete _utils_, haciendo uso del archivo de credenciales de Firestore que debe estar ubicado en la raíz del proyecto.
Contiene el método estático _getConexion()_ para que exista una sola instancia de Firestore y se comparta.

```java
public static Firestore getConexion() throws FileNotFoundException, IOException {
  FileInputStream serviceAccount = new FileInputStream(RUTA_CREDENCIALES);

  FirestoreOptions fsOptions = FirestoreOptions.getDefaultInstance().toBuilder().setProjectId(PROJECT_ID)
      .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

  Firestore db = fsOptions.getService();
  
  return db;
}
```

Por lo tanto, para hacer uso de esta conexión se acceder a dicho método estático:
```java
Firestore db;

try {
  db = Conexion.getConexion();
} catch (IOException e) {}
```

## Gestores
La clase **GestorDeUsuarios** realiza operaciones CRUD sobre la base de datos del proyecto para la colección de _Usuarios_. Los usuarios se gestionan en la aplicación como objetos Usuario, definido en su propia clase que se encuentra en el paquete _/modelo/pojos_.

## Lanzar la aplicación
La aplicación se lanza mediante la clase **EpicFitPC** que instancia el _MainFrame_, donde se irán añadiendo los diferentes paneles que componen la vista.
