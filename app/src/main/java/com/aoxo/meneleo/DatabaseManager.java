package com.aoxo.meneleo;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Vector;

/**
 * Created by tomek on 27.10.2016.
 */

public class DatabaseManager {

    //KOCHANY TOMECZEK

    private void createDatabase()
    {

    }

    public DatabaseManager(String username)
    {
        /*
        Tutaj tworzymy baze danych. Jesli dana tabela nie istnieje dla danego uzytkownika to ja tworzymy
        uzywajac funkcji createDatabase();
         */
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
