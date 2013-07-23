package com.betsamsoft.sudokucam;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;

public class SudokuCamActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_camera);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_camera, menu);
		return true;
	}

	
	/*
	 * http://developer.android.com/guide/topics/media/camera.html#custom-camera	
	 */
	
	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context _context) {
	    if (_context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
        // Create an instance of Camera
        mCamera = getCameraInstance();
        
        if( mCamera != null ) {
        	
	        // Create our Preview view and set it as the content of our activity.
	        mPreview = new CameraPreview(this, mCamera);
	        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.removeAllViews();
	        preview.addView(mPreview);
        }
	}
	
	
    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }


    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
	
}
