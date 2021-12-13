package com.example.huyng.nutrisnap.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;

public class SemaforoGiallo extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView button;
    int turno;
    int puntipepper;
    int puntibambino;
    TextView pepper;
    TextView bambino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.semaforogiallo);
        button = findViewById(R.id.imageView48);

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




        bambino = findViewById(R.id.punti11);
        pepper = findViewById(R.id.punti12);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
    }

    //Funzione per il Bottone Indietro
    public void ButtonSi(View view) {
        puntibambino++;
        Intent intent1 = new Intent(getApplicationContext(), BambinoCorretto.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data2 = new Bundle();
        data2.putInt("pepper",puntipepper);
        Bundle data3 = new Bundle();
        data3.putInt("bambino",puntibambino);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    //Funzione per il Bottone Home
    public void ButtonNO(View view) {
        Intent intent1 = new Intent(getApplicationContext(), BambinoNoncorretto.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data2 = new Bundle();
        data2.putInt("pepper",puntipepper);
        Bundle data3 = new Bundle();
        data3.putInt("bambino",puntibambino);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), TurnoBambino.class);
        startActivity(activity2Intent); //Per andare indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.

        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("Hai deciso di accendere la luce gialla. Giudice è corretto?").build();


        sayVerdotti1.run();

        // Create the phraseSetGrezzo.
        PhraseSet phraseSetStrada = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Si", "E' corretto") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the phraseSetFibre.
        PhraseSet phraseSetPedone = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("NO","Non è corretto") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.



        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetStrada, phraseSetPedone) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetStrada)) {
            //Richiamare animazione affermazione e andare avanti
            puntibambino++;
            Intent intent1 = new Intent(getApplicationContext(), BambinoCorretto.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data2 = new Bundle();
            data2.putInt("pepper",puntipepper);
            Bundle data3 = new Bundle();
            data3.putInt("bambino",puntibambino);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            intent1.putExtras(data1);
            startActivity(intent1);

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetPedone)) {
            Intent intent1 = new Intent(getApplicationContext(), BambinoNoncorretto.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data2 = new Bundle();
            data2.putInt("pepper",puntipepper);
            Bundle data3 = new Bundle();
            data3.putInt("bambino",puntibambino);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            intent1.putExtras(data1);
            startActivity(intent1);

        }
    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}