package com.example.digitalme.nav_fragments.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.notes.Note;
import com.example.digitalme.nav_fragments.notes.NoteAdapter;

public class DocumentTypeAdapter extends ListAdapter<DocumentType, DocumentTypeAdapter.DocumentTypeHolder> {
    private OnItemClickListener listener;

    public DocumentTypeAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<DocumentType> DIFF_CALLBACK = new DiffUtil.ItemCallback<DocumentType>() {
        @Override
        public boolean areItemsTheSame(@NonNull DocumentType oldItem, @NonNull DocumentType newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DocumentType oldItem, @NonNull DocumentType newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };

    @NonNull
    @Override
    public DocumentTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.document_type_item, parent, false);
        return new DocumentTypeAdapter.DocumentTypeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentTypeHolder holder, int position) {
        DocumentType currentDocumentType = getItem(position);
        holder.textViewTitle.setText(currentDocumentType.getTitle());
    }

    public DocumentType getDocumentTypeAt(int position){
        return  getItem(position);
    }

    class DocumentTypeHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        public DocumentTypeHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title_document_type);

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
        void onItemClick(DocumentType documentType);
    }

    public void  setOnItemClickListener(DocumentTypeAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
