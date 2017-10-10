package diaspora.forager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

public class AccountSettings extends AppCompatActivity {

    private Button logout;

    private void setComponents() {
        logout = (Button) findViewById(R.id.logout);
    }

    private void setOnClickListeners() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AccountSettings.this).setTitle("Logout")
                        .setMessage("Are You Sure You Wish To Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    FirebaseAuth.getInstance().signOut();
                                    Toast.makeText(AccountSettings.this, "Successfully Logged Out", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AccountSettings.this, LoginSignUp.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    FirebaseCrash.report(new Exception("Signed out user, trying to sign out again"));
                                    Toast.makeText(AccountSettings.this, "Logout Attempt Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // DO NOTHING
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        setComponents();
        setOnClickListeners();
    }

}
