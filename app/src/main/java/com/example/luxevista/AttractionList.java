package com.example.luxevista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AttractionList extends ArrayAdapter<Attraction> {

    Context context;
    ArrayList<Attraction> attractions;

    public AttractionList(@NonNull Context context, ArrayList<Attraction> attractions) {
        super(context, 0, attractions);
        this.context = context;
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_attraction, parent, false);
        }

        Attraction attraction = attractions.get(position);

        TextView txtName = convertView.findViewById(R.id.txtAttractionName);
        TextView txtDesc = convertView.findViewById(R.id.txtAttractionDesc);
        ImageView imgAttraction = convertView.findViewById(R.id.imgAttraction);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        txtName.setText(attraction.getName());
        txtDesc.setText(attraction.getDescription());
        ratingBar.setRating((float) attraction.getRating());

        int resId = context.getResources().getIdentifier(attraction.getImage(), "drawable", context.getPackageName());
        imgAttraction.setImageResource(resId);

        return convertView;
    }
}
