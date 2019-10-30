package com.example.itemmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    AppDatabase db;
    private String name, quantity;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        buttonAdd = findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                finish();
            }
        });
    }
    private void addItem() {
        final EditText editName = (EditText) findViewById(R.id.edit_name);
        final EditText editQuantity = (EditText) findViewById(R.id.edit_quantity);

        name = editName.getText().toString();
        quantity = editQuantity.getText().toString();

        if (name.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "Data must not null", Toast.LENGTH_SHORT).show();
            return;
        }
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Item newItem = new Item();
                newItem.setName(name);
                newItem.setQuantity(quantity);
                db.itemDao().insert(newItem);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(Add.this, " has been added successfully", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }
}
