package cn.lytcom.camera2kit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int[] FLASH_OPTIONS = {
        CameraConstants.FLASH_AUTO,
        CameraConstants.FLASH_OFF,
        CameraConstants.FLASH_ON,
    };

    private static final int[] FLASH_ICONS = {
        R.drawable.ic_flash_auto,
        R.drawable.ic_flash_off,
        R.drawable.ic_flash_on,
    };

    private static final int[] FLASH_TITLES = {
        R.string.flash_auto,
        R.string.flash_off,
        R.string.flash_on,
    };

    private int mCurrentFlashIndex;


    private Camera2Fragment mCamera2Fragment;

    /**
     * The button of record video
     */
    private Button mRecordButton;

    /**
     * The button of take picture
     */
    private Button mPictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (null == savedInstanceState) {
            mCamera2Fragment = Camera2Fragment.newInstance();
            getFragmentManager().beginTransaction()
                .replace(R.id.container, mCamera2Fragment)
                .commit();
        } else {
            mCamera2Fragment = (Camera2Fragment) getFragmentManager().findFragmentById(R.id.container);
        }

        mRecordButton = (Button) findViewById(R.id.video);
        mRecordButton.setOnClickListener(this);
        mPictureButton = (Button) findViewById(R.id.picture);
        mPictureButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_flash:
                mCurrentFlashIndex = (mCurrentFlashIndex + 1) % FLASH_OPTIONS.length;
                item.setTitle(FLASH_TITLES[mCurrentFlashIndex]);
                item.setIcon(FLASH_ICONS[mCurrentFlashIndex]);
                mCamera2Fragment.setFlash(FLASH_OPTIONS[mCurrentFlashIndex]);
                return true;
            case R.id.switch_camera:
                int facing = mCamera2Fragment.getFacing();
                mCamera2Fragment.setFacing(facing == CameraConstants.FACING_FRONT ?
                    CameraConstants.FACING_BACK : CameraConstants.FACING_FRONT);

                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mCamera2Fragment.isRecordingVideo()) {
            menu.findItem(R.id.switch_camera).setVisible(false);
            menu.findItem(R.id.switch_flash).setVisible(false);
        } else {
            menu.findItem(R.id.switch_camera)
                .setVisible(mCamera2Fragment.isFacingSupported());
            menu.findItem(R.id.switch_flash)
                .setTitle(FLASH_TITLES[mCurrentFlashIndex])
                .setIcon(FLASH_ICONS[mCurrentFlashIndex])
                .setVisible(mCamera2Fragment.isFlashSupported());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.picture: {
                mCamera2Fragment.takePicture();
                break;
            }
            case R.id.video: {
                if (mCamera2Fragment.isRecordingVideo()) {
                    mCamera2Fragment.stopRecordingVideo();
                    mRecordButton.setText(R.string.start_record_video);
                    mPictureButton.setEnabled(true);
                } else {
                    mPictureButton.setEnabled(false);
                    mCamera2Fragment.startRecordingVideo();
                    mRecordButton.setText(R.string.stop_record_video);
                }
                invalidateOptionsMenu();
                break;
            }
        }
    }
}
