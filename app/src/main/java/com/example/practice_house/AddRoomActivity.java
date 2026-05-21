package com.example.practice_house;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM_NAME = "extra_room_name";
    public static final String EXTRA_ROOM_TYPE_INDEX = "extra_room_type_index";

    private final List<View> typeItems = new ArrayList<>();
    private int selectedTypeIndex = 0;
    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room);

        View root = findViewById(R.id.add_room_root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);

            View header = findViewById(R.id.add_room_header);
            header.setPadding(
                    header.getPaddingLeft(),
                    systemBars.top + getResources().getDimensionPixelSize(R.dimen.add_room_header_padding_top),
                    header.getPaddingRight(),
                    header.getPaddingBottom()
            );
            return insets;
        });

        nameInput = findViewById(R.id.add_room_name_input);

        findViewById(R.id.add_room_back_button).setOnClickListener(v -> finish());

        populateRoomTypes();
        updateTypeSelection(selectedTypeIndex);

        Button saveButton = findViewById(R.id.add_room_save_button);
        saveButton.setOnClickListener(v -> saveRoom());
    }

    private void populateRoomTypes() {
        GridLayout grid = findViewById(R.id.add_room_type_grid);
        grid.removeAllViews();
        typeItems.clear();

        LayoutInflater inflater = LayoutInflater.from(this);
        int rowSpacing = getResources().getDimensionPixelSize(R.dimen.add_room_grid_row_spacing);
        int columnCount = 3;
        int rowCount = (RoomTypes.ALL.length + columnCount - 1) / columnCount;
        grid.setRowCount(rowCount);

        for (int i = 0; i < RoomTypes.ALL.length; i++) {
            View item = inflater.inflate(R.layout.item_add_room_type, grid, false);
            TextView label = item.findViewById(R.id.add_room_type_label);
            label.setText(RoomTypes.ALL[i].labelRes);

            int row = i / columnCount;
            int column = i % columnCount;

            GridLayout.Spec rowSpec = GridLayout.spec(row);
            GridLayout.Spec columnSpec = GridLayout.spec(column, 1f);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setGravity(Gravity.FILL_HORIZONTAL);
            if (row > 0) {
                params.topMargin = rowSpacing;
            }

            item.setLayoutParams(params);

            final int index = i;
            item.setOnClickListener(v -> {
                selectedTypeIndex = index;
                updateTypeSelection(selectedTypeIndex);
            });

            grid.addView(item);
            typeItems.add(item);
        }
    }

    private void updateTypeSelection(int selectedIndex) {
        int innerPadding = getResources().getDimensionPixelSize(R.dimen.add_room_type_icon_inner_padding);
        int primaryColor = ContextCompat.getColor(this, R.color.main_primary);
        int defaultLabelColor = ContextCompat.getColor(this, R.color.add_room_type_label_default);

        for (int i = 0; i < typeItems.size(); i++) {
            View item = typeItems.get(i);
            FrameLayout iconContainer = item.findViewById(R.id.add_room_type_icon_container);
            ImageView icon = item.findViewById(R.id.add_room_type_icon);
            TextView label = item.findViewById(R.id.add_room_type_label);
            RoomTypes.Type roomType = RoomTypes.ALL[i];
            boolean selected = i == selectedIndex;

            icon.setImageResource(roomType.pickerIconRes);
            icon.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
            iconContainer.setBackgroundResource(selected
                    ? R.drawable.bg_add_room_type_circle_selected
                    : R.drawable.bg_add_room_type_circle_default);
            label.setTextColor(selected ? primaryColor : defaultLabelColor);
        }
    }

    private void saveRoom() {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            name = getString(RoomTypes.ALL[selectedTypeIndex].labelRes);
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ROOM_NAME, name);
        data.putExtra(EXTRA_ROOM_TYPE_INDEX, selectedTypeIndex);
        setResult(RESULT_OK, data);
        finish();
    }
}
