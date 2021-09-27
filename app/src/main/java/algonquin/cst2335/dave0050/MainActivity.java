package algonquin.cst2335.dave0050;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView mytext = findViewById(R.id.textview);

        Button but = findViewById(R.id.mybutton);

        EditText myedit = findViewById(R.id.myedittext);

        String editString = myedit.toString();


        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mytext.setText("Your edit text has:" + editString);

            }
        });

        CheckBox cb = findViewById(R.id.CB);

        Switch sw = findViewById(R.id.Switch);

        RadioButton rd = findViewById(R.id.radio);


        cb.setOnCheckedChangeListener((b,c) -> {
            Toast.makeText(MainActivity.this, "You Clicked on Checkbox", Toast.LENGTH_LONG);

        });

        sw.setOnCheckedChangeListener(( b,c) -> {
            Toast.makeText(MainActivity.this, "You Clicked on switch", Toast.LENGTH_SHORT);

        });

        rd.setOnCheckedChangeListener(( b,c) -> {
            Toast.makeText(MainActivity.this, "You Clicked on radio", Toast.LENGTH_SHORT);

        });


    }
}
