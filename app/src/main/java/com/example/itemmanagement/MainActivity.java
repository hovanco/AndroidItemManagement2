package com.example.itemmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClicked {

    AppDatabase db;
    public Button btn_add;

    RecyclerView recyclerviewItem;
    ItemAdapter itemAdapter;
    public static List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerviewItem = findViewById(R.id.recycler_view_item);
        recyclerviewItem.setLayoutManager(new LinearLayoutManager((this)));

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        btn_add = (Button) findViewById(R.id.btnAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddScreen();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllItem();
    }

    public void getAllItem() {
        new AsyncTask<Void, Void, List<Item>>() {
            @Override
            protected List<Item> doInBackground(Void... voids) {
                items = db.itemDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        itemAdapter = new ItemAdapter(this, items);
                        itemAdapter.setOnClick(MainActivity.this);
                        recyclerviewItem.setAdapter(itemAdapter);
                        //Toast.makeText(MainActivity.this, "size" + memories.size(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
        }.execute();
    }

    public void openAddScreen() {
        Intent intent = new Intent(MainActivity.this, Add.class);
        startActivity(intent);
    }


    private void openUpdateTodoScreen(Item item) {
        Intent intent = new Intent(MainActivity.this, Update.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("name", item.getName());
        intent.putExtra("quantity", item.getQuantity());
        startActivity(intent);
    }


    @Override
    public void onItemDeleteClick(int position) {
        deleteItem(position);
    }

    void deleteItem(final int position){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.itemDao().delete(itemAdapter.getItems().get(position));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                itemAdapter.getItems().remove(position);
                itemAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void showAlertDelete(int position) {
    }

    @Override
    public void onItemUpdateClick(int position) {
        openUpdateTodoScreen(items.get(position));

    }
}
