package algonquin.cst2335.dave0050;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView TV = findViewById(R.id.text);
        EditText ET = findViewById(R.id.typePW);
        Button btn = findViewById(R.id.login);



    }
}