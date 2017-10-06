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
        Intent intent = new Intent(SplashScreen.this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
