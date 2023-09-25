package com.shivskj.uniplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageButton closebutton = findViewById(R.id.closebutton);
        closebutton.setOnClickListener(v ->
        {
            home.this.finish();
            System.exit(0);
        });

        ImageButton statsbutton = findViewById(R.id.statsbutton);
        statsbutton.setOnClickListener(v -> openstats());

        ImageButton todobutton = findViewById(R.id.todobutton);
        todobutton.setOnClickListener(v -> opentask());

        ImageButton notesbutton = findViewById(R.id.notesbutton);
        notesbutton.setOnClickListener(v -> opennotes());

        ImageButton settingsbutton = findViewById(R.id.settingsbutton);
        settingsbutton.setOnClickListener(v -> opensettings());
    }
    public void openstats() {
        Intent intent = new Intent(this, stats.class);
        startActivity(intent);
    }
    public void opennotes() {
        Intent intent = new Intent(this, notes.class);
        startActivity(intent);
    }
    public void opentask() {
        Intent intent = new Intent(this, task.class);
        startActivity(intent);
    }
    public void opensettings() {
        Intent intent = new Intent(this, settings.class);
        startActivity(intent);
    }
}