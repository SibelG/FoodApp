package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SucceessActivity extends AppCompatActivity {


    TextView OrderNo,backToOrder;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        OrderNo=findViewById(R.id.orderNo);
        backToOrder=findViewById(R.id.backOrder);
        back=findViewById(R.id.backToOrders);

        if(getIntent()!=null){
            OrderNo.setText("Your Order Number:"+OrderNumber());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SucceessActivity.this,OrderListActivity.class);
                startActivity(intent);
            }
        });
        backToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SucceessActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private int OrderNumber(){
        int randomNumber;
        Random random = new Random();
        randomNumber = random.nextInt(89999999) + 100000;
        return randomNumber;

    }
}