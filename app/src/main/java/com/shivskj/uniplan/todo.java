package com.shivskj.uniplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.shivskj.uniplan.Adapter.ToDoAdapter;
import com.shivskj.uniplan.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class todo extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    protected List<ToDoModel> mList;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        FloatingActionButton mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();



        mFab.setOnClickListener(v -> newtask.newInstance().show(getSupportFragmentManager() , newtask.TAG));

        mList = new ArrayList<>();

    }
    private void showData(){
        Query query = firestore.collection("task").orderBy("time", Query.Direction.DESCENDING);

        listenerRegistration = query.addSnapshotListener((value, error) -> {
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String id = documentChange.getDocument().getId();
                    ToDoModel toDoModel = documentChange.getDocument().toObject(ToDoModel.class).withId(id);
                    mList.add(toDoModel);
                    adapter.notifyDataSetChanged();
                }
            }
            listenerRegistration.remove();

        });


        ImageButton crossbutton = findViewById(R.id.crossbutton);
        crossbutton.setOnClickListener(v -> opentask());


    }
    public void opentask()
    {
        Intent intent = new Intent(this, task.class);
        startActivity(intent);
    }

}