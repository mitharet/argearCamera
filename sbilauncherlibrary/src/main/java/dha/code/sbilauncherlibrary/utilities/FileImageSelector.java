package dha.code.sbilauncherlibrary.utilities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;


public class FileImageSelector {
    private final int REQUEST_IMAGE_CAPTURE = 1324;
    private final int REQUEST_IMAGE_GALLERY = 2435;
    private final int REQUEST_PERMISSIONS = 7656;

    private final String [] galleryPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private final String [] cameraPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private Activity mActivity;
    private FileImageSelectorDelegate mDelegate;
    private Uri mCameraUri;
    private int mAction = 0;

    public static FileImageSelector create(Activity activity, FileImageSelectorDelegate delegate){
        return new FileImageSelector(activity, delegate);
    }

    private FileImageSelector(Activity activity, FileImageSelectorDelegate delegate){
        this.mActivity = activity;
        this.mDelegate = delegate;
    }

    public void openGallery(){
        mAction = 0;

        if(!PermissionUtils.hashPermission(mActivity, galleryPermissions)){
            PermissionUtils.requestPermission(mActivity, galleryPermissions, REQUEST_PERMISSIONS);
            return;
        }

        Intent pickerIntent = new Intent();
        pickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        pickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickerIntent.setTypeAndNormalize("image/*");
        mActivity.startActivityForResult(pickerIntent, REQUEST_IMAGE_GALLERY);
    }

    @SuppressLint("UnsupportedChromeOsCameraSystemFeature")
    public void captureImage(){
        mAction = 1;

        if(!PermissionUtils.hashPermission(mActivity, cameraPermissions)){
            PermissionUtils.requestPermission(mActivity, cameraPermissions, REQUEST_PERMISSIONS);
            return;
        }

        if(!mActivity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // TODO show error message
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
            mCameraUri = mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
                mActivity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && mCameraUri != null){
            // send callback
            mDelegate.onImageSelected(mCameraUri);
            return;
        }

        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){
            // send callback
            mDelegate.onImageSelected(data.getData());
        }

        // TODO show error message
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_PERMISSIONS && PermissionUtils.hashPermission(mActivity, permissions)){
            if(mAction == 1)
                captureImage();
            else
                openGallery();
        }
    }

    public interface FileImageSelectorDelegate{
        void onImageSelected(Uri uri);
    }
}
