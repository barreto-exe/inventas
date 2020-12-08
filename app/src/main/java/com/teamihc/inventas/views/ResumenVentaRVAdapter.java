package com.teamihc.inventas.views;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.teamihc.inventas.R;
import com.teamihc.inventas.activities.CarritoActivity;
import com.teamihc.inventas.activities.CrearProductoActivity;
import com.teamihc.inventas.activities.FacturaActivity;
import com.teamihc.inventas.activities.MainActivity;
import com.teamihc.inventas.backend.entidades.Venta;
import java.util.ArrayList;

public class ResumenVentaRVAdapter extends RecyclerView.Adapter<ResumenVentaRVAdapter.ResumenVentaAdapter>
        /*implements View.OnClickListener*/{

    private View.OnClickListener listener;
    private ArrayList<Venta> listaVenta;



    //Constructor
    public ResumenVentaRVAdapter(ArrayList<Venta> listaVenta) {
       // this.listaVenta = listaVenta;
    }

    @NonNull
    @Override
    public ResumenVentaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_info_venta, parent, false);
      //   view.setOnClickListener(this); /*esto lo pones cuando tengas lo de la factura para que al tocar el cardView vayas a la otra ventana*/
        return new ResumenVentaAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResumenVentaAdapter holder, int position) {
       // holder.asignarDatos(listaVenta.get(position));
    }

    @Override
    public int getItemCount() {
    //    return listaVenta.size();
        return 0;
    }

    @Override
    public void onClick(View v) {
        //Lleva a la factura, no estoy muy segura de esto, gustavo puede que sepa un chin mas
        MainActivity mainActivity = ((MainActivity) v.getContext());
        Intent intent = new Intent(mainActivity,FacturaActivity.class);
        //intent.putExtra("descripcion", descripcion.getText().toString());
        mainActivity.startActivity(intent);
    }

    public class ResumenVentaAdapter extends RecyclerView.ViewHolder {

        CardView cardView;
        public ResumenVentaAdapter(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.infoVenta);
        }

        public void asignarDatos(Venta venta)
        {
            TextView hora = (TextView) cardView.findViewById(R.id.hora);
            TextView ventaBsS = (TextView) cardView.findViewById(R.id.ventaBsS);
            TextView resumen = (TextView) cardView.findViewById(R.id.resumen);
            TextView ventaD = (TextView) cardView.findViewById(R.id.ventaD);
/*

            hora.setText((CharSequence) venta.getFechaHora());
            //resumen.setText("" + venta.el campo del resumen);
          //  ventaD.setText("" + venta. el campo de cuanto se vendio en dolares );
            //   ventaBsS.setText("" + venta. el campo de cuanto se vendió en bolivares);
*/
        }
    }
}
