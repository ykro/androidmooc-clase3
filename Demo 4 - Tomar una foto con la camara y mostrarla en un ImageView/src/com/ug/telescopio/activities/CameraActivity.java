package com.ug.telescopio.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.ug.telescopio.R;

public class CameraActivity extends Activity implements OnClickListener {
	private final static int LOAD_IMAGE = 1;
	private final static int USE_CAMERA = 2;
	Button btnFromCamera;
	Button btnFromGallery;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
	    btnFromGallery = (Button)findViewById(R.id.btnFromGallery);
	    btnFromCamera = (Button)findViewById(R.id.btnFromCamera);
	    btnFromGallery.setOnClickListener(this);	    
	    btnFromCamera.setOnClickListener(this);		
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        switch (requestCode) {
        	case LOAD_IMAGE:
        		if (resultCode == RESULT_OK) {
        			fromGallery(data);
        		}
        		break;
        	case USE_CAMERA:
        		if (resultCode == RESULT_OK) {
        			fromCamera(data);
        		}        		
        		break;
        }
    }	
    
    public void fromCamera (Intent data) {
        Bundle extras = data.getExtras();
        ImageView imageView = (ImageView) findViewById(R.id.img);
        imageView.setImageBitmap((Bitmap)extras.get("data"));    	
 
    }
    
    public void fromGallery(Intent data) {
    	if (data != null) {
	        Uri selectedImage = data.getData();
	        String[] filePathColumn = { MediaStore.Images.Media.DATA };
	
	        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	        cursor.moveToFirst();
	
	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        String picturePath = cursor.getString(columnIndex);
	        cursor.close();
	         
	        ImageView imageView = (ImageView) findViewById(R.id.img);
	        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    	}
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int code = -1;
		Intent intent = null;
		if (v.getId() 
				== btnFromGallery.getId()) {
			code = LOAD_IMAGE;
			intent = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			
		} else if (v.getId() == btnFromCamera.getId()) {
			code = USE_CAMERA;
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);			    				
		}
		startActivityForResult(intent, code);
	}
}
