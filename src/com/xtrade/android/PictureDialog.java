package com.xtrade.android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class PictureDialog extends DialogFragment {

	private static PictureDialog instance;
	private ImageButton txtClientPhoto;
	
	private final int GALLERY_REQUEST = 102;
	private final int CAMERA_REQUEST = 103;
	
	public static PictureDialog newInstance() {
		instance = new PictureDialog();
		return instance;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		txtClientPhoto = (ImageButton) getActivity().findViewById(R.id.txtClientPhoto);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.client_photo, container, false);
		
		Button btnFromGallery = (Button) view.findViewById(R.id.btnFromGallery);
		btnFromGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, GALLERY_REQUEST);
				
				// Disposing the current dialog
				dismiss();
			}
		});
		
		Button btnFromCamera = (Button) view.findViewById(R.id.btnFromCamera);
		btnFromCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST);
				
				// Disposing the current dialog
				dismiss();
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == FragmentActivity.RESULT_OK)
			if (txtClientPhoto != null)
				if (requestCode == GALLERY_REQUEST)
					txtClientPhoto.setImageURI(data.getData());
				else if (requestCode == CAMERA_REQUEST)
					txtClientPhoto.setImageBitmap((Bitmap) data.getExtras().get("data"));
	}
	
}
