package com.example.huyng.nutrisnap;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.huyng.nutrisnap.Food.main;

public class MenuImpariamoGiochiamoActivity extends RobotActivity implements RobotLifecycleCallbacks {

    // Store the Animate action.
    private Animate animate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_menu_impariamo_giochiamo);
    }


    //Funzione per il Bottone Impariamo
    public void ButtonImpariamo(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorieSostanze.class);
        startActivity(activity2Intent); //Per andare alla pagina storie sostanze
    }

    //Funzione per il Bottone Giochiamo
    public void ButtonGiochiamo(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla pagina Giochiamo
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
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

        //Say iniziale di Pepper
        Say sayScelta1 = SayBuilder.with(qiContext)
                .withText("Ora puoi scegliere se:").build();

        Say sayScelta2 = SayBuilder.with(qiContext)
                .withText(" Imparare a conoscere il cibo").build();

        Say sayScelta3 = SayBuilder.with(qiContext)
                .withText(" Oppure Giocare insieme").build();

        //Say affermazione Indietro
        Say sayAffermazioneIndietro = SayBuilder.with(qiContext)
                .withText("Ok Torniamo Indietro").build();

        //Say affermazione Ok
        Say sayOkIniziamoImpariamo = SayBuilder.with(qiContext)
                .withText("Ok Impariamo!").build();

        //Say affermazione Ok
        Say sayOkIniziamoGiochiamo = SayBuilder.with(qiContext)
                .withText("Va bene Giochiamo").build();

        //Say affermazione Ripetiamo
        Say sayRipetiamo = SayBuilder.with(qiContext)
                .withText("Ok").build();


        // Create an animation.
        Animation animationPresentazione = AnimationBuilder.with(qiContext) // Create the builder with the context.
                .withResources(R.raw.show_tablet_a003) // Set the animation resource.
                .build(); // Build the animation.

        animate = AnimateBuilder.with(qiContext) // Create the builder with the context.
                .withAnimation(animationPresentazione) // Set the animation.
                .build(); // Build the animate action


// Create the phraseSetLearn Impariamo.
        PhraseSet phraseSetLearn = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Impariamo", "Voglio imparare", "Imparare", "Impariamo insieme") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the PhraseSetPlay Giochiamo.
        PhraseSet phraseSetPlay = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Giochiamo", "Voglio giocare", "Giocare", "Giochiamo insieme") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet


        // Create the PhraseSetBack Indietro.
        PhraseSet phraseSetBack = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Indietro", "Basta", "Stop", "Basta così", "Pepper vai indietro", "Pepper Basta", "Pepper Stop", "Pepper Basta così") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet


        // Create the PhraseSetRepeat Ripeti .
        PhraseSet phraseSetRepeat = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Ripeti", "Ricominciamo", "Ricomincia", "Da capo", "Ciao Pepper Ripeti", "Ciao Pepper Ricominciamo", "Ciao Pepper Ricomincia", "Ciao Pepper, Da capo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet


        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetLearn, phraseSetPlay, phraseSetBack, phraseSetRepeat) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        //Serie di Run del MainActivity
        //Run Animazione Presentazione
        Future<Void> animateFuture = animate.async().run();
        // Set the text to say.
        sayScelta1.run();
        sayScelta2.run();
        sayScelta3.run();
        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetLearn)) {
            //Andare nella pagine storie sostanze
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.tap_display_right_hand_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayOkIniziamoImpariamo.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MenuStorieSostanze.class);
            startActivity(activity2Intent); //Per andare alla pagina avanti

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetPlay)) {
            //Andare nella pagine Giochiamo
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.tap_display_left_hand_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayOkIniziamoGiochiamo.run();
            Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
            startActivity(activity2Intent); //Per andare alla pagina avanti
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetBack)) {
            //Richiamare animazione Negazione e andare di nuovo sulla pagina
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a002).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAffermazioneIndietro.run();
            Intent activity2Intent = new Intent(getApplicationContext(), main.class);
            startActivity(activity2Intent); //Per andare indietro

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetRepeat)) {
            //Richiamare animazione Negazione e andare di nuovo sulla pagina
            Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.coughing_right_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayRipetiamo.run();;
            Intent activity2Intent = new Intent(getApplicationContext(), MenuImpariamoGiochiamoActivity.class);
            startActivity(activity2Intent); //Per ripetere
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