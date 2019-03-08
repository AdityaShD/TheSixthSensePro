package com.example.hackbpitpro;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HearingAssist extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView textView;
    private CircleImageView circleImageView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView visionTextNav,SamTextNav;
    private ImageView navLeft,navRight;
    private TextToSpeech tts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_assist);
        textView=findViewById(R.id.speechToText);
        circleImageView=findViewById(R.id.circleImageView);
        visionTextNav=findViewById(R.id.navVision);
        SamTextNav=findViewById(R.id.navSAM);
        navLeft=findViewById(R.id.navImageLefthadact);
        navRight=findViewById(R.id.navImageRighthadact);
        tts=new TextToSpeech(this, this);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        visionTextNav.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                tts.speak("Switching to SAM", TextToSpeech.QUEUE_FLUSH,null);

                if(tts.isSpeaking()) {
                    Intent intent = new Intent(HearingAssist.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return false;
            }
        });

        navLeft.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                tts.speak("Switching to VISION", TextToSpeech.QUEUE_FLUSH,null);

                if(tts.isSpeaking()) {
                    Intent intent = new Intent(HearingAssist.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                return false;
            }
        });

      SamTextNav.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {

              v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
              tts.speak("Switching to SAM", TextToSpeech.QUEUE_FLUSH,null);

              if(tts.isSpeaking()) {
                  Intent intent = new Intent(HearingAssist.this, SAM.class);
                  startActivity(intent);
                  finish();
              }

              return false;
          }
      });

     navRight.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
             v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
             tts.speak("Switching to SAM", TextToSpeech.QUEUE_FLUSH,null);

             if(tts.isSpeaking()) {
                 Intent intent = new Intent(HearingAssist.this, SAM.class);
                 startActivity(intent);
                 finish();
             }


             return false;
         }
     });



    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                }
                break;
            }

        }
    }


    @Override
    public void onInit(int status) {

    }
}
