package com.chistia007.cgpadom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chistia007.cgpadom.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //mRootRef= FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName=binding.edtName.getText().toString();
                String txtEmail=binding.edtEmail.getText().toString();
                String txtPassword=binding.edtPassword.getText().toString();

                if(txtName.equals("") || txtEmail.equals("") || txtPassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(TextUtils.isEmpty(txtName)|| TextUtils.isEmpty(txtEmail)|| TextUtils.isEmpty(txtPassword)){
                        Toast.makeText(RegisterActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                    }
                    else if(txtPassword.length()<6){
                        Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        registerUser(txtEmail,txtPassword);
                    }
                }

            }

            private void registerUser(String email, String password) {
                progressDialog.setTitle("Please wait...");
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String,String> user=new HashMap<>();
                    user.put("name",binding.edtName.getText().toString());
                    user.put("email",binding.edtEmail.getText().toString());
                    user.put("password",binding.edtPassword.getText().toString());
                    user.put("credit","0");
                    user.put("cgpa","0");

                    CollectionReference col = db.collection("Users");

                    col.document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).set(user).addOnSuccessListener(aVoid -> {

                    })
                            .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                    progressDialog.dismiss();
                    startActivity(new Intent(RegisterActivity.this,CgpaBracuCalculatorActivity.class));

                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}