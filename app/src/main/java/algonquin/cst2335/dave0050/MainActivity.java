package algonquin.cst2335.dave0050;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author Vinay Dave
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This holds the text at the center of the screen*/
    private TextView TV =  null;
    /** This holds the password field that is below the text field */
    private EditText cityText = null;
    /** This holds the login button at the bottome of the screen */
    private Button forecastbtn = null;

    private String stringURl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TV = findViewById(R.id.TV);
        cityText = findViewById(R.id.CityName);
        forecastbtn = findViewById(R.id.Forecast);

        forecastbtn.setOnClickListener(clk -> {

            //setup it up in the way the professor showed us!
            stringURl = "https://api.openweathermap.org/data/2.5/weather?q=TORONTO&appid=7e943c97096a9784391a981c4d878b22&Units=Metric";


            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                        URL url = null;
                        try {
                            url = new URL(stringURl);

                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        }
                        catch (IOException e) {
                            Log.e("Connection error:", e.getMessage());
                            //e.printStackTrace();
                        }
                    }
            );

            String read = cityText.getText().toString();

            if(checkPasswordComplexity(read)) {
                TV.setText("Your password meets the requirements");
            }
            else {
                TV.setText("You shall not pass!");
            };

        });

    }

    /**
     *
     * @param read The String that we are checking
     */

    private boolean checkPasswordComplexity(String read) {
    boolean foundUpperCase;
    boolean foundLowerCase;
    boolean foundNumber;
    boolean foundSpecial;

    int length = read.length();
    foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i=0; i<length; i++) {

            char character;
            character = read.charAt(i);

            if (isDigit(character)) {
                foundNumber = true;
                }


            if (isUpperCase(character)) {
                foundUpperCase = true;
                }


            if (isLowerCase(character)) {
                foundLowerCase = true;
                }

            if(isSpecialCharacter(character)) {
                foundSpecial = true;
            }

            };

        if(!foundUpperCase) {
            Toast.makeText(MainActivity.this, "You didn't input Uppercase",Toast.LENGTH_LONG).show();
            return false;
        }

        else if(!foundLowerCase) {
            Toast.makeText(MainActivity.this,"You didn't input Lowercase", Toast.LENGTH_LONG).show();
            return false;
        }

        else if(!foundNumber) {
            Toast.makeText(MainActivity.this,"You didn't input a Number", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!foundSpecial) {
            Toast.makeText(MainActivity.this, "You didn't input any Special Characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    /**
     *
     * @param character This represents the character position of the string text entered by the user
     * @return This function returns true if any special characters are dedicated in the char. It returns false if no special characters are detected.
     *
     */
    private boolean isSpecialCharacter(char character) {
        switch(character) {
            case'#':
            case'$':
            case'%':
            case'^':
            case'&':
            case'*':
            case'!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }



    }
}