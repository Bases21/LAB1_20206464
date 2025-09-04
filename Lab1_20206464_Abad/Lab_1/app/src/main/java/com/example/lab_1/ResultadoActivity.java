package com.example.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    private TextView tvTematica;
    private TextView tvPuntajeTotal;
    private TextView tvPuntaje;
    private Button btnVolverJugar;
    private Button btnAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TeleQuiz");

        tvTematica = findViewById(R.id.tvTematica);
        tvPuntajeTotal = findViewById(R.id.tvPuntajeTotal);
        tvPuntaje = findViewById(R.id.tvPuntaje);
        btnVolverJugar = findViewById(R.id.btnVolverJugar);
        btnAnterior = findViewById(R.id.btnAnterior);

        String tematica = getIntent().getStringExtra("tematica");
        int puntaje = getIntent().getIntExtra("puntaje", 0);

        tvTematica.setText(tematica);
        tvPuntajeTotal.setText("Puntaje Total:");
        tvPuntaje.setText(String.valueOf(puntaje));

        if (puntaje >= 0) {
            tvPuntaje.setBackgroundColor(0xFF4CAF50);
        } else {
            tvPuntaje.setBackgroundColor(0xFFF44336);
        }
        ProfileActivity.registrarPartida(tematica, puntaje, "30s");

        btnVolverJugar.setOnClickListener(v -> {
            Intent intent = new Intent(ResultadoActivity.this, GameSelectionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });


        btnAnterior.setOnClickListener(v -> finish());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}