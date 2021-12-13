package com.example.huyng.nutrisnap.fata;

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

public class FataStory extends RobotActivity implements RobotLifecycleCallbacks {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        QiSDK.register(this, this);

        //Modifica speech bar in Immersiva
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE);

        setContentView(R.layout.activity_fata);

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

        //Serie di Say Fata

        Say sayFata1 = SayBuilder.with(qiContext).withText(" Ora ti racconterò la storia della fata Smemorina. ").build();

        Say sayFata2 = SayBuilder.with(qiContext).withText(" Nel paese delle magiche erbette viveva la fata Smemorina. Dentro un pentolone, nella sua cucina, bolliva di tutto," +
                " solo che dimenticava quasi sempre cosa metteva nelle pozioni e sopratutto come utilizzarle. La fata era una grande pasticciona, infatti una volta aveva dato ad una ragazza" +
                " una pozione d'amore e le era cresciuta la coda! Poichè i suoi clienti erano scontenti, Smemorina decise di assumere un aiutante, il signor Talpa." +
                " Il signor Talpa non vedeva bene perciò usava dei grossi occhiali con la montatura di carapace. donatogli da una tartaruga che cambiava casa, in compenso aveva un'ottima memoria e buoni muscoli." +
                " Scavava grandi buche per piantare erbe magiche, mettendo dei cartelli per indicare il nome e a cosa servissero. Da quando la aiutava gli abitanti del paese erano soddisfatti." +
                " Una mattina in cui era uscita a far provviste di semi, la fata andò nell'orto e colse dei rametti da una pianta con i fiorellini viola, li mise in pentola e preparò una pozione che profumò tutta " +
                " la casa e che diede a chi le faceva visita. Per primo arrivò un omone con la testa pelata, era caduto dalla scala e la caviglia" +
                " era rimasta impigliata nei gradini, perciò sentiva un gran male. La fata gli offrì un bicchiere di pozione e subito il testone si riempì di capelli." +
                " L'uomo se ne andò soddisfatto, dimenticando persino il dolore. Poi fu il turno di una vecchietta con le ossa scricchiolanti, diceva un fastidioso raffreddore." +
                " Anche a lei Smemorina fece bere il decotto magico e di li a poco la vecchietta si raddrizzò e se ne andò saltellando. ").build();

        //Say spiegazione Fata
        sayFata1.run();
        sayFata2.run();

        Intent activity2Intent = new Intent(getApplicationContext(), Fata_attenzione.class);
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