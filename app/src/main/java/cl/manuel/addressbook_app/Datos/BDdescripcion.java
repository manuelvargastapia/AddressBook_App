package cl.manuel.addressbook_app.Datos;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/*DESCRIPCIÓN DE LA CLASE:
 * La clase anidada Contacto define campos para el nombre de la tabla de la bd, la Uri usada
 * para acceder a ella vía ContentProvider y los nombres de las columnas de la tabla.
 * También contiene un método para crear una Uri que hace referencia a un contacto específico
 * de la bd.
 * Esta clase es usada por las otras dos que componen la capa de datos.*/

public class BDdescripcion {

    public static final String AUTORIDAD = "cl.manuel.addressbook_app.Datos"; //Autoridad del ContentProvider
    public static final Uri URI_BASE = Uri.parse("content://" + AUTORIDAD); //Uri para interactuar con CP


    /*Clase anidada
    * Estrucutra básica y tradicional para describir BBDD*/
    public static final class Contacto implements BaseColumns{

        public static final String NOMBRE_TABLA = "contactos";

        //Uri para acceder a la tabla contactos
        public static final Uri CONTENT_URI = URI_BASE.buildUpon().appendPath(NOMBRE_TABLA).build();

        //Nombres de las columnas para la tabla contactos
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_TELEFONO = "telefono";
        public static final String COLUMNA_EMAIL = "email";
        public static final String COLUMNA_CALLE = "calle";
        public static final String COLUMNA_COMUNA = "comuna";
        public static final String COLUMNA_CIUDAD = "ciudad";

        //Creación de uri para contacto específico
        public static Uri crearUriContacto(long id){ //id será la constante "_id" definida por BaseColumns
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
