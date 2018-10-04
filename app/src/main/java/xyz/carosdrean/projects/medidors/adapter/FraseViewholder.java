package xyz.carosdrean.projects.medidors.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.carosdrean.projects.medidors.R;

public class FraseViewholder extends RecyclerView.ViewHolder {

    private View v;
    private TextView frase;
    public ImageView cerrar = itemView.findViewById(R.id.borrar_frase);

    public FraseViewholder(View itemView) {
        super(itemView);
        v = itemView;
    }

    public void setFrase(String texto) {
        frase = v.findViewById(R.id.texto_frase);
        frase.setText(texto);
    }
}
