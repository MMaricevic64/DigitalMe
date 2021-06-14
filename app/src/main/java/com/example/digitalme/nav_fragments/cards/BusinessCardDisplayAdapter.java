package com.example.digitalme.nav_fragments.cards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;

public class BusinessCardDisplayAdapter extends ListAdapter<BusinessCard, BusinessCardDisplayAdapter.BusinessCardHolder> {
    private OnItemClickListener listener;

    public BusinessCardDisplayAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<BusinessCard> DIFF_CALLBACK = new DiffUtil.ItemCallback<BusinessCard>() {
        @Override
        public boolean areItemsTheSame(@NonNull BusinessCard oldItem, @NonNull BusinessCard newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BusinessCard oldItem, @NonNull BusinessCard newItem) {
            return oldItem.getBusiness_card_owner().equals(newItem.getBusiness_card_owner()) &&
                    oldItem.getBusiness_card_owner_location().equals(newItem.getBusiness_card_owner_location()) &&
                    oldItem.getBusiness_card_email().equals(newItem.getBusiness_card_email()) &&
                    oldItem.getBusiness_card_phone().equals(newItem.getBusiness_card_phone()) &&
                    oldItem.getBusiness_card_company().equals(newItem.getBusiness_card_company());
        }
    };

    @NonNull
    @Override
    public BusinessCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_card_item, parent, false);
        return new BusinessCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessCardHolder holder, int position) {
        BusinessCard currentBusinessCard = getItem(position);
        holder.business_card_owner.setText(currentBusinessCard.getBusiness_card_owner());
        holder.business_card_owner_location.setText(currentBusinessCard.getBusiness_card_owner_location());
        holder.business_card_email.setText(currentBusinessCard.getBusiness_card_email());
        holder.business_card_phone.setText(currentBusinessCard.getBusiness_card_phone());
        holder.business_card_company.setText(currentBusinessCard.getBusiness_card_company());
    }

    public BusinessCard getBusinessCardAt(int position){
        return  getItem(position);
    }

    class BusinessCardHolder extends RecyclerView.ViewHolder{
        private TextView business_card_owner;
        private TextView business_card_owner_location;
        private TextView business_card_email;
        private TextView business_card_phone;
        private TextView business_card_company;

        public BusinessCardHolder(View itemView){
            super(itemView);
            business_card_owner = itemView.findViewById(R.id.business_card_owner);
            business_card_owner_location = itemView.findViewById(R.id.business_card_location);
            business_card_email = itemView.findViewById(R.id.business_card_email);
            business_card_phone = itemView.findViewById(R.id.business_card_phone);
            business_card_company = itemView.findViewById(R.id.business_card_company);

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
        void onItemClick(BusinessCard businessCard);
    }

    public void  setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
