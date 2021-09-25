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

        String editString = myedit.gettext().toString();


        but.setOnClickListener(new View.OnClickListener() {

            public void OnClick (View v) {
            mytext.setText("Your edit text has:" + editString);

            }
        }



    }
}