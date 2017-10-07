package diaspora.forager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends Activity {

    private Button googleSignUp;
    private Button facebookSignUp;
    private Button emailSignUp;

    private void setComponents() {
        googleSignUp = (Button) findViewById(R.id.googleSignUp);
        facebookSignUp = (Button) findViewById(R.id.facebookSignUp);
        emailSignUp = (Button) findViewById(R.id.emailSignUp);
    }

    private void setOnClickListeners() {
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
            }
        });
        facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
            }
        });
        emailSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO complete
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
