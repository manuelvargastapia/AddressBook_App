package cl.manuel.addressbook_app.Datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/*NOTA: EXPLICACIÓN DE DEITEL DE CÓMO CREAR ESTA CLASE Y POR QUÉ DE ESA FORMA

* "Class AddressBookContentProvider is a subclass of ContentProvider that defines
how to manipulate the database. To create this class, use New > Other > Content
Provider. For URI authorities specify [dirección de actual package] and
uncheck the Exported checkbox, then click Finish. Unchecking Exported indicates
that this ContentProvider is for use only in this app. The IDE defines a
subclass of ContentProvider and overrides its required methods. In addition, the
IDE declares the ContentProvider AndroidManifest.xml as a <provider> element
nested in the <application> element. This is required to register the ContentProvider
with the Android operating system—not only for use in this app,
but for use in other apps (when the ContentProvider is exported)."*/

public class ContentProviderBD extends ContentProvider {
    public ContentProviderBD() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
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
