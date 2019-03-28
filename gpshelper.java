package com.example.socialnet;

import android.content.Context;
import android.location.LocationManager;
import android.widget.Toast;

public class gpshelper {
	
	public static int services(Context lcontext, LocationManager lm){
		
		int locationOn=0;
		  if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			  if( !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){		  
			  Toast.makeText(lcontext, "Location services are not enabled",Toast.LENGTH_SHORT).show();			  		    		  
		  
			  } 
			  else {
				  Toast.makeText(lcontext, "Your GPS  service is not enabled",Toast.LENGTH_SHORT).show();
				  locationOn=1;
			  }
		  }
		  else  
		  {
			  if( !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){		  
				  Toast.makeText(lcontext, "Your Network Location services are not enabled",Toast.LENGTH_SHORT).show();
				  locationOn=3;
			  }
			  else locationOn=4;
	       }
		  return locationOn;
		
		
	}

}
