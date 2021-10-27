package com.example.levantai18093421_mainapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List<Product> list;
    private Context context;

    public MyAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recuclerviewlayout, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

            Product product = list.get(position);
            holder.txtId.setText(String.valueOf(product.getId()));
            holder.txtName.setText(product.getName());
            holder.txtPrice.setText(String.valueOf(product.getPrice()));
            holder.txtStatus.setText(String.valueOf(product.getStatus()));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(context,MainActivity.class);
                        intent.putExtra("id",String.valueOf(product.getId()));
                        intent.putExtra("name",String.valueOf(product.getName()));
                        intent.putExtra("price",String.valueOf(product.getPrice()));
                        intent.putExtra("status",String.valueOf(product.getStatus()));
                        context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtName, txtPrice,txtStatus;
        CardView cardView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
