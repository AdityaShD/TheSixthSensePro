package com.example.hackbpitpro;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.DefaultSuggestionsAdapter;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class SAM extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private FancyButton btnSpeakout,btnsaver,btnloader;
    private EditText editText;
    private ImageView imageView;
    private TextToSpeech tts;
    String keyText,valueText;
    int result,clicker=2;
    boolean saveToggler;
    HashMap<String,String> hashmapper=new HashMap<>();
    private MaterialSearchBar materialSearchBar;

    ArrayList<String> searchResults;
    SuggestionsAdapter suggestionsAdapter;
    LayoutInflater layoutInflater;

     TextView navHadLeft,navVisionRight;
     ImageView navImagLeft,navImagRight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sam);
        editText=findViewById(R.id.editText);
        imageView=findViewById(R.id.imageView);
        btnloader=findViewById(R.id.fancyButton);
        btnSpeakout=findViewById(R.id.fancyButton4);
        btnsaver=findViewById(R.id.fancyButton6);
        materialSearchBar=findViewById(R.id.searchBar);
        navHadLeft=findViewById(R.id.navHADText);
        navVisionRight=findViewById(R.id.navVISIONText);
        navImagLeft=findViewById(R.id.navImageLeftSamAct);
        navImagRight=findViewById(R.id.navImageRightVisionAct);
        saveToggler=false;
        searchResults=new ArrayList<>();
        layoutInflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        suggestionsAdapter=new DefaultSuggestionsAdapter(layoutInflater);


        tts=new TextToSpeech(this,this);
        btnSpeakout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakout();
            }
        });

        btnsaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveprompt();
            }
        });

        btnloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadprompt();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        materialSearchBar.setSuggestionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {

                materialSearchBar.setText(searchResults.get(position));
                editText.setText(searchResults.get(position));

            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });

        materialSearchBar.setHint("Search for your keyword here");
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //  MainActivity.this.suggestionsAdapter.getFilter().filter(s);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       navHadLeft.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {


               v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
               tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

               if(tts.isSpeaking()) {
                   Intent intent = new Intent(SAM.this, HearingAssist.class);
                   startActivity(intent);
                   finish();
               }

               return false;
           }
       });

        navImagLeft.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

                if(tts.isSpeaking()) {
                    Intent intent = new Intent(SAM.this, HearingAssist.class);
                    startActivity(intent);
                    finish();
                }

                return false;
            }
        });


      navImagRight.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {

              v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

              if(tts.isSpeaking()) {
                  Intent intent = new Intent(SAM.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }



              return false;
          }
      });

      navVisionRight.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

              if(tts.isSpeaking()) {
                  Intent intent = new Intent(SAM.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }

              return false;
          }
      });


    }

    private void speakout() {

        valueText = editText.getText().toString();
        tts.setPitch((float) 1);
        tts.speak(valueText, TextToSpeech.QUEUE_FLUSH, null);

    }
    private void loadprompt() {

        if (editText.getText().toString() == null) {

            Toast.makeText(this, "No keyname mentioned", Toast.LENGTH_SHORT).show();

        }

        String toBeLoaded = editText.getText().toString();

        if (!hashmapper.isEmpty())
        {
            for (Map.Entry me : hashmapper.entrySet()) {

                if (me.getKey().equals(toBeLoaded)) {
                    editText.setText((String) me.getValue());
                    break;
                }

            }
        }

    }
    private void saveprompt() {

        clicker--;
        if(saveToggler==false&&clicker%2!=0) {
            tts.speak("By which name you want to save the text", TextToSpeech.QUEUE_FLUSH, null);
            valueText = editText.getText().toString();
            Toast.makeText(this,"By which name you want to save the text?",Toast.LENGTH_SHORT).show();
            editText.setText("");
            saveToggler = true;
            clicker++;
        }

        if(saveToggler==true&&clicker%2!=0) {
            keyText = editText.getText().toString();

            editText.setText("");

            hashmapper.put(keyText, valueText);
            searchResults.add(keyText);
            materialSearchBar.setLastSuggestions(searchResults);
            // materialSearchBar.updateLastSuggestions(searchResults);
            Toast.makeText(this,"Saved!",Toast.LENGTH_SHORT).show();
            tts.speak("SAVED", TextToSpeech.QUEUE_FLUSH, null);
            Log.d("HASH", String.valueOf(hashmapper.containsKey(keyText)));
            saveToggler=false;
            clicker++;

        }

    }

    @Override
    public void onInit(int status) {
        if(status==TextToSpeech.SUCCESS){

            // int result=tts.setLanguage(Locale.CHINESE);
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){

                Log.e("TTS","This Language Is Not Supported");
            }
            else{

                btnSpeakout.setEnabled(true);
                speakout();
            }

        }

        else{

            Log.e("TTS", "Initilization Failed!");
        }
    }
}
