package com.example.rpp_lab_3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.holder> {

    Context context;
    ArrayList<Students> arrayList;

    public rvAdapter(Context ctx,ArrayList<Students> arr) {

        context = ctx;
        arrayList = arr;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.info_layout,viewGroup,false);
        holder holder = new holder(view);
        return  holder;

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class holder extends RecyclerView.ViewHolder {

        TextView idTV;
        TextView fioTV;
        TextView dateTV;


        public holder(@NonNull View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.id_tv);
            fioTV = itemView.findViewById(R.id.fio_tv);
            dateTV = itemView.findViewById(R.id.date_tv);

        }

        public void bind() {
            idTV.setText(arrayList.get(getAdapterPosition()).getId());
            fioTV.setText("id = "+ arrayList.get(getAdapterPosition()).getFio() + "  ");
            dateTV.setText(arrayList.get(getAdapterPosition()).getDate());
        }


    }

}

