package mcruncher.com.eefmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private EditText expenses;
    private EditText expensesValue;
    private TextView inHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expenses = (EditText) findViewById(R.id.expenses);
        expensesValue = (EditText) findViewById(R.id.expensesValue);
        inHand = (TextView) findViewById(R.id.textView);
        executeTask();
    }

    private void executeTask() {
        new HttpTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=14OqR7bO30AaeZpjO6nMSQn2tcFsTOEOlhkTszAVl5Qc");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ViewActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postData(View view) {

        String fullUrl = "https://docs.google.com/forms/d/1jEJW-_QgZ3sEVOtV4HeLKlxjEyznWWYr7c-McW530UU/formResponse";
        HttpRequest mReq = new HttpRequest();
        String col1 = expenses.getText().toString();
        String col2 = expensesValue.getText().toString();

        String data = "entry.1538860216=" + URLEncoder.encode(col1) + "&" +
                "entry.1894035896=" + URLEncoder.encode(col2);
        String response = mReq.sendPost(fullUrl, data);
        String message;
        if (response.isEmpty()) {
            message = "No Connection";
        } else {
            message = "Sucessfully Added";
            expenses.setText("");
            expensesValue.setText("");
            executeTask();
        }
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    private void processJson(JSONObject object) {

        try {
            Log.e("Json", object.toString());
            JSONArray rows = object.getJSONArray("rows");

            JSONObject row = rows.getJSONObject(0);
            JSONArray columns = row.getJSONArray("c");
            int totalIncome = columns.getJSONObject(6).getInt("v");
            int totalExpense = columns.getJSONObject(7).getInt("v");
            int remaining = totalIncome - totalExpense;
            if(remaining > 0) {
                inHand.setTextColor(Color.GREEN);
            } else {
                inHand.setTextColor(Color.RED);
            }
            inHand.setText(String.valueOf(remaining));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
