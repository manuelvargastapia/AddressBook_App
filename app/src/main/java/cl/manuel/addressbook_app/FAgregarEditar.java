package cl.manuel.addressbook_app;

/*DESCRIPCIÓN DE LA CLASE:
* Maneja los TextInputLayouts y un FloatingActionButton para agregar o editar.
* Su interfaz anidada define métodos callback implementados por MainAcitivty, de modo que este
* Fragment pueda responder a los eventos (guardar nuevo contacto o actualizad).*/


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import cl.manuel.addressbook_app.Datos.BDdescripcion;

public class FAgregarEditar extends Fragment
                            implements LoaderManager.LoaderCallbacks<Cursor>{

    //VARIABLES DE CLASE

    private static final int LOADER_CONTACTO = 0;
    private FAgregarEditarListener listenerFAgregarEditar;
    private Uri uriContacto;
    private boolean agregar_nuevo_contacto = true;
    private CoordinatorLayout coordinatorLayout;

    //EditTexts para info de contacto
    private TextInputLayout textInputLayout_Nombre;
    private TextInputLayout textInputLayout_Telefono;
    private TextInputLayout textInputLayout_Email;
    private TextInputLayout textInputLayout_Calle;
    private TextInputLayout textInputLayout_Comuna;
    private TextInputLayout textInputLayout_Ciudad;
    private FloatingActionButton fab_Guardar_Contactos;

    // Detecta cuando el texto en el EditText de textInputLayout_Nombre cambia,
    //esto para ocular o mostrar el botón de guardar
    private final TextWatcher listenerNombreCambiado = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        //Llamado cuando el texto en textInputLayout_Nombre cambia
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            actualizarBotonGuardarFAB();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //Responde a eventos generados cuando el usuario guarda un contacto
    private final View.OnClickListener botonGuardarContactoClickeado = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Esconder teclado virtual
            InputMethodManager im_manager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            im_manager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            guardarContacto();
        }
    };

    //-------------------------------

    //MÉTODOS PROPIOS
    private void actualizarBotonGuardarFAB(){

        String input = textInputLayout_Nombre.getEditText().getText().toString();

        //Si existe ya un nombre, mostrar botón flotante de guardar
        if (input.trim().length() != 0){
            fab_Guardar_Contactos.show();
        }else {
            fab_Guardar_Contactos.hide();
        }
    }

    //Guardar contacto en bd
    private void guardarContacto(){

        //Crear objetos ContentValues que contienen info clave-valor de los contactos
        ContentValues contentValues = new ContentValues();

        contentValues.put(BDdescripcion.Contacto.COLUMNA_NOMBRE,
                            textInputLayout_Nombre.getEditText().getText().toString());
        contentValues.put(BDdescripcion.Contacto.COLUMNA_TELEFONO,
                textInputLayout_Telefono.getEditText().getText().toString());
        contentValues.put(BDdescripcion.Contacto.COLUMNA_EMAIL,
                textInputLayout_Email.getEditText().getText().toString());
        contentValues.put(BDdescripcion.Contacto.COLUMNA_CALLE,
                textInputLayout_Calle.getEditText().getText().toString());
        contentValues.put(BDdescripcion.Contacto.COLUMNA_COMUNA,
                textInputLayout_Comuna.getEditText().getText().toString());
        contentValues.put(BDdescripcion.Contacto.COLUMNA_CIUDAD,
                textInputLayout_Ciudad.getEditText().getText().toString());

        if (agregar_nuevo_contacto){
            Uri uriNuevoContcato = getActivity().getContentResolver().
                    insert(BDdescripcion.Contacto.CONTENT_URI, contentValues);
            if (uriNuevoContcato != null){
                Snackbar.make(coordinatorLayout,
                                R.string.contact_added,
                                    Snackbar.LENGTH_LONG).show();
                listenerFAgregarEditar.agregarEditarCompletado(uriContacto);
            }else {
                Snackbar.make(coordinatorLayout,
                                R.string.contact_not_added,
                                    Snackbar.LENGTH_LONG).show();
            }
        }else {
            int filasActualizadas = getActivity().getContentResolver().update(
                                                                            uriContacto,
                                                                            contentValues,
                                                                            null,
                                                                            null
            );
            if (filasActualizadas > 0){
                listenerFAgregarEditar.agregarEditarCompletado(uriContacto);
                Snackbar.make(coordinatorLayout,
                                R.string.contact_updated,
                                    Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(coordinatorLayout,
                                R.string.contact_not_updated,
                                    Snackbar.LENGTH_LONG).show();
            }
        }
    }

    //-------------------------------

    //MÉTODOS DE Fragment

    //Definir FAgregarEditarListener cuando el fragment es agregado
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listenerFAgregarEditar = (FAgregarEditarListener) context;
    }

    //Remover FAgregarEditarListener cuando el fragment es quitado
    @Override
    public void onDetach() {
        super.onDetach();

        listenerFAgregarEditar = null;
    }

    //LLamado cuando la view del Fragment necesita ser creada
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        //Inflar GUI y referenciar a TextViews
        View view = inflater.inflate(R.layout.fragment_agregar_editar, container, false);
        textInputLayout_Nombre = view.findViewById(R.id.textInputLayout_Nombre);
        textInputLayout_Nombre.getEditText().addTextChangedListener(listenerNombreCambiado);
        textInputLayout_Telefono = view.findViewById(R.id.textInputLayout_Telefono);
        textInputLayout_Email = view.findViewById(R.id.textInputLayout_Email);
        textInputLayout_Calle = view.findViewById(R.id.textInputLayout_Calle);
        textInputLayout_Comuna = view.findViewById(R.id.textInputLayout_Comuna);
        textInputLayout_Ciudad = view.findViewById(R.id.textInputLayout_Ciudad);

        //Configurar listener de eventos de FAB
        fab_Guardar_Contactos = view.findViewById(R.id.btn_Agregar);
        fab_Guardar_Contactos.setOnClickListener(botonGuardarContactoClickeado);
        actualizarBotonGuardarFAB();

        //Desplegar SnackBars con mensajes breves
        coordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout);

        Bundle argumentos = getArguments();//Null si se crea nuevo contacto
        if (argumentos != null){
            agregar_nuevo_contacto = false;
            uriContacto = argumentos.getParcelable(MainActivity.URI_CONTACTO);
        }

        //Si se edita un contacto existente, crear Loader para obtener el contacto
        if (uriContacto != null){
            getLoaderManager().initLoader(LOADER_CONTACTO, null, this);
        }

        return view;
    }

    //-------------------------------

    //MÉTODOS DE LoaderManager.LoaderCallbacks<T>

    //Crear Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Hay un solo Loader, por lo que el switch es innecesario, aunque es una buena práctica
        switch (id){
            case LOADER_CONTACTO:
                return new CursorLoader(getActivity(),
                                        uriContacto,
                                        null,
                                        null,
                                        null,
                                        null);
            default:
                return null;
        }
    }

    //Carga completada
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //Si el contacto existe en la bd, desplegar sus datos
        if (data != null && data.moveToFirst()){

            //Obtener index de columnas
            int indexNombre = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_NOMBRE);
            int indexTelefono = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_TELEFONO);
            int indexEmail = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_EMAIL);
            int indexCalle = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_CALLE);
            int indexComuna = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_COMUNA);
            int indexCiudad = data.getColumnIndex(BDdescripcion.Contacto.COLUMNA_CIUDAD);

            //Rellenar EditTexts con datos obtenidos
            textInputLayout_Nombre.getEditText().setText(data.getString(indexNombre));
            textInputLayout_Telefono.getEditText().setText(data.getString(indexTelefono));
            textInputLayout_Email.getEditText().setText(data.getString(indexEmail));
            textInputLayout_Calle.getEditText().setText(data.getString(indexCalle));
            textInputLayout_Comuna.getEditText().setText(data.getString(indexComuna));
            textInputLayout_Ciudad.getEditText().setText(data.getString(indexCiudad));

            actualizarBotonGuardarFAB();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    //-------------------------------

    //INTERFAZ INTERNA INVOCADA POR MAINACTIVITY
    public interface FAgregarEditarListener{

        void agregarEditarCompletado(Uri uriContacto);
    }
}
