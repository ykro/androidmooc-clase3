package com.ug.telescopio.data;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ug.telescopio.R;

public class ImageAdapter extends BaseAdapter {
	int[] arrayFlags = new int[]{R.drawable.brasil, 
			 R.drawable.mexico,
			 R.drawable.colombia,
			 R.drawable.argentina,
			 R.drawable.peru,
			 R.drawable.venezuela,
			 R.drawable.chile,
			 R.drawable.ecuador,
			 R.drawable.guatemala,
			 R.drawable.cuba};
	
	String[] arrayCountries = new String[]{"Brasil", "MŽxico", "Colombia", "Argentina",
										   "Perœ", "Venezuela", "Chile", "Ecuador", 
										   "Guatemala", "Cuba"};	
	
	private Resources resources;
	private LayoutInflater inflater;
	
    public ImageAdapter(Context context) {
    	this.resources = context.getResources();
        this.inflater = LayoutInflater.from(context);
    }
	    
	@Override
	public int getCount() {
		return arrayFlags.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if (convertView == null) {
	        convertView = inflater.inflate(R.layout.grid_image, null);

	        holder = new ViewHolder();
	        holder.imgFlag = (ImageView) convertView.findViewById(R.id.imgFlag);
	        holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
	        
	        convertView.setTag(holder);	        
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
        
        holder.imgFlag.setImageBitmap(
        	    decodeSampledBitmapFromResource(resources, arrayFlags[position], 400, 200));

	    holder.txtName.setText(arrayCountries[position]);
        
        return convertView;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);

        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
}	
	
	static class ViewHolder {
		public ImageView imgFlag;
		public TextView txtName;
	}

}
