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
import com.example.huyng.nutrisnap.Food.VerdePepper;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.Second_game.SecondGame02;
import com.example.huyng.nutrisnap.SelectActivity;


public class Quiz1 extends RobotActivity implements RobotLifecycleCallbacks {

    ImageView mCard1, mCard2, mCard3;
    ImageButton mStop;
    Animation animTranslate;
    private TextView mScoreView;


    private String mAnswer;
    private int mScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.quiz01);


        mScoreView = findViewById(R.id.punti);
        mScoreView.setText("" + mScore);

        // Avvia suono delle carte

        mCard1 = findViewById(R.id.card_apple);
        mCard2 = findViewById(R.id.card_ball);
        mCard3 = findViewById(R.id.card_banana);


        mStop = findViewById(R.id.stop);

        // Animazione translate per le carte
        animTranslate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        mCard1.startAnimation(animTranslate);
        mCard2.startAnimation(animTranslate);
        mCard3.startAnimation(animTranslate);



    }

    // Ferma il gioco
    public void EndActivity(View view) {
        Intent EndIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(EndIntent);
    }



    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {

        Say sayQuestion = SayBuilder.with(qiContext).withText("Cosa dobbiamo fare per dare al nostro corpo l'energia di cui ha bisogno? ").build();
        sayQuestion.run();


        // Risposte che riconosce Pepper
        PhraseSet phraseSetCorrect = PhraseSetBuilder.with(qiContext)
                .withTexts("A","Bere bevande gassate").build();

        PhraseSet phraseSetIncorrect1 = PhraseSetBuilder.with(qiContext)
                .withTexts("B","Mangiare fritture").build();

        PhraseSet phraseSetIncorrect2 = PhraseSetBuilder.with(qiContext)
                .withTexts("C","Mangiare cibi sani").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetCorrect, phraseSetIncorrect1, phraseSetIncorrect2).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if(PhraseSetUtil.equals(matchedPhraseSet, phraseSetCorrect)){
            Say say = SayBuilder.with(qiContext)
                    .withText("Attenzione, non è la risposta esatta, bere troppe bevande gassate non ti darà l'energia di cui hai bisogno ").build();
            say.run();
            Intent intent1 = new Intent(getApplicationContext(), Quiz2.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", mScore);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
        else if(PhraseSetUtil.equals(matchedPhraseSet, phraseSetIncorrect1)){
            Say sayIncorrect1 = SayBuilder.with(qiContext)
                    .withText("Attenzione! non è la risposta esatta, mangiare troppe fritture non ti darà l'energia di cui hai bisogno ").build();
            sayIncorrect1.run();
            Intent intent1 = new Intent(getApplicationContext(), Quiz2.class);
            Bundle data1 = new Bundle();
            data1.putInt("finalscore", mScore);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
        else{Say sayIncorrect2 = SayBuilder.with(qiContext)
                .withText("Bravo! Mangiare cibi sani ti darà la corretta energia di cui hai bisogno.").build();
            sayIncorrect2.run();
            mScore++;
            Intent intent1 = new Intent(getApplicationContext(), Quiz2.class);
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