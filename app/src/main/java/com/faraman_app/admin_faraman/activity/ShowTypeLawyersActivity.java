package com.faraman_app.admin_faraman.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.faraman_app.admin_faraman.R;
import com.faraman_app.admin_faraman.helper.TypeEditRecyclerViewAdapter;
import com.faraman_app.admin_faraman.interfaces.Initialize;
import com.faraman_app.admin_faraman.model.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTypeLawyersActivity extends AppCompatActivity implements Initialize {
    private TypeEditRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Type> types = new ArrayList<>();
    private Button buttonDone;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_type_lawers);
        init();

        adapter.setOnItemClickListener(new TypeEditRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemDeleteClick(String id, int p) {
                DeleteFormFireBase(id);
            }

            @Override
            public void onItemEditClick(String id) {
                EditProduct(id);

            }
        });
        recyclerView.setAdapter(adapter);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowTypeLawyersActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void init() {
        buttonDone = findViewById(R.id.buttonDone);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_wait);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowTypeLawyersActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        progressBar.setVisibility(View.VISIBLE);

        adapter = new TypeEditRecyclerViewAdapter(ShowTypeLawyersActivity.this, types);
        handleFireBase();
    }

    @SuppressLint("NewApi")
    @Override
    public void intentTo(Context context, Class toClass) {
        startActivity(new Intent(context, toClass), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    private void handleFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("TypeOfLawyers");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Type type = ds.getValue(Type.class);
                    assert type != null;
                    types.add(type);
                    count++;
                    Log.e("100", "onDataChange: " + count);

                }
                if (count == types.size()) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("100", "onDataChange: hihih" + types.get(1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    private void DeleteFormFireBase(String id) {

        firebaseDatabase.getReference("TypeOfLawyers").child(id).removeValue();
        intentTo(ShowTypeLawyersActivity.this, ShowTypeLawyersActivity.class);


       /* for (Type product : types) {
            if (product.getId().equals(id)) {
                types.remove(product);
                Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void EditProduct(String id) {
        Intent intent = new Intent(ShowTypeLawyersActivity.this, TypeOfLawyersActivity.class);
        intent.putExtra("typeId", id);
        startActivity(intent);
        finish();
    }


}
