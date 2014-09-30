package minor.maps;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javashitsszzzea.Meetstations;
import javashitsszzzea.SchepenInDeBuurt;
import javashitsszzzea.Ships;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
 
public class HttpURLConnectionLeague extends AsyncTask<String,Void,String>{
	//Deze word wel hier geset
	String URL = "http://ship.railplanet.eu/getships.php";
	
	Gson gson = new Gson();
	ArrayList<Ships> list = new ArrayList<Ships>();
	public ArrayList<Ships> getJson(){
		
		try {
			String output = null;
	        output = new HttpURLConnectionLeague().execute(URL).get();
			String json = output;
			Ships[] s = gson.fromJson(json, Ships[].class);
			for(int i = 0; i<s.length;i++){
				list.add(s[i]);
			}
		} catch (Exception e) {
			Log.e("exeption log", e.toString());
			e.printStackTrace();
		}
		return list;
		
	}
	
	public SchepenInDeBuurt getMyShip(double lat, double lon){
		String output = null;
		SchepenInDeBuurt s = null;
		try {
			URL = "http://ship.railplanet.eu/api/vaartuig.php?lat="+lat+"&lon="+lon+"&limit=5&toon_naam";
			output = new HttpURLConnectionLeague().execute(URL).get();
			String json = output;
			s = gson.fromJson(json, SchepenInDeBuurt.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	public Meetstations getFirstMeetStation(double lat, double lon){
		String output = null;
		Meetstations m = null;
		try {
			URL = "http://ship.railplanet.eu/api/meetstation.php?toon_meetgegevens&lat="+lat+"&lon="+lon+"&limit=1";
			output = new HttpURLConnectionLeague().execute(URL).get();
			String json = output;
			m = gson.fromJson(json, Meetstations.class);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return m;
	}

	@Override
	protected String doInBackground(String... params) {
		String line = null;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(params[0]);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
		}catch(Exception e){
			Log.e("http exception : ", e.toString());
		}
		return line;
	}
}

