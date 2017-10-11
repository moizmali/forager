package diaspora.forager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Moiz Mansoor Ali on 12/10/2017.
 * This is used as a helper class to make it easier to append the current user's data in the cloud.
 *  TODO complete this helper class
 */

public class CloudDatabase {

    private static DatabaseReference databaseReference;

    public CloudDatabase() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public static void writeNewUser(FirebaseUser firebaseUser) {
        // TODO handle errors
        // TODO if google sign in, check whether user is a new one or not
        String userId = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        User user = new User(email, 0, 0);
        databaseReference.child("users").child(userId).setValue(user);
    }

    public void updateNickNameTo(String nickName) {

    }

    public void addMushroomForUser(FirebaseUser firebaseUser) {

    }

    public void addPointForUser(FirebaseUser firebaseUser) {

    }

    public void addMushroomAndPointForUser(FirebaseUser firebaseUser) {

    }

    public void getNickName() {

    }

    public int getNumberOfMushrooms() {
        return 0;
    }

    public int getNumberOfPoints() {
        return 0;
    }

}
