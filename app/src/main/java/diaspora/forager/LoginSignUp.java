package diaspora.forager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class LoginSignUp extends AppCompatActivity {

    private Button login;
    private Button signUp;

    private void setComponents() {
        login = (Button) findViewById(R.id.login);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        setComponents();
        setOnClickListeners();
    }
}
