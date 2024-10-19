# EpicFitPC
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
