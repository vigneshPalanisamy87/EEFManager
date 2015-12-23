package mcruncher.com.eefmanager;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ViewActivity extends AppCompatActivity {

    ArrayList<Expenses> expenses = new ArrayList<Expenses>();
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listview = (ListView) findViewById(R.id.listview);
        new HttpTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=14OqR7bO30AaeZpjO6nMSQn2tcFsTOEOlhkTszAVl5Qc");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void processJson(JSONObject object) {

        try {
            Log.e("Json", object.toString());
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy H:mm:ss");
                Date date = (Date) dateFormat.parse(columns.getJSONObject(0).getString("f"));
                SimpleDateFormat displayDateFormat = new SimpleDateFormat("M/d/yyyy");
                String dateTime = displayDateFormat.format(date);
                String name = columns.getJSONObject(1).getString("v");
                int value = columns.getJSONObject(2).getInt("v");
                Expenses expense = new Expenses(dateTime, name, value);
                expenses.add(expense);
            }

            final ExpensesAdapter adapter = new ExpensesAdapter(this, R.layout.expenses, expenses);
            listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewActivity.this, MainActivity.class));
        this.finish();
    }

}
