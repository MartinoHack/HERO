package com.example.huyng.nutrisnap.verdotti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;

public class VerdottiStory2 extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_verdotti2);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), VerdottiStory.class);
        startActivity(activity2Intent); //Per andare alla pagina Indietro
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

        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText(" Nulla si butta dai broccoli: con le foglie fanno i tetti delle loro case e con i fusti le pareti,").build();


        Say sayVerdotti2 = SayBuilder.with(qiContext).withText(" mentre con i fiori alimentano tutta la popolazione di Chimangiacosa." +
                " Una volta all'anno si tengono le Mangiacosiadi dove ogni abitante può partecipare con una ricetta di cucina. ").build();

        Say sayVerdotti3 = SayBuilder.with(qiContext).withText(" Questi piccoletti hanno inventato una pozione per rendere i broccoli miracolosi: il Sulfurgas " +
                " che, quando i broccoli cuociono fanno un pò di puzzetta, ma solo gli sciocchi smettono di mangiarli perchè la magia sta tutta li. ").build();

        Say sayVerdotti4 = SayBuilder.with(qiContext).withText(" Parola di Magaverde!").build();


        //Say spiegazione Verdotti
        sayVerdotti1.run();
        sayVerdotti2.run();
        sayVerdotti3.run();
        sayVerdotti4.run();

        Say sayFinale = SayBuilder.with(qiContext).withText(" Ora sai davvero tutto sul mondo dei Verdotti..").build();
        Say sayFinale2 = SayBuilder.with(qiContext).withText(" Ti metterò alla prova con delle domande di comprensione.").build();

        sayFinale.run();
        sayFinale2.run();

        //Say affermazione Indietro
        Say sayAffermazioneIndietro = SayBuilder.with(qiContext)
                .withText("Ok").build();

        // Create the PhraseSetBack Indietro.
        PhraseSet phraseSetBack = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Voglio imparare altro", "Imparare", "Imparare altro", "Impariamo", "Impariamo altro", "Altro", "Pepper impariamo altro", "Pepper altro", "Indietro", "Basta", "Stop", "Basta così", "Pepper vai indietro", "Pepper Basta", "Pepper Stop", "Pepper Basta così") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet

        // Create the PhraseSetRepeat Ripeti .
        PhraseSet phraseSetRepeat = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Ripeti", "Ripetere", "Non ho capito bene", "Non ho capito", "Ricominciamo", "Ricomincia", "Da capo", "Pepper Ripeti", "Pepper Ricominciamo", "Pepper Ricomincia", "Pepper, Da capo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet

        Intent activity2Intent = new Intent(getApplicationContext(), Verdottidomanda1.class);
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