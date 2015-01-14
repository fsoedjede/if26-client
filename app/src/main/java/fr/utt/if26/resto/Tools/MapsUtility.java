package fr.utt.if26.resto.Tools;

import android.util.Log;

import fr.utt.if26.resto.Model.Position;

/**
 * Created by soedjede on 11/01/15 for Resto
 */
public class MapsUtility {

    //Official Web site: http://www.geodatasource.com

    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    public static double distance(Position position1, Position position2, char unit) {
        /*Log.d("lat1", position1.getLatitude());
        Log.d("lat2", position2.getLatitude());
        Log.d("lng1", position1.getLongitude());
        Log.d("lng2", position2.getLongitude());*/
        double theta = Double.valueOf(position1.getLongitude()) - Double.valueOf(position2.getLongitude());
        double dist = Math.sin(
                deg2rad(Double.valueOf(position1.getLatitude()))) * Math.sin(deg2rad(Double.valueOf(position2.getLatitude()))) +
                Math.cos(deg2rad(Double.valueOf(position1.getLatitude()))) * Math.cos(deg2rad(Double.valueOf(position2.getLatitude()))) *
                Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /*system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
    system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
    system.println(distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");*/

    // Haversine_formula
    private static final double R = 6372.8; // In kilometers

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public static double haversine(Position position1, Position position2) {
        double lat1 = Double.valueOf(position1.getLatitude());
        double lat2 = Double.valueOf(position2.getLatitude());
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(Double.valueOf(position2.getLongitude()) - Double.valueOf(position1.getLongitude()));
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
