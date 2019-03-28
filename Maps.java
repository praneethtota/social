package com.example.socialnet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import java.util.StringTokenizer;

import com.example.socialnet.Socialnet.myLocationListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Maps extends MapActivity implements OnClickListener {
	
	static int T_OUT=300;
	private static final String TAG = null;
	private LocationManager lm;
    private LocationListener locationListener;
    private Location location, locationg;
	ImageButton  Search, Friends, Settings;
	Button Interest ;
	List<Overlay> mapOverlays;
	Drawable drawable,drawable1;
	SocialitemizedOverlay itemizedOverlay, itemizedOverlay1;
	MapView mapView;
    MapController mapViewController;
    public long starttime;
    
	String FILENAME="socialnet_file1.txt";
	String FILENAME1="socialnet_file.txt";
	String[] text1=null;
	 String user="ptota";
	 String lati1;
	 String longi1;
	 int sizes;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        mapOverlays= null;
        mapView = (MapView) findViewById(R.id.mapview);
        mapViewController = mapView.getController();
       // mapView.setBuiltInZoomControls(true);
        
        
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.pinblue);
        drawable1=this.getResources().getDrawable(R.drawable.pinorange);
        itemizedOverlay = new SocialitemizedOverlay(drawable1, mapView);
        itemizedOverlay1 = new SocialitemizedOverlay(drawable,mapView);

  /* Location Stuff */      
        
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);    
        Criteria criteria = new Criteria();
        
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
       // criteria.setAltitudeRequired(false);
        //criteria.setBearingRequired(false);
        //criteria.setCostAllowed(true);
       // criteria.setPowerRequirement(Criteria.POWER_HIGH); 
        locationListener = new myLocationListener();

        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER, 
                 0, 
                 0, 
                 locationListener);   
     
        lm.requestLocationUpdates(
        		LocationManager.NETWORK_PROVIDER, 
                 0, 
                 0, 
                 locationListener);   
        String provider ;
        provider= lm.getBestProvider(criteria, true);
     //   location =lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        location =lm.getLastKnownLocation(provider);
        starttime =  System.currentTimeMillis() ; 
  
        //if (location.getTime() < starttime-300000){
        	//location =lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);        	
        //}
        boolean first=true;
        while ((location ==null) || (first)){
       	for (int repeat=1; repeat <= 10; repeat++)
       	{
        
        List<String> providers = lm.getProviders(true);
           
        for (int l=1; l<= providers.size(); l++){
//        	Toast.makeText(this, "hi"+providers.get(l-1), Toast.LENGTH_SHORT).show();
        	if (providers.get(l-1)!="passive")locationg =lm.getLastKnownLocation(providers.get(l-1));
//        	if ((locationg.getTime() >= starttime) ||(locationcloseby(location,locationg))){
            if(location==null){location=locationg;}
        	if (locationg.getTime() >= (starttime - 300000)){
                    if (locationg.getAccuracy()<location.getAccuracy()){
                  	  location=locationg;}
        	  }
        	}
        	
        }
       	first=false;
        }
         
        double lat2;//=46.178676986;
		double longitude;//=-85.6786840;
        lat2=location.getLatitude();
        longitude=location.getLongitude();
        Toast.makeText(this,"Finally choosing "+location.getProvider()+ " "+ (starttime-location.getTime()),
    			Toast.LENGTH_SHORT).show();
           
          
            
/* Going to DATABASE */
           try { 
	            
	    	   InputStream in=openFileInput(FILENAME1);
	    	     if(in!=null){
	    	    	 InputStreamReader tmp= new InputStreamReader(in);
	    	    	 BufferedReader reader=new BufferedReader(tmp);
                   user=reader.readLine().toString();
                 //  pass=reader.readLine().toString();
                in.close();
	    	    	 
	    	     }
		
		 } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//	Toast.makeText(getApplicationContext(),user,Toast.LENGTH_LONG).show();
		HttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/mapand");
	        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>(1);
	        nvps.add(new BasicNameValuePair("usr_i", user));
	       
	        try {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				 StringWriter sw = new StringWriter();
				    PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			}
	        try {
	        	HttpResponse res =client.execute(post);
	        	 Log.v(TAG, res.getStatusLine().toString());
	              HttpEntity responseEntity = res.getEntity();
	              Log.v(TAG, "Set response to responseEntity");
	             // text1=HttpHelper.request(res);
	              text1=httpjsonhelp.request(res);
	            // sizes=httpjsonhelp.size;                
	    //    contact_name.setText(text1[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.v(TAG, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v(TAG, e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */

			
			
			 /* Map stuff Starts */       
	        
	        GeoPoint point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
	        OverlayItem overlayitem = new OverlayItem(point, "hi", "hi");
	           itemizedOverlay.addOverlay(overlayitem);
	   
	           mapOverlays.add(itemizedOverlay);

			
			int i=0; String STR = "EOVAL";
	      double lat1=0;
	      double longi2=0;
			while( text1[i].compareTo(STR)!=0) {
			
				
			//	Toast.makeText(getApplicationContext(),text1[i], Toast.LENGTH_LONG).show();
				
				if((i % 3)==1)
				{
					lat1=Double.parseDouble(text1[i]);	
				}
				
				if((i%3)==2)
				{
				longi2=Double.parseDouble(text1[i]);
				point = new GeoPoint( (int) (lat1 * 1E6),(int) (longi2 * 1E6));
				    overlayitem = new OverlayItem(point, text1[i-2], "hi");
			        itemizedOverlay1.addOverlay(overlayitem);
				}
				i++;
			}
	     
			
	    		
	// mapOverlays.clear();	        
		mapOverlays.add(itemizedOverlay1);
	   
	
		Search=(ImageButton)findViewById(R.id.Search);
        Friends=(ImageButton)findViewById(R.id.Friends);
        Settings=(ImageButton)findViewById(R.id.Settings);
        Interest=(Button)findViewById(R.id.Interest);
        
       Search.setOnClickListener(this);
       
        
        Friends.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Interest.setOnClickListener(this);
        point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
        mapViewController.animateTo(point);
        
	}
	
        
	@Override
	    protected boolean isRouteDisplayed() {
	        return false;
	    }
	
	class myLocationListener implements LocationListener {

    	public void onLocationChanged(Location location) {
    		// TODO Auto-generated method stub
    		final String TAG = "search";
    		double lat, longitude;
    	//	String  lati, longi;
    	//	user_id="ptota";
    	//	lati=null;
    		//longi=null;
    		lat=location.getLatitude();
    		longitude= location.getLongitude();
    		//lat=14.178676986;
    		//longitude=56.6786840;
    		if ((location.getTime()-starttime)> T_OUT*1000)
    		{lm.removeUpdates(locationListener);}
    		
		        }

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	}
		
	
	public void onClick(View v) {
	    		
	    		Intent i;
	    		
	    		// TODO Auto-generated method stub
	    		   switch(v.getId())
	    		   {
	    		   case R.id.Friends: 
	    			   i=new Intent(this, Friends.class);
	    			   startActivity(i);
	    			   break;
	    		   
	    		   case R.id.Settings:
	    			   i= new Intent(this, Settings.class);
	    			   startActivity(i);
	    			   break;
	    			   
	    		   case R.id.Interest:
	    			   i= new Intent(this, Maps.class);
	    			   startActivity(i);
	    			   break;
	    			   
	    		   case R.id.Search:
	    			   i= new Intent(this, Socialnet.class);
	    			   startActivity(i);
	    			   break;
	    		   }
	}
		
}

