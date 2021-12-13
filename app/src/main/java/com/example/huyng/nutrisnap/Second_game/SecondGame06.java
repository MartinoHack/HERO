package com.example.huyng.nutrisnap.Second_game;

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
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;

public class SecondGame06 extends RobotActivity implements RobotLifecycleCallbacks {

    MediaPlayer mediaPlayer, correctSound, incorrectSound, help;
    ImageView mCard16, mCard17, mCard18;
    ImageButton mHelp;
    Animation animTranslate;
    TextView mScoreView;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.second_game06);

        mScoreView = findViewById(R.id.punti6);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalscore");

        mScoreView.setText("" + score + ""); //+ Database.questions.length */

        // Avvia suono delle carte
        mediaPlayer = MediaPlayer.create(this, R.raw.three_card_sound);
        mediaPlayer.start();

        mCard16 = findViewById(R.id.card_helicopter);
        mCard17 = findViewById(R.id.card_car);
        mCard18 = findViewById(R.id.card_moto);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        mCard16.startAnimation(animTranslate);
        mCard17.startAnimation(animTranslate);
        mCard18.startAnimation(animTranslate);

        // Logica delle carte cliccate
        mCard16.setOnClickListener(v -> startCorrect());
        mCard17.setOnClickListener(v -> startIncorrect());
        mCard18.setOnClickListener(v -> startIncorrect());


    }

    // Ferma il gioco e vai al risultato = SecondGameResult
    public void EndActivity(View view) {
        Intent EndIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(EndIntent);
    }


    public void startCorrect() {
        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        correctSound.start();
        score++;
        Intent intent1 = new Intent(getApplicationContext(), SecondGame07.class);
        Bundle data1 = new Bundle();
        data1.putInt("finalscore", score);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    public void startIncorrect() {
        incorrectSound = MediaPlayer.create(this, R.raw.wrong_sound);
        incorrectSound.start();
        Intent intent1 = new Intent(getApplicationContext(), SecondGame07.class);
        Bundle data1 = new Bundle();
        data1.putInt("finalscore", score);
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



    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {


        // Risposte che riconosce Pepper
        PhraseSet phraseSetCorrect = PhraseSetBuilder.with(qiContext)
                .withTexts("Ceci", "La soia", "I ceci", "Soia").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("L'uva", "Uva").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("Il melone", "Melone").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)) {
            Say sayCorrect = SayBuilder.with(qiContext)
                    .withText("Complimenti!  La soia è l'intruso perchènon è una frutta come l'uva e il melone.").build();
            sayCorrect.run();
            score++;
            Intent intent1 = new Intent(getApplicationContext(), SecondGame07.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)) {
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Oh Oh! Attenzione! l'uva non è l'intruso.").build();
            sayIncorrect1.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame07.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else {
            Say sayIncorrect2 = SayBuilder.with(qiContext)
                    .withText("Oh Oh! Attenzione! Il melone non è l'intruso").build();
            sayIncorrect2.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame07.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
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
