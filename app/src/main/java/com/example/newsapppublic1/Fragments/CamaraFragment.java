package com.example.newsapppublic1.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.newsapppublic1.Adapters.MediaPagerAdapter;
import com.example.newsapppublic1.CameraPreview;
import com.example.newsapppublic1.CustomTabLayout;
import com.example.newsapppublic1.Model.PictureFacer;
import com.example.newsapppublic1.Model.Videofacer;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.BottomsheetMediaBinding;
import com.example.newsapppublic1.databinding.FragmentCamaraBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CamaraFragment extends Fragment implements View.OnTouchListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String TAG = "camarafrag";
    FragmentCamaraBinding binding;
    ImageView imageView;
    FrameLayout preview;
    Button btnretack;
    View captureButton;
    ScaleGestureDetector scaleGestureDetector;
    TextView textView;
    ImageView imgstatus;
    float olddis = 0;
    CircularProgressBar progressBar;
    String status = "";
    int seconds = 0;
    boolean running = false;
    Boolean flashOn = false;
    ArrayList<PictureFacer> listImages;
    ArrayList<Videofacer> listVideos;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            textView.setText(" PIC Tacken");
            preview.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            btnretack.setVisibility(View.VISIBLE);
            captureButton.setVisibility(View.GONE);


            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions");
                return;
            }
            pictureFile.getAbsolutePath();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            //  imageView.setImageBitmap(bmp);
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //  imageView.setImageURI(Uri.parse(pictureFile.getPath()));

                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(
                        BitmapFactory.decodeFile(pictureFile.getAbsolutePath()), 2000, 2000);
                imageView.setImageBitmap(thumbImage);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");

        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camara, container, false);
        listImages = getAllImages();
        listVideos = getAllVideos();
        status = "";
        initView();
        flash();
        initCamara();
        initListnear();
        handlar();


        return binding.getRoot();
    }

    private void flash() {
        binding.imgFlashOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flashOn) {
                    flashOn = false;
                    binding.imgFlashOn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_flash_on_24));
                } else {
                    flashOn = true;
                    binding.imgFlashOn.setImageDrawable(getResources().getDrawable(R.drawable.ic_outline_flash_off_24));
                }
            }
        });
    }

    private void handlar() {

        final Handler handler
                = new Handler();

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                Log.d(TAG, "runttt " + time);
                binding.tvTime.setText(time);
                progressBar.setProgress(seconds);

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        status = "";
    }

    private void initCamara() {
        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);
        mPreview = new CameraPreview(getContext(), mCamera);
        preview.addView(mPreview);

        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size size = sizes.get(0);
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).width > size.width)
                size = sizes.get(i);
        }
        params.setPictureSize(size.width, size.height);

        if (flashOn) {
            params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
        } else {
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
        //  params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
        //  params.setExposureCompensation(0);
        params.setPictureFormat(ImageFormat.JPEG);
        params.setJpegQuality(100);
        params.setRotation(90);

        List<String> focusModes = params.getSupportedFocusModes();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            // Autofocus mode is supported
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        mCamera.setParameters(params);

    }

    private void initListnear() {
        binding.imgGalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnBottomSheet();
            }
        });
        mCamera.setZoomChangeListener(new Camera.OnZoomChangeListener() {
            @Override
            public void onZoomChange(int i, boolean b, Camera camera) {

            }
        });
        mPreview.setOnTouchListener(this);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (status.toString().equals("STOP")) {
                            Log.d("TAG", "onClick: stop vid");

                            status = "START";
                            gotovideorecoder();
                        } else {
                            // get an image from the camera
                            mCamera.takePicture(null, null, mPicture);
                            textView.setText("IMAGE");

                            // gotovideorecoder();
                        }
                    }
                }
        );
        btnretack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnretack.setVisibility(View.GONE);
                captureButton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                preview.setVisibility(View.VISIBLE);
                mCamera = getCameraInstance();
                mCamera.setDisplayOrientation(90);

                mPreview = new CameraPreview(getContext(), mCamera);
                preview.addView(mPreview);

            }


        });

        captureButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("TAG", "runtt long click ttt");
                Log.d("TAG", "onLongClick: start vid");
                status = "STOP";
                gotovideorecoder();
                return true;
            }
        });
    }

    private void oprnBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        final BottomsheetMediaBinding bottomsheetMediaBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.bottomsheet_media, null, false);
        bottomSheetDialog.setContentView(bottomsheetMediaBinding.getRoot());

        CustomTabLayout tabLayout = bottomsheetMediaBinding.tablayoutMedia;

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText("  Images  "));
        tabLayout.addTab(tabLayout.newTab().setText("  Videos  "));

        final ViewPager viewPager = bottomsheetMediaBinding.viewpagerMedia;
        MediaPagerAdapter adapter = new MediaPagerAdapter(2, listImages, listVideos);
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        bottomSheetDialog.show();
    }

    private ArrayList<Videofacer> getAllVideos() {
        Uri allImagesuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        ArrayList<Videofacer> videos = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE, MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Media._ID};
        Cursor cursor = getActivity().getContentResolver().query(allImagesuri, projection, null, null, null);

        try {
            cursor.moveToFirst();
            do {
                Videofacer pic = new Videofacer();

                pic.setVidName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                pic.setVidPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                pic.setVidSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)));

                String thumburi = getthumburi(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)));
                String thumburifinel = null;
                try {
                    Log.d("iiiith3", thumburi.toString());
                    thumburifinel = thumburi;
                } catch (Exception o) {
                    Log.d("iiiie207", o.toString());
                    thumburifinel = "null";
                }
                pic.setThumbPath(thumburifinel);

                videos.add(pic);
                Log.d(TAG, "getAllVideos: " + videos.size());
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<Videofacer> reSelection = new ArrayList<>();
            for (int i = videos.size() - 1; i > -1; i--) {
                reSelection.add(videos.get(i));
            }
            videos = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("iiiie222", e.toString());
        }
        return videos;
    }
/*
    private String getthumburiImage(int imageId) {
        String s = null;
        String[] projection2 = {MediaStore.Images.Thumbnails.DATA ,MediaStore.Images.Thumbnails._ID,MediaStore.Images.Thumbnails.IMAGE_ID};

        Cursor c = getActivity().getContentResolver().query( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection2,MediaStore.Images.Thumbnails.IMAGE_ID+"=?",new String[]{String.valueOf(imageId)},null);

        //MediaStore.Video.Thumbnails.VIDEO_ID + "=?",new String[]{String.valueOf(videoID)}, null);
        try {
            if (c != null) {
                c.moveToFirst();
            }
            do {
                s = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                String id = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails._ID));
                String vid = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.VIDEO_ID));
                String vid2 = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                Log.d("kkthumbpath",s.toString());
                Log.d("kkthumbid",id.toString());
                Log.d("kkthumbvidid",vid.toString());
                Log.d("kkvididfromthumb",vid2.toString());

            }while(c.moveToNext());
            c.close();
        } catch (Exception e) {

            Log.d("iiiie",e.toString());
        }

        return s;
    }
*/

    private String getthumburi(int videoID) {
        String s = null;
        String[] projection2 = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.VIDEO_ID};

        Cursor c = getActivity().getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                projection2, MediaStore.Video.Thumbnails.VIDEO_ID + "=?", new String[]{String.valueOf(videoID)}, null);

        //MediaStore.Video.Thumbnails.VIDEO_ID + "=?",new String[]{String.valueOf(videoID)}, null);
        try {
            if (c != null) {
                c.moveToFirst();
            }
            do {
                s = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
                String id = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails._ID));
                String vid = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.VIDEO_ID));
                String vid2 = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                Log.d("kkthumbpath", s.toString());
                Log.d("kkthumbid", id.toString());
                Log.d("kkthumbvidid", vid.toString());
                Log.d("kkvididfromthumb", vid2.toString());

            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {

            Log.d("iiiie", e.toString());
        }

        return s;
    }

    private ArrayList<PictureFacer> getAllImages() {
        ArrayList<PictureFacer> images = new ArrayList<>();
        Uri allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.d("iiiii1", allImagesuri.toString());
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media._ID};
        Cursor cursor = getActivity().getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                PictureFacer pic = new PictureFacer();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));


            /*    String thumburi=getthumburiImage(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)));
                String thumburifinel=null;
                try {
                    Log.d("imgiiiith3",thumburi.toString());
                    thumburifinel=thumburi;
                }catch (Exception o){
                    Log.d("imgiiiie207",o.toString());
                    thumburifinel="null";
                }
                pic.setThumbPath(thumburifinel);
*/

                images.add(pic);
                Log.d(TAG, "imgoprnBottomSheet: " + images.size());
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<PictureFacer> reSelection = new ArrayList<>();
            for (int i = images.size() - 1; i > -1; i--) {
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            Log.d(TAG, "imgoprnBottomSheet: error " + e);
            e.printStackTrace();
        }
        return images;
    }

    private void initView() {
        textView = binding.tvStatus;
        imgstatus = binding.imgStuts;
        imageView = binding.imageview;
        btnretack = binding.buttonCaptureDone;
        progressBar = binding.progressBar;

        preview = binding.cameraPreview;
        btnretack.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        captureButton = binding.btnCamara;


    }

    @Override
    public void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private boolean prepareVideoRecorder() {


        mediaRecorder = new MediaRecorder();

       /* mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);*/
        // Step 1: Unlock and set camera to MediaRecorder


        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        // Step 4: Set output file
        mediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());


        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        textView.setText("VID TSKEN");
        imgstatus.setVisibility(View.GONE);
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if (view.getId() == mPreview.getId()) {
            if (event.getPointerCount() == 2) {
          /*  Log.d(TAG, "onTouch: point zoom enable");
            Log.d(TAG, "onTouch: "+spacing(event));*/
                if (olddis < spacing(event)) {
                    Log.d(TAG, "onTouch: is zoom");
                    olddis = spacing(event);

                    Camera.Parameters params = mCamera.getParameters();
                    if (params.isZoomSupported()) {
                        int maxZoom = params.getMaxZoom();
                        Log.d(TAG, "zoom " + maxZoom);

                        if (params.getZoom() <= params.getMaxZoom() - 6) {
                            params.setZoom(params.getZoom() + 5);

                        } else {
                            params.setZoom(maxZoom);
                        }
                        mCamera.setParameters(params);

                    }
                } else {
                    Log.d(TAG, "onTouch: is zoom out ");
                    olddis = spacing(event);
                    Camera.Parameters params = mCamera.getParameters();
                    if (params.isZoomSupported()) {
                        int maxZoom = params.getMaxZoom();
                        Log.d(TAG, "zoom " + maxZoom);
                        params.setZoom(params.getZoom() - 5);
                        if (params.getZoom() <= 0) {

                        } else {
                            mCamera.setParameters(params);
                        }
                    }
                }

            }
            return true;

        } else if (view.getId() == captureButton.getId()) {

            captureButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Log.d(TAG, "runtt long click ttt");
                    return true;
                }
            });

            int longClickDuration = 3000;
            boolean isLongPress = false;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //       Log.d(TAG, "runtt: long press yyy dddd");
                isLongPress = true;
                Handler handler = new Handler();
                final boolean finalIsLongPress = isLongPress;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (finalIsLongPress) {
                            //      Log.d(TAG, "runtt: long press yyy");

                            //  gotovideorecoder();
                            // set your code here
                        }
                    }
                }, longClickDuration);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                isLongPress = false;
                Log.d(TAG, "runtt: long press nnn");
                // gotovideorecoder();
            }
            return true;
        }
        return true;
    }

    private void gotovideorecoder() {
        mCamera.setDisplayOrientation(90);
        if (isRecording) {


            // stop recording and release camera
            mediaRecorder.stop();  // stop the recording
            imgstatus.setVisibility(View.GONE);
            releaseMediaRecorder(); // release the MediaRecorder object
            mCamera.lock();         // take camera access back from MediaRecorder

            // inform the user that recording has stopped

            isRecording = false;
            running = false;
            progressBar.setProgress(0);
        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mediaRecorder.start();
                running = true;
                // inform the user that recording has started
                isRecording = true;
                imgstatus.setVisibility(View.VISIBLE);
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }
    }

    public float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);

            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float s = x * x + y * y;
        return (float) Math.sqrt(s);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

}