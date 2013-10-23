package com.ug.telescopio.activities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
	String photoPath;
	
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
        			fromCamera();
        		}        		
        		break;
        }
    }	
    
    public void fromCamera () {
    	ImageView imageView = (ImageView) findViewById(R.id.img);    	
		Bitmap bitmap = resizeBitmap(imageView.getWidth(), imageView.getHeight());
		imageView.setImageBitmap(bitmap);
		
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(photoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
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
    
    public Bitmap resizeBitmap(int targetW, int targetH) {
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		return BitmapFactory.decodeFile(photoPath, bmOptions);    	
    }
    
    public File setUpFile() {
        File albumDir;
        String albumName = "ejemplo";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			albumDir = new File(
	                  Environment.getExternalStoragePublicDirectory(
	                          Environment.DIRECTORY_PICTURES
	                        ), 
	                        albumName
	                      );		
		} else {
			albumDir = new File (
	                Environment.getExternalStorageDirectory()
	                + "/dcim/"
	                + albumName);				
		}
		
		albumDir.mkdirs();
		
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
			       Locale.getDefault())
							.format(Calendar.getInstance().getTime());
		String imageFileName = "IMG_" + timeStamp + ".jpg";
		File imageF = new File(albumDir + "/" + imageFileName);
		return imageF;
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
			File photo = setUpFile();
			photoPath = photo.getAbsolutePath();
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		}
		startActivityForResult(intent, code);
	}
}
