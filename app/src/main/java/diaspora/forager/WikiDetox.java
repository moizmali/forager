package diaspora.forager;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by moiz on 10/11/17.
 */

public class WikiDetox {

    private static final Logger logger = Logger.getLogger(WikiDetox.class.getName());

    private static final String KEY = "wp_x2000_test";
    private static final String SERVER = "https://crowd9api-dot-wikidetox.appspot.com/client_jobs/";

    public static JSONArray getNext10UnansweredQuestions() {
        return get("/next10_unanswered_questions");
    }

    public static JSONArray getTrainingQuestions() {
        return get("/training_questions");
    }

    public static JSONArray getToAnswerQuestions() {
        return get("/to_answer_questions");
    }

    public static void postAnswer(JSONObject answer) {

    }

    private static JSONArray get(String request) {
        JSONArray result = null;
        try {
            String urlStr = SERVER + KEY + request;
            URL url = new URL(urlStr);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                String inputLine;
                StringBuffer stringBuffer = new StringBuffer();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuffer.append(inputLine);
                }
                bufferedReader.close();
                result = new JSONArray(stringBuffer.toString());
            }
        } catch (MalformedURLException e) {
            // TODO handle exceptions properly
            logger.log(Level.SEVERE, "MalformedURLException", e);
            FirebaseCrash.report(e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException", e);
            FirebaseCrash.report(e);
        } catch (JSONException e) {
            logger.log(Level.SEVERE, "JSONException", e);
            FirebaseCrash.report(e);
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Throwable", e);
            FirebaseCrash.report(e);
        }
        return result;
    }

}
