package diaspora.forager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;

public class LoginSignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private SignInButton googleSignIn;
    private static final int RC_SIGN_IN = 123;
    private long lastClickTime = 0;

    private Button login;
    private TextView loginLabel;
    private Button signUp;

    private void setComponents() {
        firebaseAuth = FirebaseAuth.getInstance();
        googleSignIn = (SignInButton) findViewById(R.id.googleSignIn);

        login = (Button) findViewById(R.id.login);
        loginLabel = (TextView) findViewById(R.id.loginLabel);
        signUp = (Button) findViewById(R.id.signup);
    }

    private void setOnClickListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUp.this.getWindow().setExitTransition(new Slide(Gravity.RIGHT).setDuration(800));
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, Login.class), options.toBundle());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUp.this.getWindow().setExitTransition(new Slide(Gravity.LEFT).setDuration(800));
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, SignUp.class), options.toBundle());
            }
        });
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Does not let the user click the sign in button multiple times
                if (SystemClock.elapsedRealtime() - lastClickTime < 3000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                // Open The Sign In Intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void setGoogleApiClient() {
        // Configure Google Sign In Options
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(LoginSignUp.this)
                .enableAutoManage(LoginSignUp.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginSignUp.this, "Unable to sign up with google", Toast.LENGTH_LONG).show();
                        FirebaseCrash.report(new Exception("Unable to sign up with google"));
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // TODO get the nickname
                            Toast.makeText(LoginSignUp.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginSignUp.this, MainMenu.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println("signInWithCredential:failure" + task.getException());
                            Toast.makeText(LoginSignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        setComponents();
        setOnClickListeners();
        setGoogleApiClient();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // TODO check this (bug here)
                // Google sign in unsuccessful
            }
        }
    }
}
