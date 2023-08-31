package com.example.maps.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.maps.R;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class MapsFragment extends Fragment {

    private MapView mapView;
    private LinearLayout layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MapKitFactory.setApiKey("Ваш_API_Ключ");
        MapKitFactory.initialize(requireContext());

        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.getMap().move(new CameraPosition(new com.yandex.mapkit.geometry.Point( 55.7558,  37.6176), 14.0f, 0.0f, 0.0f));

        mapView.getMap().getMapObjects().addPlacemark(new com.yandex.mapkit.geometry.Point(55.7558,  37.6176),
                ImageProvider.fromResource(requireContext(), R.drawable.bike_logo_max));



        layout = view.findViewById(R.id.ll_back);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, newFragment).commit();
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }
}