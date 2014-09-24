package minor.maps;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import java.util.ArrayList;
import java.util.List;

import javashitsszzzea.Positie;
import javashitsszzzea.SchepenInDeBuurt;
import javashitsszzzea.Ships;
import javashitsszzzea.Vaartuigen;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
//TODO tekstviews en button moeten boven gedeclareed worden en in een methode allemaal geinstantierd worden.
public class MainActivity extends FragmentActivity implements LocationListener {

	List<Vaartuigen> schepen;
	private GoogleMap mMap;
	Button btNext, btJa, btNee;
	TextView schipnaam,afstand, schip2, schip3, schip4;
	HttpURLConnectionLeague d;
	View l, snelheidEnWaterstandView;
	int minAfstandBoot = 200;
	boolean schipGevonden = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		l = (View) findViewById(R.id.idVakje);
		snelheidEnWaterstandView = (View) findViewById(R.id.idsnelheidEnWaterstandView);
		
		schipnaam = (TextView) findViewById(R.id.tvSchipnaam);
		afstand = (TextView) findViewById(R.id.tvAfstand);
		schip2 = (TextView) findViewById(R.id.tvSchip2);
		schip3 = (TextView) findViewById(R.id.tvSchip3);
		schip4 = (TextView) findViewById(R.id.tvSchip4);
		
		btNext = (Button) findViewById(R.id.btNext);
		btNext.setBackgroundColor(Color.TRANSPARENT);
		btJa = (Button) findViewById(R.id.btJa);
		btNee = (Button) findViewById(R.id.btNee);
		btJa.setVisibility(View.INVISIBLE);
		btNee.setVisibility(View.INVISIBLE);
		
		createOnClickListeners();
	}

	private void createOnClickListeners() {
		// TODO Auto-generated method stub
		btJa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				l.setVisibility(View.GONE);
				schipGevonden = true;
				snelheidEnWaterstandView.setVisibility(View.VISIBLE);
			}
		});
		
		btNee.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				schipnaam.setText("Kies uw Schip");
				afstand.setText(schepen.get(1).getNaam());
				schip2.setText(schepen.get(2).getNaam());
				schip3.setText(schepen.get(3).getNaam());
				schip4.setText(schepen.get(4).getNaam());
				btJa.setVisibility(View.INVISIBLE);
				btNee.setVisibility(View.GONE);
			}
		});
		
		
		
		btNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		d = new HttpURLConnectionLeague();
		List<Ships> l = new ArrayList<Ships>();
		l = d.getJson();
		mMap.setMyLocationEnabled(true);
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			onLocationChanged(location);
		}
		locationManager.requestLocationUpdates(provider, 1000, 0, this);

		for (int i = 0; i < l.size(); i++) {
			Positie pos = l.get(i).getPositie();
			double lat = pos.getLat();
			double lon = pos.getLon();
			LatLng latLon = new LatLng(lat, lon);
			double snelheid = pos.getSnelheid();
			String name = l.get(i).getScheepsnaam();
			if (snelheid < 0.1) {
				mMap.addMarker(new MarkerOptions()
						.position(latLon)
						.title(name)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.stilbootje)));
			} else {
				if (name == "") {
					name = "GhostShip";
				}
				mMap.addMarker(new MarkerOptions()
						.position(latLon)
						.title(name)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.bootje))
						.snippet("Snelheid : " + snelheid + " knts"));
				LatLng newLatLon = computeOffsetOrigin(latLon, -snelheid * 20,
						pos.getGraden());
				mMap.addPolyline(new PolylineOptions().add(latLon, newLatLon)
						.width(10).color(Color.RED));
			}
		}
		SchepenInDeBuurt s = d.getMyShip(location.getLatitude(),
				location.getLongitude());
		schepen = s.getVAARTUIGEN();
		Vaartuigen schip = s.getVAARTUIGEN().get(0);
		
		schipnaam.setText(schip.getNaam());
		String meters = String.valueOf(schip.getPositie().getMeters());
		afstand.setText(meters + " Meter");
	}

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);
		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		
		if (!schipGevonden) {
			SchepenInDeBuurt s = d.getMyShip(location.getLatitude(),
					location.getLongitude());
			int intMeters = s.getVAARTUIGEN().get(0).getPositie()
					.getMeters();
			checkInBuurtVanSchip(intMeters);
		}
		//DEBUG variablen die we kunnen gebruiken.
		float speed = location.getSpeed();
		float degrees = location.getBearing();
		String stringSpeed = String.valueOf(speed);
		String stringDegrees = String.valueOf(degrees);
		}

	private void checkInBuurtVanSchip(int intMeters) {
		if(intMeters<minAfstandBoot){
			btJa.setVisibility(View.VISIBLE);
			btNee.setVisibility(View.VISIBLE);
		}else{
			String meters = String.valueOf(intMeters);
			afstand.setText(meters + " Meter");
		}	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}
	
	public static LatLng computeOffsetOrigin(LatLng to, double distance,
			double heading) {
		heading = toRadians(heading);
		distance /= 6371009;
		double n1 = cos(distance);
		double n2 = sin(distance) * cos(heading);
		double n3 = sin(distance) * sin(heading);
		double n4 = sin(toRadians(to.latitude));
		// There are two solutions for b. b = n2 * n4 +/- sqrt(), one solution
		// results
		// in the latitude outside the [-90, 90] range. We first try one
		// solution and
		// back off to the other if we are outside that range.
		double n12 = n1 * n1;
		double discriminant = n2 * n2 * n12 + n12 * n12 - n12 * n4 * n4;
		if (discriminant < 0) {
			// No real solution which would make sense in LatLng-space.
			return null;
		}
		double b = n2 * n4 + sqrt(discriminant);
		b /= n1 * n1 + n2 * n2;
		double a = (n4 - n2 * b) / n1;
		double fromLatRadians = atan2(a, b);
		if (fromLatRadians < -PI / 2 || fromLatRadians > PI / 2) {
			b = n2 * n4 - sqrt(discriminant);
			b /= n1 * n1 + n2 * n2;
			fromLatRadians = atan2(a, b);
		}
		if (fromLatRadians < -PI / 2 || fromLatRadians > PI / 2) {
			// No solution which would make sense in LatLng-space.
			return null;
		}
		double fromLngRadians = toRadians(to.longitude)
				- atan2(n3, n1 * cos(fromLatRadians) - n2 * sin(fromLatRadians));
		return new LatLng(toDegrees(fromLatRadians), toDegrees(fromLngRadians));
	}
}
