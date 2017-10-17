package diaspora.forager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private static final String KEY = "wp_v2_x2000_2piyq";
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

    private RadioButton veryToxic;
    private RadioButton somewhatToxic;
    private RadioButton notToxic;
    private RadioButton veryInsult;
    private RadioButton somewhatInsult;
    private RadioButton notInsult;
    private RadioButton veryObscene;
    private RadioButton somewhatObscene;
    private RadioButton notObscene;
    private RadioButton veryThreat;
    private RadioButton somewhatThreat;
    private RadioButton notThreat;
    private RadioButton veryIdentityHate;
    private RadioButton somewhatIdentityHate;
    private RadioButton notIdentityHate;

    private RadioGroup toxic;
    private RadioGroup insult;
    private RadioGroup obscene;
    private RadioGroup threat;
    private RadioGroup identityHate;

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

        veryToxic = findViewById(R.id.veryToxic);
        somewhatToxic = findViewById(R.id.somewhatToxic);
        notToxic = findViewById(R.id.notToxic);
        veryInsult = findViewById(R.id.veryInsult);
        somewhatInsult = findViewById(R.id.somewhatInsult);
        notInsult = findViewById(R.id.notInsult);
        veryObscene = findViewById(R.id.veryObscene);
        somewhatObscene = findViewById(R.id.somewhatObscene);
        notObscene = findViewById(R.id.notObscene);
        veryThreat = findViewById(R.id.veryThreat);
        somewhatThreat = findViewById(R.id.somewhatThreat);
        notThreat = findViewById(R.id.notThreat);
        veryIdentityHate = findViewById(R.id.veryIdentityHate);
        somewhatIdentityHate = findViewById(R.id.somewhatIdentityHate);
        notIdentityHate = findViewById(R.id.notIdentityHate);

        toxic = findViewById(R.id.toxic);
        insult = findViewById(R.id.insult);
        obscene = findViewById(R.id.obscene);
        threat = findViewById(R.id.threat);
        identityHate = findViewById(R.id.hate);

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
        Map<String, String> params = new HashMap<String, String>();

        if (readable.isChecked()) {
            params.put("readableAndInEnglish", "Yes");
        } else {
            params.put("readableAndInEnglish", "No");
        }

        if (veryToxic.isChecked()) {
            params.put("toxic", "Very");
        } else if (somewhatToxic.isChecked()) {
            params.put("toxic", "Somewhat");
        } else if (notToxic.isChecked()) {
            params.put("toxic", "NotAtAll");
        }

        if (veryInsult.isChecked()) {
            params.put("insult", "Very");
        } else if (somewhatInsult.isChecked()) {
            params.put("insult", "Somewhat");
        } else if (notInsult.isChecked()) {
            params.put("insult", "NotAtAll");
        }

        if (veryObscene.isChecked()) {
            params.put("obscene", "Very");
        } else if (somewhatObscene.isChecked()) {
            params.put("obscene", "Somewhat");
        } else if (notObscene.isChecked()) {
            params.put("obscene", "NotAtAll");
        }

        if (veryThreat.isChecked()) {
            params.put("threat", "Very");
        } else if (somewhatThreat.isChecked()) {
            params.put("threat", "Somewhat");
        } else if (notThreat.isChecked()) {
            params.put("threat", "NotAtAll");
        }

        if (veryIdentityHate.isChecked()) {
            params.put("identityHate", "Very");
        } else if (somewhatIdentityHate.isChecked()) {
            params.put("identityHate", "Somewhat");
        } else if (notIdentityHate.isChecked()) {
            params.put("identityHate", "NotAtAll");
        }

        if (!comments.getText().toString().equals("")) {
            params.put("comments", comments.getText().toString());
        }

        return params;
    }

    private void pushAnswer() {
        String url = SERVER + KEY + "/questions/" + questionId + "/answers/" + uid;

        Map<String, JSONObject> params = new HashMap<String, JSONObject>();
        // TODO log the answer being added
        JSONObject answer = new JSONObject(buildParams());
        Log.i("Answer", answer.toString());
        params.put("answer", answer);
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
                        FirebaseCrash.report(error);
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

        toxic.clearCheck();
        obscene.clearCheck();
        insult.clearCheck();
        threat.clearCheck();
        identityHate.clearCheck();

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
                checkInputsAndSubmitAnswer();
                submitButton.setEnabled(true);
            }
        });
    }

    private void checkInputsAndSubmitAnswer() {
        boolean toxicIsChecked = (veryToxic.isChecked() || somewhatToxic.isChecked() || notToxic.isChecked());
        boolean insultIsChecked = (veryInsult.isChecked() || somewhatInsult.isChecked() || notInsult.isChecked());
        boolean obsceneIsChecked = (veryObscene.isChecked() || somewhatObscene.isChecked() || notObscene.isChecked());
        boolean threatIsChecked = (veryThreat.isChecked() || somewhatThreat.isChecked() || notThreat.isChecked());
        boolean identityHateIsChecked = (veryIdentityHate.isChecked() || somewhatIdentityHate.isChecked() || notIdentityHate.isChecked());

        if (toxicIsChecked && insultIsChecked && obsceneIsChecked && threatIsChecked && identityHateIsChecked) {
            pushAnswer();
            subtractFromDistanceRemaining(databaseReference, firebaseAuth.getCurrentUser());
            addMushroomToDatabase(databaseReference, firebaseAuth.getCurrentUser());
            addPointToDatabase(databaseReference, firebaseAuth.getCurrentUser());
        } else {
            new AlertDialog.Builder(StartGame.this).setTitle("Answer Incomplete")
                    .setMessage("You have missed one/more bullet points for your answer, please complete the answer before submitting")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // DO NOTHING
                        }
                    })
                    .show();
        }
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
