package cl.manuel.addressbook_app;

/*DESCRIPCIÓN DE LA CLASE:
* Maneja los TextInputLayouts y un FloatingActionButton para agregar o editar.
* Su interfaz anidada define métodos callback implementados por MainAcitivty, de modo que este
* Fragment pueda responder a los eventos (guardar nuevo contacto o actualizad).*/


import android.net.Uri;
import android.support.v4.app.Fragment;

public class FAgregarEditar extends Fragment {


    //INTERFAZ INTERNA INVOCADA POR MAINACTIVITY
    public interface FAgregarEditarListener{

        void agregarEditarCompletado(Uri uriContacto);
    }
}
