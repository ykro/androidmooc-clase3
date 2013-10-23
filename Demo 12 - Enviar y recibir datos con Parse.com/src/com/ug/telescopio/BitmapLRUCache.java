package com.ug.telescopio;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapLRUCache extends LruCache<String, Bitmap> implements ImageCache {
	private static final int CACHE_SIZE_BYTES = 4 * 1024 * 1024; // 4 MB
	
	public BitmapLRUCache() {
		super(CACHE_SIZE_BYTES);
	}
		
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}
 
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
 
}