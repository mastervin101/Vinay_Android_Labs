package algonquin.cst2335.dave0050;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("Main Activity", "In onCreate() - Loading Widgets");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("Main Activity", "In onStop() - This application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("Main Activity", "In onDestroy()- Any memory used by the application is freed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("Main Activity", "In onPause() - This application no longer responds to user input");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("Main Activity", "In onResume() - The application is now responding to user input");
    }
}