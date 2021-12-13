package com.example.huyng.nutrisnap.fata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;

public class FataStory2 extends RobotActivity implements RobotLifecycleCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_fata2);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), FataStory.class);
        startActivity(activity2Intent); //Per andare alla pagina scelta Indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Stop
    public void ButtonStop(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
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

        //Serie di Say Fata

        Say sayFata1 = SayBuilder.with(qiContext).withText(" Quanda il signor Talpa rientrò, si complimentò con la fata perchè in città si parlava delle sue pozioni. ").build();


        Say sayFata2 = SayBuilder.with(qiContext).withText(" Naturalmente ne aveva dimenticato gli ingredienti," +
                " ma lui ne annusò l'aria e disse: questo è il rosmarino che mettiamo nell'arrosto con le patate! ").build();

        Say sayFata3 = SayBuilder.with(qiContext).withText(" Da quel giorno, quell'erbetta squisita venne usata per le pozioni magiche di smemorina " +
                " e la fata divenne famosa in tutti i paesi del mondo fatato. ").build();

        //Say spiegazione Fata
        sayFata1.run();
        sayFata2.run();
        sayFata3.run();

        Say sayFinale = SayBuilder.with(qiContext).withText(" Ora sai davvero tutto sulla storia della Fata Smemorina.").build();
        Say sayFinale2 = SayBuilder.with(qiContext).withText(" Ti metterò alla prova con delle domande di comprensione.").build();

        sayFinale.run();
        sayFinale2.run();


        Intent activity2Intent = new Intent(getApplicationContext(), Fatadomanda1.class);
        startActivity(activity2Intent); //Per andare alla pagine successiva

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