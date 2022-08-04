package com.medico.app.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.medico.app.R;
import com.medico.app.databinding.ActivityPrescriptionBinding;
import com.medico.app.utils.HideStatus;

import java.io.ByteArrayOutputStream;

public class PrescriptionActivity extends AppCompatActivity {
    ActivityPrescriptionBinding binding;
    HideStatus hideStatus;
    private static final int PICK_IMAGE_CAMERA_REQUEST_CODE = 0;
    private static final int PICK_IMAGE_GALLERY_REQUEST_CODE = 1;
    private String eventValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prescription);
        hideStatus = new HideStatus(getWindow(), true);
        eventValue = getIntent().getStringExtra("Event");
        if (eventValue.equals("Account")){
            binding.clPrList.setVisibility(View.VISIBLE);
        }

        binding.setClickListener(new EventHandler(this));
        binding.tvPrice.setText("₹ " + "199");
        binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


    public class EventHandler {
        Context mContext;

        public EventHandler(Context mContext) {
            this.mContext = mContext;
        }

        public void backPage() {
            onBackPressed();
            finish();
        }

        public void camera() {
            Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, PICK_IMAGE_CAMERA_REQUEST_CODE);
        }

        public void gallery() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY_REQUEST_CODE);
        }

        public void radioPrescription() {
            binding.rbPr.setSelected(true);
            binding.rbNoPr.setChecked(false);
        }

        public void radioNoPrescription() {
            binding.rbNoPr.setSelected(true);
            binding.rbPr.setChecked(false);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null || requestCode == PICK_IMAGE_CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        Uri selectedCamera = getCameraUri(getApplicationContext(), photo);
                        String finalCameraImage = selectedCamera.getPath();
                        Log.e("selectedImage", "CameraImage:" + finalCameraImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        Uri selectedImageUri = data.getData();
                        String picturePath = getImagePath(this, selectedImageUri);
                        Log.e("selectedImage", "GalleryImage:" + picturePath);
                    }
                    break;
            }
        }

    }

    public static String getImagePath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    public Uri getCameraUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Camera", null);
        return Uri.parse(path);
    }

}