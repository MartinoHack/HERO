package com.example.huyng.nutrisnap.quiz;

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
import com.example.huyng.nutrisnap.Second_game.SecondGameResult;
import com.example.huyng.nutrisnap.SelectActivity;

public class Quiz7 extends RobotActivity implements RobotLifecycleCallbacks {

    MediaPlayer mediaPlayer, correctSound, incorrectSound, help;
    ImageView mCard19, mCard20, mCard21;
    ImageButton mHelp;
    Animation animTranslate;
    TextView mScoreView;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.quiz07);


        mScoreView = findViewById(R.id.punti7);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalscore");

        mScoreView.setText("" + score + ""); //+ Database.questions.length */



        mCard19 = findViewById(R.id.card_pizza);
        mCard20 = findViewById(R.id.card_juice);
        mCard21 = findViewById(R.id.card_sandwich);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        mCard19.startAnimation(animTranslate);
        mCard20.startAnimation(animTranslate);
        mCard21.startAnimation(animTranslate);



    }

    // Ferma il gioco e vai al risultato = SecondGameResult
    public void EndActivity(View view) {
        Intent EndIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(EndIntent);
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


        Say sayQuestion = SayBuilder.with(qiContext).withText("Perchè è importante l'energia per il nostro corpo?").build();
        sayQuestion.run();

        // Risposte che riconosce Pepper
        PhraseSet phraseSetCorrect = PhraseSetBuilder.with(qiContext)
                .withTexts("a", "per crescere bene e per mantenere sano il nostro organismo").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("b", "Per mangiare meglio").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("c", "Per guardare la televisione").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)) {
            Say sayCorrect = SayBuilder.with(qiContext)
                    .withText("Esatto. Risposta corretta").build();
            sayCorrect.run();
            score++;
            Intent intent1 = new Intent(getApplicationContext(), QuizResult.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)) {
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Attenzione! non è corretto, l'energia è importante per crescere bene e mantenere sano il nostro organismo.").build();
            sayIncorrect1.run();
            Intent intent1 = new Intent(getApplicationContext(), QuizResult.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else {
            Say sayIncorrect2 = SayBuilder.with(qiContext)
                    .withText("Attenzione! non è corretto, l'energia è importante per crescere bene e mantenere sano il nostro organismo.").build();
            sayIncorrect2.run();
            Intent intent1 = new Intent(getApplicationContext(), QuizResult.class);
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
