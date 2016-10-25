package com.aoxo.meneleo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Pusty extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {Log.d("Zuzka","Utworzono fragment");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pusty, container, false);

    }


    public void onDestroyView()
    {
      /*  Log.d("Zuzka","destroyer");
        super.onDestroyView();
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.headlines_fragment));
        android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();*/
    }

}
