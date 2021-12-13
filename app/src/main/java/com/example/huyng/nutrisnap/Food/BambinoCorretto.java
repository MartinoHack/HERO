package com.example.huyng.nutrisnap.Food;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Listen;
import com.aldebaran.qi.sdk.object.conversation.ListenResult;
import com.aldebaran.qi.sdk.object.conversation.PhraseSet;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.util.PhraseSetUtil;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;
import com.example.huyng.nutrisnap.quiz.Quiz1;

public class BambinoCorretto extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView button;
    MediaPlayer end;
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

        setContentView(R.layout.bambinocorretto);
        Bundle data1 = getIntent().getExtras();
        turno = data1.getInt("turno");
        Bundle data2 = getIntent().getExtras();
        puntipepper = data2.getInt("pepper");
        Bundle data3 = getIntent().getExtras();
        puntibambino = data3.getInt("bambino");
        System.out.println(puntibambino);
        System.out.println(puntipepper);

        bambino = findViewById(R.id.punti13);
        pepper = findViewById(R.id.punti14);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
        // Avvia suono applausi
        end = MediaPlayer.create(this, R.raw.correct);
        end.setVolume(1.5f, 1.5f);
        end.start();
        System.out.println(turno);

    }

    // Inizia il gioco = SecondGame
    public void PlayGameActivity(View view) {
        Intent intent1 = new Intent(getApplicationContext(), main.class);
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

    //Funzione per il Bottone Indietro
    public void ButtonSi(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), main.class);
        startActivity(activity2Intent); //Per andare indietro
    }

    //Funzione per il Bottone Home
    public void ButtonNO(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), main.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }



    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }
    // Pulisci memoria player
    protected void onStop() {
        super.onStop();
        if (end != null) {
            end.stop();
            end.release();
            end = null;
        }
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
        // Crea animazione vittoria
        Animation vittoria = AnimationBuilder.with(qiContext)
                .withResources(R.raw.nicereaction_a001)
                .build();

        // Monta animazione
        Animate animateFinal = AnimateBuilder.with(qiContext)
                .withAnimation(vittoria)
                .build();

        //Serie di Say Verdotti
// Esegui animazione synchronously
        animateFinal.run();
        Say sayVerdotti1 = SayBuilder.with(qiContext).withText("Ben fatto, guadagni un punto.").build();
        Say sayVerdotti2 = SayBuilder.with(qiContext).withText("Ora tocca a me.").build();

        sayVerdotti1.run();
        sayVerdotti2.run();
        Intent intent1 = new Intent(getApplicationContext(), main.class);
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

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}