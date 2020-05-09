package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class MainActivity0 extends AppCompatActivity {
    private int[] id={R.drawable.m1,R.drawable.m2,R.drawable.m3,R.drawable.m4,R.drawable.m5,R.drawable.m6,R.drawable.m7,R.drawable.m8,
            R.drawable.m9,R.drawable.m10,R.drawable.m11,R.drawable.m12,R.drawable.m13,R.drawable.m14,R.drawable.m15,R.drawable.m16,
            R.drawable.m17,R.drawable.m18,R.drawable.m19,R.drawable.m20,R.drawable.m21,R.drawable.m22,R.drawable.m23,R.drawable.m24};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        ImageView imageView =findViewById(R.id.mimag);


        int x =((int)( Math.random() * 23))+0;
        imageView.setImageResource(id[x]);
        final Intent i = new Intent(this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            startActivity(i);
            MainActivity0.this.finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        },2500);



    }
}
