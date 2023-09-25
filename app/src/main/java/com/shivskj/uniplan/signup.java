package com.shivskj.uniplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private EditText ename;
    private EditText eemail;
    private EditText epassword;
    private EditText cpassword;
    private ProgressBar progressbar;

    FirebaseDatabase logindb = FirebaseDatabase.getInstance();
    DatabaseReference loginrf = logindb.getReference("users");


    FirebaseAuth mAuth;

// ------------------------------------------------------------------------------------------------- START OF CODE #shivskj
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup);


        ename = findViewById(R.id.ename);
        eemail = findViewById(R.id.eemail);
        epassword = findViewById(R.id.epassword);
        cpassword = findViewById(R.id.cpassword);
        progressbar = findViewById(R.id.progressbar);
        Button signupbutton = findViewById(R.id.signupbutton);
        mAuth = FirebaseAuth.getInstance();

        // ----------------------------------------------------------------------------------------- SIGNUP BUTTON

        signupbutton.setOnClickListener(v ->
        {

            if (!enterdata())
            {
            Toast.makeText(signup.this, "Try Again",Toast.LENGTH_SHORT).show();
            }
            else {

            progressbar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(eemail.getText().toString(), epassword.getText().toString()).addOnSuccessListener(authResult ->
                {
                    Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, signin.class);
                    startActivity(intent);
                }
            ).addOnFailureListener(e ->
            {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(signup.this, "User Exist", Toast.LENGTH_SHORT).show();
            });

            }

        });



    // ----------------------------------------------------------------------------------------- CROSS BUTTON

        ImageButton crossbutton = findViewById(R.id.crossbutton);
        crossbutton.setOnClickListener(v -> openlogin());


    }

    public void openlogin()
    {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }



    // --------------------------------------------------------------------------------------------- DATABASE ENTER | VALIDATION
    public Boolean enterdata()
    {

        logindb = FirebaseDatabase.getInstance();
        loginrf = logindb.getReference("users");

        String name = ename.getText().toString();
        String email = eemail.getText().toString();
        String password = epassword.getText().toString();
        String confirmpassword = cpassword.getText().toString();
        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String namepattern = "(?i)(^[a-z])((?![? .,'-]$)[ 1.]?[a-z]){3,24}$";

        if (name.isEmpty())
        {
            ename.setError("Enter name");
            ename.requestFocus();
            return false;
        }
        else if (!name.matches(namepattern)) {
            ename.setError("Enter correct name");
            ename.requestFocus();
            return false;
        }
        else if (email.isEmpty())
        {
            eemail.setError("Enter email");
            eemail.requestFocus();
            return false;
        }

        else if (!email.matches(emailpattern))
        {
            eemail.setError("Enter correct Email");
            eemail.requestFocus();
            return false;
        }
        else if (password.isEmpty())
        {
            epassword.setError("Enter password");
            epassword.requestFocus();
            return false;
        }
        else if(password.length()<9)
        {
            epassword.setError("Password length too short < 9");
            epassword.requestFocus();
            return false;
        }
        else if (confirmpassword.isEmpty())
        {
            cpassword.setError("Enter password");
            cpassword.requestFocus();
            return false;
        }
        else if(!password.equals(confirmpassword))
        {
            cpassword.setError("Password do not match");
            cpassword.requestFocus();
            return false;
        }
        else
        {
            logindata logindata = new logindata(name,email,password,confirmpassword);

            loginrf.child(name).setValue(logindata);

            return true;
        }
    }

// ------------------------------------------------------------------------------------------------- END OF CODE #shivskj
}