package com.chistia007.cgpadom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chistia007.cgpadom.databinding.ActivityUpdateDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class UpdateDashboard extends AppCompatActivity {
    ActivityUpdateDashboardBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        binding.btnUpdateDash.setOnClickListener(view -> {
            binding.layVisibility1.setVisibility(View.VISIBLE);
            binding.layVisibility2.setVisibility(View.VISIBLE);


            binding.btnUpdate.setOnClickListener(view1 -> {
                String credit= Objects.requireNonNull(binding.edtCurrentCredit.getText()).toString();
                String cgpa= Objects.requireNonNull(binding.edtCurrentCgpa.getText()).toString();
                db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .update(
                                "cgpa", cgpa,
                                "credit", credit
                        );
                Intent intent= new Intent(UpdateDashboard.this, CgpaBracuCalculatorActivity.class);
                startActivity(intent);
            });
        });




        binding.btnResetDash.setOnClickListener(view -> {
            db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .update(
                            "cgpa", "0",
                            "credit", "0"
                    );
            Intent intent= new Intent(UpdateDashboard.this, CgpaBracuCalculatorActivity.class);
            startActivity(intent);
        });
    }
}