package com.example.itemmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Update extends AppCompatActivity {
    int itemId;
    EditText editTextUpdateName, editTextUpdateQuantity;
    Button ButtonUpdate;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        editTextUpdateName = findViewById(R.id.edt_update_name);
        editTextUpdateQuantity = findViewById(R.id.edt_update_quantity);
        ButtonUpdate = findViewById(R.id.button_update);

        itemId= getIntent().getIntExtra("id",0);

        String name = getIntent().getStringExtra("name");
        String quantity = getIntent().getStringExtra("quantity");

        editTextUpdateName.setText(name);
        editTextUpdateQuantity.setText(quantity);




        //kich vao nut update
        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();
            }
        });
    }

    //thuc hien chuc nang update
    private void updateTodoToDatabase() {
        final String name = editTextUpdateName.getText().toString();
        final String quantity = editTextUpdateQuantity.getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Item newItem = new Item();
                newItem.setName(name);
                newItem.setQuantity(quantity);
                newItem.setId(itemId); // thinking about why we need to set id here

                db.itemDao().update(newItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }
        }.execute();
    }

    // Xuat hien dialog
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

}
