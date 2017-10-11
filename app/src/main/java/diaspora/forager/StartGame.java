package diaspora.forager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartGame extends AppCompatActivity {

    private TextView question;

    private void setComponents() {
        question = (TextView) findViewById(R.id.question);
    }

    private void setQuestion() {
        String questionStr = WikiDetox.getNext10UnansweredQuestions().toString();
        question.setText(questionStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        setComponents();
        setQuestion();
    }
}
