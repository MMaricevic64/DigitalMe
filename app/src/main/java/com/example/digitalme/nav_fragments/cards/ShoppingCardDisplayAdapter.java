package com.example.digitalme.nav_fragments.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.documents.Document;
import com.example.digitalme.nav_fragments.documents.DocumentType;
import com.example.digitalme.nav_fragments.documents.DocumentTypeAdapter;
import com.example.digitalme.nav_fragments.documents.DocumentsDisplayAdapter;
import com.example.digitalme.nav_fragments.documents.DocumentsImageViewModel;

public class ShoppingCardDisplayAdapter extends ListAdapter<Card, ShoppingCardDisplayAdapter.ShoppingCardHolder> {
    private OnItemClickListener listener;

    public ShoppingCardDisplayAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Card> DIFF_CALLBACK = new DiffUtil.ItemCallback<Card>() {
        @Override
        public boolean areItemsTheSame(@NonNull Card oldItem, @NonNull Card newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Card oldItem, @NonNull Card newItem) {
            return oldItem.getCard_description().equals(newItem.getCard_description()) &&
                    oldItem.getCard_number().equals(newItem.getCard_number()) &&
                    oldItem.getCard_owner().equals(newItem.getCard_owner());
        }
    };

    @NonNull
    @Override
    public ShoppingCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_card_item, parent, false);
        return new ShoppingCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCardHolder holder, int position) {
        Card currentCard = getItem(position);
        holder.shopping_card_description.setText(currentCard.getCard_description());
        holder.shopping_card_number.setText(currentCard.getCard_number());
        holder.shopping_card_owner.setText(currentCard.getCard_owner());
    }

    public Card getCardAt(int position){
        return  getItem(position);
    }

    class ShoppingCardHolder extends RecyclerView.ViewHolder{
        private TextView shopping_card_description;
        private TextView shopping_card_number;
        private TextView shopping_card_owner;

        public ShoppingCardHolder(View itemView){
            super(itemView);
            shopping_card_description = itemView.findViewById(R.id.shopping_card_desc);
            shopping_card_number = itemView.findViewById(R.id.shopping_card_number);
            shopping_card_owner = itemView.findViewById(R.id.shopping_card_owner);

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
        void onItemClick(Card card);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
