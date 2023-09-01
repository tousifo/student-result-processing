package com.chistia007.cgpadom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.chistia007.cgpadom.databinding.ActivityStartBinding;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    ActivityStartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.layoutLinear.animate().alpha(0f).setDuration(1);
        TranslateAnimation animation=new TranslateAnimation(0,0,0,-1000);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        binding.imgIcon.setAnimation(animation);

        animation.setAnimationListener(new MyAnimationListener());

        binding.btnCgpaCal.setOnClickListener(view -> startActivity(new Intent(StartActivity.this,CalculateCgpaActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)));


        binding.btnBracuStudent.setOnClickListener(view -> startActivity(new Intent(StartActivity.this,BracuStartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)));

        binding.btnAboutUS.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(StartActivity.this);
            builder.setTitle("Student Result Processing");
            builder.setMessage("Designed and Developed by Tousif bin Parves\n"+
                    "From Comilla University");
            builder.setNegativeButton("Dismiss", (dialog, which) -> {

            });

            builder.create().show();
        });
    }


    private class MyAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            binding.imgIcon.clearAnimation();
            binding.imgIcon.setVisibility(View.INVISIBLE);
            binding.layoutLinear.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(StartActivity.this,CgpaBracuCalculatorActivity.class));
            finish();
        }
    }
}