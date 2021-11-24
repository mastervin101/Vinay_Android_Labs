package algonquin.cst2335.dave0050;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MessageDetailsFragment extends Fragment {

    MessageListFragment.ChatMessage chosenMessage;

    int ChosenPosition;

public MessageDetailsFragment(MessageListFragment.ChatMessage message, int position) {

    chosenMessage = message;
    ChosenPosition = position;

}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View detailsView = inflater.inflate(R.layout.details_layout, container,false);

        TextView messageView = detailsView.findViewById(R.id.messageView);
        TextView sendView = detailsView.findViewById(R.id.sendView);
        TextView timeView = detailsView.findViewById(R.id.TimeView);
        TextView idView = detailsView.findViewById(R.id.idView);


        messageView.setText("Message is: " + chosenMessage.getMessage());
        sendView.setText(("Send or Receive?" + chosenMessage.getSendorReceive()));
        timeView.setText("Time Send:" + chosenMessage.getTimesent());
        idView.setText("Database id is:" + chosenMessage.getID());


        Button closeButton = detailsView.findViewById(R.id.closeButton);

        closeButton.setOnClickListener( closeClicked -> {
                    getParentFragmentManager().beginTransaction().remove(this).commit();

        });



        Button deleteButton = detailsView.findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener( deleteClicked -> {

                ChatRoom parentActivity = (ChatRoom) getContext();
                parentActivity.notifyMessageDeleted(chosenMessage,ChosenPosition);

                getParentFragmentManager().beginTransaction().remove(this).commit();

        });




        return detailsView;
    }
}
