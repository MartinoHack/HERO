package com.example.huyng.nutrisnap.verdotti;

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

public class VerdottiStory extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_verdotti);
    }

    //Funzione per il Bottone Indietro
    public void ButtonBack(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuStorie.class);
        startActivity(activity2Intent); //Per andare alla pagina scelta Impariamo
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
        // The robot focus is gained.

        //Serie di Say Verdotti

        Say sayVerdotti1 = SayBuilder.with(qiContext).withText(" Ora ti racconterò la storia dei Verdotti. ").build();

        Say sayVerdotti2 = SayBuilder.with(qiContext).withText(" I Verdotti sono come il pollice di una mano, piccoli e grassottelli." +
                " Hanno il viso tondo circondato da una chioma folta color smeraldo e in cima alla testa piccole corna color avorio, le orecchie sono rosee a forma di cono." +
                " Sembrano folletti ma sono una razza a parte. Se gli uomini stessero zitti zitti si accorgerebbero della loro esistenza." +
                " Vivono nel mondo di Chimangiacosa, un allegro osto fatto di prati colorati, e hanno scoperto l segreto per vivere a lungo e in perfetta salute." +
                " Coltivano alberi enormi, naturalmente verdi, dal grande fusto e dall'infiorescenza spumosa: i broccoli. ").build();

        Say sayVerdotti3 = SayBuilder.with(qiContext).withText(" Ognuno ha il suo ruolo, come in una grande famiglia: i nonni, saggi e pazienti preparano" +
                " con cura il terreno; i papà, molto forti, pensano alla semina; le mamme si prendono cura delle piantine per farle diventare forti; i più piccoli, ogni mattina," +
                " annaffiano le foglie che nascono intorno ai broccoli; le nonne pensano a cucinare e a scrivere le ricette. Insomma c'è lavoro per tutti!").build();

        sayVerdotti1.run();
        sayVerdotti2.run();
        sayVerdotti3.run();

        Intent activity2Intent = new Intent(getApplicationContext(), Verdotti_attenzione.class);
        startActivity(activity2Intent); //Per andare alla pagina successiva


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