package com.zero.wolf.phototest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mImageView;
    private String mFilePath;
    private Uri mUri;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mImageView = (ImageView) findViewById(R.id.image_view);

        mFilePath = Environment.getExternalStorageDirectory().getPath();
        mFilePath = mFilePath + "/" + "temp.png";

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mUri = Uri.fromFile(new File(mFilePath));

                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);


                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Log.i(TAG, "onActivityResult: "+mUri);
                Log.i(TAG, "onActivityResult: "+mUri.getPath());
                Log.i(TAG, "onActivityResult: "+mFilePath);
                Bitmap bitmap = BitmapFactory.decodeFile(mUri.getPath());
               // Log.i(TAG, String.valueOf("onActivityResult: " + bitmap == null));
                boolean b = bitmap == null;
                Log.i(TAG, "onActivityResult: "+b);
//                mImageView.setImageBitmap(bitmap);
                mImageView.setImageURI(mUri);
            }
        }
    }
}
