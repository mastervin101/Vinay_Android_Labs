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

        TextView mytext = findViewById(R.id.textview);

        Button but = findViewById(R.id.mybutton);

        EditText myedit = findViewById(R.id.myedittext);

        but.onclick(View v);

    }

}