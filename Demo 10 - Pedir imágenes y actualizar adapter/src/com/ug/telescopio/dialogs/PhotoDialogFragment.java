package com.ug.telescopio.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.ug.telescopio.R;

public class PhotoDialogFragment extends DialogFragment {    
	NoticeDialogListener listener;
	    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
		try {
			listener = (NoticeDialogListener) activity;
		} catch (ClassCastException e) {
			Log.e("ERROR",Log.getStackTraceString(e));
		}
    }
   
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.msg_photo)
               .setPositiveButton(R.string.msg_yes, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   listener.onDialogPositiveClick(PhotoDialogFragment.this);
                   }
               })
               .setNegativeButton(R.string.msg_no, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   listener.onDialogNegativeClick(PhotoDialogFragment.this);
                   }
               });
        return builder.create();
    }
    
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }    
}
