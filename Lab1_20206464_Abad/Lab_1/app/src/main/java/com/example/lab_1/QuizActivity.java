package com.example.lab_1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView tvTematica;
    private TextView tvPuntaje;
    private TextView tvPregunta;
    private Button btnOpcion1;
    private Button btnOpcion2;
    private Button btnOpcion3;
    private Button btnOpcion4;
    private Button btnAnterior;
    private Button btnSiguiente;
    private Button btnPista;

    private String tematica;
    private int preguntaActual = 0;
    private int puntaje = 0;
    private String[][] preguntas;
    private int[] respuestasCorrectas;
    private int[] respuestasUsuario;
    private boolean[] preguntasRespondidas;
    private boolean[] pistaUsada;
    private int pistasRestantes = 3;
    private int[] puntajesPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("TeleQuiz");
        }

        tematica = getIntent().getStringExtra("tematica");

        tvTematica = findViewById(R.id.tvTematica);
        tvPuntaje = findViewById(R.id.tvPuntaje);
        tvPregunta = findViewById(R.id.tvPregunta);
        btnOpcion1 = findViewById(R.id.btnOpcion1);
        btnOpcion2 = findViewById(R.id.btnOpcion2);
        btnOpcion3 = findViewById(R.id.btnOpcion3);
        btnOpcion4 = findViewById(R.id.btnOpcion4);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnPista = findViewById(R.id.btnPista);

        tvTematica.setText(tematica);

        inicializarPreguntas();
        mostrarPregunta();

        btnOpcion1.setOnClickListener(v -> responderPregunta(1));
        btnOpcion2.setOnClickListener(v -> responderPregunta(2));
        btnOpcion3.setOnClickListener(v -> responderPregunta(3));
        btnOpcion4.setOnClickListener(v -> responderPregunta(4));

        btnAnterior.setOnClickListener(v -> {
            if (preguntaActual > 0) {
                preguntaActual--;
                mostrarPregunta();
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            if (preguntaActual < preguntas.length - 1) {
                preguntaActual++;
                mostrarPregunta();
            } else if (todasPreguntasRespondidas()) {
                mostrarResultadoFinal();
            }
        });

        btnPista.setOnClickListener(v -> usarPista());
    }

    private void inicializarPreguntas() {
        if ("Redes".equals(tematica)) {
            preguntas = new String[][]{
                    {"¿En qué rango de frecuencias suelen operar las redes Wi-Fi?", "10 GHz y 20 GHz", "2.4 GHz y 5 GHz", "900 MHz y 1.8 GHz", "440 MHz y 550 MHz"},
                    {"¿Qué significa TCP?", "Transfer Control Protocol", "Transmission Control Protocol", "Transport Control Protocol", "Technical Control Protocol"},
                    {"¿Cuál es la dirección IP de localhost?", "192.168.1.1", "127.0.0.1", "10.0.0.1", "255.255.255.255"},
                    {"¿Qué puerto usa HTTP por defecto?", "21", "80", "443", "25"},
                    {"¿Qué protocolo es más seguro?", "HTTP", "HTTPS", "FTP", "TELNET"},
                    {"¿Cuántos bits tiene una dirección IPv4?", "16", "32", "64", "128"},
                    {"¿Qué significa DNS?", "Dynamic Name System", "Domain Name System", "Data Name System", "Digital Name System"}
            };
            respuestasCorrectas = new int[]{2, 2, 2, 2, 2, 2, 2};
        } else if ("CiberSeguridad".equals(tematica)) {
            preguntas = new String[][]{
                    {"¿Qué es un firewall?", "Un virus", "Un sistema de seguridad", "Un navegador", "Un protocolo"},
                    {"¿Qué significa phishing?", "Pescar", "Robo de identidad", "Navegación", "Programación"},
                    {"¿Qué es malware?", "Software bueno", "Software malicioso", "Hardware", "Red social"},
                    {"¿Qué es una VPN?", "Red privada virtual", "Virus", "Protocolo web", "Base de datos"},
                    {"¿Qué significa DDoS?", "Distributed Denial of Service", "Data Driven Operations", "Dynamic Data Output", "Digital Defense System"},
                    {"¿Qué es la criptografía?", "Escritura oculta", "Arte de cifrar información", "Lenguaje de programación", "Sistema operativo"},
                    {"¿Qué es un antivirus?", "Un virus", "Software de protección", "Hardware", "Protocolo"}
            };
            respuestasCorrectas = new int[]{2, 2, 2, 1, 1, 2, 2};
        } else {
            preguntas = new String[][]{
                    {"¿En qué rango de frecuencias suelen operar las redes Wi-Fi?", "10 GHz y 20 GHz", "2.4 GHz y 5 GHz", "900 MHz y 1.8 GHz", "440 MHz y 550 MHz"},
                    {"¿Qué frecuencia usan los hornos microondas?", "1.8 GHz", "2.4 GHz", "5 GHz", "900 MHz"},
                    {"¿Qué tipo de radiación usan las microondas?", "Radiación ionizante", "Radiación no ionizante", "Rayos X", "Radiación gamma"},
                    {"¿Para qué se usan las microondas en telecomunicaciones?", "Solo para cocinar", "Comunicaciones punto a punto", "Navegación", "Televisión"},
                    {"¿Qué ventaja tienen las microondas en comunicaciones?", "Baja frecuencia", "Alta capacidad de datos", "Poco alcance", "Mucho ruido"},
                    {"¿Cuál es una aplicación de microondas?", "Radio AM", "Radar", "Televisión análoga", "Teléfonos fijos"},
                    {"¿Las microondas pueden atravesar obstáculos?", "Sí, fácilmente", "No, se bloquean fácilmente", "Solo metales", "Solo agua"}
            };
            respuestasCorrectas = new int[]{2, 2, 2, 2, 2, 2, 2};
        }

        respuestasUsuario = new int[preguntas.length];
        preguntasRespondidas = new boolean[preguntas.length];
        pistaUsada = new boolean[preguntas.length];
        puntajesPregunta = new int[preguntas.length];
    }

    private void responderPregunta(int opcion) {
        if (preguntasRespondidas[preguntaActual]) return;

        respuestasUsuario[preguntaActual] = opcion;
        preguntasRespondidas[preguntaActual] = true;

        boolean esCorrecta = opcion == respuestasCorrectas[preguntaActual];
        int puntajePregunta = esCorrecta ? 2 : -3;

        int consecutivas = calcularConsecutivas(preguntaActual);
        if (consecutivas > 1) {
            puntajePregunta *= consecutivas;
        }

        puntajesPregunta[preguntaActual] = puntajePregunta;
        puntaje += puntajePregunta;

        tvPuntaje.setText("Puntaje " + puntaje);
        mostrarPregunta();

        if (todasPreguntasRespondidas()) {
            btnSiguiente.setText("FINALIZAR");
            btnSiguiente.setEnabled(true);
        }
    }

    private int calcularConsecutivas(int indice) {
        if (indice == 0 || !preguntasRespondidas[indice - 1]) return 1;

        boolean actualCorrecta = respuestasUsuario[indice] == respuestasCorrectas[indice];
        boolean anteriorCorrecta = respuestasUsuario[indice - 1] == respuestasCorrectas[indice - 1];

        if (actualCorrecta == anteriorCorrecta) {
            return calcularConsecutivas(indice - 1) + 1;
        } else {
            return 1;
        }
    }

    private boolean todasPreguntasRespondidas() {
        for (boolean r : preguntasRespondidas) if (!r) return false;
        return true;
    }

    private void mostrarResultadoFinal() {
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("puntaje", puntaje);
        intent.putExtra("tematica", tematica);
        startActivity(intent);
        finish();
    }

    private void usarPista() {
        if (pistasRestantes <= 0) {
            Toast.makeText(this, "No quedan pistas disponibles", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pistaUsada[preguntaActual]) {
            Toast.makeText(this, "Ya usaste una pista en esta pregunta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (preguntasRespondidas[preguntaActual]) {
            Toast.makeText(this, "Ya respondiste esta pregunta", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Integer> opcionesIncorrectas = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            if (i != respuestasCorrectas[preguntaActual]) opcionesIncorrectas.add(i);
        }
        if (!opcionesIncorrectas.isEmpty()) {
            Random random = new Random();
            int opcionADescartar = opcionesIncorrectas.get(random.nextInt(opcionesIncorrectas.size()));
            Button[] botones = {btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4};
            botones[opcionADescartar - 1].setEnabled(false);
            botones[opcionADescartar - 1].setAlpha(0.5f);
            pistasRestantes--;
            pistaUsada[preguntaActual] = true;
            btnPista.setText("PISTAS: " + pistasRestantes);
        }
    }

    private void mostrarPregunta() {
        String[] preguntaArray = preguntas[preguntaActual];
        String textoPregunta = (preguntaActual + 1) + ". " + preguntaArray[0];
        if (preguntasRespondidas[preguntaActual]) {
            int p = puntajesPregunta[preguntaActual];
            textoPregunta += " (Puntaje: " + (p > 0 ? "+" : "") + p + ")";
        }
        tvPregunta.setText(textoPregunta);

        btnOpcion1.setText(preguntaArray[1]);
        btnOpcion2.setText(preguntaArray[2]);
        btnOpcion3.setText(preguntaArray[3]);
        btnOpcion4.setText(preguntaArray[4]);

        Button[] botones = {btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4};
        for (Button boton : botones) {
            boton.setEnabled(!preguntasRespondidas[preguntaActual]);
            boton.setAlpha(1f);
        }

        colorearOpciones();
        btnAnterior.setEnabled(preguntaActual > 0);

        if (preguntasRespondidas[preguntaActual]) {
            btnSiguiente.setEnabled(true);
            btnSiguiente.setText(preguntaActual == preguntas.length - 1 ? "FINALIZAR" : "Siguiente");
        } else {
            btnSiguiente.setEnabled(false);
            btnSiguiente.setText("Siguiente");
        }

        btnPista.setText("PISTAS: " + pistasRestantes);
    }

    private void colorearOpciones() {
        Button[] botones = {btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4};
        for (int i = 0; i < 4; i++) {
            if (preguntasRespondidas[preguntaActual]) {
                if (i + 1 == respuestasCorrectas[preguntaActual]) {
                    botones[i].setBackgroundTintList(ColorStateList.valueOf(0xFF4CAF50));
                } else if (i + 1 == respuestasUsuario[preguntaActual]) {
                    botones[i].setBackgroundTintList(ColorStateList.valueOf(0xFFF44336));
                } else {
                    botones[i].setBackgroundTintList(ColorStateList.valueOf(0xFFE0E0E0));
                }
            } else {
                botones[i].setBackgroundTintList(ColorStateList.valueOf(0xFFE0E0E0));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_profile) {
            String horaInicio = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            ProfileActivity.registrarEnCurso(tematica, horaInicio);
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            ProfileActivity.registrarCancelada();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ProfileActivity.registrarCancelada();
        super.onBackPressed();
    }
}
