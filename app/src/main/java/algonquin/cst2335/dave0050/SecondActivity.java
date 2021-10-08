package algonquin.cst2335.dave0050;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    ImageView camera = findViewById(R.id.imageView);

    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(

            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap thumbnail = data.getParcelableExtra("data");
                        camera.setImageBitmap(thumbnail);

                        try {
                            FileOutputStream File = openFileOutput("Profile.png", Context.MODE_PRIVATE);

                            thumbnail.compress(Bitmap.CompressFormat.PNG,100,File);

                            File.flush();
                            File.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("Email_Address");

        TextView TV = findViewById(R.id.EM);

        TV.setText("Welcome Home" + emailAddress);

        Button Call = findViewById(R.id.Call);

        EditText phone =findViewById(R.id.PN);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String StoredNumber = prefs.getString("PN","");

        phone.setText(StoredNumber);

        String phoneNumber = phone.getText().toString();

        Call.setOnClickListener( clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:"+ phoneNumber));
            startActivity(call);


        });

        Button CP =findViewById(R.id.button3);
        CP.setOnClickListener( clk -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraResult.launch(cameraIntent);



        });


    }

    protected void onPause() {
        super.onPause();

        EditText phone =findViewById(R.id.PN);
        String phoneNumber = phone.getText().toString();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor Editor = prefs.edit();
        Editor.putString("PN",phoneNumber);
        Editor.apply();


    }
}