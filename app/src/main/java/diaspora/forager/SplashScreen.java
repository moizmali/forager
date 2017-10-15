package diaspora.forager;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private boolean isNetworkAvailable() {
        // TODO send a volley request to check for conn.
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(SplashScreen.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNetworkAvailable()) {
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
        } else {
            new AlertDialog.Builder(SplashScreen.this).setTitle("Network Unavailable")
                    .setMessage("Please turn on WIFI/Mobile Data to continue")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .show();
        }
    }

}
