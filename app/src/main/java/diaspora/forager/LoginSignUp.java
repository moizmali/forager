package diaspora.forager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoginSignUp extends AppCompatActivity {

    private ViewGroup animsLayout;
    private ViewGroup loginLayout;

    private Button login;
    private TextView loginLabel;
    private TextView loginSuccess;
    private Button signUp;

    private AutoTransition autoTransition;

    private void setComponents() {
        animsLayout = (ViewGroup) findViewById(R.id.animsLayout);
        loginLayout = (ViewGroup) findViewById(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        loginLabel = (TextView) findViewById(R.id.loginLabel);
        loginSuccess = (TextView) findViewById(R.id.loginSuccess);
        signUp = (Button) findViewById(R.id.signup);

        autoTransition = new AutoTransition();
    }

    private void setOnClickListeners() {
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
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, SignUp.class),options.toBundle());
            }
        });
    }

    private void setupElements() {
        loginLabel.setVisibility(View.GONE);loginLabel.setVisibility(View.VISIBLE);
        loginSuccess.setVisibility(View.VISIBLE);loginSuccess.setVisibility(View.GONE);

        autoTransition.setDuration(1000);
        autoTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                LoginSignUp.this.getWindow().setExitTransition(new Slide());
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(LoginSignUp.this);
                startActivity(new Intent(LoginSignUp.this, Login.class),options.toBundle());
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        setComponents();
        setOnClickListeners();
        setupElements();
    }

    @Override
    public void onStop() {
        super.onStop();

        loginLabel.setVisibility(View.VISIBLE);
        loginSuccess.setVisibility(View.GONE);
    }
}
