package algonquin.cst2335.dave0050;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("Email Address");

        TextView TV = findViewById(R.id.EM);

        TV.setText("Welcome Home" + emailAddress);

        Button Call = findViewById(R.id.Call);

        EditText phone =findViewById(R.id.PN);

        String phoneNumber = phone.getText().toString();

        Call.setOnClickListener( clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:"+ phoneNumber));
            startActivity(call);


        });

        ImageView camera = findViewById(R.id.imageView);

        camera.setOnClickListener( clk -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(

                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                camera.setImageBitmap(thumbnail);
                        }
                    }
                    }

            );
            cameraResult.launch(cameraIntent);
        });


    }
}