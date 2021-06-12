package com.example.digitalme.nav_fragments.documents;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.notes.AddEditNoteActivity;
import com.example.digitalme.nav_fragments.notes.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DocumentsFragment extends Fragment {

    public static final int DISPLAY_DOCUMENTS = 1;
    private DocumentsViewModel documentsViewModel;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        documentsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(DocumentsViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_documents, container, false);

        FloatingActionButton buttonAddDocumentType = rootView.findViewById(R.id.button_add_document_type);
        buttonAddDocumentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.add_document_type_custom_dialog);
                dialog.findViewById(R.id.add_new_document_type_butt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText title = dialog.findViewById(R.id.new_document_type_title);
                        if(TextUtils.isEmpty(title.getText().toString())){
                            title.setError("Your title field can't be empty!");
                            return;
                        }
                        else{
                            addDocumentType(title.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_document_types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final DocumentTypeAdapter adapter = new DocumentTypeAdapter();
        recyclerView.setAdapter(adapter);

        documentsViewModel.getAllDocumentTypes().observe(getViewLifecycleOwner(), new Observer<List<DocumentType>>() {
            @Override
            public void onChanged(List<DocumentType> documentTypes) {
                adapter.submitList(documentTypes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                documentsViewModel.delete(adapter.getDocumentTypeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(),"Document type deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DocumentTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentType documentType) {
                //Otvori novi intent i posalji ID itema
                Intent intent = new Intent(getActivity(), DocumentDisplay.class);
                intent.putExtra("ID_TYPE_DOCUMENT", documentType.getId());
                startActivityForResult(intent, DISPLAY_DOCUMENTS);
            }
        });

        return rootView;
    }

    private void addDocumentType(String title) {
        //Make new Document type
        DocumentType newDocumentType = new DocumentType(title);
        documentsViewModel.insert(newDocumentType);
    }
}