package com.example.lab_1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pregunta {
    private String texto;
    private String[] opciones;
    private int respuestaCorrecta;

    public Pregunta(String texto, String[] opciones, int respuestaCorrecta) {
        this.texto = texto;
        this.opciones = opciones.clone();
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public void mezclarOpciones() {
        List<String> listaOpciones = Arrays.asList(opciones);
        Collections.shuffle(listaOpciones);
        opciones = listaOpciones.toArray(new String[0]);
    }

    public String getTexto() {
        return texto;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}