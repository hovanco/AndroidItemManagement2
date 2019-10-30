package com.example.itemmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    List<Item> items;
    Context context;
    private OnItemClicked onClick;

    public ItemAdapter(Runnable mainActivity, List<Item> Items) {
        items = Items;
    }

    public  List<Item>getItems(){return items;}

    public  void setItems(List<Item> items){
        this.items = items;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new ItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.textViewName.setText(items.get(position).getName());
        holder.textViewQuantity.setText(items.get(position).getQuantity());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemUpdateClick(position);
            }
        });

        // Function delete vocabulary
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemDeleteClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewQuantity;
        LinearLayout item;

        Button btnDelete;
        Button btnUpdate;




        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tvVocabulary);
            textViewQuantity = itemView.findViewById(R.id.tvMean);


            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            item = itemView.findViewById(R.id.item);
        }
    }

    public interface OnItemClicked {
        void onItemDeleteClick(int position);
        void onItemUpdateClick(int position);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }


}
