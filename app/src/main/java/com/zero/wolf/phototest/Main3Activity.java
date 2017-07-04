package com.zero.wolf.phototest;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Main3Activity extends AppCompatActivity {
    private Button mButton;
    private ImageView mImageView;
    private static final String TAG = "Main3Activity";
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.image_view);

        String path = Environment.getExternalStorageDirectory().getPath();
        Log.i(TAG, "onCreate: " + path);

        String mFilePath = path + "/" + "DCIM" + "/Camera";
        String mFileName = "IMG_20170704_180713.jpg";

        mUri = Uri.fromFile(new File(mFilePath + "/" + mFileName));

        Log.i(TAG, "onCreate: " + mUri.getPath());
        Log.i(TAG, "onCreate: " + mUri.toString());

        mButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                Log.i(TAG, "onClick: " + mUri.getPath());

                String imagePath = null;
                if (DocumentsContract.isDocumentUri(Main3Activity.this, mUri)) {
                    String documentId = DocumentsContract.getDocumentId(mUri);
                    if ("com.android.providers.media,documents".equals(mUri.getAuthority())) {
                        String id = documentId.split(":")[1];
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

                    } else if ("com.android.providers.downloads.documents".equals(mUri.getAuthority())) {
                        Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(documentId));
                        imagePath = getImagePath(contentUri, null);
                    }
                } else if ("content".equalsIgnoreCase(mUri.getScheme())) {
                    //Log.d(TAG, "content: " + uri.toString());
                    imagePath = getImagePath(mUri, null);
                } else if ("file".equalsIgnoreCase(mUri.getScheme())) {
                    imagePath = mUri.getPath();
                }
                dispalyImage(imagePath);
            }


//                mImageView.setImageURI(mUri);
                /*
                Bitmap bitmap = BitmapFactory.decodeFile(mUri.getPath());

                boolean b = bitmap == null;
                Log.i(TAG, "onClick: "+b);
                mImageView.setImageBitmap(bitmap);
*/

        });
    }

    private void dispalyImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mImageView.setImageBitmap(bitmap);

        } else {
            Toast.makeText(Main3Activity.this, "cuowu", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            cursor.close();
        }
        return path;
    }

}
