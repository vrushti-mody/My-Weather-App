package com.example.full_stack_assessment;

import android.content.Intent;
import android.os.Bundle;

import com.example.full_stack_assessment.ViewModels.ForecastViewModel;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ToDO: Create the forecast button listener to transition to the next activity
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.forecast_button);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
            // Start the new activity
            startActivity(intent);
        });
    }

}