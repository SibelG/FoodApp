package com.example.foodapp;

import static com.example.foodapp.MainActivity.cartFoods;
import static com.example.foodapp.MainActivity.orderCount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.adapter.OrderAdapter;
import com.example.foodapp.model.Allmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderListActivity extends AppCompatActivity implements OrderAdapter.MenuListClickListener {

    private EditText inputName, inputAddress, inputCity, inputState,inputCardNumber, inputCardExpiry, inputCardPin ;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    RecyclerView OrderRecyclerView;
    ImageView backDetail;
    private int totalItemInCart = 0;
    private Allmenu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);


        OrderRecyclerView=findViewById(R.id.cartItemsRecyclerView);
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckOrderButtonClick();

            }
        });
        backDetail=findViewById(R.id.backButton);
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderListActivity.this,FoodDetails.class);
                startActivity(intent);
            }
        });
        getRecyclerView();
        grandTotal(cartFoods);

    }
    private void getRecyclerView(){
        OrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter(cartFoods);
        OrderRecyclerView.setAdapter(orderAdapter);
    }


    private void onCheckOrderButtonClick() {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if(TextUtils.isEmpty(inputCity.getText().toString())) {
            inputCity.setError("Please enter city ");
            return;
        }else if(TextUtils.isEmpty(inputState.getText().toString())) {
            inputState.setError("Please enter zip ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }else if( TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        //start success activity..
        Intent i = new Intent(OrderListActivity.this, SucceessActivity.class);
        startActivityForResult(i, 1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    public Bitmap makeSmallerImage(Bitmap image,int maximumSize){
        int width=image.getWidth();
        int height=image.getHeight();
        float bitmapRatio=(float)width/(float)height;
        if(bitmapRatio>1){
            width=maximumSize;
            height=(int)(width/bitmapRatio);
        }else{
            height=maximumSize;
            width=(int)(height*bitmapRatio);
        }

        return image.createScaledBitmap(image,width,height,true);
    }

    public void grandTotal(List<Allmenu> cartFoods){

        int totalPrice = 0;
        for(int i = 0 ; i < cartFoods.size(); i++) {
            totalPrice += Integer.parseInt(cartFoods.get(i).getPrice())*cartFoods.get(i).getCount();
        }
        tvSubtotalAmount.setText(String.valueOf(totalPrice));

    }
    @Override
    public void onAddToCartClick(Allmenu menu) {
        if(cartFoods == null) {
            cartFoods = new ArrayList<>();
        }
        cartFoods.add(menu);
        totalItemInCart = 0;

        for(Allmenu m : cartFoods) {
            totalItemInCart = totalItemInCart + m.getCount();
        }
        orderCount.setText(totalItemInCart);
    }

    @Override
    public void onUpdateCartClick(Allmenu menu) {
        if(cartFoods.contains(menu)) {
            int index = cartFoods.indexOf(menu);
            cartFoods.remove(index);
            cartFoods.add(index, menu);

            totalItemInCart = 0;

            for(Allmenu m : cartFoods) {
                totalItemInCart = totalItemInCart + m.getCount();
            }
            orderCount.setText(totalItemInCart);
        }
    }

    @Override
    public void onRemoveFromCartClick(Allmenu menu) {
        if(cartFoods.contains(menu)) {
            cartFoods.remove(menu);
            totalItemInCart = 0;

            for(Allmenu m : cartFoods) {
                totalItemInCart = totalItemInCart + m.getCount();
            }
            orderCount.setText(totalItemInCart);
        }
    }
}
