package com.example.canma.eurekaswe.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.ToggleButton;

import com.example.canma.eurekaswe.EurekaApplication;
import com.example.canma.eurekaswe.MainActivity;
import com.example.canma.eurekaswe.R;
import com.example.canma.eurekaswe.data.CategoryFormat;
import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.CreateData;
import com.example.canma.eurekaswe.data.LatLong;
import com.example.canma.eurekaswe.data.Markers;
import com.example.canma.eurekaswe.data.PassedPolySmth;
import com.example.canma.eurekaswe.data.Points;
import com.example.canma.eurekaswe.data.Polylines;
import com.example.canma.eurekaswe.data.TimeFormat;
import com.example.canma.eurekaswe.data.TimeInfo;
import com.example.canma.eurekaswe.interfaces.calls.CreateApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.*;

import com.google.gson.Gson;
import com.tumblr.remember.Remember;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ListoryCreateMapFragment extends Fragment implements OnMapReadyCallback {

    View view = null;

    private static final String TAG = "MapActivity";
    MainActivity mainActivity;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 16f;
    Circle circle;
    Marker marker1;
    Marker marker2;
    Polyline line;


    private Integer m_Text = 0;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    Unbinder unbinder;
    //widgets
    //private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker;
    Address lastAddress;
    private Location mLastKnownLocation;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    // private FusedLocationProviderClient mFusedLocationProviderClient;
    // private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private Markers mPlace;
    private Marker mMarker;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    ArrayList<Marker> markers = new ArrayList<Marker>();



    @Inject
    @Named("regular")
    Retrofit retrofit;

    @BindView(R.id.input_search)
    EditText mSearchText;

    @BindView(R.id.ic_gps)
    ImageButton gpsCurrentLocation;

    @BindView(R.id.delete_button)
    ImageButton deleteMarkersutton;

    @BindView(R.id.create_button)
    Button createButton;

    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;

    ArrayList<Points> points = new ArrayList<Points>();

    @OnClick(R.id.create_button)
    public void createLatLong(){

        boolean checked = toggleButton.isChecked();
        if(checked){
            if(circle!=null){
                Marker marker= markers.get(0);

                Markers marker1= new Markers(circle.getCenter().latitude,circle.getCenter().longitude,circle.getRadius()*2.05,mSearchText.getText().toString(),"#0000ff");

                EventBus.getDefault().post((new LatLong(marker1,null)));

                getFragmentManager().popBackStack();

            }


        }else{
            if(markers.size()>1){
                points.remove(0);
                Log.d(TAG,"lat lng array for me "+points.toString());

                Polylines polylines= new Polylines(points,"#0000ff",mSearchText.getText().toString());
                EventBus.getDefault().post(new LatLong(null,polylines));

                getFragmentManager().popBackStack();

            }

        }

    }
    @OnClick(R.id.ic_gps)
    public void gpsImagePressed(){

        geoLocate();
    }

    @OnClick(R.id.delete_button)
    public void deleteButtonPressed(){
        mMap.clear();
        points.clear();
        if(circle!=null){
            circle.remove();
            circle = null;
        }
        if(line!=null){
            line.remove();
        }
        markers.clear();
        for(Marker marker:markers){

            marker.remove();


        }

    }



    @OnClick(R.id.circle_button)
    public void markerButtonPressed(){
        boolean checked = toggleButton.isChecked();
        if(checked){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("Magnitude for the marker in meters.");

// Set up the input
            final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = Integer.parseInt(input.getText().toString());
                    if(circle != null){
                        circle = null;
                    }
                    circle = drawCircle(m_Text/2,new LatLng(lastAddress.getLatitude(),lastAddress.getLongitude()));

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();


        }else{

            Toast.makeText(this.getActivity(), "Please select Marker to select a diameter.", Toast.LENGTH_SHORT).show();


        }

    }
    private Circle drawCircle(int radius, LatLng latLng){


        CircleOptions options = new CircleOptions()
                .center(latLng)
                .radius(radius)
                .fillColor(0x33FF0000)
                .strokeColor(Color.RED);

        return mMap.addCircle(options);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        ((EurekaApplication) getActivity().getApplication()).getNetComponent().inject(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Build.VERSION.SDK_INT>24)
        getLocationPermission();

    }

    private void getDeviceLocation(){
        moveCamera(new LatLng(41.0848, 29.0510),
                DEFAULT_ZOOM,
                "My Location");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_listory_create_map_fragment, container, false);


        unbinder = ButterKnife.bind(this, view);


        return view;
    }
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment;
        if (Build.VERSION.SDK_INT < 21) {
            mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.mapNew);

        } else {

            mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapNew);
        }
        mapFragment.getMapAsync(this);
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
/*
            map=googleMap;
            LatLng pp = new LatLng(11.55,104.892);
            MarkerOptions option = new MarkerOptions();
            option.position(pp).title("jdshj");
            map.addMarker(option);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(pp,15));
        */
        Toast.makeText(this.getActivity(), "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               // return;
            }
            mMap.setMyLocationEnabled(true);





            if(mMap != null){
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        Geocoder gc = new Geocoder(getActivity());
                        LatLng ll = marker.getPosition();
                        double lat = ll.latitude;
                        double lng = ll.longitude;

                        List<Address> list = null;
                        try {
                            list = gc.getFromLocation(ll.latitude, ll.longitude, 1);
                        }catch(IOException e){

                            e.printStackTrace();
                        }
                        if(list.size() > 0) {
                            lastAddress = list.get(0);

                            setMarker(ll, lastAddress.getAddressLine(0));
                            if(circle != null){
                                double radius=circle.getRadius();
                                circle = drawCircle((int)radius,new LatLng(lastAddress.getLatitude(),lastAddress.getLongitude()));
                            }


                        }

                    }
                });



            }




        }

        init();
    }

    private void init(){
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });


        hideSoftKeyboard();

    }








/*
    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this.getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });

        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{
                        Log.d(TAG, "onClick: place info: " + mPlace.toString());
                        mMarker.showInfoWindow();
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException: " + e.getMessage() );
                }
            }
        });

        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });

        hideSoftKeyboard();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }
 */

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();
        Log.d(TAG, "geoLocate: geolocating now kdsjlkdjkaljds "+ searchString);
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            lastAddress = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + lastAddress.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(lastAddress.getLatitude(), lastAddress.getLongitude()), DEFAULT_ZOOM,
                    lastAddress.getAddressLine(0));
        }
    }


    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        if(mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            setMarker(latLng, title);
        }else{

        }

        hideSoftKeyboard();
        }
    }

    private void setMarker(LatLng latLng, String title) {
        MarkerOptions options = new MarkerOptions()
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(latLng)
                .title(title);
        boolean checked = toggleButton.isChecked();
        if(checked) {
            mMap.clear();
            markers.add(mMap.addMarker(options));
        }else{
            Marker marker = mMap.addMarker(options);
            markers.add(marker);
            Points point = new Points(marker.getPosition().latitude,marker.getPosition().longitude);
            points.add(point);
            if(markers.size()>1){
                drawLine();

            }
        }

    }

    private void drawLine(){

        PolylineOptions options = new PolylineOptions()
                .color(Color.BLUE)
                .width(20);


        for(int i=markers.size()-2;i<markers.size();i++){

            options.add(markers.get(i).getPosition());

        }
        line = mMap.addPolyline(options);


    }


    private void hideSoftKeyboard(){
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this.getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this.getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
            getFragmentManager().popBackStack();
        }
    }
    private int convertZoneRadiusToPixels(double lat, double lng, double radiusInMeters) {
        double EARTH_RADIUS = 6378100.0;
        double lat1 = radiusInMeters / EARTH_RADIUS;
        double lng1 = radiusInMeters / (EARTH_RADIUS * Math.cos((Math.PI * lat / 180)));

        double lat2 = lat + lat1 * 180 / Math.PI;
        double lng2 = lng + lng1 * 180 / Math.PI;

        Point p1 = mMap.getProjection().toScreenLocation(new LatLng(lat, lng));
        Point p2 = mMap.getProjection().toScreenLocation(new LatLng(lat2, lng2));
        return Math.abs(p1.x - p2.x);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }




}








