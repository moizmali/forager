package diaspora.forager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private static final String KEY = "wp_x2000_test";
    private static final String SERVER = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/";

    private void setComponents() {
        question = (TextView) findViewById(R.id.question);
    }

    private void setQuestion() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = SERVER + KEY + "/training_questions";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray responseObject = new JSONArray(response);
                            JSONObject questionObject = new JSONObject(responseObject.getString(0));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        setComponents();
        setQuestion();
    }
}
