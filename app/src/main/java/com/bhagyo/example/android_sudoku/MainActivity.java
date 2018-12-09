package com.bhagyo.example.android_sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bhagyo.example.android_sudoku.R;
import java.util.Locale;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private boolean currentEnglish = true;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkCurrentLocale();
    }
    protected void onStart()
    {
        super.onStart();
        Toast.makeText(this, "Created by Bhagyo", Toast.LENGTH_SHORT).show();
    }
    private void checkCurrentLocale() {
        Log.i(TAG, "Checking current locale");
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
        String currentLanguage = sharedPreferences.getString("app_language", null);
        Configuration configuration = new Configuration();
        Resources resources = getBaseContext().getResources();
        Locale locale = getResources().getConfiguration().locale;

        if (currentLanguage == null) {
            Log.i(TAG, "There is no shared Preferences... Creating...");
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (locale.getLanguage().equals("no") || locale.getLanguage().equals("nb") || locale.getLanguage().equals("nn")) {
                //bangla is selected
                Log.i(TAG, "Bangla is selected");
                editor.putString("app_language", "no");
                editor.apply();
                configuration.locale = new Locale("no", "NO");
                currentEnglish = false;
            } else {
                //English is selected
                Log.i(TAG, "English is selected");
                editor.putString("app_language", "en");
                editor.apply();
                configuration.locale = new Locale("en", "US");
                currentEnglish = true;
            }
        } else {
            if (currentLanguage.equals("no")) {
                Log.i(TAG, "Bangla is selected");
                configuration.locale = new Locale("no", "NO");
                currentEnglish = false;

            } else {
                //currentLanguage == "en"
                Log.i(TAG, "English is selected");
                configuration.locale = new Locale("en", "US");
                currentEnglish = true;
            }
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        refreshViewLanguages();
    }

    private void refreshViewLanguages() {
        Log.i(TAG, "Refreshing View Languages");
        Button buttonStartNewGame = findViewById(R.id.buttonStartNewGame);
        buttonStartNewGame.setText(R.string.new_game);
        Button buttonAddNewBoard = findViewById(R.id.buttonAddNewBoard);
        buttonAddNewBoard.setText(R.string.add_new_board);
        Button buttonShowInstructions = findViewById(R.id.buttonShowInstructions);
        buttonShowInstructions.setText(R.string.show_instructions);

        TextView textViewChooseLanguage = findViewById(R.id.textViewChooseLanguage);
        textViewChooseLanguage.setText(R.string.choose_language);
    }
    public void onStartNewGameButtonClicked(View view) {
        Intent intent = new Intent("com.bhagyo.example.GameDifficultyActivity"); //GameDifficulty
        startActivity(intent);
    }
    public void onAddNewBoardButtonClicked(View view) {
        Intent intent = new Intent("com.bhagyo.example.NewBoardActivity"); //
        startActivity(intent);
    }
    public void onShowInstructionsButtonClicked(View view) {
        Intent intent = new Intent("com.bhagyo.example.InstructionsActivity"); ///
        startActivity(intent);
    }
    public void onBangladeshFlagClick(View view) {
        if (currentEnglish) {
            Log.i(TAG, "Bangla is now selected");
            SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Resources resources = getBaseContext().getResources();
            Configuration configuration = new Configuration();
            editor.putString("app_language", "no");
            editor.apply();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());

            //Restart activity to refresh locale
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "Bangla is selected", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "Bangla is already selected");
            Toast.makeText(this, "Bangla is already selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBritishFlagClick(View view) {
        if (!currentEnglish) {
            Log.i(TAG, "English is now selected");
            SharedPreferences sharedPreferences = getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Resources resources = getBaseContext().getResources();
            Configuration configuration = new Configuration();
            editor.putString("app_language", "en");
            editor.apply();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "English is now selected", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "English is already selected");
            Toast.makeText(this, "English is already selected", Toast.LENGTH_SHORT).show();
        }
    }
}
