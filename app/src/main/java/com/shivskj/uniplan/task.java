package com.shivskj.uniplan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.shivskj.uniplan.Adapter.ToDoAdapter;
import com.shivskj.uniplan.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class task extends AppCompatActivity implements OnDialogCloseListner{

    private FirebaseFirestore firestore;
    private ToDoAdapter adapter;
    protected List<ToDoModel> mList;
    private ListenerRegistration listenerRegistration;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton mFab = findViewById(R.id.floatingActionButton);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(task.this));

        mFab.setOnClickListener(v -> newtask.newInstance().show(getSupportFragmentManager() , newtask.TAG));

        mList = new ArrayList<>();
        adapter = new ToDoAdapter(task.this , mList);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        showData();
        recyclerView.setAdapter(adapter);
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
        crossbutton.setOnClickListener(v -> openhome());
        ImageButton starbutton = findViewById(R.id.starbutton);
        starbutton.setOnClickListener(v -> opentodo());


        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void openhome()
    {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void opentodo()
    {
        Intent intent = new Intent(this, todo.class);
        startActivity(intent);
    }




    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList.clear();
        showData();
        adapter.notifyDataSetChanged();
    }
}