package br.com.heitor.jogo_figuras;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    View raiz;
    TextView textViewF;
    FigurasView figurasView;
    Button buttonStart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewF = findViewById(R.id.figuraTextView);
        buttonStart = findViewById(R.id.buttonStart);
        figurasView = findViewById(R.id.figurasView);
        final MainFragment fragmentDemo = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.figurasFragment);
        textViewF.setText(getString(R.string.start));
        buttonStart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                textViewF.setText(getString(R.string.start));
                fragmentDemo.getFigurasView().setGameStart(true);
                fragmentDemo.getFigurasView().invalidate();




            }
        });

    }
}