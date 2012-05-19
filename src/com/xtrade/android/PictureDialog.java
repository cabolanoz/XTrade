package com.xtrade.android;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xtrade.android.util.EventConstant;
import com.xtrade.android.util.Settings;

public class PictureDialog extends DialogFragment implements EventConstant {

	private static PictureDialog instance;
	private Fragment fragment;

	public static PictureDialog newInstance(Fragment activity) {
		instance = new PictureDialog(activity);

		return instance;
	}

	protected PictureDialog(Fragment activity) {
		this.fragment = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.client_photo, container, false);

		Button btnFromGallery = (Button) view.findViewById(R.id.btnFromGallery);
		btnFromGallery.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				fragment.startActivityForResult(intent, CLIENT_PHOTO_GALLERY_REQUEST);

				// Disposing the current dialog
				dismiss();
			}
		});

		Button btnFromCamera = (Button) view.findViewById(R.id.btnFromCamera);
		btnFromCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				//TODO: solve bug http://code.google.com/p/android/issues/detail?id=1480 high resolution image in some devices failes
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Settings.TEMPFILE_PATH)));
				startActivityForResult(intent, CLIENT_PHOTO_CAMERA_REQUEST);

				// Disposing the current dialog
				dismiss();
			}
		});

		return view;
	}

}
