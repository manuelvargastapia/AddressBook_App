package cl.manuel.addressbook_app;

/*DESCRIPCIÓN DE LA CLASE:
* Subclase de RecyclerView.Adapter es usada por el RecyclerView de FContacto para ligar la lista
* de contactos al RecyclerView.*/

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class AContacto extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
