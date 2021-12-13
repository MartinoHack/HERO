package com.example.huyng.nutrisnap.Terzo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class Terzo5 extends RobotActivity implements RobotLifecycleCallbacks {
    MediaPlayer end;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.terzo5);

        // Avvia suono applausi
        end = MediaPlayer.create(this, R.raw.applausi);
        end.setVolume(1.5f, 1.5f);
        end.start();

    }

    // Inizia il gioco
    public void PlayGameActivity(View view) {
        Intent PlayGameIntent = new Intent(getApplicationContext(), Terzo6.class);
        startActivity(PlayGameIntent);
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent);
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

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.
        // Crea animazione vittoria
        Animation vittoria = AnimationBuilder.with(qiContext)
                .withResources(R.raw.nicereaction_a001)
                .build();

        // Monta animazione
        Animate animateFinal = AnimateBuilder.with(qiContext)
                .withAnimation(vittoria)
                .build();
        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("\\rspd=85\\ Complimenti!! La risposta è corretta. ").build();
        Say sayVerdotti2 = SayBuilder.with(qiContext).withText("\\rspd=85\\ Il pomodoro è buono e fa bene alla salute! Contiene ferro, sali minerali e molte vitamine. Ne esistono moltissime varietà e forme.").build();
        Say sayVerdotti3 = SayBuilder.with(qiContext).withText("\\rspd=85\\ Premi il tasto verde per andare all'indovinello successivo, oppure il tasto home per tornare al menù iniziale.").build();

        sayVerdotti1.run();
        sayVerdotti2.run();
        sayVerdotti3.run();
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
