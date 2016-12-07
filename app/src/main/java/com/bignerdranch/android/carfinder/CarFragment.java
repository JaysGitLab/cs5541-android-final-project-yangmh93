package com.bignerdranch.android.carfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

/**
 * Created by Michael on 12/5/2016.
 */
public class CarFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_PHOTO = 2;


    private Car mCar;
    private File mPhotoFile;
    private Button mDateButton;
    private Button mTimeButton;
    private ImageView mPhotoView;
    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getActivity().getIntent();
        mCar = (Car) mIntent.getSerializableExtra("car");

        mPhotoFile = getPhotoFile(mCar);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_car, container, false);
        PackageManager packageManager = getActivity().getPackageManager();

        ImageButton mPhotoButton = (ImageButton) v.findViewById(R.id.parking_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) v.findViewById(R.id.parking_photo);
        updatePhotoView();

        Spinner mSpinner = (Spinner) v.findViewById(R.id.parking_spinner);
        mSpinner.setSelection(((ArrayAdapter)mSpinner.getAdapter()).getPosition(mCar.getType()));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCar.setType(parent.getItemAtPosition(position).toString());
                Toast.makeText(parent.getContext(), mCar.getType(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText mFloorField = (EditText)v.findViewById(R.id.floor);
        mFloorField.setText(mCar.getFloor());
        mFloorField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCar.setFloor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.park_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCar.getDate());
                dialog.setTargetFragment(CarFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        mTimeButton = (Button) v.findViewById(R.id.park_time);
        updateTime();
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCar.getDate());
                FragmentManager manager = getFragmentManager();
                dialog.setTargetFragment(CarFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });
        Button mSaveButton = (Button) v.findViewById(R.id.park_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getActivity(), CarFinderActivity.class);
                mIntent.putExtra("car", mCar);
                getActivity().setResult(Activity.RESULT_OK, mIntent);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        mCar.setDate(date);

        switch (requestCode) {
            case REQUEST_DATE:
                updateDate();
                break;
            case REQUEST_TIME:
                updateTime();
                break;
            case REQUEST_PHOTO:
                updatePhotoView();
                break;

        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.format("EEEE, MMMM d, yyyyy", mCar.getDate()));
    }

    private void updateTime() {
        mTimeButton.setText(DateFormat.format("h:mm a", mCar.getDate()));
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
    public File getPhotoFile(Car car) {
        File externalFilesDir = getContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, car.getPhotoFilename());
    }
}
