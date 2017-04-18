package com.aoxo.meneleo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomek on 27.10.2016.
 */

public class DatabaseManager extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "meneleo.db";
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_PARTYDATA = "PartyData";

    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "password";

    public static final String COLUMN_PARTY_UID = "uid";
    public static final String COLUMN_PARTY_USER = "userid";
    public static final String COLUMN_PARTY_STATE = "state";
    public static final String COLUMN_PARTY_DATE = "date";
    public static final String COLUMN_PARTY_LOCATIONS = "locations";
    public static final String COLUMN_PARTY_MARKERS = "markers";
    public static final String COLUMN_PARTY_NO_LOCATION = "no_location";
    private int current_db_user = 1;



    public static final String TAG_DB = "DB";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        /*
        Tutaj tworzymy baze danych. Jesli dana tabela nie istnieje dla danego uzytkownika to ja tworzymy
        uzywajac funkcji createDatabase();
         */
    }

    /*

    [ User name ] [ Password ]
       String        String



     */



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT " +
                ")";

        db.execSQL(query);
        Log.d(TAG_DB, TABLE_USERS+" created");


        /*

    COLUMN_PARTY_UID = "uid";
    COLUMN_PARTY_USER = "userid";
    COLUMN_PARTY_STATE = "state";
    COLUMN_PARTY_DATE = "date";
    COLUMN_PARTY_LOCATIONS = "locations";
    COLUMN_PARTY_MARKERS = "markers";
    COLUMN_PARTY_NO_LOCATION = "no_location";
         */
        query = "CREATE TABLE " + TABLE_PARTYDATA + " (" +
                COLUMN_PARTY_UID + " INTEGER PRIMARY KEY," +
                COLUMN_PARTY_USER + " INTEGER, " +
                COLUMN_PARTY_STATE + " INTEGER, " +
                COLUMN_PARTY_DATE + " TEXT, " +
                COLUMN_PARTY_LOCATIONS + " TEXT, " +
                COLUMN_PARTY_MARKERS + " TEXT, " +
                COLUMN_PARTY_NO_LOCATION + " INTEGER " +
                ")";

        db.execSQL(query);
        Log.d(TAG_DB, TABLE_PARTYDATA+" created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_PARTYDATA;

        db.execSQL(query);
        onCreate(db);

    }

    /*
    Ta funkcja ma za zadanie zczytanie z bazy danych wszystkich informacji i zwrocenie ich w postaci listy
     */
    public List<PartyData> getAllPartyData()
    {

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PARTYDATA + " WHERE "+COLUMN_PARTY_USER+ " = "+current_db_user;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        List <PartyData> partyDatas = new ArrayList<>();


        if (cursor.moveToFirst()) {
            do {
                boolean isThereIsNoLocation = Integer.parseInt(cursor.getString(6))  != 0;
                PartyData tmpParty;
                List <Location> locations = new ArrayList<>();
                List <MarkerData> markers = new ArrayList<>();

                String [] tmpStringLocations = cursor.getString(4).split("\\|");
                Log.d(TAG_DB, "zawartosc zapytania: "+cursor.getString(0)+ " "+cursor.getString(1)+ " "+cursor.getString(2)+ " "+cursor.getString(3)+ " "+cursor.getString(4)+ " "+cursor.getString(5)+ " "+cursor.getString(6)+ " ");



                if(!isThereIsNoLocation) {
                    for (int i = 0; i < tmpStringLocations.length; i++) {
                        Location l = new Location("Mello Location Provider");
                        String[] latLng = tmpStringLocations[i].split(";");
                        l.setLatitude(Double.parseDouble(latLng[0]));
                        l.setLongitude(Double.parseDouble(latLng[1]));

                        locations.add(l);
                    }
                }

                Pattern p = Pattern.compile("\\{(.*?)\\}");
                Matcher m = p.matcher(cursor.getString(5));
                while(m.find())
                {
                    markers.add(new MarkerData(m.group(1))); //creating marker from string
                }




                tmpParty = new PartyData(Long.parseLong(cursor.getString(0)),locations,markers,Integer.parseInt(cursor.getString(2)));
                tmpParty.noLocation = isThereIsNoLocation;
                partyDatas.add(tmpParty);



            } while (cursor.moveToNext());
        }



        return partyDatas;
    }

    /*
    Tworzy nowa tabele z danymi party na podstawie obiektu PartyData ktory dostal w parametrze.
    Tu mozna jako identyfikatora imprezy uzywac p.getUid() ktory zwraca nam uniwersalny identyfikator.
    Jesli operacja sie uda to funkcja zwraca true.
     */
    public void addParty(PartyData p)
    {
 /*

    COLUMN_PARTY_UID = "uid";
    COLUMN_PARTY_USER = "userid";
    COLUMN_PARTY_STATE = "state";
    COLUMN_PARTY_DATE = "date";
    COLUMN_PARTY_LOCATIONS = "locations";
    COLUMN_PARTY_MARKERS = "markers";
    COLUMN_PARTY_NO_LOCATION = "no_location";
         */
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_PARTY_UID, p.getUid());
        values.put(COLUMN_PARTY_USER, current_db_user);
        values.put(COLUMN_PARTY_STATE, p.state);
        values.put(COLUMN_PARTY_DATE, p.getDateInMiliseconds());

        String tmpLocations ="";
        for(int i=0; i<p.getLocations().size(); i++)
        {
            if(i!=0)
            {
                tmpLocations+="|";
            }
            tmpLocations +=p.getLocations().get(i).getLatitude()+";"+
                           p.getLocations().get(i).getLongitude();
        }


        values.put(COLUMN_PARTY_LOCATIONS, tmpLocations);

        String tmpMarkers="";
        for(int i=0; i<p.getMarkers().size(); i++)
        {

            tmpMarkers += p.getMarkers().get(i).getMarkerDataAsString();
        }

        values.put(COLUMN_PARTY_MARKERS, tmpMarkers);
        values.put(COLUMN_PARTY_NO_LOCATION, p.noLocation ? 1 : 0);

        db.insert(TABLE_PARTYDATA, null, values);
        db.close();
    }

    public boolean removeParty(PartyData p)
    {
        return true;
    }

    /*
    Dodaje do tebeli z party o ID uid nowa pozycje
     */
    public void addPosition(long uid, LatLng position)
    {

    }

    /*
    wywala z bazy pozycje
     */
    public void removePosition(long uid, LatLng position)
    {

    }

    /*
    dodaje marker do tabeli party o danym uid
     */
    public void addMarker(long uid, MarkerData md)
    {

    }

    public void removeMarker(long uid, MarkerData md)
    {

    }
}
