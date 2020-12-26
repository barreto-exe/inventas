package com.teamihc.inventas.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.teamihc.inventas.R;
import com.teamihc.inventas.backend.Estadisticas;
import com.teamihc.inventas.backend.Herramientas;
import com.teamihc.inventas.backend.entidades.Articulo;
import com.teamihc.inventas.backend.entidades.Venta;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static com.teamihc.inventas.backend.Herramientas.getCompressedBitmapImage;

public class EstadisticasFragment extends Fragment
{
    private View view;
    private TextView descripcionMasVendido, precioBsMasVendido, cantidadMasVendido, diaMasVentas, ventas_diaMasVentas;
    private TextView diaMasIngresos, ingresos_diaMasIngresos, descripcionMenosVendido, precioBsMenosVendido, cantidadMenosVendido, diaMenosVentas;
    private TextView ventas_diaMenosVentas, diaMenosIngresos, ingresos_diaMenosIngresos, ingresoTotal, gananciaTotal;
    private ImageView imagenMasVendido, imagenMenosVendido;
    
    private Spinner desicion;
    ArrayList<BarEntry> cambioVenta = new ArrayList<>();
    int[] listaVenta = new int[7];
    float[] listaIngresos = new float[7];
    private int size = Estadisticas.obtenerTamanoSemana(Calendar.getInstance().getTime());
    String[] opciones = {"Número de ventas", "Ingreso en dólares", "Ganancia en dólares"};
    String[] diasSemana = {"D", "L", "M", "M", "J", "V", "S"};
    BarChart barChart;
    BarDataSet barDataSet;
    String diaMasV;
    String diaMasI;
    String diaMenosV;
    String diaMenosI;
    float ingresoT, gananciaT;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        init();
        barChart = view.findViewById(R.id.estadisticasChart);
        spinnerLlenado();
        seleccionSpinner();
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        refrescarEstadisticas();
        barChart.notifyDataSetChanged();
        barChart.invalidate();
    }
    
    
    /**
     * LLenado de gráficos
     */
    public void llenarChartVentas()
    {
        Toast.makeText(getActivity(), "Venta", Toast.LENGTH_SHORT);
        cambioVenta = new ArrayList<BarEntry>();
        for (int i = 0; i < 7; i++)
        {
            BarEntry b = new BarEntry(i, listaVenta[i]);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter()
            {
                @Override
                public String getFormattedValue(float value)
                {
                    return diasSemana[(int) value];
                }
            });
            cambioVenta.add(b);
        }
        
        
        barDataSet = new BarDataSet(cambioVenta, "Cantidad de ventas");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
    public void llenarChartIngresos()
    {
        Toast.makeText(getActivity(), "Ingresos", Toast.LENGTH_SHORT);
        cambioVenta.clear();
        barChart.invalidate();
        barChart.clear();
        for (int i = 0; i < 7; i++)
        {
            BarEntry b = new BarEntry(i, listaIngresos[i]);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter()
            {
                @Override
                public String getFormattedValue(float value)
                {
                    return diasSemana[(int) value];
                }
            });
            cambioVenta.add(b);
        }
        barChart.invalidate();
        barDataSet = new BarDataSet(cambioVenta, "Ingresos diarios");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
    public void llenarChartGanancias()
    {
        Toast.makeText(getActivity(), "Ganancias", Toast.LENGTH_SHORT);
        cambioVenta = new ArrayList<BarEntry>();
        for (int i = 0; i < 7; i++)
        {
            BarEntry b = new BarEntry(i, listaIngresos[i]);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter()
            {
                @Override
                public String getFormattedValue(float value)
                {
                    return diasSemana[(int) value];
                }
            });
            cambioVenta.add(b);
        }
        barDataSet = new BarDataSet(cambioVenta, "Ganancias diarias");
        barDataSet.setColor(getResources().getColor(R.color.bars));
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setDrawGridLines(false);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }
    
    
    /**
     * Tratado del spinner
     */
    public void spinnerLlenado()
    {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>
                        (
                                getContext(),
                                R.layout.view_spinner_estadistica,
                                opciones
                        );
        desicion.setAdapter(adapter);
        
        desicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                    {
                        llenarChartVentas();
                        break;
                    }
                    case 1:
                    {
                        llenarChartIngresos();
                        break;
                    }
                    case 2:
                    {
                        llenarChartGanancias();
                        break;
                    }
                }
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            
            }
        });
    }
    
    
    /**
     * LLena el gráfico a mostrar
     */
    public void seleccionSpinner()
    {
        String seleccion = desicion.getSelectedItem().toString();
        
        if (seleccion.equals(opciones[0]))
        {
            llenarChartVentas();
            
            barChart.notifyDataSetChanged();
            barChart.invalidate();
        }
        if (seleccion.equals(opciones[1]))
        {
            llenarChartIngresos();
            barChart.notifyDataSetChanged();
            barChart.invalidate();
            
        }
        if (seleccion.equals(opciones[2]))
        {
            llenarChartGanancias();
            barChart.notifyDataSetChanged();
            barChart.invalidate();
            
        }
        
    }
    public void vacios()
    {
        descripcionMasVendido.setText("-");
        //  precioBsMasVendido.setText("-");
        cantidadMasVendido.setText("-");
        imagenMasVendido.setImageResource(R.color.colorPrimary);
        descripcionMenosVendido.setText("-");
        //   precioBsMenosVendido.setText("-");
        cantidadMenosVendido.setText("-");
        imagenMenosVendido.setImageResource(R.color.colorPrimary);
    }
    
    
    /**
     * Se actualzan los datos dentro del fragment
     */
    private void refrescarEstadisticas()
    {
        Estadisticas.calcularVentasDiaria(listaVenta, size);
        Estadisticas.calcularIngresoDiario(listaIngresos, size);
        Estadisticas.calcularGananciaDiaria(listaIngresos, size);

        Date[] semana = Estadisticas.limiteSemana();
        gananciaT = Estadisticas.gananciaTotalSemanal(semana[0], semana[1]);
        ingresoT = Estadisticas.ingresoTotalSemanal(semana[0], semana[1]);
        Object[] objMas = Estadisticas.articuloMasVendido(semana[0], semana[1]);
        
        Object[] objMenos = Estadisticas.articuloMenosVendido(semana[0], semana[1]);
        Articulo masV, menosV;

        Object[] objMasVentas = Estadisticas.diaMayorCantVentas(semana[0], semana[1]);
        Object[] objMenosVentas = Estadisticas.diaMenorCantVentas(listaVenta, size);

        Object[] objMasIngresos = Estadisticas.diaMayorIngreso(semana[0], semana[1]);
        Object[] objMenosIngresos = Estadisticas.diaMenorIngreso(listaIngresos, size);

        //verifico se hay ventas o articulos registrados, si no hay, todo se pone en blanco
        if (Articulo.cantidadArticulosRegistrados() > 0 && Venta.cantidadVentasRegistradas() > 0)
        {
            if (objMas != null && objMenos != null)
            {
                // reviso si los Articulos mas y menos vendidos son distintos de null, sino todo va en blanco
                masV = (Articulo) objMas[0]; //asigno valores
                menosV = (Articulo) objMenos[0];
                // aqui verifico si existe el articulo mas vendido, es decir, si no ha sido eliminado
                if (masV != null)
                {
                    descripcionMasVendido.setText(masV.getDescripcion());
                    cantidadMasVendido.setText(((int) objMas[1]) + " unidades.");
                    if (!masV.getImagen_path().equals(""))
                    {
                        Glide.with(view).load(masV.getImagen_path()).into(imagenMasVendido);
                    }
                }
                //verifico que el mas vendido no sea igual al menos vendido
                if (menosV != null && !(menosV.getDescripcion().equals(masV.getDescripcion())))
                {
                    descripcionMenosVendido.setText(menosV.getDescripcion());
                    cantidadMenosVendido.setText(((int) objMenos[1]) + " unidades.");
                    if (!menosV.getImagen_path().equals(""))
                    {
                        Glide.with(view).load(menosV.getImagen_path()).into(imagenMenosVendido);
                    }
                }
                else
                {
                    descripcionMenosVendido.setText("-");
                    cantidadMenosVendido.setText("-");
                    imagenMenosVendido.setImageResource(R.color.colorPrimary);
                }
            }
            else
            {
                vacios();
            }
        }
        else
        {
            vacios();
        }
        
        
        if (gananciaT > 0)
        {
            gananciaTotal.setText(Herramientas.formatearMonedaDolar(gananciaT));
        }
        else
        {
            gananciaTotal.setText("-");
        }
        
        if (ingresoT > 0)
        {
            ingresoTotal.setText(Herramientas.formatearMonedaDolar(ingresoT));
        }
        else
        {
            ingresoTotal.setText("-");
        }

        if (objMasVentas != null)
        {
            diaMasV = (String) objMasVentas[0];
            int mayorCantVentas = (int) objMasVentas[1];

            if (mayorCantVentas > 0)
            {
                diaMasVentas.setText(diaMasV);
                ventas_diaMasVentas.setText(mayorCantVentas + " ventas.");
            }
            else
            {
                diaMasVentas.setText("-");
                ventas_diaMasVentas.setText("-");
            }
        }
        else
        {
            diaMasVentas.setText("-");
            ventas_diaMasVentas.setText("-");
        }

        if (objMasIngresos != null)
        {
            diaMasI = (String) objMasIngresos[0];
            float mayorIngreso = (float) objMasIngresos[1];

            if (mayorIngreso > 0)
            {
                diaMasIngresos.setText(diaMasI);
                ingresos_diaMasIngresos.setText(Herramientas.formatearMonedaDolar(mayorIngreso));
            }
            else
            {
                diaMasIngresos.setText("-");
                ingresos_diaMasIngresos.setText("-");
            }
        }
        else
        {
            diaMasIngresos.setText("-");
            ingresos_diaMasIngresos.setText("-");
        }

        if (objMenosVentas != null)
        {
            diaMenosV = (String) objMenosVentas[0];

            if (!diaMenosV.equals(diaMasV) && !diaMasVentas.getText().equals("-"))
            {
                int menorCantVentas = (int) objMenosVentas[1];
                diaMenosVentas.setText(diaMenosV);
                ventas_diaMenosVentas.setText(menorCantVentas + " ventas.");
            }
            else
            {
                diaMenosVentas.setText("-");
                ventas_diaMenosVentas.setText("-");
            }
        }
        else
        {
            diaMenosVentas.setText("-");
            ventas_diaMenosVentas.setText("-");
        }

        if (objMenosIngresos != null)
        {
            diaMenosI = (String) objMenosIngresos[0];

            if (!diaMenosI.equals(diaMasI) && !diaMasIngresos.getText().equals("-"))
            {
                float menosIngreso = (float) objMenosIngresos[1];
                diaMenosIngresos.setText(diaMenosI);
                ingresos_diaMenosIngresos.setText(Herramientas.formatearMonedaDolar(menosIngreso));
            }
            else
            {
                diaMenosIngresos.setText("-");
                ingresos_diaMenosIngresos.setText("-");
            }
        }
        else
        {
            diaMenosIngresos.setText("-");
            ingresos_diaMenosIngresos.setText("-");
        }
    }
    
    /**
     * Inicializando todos los elementos de la parte grafica
     */
    public void init()
    {
        descripcionMasVendido = (TextView) view.findViewById(R.id.descripcionMasVendido);
        // precioBsMasVendido = (TextView) view.findViewById(R.id.precioBsMasVendido);
        cantidadMasVendido = (TextView) view.findViewById(R.id.cantidadMasVendido);
        imagenMasVendido = (ImageView) view.findViewById(R.id.imagenMasVendido);
        
        
        diaMasVentas = (TextView) view.findViewById(R.id.diaMasVentas);
        ventas_diaMasVentas = (TextView) view.findViewById(R.id.ventas_diaMasVentas);
        
        diaMasIngresos = (TextView) view.findViewById(R.id.diaMasIngresos);
        ingresos_diaMasIngresos = (TextView) view.findViewById(R.id.ingresos_diaMasIngresos);
        
        descripcionMenosVendido = (TextView) view.findViewById(R.id.descripcionMenosVendido);
        //   precioBsMenosVendido = (TextView) view.findViewById(R.id.precioBsMenosVendido);
        cantidadMenosVendido = (TextView) view.findViewById(R.id.cantidadMenosVendido);
        imagenMenosVendido = (ImageView) view.findViewById(R.id.imagenMenosVendido);
        
        diaMenosVentas = (TextView) view.findViewById(R.id.diaMenosVentas);
        ventas_diaMenosVentas = (TextView) view.findViewById(R.id.ventas_diaMenosVentas);
        
        diaMenosIngresos = (TextView) view.findViewById(R.id.diaMenosIngresos);
        ingresos_diaMenosIngresos = (TextView) view.findViewById(R.id.ingresos_diaMenosIngresos);
        
        ingresoTotal = (TextView) view.findViewById(R.id.ingresoTotal);
        gananciaTotal = (TextView) view.findViewById(R.id.gananciaTotal);
        
        desicion = (Spinner) view.findViewById(R.id.decision);
    }
}
