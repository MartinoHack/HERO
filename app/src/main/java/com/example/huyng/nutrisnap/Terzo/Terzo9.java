package com.example.huyng.nutrisnap.Terzo;

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
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.R;

public class Terzo9 extends RobotActivity implements RobotLifecycleCallbacks {
    // Store the Animate action.
    private Animate animate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.terzo9);
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
                .withText("\\rspd=95\\Sono buona, sono sana.").build();

        Say sayScelta2 = SayBuilder.with(qiContext)
                .withText("\\rspd=95\\ Sono io, la?").build();


        // Create an animation.
        Animation animationPresentazione = AnimationBuilder.with(qiContext) // Create the builder with the context.
                .withResources(R.raw.show_tablet_a003) // Set the animation resource.
                .build(); // Build the animation.

        animate = AnimateBuilder.with(qiContext) // Create the builder with the context.
                .withAnimation(animationPresentazione) // Set the animation.
                .build(); // Build the animate action


// Create the phraseSetLearn Talpa.
        PhraseSet phraseSetLearn = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Melanzana", "La melanzana") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.


        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetLearn) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        //Serie di Run del MainActivity
        //Run Animazione Presentazione
        Future<Void> animateFuture = animate.async().run();
        // Set the text to say.
        sayScelta1.run();
        sayScelta2.run();
        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetLearn)) {
            //Andare nella pagine storie sostanze

            Intent activity2Intent = new Intent(getApplicationContext(), Terzo10.class);
            startActivity(activity2Intent); //Per andare alla pagina avanti
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
