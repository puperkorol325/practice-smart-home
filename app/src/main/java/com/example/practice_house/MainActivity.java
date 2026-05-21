package com.example.practice_house;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<Room> rooms = new ArrayList<>();
    private LinearLayout roomList;
    private ActivityResultLauncher<Intent> addRoomLauncher;

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

        roomList = findViewById(R.id.main_room_list);

        addRoomLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                        return;
                    }
                    Intent data = result.getData();
                    String name = data.getStringExtra(AddRoomActivity.EXTRA_ROOM_NAME);
                    int typeIndex = data.getIntExtra(AddRoomActivity.EXTRA_ROOM_TYPE_INDEX, 0);
                    if (typeIndex < 0 || typeIndex >= RoomTypes.ALL.length) {
                        typeIndex = 0;
                    }
                    if (name == null || name.isEmpty()) {
                        name = getString(RoomTypes.ALL[typeIndex].labelRes);
                    }
                    rooms.add(new Room(name, typeIndex));
                    populateRoomList();
                }
        );

        if (rooms.isEmpty()) {
            seedInitialRooms();
        }

        populateRoomList();

        findViewById(R.id.main_add_room_fab).setOnClickListener(v ->
                addRoomLauncher.launch(new Intent(MainActivity.this, AddRoomActivity.class)));
    }

    private void seedInitialRooms() {
        rooms.add(new Room(getString(R.string.main_room_living), 0));
        rooms.add(new Room(getString(R.string.main_room_kitchen), 1));
        rooms.add(new Room(getString(R.string.main_room_bathroom), 2));
    }

    private void populateRoomList() {
        roomList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        int spacing = getResources().getDimensionPixelSize(R.dimen.main_room_card_spacing);

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            RoomTypes.Type type = room.getType();

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
            icon.setImageResource(type.mainListIconRes);
            name.setText(room.getName());

            roomList.addView(card);
        }
    }
}
