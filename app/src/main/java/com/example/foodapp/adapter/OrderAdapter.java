package com.example.foodapp.adapter;
import static com.example.foodapp.MainActivity.cartFoods;
import static com.example.foodapp.MainActivity.cartUpdate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.MainActivity;
import com.example.foodapp.OrderListActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.Allmenu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Allmenu> cartFood;
    private Context context;
    MenuListClickListener clickListener;
    int total=0;

    public OrderAdapter(List<Allmenu> cartFoods) {
        this.cartFood= cartFoods;

    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_items, parent, false);
        // here we need to create a layout for recyclerview cell items.


        return new OrderAdapter.OrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.allMenuName.setText(cartFood.get(position).getName());
        holder.allMenuPrice.setText(Integer.parseInt(cartFood.get(position).getPrice()) * cartFood.get(position).getCount());
        holder.allMenuTime.setText(cartFood.get(position).getDeliveryTime());
        holder.allMenuRating.setText(cartFood.get(position).getRating());



        Picasso.with(context).load(cartFood.get(position).getImageUrl()).into(holder.allMenuImage);
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Allmenu menu  = cartFood.get(position);
                cartFoods.add(menu);
                int total = menu.getCount();
                total--;
                if(total > 0 ) {
                    menu.setCount(total);
                    cartUpdate();

                    //clickListener.onUpdateCartClick(menu);
                    holder.orderCount.setText(total +"");
                } else {
                    menu.setCount(total);
                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });


        holder.plusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Allmenu menu  = cartFood.get(position);
                int total = menu.getCount();
                total++;
                if(total <= 10 ) {
                    menu.setCount(total);
                    cartUpdate();
                    //clickListener.onUpdateCartClick(menu);
                    holder.orderCount.setText(total +"");
                }
            }
        });





    }

    @Override
    public int getItemCount() {
        return cartFood.size();
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView allMenuName, allMenuRating, allMenuTime, allMenuPrice,orderCount;
        ImageView allMenuImage,cartDelete;
        Button plusButton,minusButton;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            cartDelete=itemView.findViewById(R.id.deleteView);
            orderCount=itemView.findViewById(R.id.count);
            plusButton=itemView.findViewById(R.id.plusButton);
            minusButton=itemView.findViewById(R.id.minusButton);
            allMenuName = itemView.findViewById(R.id.orderName);
            allMenuPrice = itemView.findViewById(R.id.OrderPrice);
            allMenuImage = itemView.findViewById(R.id.OrderImage);
        }
    }
    public interface MenuListClickListener {
        public void onAddToCartClick(Allmenu menu);
        public void onUpdateCartClick(Allmenu menu);
        public void onRemoveFromCartClick(Allmenu menu);
    }
}
