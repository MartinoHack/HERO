package com.example.huyng.nutrisnap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.huyng.nutrisnap.Food.main;
import com.example.huyng.nutrisnap.Food.primo;
import com.example.huyng.nutrisnap.Second_game.TutorialSecondGame;
import com.example.huyng.nutrisnap.Terzo.Tutorialterzogioco;
import com.example.huyng.nutrisnap.quiz.TutorialQuiz;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context context;
    private LayoutInflater layoutInflater;
    private final Integer[] images = {R.drawable.indovina_chi, R.drawable.intruso, R.drawable.scatto,R.drawable.ssd};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpager_layout, null);
        ImageView imageView = view.findViewById(R.id.game1);
        imageView.setImageResource(images[position]);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go;

                // Se clicchi sull'immagine vai alla relativa attività
                if (position == 0) {
                    go = new Intent(view.getContext(), Tutorialterzogioco.class);
                    view.getContext().startActivity(go);
                } else if (position == 1) {
                    go = new Intent(view.getContext(), TutorialSecondGame.class);
                    view.getContext().startActivity(go);
                } else if (position == 2) {
                    go = new Intent(view.getContext(), primo.class);
                    view.getContext().startActivity(go);
                }else if (position == 3) {
                    go = new Intent(view.getContext(), TutorialQuiz.class);
                    view.getContext().startActivity(go);
                }

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
