package diaspora.forager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Moiz Mansoor Ali on 12/10/2017.
 */

public class CloudDatabaseHandler {

    // TODO test all these methods

    private static final Logger logger = Logger.getLogger(CloudDatabaseHandler.class.getName());

    public CloudDatabaseHandler() {
    }

    private void writeNewUserToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            User user = new User(firebaseUser.getEmail(), 0, 0);
            databaseReference.child("users")
                    .child(firebaseUser.getUid()).setValue(user);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void updateNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser, String nickName) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .child("nickName").setValue(nickName);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void addMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser, final int numberOfMushrooms) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the cloud database
                            updateDatabase(dataSnapshot.getValue(User.class).getNumberOfMushrooms() + numberOfMushrooms);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void addPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser, final int numberOfPoints) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the cloud database
                            updateDatabase(dataSnapshot.getValue(User.class).getNumberOfPoints() + numberOfPoints);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void subtractMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser, final int numberOfMushrooms) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the cloud database
                            updateDatabase(dataSnapshot.getValue(User.class).getNumberOfMushrooms() - numberOfMushrooms);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void subtractPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser, final int numberOfPoints) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the cloud database
                            updateDatabase(dataSnapshot.getValue(User.class).getNumberOfPoints() - numberOfPoints);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void getNickName(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            updateUI(dataSnapshot.getValue(User.class).getNickName());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void getNumberOfMushrooms(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            updateUI(dataSnapshot.getValue(User.class).getNumberOfMushrooms());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void getNumberOfPoints(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            updateUI(dataSnapshot.getValue(User.class).getNumberOfPoints());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void getDistanceRemaining(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("global")
                    .child("distanceRemaining")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the android UI
                            updateUI(dataSnapshot.getValue(Global.class).getDistanceRemaining());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void subtractFromDistanceRemaining(DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        try {
            databaseReference.child("global")
                    .child("distanceRemaining")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Update the database
                            updateDatabase(dataSnapshot.getValue(Global.class).getDistanceRemaining() - 1);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            logger.log(Level.SEVERE, "Database Error Occurred", databaseError.toException());
                            FirebaseCrash.report(databaseError.toException());
                        }
                    });
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Unidentified Error Occurred", e);
            FirebaseCrash.report(e);
        }
    }

    private void updateUI(Object object) {
        // DO NOTHING, VIRTUAL METHOD
    }

    private void updateDatabase(Object object) {
        // DO NOTHING, VIRTUAL METHOD
    }

}
