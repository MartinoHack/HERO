package com.example.huyng.nutrisnap.Food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.Second_game.SecondGame01;
import com.example.huyng.nutrisnap.Second_game.SecondGame02;
import com.example.huyng.nutrisnap.SelectActivity;

public class Turno3 extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;
    int turno = 3;
    TextView turnazione;
    int puntipepper;
    int puntibambino;
    TextView pepper;
    TextView bambino;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);


        setContentView(R.layout.turno);
        turnazione = findViewById(R.id.turno);
        turnazione.setText("" + turno);
        Bundle data4 = getIntent().getExtras();
        puntipepper = data4.getInt("pepper");
        Bundle data5 = getIntent().getExtras();
        puntibambino = data5.getInt("bambino");
        System.out.println(puntibambino);
        System.out.println(puntipepper);

        bambino = findViewById(R.id.punti21);
        pepper = findViewById(R.id.punti22);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);

    }

    //Funzione per il Bottone Indietro
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare indietro
    }



    //Funzione per il Bottone Indietro
    public void ButtonPlay(View view) {
        turno ++;
        Intent intent1 = new Intent(getApplicationContext(), TurnoBambino.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data4 = new Bundle();
        data4.putInt("pepper",puntipepper);
        Bundle data5 = new Bundle();
        data5.putInt("bambino",puntibambino);
        intent1.putExtras(data4);
        intent1.putExtras(data5);
        intent1.putExtras(data1);
        startActivity(intent1);
        //Intent activity2Intent = new Intent(getApplicationContext(), TurnoBambino.class);
        //startActivity(activity2Intent); //Per andare indietro
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

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("Turno"+ turno).build();
        Say sayVerdotti2 = SayBuilder.with(qiContext).withText("Ora tocca a te.").build();

        sayVerdotti2.run();
        sayVerdotti1.run();

        // Create the phraseSetGrezzo.
        PhraseSet phraseSetStrada = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Inizia", "Iniziamo") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.


        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetStrada) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetStrada)) {
            turno ++;
            Intent intent1 = new Intent(getApplicationContext(), TurnoBambino.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            intent1.putExtras(data1);
            startActivity(intent1);

            //Richiamare animazione affermazione e andare avanti
            //Intent activity2Intent = new Intent(getApplicationContext(), TurnoBambino.class);
            //startActivity(activity2Intent); //Per andare alla pagina Strada

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