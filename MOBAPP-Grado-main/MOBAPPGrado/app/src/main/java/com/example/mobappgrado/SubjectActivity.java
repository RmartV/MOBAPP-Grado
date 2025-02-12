package com.example.mobappgrado;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class SubjectActivity extends AppCompatActivity {
    private TextView subjectText;
    private ImageButton backButton;
    private ImageButton plusButton;
    private ImageButton categoryButton;
    private ListView listView;
    private ArrayList<String> gradesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        // Initialize views
        subjectText = findViewById(R.id.Subject);
        backButton = findViewById(R.id.back);
        plusButton = findViewById(R.id.plus);
        categoryButton = findViewById(R.id.category);
        listView = findViewById(R.id.listView);

        // Get subject name from intent
        String subjectName = getIntent().getStringExtra("subjectName");
        subjectText.setText(subjectName);

        // Initialize list
        gradesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradesList);
        listView.setAdapter(adapter);

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // Plus button click listener
        plusButton.setOnClickListener(v -> {
            // Add grade functionality here
        });

        // Category button click listener
        categoryButton.setOnClickListener(v -> {
            // Add category management functionality here
        });
    }

    // Handle back button press (both hardware and UI back button)
    @Override
    public void onBackPressed() {
        finish(); // This will close the current activity and return to the previous one
    }
}