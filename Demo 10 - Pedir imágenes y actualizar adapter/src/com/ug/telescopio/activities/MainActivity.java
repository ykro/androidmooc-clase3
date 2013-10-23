package com.ug.telescopio.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ug.telescopio.Helper;
import com.ug.telescopio.R;
import com.ug.telescopio.data.Image;
import com.ug.telescopio.data.ImageAdapter;
import com.ug.telescopio.dialogs.PhotoDialogFragment;
import com.ug.telescopio.dialogs.PhotoDialogFragment.NoticeDialogListener;

public class MainActivity extends FragmentActivity 
						  implements NoticeDialogListener, 
						  			 OnClickListener
						  	 {

	Button btnPhoto;
	Button btnUpdate;
	ImageAdapter adapter;
	ArrayList<Image> imagesArray;
	public static RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		requestQueue = Volley.newRequestQueue(this);
		
		imagesArray = new ArrayList<Image>();
		adapter = new ImageAdapter(this, imagesArray);
		
	    GridView gridview = (GridView) findViewById(R.id.grid);
	    gridview.setAdapter(adapter);	
	    
	    btnPhoto = (Button)findViewById(R.id.btnPhoto);
	    btnPhoto.setOnClickListener(this);
	    btnUpdate = (Button)findViewById(R.id.btnUpdate);
	    btnUpdate.setOnClickListener(this);	 
	    
	    
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
		if (v.getId() == btnPhoto.getId()) {
			new PhotoDialogFragment().show(getSupportFragmentManager(), "");
		} else if (v.getId() == btnUpdate.getId()) {
			APICall();
		}
	}
	
	public void APICall() {
		String url = Helper.getRecentUrl("guatemala");
		
		btnUpdate.setEnabled(false);
		findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		
	    Response.Listener<JSONObject> successListener = 
	    		new Response.Listener<JSONObject>() {
		            @Override
		            public void onResponse(JSONObject response) {		            	
		            	findViewById(R.id.progressBar).setVisibility(View.GONE);
		            	JSONArray data;
						try {
							data = response.getJSONArray("data");
			            	for (int i = 0; i < data.length(); i++) {
			            		JSONObject currentElement = data.getJSONObject(i);
			            		String type = currentElement.getString("type");
			            		if (type.equals("image")) {
			            			JSONObject user = currentElement.getJSONObject("user");
			            			JSONObject images = currentElement.getJSONObject("images");
			            			JSONObject standardResolution = images.getJSONObject("standard_resolution");
			            			
			            			String imgUrl = standardResolution.getString("url");
			            			String userName = user.getString("username");
			            			
			            			Image image = new Image();
			            			image.setImgUrl(imgUrl);
			            			image.setUserName(userName);
			            			imagesArray.add(image);
			            		}
			            	}	
			            	adapter.notifyDataSetChanged();


						} catch (JSONException e) {
							Log.e("ERROR",Log.getStackTraceString(e));
						}
	
						btnUpdate.setEnabled(true);
						findViewById(R.id.grid).setVisibility(View.VISIBLE);
		            }
	    };
	
	    
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, 
															   url, 
															   null, 
															   successListener,
															   null);		
		requestQueue.add(jsObjRequest);		
	}

}
