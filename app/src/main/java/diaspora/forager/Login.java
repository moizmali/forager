package diaspora.forager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    // TODO Get rid of this and make the button unclickable.
    private long lastClickTime = 0;

    private FirebaseAuth firebaseAuth;

    private EditText email;
    private EditText password;

    private Button login;

    private void setComponents() {
        firebaseAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
    }

    private void setOnClickListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does not let the user click the sign in button multiple times
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                if (!emailStr.isEmpty() && !passwordStr.isEmpty()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        firebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(Login.this, MainMenu.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            new AlertDialog.Builder(Login.this).setTitle("Invalid Details")
                                                    .setMessage("The Email/Password you have entered is incorrect")
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
                    } else {
                        Log.e(TAG, "Attempt to login, when a user is already logged in");
                        Toast.makeText(Login.this, "A user is already logged in", Toast.LENGTH_LONG).show();
                        FirebaseCrash.report(new Exception("Attempt to login, when a user is already logged in"));
                    }
                } else {
                    new AlertDialog.Builder(Login.this).setTitle("Empty Fields")
                            .setMessage("Please do not leave any fields empty")
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setComponents();
        setOnClickListeners();
    }
}
