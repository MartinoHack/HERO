package com.example.huyng.nutrisnap.Food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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


public class Tutorial extends RobotActivity implements RobotLifecycleCallbacks {


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);

        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.tutorial);


    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone back
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), primo.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }





    @Override
    protected void onDestroy() {
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // Crea animazione mostra tablet
        com.aldebaran.qi.sdk.object.actuation.Animation showTablet2 = AnimationBuilder.with(qiContext)
                .withResources(R.raw.show_tablet_a006).build();
        // Monta animazione
        Animate animate = AnimateBuilder.with(qiContext)
                .withAnimation(showTablet2).build();
        Say sayGrezzo = SayBuilder.with(qiContext)
                .withText(" Ecco a te le regole del gioco.").build();


        Say sayGrezzo3 = SayBuilder.with(qiContext)
                .withText(" Giocheremo a turni. Lo scopo del gioco sarà quello di indovinare il colore del semaforo appartenente al cibo., " +
                        " Le luci indicano la frequenza, con la quale devi assumere quell'alimento, quindi, se è sano o meno." +
                        " Luce rossa indica che il cibo va mangiato occasionalmente, luce gialla qualche volta, luce verde spesso.").build();

        Say sayGrezzo4 = SayBuilder.with(qiContext)
                .withText(" Quando toccherà a te devi scegliere un cibo o una carta rappresentante il cibo e dirmi quale luce del semaforo vuoi accendere. " +
                        " Quando toccherà a me dovrai mostrarmi il cibo o la carta, e io sceglierò quale luce accendere.").build();

        Say sayGrezzo5 = SayBuilder.with(qiContext)
                .withText(" Un giudice dirà se la nostra risposta sarà corretta o no e se indovineremo guadagneremo punti.").build();


        //Say spiegazione Grezzo
        sayGrezzo.run();
        sayGrezzo3.run();
        sayGrezzo4.run();
        sayGrezzo5.run();

        // Create frase2
        Say sayFrase3 = SayBuilder.with(qiContext)
                .withText("Sei pronto? Oppure vuoi riascoltare le regole?").build();



        // Esegui frase 1, 2, 3, 4, 5
        sayFrase3.run();
        // Esegui animazione synchronously
        animate.run();



        // SALTA LISTEN
        //Intent activityXIntent = new Intent(getApplicationContext(), SecondGameResult.class);
        //startActivity(activityXIntent);

        // Risposte che riconosce Pepper
        PhraseSet phraseSetYes = PhraseSetBuilder.with(qiContext)
                .withTexts("Si", "Ok", "Certo", "Sono pronto", "Si lo sono", "Yeah").build();

        PhraseSet phraseSetNo = PhraseSetBuilder.with(qiContext)
                .withTexts("No", "Non lo sono", "Voglio riascoltare le regole").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetYes, phraseSetNo).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)) {
            Intent activityYesIntent = new Intent(getApplicationContext(), Turno.class);
            startActivity(activityYesIntent);
        } else {
            Intent activityNoIntent = new Intent(getApplicationContext(), Tutorial.class);
            startActivity(activityNoIntent);
        }
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }

}