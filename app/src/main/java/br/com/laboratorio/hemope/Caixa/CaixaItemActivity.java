package br.com.laboratorio.hemope.Caixa;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import br.com.laboratorio.hemope.R;

public class CaixaItemActivity extends ActionBarActivity {

    TableLayout table_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa_item);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        table_layout.removeAllViews();
        BuildTable(2, 2);
    }

    private void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.alocar);
                tv.setPadding(5, 5, 5, 5);
                tv.setText("R " + i + ", C" + j);

                row.addView(tv);

            }

            table_layout.addView(row);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caixa_item, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_caixa_item, container, false);
            return rootView;
        }
    }
}
