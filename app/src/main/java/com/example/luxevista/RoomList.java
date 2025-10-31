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

public class RoomList extends ArrayAdapter<Room> {

    Context context;
    ArrayList<Room> rooms;

    public RoomList(@NonNull Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.room_item,parent,false);
        }
        Room room = rooms.get(position);

        TextView txtName = convertView.findViewById(R.id.txtRoomName);
        TextView txtPrice = convertView.findViewById(R.id.txtRoomPrice);
        ImageView ImgView = convertView.findViewById(R.id.imgRoom);
        RatingBar ratingBar = convertView.findViewById(R.id.ratingBar);

        txtName.setText(room.getName());
        txtPrice.setText("$"+room.getPrice());
        ratingBar.setRating((float) room.getRating());

        int resId = context.getResources().getIdentifier(room.getImage(),"drawable",context.getPackageName());
        ImgView.setImageResource(resId);

        return convertView;
    }
}
