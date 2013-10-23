package com.ug.telescopio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.ug.telescopio.R;
import com.ug.telescopio.data.ImageAdapter;
import com.ug.telescopio.dialogs.PhotoDialogFragment;
import com.ug.telescopio.dialogs.PhotoDialogFragment.NoticeDialogListener;

public class MainActivity extends FragmentActivity 
						  implements NoticeDialogListener, 
						  			 OnClickListener
						  	 {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
	    GridView gridview = (GridView) findViewById(R.id.grid);
	    gridview.setAdapter(new ImageAdapter(this));	
	    
	    Button btnPhoto = (Button)findViewById(R.id.btnPhoto);
	    btnPhoto.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		Toast.makeText(this, getResources().getString(R.string.msg_yes), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
		startActivity(intent);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		Toast.makeText(this, getResources().getString(R.string.msg_no), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		new PhotoDialogFragment().show(getSupportFragmentManager(), "");
	}

}
