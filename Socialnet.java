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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/* NOTES:CLEAN LATER:USER_ID =-1 when not logged in and location data sent */

public class Socialnet extends Activity implements OnClickListener {
	/* Called when the activity is first created. */
	Button submit, Friendsnearme, Interest;
	ImageButton Search, Friends, Settings; 
	Button Favorites;
	//Button Categories;
	EditText edittext01, edittext02;
	TextView tv1, tv2;
	String search1, search2;
	String FILENAME = "socialnet_file.txt";
	String FILENAME1="socialnet_file1.txt";
	String[] locationString={"My Location On","Accuracy Level: Coarse (Network)"};
	private LocationManager lm;
	private LocationListener locationListener;
	private Location location, currentloc;
	ListView lv;
	public String user_id = "ptota";
	private static final String TAG = "search";
	public int T_LAG = 1000*60*5; /* intervals of 5 minutes */

	/*  Favorite categories, sub */

	String FILENAMEV = "grid_favs.txt";
	int[] cat = {-1, -1, -1, -1, -1, -1, -1, -1 ,-1};
	int[] subcat = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.main);
		
		 try { 
	            InputStream inv = openFileInput(FILENAMEV);
	            
		    	     if (inv!=null){
		    	    	   InputStreamReader tmp= new InputStreamReader(inv);
		    	    	   BufferedReader reader=new BufferedReader(tmp);
		    	    	   
		    	    	   int i=0;
		    	    	   while (i < cat.length)  
		    	    		   {
		    	    		   subcat[i]=reader.read(); 
		    	    		   cat[i] = reader.read();	    	    		   
		    	    		   i++;
		    	     }
	            //  pass=reader.readLine().toString();
		    	    	
	           inv.close();	
		    	     }
		 } catch (FileNotFoundException e) {
						try {
							OutputStreamWriter fosv;
							fosv = new OutputStreamWriter(openFileOutput(FILENAMEV, Context.MODE_PRIVATE));
							   /* set default favorites */
						     cat[0]= 0; cat[1]= 1 ; cat[2]= 2 ;
						     subcat[0] = 1; subcat[1] =2; subcat[2]=1;
						     int j=0; //String buf=null;
						     while (j < cat.length)
						         {
						    	 fosv.write(subcat[j]);
						    	 //fos.write(" ");
						    	 fosv.write(cat[j]);
						    	 //buf = buf  + subcat[j] + "\n"+ cat[j]+ "\n ";
						    	 j++;
						     }
						
						//fos.write(buf);
		                 fosv.close();
						
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// TODO Auto-generated catch block
                        	 catch (IOException e1) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
											
					} 

				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    			
//		InputStream in = new openFileStream
				user_id="-1";
				try{
				InputStream in = openFileInput(FILENAME);					
				if(in!=null){
			    	    	 InputStreamReader tmp= new InputStreamReader(in);
			    	    	 BufferedReader reader=new BufferedReader(tmp);
			    	         user_id=reader.readLine().toString();
			    	         String pass = reader.readLine().toString();
			    	         locationString[0]=reader.readLine().toString();
			    	         locationString[1]=reader.readLine().toString();
//				                user_data[1]=reader.readLine().toString();
			    	         in.close();	 

			    	         Intent svc= new Intent(this, CheckMessage.class);
			    	  
			    	         try{
			    	        	 stopService(svc);
			    	         	//CheckMessage.setMainActivity(this);
			    	         	//Intent svc= new Intent(this, CheckMessage.class);
			    	         	 startService(svc);
			    	         }
			    	         catch (Exception e){
			    	        	 startService(svc);
			    	        	 
			    	         }
			    	          
			    			 
						}	    	     
			     	   }	
		       	  catch (FileNotFoundException e) {
			 		 
			 			// TODO Auto-generated catch block
			 			e.printStackTrace();
			 		} catch (IOException e) {
			 			// TODO Auto-generated catch block
			 			e.printStackTrace();
			 		}
		
		
				
		TextView textuser= (TextView) findViewById(R.id.TextViewlogin);
		if (user_id.contentEquals("-1")) {textuser.setText("Hi,login");}
		else {textuser.setText("Hi,"+user_id);}
//		View mainv;

	//	mainv = findViewById(R.id.mainview);
      //  mainv.setAdapter(new GridAdapter(this));
		//MyGridview
		newadapter();
		
        		
		//submit=(Button)findViewById(R.id.Submit);
		Search = (ImageButton) findViewById(R.id.Search);
		Friends = (ImageButton) findViewById(R.id.Friends);
		Settings = (ImageButton) findViewById(R.id.Settings);
		Interest = (Button) findViewById(R.id.Interest);
		Favorites = (Button) findViewById(R.id.Favorites);
		//Friendsnearme=(Button)findViewById(R.id.Friendsnearme);
	//	Categories=(Button)findViewById(R.id.Categories);
	//	 edittext01=(EditText)findViewById(R.id.EditText01);
		// edittext02=(EditText)findViewById(R.id.EditText02);

		// search1= edittext01.getText().toString();
		// search2= edittext02.getText().toString();

		
		
		  lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); 
		  Criteria criteria =  new Criteria(); 
		//  criteria.setAccuracy(Criteria.ACCURACY_FINE);
		 // criteria.setAltitudeRequired(false);
		 // criteria.setBearingRequired(false); criteria.setCostAllowed(true); //
		  criteria.setPowerRequirement(Criteria.POWER_HIGH); //Why So??
		  
		  String provider;
		  
/*		  if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			  if( !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){		  
			  Toast.makeText(Socialnet.this, "Location services are not enabled",Toast.LENGTH_SHORT).show();			  		    		  
		  } else Toast.makeText(Socialnet.this, "Your GPS  service is not enabled",Toast.LENGTH_SHORT).show();
		  }else  
			  if( !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){		  
				  Toast.makeText(Socialnet.this, "Your Network Location services are not enabled",Toast.LENGTH_SHORT).show();
			  }
	*/
		  
		  locationListener= new myLocationListener();
		  if (!(gpshelper.services(Socialnet.this, lm)==0) && locationString[0].equals("My Location On"))
			     
		  {
		provider = lm.getBestProvider(criteria, true);   
		  lm.requestLocationUpdates(provider, 0, 0, locationListener);

		  if ((locationString[1].equals("Accuracy Level: Coarse (Network)"))){
			  Toast.makeText(Socialnet.this, "Coarse Location services enabled",Toast.LENGTH_SHORT).show();			  		    		  

			  lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, T_LAG, 0, locationListener);
//			  location =lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			  }
		else{	 

		Toast.makeText(Socialnet.this, "Fine Location services enabled",Toast.LENGTH_SHORT).show();			  		    		  
		
		  lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, T_LAG, 0, locationListener);
		  lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, T_LAG,
						  0, locationListener); 

		}  
		//	  location =lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		  //location =lm.getLastKnownLocation("gps");
		 /* // if(provider=="LocationManager.GPS_PROVIDER") 
		  location =lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); 
		  else location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		 */
				
		/*
		  if ((provider=="LocationManager.GPS_PROVIDER") || (provider=="gps"))
		  { */
			  
			  
	//		  location =lm.getLastKnownLocation(provider);
		//  }  
			//  else 
		 
         location =lm.getLastKnownLocation(provider);		  
		 double lat= 41.78676986; 
		 double longitude= -87.86840; 
		  
		  currentloc=location;
		  lat=location.getLatitude(); 
		  longitude=location.getLongitude();
	
	//		Toast.makeText(Socialnet.this, location.getProvider()+ " " +(int)location.getAccuracy(),
		//			Toast.LENGTH_SHORT).show();
		  
		 		   
		/*   
		  try {
		  
		  InputStream in=openFileInput(FILENAME); 
		  if(in!=null){
		  InputStreamReader tmp= new InputStreamReader(in); 
		  BufferedReader
		  reader=new BufferedReader(tmp); 
		  user_id=reader.readLine().toString();
		  // pass=reader.readLine().toString(); in.close(); 
		  }
		  
		 } 
		  catch (FileNotFoundException e) { 
			  // TODO Auto-generated catch		  block 
			  e.printStackTrace(); } 
	      catch (IOException e) { 
			  // TODO Auto-generated catch block 
	    	  e.printStackTrace(); }
		  */
		     
		     new SendDataULoc().execute(lat,longitude);
		  }    
		  
		  else
		  {
			  if (!(locationString[0].equals("My Location On")))
			      Toast.makeText(Socialnet.this, "Location services are not enabled in Settings",Toast.LENGTH_SHORT).show();			  		    		  
			  else
				  Toast.makeText(Socialnet.this, "Location services are not enabled on your phone",Toast.LENGTH_SHORT).show();  
				  
			  
		  }
/*		  HttpClient client02 = new DefaultHttpClient(); 
		  HttpPost post02 = new HttpPost("http://mobileswan.com/index.php?/socialnet/geoand");
		 List<BasicNameValuePair> nvps02 = new ArrayList<BasicNameValuePair>(2); 
		 nvps02.add(new BasicNameValuePair("user_id", user_id)); 
		 nvps02.add(new BasicNameValuePair("lat", Double.toString(lat))); 
		 nvps02.add(new BasicNameValuePair("longitude", Double.toString(longitude))); 
		 try {
		 post02.setEntity(new UrlEncodedFormEntity(nvps02, HTTP.UTF_8)); }
		 catch (UnsupportedEncodingException e) { 
			 // TODO Auto-generated catch block 
			 StringWriter sw = new StringWriter(); 
			 PrintWriter pw = new PrintWriter(sw); 
			 e.printStackTrace(pw); 
			 } 
		 try { 
			 HttpResponse res =client02.execute(post02); 
			 Log.v(TAG,res.getStatusLine().toString()); 
			 HttpEntity responseEntity = res.getEntity(); 
			 Log.v(TAG, "Set response to responseEntity");
		   String[] text1=HttpHelper.request(res);
		 
		  // contact_name.setText(text1[0]); 
		   } catch (ClientProtocolException e) { 
			   // TODO Auto-generated catch block 
			   Log.v(TAG, e.toString());
		  e.printStackTrace(); 
		  } catch (IOException e) { 
			  Log.v(TAG, e.toString()); 
			  // TODO Auto-generated catch block
		  e.printStackTrace(); 
		  }
		  
	*/	  		 
		  
		  
/*		  if (provider=="LocationManager.GPS_PROVIDER")
		   lm.requestLocationUpdates( LocationManager.GPS_PROVIDER, 300000,
		  1000, locationListener); 
		  
		  else 
			  lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 1000, locationListener);
	*/	
		Search.setOnClickListener(this);
		Friends.setOnClickListener(this);
		Settings.setOnClickListener(this);
	    Interest.setOnClickListener(this);
	    Favorites.setOnClickListener(this);
	  //Friendsnearme.setOnClickListener(this);
	  //Categories.setOnClickListener(this);
	}

	public class SendDataULoc extends AsyncTask<Double, Void, Void> {

	@Override
	protected Void doInBackground(Double... num) {
		// TODO Auto-generated method stub
		  HttpClient client02 = new DefaultHttpClient(); 
		  HttpPost post02 = new HttpPost("http://mobileswan.com/index.php?/socialnet/geoand");
		 List<BasicNameValuePair> nvps02 = new ArrayList<BasicNameValuePair>(2); 
		 nvps02.add(new BasicNameValuePair("user_id", user_id)); 
		 nvps02.add(new BasicNameValuePair("lat", Double.toString(num[0]))); 
		 nvps02.add(new BasicNameValuePair("longitude", Double.toString(num[1]))); 
		 try {
		 post02.setEntity(new UrlEncodedFormEntity(nvps02, HTTP.UTF_8)); }
		 catch (UnsupportedEncodingException e) { 
			 // TODO Auto-generated catch block 
			 StringWriter sw = new StringWriter(); 
			 PrintWriter pw = new PrintWriter(sw); 
			 e.printStackTrace(pw); 
			 } 
		 try { 
			 HttpResponse res =client02.execute(post02); 
			 Log.v(TAG,res.getStatusLine().toString()); 
			 HttpEntity responseEntity = res.getEntity(); 
			 Log.v(TAG, "Set response to responseEntity");
		   String[] text1=HttpHelper.request(res);
		 
		  // contact_name.setText(text1[0]); 
		   } catch (ClientProtocolException e) { 
			   // TODO Auto-generated catch block 
			   Log.v(TAG, e.toString());
		  e.printStackTrace(); 
		  } catch (IOException e) { 
			  Log.v(TAG, e.toString()); 
			  // TODO Auto-generated catch block
		  e.printStackTrace(); 
		  }
		return null;
	}
	
	}
	
	 class myLocationListener implements LocationListener {

		 public  int D_MINUTES = 1000  * 60 * 4;

	
		 public boolean isBetterLocation(Location location1, Location currentBestLocation) {
			    if (currentBestLocation == null) {
			        // A new location is always better than no location
			        return true;
			    }

			    // Check whether the new location fix is newer or older
			    long timeDelta = location1.getTime() - currentBestLocation.getTime();
			    boolean isSignificantlyNewer = timeDelta > D_MINUTES;
			    boolean isSignificantlyOlder = timeDelta < - D_MINUTES;
			    boolean isNewer = timeDelta > 0;
//				Toast.makeText(Socialnet.this, location1.getProvider()+ " " +(int)location.getAccuracy(),
	//					 Toast.LENGTH_SHORT).show();

			    // If it's been more than two minute since the current location, use the new location
			    // because the user has likely moved
			    if (isSignificantlyNewer) {
			        return true;
			    // If the new location is more than two minutes older, it must be worse
			    } else if (isSignificantlyOlder) {
			        return false;
			    }

			    // Check whether the new location fix is more or less accurate
			    int accuracyDelta = (int) (location1.getAccuracy() - currentBestLocation.getAccuracy());
			    boolean isLessAccurate = accuracyDelta > 0;
			    boolean isMoreAccurate = accuracyDelta < 0;
			    boolean isSignificantlyLessAccurate = accuracyDelta > 100;

			    // Check if the old and new location are from the same provider
			    boolean isFromSameProvider = isSameProvider(location1.getProvider(),
			            currentBestLocation.getProvider());

			    // Determine location quality using a combination of timeliness and accuracy
			    if (isMoreAccurate) {
			        return true;
			    } else 
			    	{ if (isNewer && !isLessAccurate) {
			        return true;
			    } else
			    	{if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			        return true;
			    	}
			    	}}
			//	Toast.makeText(Socialnet.this, "returning false "+ location1.getProvider()+ " " +(int)location.getAccuracy(),
				//		 Toast.LENGTH_SHORT).show();

			    
			    return false;

			}

			/** Checks whether two providers are the same */
			public boolean isSameProvider(String provider1, String provider2) {
			    if (provider1 == null) {
			      return provider2 == null;
			    }
			    return provider1.equals(provider2);
			}


		
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			final String TAG = "search";
			double lat, longitude;
			String lati, longi;
			// user_id="ptota";
			lati = null;
			longi = null;
			

//			Toast.makeText(Socialnet.this, location.getProvider()+ " new " +(int)location.getAccuracy(),
	//	 Toast.LENGTH_SHORT).show();
		//	Toast.makeText(Socialnet.this, currentloc.getProvider()+ " old " +(int)currentloc.getAccuracy(),
			//		 Toast.LENGTH_SHORT).show(); 
			if(isBetterLocation(location, currentloc)){
				currentloc=location;
		//		Toast.makeText(Socialnet.this, "yipee" + location.getProvider(),
			//			Toast.LENGTH_SHORT).show();
			lat = location.getLatitude();
			longitude = location.getLongitude();
			// lat=14.178676986;
			// longitude=56.6786840;

			lati = Double.toString(lat);
			longi = Double.toString(longitude);
			String buf = lati + "\n" + longi;

			try {
				OutputStreamWriter fos = new OutputStreamWriter(openFileOutput(
						FILENAME1, Context.MODE_PRIVATE));
				fos.write(buf);
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpClient client02 = new DefaultHttpClient();
			HttpPost post02 = new HttpPost(
					"http://mobileswan.com/index.php?/socialnet/geoand");
			List<BasicNameValuePair> nvps02 = new ArrayList<BasicNameValuePair>(
					2);
			nvps02.add(new BasicNameValuePair("user_id", user_id));
			nvps02.add(new BasicNameValuePair("lat", Double.toString(lat)));
			nvps02.add(new BasicNameValuePair("longitude", Double
					.toString(longitude)));
			try {
				post02.setEntity(new UrlEncodedFormEntity(nvps02, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
			}
			try {
				HttpResponse res = client02.execute(post02);
				Log.v(TAG, res.getStatusLine().toString());
				HttpEntity responseEntity = res.getEntity();
				Log.v(TAG, "Set response to responseEntity");
				String[] text1 = HttpHelper.request(res);

				// contact_name.setText(text1[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.v(TAG, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v(TAG, e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

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
		switch (v.getId()) {
		case R.id.Friends:
			i = new Intent(this, Friends.class);
			startActivity(i);
			break;

		case R.id.Settings:
			i = new Intent(this, Settings.class);
			startActivity(i);
			break;

		case R.id.Interest:
			i = new Intent(this, Interest.class);
			startActivity(i);
			break;

		case R.id.Search:
			i = new Intent(this, Socialnet.class);
			startActivity(i);
			break;

		case R.id.Favorites:
			i = new Intent(this, Favorites.class);
			startActivity(i);
			break;

/*		
		 case R.id.Friendsnearme: 
			 i=new Intent(this, Frndnearme.class);
		   startActivity(i); 
		   break;
*/

//		 case R.id.Categories:
//			 setContentView(R.layout.categories);
//			 newadapter();
//			break;
	/*	 
		 case R.id.Submit: 
			 edittext01=(EditText)findViewById(R.id.EditText01);
		  edittext02=(EditText)findViewById(R.id.EditText02); 
		  search1 = edittext01.getText().toString(); 
		  search2 = edittext02.getText().toString(); 
		  // String user_id= "ptota";
		  Log.v(TAG, edittext01.getText().toString()); 
		  Log.v(TAG, edittext02.getText().toString());
		  
		  HttpClient client = new DefaultHttpClient(); 
		  HttpPost post = new HttpPost("http://mobileswan.com/index.php?/socialnet/searchand");
		  List nvps = new ArrayList(2); 
		  nvps.add(new BasicNameValuePair("user_id", user_id)); 
		  nvps.add(new BasicNameValuePair("search1", search1)); 
		  nvps.add(new BasicNameValuePair("search2", search2)); 
		  try 
		  { 
			  post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); 
			  } 
		  catch
		  (UnsupportedEncodingException e) 
		  { 
			  // TODO Auto-generated catch block
		  StringWriter sw = new StringWriter(); 
		  PrintWriter pw = new PrintWriter(sw); e.printStackTrace(pw); 
		  } 
		  try 
		  { 
		  HttpResponse res =client.execute(post); 
		  Log.v(TAG, res.getStatusLine().toString()); 
		  HttpEntity responseEntity = res.getEntity(); 
		  Log.v(TAG, "Set response to responseEntity"); 
		  String[] text=HttpHelper.request(res); 
		  setContentView(R.layout.search);
		  tv1=(TextView)findViewById(R.id.TextView01);
		  tv2=(TextView)findViewById(R.id.TextView02); 
		  tv1.setText(search1);
		  tv2.setText(search2); 
		  addAdapter(text); 
		  } 
		  catch
		  (ClientProtocolException e) 
		  { // TODO Auto-generated catch block
		  Log.v(TAG, e.toString()); 
		  e.printStackTrace(); }
		  catch (IOException e) 
		  { 
			  Log.v(TAG, e.toString()); 
			  // TODO Auto-generated catch block
		     e.printStackTrace(); 
		 }
		  
		  
		  break; */
		}

	}

public void newadapter(){
 
GridView gridview = (GridView) findViewById(R.id.gridview);
gridview.setAdapter(new ImageAdapter(this));


gridview.setOnItemClickListener(new OnItemClickListener() 
{
    public void onItemClick(AdapterView<?> parent, View v,int position, long id) 
    {
	Toast.makeText(Socialnet.this, "" + position,
			Toast.LENGTH_SHORT).show();
	
	Intent CatIntent = new Intent();
	CatIntent.setClassName("com.example.socialnet", "com.example.socialnet.Categories");
	CatIntent.putExtra("gridposition", position);
	startActivity(CatIntent);
	/* switch (position) {
	case 0:
		  setContentView(R.layout.result);
		  tv1=(TextView)findViewById(R.id.DTextView01);
		  tv2=(TextView)findViewById(R.id.DTextView02); 
		  tv1.setText("Bus");
		  tv2.setText("Wait Times"); 
		String[] text= {"Bus Wait Time is 5 mins","Bus 3 Wait time is 10 mins"};//{"Bus No. 1 is 10 min away", "Bus 2 is 5 min away"};
		addAdapter(text);
		break;

	case 2 :
		setContentView(R.layout.result);
		  tv1=(TextView)findViewById(R.id.DTextView01);
		  tv2=(TextView)findViewById(R.id.DTextView02); 
		  tv1.setText("Bus");
		  tv2.setText("Wait Times"); 
		String[] text1= {"Table 1 Wait Time is 5 mins","Table 2 Wait time is 10 mins"};//{"Bus No. 1 is 10 min away", "Bus 2 is 5 min away"};
		addAdapter(text1);
		
		break;

	case 3:
	
		break;

	case 4:
		
		break;
	}*/
	}
});
}

	
	
	private void addAdapter(String[] text) {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.Dlist1);

		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.itemslist1, text));
		// lv = getListView();
	}

}
