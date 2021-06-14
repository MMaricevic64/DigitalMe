package com.example.digitalme.nav_fragments.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.digitalme.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private View rootView;
    static final int TAKE_PHOTO = 1;
    static final int SELECT_PHOTO = 2;
    static final int PERMISSIONS_REQUEST_CAMERA = 3;
    static final int PERMISSION_READ_EXTERNAL_STORAGE = 4;
    Uri prof_pic;

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
        ImageView profile_img_view = rootView.findViewById(R.id.profile_img);
        EditText name = rootView.findViewById(R.id.profile_name);
        EditText location = rootView.findViewById(R.id.profile_location);
        TextInputEditText email = rootView.findViewById(R.id.email_input_field);
        TextInputEditText phone = rootView.findViewById(R.id.phone_input_field);
        TextInputEditText company = rootView.findViewById(R.id.company_input_field);

        //Enable Edit and QR icon buttons
        Button edit_butt = rootView.findViewById(R.id.edit_profile_butt);
        edit_butt.setClickable(true);
        edit_butt.setVisibility(View.VISIBLE);

        //Enable QR button
        ImageButton qr_butt = rootView.findViewById(R.id.qr_code_icon);
        qr_butt.setClickable(true);

        //Button Save invisible
        Button save_butt = rootView.findViewById(R.id.save_profile_butt);
        save_butt.setVisibility(View.INVISIBLE);

        //Unable all inputs to be editable (focusable)
        setElementFocus(profile_img_view,name,location,email,phone,company,false);

        //Fill in GUI elements
        prof_pic = Uri.parse(profile.getPicture_uri());
        Glide.with(this).load(prof_pic).fitCenter().circleCrop().into(profile_img_view);

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
                editProfile(profile,profile_img_view,name,location,email,phone,company,save_butt);
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

        //Disable QR code button
        ImageButton qr_butt = rootView.findViewById(R.id.qr_code_icon);
        qr_butt.setClickable(false);

        //Make Save button visible
        save_butt.setVisibility(View.VISIBLE);

        //Implement change profile picture
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

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

                    profile.setPicture_uri(prof_pic.toString());
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

        String QrString = profile.getName() + "&" + profile.getLocation() + "&" + profile.getEmail() + "&" + profile.getPhone() + "&" + profile.getCompany();
        //Make QR code bitmap
        QRGEncoder qrgEncoder = new QRGEncoder(QrString,null, QRGContents.Type.TEXT,400);
        Bitmap qrbits = qrgEncoder.getBitmap();
        //Start custom dialog
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.qr_code_custom_dialog);
        dialog.findViewById(R.id.qr_code_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Get Image view of dialog and fill in QR code
        ImageView qr_image_view = dialog.findViewById(R.id.qr_code_image);
        qr_image_view.setImageBitmap(qrbits);
        //Show dialog
        dialog.show();

    }

    private void addProfileMode() {

        //Get GUI elements
        ImageView profile_img = rootView.findViewById(R.id.profile_img);
        EditText name = rootView.findViewById(R.id.profile_name);
        EditText location = rootView.findViewById(R.id.profile_location);
        TextInputEditText email = rootView.findViewById(R.id.email_input_field);
        TextInputEditText phone = rootView.findViewById(R.id.phone_input_field);
        TextInputEditText company = rootView.findViewById(R.id.company_input_field);

        //Unable Edit button
        Button edit_butt = rootView.findViewById(R.id.edit_profile_butt);
        edit_butt.setVisibility(View.INVISIBLE);

        //Disable QR code button
        ImageButton qr_butt = rootView.findViewById(R.id.qr_code_icon);
        qr_butt.setClickable(false);

        //Make Save button visible
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
                if(prof_pic == null){
                    prof_pic = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/default_profile_icon");
                }
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
                    Profile newProfile = new Profile(
                            prof_pic.toString(),
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
        //Create Alert dialog
        final CharSequence[] options = { "Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Choose photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    if(getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { //Check for permission
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, //If there is no permission ask it
                                    PERMISSIONS_REQUEST_CAMERA);
                        } else {
                            takePhoto();
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "You dont have camera on your device!", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //Check for permission
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, //If there is no permission ask it
                                PERMISSION_READ_EXTERNAL_STORAGE);
                    }
                    else{
                        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PHOTO);
                    }

                }
            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView profile_pic = rootView.findViewById(R.id.profile_img);

        if(requestCode == TAKE_PHOTO && resultCode == getActivity().RESULT_OK){
            Glide.with(this).load(prof_pic).fitCenter().circleCrop().into(profile_pic);
        }
        if(requestCode == SELECT_PHOTO && resultCode == getActivity().RESULT_OK){
            prof_pic = data.getData();
            Glide.with(this).load(prof_pic).fitCenter().circleCrop().into(profile_pic);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_CAMERA){
            if (permissions[0].equals(Manifest.permission.CAMERA) //Permission granted for camera
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
            }
            else{
                Toast.makeText(getContext(), "You must allow permission for camera!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == PERMISSION_READ_EXTERNAL_STORAGE){
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) //Permission granted for camera
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_PHOTO);

            }
            else{
                Toast.makeText(getContext(), "You must allow permission for external storage!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void takePhoto(){
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                prof_pic = FileProvider.getUriForFile(getContext(),
                        "com.example.digitalme.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, prof_pic);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "DigiMe_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private void setElementFocus(ImageView profile_img, EditText name, EditText location, EditText email, EditText phone, EditText company, boolean focus){
        //Set focus on elements based on different mode types

        profile_img.setEnabled(focus);
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