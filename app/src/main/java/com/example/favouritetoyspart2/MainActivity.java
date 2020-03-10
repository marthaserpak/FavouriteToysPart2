package com.example.favouritetoyspart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Gets the data from the ContentProvider and shows a series of flash cards.
 */
public class MainActivity extends AppCompatActivity {

    private int mCurrentState;
    private Cursor mData;
    private Button mButton;

    // This state is when the word definition is hidden and clicking
    // the button will therefore show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the
    // button will therefore advance the app to the next word
    private final int STATE_SHOWN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button_next);

        new WordFetchTask().execute();
    }

    /**
     * This is called from the layout when the button is clicked and switches
     * between the two app states.
     *
     * @param view The view that was clicked
     */
    public void onButtonClick(View view) {

        // Either show the definition of the current word, or if the definition is
        // currently showing, move to the next word.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {

        // Change button text
        mButton.setText(getString(R.string.show_definition));

        mCurrentState = STATE_HIDDEN;

    }

    public void showDefinition() {

        // Change button text
        mButton.setText(getString(R.string.next_word));

        mCurrentState = STATE_SHOWN;

    }

    public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        //Invoked on a background thread
        @Override
        protected Cursor doInBackground(Void... voids) {
            //Make the query to get the data

            //Get the content resolver
            ContentResolver resolver = getContentResolver();

            //Call the query method on the resolver with the
            //correct Uri from the contract class
            Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }

        // Invoked on UI thread
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Set the data for MainActivity
            mData = cursor;
        }


    }

}
