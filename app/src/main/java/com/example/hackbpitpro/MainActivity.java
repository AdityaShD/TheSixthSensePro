package com.example.hackbpitpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitTextDetect;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import com.wonderkiln.camerakit.GooglePlayServicesUnavailableException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private CameraView cameraView;
    private FancyButton cambutton;
    private CircleImageView circleImageView;
    private TextView desc;
    private TextView NavigatorLeftSam,NavigatorRightHad;
    private TextToSpeech tts;
    private FancyButton OcrButton,VisionButton;
    private ImageView navLeft,navRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView=findViewById(R.id.camera);
        cambutton=findViewById(R.id.camButton);
       // circleImageView=findViewById(R.id.circImgView);
        desc=findViewById(R.id.textView);
        NavigatorLeftSam=findViewById(R.id.navSam);
        OcrButton=findViewById(R.id.fancyButtonocr);
        VisionButton=findViewById(R.id.fancyVision);
        VisionButton.setEnabled(false);
        NavigatorRightHad=findViewById(R.id.navHad);
        navLeft=findViewById(R.id.navImageLeft);
        navRight=findViewById(R.id.navImageRight);


        tts=new TextToSpeech(this,this);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                if(!VisionButton.isEnabled()) {
                 //   circleImageView.setImageBitmap(cameraKitImage.getBitmap());
                    getImageDetails(cameraKitImage.getBitmap());
                }

               else{

                   getOcrDetails(cameraKitImage.getBitmap());

                }

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        VisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak("VISION MODE ACTIVATED",TextToSpeech.QUEUE_FLUSH,null);
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                cambutton.setIconResource(R.drawable.camera_enchanced);
                VisionButton.setEnabled(false);
                OcrButton.setEnabled(true);


            }
        });


         OcrButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 tts.speak("OCR MODE ACTIVATED",TextToSpeech.QUEUE_FLUSH,null);
                 v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                 OcrButton.setEnabled(false);
                 cambutton.setIconResource(R.drawable.ocr_dark_new);
                 VisionButton.setEnabled(true);


             }
         });




       cambutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
               cameraView.captureImage();
           }
       });

       NavigatorLeftSam.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {

               v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
           tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

           if(tts.isSpeaking()) {
               Intent intent = new Intent(MainActivity.this, SAM.class);
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
               tts.speak("Switching to SAM",TextToSpeech.QUEUE_FLUSH,null);

               if(tts.isSpeaking()) {
                   Intent intent = new Intent(MainActivity.this, SAM.class);
                   startActivity(intent);
                   finish();
               }

               return false;
           }
       });



       NavigatorRightHad.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
               tts.speak("Switching to HAD",TextToSpeech.QUEUE_FLUSH,null);
               if(tts.isSpeaking()){

                   Intent intent = new Intent(MainActivity.this, HearingAssist.class);
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
                tts.speak("Switching to HAD",TextToSpeech.QUEUE_FLUSH,null);
                if(tts.isSpeaking()){

                    Intent intent = new Intent(MainActivity.this, HearingAssist.class);
                    startActivity(intent);
                    finish();

                }



                return false;
            }
        });




    }

    private void getOcrDetails(Bitmap bitmap) {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        Task<FirebaseVisionText> result =
                detector.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {

                                String text=firebaseVisionText.getText();
                                desc.setText(text);
                                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);


                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });







    }

    private void getImageDetails(Bitmap bitmap) {

        FirebaseVisionImage image=FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionOnDeviceImageLabelerOptions options =
         new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
         .setConfidenceThreshold(0.7f)
        .build();
        FirebaseVisionImageLabeler labeler= FirebaseVision.getInstance().getOnDeviceImageLabeler();
        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {

                FirebaseVisionImageLabel label= firebaseVisionImageLabels.get(0);
                desc.setText(label.getText()+"-"+label.getConfidence());
                speaker(label.getText(),label.getConfidence());

//                for (FirebaseVisionImageLabel label:firebaseVisionImageLabels) {
//                    String text = label.getText();
//                    String entityId = label.getEntityId();
//                    float confidence = label.getConfidence();
//

                }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void speaker(String text, float confidence) {

        double roundoff=Math.round(confidence * 100.0) / 100.0;

        tts.setPitch((float)1);
        tts.setSpeechRate((float) 0.85);
         tts.speak("I detected"+text+"with an accuracy of"+roundoff*100,TextToSpeech.QUEUE_FLUSH, null);



    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    public void onInit(int status) {

    }


}
