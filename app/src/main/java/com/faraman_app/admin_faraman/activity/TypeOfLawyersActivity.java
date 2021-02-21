package com.faraman_app.admin_faraman.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faraman_app.admin_faraman.R;
import com.faraman_app.admin_faraman.interfaces.Initialize;
import com.faraman_app.admin_faraman.model.Type;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;


public class TypeOfLawyersActivity extends AppCompatActivity implements Initialize {
    Toolbar toolbar;
    ImageView imageViewBack;
    TextView textViewTitle;
    Button buttonAddTypeOfLawyers, buttonShowTypeOfLawyers;
    DatabaseReference reference;
    EditText editTextNameOfType;
    FirebaseDatabase firebaseDatabase;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_lawyers);
        init();
        id = getIntent().getStringExtra("typeId");
        if (id != null) {
            buttonShowTypeOfLawyers.setVisibility(View.GONE);
            textViewTitle.setText("تعديل التخصص");
            buttonAddTypeOfLawyers.setText("تعديل");
            Query query = firebaseDatabase.getReference().child("TypeOfLawyers").child(id).orderByChild("id");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0

                        Type type = dataSnapshot.getValue(Type.class);
                        assert type != null;
                        Log.e("123", "onDataChangefor: " + type.getName());
                        edit(type);
                        firebaseDatabase.getReference("TypeOfLawyers").child(type.getId()).removeValue();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {


            buttonShowTypeOfLawyers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intentTo(TypeOfLawyersActivity.this, ShowTypeLawyersActivity.class);

                }
            });

        }
        buttonAddTypeOfLawyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_nameOfType = editTextNameOfType.getText().toString();

                if (TextUtils.isEmpty(txt_nameOfType)) {
                    Toast.makeText(TypeOfLawyersActivity.this, "جميع الخانات مطلوبة", Toast.LENGTH_SHORT).show();
                } else {
                    addType(txt_nameOfType);
                }

            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addType(String txt_nameOfType) {

        String typeId = UUID.randomUUID().toString();

        reference = firebaseDatabase.getReference("TypeOfLawyers").child(typeId);

        Type type = new Type();
        type.setId(typeId);
        type.setName(txt_nameOfType);

        reference.setValue(type).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful() && id == null) {
                    editTextNameOfType.setText("");
                    Toast.makeText(TypeOfLawyersActivity.this, "تم إضافة التخصص بنجاح", Toast.LENGTH_SHORT).show();
                } else if (task.isSuccessful() && id != null) {
                    intentTo(TypeOfLawyersActivity.this, TypeOfLawyersActivity.class);
                    Toast.makeText(TypeOfLawyersActivity.this, "تم تعدل التخصص بنجاح", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void edit(Type type) {
        editTextNameOfType.setText(type.getName());
    }

    @Override
    public void init() {

        toolbar = findViewById(R.id.toolbar);
        editTextNameOfType = findViewById(R.id.editTextNameOfType);
        buttonAddTypeOfLawyers = findViewById(R.id.buttonAddTypeOfLawyers);
        buttonShowTypeOfLawyers = findViewById(R.id.buttonShowTypeOfLawyers);
        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewBack = toolbar.findViewById(R.id.imageViewBack);

        firebaseDatabase = FirebaseDatabase.getInstance();

    }

    @Override
    public void intentTo(Context context, Class toClass) {

        Intent intent = new Intent(context, toClass);
        startActivity(intent);
        finish();

    }


}
