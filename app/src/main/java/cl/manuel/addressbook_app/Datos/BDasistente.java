package cl.manuel.addressbook_app.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*DESCRIPCIÓN DE LA CLASE:
 * Crea la bd y permite a ContentProviderBD acceder a ella.*/

public class BDasistente extends SQLiteOpenHelper{

    private static final String NOMBRE_BD = "AddressBook.bd";
    private static final int VERSION_BD = 1;

    //Constructor
    public BDasistente(Context context) {

        //Parámetros para constructor de SQLiteOpenHelper
        super(context, NOMBRE_BD, null, VERSION_BD); //null: CursorFactory por defecto
    }

    //Creación de tabla contactos cuando la BD es creada
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Llamado cuando no existe ya una bd

        final String CONSULTA_CREAR_TABLA = "CREATE TABLE " + BDdescripcion.Contacto.NOMBRE_TABLA +
                "(" +
                BDdescripcion.Contacto._ID + " integer_primary_key, "  +
                BDdescripcion.Contacto.COLUMNA_NOMBRE + " TEXT, " +
                BDdescripcion.Contacto.COLUMNA_TELEFONO + " TEXT, " +
                BDdescripcion.Contacto.COLUMNA_EMAIL + " TEXT, " +
                BDdescripcion.Contacto.COLUMNA_CALLE + " TEXT, " +
                BDdescripcion.Contacto.COLUMNA_COMUNA + " TEXT, " +
                BDdescripcion.Contacto.COLUMNA_CIUDAD + " TEXT);";
        sqLiteDatabase.execSQL(CONSULTA_CREAR_TABLA);
    }

    //Suele usarse para definir cómo actualizar la bd cuando el esquema cambia
    //Llamado cuando hay nuevas versiones de la bd
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {}
}
