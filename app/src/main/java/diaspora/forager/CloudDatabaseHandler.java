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

    public static void writeNewUserToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        // TODO if google sign in, check whether user is a new one or not
        User user = new User(firebaseUser.getEmail(), 0, 0);
        databaseReference.child("users")
                .child(firebaseUser.getUid()).setValue(user);
    }

    public static void updateNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser, String nickName) {
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

    public static String getNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        initNickName(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNickName Method");
                    }
                });
        System.out.println("The NickName is " + nickName);
        return nickName;
    }

    public static int getNumberOfMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        initNumberOfMushrooms(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNumberOfMushrooms Method");
                    }
                });
        System.out.println("The number of mushrooms is " + numberOfMushrooms);
        return numberOfMushrooms;
    }

    public static int getNumberOfPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        initNumberOfPoints(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        FirebaseCrash.log("OnCancelled Method invoked when accessing the getNumberOfPoints Method");
                    }
                });
        System.out.println("The number of points is " + numberOfPoints);
        return numberOfPoints;
    }

    private static String nickName = "Hello";
    private static void initNickName(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        nickName = user.getNickName();
    }

    private static int numberOfMushrooms = 5;
    private static void initNumberOfMushrooms(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        numberOfMushrooms = user.getNumberOfMushrooms();
    }

    private static int numberOfPoints = 5;
    private static void initNumberOfPoints(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        numberOfPoints = user.getNumberOfPoints();
    }

}
