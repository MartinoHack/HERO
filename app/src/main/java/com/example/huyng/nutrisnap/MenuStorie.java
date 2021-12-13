package com.example.huyng.nutrisnap;

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
import com.example.huyng.nutrisnap.Food.main;
import com.example.huyng.nutrisnap.fata.FataStory;
import com.example.huyng.nutrisnap.grezzo.GrezzoStory;
import com.example.huyng.nutrisnap.verdotti.VerdottiStory;

public class MenuStorie extends RobotActivity implements RobotLifecycleCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_menu_storie);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorieSostanze.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), main.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Conoscere la Strada
    public void ButtonGrezzo(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), GrezzoStory.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
    }

    //Funzione per il Bottone Il Pedone
    public void ButtonFata(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), FataStory.class);
        startActivity(activity2Intent); //Per andare alla seconda pagina
    }

    //Funzione per il Bottone Gli Animali
    public void ButtonVerdotti(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), VerdottiStory.class);
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

        //Say su scelta cosa Imparare
        Say sayImparare = SayBuilder.with(qiContext)
                .withText(" Quale storia vuoi ascoltare?").build();

        //Say su scelta Andiamo
        Say sayAndiamo = SayBuilder.with(qiContext)
                .withText("OK!").build();

        Say sayAndiamo2 = SayBuilder.with(qiContext)
                .withText("Andiamo!").build();

        Say sayAndiamo3 = SayBuilder.with(qiContext)
                .withText("Va bene!").build();


        //Say affermazione Indietro
        Say sayAffermazioneIndietro = SayBuilder.with(qiContext)
                .withText("Ok Torniamo Indietro").build();

        //Say Ripetiamo
        Say sayRipetiamo = SayBuilder.with(qiContext)
                .withText("Ok").build();


        // Create the phraseSetGrezzo.
        PhraseSet phraseSetStrada = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Grezzo il chicco coraggioso", "La storia di Grezzo", "La prima storia") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the phraseSetFibre.
        PhraseSet phraseSetPedone = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("La fata smemorina", "La storia della fata smemorina", "Fata smemorina", "La seconda storia") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the phraseSetProteine.
        PhraseSet phraseSetAnimali = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Il mondo dei verdotti", "La storia del mondo dei verdotti", "La terza storia") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.


        // Create the PhraseSetBack Indietro.
        PhraseSet phraseSetBack = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Indietro", "Basta", "Stop", "Basta così", "Pepper vai indietro",
                        "Pepper Basta", "Pepper Stop", "Pepper Basta così") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSe

        // Create the PhraseSetRepeat Ripeti .
        PhraseSet phraseSetRepeat = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Ripeti", "Ricominciamo", "Ricomincia", "Da capo", "Pepper Ripeti",
                        "Pepper Ricominciamo", "Pepper Ricomincia", "Pepper, Da capo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet

        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetStrada, phraseSetPedone, phraseSetAnimali,
                        phraseSetBack, phraseSetRepeat) // Set the PhraseSets to listen to.
                .build(); // Build the listen action

        // Set the text to say.
        sayImparare.run();

        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetStrada)) {
            //Richiamare animazione affermazione e andare avanti
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAndiamo.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), GrezzoStory.class);
            startActivity(activity2Intent); //Per andare alla pagina Strada

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetPedone)) {
            //Richiamare animazione affermazione e andare avanti
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a002).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAndiamo2.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), FataStory.class);
            startActivity(activity2Intent); //Per andare alla pagina Pedone

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetAnimali)) {
            //Richiamare animazione affermazione e andare avanti
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a003).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAndiamo3.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), VerdottiStory.class);
            startActivity(activity2Intent); //Per andare alla pagina Animali
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetBack)) {
            //Richiamare animazione affermazione e andare indietro
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.affirmation_a002).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayAffermazioneIndietro.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MenuStorieSostanze.class);
            startActivity(activity2Intent); //Per andare indietro
        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetRepeat)) {
            //Richiamare animazione Negazione e andare di nuovo sulla pagina
            com.aldebaran.qi.sdk.object.actuation.Animation correctAnswer = AnimationBuilder.with(qiContext)
                    .withResources(R.raw.coughing_right_b001).build();
            Animate animateCorrect = AnimateBuilder.with(qiContext)
                    .withAnimation(correctAnswer).build();
            sayRipetiamo.run();
            animateCorrect.run();
            Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
            startActivity(activity2Intent); //Per ripetere la pagina
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
