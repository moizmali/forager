package diaspora.forager;

/**
 * Created by Moiz Mansoor Ali on 12/10/2017.
 */

public class User {

    private String nickName;
    private int mushrooms;
    private int points;

    public User(String nickName, int mushrooms, int points) {
        this.nickName = nickName;
        this.mushrooms = mushrooms;
        this.points = points;
    }

    public String getNickName() {
        return this.nickName;
    }

    public int getMushrooms() {
        return this.mushrooms;
    }

    public int getPoints() {
        return this.points;
    }

}
