package com.example.andrey.myapplication;

import java.util.List;

/**
 * Created by Andrey on 26.12.2016.
 */

public class OptimalWay {
    public int totalTimeSeconds;
    public int totalGoingTimeSeconds;
    public int totalTransportChangingCount;
    public List<WayPoint> points;
    public class WayPoint
    {
        public class GeoCoords {
            public double xCoord;
            public double yCoord;
        }
        public class Station {
            public String hashcode;
            public String nameRus, nameEn, nameBy;
            public String name, osm_id, osm_version, osm_changeset, osm_timestamp, osm_last_user_name, osm_last_user_id;
            public double xCoord, yCoord;
            public List<Route> routes;
            public Point Point;
            public GeoCoords Coords;
        }
        public class Route {
            public String hashcode;
            public String rusFrom, rusTo, enFrom, enTo, byFrom, byTo, number, type;
            public String from, to;
            public String[] osm_id, osm_version, osm_changeset, osm_timestamp, osm_last_user_name, osm_last_user_id;
            public String osm_num, owner;
            public List<Station>[] stations = null;
            public List<Timetable>[] timetables = null;
            public String stationsJSON;
        }
        public class Timetable {}
        public class Point {}
        public Station station;
        public Route route;
        public GeoCoords coords;
        public String time;
    }
}
