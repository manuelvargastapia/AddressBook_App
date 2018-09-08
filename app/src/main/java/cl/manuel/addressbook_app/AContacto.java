package cl.manuel.addressbook_app;

/*DESCRIPCIÓN DE LA CLASE:
* Subclase de RecyclerView.Adapter es usada por el RecyclerView de FContacto para ligar la lista
* de contactos al RecyclerView.*/

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cl.manuel.addressbook_app.Datos.BDdescripcion.Contacto;

public class AContacto extends RecyclerView.Adapter<AContacto.ViewHolder>{

    //CONSTRUCTOR
    public AContacto(AContactoClickListener clickListener){

        this.clickListener = clickListener;
    }

    //--------------------------

    //CLASE ANIDADA, SUBCLASE DE RECYLCERVIEW.VIEWHOLDER, USADA PARA IMPLEMENTAR PATRÓN
    //VIEW-HOLDER EN EL CONTEXTO DE RECYCLERVIEW
    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView textView;
        private long idFila;

        //Configurar ViewHolder del ítem de RecyclerView
        public ViewHolder(View itemView){
            super(itemView);

            textView = itemView.findViewById(android.R.id.text1);

            //Agregar listener a itemView
            itemView.setOnClickListener(new View.OnClickListener(){

                //Se ejecuta cuando se clickea el contacto en este ViewHolder
                @Override
                public void onClick(View view) {
                    clickListener.onClick(Contacto.crearUriContacto(idFila));
                }
            });
        }

        //Definir id de la fila en la bd para el contacto en este ViewHolder
        public void definirIDFila(long idFila){
            this.idFila = idFila;
        }
    }

    //--------------------------

    //INTERFAZ INTERNA PARA RESPONDER A EVENTOS DE TOQUE EN RECYCLERVIRE
    public interface AContactoClickListener{

        void onClick(Uri uriContacto);
    }

    //--------------------------

    //MÉTODOS DE RECYCLERVIEW.ADAPTER

    //Definir nuevos ítems y sus viewholders
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(view);
    }

    //Definir el texto del ítem de la lista para mostrar la etiqueta de búsqueda
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        cursor.moveToPosition(position);
        holder.definirIDFila(cursor.getLong(cursor.getColumnIndex(Contacto._ID)));
        holder.textView.setText(cursor.getString(cursor.getColumnIndex(Contacto.COLUMNA_NOMBRE)));
    }

    //Retornar número de ítems que el adaptador a ligado
    @Override
    public int getItemCount() {

        return (cursor != null) ? cursor.getCount() : 0;
    }

    //Cambiar el actual cursor por uno nuevo
    public void cambiarCursor(Cursor cursor){

        this.cursor = cursor;
        notifyDataSetChanged();
    }

    //--------------------------

    //Variables de clase
    private Cursor cursor = null;
    private final AContactoClickListener clickListener;
}
