package algonquin.cst2335.dave0050;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


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

    String serverURL ="https://api.openweathermap.org/data/2.5/weather?q=%s&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

    Bitmap image = null;

    //declare values to store weather API info outside of lambda functions
    String currentTemp = null;
    String minTemp = null;
    String maxTemp = null;
    String description = null;
    String humidity = null;
    String iconName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TV = findViewById(R.id.TV);
        cityText = findViewById(R.id.CityName);
        forecastbtn = findViewById(R.id.Forecast);

        forecastbtn.setOnClickListener(clk -> {

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting your weather forecast")
                    .setMessage("We're calling people in " + cityText.getText().toString() + " "+ "to look outside their windows")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();



            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                        URL url = null;
                        try {
                            String cityTextString = cityText.getText().toString();
                            String fullURl = String.format(serverURL, URLEncoder.encode(cityTextString));
                            url = new URL(fullURl);

                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(false);
                            XmlPullParser app = factory.newPullParser();
                            app.setInput(in,"UTF-8");




                            while(app.next() != XmlPullParser.END_DOCUMENT)
                            {
                                switch(app.getEventType())
                                {
                                    case XmlPullParser.START_TAG:
                                        if(app.getName().equals("temperature"))
                                        {
                                            currentTemp = app.getAttributeValue(null,"value");
                                            minTemp = app.getAttributeValue(null,"min");
                                            maxTemp = app.getAttributeValue(null,"max");
                                        }
                                        else if(app.getName().equals("weather"))
                                        {

                                            description = app.getAttributeValue(null,"value");
                                            iconName = app.getAttributeValue(null,"icon");

                                        }
                                        else if(app.getName().equals("humidity"))
                                        {
                                            humidity = app.getAttributeValue(null,"value");
                                    }
                                        break;

                                    case XmlPullParser.END_TAG:
                                        break;

                                    case XmlPullParser.TEXT:
                                        break;
                                }
                            }

                            //store weather icon image for later but check if it exists first

                            File file = new File(getFilesDir(), iconName +".png");
                            if(file.exists()) {
                                image = BitmapFactory.decodeFile(getFilesDir() +"/"+iconName+ ".png");

                            }

                            else {
                                URL imgURl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                                HttpURLConnection imgConnection = (HttpURLConnection) imgURl.openConnection();
                                imgConnection.connect();

                                int responseCode = imgConnection.getResponseCode();
                                if (responseCode == 200) {
                                    image = BitmapFactory.decodeStream(imgConnection.getInputStream());


                                }
                            }

                            //save image for later
                            FileOutputStream fOut = null;
                            try {
                                fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }



                            //set the values in TextView

                            Bitmap finalImage = image;
                            runOnUiThread(() -> {
                                TextView TV = findViewById(R.id.temp);
                                TV.setText("The current temperature is:" + " "+ currentTemp +" " +"degrees Celsius");
                                TV.setVisibility(View.VISIBLE);

                                TV = findViewById(R.id.maxTemp);
                                TV.setText("The max temperature is" +" "+maxTemp+" " +"degrees Celsius");
                                TV.setVisibility(View.VISIBLE);

                                TV = findViewById(R.id.minTemp);
                                TV.setText("The min temperature is" +" "+ minTemp+" " +"degrees Celsius");
                                TV.setVisibility(View.VISIBLE);

                                ImageView iv = findViewById(R.id.iconWeather);
                                iv.setImageBitmap(finalImage);
                                iv.setVisibility(View.VISIBLE);

                                TV.findViewById(R.id.humidity);
                                TV.setText("The current humidity is" + " "+humidity+"%");
                                TV.setVisibility(View.VISIBLE);

                                dialog.hide();
                            });
                        }
                        catch (IOException  | XmlPullParserException e) {
                            Log.e("Connection error:", e.getMessage());
                            //e.printStackTrace();
                        }
                    }
            );

            String read = cityText.getText().toString();



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