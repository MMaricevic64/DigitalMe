package com.example.digitalme.intro_screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;

import org.w3c.dom.Text;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>{

    private List<ScreenItem> mListScreen;

    public OnboardingAdapter(List<ScreenItem> mListScreen) {
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.screen_intro_layout, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
            holder.setOnboardingData(mListScreen.get(position));
    }

    @Override
    public int getItemCount() {
        return mListScreen.size();
    }


    class OnboardingViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView description;
        private ImageView imgSlide;

        OnboardingViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.intro_title);
            description = itemView.findViewById(R.id.intro_description);
            imgSlide = itemView.findViewById(R.id.intro_img);
        }
        void setOnboardingData(ScreenItem screenItem){
            title.setText(screenItem.getTitle());
            description.setText(screenItem.getDescription());
            imgSlide.setImageResource(screenItem.getScreenImg());
        }
    }
}
