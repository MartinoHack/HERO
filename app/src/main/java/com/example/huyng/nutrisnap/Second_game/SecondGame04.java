package com.example.huyng.nutrisnap.Second_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class SecondGame04 extends RobotActivity implements RobotLifecycleCallbacks {

    MediaPlayer mediaPlayer, correctSound, incorrectSound, help;
    ImageView mCard10, mCard11, mCard12;
    Animation animTranslate;
    TextView mScoreView;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.second_game04);

        mScoreView = findViewById(R.id.punti4);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalscore");

        mScoreView.setText("" + score + ""); //+ Database.questions.length */

        // Avvia suono delle carte
        mediaPlayer = MediaPlayer.create(this, R.raw.three_card_sound);
        mediaPlayer.start();

        mCard10 = findViewById(R.id.card_lego);
        mCard11 = findViewById(R.id.card_cupcake);
        mCard12 = findViewById(R.id.card_horse);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        mCard10.startAnimation(animTranslate);
        mCard11.startAnimation(animTranslate);
        mCard12.startAnimation(animTranslate);

        // Logica delle carte cliccate
        mCard10.setOnClickListener(v -> startIncorrect());
        mCard11.setOnClickListener(v -> startCorrect());
        mCard12.setOnClickListener(v -> startIncorrect());

    }

    // Ferma il gioco e vai al risultato = SecondGameResult
    public void EndActivity(View view) {
        Intent EndIntent = new Intent(getApplicationContext(), SecondGameResult.class);
        startActivity(EndIntent);
    }


    public void startCorrect() {
        correctSound = MediaPlayer.create(this, R.raw.correct_sound);
        correctSound.start();
        score++;
        Intent intent1 = new Intent(getApplicationContext(), SecondGame05.class);
        Bundle data1 = new Bundle();
        data1.putInt("finalscore", score);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    public void startIncorrect() {
        incorrectSound = MediaPlayer.create(this, R.raw.wrong_sound);
        incorrectSound.start();
        Intent intent1 = new Intent(getApplicationContext(), SecondGame05.class);
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
                .withTexts("Lasagna", "La lasagna", "La pasta").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("Zucchina", "La zucchina").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("Pomodori", "I pomodori").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)) {
            Say sayCorrect = SayBuilder.with(qiContext)
                    .withText("Complimenti! La lasagna è l'intruso perchè non è un ortaggio come la zucchina e il pomodoro.").build();
            sayCorrect.run();
            score++;
            Intent intent1 = new Intent(getApplicationContext(), SecondGame05.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)) {
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Oh Oh! Attenzione! La zucchina non è l'intruso").build();
            sayIncorrect1.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame05.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else {
            Say sayIncorrect2 = SayBuilder.with(qiContext)
                    .withText("Oh Oh! Attenzione! I pomodori non sono l'intruso.").build();
            sayIncorrect2.run();
            Intent intent1 = new Intent(getApplicationContext(), SecondGame05.class);
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
