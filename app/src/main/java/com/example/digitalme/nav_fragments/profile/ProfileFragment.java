package com.example.digitalme.nav_fragments.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.digitalme.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Console;
import java.io.File;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private View rootView;
    static final int TAKE_PHOTO = 1;
    static final int SELECT_PHOTO = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(ProfileViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        if(profileViewModel.getProfile() != null) { //Check if there is at least one Profile in database
            profileViewModel.getProfile().observe(getViewLifecycleOwner(), new Observer<Profile>() {
                @Override
                public void onChanged(Profile profile) {
                    if (profile != null && profile.getName() != null) { //If profile exist in database -> DEFAULT mode
                        defaultMode(profile);
                    }
                    else{
                        //If profile does not exist in database -> EDIT mode
                        addProfileMode();
                    }
                }
            });
        }

        return rootView;
    }

    private void defaultMode(Profile profile) {

        //Get GUI elements
        ImageView profile_img = rootView.findViewById(R.id.profile_img);
        EditText name = rootView.findViewById(R.id.profile_name);
        EditText location = rootView.findViewById(R.id.profile_location);
        TextInputEditText email = rootView.findViewById(R.id.email_input_field);
        TextInputEditText phone = rootView.findViewById(R.id.phone_input_field);
        TextInputEditText company = rootView.findViewById(R.id.company_input_field);

        //Enable Edit and QR icon buttons
        Button edit_butt = rootView.findViewById(R.id.edit_profile_butt);
        edit_butt.setClickable(true);
        edit_butt.setVisibility(View.VISIBLE);

        ImageButton qr_butt = rootView.findViewById(R.id.qr_code_icon);
        qr_butt.setClickable(true);

        //Button Save invisible
        Button save_butt = rootView.findViewById(R.id.save_profile_butt);
        save_butt.setVisibility(View.INVISIBLE);

        //Unable all inputs to be editable (focusable)
        setElementFocus(profile_img,name,location,email,phone,company,false);

        //Fill in GUI elements
        Uri picUri = Uri.parse(profile.getPicture_uri());
        File imgFile = new File(picUri.getPath());
        if(imgFile.exists()) {
            Glide.with(this).load(imgFile).into(profile_img);
        }

        name.setText(profile.getName());
        location.setText(profile.getLocation());
        email.setText(profile.getEmail());
        phone.setText(profile.getPhone());
        company.setText(profile.getCompany());

        //Implementation of Edit button
        edit_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_butt.setVisibility(View.INVISIBLE);
                editProfile(profile,profile_img,name,location,email,phone,company,save_butt);
            }
        });

        //Implementation of QR button
        qr_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQrCustomDialog(profile);
            }
        });
    }

    private void editProfile(Profile profile, ImageView profile_img, EditText name, EditText location, TextInputEditText email,
                             TextInputEditText phone, TextInputEditText company, Button save_butt) {

        //Enable all inputs to be editable (focusable)
        setElementFocus(profile_img,name,location,email,phone,company,true);

        //Make Save button visible
        save_butt.setVisibility(View.VISIBLE);

        //Implement Save button for editing profile
        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all inputs are not empty
                if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Your name field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(location.getText().toString())){
                    location.setError("Your location field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Your email field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Your phone field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(company.getText().toString())){
                    company.setError("Your company field can't be empty!");
                    return;
                }
                else{
                    //Get data and update profile
                    /*int id = profile.getId();

                    Profile updateProfile = new Profile("picture_uri",
                            name.getText().toString(),
                            location.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            company.getText().toString()
                            );
                    updateProfile.setId(id);*/

                    profile.setName(name.getText().toString());
                    profile.setLocation(location.getText().toString());
                    profile.setEmail(email.getText().toString());
                    profile.setPhone(phone.getText().toString());
                    profile.setCompany(company.getText().toString());

                    profileViewModel.update(profile);
                    defaultMode(profile);
                }
            }
        });
    }

    private void showQrCustomDialog(Profile profile) {

    }

    private void addProfileMode() {

        //Get GUI elements
        ImageView profile_img = rootView.findViewById(R.id.profile_img);
        EditText name = rootView.findViewById(R.id.profile_name);
        EditText location = rootView.findViewById(R.id.profile_location);
        TextInputEditText email = rootView.findViewById(R.id.email_input_field);
        TextInputEditText phone = rootView.findViewById(R.id.phone_input_field);
        TextInputEditText company = rootView.findViewById(R.id.company_input_field);

        //Unable Edit and QR icon buttons, enable Save button
        Button edit_butt = rootView.findViewById(R.id.edit_profile_butt);
        edit_butt.setVisibility(View.INVISIBLE);

        rootView.findViewById(R.id.qr_code_icon).setClickable(false);
        Button save_butt = rootView.findViewById(R.id.save_profile_butt);
        save_butt.setVisibility(View.VISIBLE);

        //Enable all inputs to be editable (focusable)
        setElementFocus(profile_img,name,location,email,phone,company,true);

        //Implement of profileImg
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //Implement Save button for adding new profile
        save_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all inputs are not empty
                if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Your name field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(location.getText().toString())){
                    location.setError("Your location field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Your email field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Your phone field can't be empty!");
                    return;
                }
                else if(TextUtils.isEmpty(company.getText().toString())){
                    company.setError("Your company field can't be empty!");
                    return;
                }
                else{
                    //Add new profile
                    Profile newProfile = new Profile("picture_uri",
                            name.getText().toString(),
                            location.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            company.getText().toString());

                    profileViewModel.insert(newProfile);
                    defaultMode(newProfile);
                }
            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery"," Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Choose photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    /*File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));*/
                    startActivityForResult(intent, TAKE_PHOTO);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_PHOTO);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView profile_pic = rootView.findViewById(R.id.profile_img);

        if(requestCode == TAKE_PHOTO && resultCode == getActivity().RESULT_OK){
            //Glide.with(this).load(data.getData()).into(profile_pic);
            Bundle extras = data.getExtras();
            /*Bitmap imageBitmap = (Bitmap) extras.get("data");
            profile_pic.setImageBitmap(imageBitmap);*/
        }
        if(requestCode == SELECT_PHOTO && resultCode == getActivity().RESULT_OK){
            Uri selectedPhoto = data.getData();
            Glide.with(this).load(selectedPhoto).fitCenter().circleCrop().into(profile_pic);
            Toast.makeText(getContext(),"Slika odabrana", Toast.LENGTH_SHORT).show();
        }
    }

    private void setElementFocus(ImageView profile_img, EditText name, EditText location, EditText email, EditText phone, EditText company, boolean focus){
        //Set focus on elements based on different mode types

        profile_img.setClickable(focus);
        name.setFocusableInTouchMode(focus);
        name.setFocusable(focus);

        location.setFocusableInTouchMode(focus);
        location.setFocusable(focus);

        email.setFocusableInTouchMode(focus);
        email.setFocusable(focus);

        phone.setFocusableInTouchMode(focus);
        phone.setFocusable(focus);

        company.setFocusableInTouchMode(focus);
        company.setFocusable(focus);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}