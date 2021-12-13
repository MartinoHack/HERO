package com.example.huyng.nutrisnap.verdotti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ListenBuilder;
import com.aldebaran.qi.sdk.builder.PhraseSetBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.fata.FataStory2;

public class Verdotti_attenzione extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_attenzione);

    }

    //Funzione per il Bottone Impariamo
    public void ButtonSi(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), VerdottiStory2.class);
        startActivity(activity2Intent); //Per andare alla pagina storie sostanze
    }

    //Funzione per il Bottone Giochiamo
    public void ButtonNo(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
        startActivity(activity2Intent); //Per andare alla pagina Giochiamo
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

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText(" Ho ancora la tua attenzione? ").build();

        sayVerdotti1.run();


        // Create the phraseSetYes Iniziamo.
        PhraseSet phraseSetYes = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Si") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the PhraseSetNo Non Iniziamo.
        PhraseSet phraseSetNo = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("No") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet
        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetYes, phraseSetNo) // Set the PhraseSets to listen to.
                .build(); // Build the listen action

        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetYes)) {
            Intent activity2Intent = new Intent(getApplicationContext(), VerdottiStory2.class);
            startActivity(activity2Intent); //Per andare alla seconda pagina

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetNo)) {
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent); //Per ritornare alla prima pagina
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