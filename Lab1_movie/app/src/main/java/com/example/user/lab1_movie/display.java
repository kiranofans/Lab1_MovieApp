package com.example.user.lab1_movie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;


public class display extends AppCompatActivity {
    private TextView disp;
    private ImageView imgDisp;
    private ImageButton backBtn;
    private Button goToWebBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        disp=(TextView)findViewById(R.id.dispPrices);
        imgDisp=(ImageView)findViewById(R.id.schedule);
        backBtn=(ImageButton)findViewById(R.id.backBtn);
        goToWebBtn=(Button)findViewById(R.id.goToWebBtn);

        goToWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start cineplex website
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.cineplex.com/")));
            }
        });

        setBackBtn();
        getTxtNImg();

    }
    private void setBackBtn(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To go back to main activity and to reset
                startActivity(new Intent(display.this,MainActivity.class));
            }
        });
    }
    private int getTxtNImg(){
        int[] positions={0,1,2}; int pos=+1;

        double price=getIntent().getExtras().getDouble("CHOICE_PRICE");
        disp.setText("Your total is $"+price);
        //Images
        int imgs = getIntent().getExtras().getInt("USERCHOICE_IMG");
        imgDisp.setImageResource(imgs);

        int i = getIntent().getIntExtra("VALUE",positions[pos]);

        return i;
    }
}



