package com.chistia007.cgpadom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.chistia007.cgpadom.databinding.ActivityCalculateCgpaBinding;

import java.util.Objects;

public class CalculateCgpaActivity extends AppCompatActivity {
    ActivityCalculateCgpaBinding binding;
    private Float currentCreditInt;
    private Float currentCgpaInt;
    private String credit1;
    private String credit2;
    private String credit3;
    private String credit4;
    private String credit5;
    private String credit6;
    private String credit7;
    private String credit8;
    private String cgpa1;
    private String cgpa2;
    private String cgpa3;
    private String cgpa4;
    private String cgpa5;
    private String cgpa6;
    private String cgpa7;
    private String cgpa8;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCalculateCgpaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toast.makeText(this, "First semester? leave first two fields empty.", Toast.LENGTH_LONG).show();



        binding.btnCalculate.setOnClickListener(view -> {
            String currentCredit= Objects.requireNonNull(binding.edtCurrentCredit.getText()).toString();
            String currentCgpa= Objects.requireNonNull(binding.edtCurrentCgpa.getText()).toString();
            if(currentCredit.equals("")|| currentCgpa.equals("")){
                currentCredit+="0";
                currentCgpa+="0";
            }

            credit1= Objects.requireNonNull(binding.edtCredit1.getText()).toString();
            credit2= Objects.requireNonNull(binding.edtCredit2.getText()).toString();
            credit3= Objects.requireNonNull(binding.edtCredit3.getText()).toString();
            credit4= Objects.requireNonNull(binding.edtCredit4.getText()).toString();
            credit5= Objects.requireNonNull(binding.edtCredit5.getText()).toString();
            credit6= Objects.requireNonNull(binding.edtCredit6.getText()).toString();
            credit7= Objects.requireNonNull(binding.edtCredit7.getText()).toString();
            credit8= Objects.requireNonNull(binding.edtCredit8.getText()).toString();

            cgpa1= Objects.requireNonNull(binding.edtCgpa1.getText()).toString();
            cgpa2= Objects.requireNonNull(binding.edtCgpa2.getText()).toString();
            cgpa3= Objects.requireNonNull(binding.edtCgpa3.getText()).toString();
            cgpa4= Objects.requireNonNull(binding.edtCgpa4.getText()).toString();
            cgpa5= Objects.requireNonNull(binding.edtCgpa5.getText()).toString();
            cgpa6= Objects.requireNonNull(binding.edtCgpa6.getText()).toString();
            cgpa7= Objects.requireNonNull(binding.edtCgpa7.getText()).toString();
            cgpa8= Objects.requireNonNull(binding.edtCgpa8.getText()).toString();

            currentCreditInt=Float.parseFloat(currentCredit);
            currentCgpaInt=Float.parseFloat(currentCgpa);
            calculateCgpa();
        });

        dropDownActivity();

    }
    private void calculateCgpa() {
        if(credit1.equals("")|| cgpa1.equals("")){
            Toast.makeText(CalculateCgpaActivity.this, "Empty fields detected", Toast.LENGTH_SHORT).show();
        }
        else{
            if(credit2.equals("")|| cgpa2.equals("")){
                credit2 +="0";
                cgpa2 +="0";
            }
            if(credit3.equals("")|| cgpa3.equals("")){
                credit3 +="0";
                cgpa3 +="0";
            }
            if(credit4.equals("")|| cgpa4.equals("")){
                credit4 +="0";
                cgpa4 +="0";
            }
            if(credit5.equals("")|| cgpa5.equals("")){
                credit5 +="0";
                cgpa5 +="0";

            }

            if(credit6.equals("")|| cgpa6.equals("")){
                credit6 +="0";
                cgpa6 +="0";
            }
            if(credit7.equals("")|| cgpa7.equals("")){
                credit7 +="0";
                cgpa7 +="0";
            }
            if(credit8.equals("")|| cgpa8.equals("")){
                credit8 +="0";
                cgpa8 +="0";

            }

            Float finalCredit1 = Float.parseFloat(credit1);
            Float finalCgpa1 = Float.parseFloat(cgpa1);

            Float finalCredit2 = Float.parseFloat(credit2);
            Float finalCgpa2 = Float.parseFloat(cgpa2);

            Float finalCredit3 = Float.parseFloat(credit3);
            Float finalCgpa3 = Float.parseFloat(cgpa3);

            Float finalCredit4 = Float.parseFloat(credit4);
            Float finalCgpa4 = Float.parseFloat(cgpa4);

            Float finalCredit5 = Float.parseFloat(credit5);
            Float finalCgpa5 = Float.parseFloat(cgpa5);

            Float finalCredit6 = Float.parseFloat(credit6);
            Float finalCgpa6 = Float.parseFloat(cgpa6);

            Float finalCredit7 = Float.parseFloat(credit7);
            Float finalCgpa7 = Float.parseFloat(cgpa7);

            Float finalCredit8 = Float.parseFloat(credit8);
            Float finalCgpa8 = Float.parseFloat(cgpa8);

            float totalCredit = currentCreditInt + finalCredit1 + finalCredit2 + finalCredit3 + finalCredit4 + finalCredit5 + finalCredit6 + finalCredit7 + finalCredit8;
            float totalCgpa = ((currentCreditInt * currentCgpaInt) + (finalCredit1 * finalCgpa1) + (finalCredit2 * finalCgpa2) + (finalCredit3 * finalCgpa3) + (finalCredit4 * finalCgpa4) + (finalCredit5 * finalCgpa5) + (finalCredit6 * finalCgpa6) + (finalCredit7 * finalCgpa7) + (finalCredit8 * finalCgpa8)) / totalCredit;
            String totalCreditStr = Float.toString(totalCredit);
            String totalCgpaStr = Float.toString(totalCgpa);

            //Dialog box to show cgpa after calculation
            AlertDialog.Builder builder=new AlertDialog.Builder(CalculateCgpaActivity.this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage("Your total Credit: "+ totalCreditStr + "\n\n"+
                    "Your CGPA: " + totalCgpaStr);
            builder.setNegativeButton("Dismiss", (dialog, which) -> {

            });


            builder.create().show();

        }



    }

    private void dropDownActivity() {
        String[] items=new String[]{
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8"

        };

        ArrayAdapter<String> adapter= new ArrayAdapter<>(
                CalculateCgpaActivity.this,
                R.layout.drop_down_item,
                items
        );

        binding.dropDownText.setAdapter(adapter);

        binding.btnProcess.setOnClickListener(view -> {
            text=binding.dropDownText.getText().toString();

            switch (text) {
                case "1":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.GONE);
                    binding.linearLayDiss3.setVisibility(View.GONE);
                    binding.linearLayDiss4.setVisibility(View.GONE);
                    binding.linearLayDiss5.setVisibility(View.GONE);
                    binding.linearLayDiss6.setVisibility(View.GONE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.GONE);
                    binding.linearLayDiss4.setVisibility(View.GONE);
                    binding.linearLayDiss5.setVisibility(View.GONE);
                    binding.linearLayDiss6.setVisibility(View.GONE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.GONE);
                    binding.linearLayDiss5.setVisibility(View.GONE);
                    binding.linearLayDiss6.setVisibility(View.GONE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "4":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.VISIBLE);
                    binding.linearLayDiss5.setVisibility(View.GONE);
                    binding.linearLayDiss6.setVisibility(View.GONE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.VISIBLE);
                    binding.linearLayDiss5.setVisibility(View.VISIBLE);
                    binding.linearLayDiss6.setVisibility(View.GONE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "6":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.VISIBLE);
                    binding.linearLayDiss5.setVisibility(View.VISIBLE);
                    binding.linearLayDiss6.setVisibility(View.VISIBLE);
                    binding.linearLayDiss7.setVisibility(View.GONE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "7":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.VISIBLE);
                    binding.linearLayDiss5.setVisibility(View.VISIBLE);
                    binding.linearLayDiss6.setVisibility(View.VISIBLE);
                    binding.linearLayDiss7.setVisibility(View.VISIBLE);
                    binding.linearLayDiss8.setVisibility(View.GONE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                case "8":
                    binding.linearLayDiss1.setVisibility(View.VISIBLE);
                    binding.linearLayDiss2.setVisibility(View.VISIBLE);
                    binding.linearLayDiss3.setVisibility(View.VISIBLE);
                    binding.linearLayDiss4.setVisibility(View.VISIBLE);
                    binding.linearLayDiss5.setVisibility(View.VISIBLE);
                    binding.linearLayDiss6.setVisibility(View.VISIBLE);
                    binding.linearLayDiss7.setVisibility(View.VISIBLE);
                    binding.linearLayDiss8.setVisibility(View.VISIBLE);
                    binding.layoutCalculate.setVisibility(View.VISIBLE);
                    break;
                default:
                    Toast.makeText(CalculateCgpaActivity.this, "Not a valid number", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }


}