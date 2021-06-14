package com.example.digitalme.nav_fragments.cards;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;

public class CardTypeAdapter extends ListAdapter<CardType, CardTypeAdapter.CardTypeHolder> {
    private OnItemClickListener listener;

    public CardTypeAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CardType> DIFF_CALLBACK = new DiffUtil.ItemCallback<CardType>() {
        @Override
        public boolean areItemsTheSame(@NonNull CardType oldItem, @NonNull CardType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CardType oldItem, @NonNull CardType newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public CardTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_type_item, parent, false);
        return new CardTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardTypeHolder holder, int position) {
        CardType currentCardType = getItem(position);
        holder.textViewCardType.setText(currentCardType.getTitle());

        if(currentCardType.getTitle().equals("Kreditne kartice")){
            holder.textViewCardType.setTextColor(Color.parseColor("#ffb500"));
            holder.cardLayout.setBackgroundColor(Color.parseColor("#283350"));
        }
        else if(currentCardType.getTitle().equals("Shopping kartice")){
            holder.textViewCardType.setTextColor(Color.parseColor("#283350"));
            holder.cardLayout.setBackgroundColor(Color.parseColor("#f93800"));
        }
        else if(currentCardType.getTitle().equals("e-Vizitke")){
            holder.textViewCardType.setTextColor(Color.parseColor("#f93800"));
            holder.cardLayout.setBackgroundColor(Color.parseColor("#ffb500"));
        }
    }

    public CardType getCardTypeAt(int position){
        return  getItem(position);
    }

    class CardTypeHolder extends RecyclerView.ViewHolder{
        private TextView textViewCardType;
        private LinearLayout cardLayout;

        public CardTypeHolder(View itemView){
            super(itemView);
            textViewCardType = itemView.findViewById(R.id.text_view_card_type);
            cardLayout = itemView.findViewById(R.id.card_type_layout);

            int height = Resources.getSystem().getDisplayMetrics().heightPixels;
            itemView.getLayoutParams().height = (int) (height / 3.7);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(CardType cardType);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
