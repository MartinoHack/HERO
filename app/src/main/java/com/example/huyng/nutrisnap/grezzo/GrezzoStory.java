package com.example.huyng.nutrisnap.grezzo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.MainActivity;
import com.example.huyng.nutrisnap.MenuStorie;
import com.example.huyng.nutrisnap.R;


public class GrezzoStory extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_grezzo);
    }


    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
        startActivity(activity2Intent); //Per andare alla pagina Impariamo
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Stop
    public void ButtonStop(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
        startActivity(activity2Intent); //Per stoppare la pagina
    }


    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {

        //Serie di Say Spiegazione Grezzo
        Say sayGrezzo = SayBuilder.with(qiContext)
                .withText(" Ora ti racconterò la storia di Grezzo, il chicco coraggioso.").build();
        Say sayGrezzo2 = SayBuilder.with(qiContext)
                .withText(" Ti piacerà un sacco.").build();

        Say sayGrezzo3 = SayBuilder.with(qiContext)
                .withText(" C'era una volta una grande risaia, dove abitavano tante famiglie di chicchi di riso, arrampicate nelle loro casette di foglie verdi." +
                        " E ci viveva anche Grezzo, un chicco di riso che aveva un colore più scuro degli altri." +
                        "Un giorno gli uomini che raccoglievano il riso presero Grezzo e lo portarono in un grande capanno fatto di canne di bambù.").build();

        Say sayGrezzo4 = SayBuilder.with(qiContext)
                .withText(" Qui il chicco di riso piccolo e scuro finì per sbaglio in una grande vasca di chicchi bianchi. " +
                        " Perchè siete nudi? Chiese Grezzo.").build();

        Say sayGrezzo5 = SayBuilder.with(qiContext)
                .withText(" Bianco, rispose: Quando ci hanno portato qui anche noi avevamo il nostro vestito marroncino, ed eravamo integrali, proprio come te..." +
                        " Ma abbiamo visto che gli altri erano tutti chiari, e ci sono sembrati molto più raffinati.").build();


        //Say spiegazione Grezzo
        sayGrezzo.run();
        sayGrezzo2.run();
        sayGrezzo3.run();
        sayGrezzo4.run();
        sayGrezzo5.run();


        Intent activity2Intent = new Intent(getApplicationContext(), Grezzo_attenzione.class);
        startActivity(activity2Intent); //Per andare avanti
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