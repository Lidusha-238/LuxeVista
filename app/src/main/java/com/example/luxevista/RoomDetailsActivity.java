package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RoomDetailsActivity extends AppCompatActivity {

    ImageView imgView;
    TextView txtname, txtdescription, txtprice;
    Button btnbookNow;
    String txtroomName, txtroomDesc, txtroomImage, txtroomAvailability, txtroomType;
    double roomPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_room_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgView = findViewById(R.id.imgRoom);
        txtname = findViewById(R.id.txtroomName);
        txtdescription = findViewById(R.id.txtroomDesc);
        txtprice = findViewById(R.id.txtroomPrice);
        btnbookNow = findViewById(R.id.btnbookNow);

        Intent i = getIntent();
        txtroomName = i.getStringExtra("name");
        txtroomDesc = i.getStringExtra("desc");
        txtroomImage = i.getStringExtra("image");
        txtroomType = i.getStringExtra("type");
        roomPrice = i.getDoubleExtra("price", 0.0);
        txtroomAvailability = i.getStringExtra("availability");

        txtname.setText(txtroomName);
        txtdescription.setText(txtroomDesc);
        txtprice.setText("Price per Night: $" + roomPrice);

        // Image (if stored as drawable name)
        int imageRes = getResources().getIdentifier(txtroomImage, "drawable", getPackageName());
        if (imageRes != 0) imgView.setImageResource(imageRes);

        btnbookNow.setOnClickListener(v -> {
            Intent intent = new Intent(RoomDetailsActivity.this, RoomBookingActivity.class);
            intent.putExtra("room_name", txtroomName);
            intent.putExtra("room_price", roomPrice);
            startActivity(intent);
        });
    }
}
