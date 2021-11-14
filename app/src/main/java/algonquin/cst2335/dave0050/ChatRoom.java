package algonquin.cst2335.dave0050;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ChatRoom extends AppCompatActivity {

    MyChatAdapter CA;
    RecyclerView chatList;
    ArrayList <ChatMessage> messages = new ArrayList<ChatMessage>();


    SQLiteDatabase db;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);


        EditText edit = findViewById(R.id.editText);
        Button send = findViewById(R.id.button2);
        Button receive = findViewById(R.id.button4);


        MyOpenHelper opener = new MyOpenHelper(this);
        db = opener.getWritableDatabase();

        send.setOnClickListener( click-> {

            ContentValues newRow = new ContentValues();
            ChatMessage CM;
            Date timeNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            CM = new ChatMessage(edit.getText().toString(), 1,
                    currentDateandTime);

            newRow.put(MyOpenHelper.col_message, CM.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, CM.getSendorReceive());
            newRow.put(MyOpenHelper.col_time_sent, CM.getTimesent());
            long newID = db.insert(MyOpenHelper.table_name, MyOpenHelper.col_message,newRow);

            CM.setID(newID);
            messages.add(CM);

            edit.setText("");

            CA.notifyItemInserted(messages.size()-1);


        });

        receive.setOnClickListener(click -> {

            ContentValues newRow = new ContentValues();
            Date timeNow = new Date();
            ChatMessage CM;
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);
            CM =new ChatMessage(edit.getText().toString(), 2, currentDateandTime);



            newRow.put(MyOpenHelper.col_message, CM.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, CM.getSendorReceive());
            newRow.put(MyOpenHelper.col_time_sent, CM.getTimesent());
            long newID = db.insert(MyOpenHelper.table_name, MyOpenHelper.col_message,newRow);

            CM.setID(newID);


            messages.add(CM);

            String test = edit.getText().toString();
            edit.setText("");

            CA.notifyItemInserted(messages.size()-1);

        });

        CA = new MyChatAdapter();

        Cursor results = db.rawQuery("Select * From " +MyOpenHelper.table_name +";",null);

        int _idcol = results.getColumnIndex(MyOpenHelper.col_id);
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);


        while(results.moveToNext())  {
            long id = results.getInt(_idcol);
            String message = results.getString((messageCol));
            String time = results.getString(timeCol);
            int sendorReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message,sendorReceive,time,id));
        }
        chatList.setAdapter(CA);
        chatList.setLayoutManager(new LinearLayoutManager(this));

    }

    private class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;

        public MyRowViews(View itemView) {
            super(itemView);

            itemView.setOnClickListener( click -> {
                int position = getAbsoluteAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder((ChatRoom.this));
                builder.setMessage("Do you want to delete the message:" + messageText.getText())
                .setTitle("Question")
                .setPositiveButton("Yes", (dialog, cl) -> {

                    ChatMessage removedMessage = messages.get(position);
                    messages.remove(position);
                    CA.notifyItemRemoved(position);

                    db.delete(MyOpenHelper.table_name, "_id=?", new String[] {
                            Long.toString((removedMessage.getID()))
                    });
                    Snackbar.make(messageText,"You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {


                                db.execSQL("Insert into " + MyOpenHelper.table_name +" values('" + removedMessage.getID() +
                                        "','" + removedMessage.getMessage() + "','" + removedMessage.getSendorReceive() +
                                        "','" + removedMessage.getTimesent() + "');");
                                messages.add(position,removedMessage);
                                CA.notifyItemInserted(position);
                            })
                            .show();
                })

                .setNegativeButton("No", (dialog,cl) -> {})
                .create().show();

            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);


        }
    }

    private class ChatMessage {

        String message;
        int sendorReceive;
        String timesent;
        long ID;

        public ChatMessage(String message, int sendorReceive, String timesent) {
            this.message = message;
            this.sendorReceive = sendorReceive;
            this.timesent = timesent;
        }

        public ChatMessage(String message, int sendorReceive, String time, long ID){
            this.message = message;
            this.sendorReceive = sendorReceive;
            this.timesent = time;
            this.ID = ID;

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

        public void setID(long newID) {
            ID = newID;

        }

        public long getID() {
            return ID;
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




