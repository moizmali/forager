package diaspora.forager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    private Button googleLogin;
    private Button facebookLogin;
    private Button emailLogin;

    private void setComponents() {
        googleLogin = (Button) findViewById(R.id.googleLogin);
        facebookLogin = (Button) findViewById(R.id.facebookLogin);
        emailLogin = (Button) findViewById(R.id.emailLogin);
    }

    private void setOnClickListeners() {
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
            }
        });
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
            }
        });
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
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
