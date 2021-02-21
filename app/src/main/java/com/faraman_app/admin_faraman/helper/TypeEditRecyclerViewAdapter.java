package com.faraman_app.admin_faraman.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.faraman_app.admin_faraman.R;
import com.faraman_app.admin_faraman.model.Type;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TypeEditRecyclerViewAdapter extends RecyclerView.Adapter<TypeEditRecyclerViewAdapter.RecyclerViewHolders> {
    private Context context;
    private ArrayList<Type> data;
    private OnItemClickListener onItemClickListener;

    public TypeEditRecyclerViewAdapter(Context context, ArrayList<Type> data) {
        this.context = context;
        this.data = data;
    }
    public TypeEditRecyclerViewAdapter() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_for_edit, parent, false);

        return new RecyclerViewHolders(v);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolders holder, @SuppressLint("RecyclerView") final int position) {

        holder.textViewNameType.setText(data.get(position).getName());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListener.onItemDeleteClick(data.get(position).getId(),position);

            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListener.onItemEditClick(data.get(position).getId());

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder {


        TextView textViewNameType;
        CardView cardView;
        Button buttonEdit, buttonDelete;

        RecyclerViewHolders(final View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            textViewNameType = itemView.findViewById(R.id.textViewNameType);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);


        }
    }

    public interface OnItemClickListener {

        void onItemDeleteClick(String id,int position);

        void onItemEditClick(String id);
    }
}
