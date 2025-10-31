package com.example.luxevista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ServiceList extends BaseAdapter {
    private Context context;
    private ArrayList<Service> services;

    public ServiceList(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getCount() { return services.size(); }

    @Override
    public Object getItem(int position) { return services.get(position); }

    @Override
    public long getItemId(int position) { return services.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.service_item, parent, false);
        }
        Service service = services.get(position);
        TextView txtName = convertView.findViewById(R.id.txtServiceName);
        TextView txtDesc = convertView.findViewById(R.id.txtServiceDesc);
        TextView txtPrice = convertView.findViewById(R.id.txtServicePrice);
        ImageView imgService = convertView.findViewById(R.id.imgService);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        txtName.setText(service.getName());
        txtDesc.setText(service.getDesc());
        txtPrice.setText("$" + service.getPrice());
        ratingBar.setRating((float) service.getRating());

        int imgRes = context.getResources().getIdentifier(service.getImage(), "drawable", context.getPackageName());
        imgService.setImageResource(imgRes);

        return convertView;
    }
}
