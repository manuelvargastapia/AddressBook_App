package cl.manuel.addressbook_app.Datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import cl.manuel.addressbook_app.R;

/*DESCRIPCIÓN DE LA CLASE:

* Subclase de ContentProvider. Manipula la bd.
* Fue creada vía "New>Other>Provider", "URI authorities: [dirección de actual package]" y "Exported"
* deseleccionado (esto indica que este ContentProvider es para uso exclusivo de esta app).
* Al hacer esto, el IDE define una subclase de ContentProvider, sobreescribe sus métodos y
* declara al ContentProvider en el Manifest. Esto es requerido para registrarlo tanto para el
* uso en esta app como en otras.*/

public class ContentProviderBD extends ContentProvider {

    private BDasistente bdAsistente; //Acceso a bd
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); //Determinar qué acción realizar

    //Constantes utilizadas con UriMarchter
    private static final int UN_CONTACTO = 1; //Manipular un contacto
    private static final int TABLA_CONTACTOS = 2; //Manipular la tabla completa

    //Bloque estático que configura el UriMatcher de este ContentProvider
    //Se ejecuta cuando esta clase es cargada en la memoria
    static {

        //Uri para contacto con id específoc (#)
        uriMatcher.addURI(BDdescripcion.AUTORIDAD,
                        BDdescripcion.Contacto.NOMBRE_TABLA + "/#",
                                UN_CONTACTO);
        //La uri agregada es: "content://cl.manuel.addressbook_app.Datos/contactos/#"
        //El símbolo # representa cualquier cadena de caracteres, que debe calzar con una
        //primary key de un contacto específico, que es en este caso siempre es "_id"
        //Cuando una Uri calza con este formato, se devuelve el valor UN_CONTACTO (1)

        //Uri para la tabla contactos
        uriMatcher.addURI(BDdescripcion.AUTORIDAD,
                            BDdescripcion.Contacto.NOMBRE_TABLA,
                                TABLA_CONTACTOS);
        //La uri agregada es: "content://cl.manuel.addressbook_app.Datos/contactos"
    }

    //Constructor
    public ContentProviderBD() {}

    //Al crearse este CP
    @Override
    public boolean onCreate() {

        //Crear el BDasistente
        BDasistente dbAsistente = new BDasistente(getContext());

        return true;
    }

    //Método que realiza las consultas y retorna un Cursor que interactúa con los resultados
    //Este método pide consultas SQL como parámetros, pero sin las palabras clave (WHERE, GROUP BY.etc)
    //Dicha especificación es realizada por el cuerpo del método
    @Override
    public Cursor query(Uri uri, String[] columnas, String criterioSeleccion,
                        String[] argumentosSeleccion, String orden) {

        //Objeto que realiza las consultas
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(BDdescripcion.Contacto.NOMBRE_TABLA);

        //Decisión de qué operación realizar
        switch (uriMatcher.match(uri)){
            case UN_CONTACTO: //Método que agrega "WHERE ID = [último segmente de query (ej: 5)]"
                queryBuilder.appendWhere(BDdescripcion.Contacto._ID + "=" + uri.getLastPathSegment());
                break;
            case TABLA_CONTACTOS: //No hay WHERE, por lo que se devuelve toda la tabla
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri) + uri);
        }

        //Ejecutar query para seleccionar uno o todos los contactos
        //Nuevamente, este método solo pide consultas sin especificar palabras clave
        Cursor cursor = queryBuilder.query(bdAsistente.getReadableDatabase(),
                columnas, criterioSeleccion, argumentosSeleccion, null, null, orden);

        //Ver cambios de contenido
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri uriNuevoContacto = null;

        switch (uriMatcher.match(uri)){
            case TABLA_CONTACTOS:
                long idFila = bdAsistente.getWritableDatabase().insert(
                        BDdescripcion.Contacto.NOMBRE_TABLA,
                        null,
                        values);
                if (idFila > 0){
                    uriNuevoContacto = BDdescripcion.Contacto.crearUriContacto(idFila);
                    getContext().getContentResolver().notifyChange(uri, null);
                }else {
                    throw new SQLException(getContext().getString(R.string.insert_failed) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_insert_uri) + uri);
        }

        return uriNuevoContacto;
    }

    @Override
    public int update(Uri uri, ContentValues columnas, String criterioSeleccion,
                      String[] argumentosSeleccion) {

        int numeroFilasActualizadas;

        switch (uriMatcher.match(uri)){
            case UN_CONTACTO:
                String id = uri.getLastPathSegment();
                numeroFilasActualizadas = bdAsistente.getWritableDatabase().update(
                        BDdescripcion.Contacto.NOMBRE_TABLA,
                        columnas,
                        BDdescripcion.Contacto._ID + "=" + id,
                        argumentosSeleccion);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_update_uri) + uri);
        }

        if(numeroFilasActualizadas != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numeroFilasActualizadas;
    }

    @Override
    public int delete(Uri uri, String criterioSeleccion, String[] argumentosSeleccion) {

        int numeroFilasEliminadas;

        switch (uriMatcher.match(uri)){
            case UN_CONTACTO:
                String id = uri.getLastPathSegment();
                numeroFilasEliminadas = bdAsistente.getWritableDatabase().delete(
                        BDdescripcion.Contacto.NOMBRE_TABLA,
                        BDdescripcion.Contacto._ID + "=" + id,
                        argumentosSeleccion);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_delete_uri) + uri);
        }

        if(numeroFilasEliminadas != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numeroFilasEliminadas;
    }

    //Requerido, pero no utilizado
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
