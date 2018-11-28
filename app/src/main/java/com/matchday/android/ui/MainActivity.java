package com.matchday.android.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.matchday.android.Const;
import com.matchday.android.R;
import com.matchday.android.prefs.Prefs;
import com.matchday.android.ui.widget.LatoBlackTextView;
import com.matchday.android.ui.widget.LatoBoldTextView;
import com.matchday.android.ui.widget.LatoMediumTextView;
import com.matchday.android.util.LogUtil;
import com.matchday.android.util.UiUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_gallery)
    protected LatoBlackTextView mTextGallery;
    @BindView(R.id.txt_photo)
    protected LatoBlackTextView mTextTakePhoto;
    @BindView(R.id.img_settings)
    protected ImageView mImageSettings;
    @BindView(R.id.txt_cards)
    LatoBlackTextView mTextCards;
    @BindView(R.id.txt_match_day)
    LatoMediumTextView mTextMatchDay;
    @BindView(R.id.txt_cards_eff)
    LatoBlackTextView mTextMenu;
    @BindView(R.id.txt_menu_item)
    LatoBoldTextView mTextTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirstOpenning();

        ButterKnife.bind(this);

        if (isStoragePermissionGranted()) {
            takePhoto();
            openGallary();
        }

        takePhoto();
        openGallary();

        openSettings();
    }

    // Check if onboarding_complete is false
    private void checkFirstOpenning() {
        if (!Prefs.getBooleanProperty(this, Const.PREFS_VALUE)) {
            // Start the onboarding Activity
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);

            // Close the main Activity
            finish();
            return;
        }
    }

    private void takePhoto() {
        mTextTakePhoto.setOnTouchListener((view, motionEvent) -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, Const.INTENT_PHOTO_RESULT);
            }
            //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                cameraFileName = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                File file = new File(SyncStateContract.Constants.DOWNLOAD_IMAGE_PATH);
//                if(!file.exists()){
//                    file.mkdirs();
//                }
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraFileName)));
            //intent.putExtra("data", true);
            //startActivityForResult(intent, Const.INTENT_PHOTO_RESULT);

            return false;
        });
    }

    private void openGallary() {
        mTextGallery.setOnTouchListener((view, motionEvent) -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, Const.INTENT_RESULT);
            return false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Const.INTENT_RESULT:
                    if (null != data) {
                        Uri URI = data.getData();
                        String[] FILE = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(URI,
                                FILE, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(FILE[0]);
                        String ImageDecode = cursor.getString(columnIndex);
                        cursor.close();
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra(Const.INTENT_EXTRA, ImageDecode);
                        intent.putExtra(Const.INTENT_EXTRA_HEIGHT, getHeightViews());
                        intent.setAction(Const.INTENT_ACTION_GALLARY);
                        startActivity(intent);
                    }
                    break;
                case Const.INTENT_PHOTO_RESULT:
                    LogUtil.info(this, "Image: "+data);
                    Bitmap imageDecode = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream arrayInputStream = new ByteArrayOutputStream();
                    imageDecode.compress(Bitmap.CompressFormat.PNG, 100, arrayInputStream);
                    byte[] byteArray = arrayInputStream.toByteArray();
                    LogUtil.info(this, "ByteArray: " + byteArray + " context: " + getBaseContext());
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra(Const.INTENT_EXTRA_FULL, byteArray);
                    intent.putExtra(Const.INTENT_EXTRA_HEIGHT, getHeightViews());
                    intent.setAction(Const.INTENT_ACTION_PHOTO);
                    startActivity(intent);
                    try {
                        arrayInputStream.flush();
                        arrayInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void openSettings() {
        mImageSettings.setOnTouchListener((view, motionEvent) -> {
            Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentSettings);
            return false;
        });
    }

    // Checking if the user has granted permission of external storage
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted
            //resume tasks needing this permission
            takePhoto();
            openGallary();
        } else {
            // Permission Denied
        }
    }

    private int getHeightViews() {
        return mTextCards.getHeight() + mTextMatchDay.getHeight() +
                mTextTemplate.getHeight() + mTextMenu.getHeight();
    }
}
