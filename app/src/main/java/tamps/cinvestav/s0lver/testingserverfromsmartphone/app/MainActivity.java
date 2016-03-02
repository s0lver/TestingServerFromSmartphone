package tamps.cinvestav.s0lver.testingserverfromsmartphone.app;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;
import backgroundengine.staypoints.StayPoint;
import tools.SmartphoneFixesFileReader;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private Button btnCreateTrajectory;
//    private Button btnProcessFix;
    private Button btnProcessFixes;
    private Button btnLoadFixes;

    private EditText txtGpsFixes;
    private EditText txtStayPoints;

    private ArrayList<RegistroUbicacion> gpsFixes;

    public ArrayList<RegistroUbicacion> getGpsFixes() {
        return gpsFixes;
    }

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignGuiReferences();


        this.presenter = new Presenter(this);
    }

    private void loadFixesFromFile() {
        gpsFixes = new ArrayList<RegistroUbicacion>();

        String storageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fullFilePath = storageDirectory + "/registros/test-registros.csv";
        Log.i(this.getClass().getSimpleName(), "Trying to read from " + fullFilePath);
        SmartphoneFixesFileReader reader = new SmartphoneFixesFileReader(fullFilePath);
        gpsFixes = reader.readFile();
        Toast.makeText(MainActivity.this, "Loaded " + gpsFixes.size() + " fixes", Toast.LENGTH_SHORT).show();
    }

    private void assignGuiReferences() {
        this.btnLoadFixes = (Button) findViewById(R.id.btnLoad);
        this.btnCreateTrajectory = (Button) findViewById(R.id.btnCreateTrajectoryInfo);
//        this.btnProcessFix = (Button) findViewById(R.id.btnProcessFix);
        this.btnProcessFixes = (Button) findViewById(R.id.btnProcessFixes);
        this.txtStayPoints = (EditText) findViewById(R.id.txtStayPointsFound);
        this.txtGpsFixes = (EditText) findViewById(R.id.txtGpsFixes);
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
        loadFixesFromFile();
        fillTxtGpsFixes();
    }

    private void fillTxtGpsFixes() {
        this.txtGpsFixes.getText().clear();
        for (RegistroUbicacion gpsFix : gpsFixes) {
            this.txtGpsFixes.append(gpsFix.toCSV());
            this.txtGpsFixes.append("\n");
        }
    }

    public void clickOnCreateTrajectory(View view) {
        presenter.createTrajectory();
    }

//    public void clickOnProcessFix(View view) {
//
//    }

    public void clickOnProcessFixes(View view) {
        presenter.processFixes();
    }

    public void updateStayPointsList(StayPoint stayPoint) {
        this.txtStayPoints.append(stayPoint.toCSV());
        this.txtStayPoints.append("\n");
    }
}
