package com.example.huyng.nutrisnap.carboidrati;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuSostanze;
import com.example.huyng.nutrisnap.MenuStorieSostanze;
import com.example.huyng.nutrisnap.R;

public class Carboidrati4 extends RobotActivity implements RobotLifecycleCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_carboidrati4);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), Carboidrati3.class);
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
                .withText(" I carboidrati sani possono essere pane, riso e pasta.").build();
        Say sayCarbo2 = SayBuilder.with(qiContext)
                .withText(" Quelli da evitare sono i dolci e le caramelle.").build();

        //Say spiegazione
        sayCarbo.run();
        sayCarbo2.run();


        Say sayFinale = SayBuilder.with(qiContext).withText(" Ora sai davvero tutto su come " +
                " sono fatti i carboidrati.").build();
        Say sayFinale2 = SayBuilder.with(qiContext).withText(" Vuoi imparare altro o vuoi ripetere?").build();

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

// Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetBack, phraseSetRepeat) // Set the PhraseSets to listen to.
                .build(); // Build the listen action

// Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetBack)) {
            //Richiamare animazione Affermazione e andare indietro
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a002).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAffermazioneIndietro.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MenuStorieSostanze.class);
            startActivity(activity2Intent); //Per ritornare alla pagina scelta Impariamo
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetRepeat)) {
            //Richiamare animazione Coughing e andare di nuovo sulla pagina
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.coughing_right_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), Carboidrati.class);
            startActivity(activity2Intent); //Per ripetere tutta la spiegazione
        }


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
