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

public class CardDisplayAdapter extends ListAdapter<Card, CardDisplayAdapter.CardHolder> {
    private OnItemClickListener listener;

    public CardDisplayAdapter(){
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
                    oldItem.getCard_cvv().equals(newItem.getCard_cvv()) &&
                    oldItem.getCard_expiration().equals(newItem.getCard_expiration()) &&
                    oldItem.getCard_owner().equals(newItem.getCard_owner()) &&
                    oldItem.getCard_account_number().equals(newItem.getCard_account_number()) &&
                    oldItem.getCard_iban().equals(newItem.getCard_iban());
        }
    };

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        Card currentCard = getItem(position);
        holder.card_description.setText(currentCard.getCard_description());
        holder.card_number.setText(currentCard.getCard_number());
        holder.card_cvv.setText(currentCard.getCard_cvv());
        holder.card_expiration.setText(currentCard.getCard_expiration());
        holder.card_owner.setText(currentCard.getCard_owner());
        holder.card_account_number.setText(currentCard.getCard_account_number());
        holder.card_iban.setText(currentCard.getCard_iban());
    }

    public Card getCardAt(int position){
        return  getItem(position);
    }

    class CardHolder extends RecyclerView.ViewHolder{
        private TextView card_description;
        private TextView card_number;
        private TextView card_cvv;
        private TextView card_expiration;
        private TextView card_owner;
        private TextView card_account_number;
        private TextView card_iban;

        public CardHolder(View itemView){
            super(itemView);
            card_description = itemView.findViewById(R.id.card_desc);
            card_number = itemView.findViewById(R.id.card_number);
            card_cvv = itemView.findViewById(R.id.card_cvv);
            card_expiration = itemView.findViewById(R.id.card_expire);
            card_owner = itemView.findViewById(R.id.card_owner);
            card_account_number = itemView.findViewById(R.id.account_number);
            card_iban = itemView.findViewById(R.id.iban_number);

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
