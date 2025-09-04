package com.example.lab_1;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {

    private TextView tvNombreJugador, tvFechaInicio, tvPartidasJugadas;
    private ListView listHistorial;

    private static String nombreJugador = "Jugador 1";
    private static String fechaInicio;
    private static int partidasJugadas = 0;
    private static ArrayList<Partida> historial = new ArrayList<>();
    private HistorialAdapter adapter;
    private static int enCursoIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TeleQuiz");

        tvNombreJugador = findViewById(R.id.tvNombreJugador);
        tvFechaInicio = findViewById(R.id.tvFechaInicio);
        tvPartidasJugadas = findViewById(R.id.tvPartidasJugadas);
        listHistorial = findViewById(R.id.listHistorial);

        if (fechaInicio == null) {
            fechaInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(new Date());
        }

        tvNombreJugador.setText("Jugador: " + nombreJugador);
        tvFechaInicio.setText("Inicio: " + fechaInicio);
        tvPartidasJugadas.setText("Partidas jugadas: " + partidasJugadas);

        adapter = new HistorialAdapter();
        listHistorial.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // <- esto regresa al Quiz o Resultado
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void registrarPartida(String tematica, int puntaje, String tiempo) {
        int color = puntaje >= 0 ? Color.GREEN : Color.RED;
        String detalle = "Tiempo: " + tiempo + " | Puntaje: " + puntaje;

        if (enCursoIndex != -1) {
            // Si había en curso, la actualizamos
            Partida p = historial.get(enCursoIndex - 1);
            p.titulo = "Juego " + enCursoIndex + ": " + tematica;
            p.detalle = detalle;
            p.color = color;
            enCursoIndex = -1;
        } else {
            // Por seguridad, si no había en curso, creamos nueva
            partidasJugadas++;
            String titulo = "Juego " + partidasJugadas + ": " + tematica;
            historial.add(new Partida(titulo, detalle, color));
        }
    }

    public static void registrarCancelada() {
        if (enCursoIndex != -1) {
            // Si había en curso, la marcamos como cancelada
            Partida p = historial.get(enCursoIndex - 1);
            p.detalle = "Sin puntaje | Canceló";
            p.color = Color.GRAY;
            enCursoIndex = -1;
        } else {
            // Si no había en curso, igual creamos nueva entrada
            partidasJugadas++;
            String titulo = "Juego " + partidasJugadas + ": Canceló";
            historial.add(new Partida(titulo, "Sin puntaje | Canceló", Color.GRAY));
        }
    }

    public static void registrarEnCurso(String tematica, String horaInicio) {
        if (enCursoIndex != -1) {
            // Ya hay una en curso: solo actualizamos datos
            Partida p = historial.get(enCursoIndex - 1);
            p.titulo = "Juego " + enCursoIndex + ": " + tematica;
            p.detalle = "Inicio: " + horaInicio + " | En curso";
            p.color = 0xFFFFC107; // Amarillo
            return;
        }

        partidasJugadas++;
        enCursoIndex = partidasJugadas;
        String titulo = "Juego " + enCursoIndex + ": " + tematica;
        String detalle = "Inicio: " + horaInicio + " | En curso";
        historial.add(new Partida(titulo, detalle, 0xFFFFC107));
    }

    private static class Partida {
        String titulo;
        String detalle;
        int color;

        Partida(String titulo, String detalle, int color) {
            this.titulo = titulo;
            this.detalle = detalle;
            this.color = color;
        }
    }

    private class HistorialAdapter extends ArrayAdapter<Partida> {
        HistorialAdapter() {
            super(ProfileActivity.this, android.R.layout.simple_list_item_2, historial);
        }

        @Override
        public android.view.View getView(int position, android.view.View convertView,
                                         android.view.ViewGroup parent) {
            Partida partida = getItem(position);

            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_2, parent, false);
            }

            TextView titulo = convertView.findViewById(android.R.id.text1);
            TextView detalle = convertView.findViewById(android.R.id.text2);

            titulo.setText(partida.titulo);
            detalle.setText(partida.detalle);

            // Colorear según estado
            titulo.setTextColor(partida.color);
            detalle.setTextColor(partida.color);

            return convertView;
        }
    }
}
