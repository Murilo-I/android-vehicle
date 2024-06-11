package com.example.estacionamento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.estacionamento.model.Owner;

import java.util.ArrayList;

public class OwnerAdapter extends ArrayAdapter<Owner> {
    int groupId;
    ArrayList<Owner> list;
    Context context;

    public OwnerAdapter(Context context, int vg, int id,
                        ArrayList<Owner> list) {
        super(context, vg, id, list);
        this.context = context;
        groupId = vg;
        this.list = list;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View itemView = inflater.inflate(groupId, parent, false);
        TextView textName = itemView.findViewById(R.id.ownerId);
        textName.setText(list.get(position).getNome());
        return itemView;
    }

}
