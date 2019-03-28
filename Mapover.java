package com.example.socialnet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.example.socialnet.Categories.BRowD;
import com.example.socialnet.Maps.myLocationListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;



public class Mapover<OverlayController> extends MapActivity implements OnClickListener {
	
	class BRowD {
		 String bid= " ";
		 String bname= " ";
		 String baddress="";
		 String city="";
		 String state="";
		 String zip="";
		 String latitude ="";
		 String longitute ="";
		 String description ="";
		 String bemail = " ";
		 
		 String specials ="";
		 String coupon = " ";
		 String coupondesc = " ";
		 String waitime = " ";
		 String timeentry= " "; 
	     String image="";
	     
	    	 
	     
		BRowD(String S0, String S1, String S2, String S3,String S4, String S5, String S6,
				String S7, String S8,  String R0, String R1, String R2, String R3, String R4, 
				String R5){
	     	
			bid=S0;
			 bname= S1;
			 baddress= S2;
			 city= S3;
			 state=S4;
			 zip=S5;
			 latitude = S6;
			 longitute = S7;
			 description =S8;
			 
			 
			 image=R0;
			 specials = R1;
			 coupon =  R2;
			 coupondesc = R3;
			 waitime = R4;
			 timeentry= R5; 
		     
			

	     	}
		 }
	
	 Vector<BRowD> bdata;

	boolean emulator= false;
	boolean addfrnd=false;
	
	static int T_OUT=20000;
	private static final String TAG = null;
	private LocationManager lm;
    private LocationListener locationListener;
    private Location location, locationg;
	ImageButton  Search, Friends, Settings; 
	Button Mapfrnd,Interest ;
	List<Overlay> mapOverlays;
	Drawable drawable,drawable1,drawablefr;
	SocialitemizedOverlay itemizedOverlay, itemizedOverlay1, frndOverlay;
	MapView mapView;
    MapController mapViewController;
  //  OverlayController myoc;
    MyLocationOverlay myLocOverlay;

    public long starttime;
    GeoPoint point ;
    OverlayItem overlayitem;
    
	String FILENAME="socialnet_file1.txt";
	String FILENAME1="socialnet_file.txt";
	String[] text1=null;
	 String user="ptota";
	 String lati1;
	 String longi1;
	 double lat2=46.178676986;
		double longitude=-85.6786840;
	 int sizes;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapover);
        Intent currIntent =  getIntent();
		text1 = currIntent.getStringArrayExtra("bizdata");		        
		
		 BRowD brd;
	   		bdata= new Vector<BRowD>();
				  int l = 0; //counter for first data block
				  int records_num=text1.length/15;
				  int l2= records_num*9; //l2 is the start point of the second data block
				  while (l2 < text1.length ) {				  
//				      rd = new RowD(text1[i], "Hi, I am at Sbaucks", "kapoor@iit.edu",1);
				      brd = new BRowD(text1[l],text1[l+1],text1[l+2],text1[l+3],
				    		  text1[l+4],text1[l+5],text1[l+6],text1[l+7],text1[l+8],
				    		  text1[l2],text1[l2+1],text1[l2+2],text1[l2+3],
				    		  text1[l2+4],text1[l2+5]);
				      l=l+9;l2=l2+6;
				  bdata.add(brd);
				  }

				 
        mapOverlays= null;
        mapView = (MapView) findViewById(R.id.mapviewOver);
        mapViewController = mapView.getController();
  //      myoc = ((MapView) this.mapView).createOverlayController(); 
        
       // mapView.setBuiltInZoomControls(true);
        mapOverlays = mapView.getOverlays();        
      
        drawable = this.getResources().getDrawable(R.drawable.bizzpin);
        drawable1=this.getResources().getDrawable(R.drawable.pinorange);
        itemizedOverlay = new SocialitemizedOverlay(drawable1, mapView);
        itemizedOverlay1 = new SocialitemizedOverlay(drawable,mapView);
        
        /* start location stuff */
       
    if (!emulator){   
    	
  /* Location Stuff */      
        
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);    
       // Criteria criteria = new Criteria();
        
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
       // provider= lm.getBestProvider(criteria, true);
        location =lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        starttime =  System.currentTimeMillis() ; 
  
     //   if (location.getTime() < starttime-300000){
       // 	location =lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);        	
       // }
        boolean first=true;
        while ((location ==null) || (first)){
        
       	for (int repeat=1; repeat <= 10; repeat++)
       	{
        
        List<String> providers = lm.getProviders(true);
           
        for (int prov=1; prov<= providers.size(); prov++){
//        	Toast.makeText(this, "hi"+providers.get(l-1), Toast.LENGTH_SHORT).show();
        	if (providers.get(prov-1)!="passive")locationg =lm.getLastKnownLocation(providers.get(prov-1));
//        	if ((locationg.getTime() >= starttime) ||(locationcloseby(location,locationg))){

        	if (locationg.getTime() >= (starttime-200000)){
                    if (locationg.getAccuracy()<location.getAccuracy()){
                  	  location=locationg;}
        	  }
        	}
        	
        }
        
    	first= false;
        }
    	
		lat2=location.getLatitude();
        longitude=location.getLongitude();
    }

    
    
    
    
    
 /* Map stuff Starts */       
        
         GeoPoint point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
          overlayitem = new OverlayItem(point, "hi", "hi");
           itemizedOverlay.addOverlay(overlayitem);
   
   		mapView.getOverlays().add(itemizedOverlay);
//           mapOverlays.add(itemizedOverlay);
           
          
           
        

	     
	      int count=0;
		  while( count < bdata.size() ) {
			
				
			//	Toast.makeText(getApplicationContext(),"hey"+count, Toast.LENGTH_SHORT).show();
				
				point = new GeoPoint( (int) ((Double.parseDouble(bdata.get(count).latitude)+0.075*count) * 1E6),
						(int) (Double.parseDouble(bdata.get(count).longitute) * 1E6 + 0.025*count));
				
				    overlayitem = new OverlayItem(point, bdata.get(count).bname, "hi");
			        itemizedOverlay1.addOverlay(overlayitem);
				
				count++;
			};
	     
			
	    		
	// mapOverlays.clear();	        
			
			mapView.getOverlays().add(itemizedOverlay1);
//		mapOverlays.add(itemizedOverlay1);
//		if (addfrnd) Addfrndoverlay();
	
		/*
//		  Alternate location service 
          
		myLocOverlay = new MyLocationOverlay(this, mapView);
     	myLocOverlay.enableMyLocation();
     	mapView.getOverlays().add(myLocOverlay);
	*/
		Search=(ImageButton)findViewById(R.id.Search);
        Friends=(ImageButton)findViewById(R.id.Friends);
        Settings=(ImageButton)findViewById(R.id.Settings);
        Interest=(Button)findViewById(R.id.Interest);
        Mapfrnd=(Button)findViewById(R.id.Overlayfrnd);

      
        Search.setOnClickListener(this);
        Mapfrnd.setOnClickListener(this);      
        Friends.setOnClickListener(this);
        Settings.setOnClickListener(this);
        Interest.setOnClickListener(this);
        point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
        mapViewController.animateTo(point);
     //   mapView.postInvalidate();
	}
	/*
	@Override
		public void onConfigurationChanged(Configuration newConfig) {
		  super.onConfigurationChanged(newConfig);
		   setContentView(R.layout.mapover);
	   		mapView.getOverlays().add(itemizedOverlay);
	   		mapView.getOverlays().add(itemizedOverlay1);
			if (addfrnd){ mapView.getOverlays().add(frndOverlay);}
			point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
	        mapViewController.animateTo(point);

			
			
			Search=(Button)findViewById(R.id.Search);
	        Friends=(Button)findViewById(R.id.Friends);
	        Settings=(Button)findViewById(R.id.Settings);
	        Map=(Button)findViewById(R.id.Map);
	        Mapfrnd=(Button)findViewById(R.id.Overlayfrnd);

	      
	        Search.setOnClickListener(this);
	        Mapfrnd.setOnClickListener(this);      
	        Friends.setOnClickListener(this);
	        Settings.setOnClickListener(this);
	        Map.setOnClickListener(this);			
	}*/
        
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
    		if ((location.getTime()-starttime)> T_OUT)
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
		
	public void Addfrndoverlay(){
		
        drawablefr=this.getResources().getDrawable(R.drawable.pinblue);
        frndOverlay = new SocialitemizedOverlay(drawablefr, mapView);
		
        
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
		        frndOverlay.addOverlay(overlayitem);
			}
			i++;
		}
     
		
    		
// mapOverlays.clear();	        
	//mapOverlays.add(0,frndOverlay);
	mapView.getOverlays().add(frndOverlay);
	point = new GeoPoint( (int) (lat2 * 1E6),(int) (longitude * 1E6));
    mapViewController.animateTo(point);
  //  mapView.postInvalidate();
                        
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
	    			
	    		   case R.id.Overlayfrnd:
	    			   addfrnd=true;
	    			   Addfrndoverlay();
	    			   break;
	    			   
	    		   }
	}
		
	
}
