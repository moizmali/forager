package diaspora.forager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    private Button leaderboard;
    private Button accessories;
    private Button community;
    private Button startGame;
    private Button accountSettings;

    private void setComponents() {
        leaderboard = (Button) findViewById(R.id.leaderboard);
        accessories = (Button) findViewById(R.id.accessories);
        community = (Button) findViewById(R.id.community);
        startGame = (Button) findViewById(R.id.startGame);
        accountSettings = (Button) findViewById(R.id.accountSettings);
    }

    private void setOnClickListeners() {
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Leaderboard.class));
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Accessories.class));
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Community.class));
            }
        });
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, StartGame.class));
            }
        });
        accountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AccountSettings.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setComponents();
        setOnClickListeners();
    }
}
