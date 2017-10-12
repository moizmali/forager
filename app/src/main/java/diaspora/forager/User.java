package diaspora.forager;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Moiz Mansoor Ali on 12/10/2017.
 */

@IgnoreExtraProperties
public class User {

    private String nickName;
    private int numberOfMushrooms;
    private int numberOfPoints;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String nickName, int numberOfMushrooms, int numberOfPoints) {
        this.nickName = nickName;
        this.numberOfMushrooms = numberOfMushrooms;
        this.numberOfPoints = numberOfPoints;
    }

    public String getNickName() {
        return this.nickName;
    }

    public int getNumberOfMushrooms() {
        return this.numberOfMushrooms;
    }

    public int getNumberOfPoints() {
        return this.numberOfPoints;
    }

}
