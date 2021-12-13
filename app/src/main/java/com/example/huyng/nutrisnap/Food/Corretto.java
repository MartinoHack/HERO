package com.example.huyng.nutrisnap.Food;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.SelectActivity;
import com.example.huyng.nutrisnap.database.Food;

public class Corretto extends RobotActivity implements RobotLifecycleCallbacks {
    private ImageView button;
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

        setContentView(R.layout.corretto);



//        String datopassato = getIntent().getExtras().getString("dato");
        //      System.out.println(datopassato);
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

        bambino = findViewById(R.id.punti16);
        pepper = findViewById(R.id.punti15);
        bambino.setText("" + puntibambino);
        pepper.setText("" + puntipepper);
        System.out.println(turno);
        System.out.println(name);
        System.out.println(color);
    }

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
    

    //Funzione per il Bottone Riprova
    public void ButtonRiprova(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), main.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }

    //Funzione per il Bottone Home
    public void ButtonHome(View view) {
        Intent activity2Intent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivity(activity2Intent); //Per andare alla prima pagina
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.

        //Say su scelta cosa Imparare
        Say sayImparare = SayBuilder.with(qiContext)
                .withText("Parliamo dell'alimento" + name ).build();
        Say sayRosso = SayBuilder.with(qiContext)
                .withText("Si tratta di un alimento che puoi consumare occasionalmente, senza esagerare, Miraccomando").build();
        Say sayGiallo = SayBuilder.with(qiContext)
                .withText("Si tratta di un alimento che puoi consumare qualche volta. ").build();
        Say sayVerde = SayBuilder.with(qiContext)
                .withText("Si tratta di un alimento sano che puoi consumare spesso, ti farà bene.").build();


        // Set the text to say.
        sayImparare.run();
        if(color.equalsIgnoreCase("verde")){
            sayVerde.run();
        } else if(color.equalsIgnoreCase("giallo")){
            sayGiallo.run();
        } else{
            sayRosso.run();
        }

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
