package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class FoodDetails extends AppCompatActivity {

    // now we will get data from intent and set to UI

    ImageView imageView,imageView2,back;
    TextView itemName, itemPrice, itemRating,foodDetails;
    RatingBar ratingBar;
    public static  Button addToBag;

    String name, price, rating, imageUrl,note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");
        note=intent.getStringExtra("note");

        imageView = findViewById(R.id.imageView5);
        imageView2=findViewById(R.id.backMain);
        itemName = findViewById(R.id.name);
        foodDetails=findViewById(R.id.foodDetails);
        itemPrice = findViewById(R.id.price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        addToBag=findViewById(R.id.addBag);
        back=findViewById(R.id.backMain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FoodDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });



        //Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);
        itemName.setText(name);
        foodDetails.setText(note);
        itemPrice.setText("₹ "+price);
        itemRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));


        addToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
