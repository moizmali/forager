package diaspora.forager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Check if user is already signed in (if not then send to loginSignUp Page).
        // TODO check whether mobile data/WIFI is connected.

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // If the user is already signed in, then go to the main menu activity.
            startActivity(new Intent(SplashScreen.this, MainMenu.class));
            finish();
        } else {
            // If no user is signed in, go to the login-signup activity.
            startActivity(new Intent(SplashScreen.this, LoginSignUp.class));
            finish();
        }
    }

}
