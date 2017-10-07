package diaspora.forager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Check if user is already signed in (if not then send to loginSignUp Page).
        // TODO check whether mobile data/WIFI is connected.
//        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
        // If the user is already signed in, then go to the main menu activity.
        startActivity(new Intent(SplashScreen.this, MainMenu.class));
        finish();
//        } else {
        // If no user is signed in, go to the login-signup activity.
//            startActivity(new Intent(SplashScreen.this, LoginSignUp.class));
//            finish();
//        }
    }

}
