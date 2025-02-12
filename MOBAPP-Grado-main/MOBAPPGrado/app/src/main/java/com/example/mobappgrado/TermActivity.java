package com.example.mobappgrado;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TermActivity extends AppCompatActivity {
    private TextView termNumText;
    private ImageButton plusButton;
    private ImageButton backButton;
    private ListView listView;
    private ArrayList<String> subjectsList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;
    private int termId;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        // Initialize views
        termNumText = findViewById(R.id.TermNum);
        plusButton = findViewById(R.id.plus);
        backButton = findViewById(R.id.back);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        // Get term name from intent
        String termName = getIntent().getStringExtra("termName");
        termNumText.setText(termName);

        // Initialize list
        subjectsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectsList);
        listView.setAdapter(adapter);

        // Plus button click listener
        plusButton.setOnClickListener(v -> showAddSubjectDialog());

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // List item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String subjectName = subjectsList.get(position);
            Intent intent = new Intent(TermActivity.this, SubjectActivity.class);
            intent.putExtra("subjectName", subjectName);
            startActivity(intent);
        });
    }

    private void showAddSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_item, null);
        EditText input = dialogView.findViewById(R.id.inputSubjct);

        builder.setView(dialogView)
                .setTitle("Add Subject")
                .setPositiveButton("Add", (dialog, which) -> {
                    String subjectName = input.getText().toString();
                    if (!subjectName.isEmpty()) {
                        subjectsList.add(subjectName);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.create().show();
    }

    // Handle back button press (both hardware and UI back button)
    @Override
    public void onBackPressed() {
        finish(); // This will close the current activity and return to the previous one
    }
}