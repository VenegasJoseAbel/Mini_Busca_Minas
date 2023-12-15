package com.venegas.minibuscaminas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView hola;
    String paso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView i = findViewById(R.id.imageView2);

        i.setScaleX(2.0f);
        i.setScaleY(2.0f);

        findViewById(R.id.buttonNuevo2).setOnClickListener(v -> nuevo());

        hola = findViewById(R.id.textView2);

        Intent intent = getIntent();
        if (intent != null) {
            String Paso = intent.getStringExtra("Paso");
            hola.setText(Paso);
        }
    }

    public void nuevo() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}