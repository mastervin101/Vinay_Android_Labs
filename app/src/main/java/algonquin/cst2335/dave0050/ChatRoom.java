package algonquin.cst2335.dave0050;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;


public class ChatRoom extends AppCompatActivity {

    RecyclerView chatList;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter());

    }

    private class ChatMessage {

        String message;
        int sendorReceive;
        Date timesent;
    }

    private class MyChatAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }



    }




