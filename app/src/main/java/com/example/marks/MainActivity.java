package com.example.marks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marks.db.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    EditText editName, editMarks, editId;
    Button btnAddData;
    Button btnViewAll;
    Button btnDelete;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editText_name);
        editMarks = findViewById(R.id.editText_marks);
        editId = findViewById(R.id.editText_id);
        btnAddData = findViewById(R.id.button_add);
        btnViewAll = findViewById(R.id.button_viewAll);
        btnDelete = findViewById(R.id.button_delete);
        btnUpdate = findViewById(R.id.button_update);

        btnDelete.setOnClickListener(
            new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Integer deletedRows = myDb.deleteData(editId.getText().toString());
                     Log.i("Status",deletedRows>0?"Record deleted.":"No Record delete.");
                 }
            }
        );

        btnUpdate.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdate = myDb.updateData(editId.getText().toString(),
                    editName.getText().toString(), editMarks.getText().toString());
                    Log.i("Status",isUpdate?"Record updated.":"No Record update.");
                }
            }
        );

        btnAddData.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = myDb.insertData(editName.getText().toString(),
                    editMarks.getText().toString());
                    Log.i("Status",isInserted?"Record added.":"No Record add.");
                }
            }
        );

        btnViewAll.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res = myDb.getAllData();

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("Record Data...\n");
                    while (res.moveToNext()) { //cycle thru result set
                        buffer.append("Id :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Marks :" + res.getString(2) + "\n\n");
                    }
                    // Show all data
                    Log.i("Data",  buffer.toString());
                }
            }
        );

    }
}