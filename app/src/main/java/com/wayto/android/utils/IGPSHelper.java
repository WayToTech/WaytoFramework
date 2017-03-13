package com.wayto.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IGPSHelper {
	
	public static final int RESULT_GPS_OPEN=0;
	public static List<GpsSatellite> numSatelliteList = new ArrayList<GpsSatellite>(); //卫星状态监听器  
	
	
	private IGPSHelper(){/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");}
	
	/**
	 * 监测gps或者wlan/移动网络信号是否打开
	 * @param context
	 * @return
	 */
	public static final boolean isOPen(final Context context){
		LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		//GPS卫星定位信号
		boolean gps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//Wlan或者移动网络3g/2g信号
//		boolean network=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps) {
			return true;
		}
		return false;
	}
	
	/**
	 * 打开GPS
	 * @param activity
	 */
	public static final void openGPS(Activity activity){
		Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		activity.startActivityForResult(intent,RESULT_GPS_OPEN);//手动打开
	}
	
	/**
	 * 更新gps数量
	 * @param event
	 * @param status
	 * @return
	 */
    @SuppressWarnings("unused")
	public static int updateGpsNumber(int event, GpsStatus status) {
    	int num=0;
        if (status==null) {
        	num=0;
		}
        switch (event) {
        case GpsStatus.GPS_EVENT_STARTED:
        	num=0;
        	break;
		case GpsStatus.GPS_EVENT_FIRST_FIX:
			int timeFirst=status.getTimeToFirstFix();//毫秒
			num=1;
			break;
		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
			int maxSatellites = status.getMaxSatellites(); 
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            numSatelliteList.clear();
            int count = 0;  
            while (it.hasNext() && count <= maxSatellites) {  
                GpsSatellite s = it.next();  
                numSatelliteList.add(s);  
                count++;  
            }
            num=numSatelliteList.size();
		case GpsStatus.GPS_EVENT_STOPPED:
			
			break;
		}
        return num;
    }
    
    /**
	 * 更新搜索到的卫星状态
	 * @param event 卫星 GpsStatus状态标识（GPS_EVENT_STARTED，GPS_EVENT_FIRST_FIX，GPS_EVENT_SATELLITE_STATUS，GPS_EVENT_STOPPED）
	 * @param status gps状态信息，包含卫星数目
	 * @return
	 */
    public static String updateGpsStatus(int event, GpsStatus status) {
    	StringBuilder sbSatelliteInfo = new StringBuilder("");
    	if (status==null) {
    		sbSatelliteInfo.append("搜索到卫星个数：" +0+"\n");
    	}
    	switch (event) {
    	case GpsStatus.GPS_EVENT_STARTED:
    		sbSatelliteInfo.append("GPS已启动,正在搜索卫星..."+"\n");
    		break;
    	case GpsStatus.GPS_EVENT_FIRST_FIX:
    		int timeFirst=status.getTimeToFirstFix();//毫秒
    		sbSatelliteInfo.append("搜索到第一颗卫星...用时"+timeFirst/1000+"秒"+"\n");
    		break;
    	case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
    		int maxSatellites = status.getMaxSatellites(); 
    		Iterator<GpsSatellite> it = status.getSatellites().iterator();
    		numSatelliteList.clear();
    		int count = 0;  
    		while (it.hasNext() && count <= maxSatellites) {  
    			GpsSatellite s = it.next();  
    			numSatelliteList.add(s);  
    			count++;  
    		}
    		sbSatelliteInfo.append("搜索到卫星个数：" + numSatelliteList.size()+"\n");
    	case GpsStatus.GPS_EVENT_STOPPED:
    		sbSatelliteInfo.append("GPS已停止");
    		break;
    	}
    	return sbSatelliteInfo.toString();  
    }
    
  /**
   * 更新位置信息
   * @param loc GPS获取到的定位信息
   * @return 定位信息字符串
   */
  	public static String updateMsg(Location loc) {  
  		StringBuilder sbLocationInfo = null;  
  		sbLocationInfo = new StringBuilder("位置信息：\n");  
          if (loc != null) {  
              double lat = loc.getLatitude();  
              double lng = loc.getLongitude();  
              
              sbLocationInfo.append("纬度：" + lat + "\n经度：" + lng);  
              if (loc.hasAccuracy()) {  
              	sbLocationInfo.append("\n精度：" + loc.getAccuracy());  
              }  
    
              if (loc.hasAltitude()) {  
                  sbLocationInfo.append("\n海拔：" + loc.getAltitude() + "m");  
              }  
    
              if (loc.hasBearing()) {// 偏离正北方向的角度  
                  sbLocationInfo.append("\n方向：" + loc.getBearing());  
              }  
    
              if (loc.hasSpeed()) {  
                  if (loc.getSpeed() * 3.6 < 5) {  
                      sbLocationInfo.append("\n速度：0.0km/h");  
                  } else {  
                      sbLocationInfo.append("\n速度：" + loc.getSpeed() * 3.6 + "km/h");  
                  }  
    
              }  
          } else {  
              sbLocationInfo.append("没有位置信息！");  
          }  
          return sbLocationInfo.toString();  
      }
  	
  	private static final int TWO_MINUTES = 1000 * 1 * 2;

	/**
	 * Determines whether one Location reading is better than the current(官网提供方法)
	 * 当前location 比 上一个currentBestLocation位置更精确则返回true
	 * Location fix
	 * @param location The new Location that you want to evaluate
	 * @param currentBestLocation The current Location fix, to which you want to compare the new one
	 */
	public static boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	public static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

}
