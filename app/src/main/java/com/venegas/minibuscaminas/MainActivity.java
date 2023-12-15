package com.venegas.minibuscaminas;

import static android.app.ProgressDialog.show;
import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontsContractCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView paso;

    int ganas = 22;
    String pasar;
    int puntos = 0;

    Button[][] botones = new Button[5][5];

    int fila1, colum1, fila2, colum2, fila3, colum3;

    boolean GameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paso = findViewById(R.id.textView2);

        botones[0][0]= findViewById(R.id.button00);
        botones[0][1]= findViewById(R.id.button01);
        botones[0][2]= findViewById(R.id.button02);
        botones[0][3]= findViewById(R.id.button03);
        botones[0][4]= findViewById(R.id.button04);
        botones[1][0]= findViewById(R.id.button10);
        botones[1][1]= findViewById(R.id.button11);
        botones[1][2]= findViewById(R.id.button12);
        botones[1][3]= findViewById(R.id.button13);
        botones[1][4]= findViewById(R.id.button14);
        botones[2][0]= findViewById(R.id.button20);
        botones[2][1]= findViewById(R.id.button21);
        botones[2][2]= findViewById(R.id.button22);
        botones[2][3]= findViewById(R.id.button23);
        botones[2][4]= findViewById(R.id.button24);
        botones[3][0]= findViewById(R.id.button30);
        botones[3][1]= findViewById(R.id.button31);
        botones[3][2]= findViewById(R.id.button32);
        botones[3][3]= findViewById(R.id.button33);
        botones[3][4]= findViewById(R.id.button34);
        botones[4][0]= findViewById(R.id.button40);
        botones[4][1]= findViewById(R.id.button41);
        botones[4][2]= findViewById(R.id.button42);
        botones[4][3]= findViewById(R.id.button43);
        botones[4][4]= findViewById(R.id.button44);
        iniciar();
        findViewById(R.id.buttonNuevo).setOnClickListener(v -> iniciar());
        findViewById(R.id.buttonResolver).setOnClickListener(v -> revelar());
    }

    public void iniciar(){
        GameOver = false;
        ganas = 22;
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                final int row = i;
                final int col = j;
                botones[i][j].setBackgroundColor(getColor(R.color.boton));
                botones[i][j].setOnClickListener(v -> {
                    if (!GameOver) {
                        int hayBombaAdyacente = verificarBombasAdyacentes(row, col);

                        if (hayBombaAdyacente > 0) {
                            botones[row][col].setTextColor(getColor(R.color.white));
                            botones[row][col].setText("" + hayBombaAdyacente);
                            puntos+= hayBombaAdyacente * 10;
                            paso.setText("Puntos: " + puntos);
                            botones[row][col].setEnabled(false);
                            ganas--;
                        }
                        else{
                            if(!GameOver) {
                                botones[row][col].setVisibility(View.INVISIBLE);
                                puntos += 100;
                                paso.setText("Puntos: " + puntos);
                                ganas--;
                            }
                            else{
                                botones[row][col].setBackgroundColor(getColor(R.color.red));
                            }
                        }
                        ganar();
                }});
                botones[i][j].setVisibility(View.VISIBLE);
                botones[i][j].setText("");
                puntos = 0;
                botones[row][col].setEnabled(true);
            }
        }
        paso.setText("Puntos: 0");
        numeroRandom();
    }

    public void revelar(){
        botones[fila1][colum1].setBackgroundColor(getColor(R.color.red));
        botones[fila2][colum2].setBackgroundColor(getColor(R.color.red));
        botones[fila3][colum3].setBackgroundColor(getColor(R.color.red));
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                botones[i][j].setEnabled(false);
            }
        }
        Toast.makeText(this, "El juego ha terminado", Toast.LENGTH_SHORT).show();
    }

    public void ganar(){
        if(ganas == 0){
            paso.setText("Has ganado: Con " + puntos + " puntos");
            botones[fila1][colum1].setBackgroundColor(getColor(R.color.win));
            botones[fila2][colum2].setBackgroundColor(getColor(R.color.win));
            botones[fila3][colum3].setBackgroundColor(getColor(R.color.win));
        }
    }

    public void numeroRandom(){
        int[][] bombas = new int[3][2];

        for (int i = 0; i < 3; i++) {
            int fila, columna;
            boolean coordenadasRepetidas;

            do {
                fila = (int) (Math.random() * 5);
                columna = (int) (Math.random() * 5);

                coordenadasRepetidas = false;
                for (int j = 0; j < i; j++) {
                    if (fila == bombas[j][0] && columna == bombas[j][1]) {
                        coordenadasRepetidas = true;
                        break;
                    }
                }
            } while (coordenadasRepetidas);

            bombas[i][0] = fila;
            bombas[i][1] = columna;
        }

        fila1 = bombas[0][0];
        colum1 = bombas[0][1];
        Log.d("Random", ""+fila1+" "+colum1);

        fila2 = bombas[1][0];
        colum2 = bombas[1][1];
        Log.d("Random", ""+fila2+" "+colum2);

        fila3 = bombas[2][0];
        colum3 = bombas[2][1];
        Log.d("Random", ""+fila3+" "+colum3);
}


    public int verificarBombasAdyacentes(int fila, int columna){
        int count = 0;

        if(fila == fila1 && columna == colum1 || fila == fila2 && columna == colum2 || fila == fila3 && columna == colum3){
            GameOver = true;
            pasar = paso.getText().toString();

            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("Paso", pasar);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            finish();
        }
        else{
            int[] filasAdyacentes = {-1, -1, -1, 0, 1, 1, 1, 0};
            int[] columnasAdyacentes = {-1, 0, 1, 1, 1, 0, -1, -1};

            for (int i = 0; i < 8; i++) {
                int filaAdyacente = fila + filasAdyacentes[i];

                int columnaAdyacente = columna + columnasAdyacentes[i];

                if (filaAdyacente >= 0 && filaAdyacente < 5 && columnaAdyacente >= 0 && columnaAdyacente < 5) {

                    if (filaAdyacente == fila1 && columnaAdyacente == colum1) {
                        count++;
                    }
                    if (filaAdyacente == fila2 && columnaAdyacente == colum2) {
                        count++;
                    }
                    if (filaAdyacente == fila3 && columnaAdyacente == colum3) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}