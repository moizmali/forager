package diaspora.forager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";

    // TODO Get rid of this and make the button unclickable.
    private long mLastClickTime = 0;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private EditText email;
    private EditText password;
    private EditText retypePassword;

    private Button signUp;
    private Button clear;

    private void setComponents() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        retypePassword = (EditText) findViewById(R.id.retypePassword);

        signUp = (Button) findViewById(R.id.signUp);
        clear = (Button) findViewById(R.id.clear);
    }

    private void setOnClickListeners() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does not let the user click the sign in button multiple times
                // TODO get rid of this and add a loading section
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                final String emailStr = email.getText().toString();
                final String passwordStr = password.getText().toString();
                final String retypePasswordStr = retypePassword.getText().toString();

                if (!emailStr.isEmpty() && !passwordStr.isEmpty() && !retypePasswordStr.isEmpty()) {
                    if (passwordStr.equals(retypePasswordStr)) {
                        if (passwordStr.length() >= 6) {
                            new AlertDialog.Builder(SignUp.this).setTitle("Terms of Service")
                                    .setMessage(R.string.termsOfService)
                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // DO NOTHING
                                        }
                                    })
                                    .setPositiveButton("I Accept", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            createUserWithEmailAndPassword(emailStr, passwordStr);
                                        }
                                    })
                                    .show();
                        } else {
                            new AlertDialog.Builder(SignUp.this).setTitle("Password Too Short")
                                    .setMessage("The password should be a minimum of six characters")
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // DO NOTHING
                                        }
                                    })
                                    .show();
                        }
                    } else {
                        new AlertDialog.Builder(SignUp.this).setTitle("Passwords Do Not Match")
                                .setMessage("The 2 passwords that you have entered do not match")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // DO NOTHING
                                    }
                                })
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(SignUp.this).setTitle("Empty Fields")
                            .setMessage("Please Do Not Leave Any Fields Empty")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // DO NOTHING
                                }
                            })
                            .show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText(null);
                password.setText(null);
                retypePassword.setText(null);
            }
        });
    }

    private void createUserWithEmailAndPassword(String emailStr, String passwordStr) {
        firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeNewUserToDatabase(databaseReference, firebaseAuth.getCurrentUser());
                            Toast.makeText(SignUp.this, "Account Successfully Created", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, MainMenu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            new AlertDialog.Builder(SignUp.this).setTitle("Account Invalid/Taken")
                                    .setMessage("The following account is either invalid or already exists, please re-check your information")
                                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // DO NOTHING
                                        }
                                    })
                                    .show();
                            // TODO bug, this can happen if the user enters an invalid email address as well
                            Log.e(TAG, "User attempting to create an account that already exists");
                            FirebaseCrash.log("User attempting to create an account that already exists");
                        }
                    }
                });
    }

    public void writeNewUserToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            User user = new User(firebaseUser.getEmail(), 0, 0);
            databaseReference.child("users")
                    .child(firebaseUser.getUid()).setValue(user);
        } catch (Throwable e) {
            Log.e("Unidentified Exception", e.toString());
            FirebaseCrash.report(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setComponents();
        setOnClickListeners();
    }

}
