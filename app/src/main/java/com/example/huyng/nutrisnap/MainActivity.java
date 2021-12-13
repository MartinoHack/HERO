package com.example.huyng.nutrisnap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import com.aldebaran.qi.Future;
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
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    // Store the Animate action.
    private Animate animate;


    @SuppressLint("SoonBlockedPrivateApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_main);
    }

    //Funzione per il Bottone Iniziamo
    public void ButtonIniziamo(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuImpariamoGiochiamoActivity.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
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

        // Crea animazione saluto
        Animation saluto = AnimationBuilder.with(qiContext) // Create the builder with the context
                .withResources(R.raw.hello_a010) // Set the action to say
                .build(); // Build the say action

        // Monta animazione
        Animate animate = AnimateBuilder.with(qiContext)
                .withAnimation(saluto)
                .build();


/**
 // Create a phrase.
 Phrase phrase = new Phrase("\\rspd=70\\ Questa frase è molto lenta \\rspd=200\\ Questa frase è molto lenta");

 // Build the action.
 Say say = SayBuilder.with(qiContext)
 .withPhrase(phrase)
 .build();

 // Run the action synchronously.
 say.run();
 **/

// \rspd=50\ rallenta la velocità di pepper

        //Say iniziale di Pepper
        Say sayhello = SayBuilder.with(qiContext)
                .withText("Ciao amici,  sono  Pepperr. Oggi Parleremo di cibo. Iniziamo?").build();


        Say sayOk = SayBuilder.with(qiContext)
                .withText("Ok Iniziamo").build();


        // Create the phraseSetYes Iniziamo.
        PhraseSet phraseSetYes = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Si", "OK", "Andiamo", "Iniziamo", "Inizia", "Ciao Pepper iniziamo", "Ciao Pepper Inizia", "Ciao Pepper Si", "Ciao Pepper andiamo", "Ciao Pepper ok") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the PhraseSetNo Non Iniziamo.
        PhraseSet phraseSetNo = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("No", "Non iniziamo", "Non voglio", "Ciao Pepper no", "Ciao Pepper non voglio", "Ciao Pepper, non iniziamo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet


        // Create the PhraseSetRepeat Ripeti .
        PhraseSet phraseSetRepeat = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Ripeti", "Ricominciamo", "Ricomincia", "Da capo", "Ciao Pepper Ripeti", "Ciao Pepper Ricominciamo", "Ciao Pepper Ricomincia", "Ciao Pepper, Da capo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet

        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetYes, phraseSetNo, phraseSetRepeat) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        //Serie di Run del MainActivity
        //Run Animazione Presentazione
        Future<Void> animateFuture = animate.async().run();
        // Set the text to say.
        sayhello.run();

        // Esegui saluto synchronously
        animate.run();


        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)) {
            //Richiamare animazione affermazione e andare avanti
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_right_hand_a001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayOk.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MenuImpariamoGiochiamoActivity.class);
            startActivity(activity2Intent); //Per andare alla seconda pagina

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)) {
            //Richiamare animazione Negazione e andare di nuovo sulla pagina
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.negation_both_hands_a003).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            animateCorrect.run();
            sayOk.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent); //Per ritornare alla prima pagina
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetRepeat)) {
            //Richiamare animazione Coughing e andare di nuovo sulla pagina
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.coughing_right_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent); //Per ripetere la prima pagina

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