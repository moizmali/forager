package diaspora.forager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity {

    private EditText nickName;
    private EditText email;
    private EditText password;
    private EditText retypePassword;

    private Button signUp;
    private Button clear;

    private void setComponents() {
        nickName = (EditText) findViewById(R.id.nickName);
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
                // TODO complete
                String nickNameStr = nickName.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String retypePasswordStr = retypePassword.getText().toString();

                if (!nickNameStr.isEmpty() || !emailStr.isEmpty() || !passwordStr.isEmpty() || !retypePasswordStr.isEmpty()) {
                    if (passwordStr.equals(retypePasswordStr)) {

                    }
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
