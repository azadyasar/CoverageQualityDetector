package com.example.azadyasar.coveragequalitydetector;

/**
 * Created by azadyasar on 18/05/2016.
 */
public class LocationDBSchema {

    public static final class LocationTable{

        public static final String TABLE_NAME = "locationTable";

        public static final class Cols {
            public static final String ID = "id";
            public static final String OPERATOR_NAME = "operator_name";
            public static final String DATE = "date";
            public static final String SIGNAL_STRENGTH = "signalstrength";
          //  public static final String LOCATION = "location";
            public static final String LONGITUDE = "longitude";
            public static final String LATITUDE = "latitude";
        }

    }

}
