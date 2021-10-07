package algonquin.cst2335.dave0050;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

        Button loginButton = findViewById(R.id.button);

        EditText ET = findViewById(R.id.ET);

        loginButton.setOnClickListener( clk -> {

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);

            nextPage.putExtra("Email Address",ET.getText().toString());
            startActivity(nextPage);

        });


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