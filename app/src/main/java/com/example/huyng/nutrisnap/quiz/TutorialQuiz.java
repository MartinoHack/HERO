package com.example.huyng.nutrisnap.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;


public class TutorialQuiz extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView mArrow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.quiztutorial);

        mArrow2 = findViewById(R.id.arrow2);

        // Animazione fade in per le frecce
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_4arrows);
        mArrow2.startAnimation(animation);
    }

    // Torna alla scelta del gioco = SelectActivity
    public void CameToSelectActivity(View view) {
        Intent CameToSelectIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(CameToSelectIntent);
    }

    // Inizia il gioco = SecondGame
    public void PlayGameActivity(View view) {
        Intent PlayGameIntent = new Intent(getApplicationContext(), Quiz1.class);
        startActivity(PlayGameIntent);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {

        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // Crea animazione mostra tablet
        com.aldebaran.qi.sdk.object.actuation.Animation showTablet2 = AnimationBuilder.with(qiContext)
                .withResources(R.raw.show_tablet_a006).build();
        // Monta animazione
        Animate animate = AnimateBuilder.with(qiContext)
                .withAnimation(showTablet2).build();

        // Create frase1
        Say sayFrase1 = SayBuilder.with(qiContext)
                .withText("Benvenuto nel quiz alimentare").build();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create frase2
        Say sayFrase2 = SayBuilder.with(qiContext)
                .withText("\\rspd=90\\ Ti farò delle domande e tu cercherai di rispondere in modo corretto. Per rispondere, comunica a Pepper la risposta ad alta voce.  Buona fortuna").build();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Create frase4
        Say sayFrase4 = SayBuilder.with(qiContext)
                .withText("\\rspd=90\\ Se vuoi interrompere il gioco troverai il pulsante STOP.").build();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create frase5
        Say sayFrase5 = SayBuilder.with(qiContext)
                .withText("\\rspd=90\\ Per iniziare clicca il tasto verde.").build();

        // Esegui frase 1, 2, 3, 4, 5
        sayFrase1.run();
        sayFrase2.run();
        sayFrase4.run();
        sayFrase5.run();
        // Esegui animazione synchronously
        animate.run();

        // Crea domanda inizio gioco
        Say sayInizia = SayBuilder.with(qiContext)
                .withText("Sei pronto?").build();

        // Esegui domanda inizio gioco asynchronously
        sayInizia.run();

        // SALTA LISTEN
        //Intent activityXIntent = new Intent(getApplicationContext(), SecondGameResult.class);
        //startActivity(activityXIntent);

        // Risposte che riconosce Pepper
        PhraseSet phraseSetYes = PhraseSetBuilder.with(qiContext)
                .withTexts("Si", "Ok", "Certo", "Sono pronto", "Si lo sono", "Yeah").build();

        PhraseSet phraseSetNo = PhraseSetBuilder.with(qiContext)
                .withTexts("No", "Non lo sono", "Non ancora").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetYes, phraseSetNo).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)) {
            Intent activityYesIntent = new Intent(getApplicationContext(), Quiz1.class);
            startActivity(activityYesIntent);
        } else {
            Intent activityNoIntent = new Intent(getApplicationContext(), TutorialQuiz.class);
            startActivity(activityNoIntent);
        }
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }

}