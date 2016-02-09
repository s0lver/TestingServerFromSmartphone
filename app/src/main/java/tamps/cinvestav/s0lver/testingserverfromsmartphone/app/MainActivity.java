package tamps.cinvestav.s0lver.testingserverfromsmartphone.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    private Button btnCreateTrajectory;
    private Button btnProcessFix;
    private Button btnProcessFixes;
    private Button btnLoadFixes;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignGuiReferences();

        this.presenter = new Presenter(this);
    }

    private void assignGuiReferences() {
        this.btnLoadFixes = (Button) findViewById(R.id.btnLoad);
        this.btnCreateTrajectory = (Button) findViewById(R.id.btnCreateTrajectoryInfo);
        this.btnProcessFix = (Button) findViewById(R.id.btnProcessFix);
        this.btnProcessFixes = (Button) findViewById(R.id.btnProcessFixes);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickOnLoadFixes(View view) {

    }

    public void clickOnCreateTrajectory(View view) {
        presenter.createTrajectory();
    }

    public void clickOnProcessFix(View view) {

    }

    public void clickOnProcessFixes(View view) {

    }
}
