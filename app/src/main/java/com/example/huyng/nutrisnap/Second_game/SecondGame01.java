package com.example.huyng.nutrisnap.Second_game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.huyng.nutrisnap.Food.TurnoBambino;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;


public class SecondGame01 extends RobotActivity implements RobotLifecycleCallbacks {

    MediaPlayer mediaPlayer, correctSound, incorrectSound, help;
    ImageView mCard1, mCard2, mCard3;
    ImageButton mStop;
    Animation animTranslate;
    private TextView mScoreView;
    private int mScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.second_game01);


        mScoreView = findViewById(R.id.punti);
        mScoreView.setText("" + mScore);

        // Avvia suono delle carte
        mediaPlayer = MediaPlayer.create(this, R.raw.three_card_sound);
        mediaPlayer.start();

        mCard1 = findViewById(R.id.card_apple);
        mCard2 = findViewById(R.id.card_ball);
        mCard3 = findViewById(R.id.card_banana);


        mStop = findViewById(R.id.stop);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        mCard1.startAnimation(animTranslate);
        mCard2.startAnimation(animTranslate);
        mCard3.startAnimation(animTranslate);

        // Logica delle carte cliccate
        mCard1.setOnClickListener(v -> startIncorrect());
        mCard2.setOnClickListener(v -> startCorrect());
        mCard3.setOnClickListener(v -> startIncorrect());


    }

    // Ferma il gioco
    public void EndActivity(View view) {
        Intent EndIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(EndIntent);
    }


    public void startCorrect() {
        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        correctSound.start();
        mScore++;
        Intent intent1 = new Intent(getApplicationContext(), SecondGame02.class);
        Bundle data1 = new Bundle();
        data1.putInt("finalscore", mScore);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    public void startIncorrect() {
        incorrectSound = MediaPlayer.create(this, R.raw.wrong_sound);
        incorrectSound.start();
        Intent intent1 = new Intent(getApplicationContext(), SecondGame02.class);
        Bundle data1 = new Bundle();
        data1.putInt("finalscore", mScore);
        intent1.putExtras(data1);
        startActivity(intent1);
    }


    // Pulisci memoria player
    protected void onStop() {
        super.onStop();
        if (help != null) {
            help.stop();
            help.release();
            help = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (correctSound != null) {
            correctSound.stop();
            correctSound.release();
            correctSound = null;
        }
        if (incorrectSound != null) {
            incorrectSound.stop();
            incorrectSound.release();
            incorrectSound = null;
        }
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {

        Say sayQuestion = SayBuilder.with(qiContext).withText("Cerca l'intruso!").build();
        sayQuestion.run();


        // Risposte che riconosce Pepper
        PhraseSet phraseSetCorrect = PhraseSetBuilder.with(qiContext)
                .withTexts("Sedano", "Il sedano", "Verdura").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("La bistecca", "bistecca").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("Pollo", "Il pollo", "Coscia di pollo").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if(PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)){
            Say sayCorrect = SayBuilder.with(qiContext)
                    .withText("Bravo! Il sedano è un intruso perchè è una verdura, mentre il pollo e la bistecca no").build();
            sayCorrect.run();
            mScore++;
            Intent intent1 = new Intent(getApplicationContext(), SecondGame02.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", mScore);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
        else if(PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)){
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Attenzione! La bistecca non è l'intruso").build();
            sayIncorrect1.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame02.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", mScore);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
        else{Say sayIncorrect2 = SayBuilder.with(qiContext)
                .withText("Attenzione! Il pollo non è l'intruso").build();
            sayIncorrect2.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame02.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", mScore);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
    }

    @Override
    public void onRobotFocusLost() {
    }

    @Override
    public void onRobotFocusRefused(String reason) {
    }

}