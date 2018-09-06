package cl.manuel.addressbook_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*DESCRIPCIÓN DE LA CLASE:
* Esta clase gestiona el RecyclerView con la lista de contactos, más el FloatingAcionButton
* que agrega contactos.
* Es el Fragment que MainActivity despliega por primera vez en celular y por siempre en tablet.
* Tiene una interfaz anidada que define métodos callback, implementados por MainActivity, para
* reaccionar (este Fragment) a los eventos asociados a los contactos.*/

public class FContacto extends Fragment {

    public FContacto() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contactos, container, false);
    }
}
