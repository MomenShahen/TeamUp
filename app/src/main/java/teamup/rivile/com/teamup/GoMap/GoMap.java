package teamup.rivile.com.teamup.GoMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import teamup.rivile.com.teamup.R;

public class GoMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    View intro;
    //    MovableFloatingActionButton floatingActionButton;
//    int fabState = 0;
    //    View help;
//    AlertDialog dialog2;
    LinearLayout cardView;


    RelativeLayout now;

    Integer[] imageArray = { R.drawable.ic_menu_manage, R.drawable.ic_location,
            R.drawable.ic_menu_camera, R.drawable.ic_menu_gallery };
    String[] textArray = { "clouds", "mark", "techcrunch", "times" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_value_layout, textArray, imageArray);
        spinner.setAdapter(adapter);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(30.5883084, 31.4831937);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(sydney);
        mMap.clear();
        markerOptions.icon(bitmapDescriptorFromVector(this, R.drawable.ic_location));
        mMap.addMarker(markerOptions);

        LatLng s = new LatLng(-64, 101);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.clear();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        MarkerOptions m = new MarkerOptions();
        m.position(sydney);
        mMap.clear();
        m.icon(bitmapDescriptorFromVector(this, R.drawable.ic_location));
        mMap.addMarker(m);
//        markerOptions.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sydney.latitude, sydney.longitude), 14f));

//        mMap.animateCamera(CameraUpdateFactory.newLatLng(s));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                intro = inflater.inflate(R.layout.marker_view, null);

                return intro;
            }

            @Override
            public View getInfoContents(Marker marker) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                intro = inflater.inflate(R.layout.marker_view, null);

                return intro;
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_location);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
