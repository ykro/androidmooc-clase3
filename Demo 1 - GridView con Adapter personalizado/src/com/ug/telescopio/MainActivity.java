package com.ug.telescopio;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridView;

import com.ug.telescopio.data.ImageAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    GridView gridview = (GridView) findViewById(R.id.grid);
	    gridview.setAdapter(new ImageAdapter(this));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
