package com.shivskj.uniplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    ImageButton crossbutton = findViewById(R.id.crossbutton);
        crossbutton.setOnClickListener(v -> openhome());

}

    public void openhome()
    {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }
}