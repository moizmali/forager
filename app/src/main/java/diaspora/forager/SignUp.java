package diaspora.forager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends Activity {

    private long mLastClickTime = 0;

    private FirebaseAuth firebaseAuth;

    private EditText nickName;
    private EditText email;
    private EditText password;
    private EditText retypePassword;

    private Button signUp;
    private Button clear;

    private void setComponents() {
        firebaseAuth = FirebaseAuth.getInstance();

        nickName = (EditText) findViewById(R.id.nickName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        retypePassword = (EditText) findViewById(R.id.retypePassword);

        signUp = (Button) findViewById(R.id.signUp);
        clear = (Button) findViewById(R.id.clear);
    }

    private void setOnClickListeners() {
        signUp.setOnClickListener(new View.OnClickListener() {
            // TODO make sure this is only clickable once
            @Override
            public void onClick(View v) {
                // TODO test this to see if it works.
                if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                // TODO complete
                String nickNameStr = nickName.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String retypePasswordStr = retypePassword.getText().toString();

                if (!nickNameStr.isEmpty() && !emailStr.isEmpty() && !passwordStr.isEmpty() && !retypePasswordStr.isEmpty()) {
                    if (passwordStr.equals(retypePasswordStr)) {
                        if (passwordStr.length() >= 6) {
                            firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(SignUp.this, MainMenu.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // TODO this is also if the given account already exists, add a message for that
                                                new AlertDialog.Builder(SignUp.this).setTitle("Error")
                                                        .setMessage("An Internal Error Occurred When Attempting To Sign Up")
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
                nickName.setText(null);
                email.setText(null);
                password.setText(null);
                retypePassword.setText(null);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setComponents();
        setOnClickListeners();
    }

}
