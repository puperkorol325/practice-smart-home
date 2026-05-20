package com.example.practice_house;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreatePinActivity extends AppCompatActivity {

    private static final int PIN_LENGTH = 4;

    private final View[] pinDots = new View[PIN_LENGTH];
    private final StringBuilder pin = new StringBuilder(PIN_LENGTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_pin);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.create_pin_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pinDots[0] = findViewById(R.id.pin_dot_1);
        pinDots[1] = findViewById(R.id.pin_dot_2);
        pinDots[2] = findViewById(R.id.pin_dot_3);
        pinDots[3] = findViewById(R.id.pin_dot_4);

        int[] keyIds = {
                R.id.pin_key_1, R.id.pin_key_2, R.id.pin_key_3,
                R.id.pin_key_4, R.id.pin_key_5, R.id.pin_key_6,
                R.id.pin_key_7, R.id.pin_key_8, R.id.pin_key_9
        };

        for (int i = 0; i < keyIds.length; i++) {
            final String digit = String.valueOf(i + 1);
            Button key = findViewById(keyIds[i]);
            key.setOnClickListener(v -> onDigitPressed(digit));
        }

        updatePinDots();
    }

    private void onDigitPressed(String digit) {
        if (pin.length() >= PIN_LENGTH) {
            return;
        }

        pin.append(digit);
        updatePinDots();

        if (pin.length() == PIN_LENGTH) {
            startActivity(new Intent(CreatePinActivity.this, MainActivity.class));
            finish();
        }
    }

    private void updatePinDots() {
        for (int i = 0; i < PIN_LENGTH; i++) {
            pinDots[i].setBackgroundResource(
                    i < pin.length()
                            ? R.drawable.bg_pin_dot_filled
                            : R.drawable.bg_pin_dot_empty
            );
        }
    }
}
