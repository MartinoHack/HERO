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
import com.example.huyng.nutrisnap.SelectActivity;

public class Quiz6 extends RobotActivity implements RobotLifecycleCallbacks {

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

        setContentView(R.layout.quiz06);

        mScoreView = findViewById(R.id.punti6);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalscore");

        mScoreView.setText("" + score + ""); //+ Database.questions.length */



        mCard16 = findViewById(R.id.card_helicopter);
        mCard17 = findViewById(R.id.card_car);
        mCard18 = findViewById(R.id.card_moto);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        mCard16.startAnimation(animTranslate);
        mCard17.startAnimation(animTranslate);
        mCard18.startAnimation(animTranslate);




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
        Say sayQuestion = SayBuilder.with(qiContext).withText("Gli alimenti sono classificati in gruppi. Quali?").build();
        sayQuestion.run();

        // Risposte che riconosce Pepper
        PhraseSet phraseSetCorrect = PhraseSetBuilder.with(qiContext)
                .withTexts("a", "Acqua, olio e zuccheri").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("b", "Carboidrati, grassi e proteine").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("c", "sali minerali").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)) {
            Say sayCorrect = SayBuilder.with(qiContext)
                    .withText("Attenzione. non ?? corretto, gli alimenti sono classificati in Carboidrati, grassi e proteine.").build();
            sayCorrect.run();
            Intent intent1 = new Intent(getApplicationContext(), Quiz7.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)) {
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Esatto! Ben fatto").build();
            sayIncorrect1.run();
            score++;
            Intent intent1 = new Intent(getApplicationContext(), Quiz7.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", score);
            intent1.putExtras(data1);
            startActivity(intent1);
        } else {
            Say sayIncorrect2 = SayBuilder.with(qiContext)
                    .withText("Attenzione. non ?? corretto, gli alimenti sono classificati in Carboidrati, grassi e proteine.").build();
            sayIncorrect2.run();
            Intent intent1 = new Intent(getApplicationContext(), Quiz7.class);
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
