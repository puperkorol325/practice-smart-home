package com.example.practice_house;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int[][] ROOMS = {
            {R.drawable.ic_main_room_living, R.string.main_room_living},
            {R.drawable.ic_main_room_kitchen, R.string.main_room_kitchen},
            {R.drawable.ic_main_room_bathroom, R.string.main_room_bathroom},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            View header = findViewById(R.id.main_header);
            header.setPadding(
                    header.getPaddingLeft(),
                    systemBars.top + getResources().getDimensionPixelSize(R.dimen.main_header_padding_top),
                    header.getPaddingRight(),
                    header.getPaddingBottom()
            );
            return insets;
        });

        populateRoomList();
    }

    private void populateRoomList() {
        LinearLayout roomList = findViewById(R.id.main_room_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        int spacing = getResources().getDimensionPixelSize(R.dimen.main_room_card_spacing);

        for (int i = 0; i < ROOMS.length; i++) {
            View card = inflater.inflate(R.layout.item_main_room, roomList, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getResources().getDimensionPixelSize(R.dimen.main_room_card_height)
            );
            if (i > 0) {
                params.topMargin = spacing;
            }
            card.setLayoutParams(params);

            ImageView icon = card.findViewById(R.id.room_icon);
            TextView name = card.findViewById(R.id.room_name);
            icon.setImageResource(ROOMS[i][0]);
            name.setText(ROOMS[i][1]);

            roomList.addView(card);
        }
    }
}
