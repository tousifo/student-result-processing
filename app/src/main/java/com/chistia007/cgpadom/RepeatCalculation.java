package com.chistia007.cgpadom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.chistia007.cgpadom.databinding.ActivityRepeatCalculationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RepeatCalculation extends AppCompatActivity {
    ActivityRepeatCalculationBinding binding;
    private String text;
    private String currentCredit;
    private String currentCgpa;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRepeatCalculationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        docRef = db.collection("Users").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    //Setting data to dash board
                    binding.txtName.setText(documentSnapshot.getString("name"));
                    binding.txtCredit.setText(documentSnapshot.getString("credit"));
                    binding.txtCgpa.setText(documentSnapshot.getString("cgpa"));

                    //Storing data for calculation
                    currentCredit=documentSnapshot.getString("credit");
                    currentCgpa= documentSnapshot.getString("cgpa");
                }
                else{
                    Toast.makeText(RepeatCalculation.this, "Row not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dropDownWork();

        calculateActivity();


    }

    private void calculateActivity() {
        binding.btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String prevCgpa1=binding.edtPrevCgpa1.getText().toString();
                String prevCgpa2=binding.edtPrevCgpa2.getText().toString();
                String prevCgpa3=binding.edtPrevCgpa3.getText().toString();
                String prevCgpa4=binding.edtPrevCgpa4.getText().toString();
                String prevCgpa5=binding.edtPrevCgpa5.getText().toString();

                String ExpectedCgpa1=binding.edtExpCgpa1.getText().toString();
                String ExpectedCgpa2=binding.edtExpCgpa2.getText().toString();
                String ExpectedCgpa3=binding.edtExpCgpa3.getText().toString();
                String ExpectedCgpa4=binding.edtExpCgpa4.getText().toString();
                String ExpectedCgpa5=binding.edtExpCgpa5.getText().toString();

                Float currentCreditInt=Float.parseFloat(currentCredit);
                Float currentCgpaInt=Float.parseFloat(currentCgpa);

                String creditPerHour=binding.edtCreditPerHour.getText().toString();
                Float creditPerHourInt=Float.parseFloat(creditPerHour);


                if(prevCgpa1.equals("")|| ExpectedCgpa1.equals("")){
                    Toast.makeText(RepeatCalculation.this, "Empty fields detected", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(prevCgpa2.equals("")|| ExpectedCgpa2.equals("")){
                        prevCgpa2 +="0";
                        ExpectedCgpa2 +="0";
                    }
                    if(prevCgpa3.equals("")|| ExpectedCgpa3.equals("")){
                        prevCgpa3 +="0";
                        ExpectedCgpa3 +="0";
                    }
                    if(prevCgpa4.equals("")|| ExpectedCgpa4.equals("")){
                        prevCgpa4 +="0";
                        ExpectedCgpa4 +="0";
                    }
                    if(prevCgpa5.equals("")|| ExpectedCgpa5.equals("")){
                        prevCgpa5 +="0";
                        ExpectedCgpa5 +="0";

                    }



                    Float finalPrevCgpa1 = Float.parseFloat(prevCgpa1);
                    Float finalExpCgpa1 = Float.parseFloat(ExpectedCgpa1);

                    Float finalPrevCgpa2 = Float.parseFloat(prevCgpa2);
                    Float finalExpCgpa2 = Float.parseFloat(ExpectedCgpa2);

                    Float finalPrevCgpa3 = Float.parseFloat(prevCgpa3);
                    Float finalExpCgpa3 = Float.parseFloat(ExpectedCgpa3);

                    Float finalPrevCgpa4 = Float.parseFloat(prevCgpa4);
                    Float finalExpCgpa4 = Float.parseFloat(ExpectedCgpa4);

                    Float finalPrevCgpa5 = Float.parseFloat(prevCgpa5);
                    Float finalExpCgpa5 = Float.parseFloat(ExpectedCgpa5);


                    float totalCgpaTrim =  (currentCgpaInt*currentCreditInt) - (creditPerHourInt*(finalPrevCgpa1+finalPrevCgpa2+finalPrevCgpa3+finalPrevCgpa4+finalPrevCgpa5));
                    float totalCgpa= (totalCgpaTrim + (creditPerHourInt*(finalExpCgpa1+finalExpCgpa2+finalExpCgpa3+finalExpCgpa4+finalExpCgpa5)))/currentCreditInt;
                    String totalCgpaStr = Float.toString(totalCgpa);

                    //Dialog box to show cgpa after calculation
                    AlertDialog.Builder builder=new AlertDialog.Builder(RepeatCalculation.this);
                    builder.setTitle(getString(R.string.app_name));
                    builder.setMessage("Your total Credit: "+ currentCredit + "\n\n"+
                            "Your CGPA: " + totalCgpaStr);
                    builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setPositiveButton("Update to Dashboard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .update(
                                            "cgpa", totalCgpaStr
                                    );
                            Intent intent= new Intent(RepeatCalculation.this, CgpaBracuCalculatorActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.create().show();

                }
            }


        });
    }

    private void dropDownWork() {

        String[] items=new String[]{
                "1",
                "2",
                "3",
                "4",
                "5"

        };

        ArrayAdapter<String> adapter= new ArrayAdapter<>(
                RepeatCalculation.this,
                R.layout.drop_down_item,
                items
        );

        binding.dropDownText.setAdapter(adapter);

        binding.btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=binding.dropDownText.getText().toString();
                String editCreditPErHour=binding.edtCreditPerHour.getText().toString();
                if (editCreditPErHour.equals("")){
                    Toast.makeText(RepeatCalculation.this, "Please Enter your credit per hour", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (currentCredit.equals("0")){
                        Toast.makeText(RepeatCalculation.this, "Update Dashboard manually with CGPA and Credit first", Toast.LENGTH_LONG).show();
                    }
                    else{

                        if(text.equals("1")){
                            binding.linearLayDiss1.setVisibility(View.VISIBLE);
                            binding.linearLayDiss2.setVisibility(View.GONE);
                            binding.linearLayDiss3.setVisibility(View.GONE);
                            binding.linearLayDiss4.setVisibility(View.GONE);
                            binding.linearLayDiss5.setVisibility(View.GONE);
                            binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                        }
                        else if(text.equals("2")){
                            binding.linearLayDiss1.setVisibility(View.VISIBLE);
                            binding.linearLayDiss2.setVisibility(View.VISIBLE);
                            binding.linearLayDiss3.setVisibility(View.GONE);
                            binding.linearLayDiss4.setVisibility(View.GONE);
                            binding.linearLayDiss5.setVisibility(View.GONE);
                            binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                        }
                        else if(text.equals("3")){
                            binding.linearLayDiss1.setVisibility(View.VISIBLE);
                            binding.linearLayDiss2.setVisibility(View.VISIBLE);
                            binding.linearLayDiss3.setVisibility(View.VISIBLE);
                            binding.linearLayDiss4.setVisibility(View.GONE);
                            binding.linearLayDiss5.setVisibility(View.GONE);
                            binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                        }
                        else if(text.equals("4")){
                            binding.linearLayDiss1.setVisibility(View.VISIBLE);
                            binding.linearLayDiss2.setVisibility(View.VISIBLE);
                            binding.linearLayDiss3.setVisibility(View.VISIBLE);
                            binding.linearLayDiss4.setVisibility(View.VISIBLE);
                            binding.linearLayDiss5.setVisibility(View.GONE);
                            binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                        }
                        else if(text.equals("5")){
                            binding.linearLayDiss1.setVisibility(View.VISIBLE);
                            binding.linearLayDiss2.setVisibility(View.VISIBLE);
                            binding.linearLayDiss3.setVisibility(View.VISIBLE);
                            binding.linearLayDiss4.setVisibility(View.VISIBLE);
                            binding.linearLayDiss5.setVisibility(View.VISIBLE);
                            binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(RepeatCalculation.this, "Not a valid number", Toast.LENGTH_SHORT).show();
                        }

                }


                }

            }
        });
    }
}