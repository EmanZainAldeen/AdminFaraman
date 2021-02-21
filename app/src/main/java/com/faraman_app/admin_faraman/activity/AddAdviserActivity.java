package com.faraman_app.admin_faraman.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.faraman_app.admin_faraman.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.faraman_app.admin_faraman.interfaces.Initialize;
import com.faraman_app.admin_faraman.model.Adviser;
import com.faraman_app.admin_faraman.model.Type;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;


public class AddAdviserActivity extends AppCompatActivity implements Initialize {

    private MaterialEditText editTextFullName, editTextEmail, editTextPassword;
    private CheckBox checkBoxIsStar;
    private Button buttonNewAccount;
    private Toolbar toolbar;
    private ImageView imageViewBack;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private FirebaseDatabase firebaseDatabase;
    private int count = 0;
    private String typeOfLawyer, username, email, password;
    private ProgressDialog dialog;
    private Spinner spinnerType;
    private ArrayList<String> typesName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lawyer);
        init();
        handleFireBaseForSpinner();


        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = editTextFullName.getText().toString();
                String txt_email = editTextEmail.getText().toString();
                String txt_password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) ||
                        TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(AddAdviserActivity.this, "جميع الخانات مطلوبة", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(AddAdviserActivity.this, "كلمة المرور يجب ان تكون اكثر من 6 خانات", Toast.LENGTH_SHORT).show();
                } else {
                    username = txt_username;
                    email = txt_email;
                    password = txt_password;
                    register();

                }
            }
        });

    }

    private void register() {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Advisers").child(userid);
                            /*String id, String adviserName, String imageURL,
                                    String email, String experience, String phoneNumber, String location,
                                    String status, String major, String bio, String search*/

                            Adviser adviser = new Adviser();
                            adviser.setId(userid);
                            adviser.setAdviserName(username);
                            adviser.setEmail(email);
                            adviser.setImageURL("default");
                            adviser.setStatus("offline");
                            adviser.setMajor(typeOfLawyer);
                            adviser.setSearch(username.toLowerCase());
                            adviser.setBio("محامي حديث الاضافة الى فـرمـان");
                            adviser.setLocation("الموقع :لم يتم التحديد بعد");
                            adviser.setPhoneNumber("رقم الهاتف :لم يتم التحديد بعد");
                            adviser.setExperience("عدد سنوات الخبرة :لم يتم التحديد بعد");
                            adviser.setStar(checkBoxIsStar.isChecked());

                            dialog = ProgressDialog.show(AddAdviserActivity.this, "من فضلك ، انتظر قليلاً ",
                                    "يتم تسجيل المحامي", true);
                            reference.setValue(adviser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        intentTo(AddAdviserActivity.this, MainActivity.class);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AddAdviserActivity.this, "لا يمكنك التسجيل باستخدام هذا البريد الإلكتروني أو كلمة المرور", Toast.LENGTH_SHORT).show();
                            Log.e("100", "onComplete: " + task.getResult());
                        }
                    }
                });
    }

    @Override
    public void init() {
        toolbar = findViewById(R.id.toolbar);
        spinnerType = findViewById(R.id.spinner_type);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageViewBack = toolbar.findViewById(R.id.imageViewBack);
        checkBoxIsStar = findViewById(R.id.checkBoxIsStar);
        buttonNewAccount = findViewById(R.id.buttonAddLawyer);
        auth = FirebaseAuth.getInstance();
        dialog = ProgressDialog.show(AddAdviserActivity.this, "من فضلك ، انتظر قليلاً ",
                "يتم تحميل التخصصات ..", true);
    }

    private void handleFireBaseForSpinner() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("TypeOfLawyers");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Type type = ds.getValue(Type.class);
                    assert type != null;
                    typesName.add(type.getName());
                    count++;

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddAdviserActivity.this, android.R.layout.simple_spinner_item, typesName);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerType.setAdapter(arrayAdapter);
                    spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            typeOfLawyer = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    dialog.dismiss();
                    Log.e("100", "onDataChange: " + count);

                }
                if (count == typesName.size()) {
                    Log.e("100", "onDataChange: oh,no");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    public void intentTo(Context context, Class toClass) {
        Intent intent = new Intent(context, toClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(AddAdviserActivity.this, "تمت اضافة المحامي بنجاح", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
