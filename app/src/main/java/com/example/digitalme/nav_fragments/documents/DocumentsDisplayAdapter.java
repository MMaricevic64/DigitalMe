package com.example.digitalme.nav_fragments.documents;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.notes.Note;
import com.example.digitalme.nav_fragments.notes.NoteAdapter;

import java.util.List;

public class DocumentsDisplayAdapter extends ListAdapter<Document,DocumentsDisplayAdapter.DocumentHolder> {
    private ImageListener listener;
    private LongImageListener longclicklistener;
    private Context context;

    public DocumentsDisplayAdapter(Context context){
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<Document> DIFF_CALLBACK = new DiffUtil.ItemCallback<Document>() {
        @Override
        public boolean areItemsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Document oldItem, @NonNull Document newItem) {
            return oldItem.getPicture_uri().equals(newItem.getPicture_uri());
        }
    };

    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_image_item, parent, false);
        return new DocumentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentHolder holder, int position) {
        Document currentDocument = getItem(position);
        String image_uri = currentDocument.getPicture_uri();
        Glide.with(context).load(image_uri).fitCenter().into(holder.image);
    }

    public Document getDocumentAt(int position){
        return  getItem(position);
    }

    public class DocumentHolder extends RecyclerView.ViewHolder{
        private ImageView image;

        public DocumentHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.document_image);
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();

                    if(longclicklistener != null && position != RecyclerView.NO_POSITION){
                        longclicklistener.onLongImageClick(getItem(position));
                    }
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onImageClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface ImageListener{
        void onImageClick(Document document);
    }

    public interface LongImageListener{
        void onLongImageClick(Document document);
    }

    public void  setOnItemClickListener(ImageListener listener){
        this.listener = listener;
    }
    public void setOnItemLongClickListener(LongImageListener listener){this.longclicklistener = listener;}
}
