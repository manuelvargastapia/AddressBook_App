package cl.manuel.addressbook_app.Datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

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

    @Override
    public boolean onCreate() {



        return false;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
