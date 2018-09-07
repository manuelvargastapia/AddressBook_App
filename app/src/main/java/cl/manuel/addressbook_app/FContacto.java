package cl.manuel.addressbook_app;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.manuel.addressbook_app.Datos.BDdescripcion;

/*DESCRIPCIÓN DE LA CLASE:
* Esta clase gestiona el RecyclerView con la lista de contactos, más el FloatingAcionButton
* que agrega contactos.
* Es el Fragment que MainActivity despliega por primera vez en celular y por siempre en tablet.
* Tiene una interfaz anidada que define métodos callback, implementados por MainActivity, para
* reaccionar (este Fragment) a los eventos asociados a los contactos.*/

public class FContacto extends Fragment
                       implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_CONTACTOS = 0;
    private FContactoListener listener;
    private AContacto adaptador;

    //Constructor
    public FContacto() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setHasOptionsMenu(true);

        View view = inflater.inflate(
                R.layout.fragment_contactos,
                container,
                false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        adaptador = new AContacto(new AContacto.ContactosClickListener(){
                                    @Override
                                    public void onClick(Uri uriContcato) {
                                        listener.contactoSeleccionado(uriContcato);
                                    }
        });

        recyclerView.setAdapter(adaptador);
        recyclerView.addItemDecoration(new ItemDivider(getContext()));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton botonAgregar = (FloatingActionButton) view.findViewById(R.id.btn_Agregar);
        botonAgregar.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {
                                        listener.agregarContacto();
                                    }
        });

        return view;
    }

    //-------------------------------

    //Métodos de Fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FContactoListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_CONTACTOS, null, this);
    }

    //-------------------------------

    //MÉTODOS DE INTEFAZ LoaderManager.LoaderCallbacks<>
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {

        switch (id){
            case LOADER_CONTACTOS:
                return new CursorLoader(getActivity(),
                        BDdescripcion.Contacto.CONTENT_URI, // Uri of contacts table
                        null, // null projection returns all columns
                        null, // null selection returns all rows
                        null, // no selection arguments
                        BDdescripcion.Contacto.COLUMNA_NOMBRE + " COLLATE NOCASE ASC");
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adaptador.cambiarCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adaptador.cambiarCursor(null);
    }

    //----------------------------------

    //INTERFAZ INTERNA INVOCADA POR MAINACTIVITY
    public interface FContactoListener{

        void agregarContacto();
        void contactoSeleccionado(Uri uriContacto);
    }

    //----------------------------------

    //Método llamado por MainActivity
    public void actualizarListaContactos(){
        adaptador.notifyDataSetChanged();
    }
}
