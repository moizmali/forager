package diaspora.forager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

/**
 * Created by Moiz Mansoor Ali on 12/10/2017.
 * TODO complete this helper class
 * This is used as a helper class to make it easier to append the current user's data in the cloud.
 */

public class CloudDatabaseHandler {

    // TODO handle errors
    // TODO test all these methods in the sign up class

    public CloudDatabaseHandler() {
    }

    public void writeNewUserToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        // TODO if google sign in, check whether user is a new one or not
        User user = new User(firebaseUser.getEmail(), 0, 0);
        databaseReference.child("users")
                .child(firebaseUser.getUid()).setValue(user);
    }

    public void updateNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser, String nickName) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .child("nickName").setValue(nickName);
    }

    public void addMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfMushrooms) {

    }

    public void addPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfPoints) {

    }

    public void subtractMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfMushrooms) {

    }

    public void subtractPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfPoints) {

    }

    public String getNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        final String[] result = new String[1];
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result[0] = dataSnapshot.getValue(User.class).getNickName();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNickName Method");
                        System.out.println("OnCancelled Method invoked when accessing the getNickName Method");
                    }
                });
        return result[0];
    }

    public int getNumberOfMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        final int[] result = new int[1];
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result[0] = dataSnapshot.getValue(User.class).getNumberOfMushrooms();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNumberOfMushrooms Method");
                        System.out.println("OnCancelled Method invoked when accessing the getNumberOfMushrooms Method");
                    }
                });
        return result[0];
    }

    public int getNumberOfPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        final int[] result = new int[1];
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result[0] = dataSnapshot.getValue(User.class).getNumberOfPoints();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNumberOfPoints Method");
                    }
                });
        return result[0];
    }

}
