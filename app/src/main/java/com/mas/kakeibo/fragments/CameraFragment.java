package com.mas.kakeibo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mas.kakeibo.R;
import com.mas.kakeibo.constants.Common;
import com.mas.kakeibo.managers.PermissionManager;
import com.mas.kakeibo.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.configuration.CameraConfiguration;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;

import static io.fotoapparat.log.LoggersKt.fileLogger;
import static io.fotoapparat.log.LoggersKt.logcat;
import static io.fotoapparat.log.LoggersKt.loggers;
import static io.fotoapparat.result.transformer.ResolutionTransformersKt.scaled;
import static io.fotoapparat.selector.AspectRatioSelectorsKt.standardRatio;
import static io.fotoapparat.selector.FlashSelectorsKt.autoFlash;
import static io.fotoapparat.selector.FlashSelectorsKt.autoRedEye;
import static io.fotoapparat.selector.FlashSelectorsKt.off;
import static io.fotoapparat.selector.FlashSelectorsKt.torch;
import static io.fotoapparat.selector.FocusModeSelectorsKt.autoFocus;
import static io.fotoapparat.selector.FocusModeSelectorsKt.continuousFocusPicture;
import static io.fotoapparat.selector.FocusModeSelectorsKt.fixed;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;
import static io.fotoapparat.selector.PreviewFpsRangeSelectorsKt.highestFps;
import static io.fotoapparat.selector.ResolutionSelectorsKt.highestResolution;
import static io.fotoapparat.selector.SelectorsKt.firstAvailable;
import static io.fotoapparat.selector.SensorSensitivitySelectorsKt.highestSensorSensitivity;

/**
 * Created by sow.m on 2018/12/10.
 */
public class CameraFragment extends BaseFragment {
    private static final String TAG = CameraFragment.class.getSimpleName();

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @BindView(R.id.fragment_camera_view)
    CameraView mCameraView;
    @BindView(R.id.image)
    ImageView imageView;

    private boolean hasCameraPermission;
    private Fotoapparat mFotoapparat;
    private PermissionManager mPermissionManager;


    private CameraConfiguration mCameraConfiguration = CameraConfiguration
            .builder()
            .photoResolution(standardRatio(highestResolution()))
            .focusMode(firstAvailable(
                    continuousFocusPicture(),
                    autoFocus(),
                    fixed()
            ))
            .flash(firstAvailable(
                    autoRedEye(),
                    autoFlash(),
                    torch(),
                    off()
            ))
            .previewFpsRange(highestFps())
            .sensorSensitivity(highestSensorSensitivity())
            .frameProcessor(new SampleFrameProcessor())
            .build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPermissionManager = new PermissionManager(getActivity());
        hasCameraPermission = mPermissionManager.hasCameraPermission();

        if (hasCameraPermission) {

        } else {
            mPermissionManager.requestCameraPermission();
        }

        mFotoapparat = createFotoapparat();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasCameraPermission) {
            mFotoapparat.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hasCameraPermission) {
            mFotoapparat.stop();
        }
    }

    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(getActivity())
                .into(mCameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(back())
                .frameProcessor(new SampleFrameProcessor())
                .logger(loggers(
                        logcat(),
                        fileLogger(getActivity())
                ))
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NotNull CameraException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                .build();
    }

    @OnClick(R.id.fragment_camera_capture)
    void onCapturePicture() {
        PhotoResult photoResult = mFotoapparat.takePicture();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timeStamp + ".jpg";

        File path = new File(obtainBaseActivity().getExternalFilesDir("photos"), filename);
        final String imageLocation = path.getAbsolutePath();
        photoResult.saveToFile(path);


        photoResult
                .toBitmap(scaled(0.25f))
                .whenDone(new WhenDoneListener<BitmapPhoto>() {
                    @Override
                    public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                        String imageUrl = "";
                        if (bitmapPhoto == null) {
                            LogUtil.debug(TAG, "Couldn't capture photo.");
                        } else {
                            imageUrl = imageLocation;
                        }

                        final Intent intent = new Intent();
                        intent.putExtra(Common.BUNDLE_IMAGE_PATH, imageUrl);
                        getTargetFragment().onActivityResult(getTargetRequestCode(),
                                Activity.RESULT_OK,
                                intent);

                        getFragmentManager().popBackStackImmediate();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mPermissionManager.resultGranted(requestCode, permissions, grantResults)) {
            hasCameraPermission = true;
            mFotoapparat.start();
        }
    }

    private class SampleFrameProcessor implements FrameProcessor {
        @Override
        public void process(@NotNull Frame frame) {
            // Perform frame processing, if needed
        }
    }
}
