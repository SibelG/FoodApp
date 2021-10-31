package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.foodapp.adapter.AllFoodAdapter;
import com.example.foodapp.adapter.PopularAdapter;
import com.example.foodapp.adapter.RecommendedAdapter;
import com.example.foodapp.model.AllFood;
import com.example.foodapp.model.Allmenu;
import com.example.foodapp.model.Popular;
import com.example.foodapp.model.Recommended;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MainActivity extends AppCompatActivity implements AllFoodAdapter.MenuListClickListener {

    ApiInterface apiInterface;
    RecyclerView popularRecyclerView, recommendedRecyclerView, allMenuRecyclerView;
    public static List<Allmenu> foodDataList;
    public static List<Allmenu> cartFoods=new ArrayList<>();
    PopularAdapter popularAdapter;
    private int totalItemInCart = 0;
    RecommendedAdapter recommendedAdapter;
    AllFoodAdapter allMenuAdapter;
    public static TextView orderCount;
    public static ImageView orderView;
    View orderClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orderClick=findViewById(R.id.orderViewClick);
        orderCount=findViewById(R.id.orderCount);
        orderClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,OrderListActivity.class);
                startActivity(intent);
            }
        });

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<AllFood> call = apiInterface.getAllData();
        call.enqueue(new Callback<AllFood>() {
            @Override
            public void onResponse(Call<AllFood> call, Response<AllFood> response) {

                foodDataList = response.body().getAllmenu();
                List<Popular> getPopular= response.body().getPopular();
                List<Recommended> getRecommended=response.body().getRecommended();


                getAllMenu(foodDataList);
                getPopularData(getPopular);
                getRecommendedData(getRecommended);

            }

            @Override
            public void onFailure(Call<AllFood> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    private void  getPopularData(List<Popular> popularList){

        popularRecyclerView = findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);

    }

    private void  getRecommendedData(List<Recommended> recommendedList){

        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

    }

    private void  getAllMenu(List<Allmenu> allmenuList){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllFoodAdapter(this, allmenuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }
    public static int grandTotal(List<Allmenu> cartFoods){

        int total = 0;
        for(int i = 0 ; i < cartFoods.size(); i++) {
            total += cartFoods.get(i).getCount();
        }
        return total;
    }
    public static void cartUpdate() {
        if (orderCount != null && cartFoods != null)
            orderCount.setText(Integer.toString(grandTotal(cartFoods)));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

}
