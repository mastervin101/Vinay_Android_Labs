package algonquin.cst2335.dave0050;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ChatRoom extends AppCompatActivity {

    MyChatAdapter CA;
    RecyclerView chatList;
    ArrayList <ChatMessage> messages = new ArrayList<ChatMessage>();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);


        EditText edit = findViewById(R.id.editText);
        Button send = findViewById(R.id.button2);
        Button receive = findViewById(R.id.button4);


        send.setOnClickListener( click-> {

            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);
            messages.add(new ChatMessage(edit.getText().toString(), 1,
                    currentDateandTime));

            edit.setText("");

            CA.notifyItemInserted(messages.size()-1);


        });

        receive.setOnClickListener(click -> {

            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);
            messages.add(new ChatMessage(edit.getText().toString(), 2,
                    currentDateandTime));

            String test = edit.getText().toString();
            edit.setText("");

            CA.notifyItemInserted(messages.size()-1);

        });

        CA = new MyChatAdapter();
        chatList.setAdapter(CA);
        chatList.setLayoutManager(new LinearLayoutManager(this));

    }

    private class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowViews(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);


        }
    }

    private class ChatMessage {

        String message;
        int sendorReceive;
        String timesent;

        public ChatMessage(String message, int sendorReceive, String timesent) {
            this.message = message;
            this.sendorReceive = sendorReceive;
            this.timesent = timesent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendorReceive() {
            return sendorReceive;
        }

        public String getTimesent() {
            return timesent;
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;
            if(viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;

            View loadedRow = inflater.inflate(layoutID, parent, false);

            return new MyRowViews(loadedRow);
        }


        @Override
        public void onBindViewHolder( MyRowViews holder, int position) {
            ChatMessage thisRow = messages.get(position);
            holder.messageText.setText(thisRow.getMessage());
            holder.timeText.setText(thisRow.getTimesent());

        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public int getItemViewType(int position) {
            return messages.get(position).getSendorReceive();
        }
    }



    }




