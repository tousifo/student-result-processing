package com.chistia007.cgpadom;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.chistia007.cgpadom.databinding.ActivityCgpaBracuCalculatorBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class CgpaBracuCalculatorActivity extends AppCompatActivity {
    ActivityCgpaBracuCalculatorBinding binding;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private String text;
    private String currentCredit;
    private String currentCgpa;
    private String totalCreditStr;
    private String totalCgpaStr;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCgpaBracuCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, "Your Dashboard. Start calculating your CGPA!", Toast.LENGTH_LONG).show();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);


        // Navigation Drawer------------------------------

        toggle = new ActionBarDrawerToggle(CgpaBracuCalculatorActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Drawer click event
        // Drawer item Click event ------
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.DashBoard:
                    Toast.makeText(CgpaBracuCalculatorActivity.this, "Your Dashboard", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawers();
                    break;

                case R.id.RepeatCalculator:
                    startActivity(new Intent(CgpaBracuCalculatorActivity.this,RepeatCalculation.class));
                    break;

                case R.id.updateDashboard:
                    startActivity(new Intent(CgpaBracuCalculatorActivity.this,UpdateDashboard.class));
                    break;

                case R.id.logOut:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(CgpaBracuCalculatorActivity.this,StartActivity.class));
                    break;

            }

            return false;
        });
        //------------------------------

        // ------------------------
        // App Bar Click Event

        binding.imageMenu.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));




        //Welcome text

        docRef = db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        docRef.get().addOnSuccessListener(documentSnapshot -> {
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
                Toast.makeText(CgpaBracuCalculatorActivity.this, "Row not found", Toast.LENGTH_SHORT).show();
            }

        })
                .addOnFailureListener(e -> Toast.makeText(CgpaBracuCalculatorActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show());


        dropDownWork();

        calculateActivity();



    }

    private void calculateActivity() {
        binding.btnCalculate.setOnClickListener(view -> {

            String credit1= Objects.requireNonNull(binding.edtCredit1.getText()).toString();
            String credit2= Objects.requireNonNull(binding.edtCredit2.getText()).toString();
            String credit3= Objects.requireNonNull(binding.edtCredit3.getText()).toString();
            String credit4= Objects.requireNonNull(binding.edtCredit4.getText()).toString();
            String credit5= Objects.requireNonNull(binding.edtCredit5.getText()).toString();
            String credit6= Objects.requireNonNull(binding.edtCredit6.getText()).toString();
            String credit7= Objects.requireNonNull(binding.edtCredit7.getText()).toString();
            String credit8= Objects.requireNonNull(binding.edtCredit8.getText()).toString();

            String cgpa1= Objects.requireNonNull(binding.edtCgpa1.getText()).toString();
            String cgpa2= Objects.requireNonNull(binding.edtCgpa2.getText()).toString();
            String cgpa3= Objects.requireNonNull(binding.edtCgpa3.getText()).toString();
            String cgpa4= Objects.requireNonNull(binding.edtCgpa4.getText()).toString();
            String cgpa5= Objects.requireNonNull(binding.edtCgpa5.getText()).toString();
            String cgpa6= Objects.requireNonNull(binding.edtCgpa6.getText()).toString();
            String cgpa7= Objects.requireNonNull(binding.edtCgpa7.getText()).toString();
            String cgpa8= Objects.requireNonNull(binding.edtCgpa8.getText()).toString();

            Float currentCreditInt=Float.parseFloat(currentCredit);
            Float currentCgpaInt=Float.parseFloat(currentCgpa);


            if(credit1.equals("")|| cgpa1.equals("")){
                Toast.makeText(CgpaBracuCalculatorActivity.this, "Empty fields detected", Toast.LENGTH_SHORT).show();
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
                 totalCreditStr = Float.toString(totalCredit);
                 totalCgpaStr = Float.toString(totalCgpa);

                //Dialog box to show cgpa after calculation
                AlertDialog.Builder builder=new AlertDialog.Builder(CgpaBracuCalculatorActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setMessage("Your total Credit: "+ totalCreditStr + "\n\n"+
                        "Your CGPA: " + totalCgpaStr);
                builder.setNegativeButton("Dismiss", (dialog, which) -> {

                });

                builder.setPositiveButton("Update to Dashboard", (dialog, which) -> {
                    //DocumentReference washingtonRef = db.collection("cities").document(mAuth.getCurrentUser().getUid());

                    db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                            .update(
                                    "cgpa", totalCgpaStr,
                                    "credit", totalCreditStr
                            );
                    Intent intent= new Intent(CgpaBracuCalculatorActivity.this, CgpaBracuCalculatorActivity.class);
                    startActivity(intent);
                });
                builder.create().show();

            }
        });
    }

    private void dropDownWork() {

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
                CgpaBracuCalculatorActivity.this,
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
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
                    binding.btnCalculateVisibilty.setVisibility(View.VISIBLE);
                    break;
                default:
                    Toast.makeText(CgpaBracuCalculatorActivity.this, "Not a valid number", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CgpaBracuCalculatorActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}