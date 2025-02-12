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

public class MainPage extends AppCompatActivity {
    private TextView usernameText;
    private ImageButton plusButton;
    private ListView listView;
    private ArrayList<String> termsList;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Initialize views
        usernameText = findViewById(R.id.username);
        plusButton = findViewById(R.id.plus);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        // Get username from intent
        String username = getIntent().getStringExtra("username");
        usernameText.setText("Hello, " + username);

        // Initialize list
        termsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termsList);
        listView.setAdapter(adapter);

        // Plus button click listener
        plusButton.setOnClickListener(v -> showAddTermDialog());

        // List item click listener
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String termName = termsList.get(position);
            Intent intent = new Intent(MainPage.this, TermActivity.class);
            intent.putExtra("termName", termName);
            startActivity(intent);
        });
    }

    private void showAddTermDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_term, null);
        EditText input = dialogView.findViewById(R.id.inputTerm);

        builder.setView(dialogView)
                .setTitle("Add Term")
                .setPositiveButton("Add", (dialog, which) -> {
                    String termName = input.getText().toString();
                    if (!termName.isEmpty()) {
                        termsList.add(termName);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.create().show();
    }
}