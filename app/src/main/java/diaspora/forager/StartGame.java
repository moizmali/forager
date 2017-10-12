package diaspora.forager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StartGame extends AppCompatActivity {

    private TextView question;
    private TextView counter;
    private Button skipButton;

    private int questionCurrent = 0;
    private static final String KEY = "wp_v2_x2000_test";
    private static final String SERVER = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/";

    private void setComponents() {
        question = (TextView) findViewById(R.id.question);
        counter = (TextView) findViewById(R.id.counter);
        skipButton = (Button) findViewById(R.id.skipButton);
    }

    private void loadQuestion() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = SERVER + KEY + "/next10_unanswered_questions";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int questionNo = Integer.parseInt(counter.getText().toString()) - 1;
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray responseObject = new JSONArray(response);
                            JSONObject questionObject = new JSONObject(responseObject.getString(questionNo));
                            JSONObject revisionObject = new JSONObject(questionObject.getString("question"));
                            String questiontoRate = revisionObject.getString("revision_text");
                            question.setText(questiontoRate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                question.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void setOnClick() {
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setText("Loading question...");
                int questionNo = Integer.parseInt(counter.getText().toString());
                questionNo++;
                if ((questionNo) > 10) {
                    counter.setText(Integer.toString(1));
                } else {
                    counter.setText(Integer.toString(questionNo));
                }
                loadQuestion();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        setComponents();
        setOnClick();
        loadQuestion();
    }
}