package com.example.lab_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameSelectionActivity extends AppCompatActivity {

    private TextView tvNombreUsuario;
    private Button btnRedes;
    private Button btnCiberSeguridad;
    private Button btnMicroondas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        if (getSupportActionBar() != null) {
            getSupportActionBar().show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("TeleQuiz");
        }

        String nombre = getIntent().getStringExtra("nombre");

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        btnRedes = findViewById(R.id.btnRedes);
        btnCiberSeguridad = findViewById(R.id.btnCiberSeguridad);
        btnMicroondas = findViewById(R.id.btnMicroondas);

        tvNombreUsuario.setText(nombre);

        btnRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz("Redes");
            }
        });

        btnCiberSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz("CiberSeguridad");
            }
        });

        btnMicroondas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarQuiz("Microondas");
            }
        });
    }

    private void iniciarQuiz(String tematica) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("tematica", tematica);
        startActivity(intent);
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
            String nombre = getIntent().getStringExtra("nombre");
            intent.putExtra("nombre", nombre);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}