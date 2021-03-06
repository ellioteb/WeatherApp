package elliot_boileau.myweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView placeTextView;
    static TextView tempTextView;
     TextView lonTextView;
     TextView latTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("myLog1", "reached execute1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        placeTextView = (TextView) findViewById(R.id.nameTextView);
        tempTextView = (TextView) findViewById(R.id.tempTextView);

        Log.i("myLog1", "reached execute2");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(new Criteria(), false);
        Log.i("myLog1", "reached execute3");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        Log.i("myLog1", "reached execute3.1");

        if (ActivityCompat.checkSelfPermission
                (this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission
                (this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        Log.i("myLogProvider",provider);
        Log.i("myLog1","reached execute4");

        Double lat = location.getLatitude();

        Double lon = location.getLongitude();
        Log.i("myLog LatLon",lat+""+lon);
        latTextView = (TextView) findViewById(R.id.latTextView);
        lonTextView = (TextView)  findViewById(R.id.lonTextView);
        latTextView.setText("lat"+lat);
        lonTextView.setText("Lon"+lon);
        dataDownload task = new dataDownload();

        task.execute("http://api.openweathermap.org/data/2.5/weather?lat={"
                + String.valueOf(lat) + "}&lon={" + String.valueOf(lon) + "}&appid=9c3f570f69afa7651fa40846233996cd\n");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
