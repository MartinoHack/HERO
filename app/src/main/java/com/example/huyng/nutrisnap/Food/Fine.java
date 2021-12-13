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
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;


public class Fine extends RobotActivity implements RobotLifecycleCallbacks {
    private ImageView button;
    private ImageView button1;
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

        setContentView(R.layout.fine);

        Bundle data2 = getIntent().getExtras();
        puntipepper = data2.getInt("pepper");
        Bundle data3 = getIntent().getExtras();
        puntibambino = data3.getInt("bambino");

        bambino = findViewById(R.id.punti25);
        pepper = findViewById(R.id.punti24);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
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
                .withText("Ben fatto, questa volta mi hai battuto. Complimenti!!").build();

        // Create frase2
        Say sayFrase2 = SayBuilder.with(qiContext)
                .withText("Abbiamo pareggiato!! Complimenti ad entrambi!!!").build();

        // Create frase2
        Say sayFrase3 = SayBuilder.with(qiContext)
                .withText("Mi dispiace. Questa volta ho vinto io. La prossiva molta sarai più preparato e potrai battermi.").build();

        // Create frase2
        Say sayFrase4 = SayBuilder.with(qiContext)
                .withText("Premi il pulsante home per tornare alla lista dei giochi!").build();



        if(puntibambino > puntipepper){
            sayFrase1.run();
            System.out.println("bambino");
        }else if(puntibambino == puntipepper){
            sayFrase2.run();
            System.out.println("pareggio");
        }else if(puntibambino < puntipepper){
            sayFrase3.run();
            System.out.println("pepper");
        }

         sayFrase4.run();


    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }

}