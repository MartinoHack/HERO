package com.example.huyng.nutrisnap.Food;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.example.huyng.nutrisnap.R;
import com.example.huyng.nutrisnap.Second_game.SecondGame02;
import com.example.huyng.nutrisnap.database.Food;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RobotLifecycleCallbacks{
    private static final int HEADER_VIEW = 1;
    private static final int LOADING_VIEW = 2;

    private static Activity context;
    //    private AddFoodDialogFragment dialog;
    Bitmap headerBitmap;
    Boolean showLoading;
    List<Food> foodInfos;

    RecyclerAdapter(Activity context, Bitmap headerBitmap, Boolean showLoading, List<Food> foodInfos) {
        RecyclerAdapter.context = context;
        this.headerBitmap = headerBitmap;
        this.showLoading = showLoading;
        this.foodInfos = foodInfos;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header, parent, false);
            HeaderHolder holder = new HeaderHolder(v);
            return holder;
        } else if (viewType == LOADING_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_loading, parent, false);
            LoadingHolder holder = new LoadingHolder(v);
            return holder;
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent, false);
            FoodInfoHolder holder = new FoodInfoHolder(v);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        try {
            if (holder instanceof HeaderHolder) {
                HeaderHolder vh = (HeaderHolder) holder;
                Log.i(TAG, headerBitmap.toString());
                vh.headerPhoto.setImageBitmap(headerBitmap);

            } else if (holder instanceof LoadingHolder) {
                LoadingHolder vh = (LoadingHolder) holder;
                //vh.loadingPanel.setVisibility();
            } else {
                FoodInfoHolder vh = (FoodInfoHolder) holder;
                final Food foodInfo = foodInfos.get(i - 2);

                // Display food information
                vh.foodName.setText(foodInfo.getName());
                vh.foodCal.setText("Calorie: " + foodInfo.getCalories());
                vh.foodCarb.setText("Carboidrati: " + foodInfo.getCarb() + " g");
                vh.foodProtein.setText("Proteine: " + foodInfo.getProtein() + " g");
                vh.foodFat.setText("Grassi: " + foodInfo.getFat() + " g");
                String serving = "Quantità: " + foodInfo.getServing() + " g";
                if (!foodInfo.getUnit().equals(""))
                    serving += " (per " + foodInfo.getUnit() + ")";
                vh.foodServing.setText(serving);
                String pippo = vh.foodName.getText().toString();
                //System.out.println(pippo);


            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }

        //holder.foodCal.setText(foodInfos.get(i).cal);
    }

    @Override
    public int getItemCount() {
        if (foodInfos == null)
            return 0;
        return foodInfos.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        } else if (position == 1) {
            return LOADING_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {

    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }



    /*
     ******************************************************************
     *  SUB CLASSES
     ******************************************************************
     */

    /* HeadeHolder that display food image */
    public static class HeaderHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView headerPhoto;

        public HeaderHolder(View itemView) {
            super(itemView);
//            cv = itemView.findViewById(R.id.cv);
            //headerPhoto = (ImageView)itemView.findViewById(R.id.header_photo);
        }
    }

    /* LoadingHolder that display loading panel */
    public static class LoadingHolder extends RecyclerView.ViewHolder {
        CardView cv;
        View loadingPanel;

        public LoadingHolder(View itemView) {
            super(itemView);
           // cv = itemView.findViewById(R.id.cv);
            loadingPanel = itemView.findViewById(R.id.loading_panel);
        }
    }

    /* FoodInfoHolder that displays food information */
    public static class FoodInfoHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView foodName;
        TextView foodCal;
        TextView foodCarb;
        TextView foodProtein;
        TextView foodFat;
        TextView foodServing;
        ImageView addBtn;

        FoodInfoHolder(View itemView) {
            super(itemView);
          //  cv = itemView.findViewById(R.id.cv);
          //  foodName = itemView.findViewById(R.id.food_name);
          //  foodServing = itemView.findViewById(R.id.food_serving);
          //  foodCal = itemView.findViewById(R.id.food_cal);
          //  foodCarb = itemView.findViewById(R.id.food_carb);
          //  foodProtein = itemView.findViewById(R.id.food_protein);
          //  foodFat = itemView.findViewById(R.id.food_fat);
        }

    }
}
