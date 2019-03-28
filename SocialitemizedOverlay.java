package com.example.socialnet;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class SocialitemizedOverlay extends ItemizedOverlay {
	
	private Context mContext;
	private MapView mView;
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	public SocialitemizedOverlay(Drawable defaultMarker, MapView mapView) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = mapView.getContext();
		  mView=mapView;
		}
	

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mOverlays.size();
	}
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  GeoPoint point = item.getPoint();
	  MapController mapViewController;
	  mapViewController = mView.getController();
	  mapViewController.animateTo(point);
	 // AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);

	//	  dialog.setTitle(item.getTitle());
	/*  dialog.setCancelable(true) ;
	  dialog.setMessage(item.getSnippet()+ ","+item.getTitle());
	  dialog.show();	  
*/
	  Toast toast = Toast.makeText(mContext, item.getTitle()+","+item.getSnippet(), Toast.LENGTH_SHORT);
	  toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
	  toast.show();
	  return true;
	}
	
}
