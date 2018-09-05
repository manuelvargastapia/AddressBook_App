package cl.manuel.addressbook_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*Clase que define los dividers entre los ítems de RecyclerView.
 * Ver sample de implementación: bit.ly/DividerItemDecoration*/

public class DItem extends RecyclerView.ItemDecoration{

    private final Drawable divider;

    //Constructor: carga lista de ítems divider de fábrica de Android
    public DItem(Context context){

        int[] attrs = {android.R.attr.listDivider};
        divider = context.obtainStyledAttributes(attrs).getDrawable(0);
    }

    /*DIBUJAR LA LISTA DE ÍTEMS DIVIDER EN RECYCLERVIEW*/

    /*A medida que el usuario scrollea a través de los ítems del RecyclerView, sus contenidos
    van siendo redibujados para mostrar los ítems en sus nuevas posiciones en la pantalla.
    Para ello, RecyclerView llama a onDrawOver() de su subclase RecyclerView.ItemDecoration.*/

    /*onDrawOver() requiere: un Canvas para dibujar en el RecyclerView, el RecyclerView donde el
    * Canvas dibujará, y el RecyclerView.State, que almacena info pasada entre varios componentes
    * RecyclerView, aunque en esta ocasión solo se le pasa a super (el onDrawOver de la superclase).*/
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        //Calcular coordenadas x (izq/der) de cada divider
        int izq = parent.getPaddingLeft();
        int der = parent.getWidth() - parent.getPaddingRight();

        //Dibujar una línea debajo de cada ítem, salvo por el último (comuna)
        for(int i = 0; i < parent.getChildCount() - 1; ++i){

            View item = parent.getChildAt(i); //Obtener divider actual en iteración

            //Calcular coordenadas y (arriba/abajo) de cada ítem divider
            int arriba = item.getBottom() + ((RecyclerView.LayoutParams)item.getLayoutParams()).bottomMargin;
            int abajo = arriba + divider.getIntrinsicHeight();

            //Dibujar divider (línea) con los valores obtenidos
            divider.setBounds(izq, arriba, der, abajo);
            divider.draw(c);
        }
    }
}
