package cl.manuel.addressbook_app;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

//Administra todos los Fragment de la App vía implementación de sus interfaces
public class MainActivity extends AppCompatActivity
                          implements FContacto.FContactoListener,
                                     FDetalle.FDetalleListener,
                                     FAgregarEditar.FAgregarEditarListener{

    //Key para almacenar uri de un contacto y pasárselos a los Fragment
    public static final String URI_CONTACTO = "uri de contacto";

    //Instancias que gestionarán el despliegue de los contactos, detalles y adición o edición, resp.
    private FContacto fragmentoContactos;

    //Desplegar contactos (FContacto) cuando la app es iniciada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Evaluar si el layout está en un teléfono (else: tablet)
        if (savedInstanceState != null && findViewById(R.id.fragmentContainer) != null){
            fragmentoContactos = new FContacto();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, fragmentoContactos);
            transaction.commit();
        }else{
            fragmentoContactos = (FContacto) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        }
    }

    //-------------------------------

    //MÉTODOS DE INTERFAZ FCONTACTOLISTENER
    @Override
    public void contactoSeleccionado(Uri uriContacto) {

        if (findViewById(R.id.fragmentContainer) != null){          //Tel
            mostrarContacto(uriContacto, R.id.fragmentContainer);
        }else{                                                      //Tablet
            getSupportFragmentManager().popBackStack();
            mostrarContacto(uriContacto, R.id.contenedor_panel_derecho);
        }
    }

    @Override
    public void agregarContacto() {

        if (findViewById(R.id.fragmentContainer) != null){          //Tel
            mostrarFragmentoAgregarEditar(R.id.fragmentContainer, null);
        }else{                                                      //Tablet
            getSupportFragmentManager().popBackStack();
            mostrarFragmentoAgregarEditar(R.id.contenedor_panel_derecho, null);
        }
    }

    //-------------------------------

    //MÉTODOS DE INTERFAZ FDETALLELISTENER
    @Override
    public void contactoEliminado() {

        getSupportFragmentManager().popBackStack();
        fragmentoContactos.actualizarListaContactos();
    }

    @Override
    public void editarContacto(Uri uriContacto) {

        if(findViewById(R.id.fragmentContainer) != null){
            mostrarFragmentoAgregarEditar(R.id.fragmentContainer, uriContacto);
        }else {
            mostrarFragmentoAgregarEditar(R.id.contenedor_panel_derecho, uriContacto);
        }
    }

    //-------------------------------

    //MÉTODOS DE INTEERFAZ FAGREGAREDITARLISTENER
    @Override
    public void agregarEditarCompletado(Uri uriContacto) {

        getSupportFragmentManager().popBackStack();
        fragmentoContactos.actualizarListaContactos();

        if(findViewById(R.id.fragmentContainer) == null){
            getSupportFragmentManager().popBackStack();
            mostrarContacto(uriContacto, R.id.contenedor_panel_derecho);
        }
    }

    //-------------------------------

    //MÉTODOS INVOCADOS EN MÉTODOS DE INTERFACES
    private void mostrarFragmentoAgregarEditar(int idView, Uri uriContacto) {

        FAgregarEditar fragmentoAgregarEditar = new FAgregarEditar();

        if (uriContacto != null){
            Bundle argumentos = new Bundle();
            argumentos.putParcelable(URI_CONTACTO, uriContacto);
            fragmentoAgregarEditar.establecerArgumentos(argumentos);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(idView, fragmentoAgregarEditar);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void mostrarContacto(Uri uriContacto, int idView) {

        FDetalle fragmentoDetalles = new FDetalle();
        Bundle argumentos = new Bundle();
        argumentos.putParcelable(URI_CONTACTO, uriContacto);
        fragmentoDetalles.establecerArgumentos(argumentos);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(idView, fragmentoDetalles);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
