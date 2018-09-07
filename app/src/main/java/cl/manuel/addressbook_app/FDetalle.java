package cl.manuel.addressbook_app;

/*DESCRIPCIÓN DE LA CLASE:
* Maneja los TextViews estilizados que despliegan los datos del contacto seleccionado,
* además de los ítems de la app bar que permiten al usuario editar o borrar el contacto
* actualmente desplegado.
* Su interfaz anidado define los métodos callback implementados por MainActivity para responder
* adecuadamente a los eventos.*/


import android.net.Uri;
import android.support.v4.app.Fragment;

public class FDetalle extends Fragment {


    //INTERFAZ INTERNA INVOCADA POR MAINACTIVITY
    public interface FDetalleListener{

        void contactoEliminado();
        void editarContacto(Uri uriContacto);
    }
}
