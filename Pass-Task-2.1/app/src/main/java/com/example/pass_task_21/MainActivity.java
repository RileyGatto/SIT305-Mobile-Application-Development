//Imports Project
package com.example.pass_task_21;

//Libraries
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Class for Main Activity
public class MainActivity extends AppCompatActivity {

    //Variables Declarations
    Spinner fromSpinner;
    Spinner toSpinner;
    EditText inputTxt;
    Button convertButton;
    TextView output;

    //This Method Runs On Startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Default App Setup Steps
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assign UI elements to my java variables
        fromSpinner = findViewById(R.id.spinner_from);
        toSpinner = findViewById(R.id.spinner_too);
        inputTxt = findViewById(R.id.user_input);
        convertButton = findViewById(R.id.convert_button);
        output = findViewById(R.id.result_text);

        //This stores the options for the drop-down menu in an array.
        String[] units = getResources().getStringArray(R.array.options);

        //Used to populate the spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Populates Spinners
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        //Using onclick to update values when convert button is pressed
        convertButton.setOnClickListener(v -> {
            String from = fromSpinner.getSelectedItem().toString();
            String to = toSpinner.getSelectedItem().toString();

            double userValue = Double.parseDouble(inputTxt.getText().toString());
            //Calls the convert Function
            double result = convert(from, to, userValue);
            output.setText(String.valueOf(result));
        });
    }

    double convert(String from, String to, double value) {
        //Currency
        double cur = 0;
        switch (from) {
            case "USD":
                cur = value;
                break;
            case "AUD":
                cur = value / 1.55;
                break;
            case "EUR":
                cur = value / 0.92;
                break;
            case "JPY":
                cur = value / 148.50;
                break;
            case "GBP":
                cur = value / 0.78;
                break;
        }

        if(from.equals("USD") || from.equals("AUD") || from.equals("EUR") || from.equals("JPY") || from.equals("GBP")) {
            switch (to) {
                case "USD":
                    return cur;
                case "AUD":
                    return cur * 1.55;
                case "EUR":
                    return cur * 0.92;
                case "JPY":
                    return cur * 148.50;
                case "GBP":
                    return cur * 0.78;
            }
        }

        //Fuel Efficiency & Distance
        if(from.equals("mpg") && to.equals("km/L")) {
            return value * 0.425;
        }
        if(from.equals("km/L") && to.equals("mpg")) {
            return value / 0.425;
        }

        if(from.equals("Gallon") && to.equals("Liter")) {
            return value * 3.785;
        }
        if(from.equals("Liter") && to.equals("Gallon")) {
            return value / 3.785;
        }

        if(from.equals("Nautical Mile") && to.equals("Kilometer")) {
            return value * 1.852;
        }
        if(from.equals("Kilometer") && to.equals("Nautical Mile")) {
            return value / 1.852;
        }

        //Temperature
        if(from.equals("Celsius") && to.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        }
        if(from.equals("Fahrenheit") && to.equals("Celsius")) {
            return (value - 32) / 1.8;
        }

        if(from.equals("Celsius") && to.equals("Kelvin")) {
            return value + 273.15;
        }
        if(from.equals("Kelvin") && to.equals("Celsius")) {
            return value - 273.15;
        }

        //return entered value if spinners don't match conditions,
        return cur;
    }
}