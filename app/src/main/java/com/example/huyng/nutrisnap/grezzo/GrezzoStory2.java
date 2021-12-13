package com.example.huyng.nutrisnap.grezzo;

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

public class GrezzoStory2 extends RobotActivity implements RobotLifecycleCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_grezzo2);
    }


    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), GrezzoStory.class);
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

        //Serie di Say Spiegazione Grezzo
        Say sayGrezzo6 = SayBuilder.with(qiContext)
                .withText(" Così, abbiamo deciso di toglierci il vestito, pur sapendo che avremmo perso tante cose buone." +
                        " Lo abbiamo fatto per essere uguali a tutti gli altri." +
                        " Grezzo si fermò a riflettere, gli avrebbe fatto piacere stare insieme a loro, così non sarebbe stato solo" +
                        " ma allo stesso tempo voleva rimanere se stesso. Si fece coraggio e dopo averli salutati saltò fuori dalla vasca." +
                        " Mentre camminava notò un grande sacco di iuta abbandonato in un angolo.").build();

        Say sayGrezzo7 = SayBuilder.with(qiContext)
                .withText(" Si arrampicò e vide che era pieno di tantissimi chicchi scuri, proprio come lui." +
                        " Ciao fratello, salta giù con noi! Lo incitarono in coro. Noi siamo integrali, doniamo tanta energia a grandi e piccini.").build();
        Say sayGrezzo = SayBuilder.with(qiContext).withText(" Grezzo non se lo fece ripetere due volte e si unì alla compagnia. ").build();

        Say sayGrezzo1 = SayBuilder.with(qiContext).withText(" Insieme sarebbero stati più forti, e avrebbero affrontato coloro che li consideravano brutti," +
                " solo perchè invidiosi dei loro poteri magici.").build();

        sayGrezzo6.run();
        sayGrezzo7.run();
        sayGrezzo.run();
        sayGrezzo1.run();


        Say sayFinale = SayBuilder.with(qiContext).withText("\\rspd=85\\ Ora sai davvero tutto sulla storia di Grezzo.").build();
        Say sayFinale2 = SayBuilder.with(qiContext).withText("\\rspd=85\\ Ti metterò alla prova con delle domande di comprensione.").build();

        sayFinale.run();
        sayFinale2.run();

        Intent activity2Intent = new Intent(getApplicationContext(), Grezzodomanda1.class);
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