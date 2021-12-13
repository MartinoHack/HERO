package com.example.huyng.nutrisnap.fibre;

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

public class Fibre extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_fibre);

        img = findViewById(R.id.imgvw);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        img.startAnimation(animFadeIn);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuSostanze.class);
        startActivity(activity2Intent); //Per andare alla pagina scelta Impariamo
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

        //Serie di Say Spiegazione Fibre
        Say sayFibre1 = SayBuilder.with(qiContext).withText(" Le fibre alimentari sono sostanze nutritive che hanno un effetto positivo per il benessere del nostro corpo.").build();

        Say sayFibre2 = SayBuilder.with(qiContext).withText(" Favoriscono il suo corretto funzionamento e il metabolismo.").build();


        //Say spiegazione Fibre
        sayFibre1.run();
        sayFibre2.run();

        Intent activity2Intent = new Intent(getApplicationContext(), Fibre2.class);
        startActivity(activity2Intent); //Per andare alla pagina successiva


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