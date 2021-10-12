package com.example.homefixer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class SignUp extends AppCompatActivity  implements View.OnClickListener {

    private EditText nameEditText,emailEditText,phoneEditText,nidEditText,passwordEditText,descriptionEditText;
    private Spinner professionSpinner,experienceSpinner,locationSpinner;
    private Button signUpButton;
    private String[] professions,locations,experiences;


    private ImageView profilePicture;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;


    private TextView professionTextView,experienceTextView,locationTextView;


    String name,email,phone,nid,profession,experience,location,password,description,imageUrl;





    DatabaseReference databaseReference;
    StorageReference storageReference;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        storageReference = FirebaseStorage.getInstance().getReference("Users");





        profilePicture = findViewById(R.id.profilePictureId);

        profilePicture.setOnClickListener(this);


        professionTextView = findViewById(R.id.professionTextViewId);
        experienceTextView = findViewById(R.id.experienceTextViewId);
        locationTextView = findViewById(R.id.locationTextViewId);


        nameEditText = findViewById(R.id.signUpNameId);
        emailEditText = findViewById(R.id.signUpEamilId);
        phoneEditText = findViewById(R.id.signUpPhoneId);
        nidEditText = findViewById(R.id.signUpNIDId);
        passwordEditText = findViewById(R.id.signUpPasswordId);
        descriptionEditText = findViewById(R.id.signUpDescriptionId);

        professionSpinner = findViewById(R.id.professionSpinnerId);
        experienceSpinner = findViewById(R.id.experienceSpinnerId);
        locationSpinner = findViewById(R.id.locationSpinnerId);

        signUpButton = findViewById(R.id.signUpButtonId);
        signUpButton.setOnClickListener(this);

        professions = getResources().getStringArray(R.array.profressions);
        experiences = getResources().getStringArray(R.array.experiences);
        locations = getResources().getStringArray(R.array.locations);

        ArrayAdapter professionsSpinnerAdapter = new ArrayAdapter(this,R.layout.spinner_sample_view,R.id.spinnerTextSampleId,professions);
        ArrayAdapter experiencesSpinnerAdapter = new ArrayAdapter(this,R.layout.spinner_sample_view,R.id.spinnerTextSampleId,experiences);
        ArrayAdapter locationsSpinnerAdapter = new ArrayAdapter(this,R.layout.spinner_sample_view,R.id.spinnerTextSampleId,locations);

        professionSpinner.setAdapter(professionsSpinnerAdapter);
        experienceSpinner.setAdapter(experiencesSpinnerAdapter);
        locationSpinner.setAdapter(locationsSpinnerAdapter);


    }







    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signUpButtonId){
            getDataFromUserAndCheckValidity();
        }
        if (view.getId()==R.id.profilePictureId){
            uploadImage();
        }
    }





    public String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }




    //CHOOSE IMAGE CODE --------------->
    private void uploadImage() {
        openFileChooser();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    } @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode==RESULT_OK && data.getData()!=null){
            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(profilePicture);
        }
    }




    private void getDataFromUserAndCheckValidity() {

        //getting data from user -------------------->



            name = nameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            phone = phoneEditText.getText().toString().trim();
            nid = nidEditText.getText().toString().trim();
            password = passwordEditText.getText().toString().trim();
            description = descriptionEditText.getText().toString().trim();

            profession = professionSpinner.getSelectedItem().toString();
            experience = experienceSpinner.getSelectedItem().toString();
            location = locationSpinner.getSelectedItem().toString();


      //Checking Validity --------------------------->
        if(name.isEmpty()){
            nameEditText.setError("Please! enter your name");
            nameEditText.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailEditText.setError("Please! enter your email");
            emailEditText.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailEditText.setError("Enter a valid email address");
            emailEditText.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            phoneEditText.setError("Please! enter your phone number");
            phoneEditText.requestFocus();
            return;
        }

        if(nid.isEmpty()){
            nidEditText.setError("Please! enter your NID");
            nidEditText.requestFocus();
            return;
        }
        if(profession.isEmpty()){
            professionTextView.setError("Please! select your profession");
            professionSpinner.requestFocus();
            return;
        }
        if(experience.isEmpty()){
            experienceTextView.setError("Please! select your experience");
            experienceSpinner.requestFocus();
            return;
        }

        if(location.isEmpty()){
            locationTextView.setError("Please! select your service location");
            locationSpinner.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEditText.setError("Please! enter your password");
            passwordEditText.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            passwordEditText.setError("Password should be more than 6 character");
            passwordEditText.requestFocus();
            return;
        }

        if(description.isEmpty()){
            descriptionEditText.setError("Please! write something about you");
            descriptionEditText.requestFocus();
            return;
        }




        saveData();
    }



    @SuppressLint("ResourceAsColor")
    private void saveData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registration on progress");
        progressDialog.show();

                try{

                    StorageReference ref = storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(imageUri));

                    ref.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    progressDialog.dismiss();
//                                    Snackbar.make(findViewById(android.R.id.content),"Registration Successful",Snackbar.LENGTH_LONG).show();
                                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "CONGRATULATION!\nREGISTRATION SUCCESSFUL", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null);
                                    View sbView = snackbar.getView();
//                                    sbView.setBackgroundColor(Color.GREEN);
                                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                                    snackbar.show();



                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while(!urlTask.isSuccessful());
                                    Uri downloadUrl = urlTask.getResult();

                                    imageUrl = downloadUrl.toString();




                                    UserModel userModel = new UserModel(name,email,phone,nid,profession,experience,location,password,description,imageUrl);

                                    String uploadId = databaseReference.push().getKey();

                                    databaseReference.child(uploadId).setValue(userModel);


                                    ClearField();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Image uploading failed", Toast.LENGTH_SHORT).show();
                                }
                            })

                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    progressDialog.setMessage("Saving information: "+ (int)progressPercent+"%");
                                }
                            });




                }catch (Exception e){
                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "REGISTRATION FAILED\nSELECT AN IMAGE FOR YOUR PROFILE", Snackbar.LENGTH_LONG).setAction("Action", null);
                    View sbView = snackbar.getView();
//                    sbView.setBackgroundColor(Color.RED);
                    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                    snackbar.show();
                }
    }

    private void ClearField() {
        nameEditText.setText("");
        emailEditText.setText("");
        phoneEditText.setText("");
        nidEditText.setText("");
        professionSpinner.setSelection(0,true);
        experienceSpinner.setSelection(0,true);
        locationSpinner.setSelection(0,true);
        passwordEditText.setText("");
        descriptionEditText.setText("");
        profilePicture.setImageResource(R.drawable.user);

    }

}