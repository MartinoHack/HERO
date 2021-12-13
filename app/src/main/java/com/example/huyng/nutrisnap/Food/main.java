package com.example.huyng.nutrisnap.Food;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorieSostanze;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;

import java.io.File;
import java.io.IOException;

public class main extends RobotActivity implements RobotLifecycleCallbacks {
    static final int SELECT_PHOTO = 1;
    static final int IMAGE_CAPTURE = 2;
    private static final String FILE_PROVIDER_ATHORITY = "com.example.huyng.nutrisnap.fileprovider";
    String mCurrentPhotoPath;
    private ImageView button;
    int turno;
    int puntipepper;
    int puntibambino;
    TextView pepper;
    TextView bambino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.main);

        button = findViewById(R.id.imageView54);

        // Animazione fade in per le frecce
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        button.startAnimation(animation);
        Bundle data1 = getIntent().getExtras();
        turno = data1.getInt("turno");
        System.out.println(turno);
        Bundle data2 = getIntent().getExtras();
        puntipepper = data2.getInt("pepper");
        Bundle data3 = getIntent().getExtras();
        puntibambino = data3.getInt("bambino");
        System.out.println(puntibambino);
        System.out.println(puntipepper);

        bambino = findViewById(R.id.punti19);
        pepper = findViewById(R.id.punti20);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Intent resultIntent;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (intent != null) {
                        // Get the URI of the selected image and pass it to resultItent
                        Uri selectedImage = intent.getData();
                        resultIntent = new Intent(main.this, ResultActivity.class);
                        resultIntent.putExtra("selectedImage", selectedImage.toString());
                        startActivity(resultIntent);
                    }
                    break;

                case IMAGE_CAPTURE:
                    // Get the URI of the temporary file and pass it to resultIntent
                    resultIntent = new Intent(main.this, ResultActivity.class);
                    resultIntent.putExtra("capturedImage", mCurrentPhotoPath);
                    Bundle data1 = new Bundle();
                    data1.putInt("turno",turno);
                    Bundle data2 = new Bundle();
                    data2.putInt("pepper",puntipepper);
                    Bundle data3 = new Bundle();
                    data3.putInt("bambino",puntibambino);
                    resultIntent.putExtras(data2);
                    resultIntent.putExtras(data3);
                    resultIntent.putExtras(data1);
                    startActivity(resultIntent);



                    break;
            }
        }
    }


    // Dispatch camera intent
    public void cameraBtnOnClick(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Save image to a temporary file
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "There was an error while capturing photo", Toast.LENGTH_SHORT).show();
            }
            // Send file URI to camera intent
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER_ATHORITY, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
            }
        }
    }

    // Create a temporary file to store captured photo
    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir + "/temp.jpg");
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onRobotFocusGained(QiContext qiContext) {
// Crea animazione saluto
        Animation saluto = AnimationBuilder.with(qiContext) // Create the builder with the context
                .withResources(R.raw.show_tablet_a004) // Set the action to say
                .build(); // Build the say action

        // Monta animazione
        Animate animate = AnimateBuilder.with(qiContext)
                .withAnimation(saluto)
                .build();


        Say lollo = SayBuilder.with(qiContext)
                .withText("Adesso è il mio turno. Premi sul pulsante, mostrami il cibo e scatta la foto. ").build();



        //Run Animazione Presentazione
        Future<Void> animateFuture = animate.async().run();
        // Set the text to say.
        lollo.run();

        // Esegui saluto synchronously
        animate.run();

    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }
}
