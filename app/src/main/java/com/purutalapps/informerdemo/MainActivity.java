package com.purutalapps.informerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import com.purutalapps.informer.Informer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_def).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Informer.informDefault(MainActivity.this, "This is default message");
            }
        });

        findViewById(R.id.btn_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Informer.informSuccess(MainActivity.this, "This is success message");
            }
        });

        findViewById(R.id.btn_erro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Informer.informError(MainActivity.this, "This is error message");
            }
        });

        findViewById(R.id.btn_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Informer.WithOptions(MainActivity.this)
                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black))
                        .setAnimation(Informer.AnimType.SLIDE_LEFT_RIGHT)
                        .setDuration(Informer.Duration.LONG)
                        .inform("This is custom message");
            }
        });
    }
}
