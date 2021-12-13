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
import com.example.huyng.nutrisnap.SelectActivity;

public class PepperNoncorretto extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView button;
    int turno;
    int puntipepper;
    int puntibambino;
    TextView pepper;
    TextView bambino;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.bambinononcorretto);

        // Animazione fade in per le frecce
        Bundle data3 = getIntent().getExtras();
        turno = data3.getInt("turno");
        System.out.println(turno);
        Bundle data4 = getIntent().getExtras();
        puntipepper = data4.getInt("pepper");
        Bundle data5 = getIntent().getExtras();
        puntibambino = data5.getInt("bambino");
        System.out.println(puntibambino);
        System.out.println(puntipepper);

        bambino = findViewById(R.id.punti17);
        pepper = findViewById(R.id.punti18);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);

    }

    // Inizia il gioco = SecondGame
    public void PlayGameActivity(View view) {
        if(turno ==2) {
            Intent intent1 = new Intent(getApplicationContext(), Turno2.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            startActivity(intent1);
        } else if(turno == 3){
            Intent intent1 = new Intent(getApplicationContext(), Turno3.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            startActivity(intent1);
        } else if(turno ==4){
            Intent intent1 = new Intent(getApplicationContext(), Fine.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            startActivity(intent1);
        }
    }



    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), TurnoBambino.class);
        startActivity(activity2Intent); //Per andare indietro
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
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

        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("Ops , ho sbagliato. Non guadagno punti per questa volta.").build();

        sayVerdotti1.run();
        if(turno ==2) {
            Intent intent1 = new Intent(getApplicationContext(), Turno2.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            startActivity(intent1);
        } else if(turno == 3){
            Intent intent1 = new Intent(getApplicationContext(), Turno3.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
            startActivity(intent1);
        } else if(turno ==4){
            Intent intent1 = new Intent(getApplicationContext(), Fine.class);
            Bundle data4 = new Bundle();
            data4.putInt("pepper",puntipepper);
            Bundle data5 = new Bundle();
            data5.putInt("bambino",puntibambino);
            intent1.putExtras(data4);
            intent1.putExtras(data5);
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