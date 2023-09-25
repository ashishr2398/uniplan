package com.shivskj.uniplan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    
    private EditText eemail;
    private EditText epassword;
    private ProgressBar progressbar;

    FirebaseAuth mAuth;

    // ------------------------------------------------------------------------------------------------- START OF CODE #shivskj
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signin);


        eemail = findViewById(R.id.eemail);
        epassword = findViewById(R.id.epassword);
        progressbar = findViewById(R.id.progressbar);
        TextView fpassword = findViewById(R.id.fpassword);
        TextView website = findViewById(R.id.website);
        Button signinbutton = findViewById(R.id.signinbutton);
        Button signupbutton = findViewById(R.id.signupbutton);

        mAuth = FirebaseAuth.getInstance();

        // ----------------------------------------------------------------------------------------- SIGNIN BUTTON | AUTHENTICATION
        signinbutton.setOnClickListener(v ->
        {
            if (!validateemail() || !validatepassword())
            {
                Toast.makeText(signin.this, "Try Again",Toast.LENGTH_SHORT).show();
            }
            else
            {
                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(eemail.getText().toString(), epassword.getText().toString()).addOnSuccessListener(authResult ->
                        {
                            Toast.makeText(signin.this, "Signin Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signin.this, home.class);
                            startActivity(intent);
                        }
                ).addOnFailureListener(e ->
                        {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(signin.this, "Signin Failed", Toast.LENGTH_SHORT).show();
                        }
                );
            }

        }
        );

        // ----------------------------------------------------------------------------------------- SIGNUP BUTTON

        signupbutton.setOnClickListener(v -> opensignup());
        fpassword.setOnClickListener(v -> gotoUrl("https://uniplan.shivskj.com/password"));
        website.setOnClickListener(v -> gotoUrl("https://uniplan.shivskj.com"));
    }

    private void gotoUrl(String s)
    {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }


    public void opensignup() {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    // --------------------------------------------------------------------------------------------- VALIDATION

    public Boolean validateemail()
    {
        String email = eemail.getText().toString();

        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.isEmpty())
        {
            eemail.setError("Enter Email");
            eemail.requestFocus();
            return false;
        }
        else if(!email.matches(emailpattern))
        {
            eemail.setError("Enter correct Email");
            eemail.requestFocus();
            return false;
        }
        else
        {
            eemail.setError(null);
            return true;
        }
    }

    public Boolean validatepassword()
    {
        String password = epassword.getText().toString();

        if(password.isEmpty())
        {
            epassword.setError("Enter Password");
            epassword.requestFocus();
            return false;
        }
        else
        {
            epassword.setError(null);
            return true;
        }
    }
// ------------------------------------------------------------------------------------------------- END OF CODE #shivskj
}
