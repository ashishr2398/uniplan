package com.shivskj.uniplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageButton crossbutton = findViewById(R.id.crossbutton);
        crossbutton.setOnClickListener(v -> openhome());
        Button taskbutton = findViewById(R.id.taskbutton);
        taskbutton.setOnClickListener(v -> opentask());
        Button notesbutton = findViewById(R.id.notesbutton);
        notesbutton.setOnClickListener(v -> opennotes());


    }

    public void openhome()
    {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void opentask()
    {
        Intent intent = new Intent(this, task.class);
        startActivity(intent);
    }



    public void opennotes()
    {
        Intent intent = new Intent(this, notes.class);
        startActivity(intent);
    }
}