/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelligentz.sahasara;

/**
 *
 * @author Heshan
 */
public class DistanceCalculator {
    
    public static boolean isAtRange(double lat1, double lon1, double lat2, double lon2,int km){
        System.out.println(distance( lat1, lon1, lat2, lon2, 'K'));
//        System.out.println(lat1);
//        System.out.println(lon1);
//        System.out.println(lat2);
//        System.out.println(lon2);
        return (distance(lat1, lon1, lat2, lon2, 'K')< 100);
    }
    
    public static final double distance(double lat1, double lon1, double lat2, double lon2, char unit)
{
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
     
    if (unit == 'K') {
        dist = dist * 1.609344;
    }
    else if (unit == 'N') {
        dist = dist * 0.8684;
    }
     
    return (dist);
}
 
/**
 * <p>This function converts decimal degrees to radians.</p>
 * 
 * @param deg - the decimal to convert to radians
 * @return the decimal converted to radians
 */
private static final double deg2rad(double deg)
{
    return (deg * Math.PI / 180.0);
}
 
/**
 * <p>This function converts radians to decimal degrees.</p>
 * 
 * @param rad - the radian to convert
 * @return the radian converted to decimal degrees
 */
private static final double rad2deg(double rad)
{
    return (rad * 180 / Math.PI);
}

}
