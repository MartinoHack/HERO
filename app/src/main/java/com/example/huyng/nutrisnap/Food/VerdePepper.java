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
import com.example.huyng.nutrisnap.SelectActivity;

public class VerdePepper extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;
    String name;
    String color;
    int turno;
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

        setContentView(R.layout.semaforoverde);
        Bundle data1 = getIntent().getExtras();
        name = data1.getString("dato");
        Bundle data2 = getIntent().getExtras();
        color = data2.getString("colore");
        Bundle data3 = getIntent().getExtras();
        turno = data3.getInt("turno");
        Bundle data4 = getIntent().getExtras();
        puntipepper = data4.getInt("pepper");
        Bundle data5 = getIntent().getExtras();
        puntibambino = data5.getInt("bambino");
        System.out.println(puntibambino);
        System.out.println(puntipepper);

        bambino = findViewById(R.id.punti9);
        pepper = findViewById(R.id.punti10);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
    }

    //Funzione per il Bottone Indietro
    public void ButtonSi(View view) {
        puntipepper++;

        Intent intent1 = new Intent(getApplicationContext(), PepperCorretto.class);
        Bundle data1 = new Bundle();
        data1.putString("dato",name);
        Bundle data2 = new Bundle();
        data2.putString("colore",color);
        Bundle data3 = new Bundle();
        data3.putInt("turno",turno);
        Bundle data4 = new Bundle();
        data4.putInt("pepper",puntipepper);
        Bundle data5 = new Bundle();
        data5.putInt("bambino",puntibambino);
        intent1.putExtras(data4);
        intent1.putExtras(data5);
        intent1.putExtras(data1);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        startActivity(intent1);
    }

    //Funzione per il Bottone Home
    public void ButtonNO(View view) {
        Intent intent1 = new Intent(getApplicationContext(), PepperNoncorretto.class);
        Bundle data3 = new Bundle();
        data3.putInt("turno",turno);
        Bundle data4 = new Bundle();
        data4.putInt("pepper",puntipepper);
        Bundle data5 = new Bundle();
        data5.putInt("bambino",puntibambino);
        intent1.putExtras(data4);
        intent1.putExtras(data5);
        intent1.putExtras(data3);
        startActivity(intent1);
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

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("Decido di accendere la luce verde. Giudice è corretto?").build();

        sayVerdotti1.run();

        // Create the phraseSetGrezzo.
        PhraseSet phraseSetStrada = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("Si", "E' corretto") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.

        // Create the phraseSetFibre.
        PhraseSet phraseSetPedone = PhraseSetBuilder.with(qiContext) // Create the builder using the QiContext.
                .withTexts("NO","Non è corretto") // Add the phrases Pepper will listen to.
                .build(); // Build the PhraseSet.



        // Create a new listen action.
        Listen listen = ListenBuilder.with(qiContext) // Create the builder with the QiContext.
                .withPhraseSets(phraseSetStrada, phraseSetPedone) // Set the PhraseSets to listen to.
                .build(); // Build the listen action


        // Run the listen action and get the result.
        ListenResult listenResult = listen.run();
        // Identify the matched phrase set.
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetStrada)) {
            //Richiamare animazione affermazione e andare avanti
            puntipepper++;
            Intent intent1 = new Intent(getApplicationContext(), PepperCorretto.class);
            Bundle data1 = new Bundle();
            data1.putString("dato",name);
            Bundle data2 = new Bundle();
            data2.putString("colore",color);
            Bundle data3 = new Bundle();
            data3.putInt("turno",turno);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            intent1.putExtras(data1);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            startActivity(intent1);

        } else if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetPedone)) {
            Intent intent1 = new Intent(getApplicationContext(), PepperNoncorretto.class);
            Bundle data3 = new Bundle();
            data3.putInt("turno",turno);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            intent1.putExtras(data3);
            startActivity(intent1);

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