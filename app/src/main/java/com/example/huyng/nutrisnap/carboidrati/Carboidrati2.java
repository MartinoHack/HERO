package com.example.huyng.nutrisnap.carboidrati;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuSostanze;
import com.example.huyng.nutrisnap.R;

public class Carboidrati2 extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_carboidrati2);

        img = findViewById(R.id.imgvw);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown);
        img.startAnimation(animFadeIn);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), Carboidrati.class);
        startActivity(activity2Intent); //Per andare alla pagina Indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Stop
    public void ButtonStop(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuSostanze.class);
        startActivity(activity2Intent); //Per stoppare la pagina
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

        //Serie di Say Spiegazione
        Say sayCarbo = SayBuilder.with(qiContext)
                .withText(" I carboidrati sono indispensabili per fare qualsiasi cosa: " +
                        " camminare, correre, studiare, respirare e anche dormire.").build();

        //Say spiegazione
        sayCarbo.run();

        Intent activity2Intent = new Intent(getApplicationContext(), Carboidrati3.class);
        startActivity(activity2Intent); //Per andare avanti
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
