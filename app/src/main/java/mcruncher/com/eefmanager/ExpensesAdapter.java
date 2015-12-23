package mcruncher.com.eefmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vignesh on 22/12/2015.
 */
public class ExpensesAdapter extends ArrayAdapter<Expenses> {

    Context context;
    private ArrayList<Expenses> teams;

    public ExpensesAdapter(Context context, int textViewResourceId, ArrayList<Expenses> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.teams = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.expenses, null);
        }
        Expenses o = teams.get(position);
        if (o != null) {
            TextView pos = (TextView) v.findViewById(R.id.dateTime);
            TextView name = (TextView) v.findViewById(R.id.expense);
            TextView wins = (TextView) v.findViewById(R.id.expenseValue);

            pos.setText(String.valueOf(o.getDateTime()));
            name.setText(String.valueOf(o.getExpenseName()));
            wins.setText(String.valueOf(o.getValue()));
        }
        return v;
    }
}
