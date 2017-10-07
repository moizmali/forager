package diaspora.forager;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

public class SplashScreen extends Activity {

    private static final Logger logger = Logger.getLogger(SplashScreen.class.getName());

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(SplashScreen.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null);
    }

    private boolean hasActiveInternetConnection() {
        boolean hasInternetAccess = false;
        if (isNetworkAvailable()) {
            try {
                HttpsURLConnection httpsURLConnection =
                        (HttpsURLConnection) (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                httpsURLConnection.setRequestProperty("User-Agent", "Android");
                httpsURLConnection.setRequestProperty("Connection", "Close");
                httpsURLConnection.setConnectTimeout(1500);
                httpsURLConnection.connect();
                hasInternetAccess = (httpsURLConnection.getResponseCode() == 204 &&
                        httpsURLConnection.getContentLength() == 0);
            } catch (IOException e) {
                // TODO debug this, check whether it is correct.
                logger.log(Level.SEVERE, "Unable To Check For Internet Connection", e);
            }
        }
//        else {
//            // TODO Debug this, see whether it is correct.
//            return false;
//        }
        return hasInternetAccess;
    }

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
