package diaspora.forager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartGame extends AppCompatActivity {

    private static final String TAG = "StartGame";
    private static final String KEY = "wp_v2_x2000_test";
    private static final String SERVER = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/";

    private TextView question;
    private TextView counter;
    private Button skipButton;
    private Button submitButton;

    private RequestQueue queue;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private String uid;
    private String questionId;

    private RadioButton vToxic;
    private RadioButton sToxic;
    private RadioButton nToxic;
    private RadioButton vInsult;
    private RadioButton sInsult;
    private RadioButton nInsult;
    private RadioButton vObscene;
    private RadioButton sObscene;
    private RadioButton nObscene;
    private RadioButton vThreat;
    private RadioButton sThreat;
    private RadioButton nThreat;
    private RadioButton vIdentity;
    private RadioButton sIdentity;
    private RadioButton nIdentity;

    private CheckBox readable;
    private EditText comments;

    private void setComponents() {
        question = (TextView) findViewById(R.id.question);
        counter = (TextView) findViewById(R.id.counter);
        skipButton = (Button) findViewById(R.id.skipButton);
        submitButton = (Button) findViewById(R.id.Submit);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);
        //Initialise FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        uid = firebaseAuth.getCurrentUser().getUid();

        readable = (CheckBox) findViewById(R.id.legibleCheckBox);

        vToxic = findViewById(R.id.veryToxic);
        sToxic = findViewById(R.id.somewhatToxic);
        nToxic = findViewById(R.id.notToxic);
        vInsult = findViewById(R.id.veryInsult);
        sInsult = findViewById(R.id.somewhatInsult);
        nInsult = findViewById(R.id.notInsult);
        vObscene = findViewById(R.id.veryObscene);
        sObscene = findViewById(R.id.somewhatObscene);
        nObscene = findViewById(R.id.notObscene);
        vThreat = findViewById(R.id.veryThreat);
        sThreat = findViewById(R.id.somewhatThreat);
        nThreat = findViewById(R.id.notThreat);
        vIdentity = findViewById(R.id.veryHate);
        sIdentity = findViewById(R.id.somewhatHate);
        nIdentity = findViewById(R.id.notHate);

        comments = (EditText) findViewById(R.id.comments);
    }

    private void loadQuestion() {
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
                            questionId = questionObject.getString("question_id");
                        } catch (JSONException e) {
                            Log.e(TAG, "JSONException Occurred", e);
                            FirebaseCrash.report(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                question.setText("That didn't work!");
                Log.e(TAG, "VolleyError Occurred", error);
                FirebaseCrash.report(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private Map buildParams() {
        boolean toxic = false;
        boolean obscene = false;
        boolean insult = false;
        boolean identity = false;
        boolean threat = false;
        Map<String, String> params = new HashMap<String, String>();
        if (readable.isChecked()) {
            params.put("readableAndInEnglish", "Yes");
        } else {
            params.put("readableAndInEnglish", "No");
        }
        if (vToxic.isChecked()) {
            toxic = true;
            params.put("toxic", "Very");
        }
        if (sToxic.isChecked()) {
            toxic = true;
            params.put("toxic", "Somewhat");
        }
        if (nToxic.isChecked()) {
            toxic = true;
            params.put("toxic", "NotAtAll");
        }
        if (vObscene.isChecked()) {
            obscene = true;
            params.put("obscene", "Very");
        }
        if (sObscene.isChecked()) {
            obscene = true;
            params.put("obscene", "Somewhat");
        }
        if (nObscene.isChecked()) {
            obscene = true;
            params.put("obscene", "NotAtAll");
        }
        if (vIdentity.isChecked()) {
            identity = true;
            params.put("identityHate", "Very");
        }
        if (sIdentity.isChecked()) {
            identity = true;
            params.put("identityHate", "Somewhat");
        }
        if (nIdentity.isChecked()) {
            identity = true;
            params.put("identityHate", "NotAtAll");
        }
        if (vInsult.isChecked()) {
            insult = true;
            params.put("insult", "Very");
        }
        if (sInsult.isChecked()) {
            insult = true;
            params.put("insult", "Somewhat");
        }
        if (nInsult.isChecked()) {
            insult = true;
            params.put("insult", "NotAtAll");
        }
        if (vThreat.isChecked()) {
            threat = true;
            params.put("threat", "Very");
        }
        if (sThreat.isChecked()) {
            threat = true;
            params.put("threat", "Somewhat");
        }
        if (nThreat.isChecked()) {
            threat = true;
            params.put("threat", "NotAtAll");
        }
        if ((comments.getText() != null) || (!comments.getText().equals(""))) {
            params.put("comments", comments.getText().toString());
        }
        if (!toxic || !obscene || !insult || !identity || !threat) {
            new android.support.v7.app.AlertDialog.Builder(StartGame.this).setTitle("Missing details")
                    .setMessage("You've missed some critical fields. Please be more careful in future.")
                    .setCancelable(true)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    })
                    .show();
        }
        return params;
    }

    private void pushAnswer() {
        String url = SERVER + KEY + "/questions/" + questionId + "/answers/" + uid;

        Map<String, JSONObject> params = new HashMap<String, JSONObject>();

        params.put("answer", new JSONObject(buildParams()));
        JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", response.toString());
                        nextQuestion();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "VolleyError Occurred", error);
                        FirebaseCrash.log("VolleyError Occurred:" + error.toString());
                        nextQuestion();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(req);
    }

    private void nextQuestion() {
        readable.setChecked(true);

        vToxic.setChecked(false);
        sToxic.setChecked(false);
        nToxic.setChecked(false);
        vInsult.setChecked(false);
        sInsult.setChecked(false);
        nInsult.setChecked(false);
        vObscene.setChecked(false);
        sObscene.setChecked(false);
        nObscene.setChecked(false);
        vThreat.setChecked(false);
        sThreat.setChecked(false);
        nThreat.setChecked(false);
        vIdentity.setChecked(false);
        sIdentity.setChecked(false);
        nIdentity.setChecked(false);

        comments.setText("");
        //comments = (EditText) findViewById(R.id.comments);
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

    private void setOnClickListeners() {
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButton.setEnabled(false);
                pushAnswer();
                subtractFromDistanceRemaining(databaseReference, firebaseAuth.getCurrentUser());
                addMushroomToDatabase(databaseReference, firebaseAuth.getCurrentUser());
                addPointToDatabase(databaseReference, firebaseAuth.getCurrentUser());
                submitButton.setEnabled(true);
            }
        });
    }

    private void subtractFromDistanceRemaining(final DatabaseReference databaseReference, FirebaseUser firebaseUser) {
        databaseReference.child("global")
                .child("distanceRemaining")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Update the database
                        subtractFromDistanceRemaining(databaseReference, (dataSnapshot.getValue(Long.class).intValue() - 1));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Database Error Occurred", databaseError.toException());
                        FirebaseCrash.report(databaseError.toException());
                    }
                });
    }

    private void subtractFromDistanceRemaining(DatabaseReference databaseReference, int distanceRemaining) {
        databaseReference.child("global")
                .child("distanceRemaining").setValue(distanceRemaining);
    }

    private void addMushroomToDatabase(final DatabaseReference databaseReference, final FirebaseUser firebaseUser) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Update the cloud database
                        addMushroomToDatabase(databaseReference, firebaseUser, (dataSnapshot.getValue(User.class).getNumberOfMushrooms() + 1));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Database Error Occurred", databaseError.toException());
                        FirebaseCrash.report(databaseError.toException());
                    }
                });
    }

    private void addMushroomToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfMushrooms) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .child("numberOfMushrooms").setValue(numberOfMushrooms);
    }

    private void addPointToDatabase(final DatabaseReference databaseReference, final FirebaseUser firebaseUser) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Update the cloud database
                        addPointToDatabase(databaseReference, firebaseUser, (dataSnapshot.getValue(User.class).getNumberOfPoints() + 1));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Database Error Occurred", databaseError.toException());
                        FirebaseCrash.report(databaseError.toException());
                    }
                });
    }

    private void addPointToDatabase(DatabaseReference databaseReference, FirebaseUser firebaseUser, int numberOfPoints) {
        databaseReference.child("users")
                .child(firebaseUser.getUid())
                .child("numberOfPoints").setValue(numberOfPoints);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        setComponents();
        setOnClickListeners();
        loadQuestion();
    }
}
