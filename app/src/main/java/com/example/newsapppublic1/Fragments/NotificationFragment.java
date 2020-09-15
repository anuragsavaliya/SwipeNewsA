package com.example.newsapppublic1.Fragments;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.newsapppublic1.LocationUtils.GpsTracker;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.FragmentNotificationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateRemoteModel;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class NotificationFragment extends Fragment {
    private static final int REQUEST_LOCATION = 200;
    FragmentNotificationBinding binding;
    LocationManager nManager;
    String TAG = "localeex";
    FirebaseTranslator translator;
    String teanslatedStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        getMyLocation();

        //   Locale locale =new Locale(Locale.getDefault().getLanguage());
        Locale locale = new Locale("gu");
        Log.d(TAG, "onCreateView: " + locale);
        Log.d(TAG, "onCreateView: " + locale.getCountry());
        Log.d(TAG, "onCreateView: " + locale.getDisplayLanguage());
        Log.d(TAG, "onCreateView: " + locale.getDisplayVariant());
        Log.d(TAG, "onCreateView: " + locale.getLanguage());
        Log.d(TAG, "onCreateView: " + locale.getDisplayCountry());

        String str = firebaseLangaugeTransfer("anurag savaliya helo this is my word keyboard unit mouse computer");
        binding.tvLan.setText(str);
        return binding.getRoot();
    }

    public String firebaseLangaugeTransfer(String str) {


        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(FirebaseTranslateLanguage.EN)
                .setTargetLanguage(FirebaseTranslateLanguage.HI)
                .build();
        translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelManager modelManager = FirebaseModelManager.getInstance();

        modelManager.getDownloadedModels(FirebaseTranslateRemoteModel.class)
                .addOnSuccessListener(new OnSuccessListener<Set<FirebaseTranslateRemoteModel>>() {
                    @Override
                    public void onSuccess(Set<FirebaseTranslateRemoteModel> firebaseTranslateRemoteModels) {
                        Log.d(TAG, "onSuccess: 89 " + firebaseTranslateRemoteModels.size());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 94 " + e);
            }
        });

        translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: 101 model download");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 106 " + e);
            }
        });
        translator.translate(str)
                .addOnSuccessListener(new OnSuccessListener<String>() {


                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "onSuccess: 113 " + s);
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                        teanslatedStr = s;
                        binding.tvLan.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: 119" + e);
                teanslatedStr = "null";
            }
        });
        return teanslatedStr;
    }


    private void getMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        GpsTracker gpsTracker = new GpsTracker(getActivity());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);
                Toast.makeText(getContext(), cityName + "\n" + stateName + "\n" + countryName, Toast.LENGTH_SHORT).show();
                Log.d("lllll", "onCreateView: " + cityName + "\n" + stateName + "\n" + countryName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            gpsTracker.showSettingsAlert();
        }
    }


}
