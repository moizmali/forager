package diaspora.forager;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginSignUp extends AppCompatActivity {


    // TODO clean
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton googleSignIn;
    private static final int RC_SIGN_IN = 123;

    private ViewGroup animsLayout;
    private ViewGroup loginLayout;

    private Button login;
    private TextView loginLabel;
    private TextView loginSuccess;
    private Button signUp;

    private AutoTransition autoTransition;

    private void setComponents() {
        // TODO Clean
        googleSignIn = (SignInButton) findViewById(R.id.googleSignIn);
        mAuth = FirebaseAuth.getInstance();

        animsLayout = (ViewGroup) findViewById(R.id.animsLayout);
        loginLayout = (ViewGroup) findViewById(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        loginLabel = (TextView) findViewById(R.id.loginLabel);
        loginSuccess = (TextView) findViewById(R.id.loginSuccess);
        signUp = (Button) findViewById(R.id.signup);

        autoTransition = new AutoTransition();
    }

    private void setOnClickListeners() {
        // TODO clean
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(animsLayout, autoTransition);
                loginLabel.setVisibility(View.GONE);
                loginSuccess.setVisibility(View.VISIBLE);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSignUp.this.getWindow().setExitTransition(new Slide());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, SignUp.class), options.toBundle());
            }
        });

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void setupElements() {
        loginLabel.setVisibility(View.GONE);
        loginLabel.setVisibility(View.VISIBLE);
        loginSuccess.setVisibility(View.VISIBLE);
        loginSuccess.setVisibility(View.GONE);

        autoTransition.setDuration(1000);
        autoTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                LoginSignUp.this.getWindow().setExitTransition(new Slide());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, Login.class), options.toBundle());
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        setComponents();
        setOnClickListeners();
        setupElements();

        // TODO clean (add to another method to make it better)
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(LoginSignUp.this)
                .enableAutoManage(LoginSignUp.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        new AlertDialog.Builder(LoginSignUp.this).setTitle("Error")
                                .setMessage("An Error Occurred When Creating GoogleAPIClient")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // DO NOTHING
                                    }
                                })
                                .show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO clean and handle errors
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // TODO Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // TODO clean, try to merge methods
        System.out.println("firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginSignUp.this, Login.class));
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
    public void onStop() {
        super.onStop();

        loginLabel.setVisibility(View.VISIBLE);
        loginSuccess.setVisibility(View.GONE);
    }
}
