package diaspora.forager;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Moiz Mansoor Ali on 13/10/2017.
 */

@IgnoreExtraProperties
public class Global {

    private int distanceRemaining;

    public Global() {
        // Default constructor required for calls to DataSnapshot.getValue(Global.class)
    }

    public int getDistanceRemaining() {
        return this.distanceRemaining;
    }

}
