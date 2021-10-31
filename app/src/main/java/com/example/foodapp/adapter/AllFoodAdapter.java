package com.example.foodapp.adapter;


import static com.example.foodapp.MainActivity.cartFoods;
import static com.example.foodapp.MainActivity.cartUpdate;
import static com.example.foodapp.MainActivity.orderCount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.FoodDetails;
import com.example.foodapp.OrderListActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.Allmenu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllFoodAdapter extends RecyclerView.Adapter<AllFoodAdapter.AllMenuViewHolder> {

    Context context;
    List<Allmenu> allmenuList;
    private int totalItemInCart=0;
    int total=0;
    private MenuListClickListener clickListener;

    public AllFoodAdapter(Context context, List<Allmenu> allmenuList) {
        this.context = context;
        this.allmenuList = allmenuList;
    }

    @NonNull
    @Override
    public AllMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allmenu_recycler_items, parent, false);

        return new AllMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllMenuViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.allMenuName.setText(allmenuList.get(position).getName());
        holder.allMenuPrice.setText("₹ "+allmenuList.get(position).getPrice());
        holder.allMenuTime.setText(allmenuList.get(position).getDeliveryTime());
        holder.allMenuRating.setText(allmenuList.get(position).getRating());
        holder.allMenuNote.setText(allmenuList.get(position).getNote());

        //Glide.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.allMenuImage);
        Picasso.with(context).load(allmenuList.get(position).getImageUrl()).into(holder.allMenuImage);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Allmenu menu  = allmenuList.get(position);
                cartFoods.add(menu);
                int total = menu.getCount();
                total--;
                if(total > 0 ) {
                    menu.setCount(total);
                    cartUpdate();

                    //clickListener.onUpdateCartClick(menu);
                    holder.count.setText(total +"");
                } else {
                    holder.layout.setVisibility(View.GONE);
                    holder.addButton.setVisibility(View.VISIBLE);
                    menu.setCount(total);

                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Allmenu menu  = allmenuList.get(position);
                int total = menu.getCount();
                total++;
                if(total <= 10 ) {
                    menu.setCount(total);
                    cartUpdate();
                    //clickListener.onUpdateCartClick(menu);
                    holder.count.setText(total +"");
                }
            }
        });
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Allmenu menu  = allmenuList.get(position);
                menu.setCount(1);
                cartFoods.add(menu);
                cartUpdate();
               // clickListener.onAddToCartClick(menu);
                holder.layout.setVisibility(View.VISIBLE);
                holder.addButton.setVisibility(View.GONE);
                holder.count.setText(menu.getCount()+"");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", allmenuList.get(position).getName());
                i.putExtra("price", allmenuList.get(position).getPrice());
                i.putExtra("rating", allmenuList.get(position).getRating());
                i.putExtra("image", allmenuList.get(position).getImageUrl());
                i.putExtra("note",allmenuList.get(position).getNote());

                context.startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return allmenuList.size();
    }

    public static class AllMenuViewHolder extends RecyclerView.ViewHolder{

        TextView allMenuName,count, allMenuNote, allMenuRating, allMenuTime, allMenuCharges, allMenuPrice;
        ImageView allMenuImage;
        Button plus,minus,addButton;
        LinearLayout layout;
        public AllMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            layout=itemView.findViewById(R.id.orderChangeLayout);
            addButton=itemView.findViewById(R.id.addOrder);
            plus=itemView.findViewById(R.id.plusButton);
            minus=itemView.findViewById(R.id.minusButton);
            count=itemView.findViewById(R.id.countText);
            allMenuName = itemView.findViewById(R.id.all_menu_name);
            allMenuNote = itemView.findViewById(R.id.all_menu_note);
            allMenuRating = itemView.findViewById(R.id.all_menu_rating);
            allMenuTime = itemView.findViewById(R.id.all_menu_deliverytime);
            allMenuPrice = itemView.findViewById(R.id.all_menu_price);
            allMenuImage = itemView.findViewById(R.id.all_menu_image);
        }
    }
    public interface MenuListClickListener {
        public void onAddToCartClick(Allmenu menu);
        public void onUpdateCartClick(Allmenu menu);
        public void onRemoveFromCartClick(Allmenu menu);
    }

}
