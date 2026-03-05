package com.example.pass_task_21;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Spinner fromSpinner;
    Spinner toSpinner;
    EditText inputTxt;
    Button convertButton;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromSpinner = findViewById(R.id.spinner_from);
        toSpinner = findViewById(R.id.spinner_too);
        inputTxt = findViewById(R.id.user_input);
        convertButton = findViewById(R.id.convert_button);
        output = findViewById(R.id.result_text);

        String[] units = getResources().getStringArray(R.array.options);





    }
}