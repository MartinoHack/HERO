package com.example.huyng.nutrisnap.Food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.Second_game.SecondGame01;
import com.example.huyng.nutrisnap.Second_game.SecondGame02;
import com.example.huyng.nutrisnap.SelectActivity;


public class TurnoBambino extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView mArrow2;
    int turno;
    int puntipepper;
    int puntibambino;
    TextView pepper;
    TextView bambino;


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QiSDK.register(this, this);
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.turnobambino);

        Bundle data1 = getIntent().getExtras();
        turno = data1.getInt("turno");
        System.out.println(turno);
        Bundle data2 = getIntent().getExtras();
        puntipepper = data2.getInt("pepper");
        Bundle data3 = getIntent().getExtras();
        puntibambino = data3.getInt("bambino");

        bambino = findViewById(R.id.punti9);
        pepper = findViewById(R.id.punti10);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
    }

    // Torna alla scelta del gioco = SelectActivity
    public void ButtonVerde(View view) {
        Intent intent1 = new Intent(getApplicationContext(), SemaforoVerde.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data2 = new Bundle();
        data2.putInt("pepper",puntipepper);
        Bundle data3 = new Bundle();
        data3.putInt("bambino",puntibambino);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    // Inizia il gioco = SecondGame
    public void ButtonRosso(View view) {
        Intent intent1 = new Intent(getApplicationContext(), SemaforoRosso.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data2 = new Bundle();
        data2.putInt("pepper",puntipepper);
        Bundle data3 = new Bundle();
        data3.putInt("bambino",puntibambino);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        intent1.putExtras(data1);
        startActivity(intent1);
    }

    // Inizia il gioco = SecondGame
    public void ButtonGiallo(View view) {
        Intent intent1 = new Intent(getApplicationContext(), SemaforoGiallo.class);
        Bundle data1 = new Bundle();
        data1.putInt("turno",turno);
        Bundle data2 = new Bundle();
        data2.putInt("pepper",puntipepper);
        Bundle data3 = new Bundle();
        data3.putInt("bambino",puntibambino);
        intent1.putExtras(data2);
        intent1.putExtras(data3);
        intent1.putExtras(data1);
        startActivity(intent1);
    }



    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
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

        // Create frase1
        Say sayFrase1 = SayBuilder.with(qiContext)
                .withText("è il tuo turno. Ora che hai scelto l'alimento, Quale luce del semaforo vuoi accendere?").build();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        // Esegui frase 1, 2, 3, 4, 5
        sayFrase1.run();
        // Esegui animazione synchronously
        animate.run();



        // SALTA LISTEN
        //Intent activityXIntent = new Intent(getApplicationContext(), SecondGameResult.class);
        //startActivity(activityXIntent);

        // Risposte che riconosce Pepper
        PhraseSet phraseSetVerde = PhraseSetBuilder.with(qiContext)
                .withTexts("Verde").build();

        PhraseSet phraseSetGiallo = PhraseSetBuilder.with(qiContext)
                .withTexts("Giallo").build();
        PhraseSet phraseSetRosso = PhraseSetBuilder.with(qiContext)
                .withTexts("Rosso").build();

        // Pepper ascolta le risposte
        Listen listen = ListenBuilder.with(qiContext).withPhraseSets(phraseSetVerde, phraseSetGiallo ,phraseSetRosso).build();
        ListenResult listenResult = listen.run();
        PhraseSet matchedPhraseSet = listenResult.getMatchedPhraseSet();

        if (PhraseSetUtil.equals(matchedPhraseSet, phraseSetVerde)) {
            Intent intent1 = new Intent(getApplicationContext(), SemaforoVerde.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data2 = new Bundle();
            data2.putInt("pepper",puntipepper);
            Bundle data3 = new Bundle();
            data3.putInt("bambino",puntibambino);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            intent1.putExtras(data1);
            startActivity(intent1);

        } else if(PhraseSetUtil.equals(matchedPhraseSet, phraseSetGiallo)){
            Intent intent1 = new Intent(getApplicationContext(), SemaforoGiallo.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data2 = new Bundle();
            data2.putInt("pepper",puntipepper);
            Bundle data3 = new Bundle();
            data3.putInt("bambino",puntibambino);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            intent1.putExtras(data1);
            startActivity(intent1);
        }else {
            Intent intent1 = new Intent(getApplicationContext(), SemaforoRosso.class);
            Bundle data1 = new Bundle();
            data1.putInt("turno",turno);
            Bundle data2 = new Bundle();
            data2.putInt("pepper",puntipepper);
            Bundle data3 = new Bundle();
            data3.putInt("bambino",puntibambino);
            intent1.putExtras(data2);
            intent1.putExtras(data3);
            intent1.putExtras(data1);
            startActivity(intent1);
        }
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }

}