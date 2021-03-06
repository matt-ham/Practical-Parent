package ca.cmpt276.parentapp.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ca.cmpt276.as3.parentapp.databinding.ActivityHistoryFlipViewBinding;
import ca.cmpt276.as3.parentapp.R;
import ca.cmpt276.parentapp.model.FlipResult;
import ca.cmpt276.parentapp.model.FlipResultManager;

/**
 * HistoryFlipView class populates the
 * listview which contains the history of
 * flipped coins. Information shown includes
 * child name, chosen face, correct face and time.
 */
public class HistoryFlipView extends AppCompatActivity {

    private List<FlipResult> myFlips = new ArrayList<>();
    private FlipResultManager resultManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        resultManager = FlipResultManager.getInstance();
        ca.cmpt276.as3.parentapp.databinding.ActivityHistoryFlipViewBinding binding = ActivityHistoryFlipViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor(getString(R.string.yellow_brown_color)));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        populateListView();
    }

    public void getFlipList() {
        myFlips = resultManager.getFlipHistoryList();
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, HistoryFlipView.class);
    }

    private void populateListView() {
        getFlipList();
        ArrayAdapter<FlipResult> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.flipsListView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class MyListAdapter extends ArrayAdapter<FlipResult> {
        public MyListAdapter() {
            super(HistoryFlipView.this, R.layout.item_view, myFlips);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            FlipResult currentResult = resultManager.getFlipHistoryList().get(position);

            TextView childName = itemView.findViewById(R.id.tvChildName);
            TextView timeFlip = itemView.findViewById(R.id.tvFlipTime);
            TextView flipResult = itemView.findViewById(R.id.tvChildResult);
            TextView childChoice = itemView.findViewById(R.id.tvChildChoice);
            ImageView resultImage = itemView.findViewById(R.id.resultImage);

            childName.setText(currentResult.getNameOfChild() + getString(R.string.picked));
            childChoice.setText(currentResult.getChildChoiceString());
            timeFlip.setText(currentResult.getTimeString());
            flipResult.setText(currentResult.getFlippedResultString());
            resultImage.setImageResource(currentResult.getIcon());

            return itemView;
        }
    }
}
