package com.example.contactssampleapp.addcontact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.contactssampleapp.R;

import java.io.IOException;


public class AddContactActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 333;
    private Button mButtonDone;
    private Button mButtonCancel;
    private Button mUploadButton;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private ImageView imageView;
    private Uri selectedImageURI;
    private String name, email;
    private int phone;
    private String imagePath;
    private static final String IMAGE_DIRECTORY = "/jose";
    private static final String TAG = "AddContactActivity";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PHONE = "PHONE";
    public static final String EXTRA_IMAGE = "IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mButtonCancel = findViewById(R.id.button_cancel);
        mButtonDone = findViewById(R.id.button_done);
        editTextEmail = findViewById(R.id.editText_email);
        editTextName = findViewById(R.id.editText_name);
        editTextPhoneNumber = findViewById(R.id.editText_phone);
        mUploadButton = findViewById(R.id.button_upload);
        imageView = findViewById(R.id.imageView_upload);

        mUploadButton.setOnClickListener(View -> selectImageFromGallery());

        mButtonCancel.setOnClickListener(View -> finish());

        mButtonDone.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(editTextName.getText())||
                    TextUtils.isEmpty(editTextEmail.getText())||
                    TextUtils.isEmpty(editTextPhoneNumber.getText())) {

                setResult(RESULT_CANCELED, replyIntent);

            } else {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                phone = Integer.parseInt(editTextPhoneNumber.getText().toString());
                replyIntent.putExtra(EXTRA_NAME, name);
                replyIntent.putExtra(EXTRA_EMAIL, email);
                replyIntent.putExtra(EXTRA_PHONE, phone);
                replyIntent.putExtra(EXTRA_IMAGE, imagePath);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                //Get ImageURi
                selectedImageURI = data.getData();
                if (selectedImageURI != null) {
                    imagePath = selectedImageURI.toString();
                }
                Log.d(TAG, "onActivityResult: " + selectedImageURI.toString());

                Glide.with(AddContactActivity.this)
                        .load(selectedImageURI)
                        .apply(RequestOptions.circleCropTransform())
                        .into((ImageView) findViewById(R.id.imageView_upload));

            }

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
