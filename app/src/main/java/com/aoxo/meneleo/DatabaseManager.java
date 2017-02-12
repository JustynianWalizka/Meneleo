package com.aoxo.meneleo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Vector;

/**
 * Created by tomek on 27.10.2016.
 */

public class DatabaseManager extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "meneleo.db";
    public static final String TABLE_PARTYDATA = "PartyData";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_MARKERS = "markers";
    public static final String COLUMN_MAPPOINTS = "mappoints";

    public static final String TAG_DB = "DB";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        /*
        Tutaj tworzymy baze danych. Jesli dana tabela nie istnieje dla danego uzytkownika to ja tworzymy
        uzywajac funkcji createDatabase();
         */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PARTYDATA + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_STATE + " BIT " +
                COLUMN_MARKERS + /* to do: type of data (MarkerData list) */
                COLUMN_MAPPOINTS + /*to do: type of data (LatLng list) */
                ")";

        db.execSQL(query);
        Log.d(TAG_DB, "PartyData created");
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
    public List<PartyData> loadPartyData()
    {
        // wczytujemy dane z DB do listy wywolujac odpowiedni konstruktor klasy PartyData
        // public PartyData(long uid, List<LatLng> mapPoints, List<MarkerData> markers, int state)


        return null;
    }

    /*
    Tworzy nowa tabele z danymi party na podstawie obiektu PartyData ktory dostal w parametrze.
    Tu mozna jako identyfikatora imprezy uzywac p.getUid() ktory zwraca nam uniwersalny identyfikator.
    Jesli operacja sie uda to funkcja zwraca true.
     */
    public boolean addParty(PartyData p)
    {
        return true;
    }

    /*
    Usuwa dane na temat imprezy z bazy danych na podstawie PartyData z parametru. p.getUid() zwroci identyfikator obiektu.
    Jesli operacja sie uda to funkcja zwraca true.
     */
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
