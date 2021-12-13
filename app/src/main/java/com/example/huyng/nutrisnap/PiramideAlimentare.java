package com.example.huyng.nutrisnap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy;
import com.aldebaran.qi.sdk.object.conversation.Say;

public class PiramideAlimentare extends RobotActivity implements RobotLifecycleCallbacks {
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.piramide);

    }


    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Skip
    public void ButtonSkip(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), MenuSostanze.class);
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

        //Serie di Say Spiegazione
        Say sayFibre1 = SayBuilder.with(qiContext).withText(" La piramide alimentare è un modello che descrive un regime alimentare corretto ed equilibrato; rappresenta graficamente l’importanza dei vari alimenti e la frequenza con i quali andrebbero consumati. Conoscerla è utile per prendersi cura del proprio corpo e per il proprio benessere.").build();

        Say sayFibre2 = SayBuilder.with(qiContext).withText(" Oltre all’alimentazione, infatti, vanno aggiunti altri importanti consigli: mantenersi sempre idratati bevendo almeno 2 litri di acqua al giorno, fare attività fisica, mangiare con moderazione per ridurre le calorie e dormire a sufficienza.").build();


        //Say spiegazione
        sayFibre1.run();
        sayFibre2.run();

        Intent activity2Intent = new Intent(getApplicationContext(), MenuSostanze.class);
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
