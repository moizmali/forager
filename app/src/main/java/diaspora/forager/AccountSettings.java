package diaspora.forager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountSettings extends AppCompatActivity {

    private static final Logger logger = Logger.getLogger(AccountSettings.class.getName());

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private EditText nickName;
    private Button changeNickName;
    private TextView mushroomsValue;
    private TextView pointsValue;
    private Button logout;

    private void setComponents() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        nickName = (EditText) findViewById(R.id.nickName);
        changeNickName = (Button) findViewById(R.id.changeNickName);
        mushroomsValue = (TextView) findViewById(R.id.mushroomsValue);
        pointsValue = (TextView) findViewById(R.id.pointsValue);
        logout = (Button) findViewById(R.id.logout);
    }

    private void setOnClickListeners() {
        changeNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AccountSettings.this).setTitle("Change NickName")
                        .setMessage("Are you sure you would like to change your nick name to " + nickName.getText().toString())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateNickNameToDatabase(databaseReference, firebaseAuth.getCurrentUser(), nickName.getText().toString());
                                Toast.makeText(AccountSettings.this, "NickName has been updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AccountSettings.this, MainMenu.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // DO NOTHING
                            }
                        })
                        .show();
            }
        });
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

    private void setCompleteUI() {
        setNumberOfMushroomsToUI(databaseReference, firebaseAuth.getCurrentUser());
        setNumberOfPointsToUI(databaseReference, firebaseAuth.getCurrentUser());
        setNickNameToUI(databaseReference, firebaseAuth.getCurrentUser());
    }

    private void setNickNameToUI(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            setNickNameToUI(dataSnapshot.getValue(User.class).getNickName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("DatabaseError", databaseError.toString());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void setNickNameToUI(String nickName) {
        this.nickName.setText(nickName);
    }

    private void setNumberOfMushroomsToUI(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            setNumberOfMushroomsToUI(dataSnapshot.getValue(User.class).getNumberOfMushrooms());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("DatabaseError", databaseError.toString());
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void setNumberOfMushroomsToUI(int numberOfMushrooms) {
        mushroomsValue.setText(Integer.toString(numberOfMushrooms));
    }

    private void setNumberOfPointsToUI(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            setNumberOfPointsToUI(dataSnapshot.getValue(User.class).getNumberOfPoints());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("DatabaseError", databaseError.toString());
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void setNumberOfPointsToUI(int numberOfPoints) {
        pointsValue.setText(Integer.toString(numberOfPoints));
    }

    private void updateNickNameToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser, String nickName) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .child("nickName").setValue(nickName);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        setComponents();
        setOnClickListeners();
        setCompleteUI();
    }

}
