package com.example.huyng.nutrisnap.verdotti;

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
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;

public class DomandaV2errata extends RobotActivity implements RobotLifecycleCallbacks {
    MediaPlayer end;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.verdottierrata2);

        // Avvia suono applausi
        end = MediaPlayer.create(this, R.raw.wrong);
        end.setVolume(1.5f, 1.5f);
        end.start();

    }

    //Funzione per il Bottone Impariamo
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
        startActivity(activity2Intent); //Per andare alla pagina storie sostanze
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
                .withResources(R.raw.negation_shake_head_b001)
                .build();

        // Monta animazione
        Animate animateFinal = AnimateBuilder.with(qiContext)
                .withAnimation(vittoria)
                .build();
        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText(" Oh no!! La tua risposta ?? sbagliata! La risposta esatta era con le foglie dei broccoli! ").build();
        Say sayVerdotti2 = SayBuilder.with(qiContext).withText(" Premi il tasto home per tornare alla schermata principale. ").build();

        sayVerdotti1.run();
        sayVerdotti2.run();
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
