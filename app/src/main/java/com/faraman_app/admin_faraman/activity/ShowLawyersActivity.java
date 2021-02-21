package com.faraman_app.admin_faraman.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.faraman_app.admin_faraman.helper.AdvisersAdapter;
import com.faraman_app.admin_faraman.model.Adviser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faraman_app.admin_faraman.R;

public class ShowLawyersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private AdvisersAdapter adviserAdapter;
    private List<Adviser> mAdvisers;

    EditText search_advisers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lawyers);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdvisers = new ArrayList<>();

        readAdvisers();

//        search_advisers = findViewById(R.id.search_advisers);
/*
        search_advisers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
*/


    }

    private void searchUsers(String s) {

        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdvisers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Adviser adviser = snapshot.getValue(Adviser.class);

                    assert adviser != null;
                    assert fuser != null;
                    if (!adviser.getId().equals(fuser.getUid())) {
                        mAdvisers.add(adviser);
                    }
                }

                adviserAdapter = new AdvisersAdapter(getBaseContext(), mAdvisers, false);
                recyclerView.setAdapter(adviserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readAdvisers() {

        final FirebaseUser firebaseAdviser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Adviser");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_advisers.getText().toString().equals("")) {
                    mAdvisers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Adviser adviser = snapshot.getValue(Adviser.class);

                        if (!adviser.getId().equals(firebaseAdviser.getUid())) {
                            mAdvisers.add(adviser);
                        }

                    }

                    adviserAdapter = new AdvisersAdapter(getBaseContext(), mAdvisers, false);
                    recyclerView.setAdapter(adviserAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
