package com.example.huyng.nutrisnap.Second_game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;

public class SecondGameResult extends RobotActivity implements RobotLifecycleCallbacks {

    MediaPlayer end;
    TextView mScoreView;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.second_game_result);


        mScoreView = findViewById(R.id.punti8);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalscore");

        mScoreView.setText("" + score + ""); //+ Database.questions.length */


        // Avvia suono applausi
        end = MediaPlayer.create(this, R.raw.applausi_short);
        end.setVolume(1.5f, 1.5f);
        end.start();


    }

    //Funzione per il Bottone Iniziamo
    public void Riprova(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SecondGame01.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
    }

    //Funzione per il Bottone Iniziamo
    public void Home(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
    }

    // Pulisci memoria player
    protected void onStop() {
        super.onStop();
        if (end != null) {
            end.stop();
            end.release();
            end = null;
        }
    }

    // Visualizza i punti raccolti
    public void updateScore(int point) {
        mScoreView.setText("" + score);
    }

    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }


    @Override
    public void onRobotFocusGained(QiContext qiContext) {

        // Crea animazione vittoria
        Animation vittoria = AnimationBuilder.with(qiContext)
                .withResources(R.raw.nicereaction_a001)
                .build();

        // Monta animazione
        Animate animateFinal = AnimateBuilder.with(qiContext)
                .withAnimation(vittoria)
                .build();

        // Crea frase finale
        Say sayFrasefinale = SayBuilder.with(qiContext)
                .withText("Bravissimo!! Hai concluso il gioco con un punteggio di " +score + "punti.").build();

        Say sayFrasefinale1 = SayBuilder.with(qiContext)
                .withText("Ben fatto!").build();

        Say sayFrasefinale2 = SayBuilder.with(qiContext)
                .withText("Ora puoi riprovare o tornare indietro.").build();

        // Esegui animazione synchronously
        animateFinal.run();
        // Esegui frase finale synchronously
        sayFrasefinale.run();
        sayFrasefinale1.run();
        sayFrasefinale2.run();

    }

    @Override
    public void onRobotFocusLost() {
    }

    @Override
    public void onRobotFocusRefused(String reason) {
    }
}

