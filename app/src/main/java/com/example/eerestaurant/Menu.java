package com.example.eerestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Menu extends Fragment {

    //This is just a ListView with items listed in an array. If the admin role worked, a method for the admin to add items to the list would have been done.

    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_layout, container, false);

        String[] appetizerItems = {"Nuts","Helwa Tat-Tork","Hobz biz-zejt"};

        listView = (ListView) view.findViewById(R.id.listViewAppetizer);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, appetizerItems);

        listView.setAdapter(listViewAdapter);

        String[] entreeItems = {"Pizza","Burger"};

        listView = (ListView) view.findViewById(R.id.listViewEntree);

        ArrayAdapter<String> entreelistViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entreeItems);

        listView.setAdapter(entreelistViewAdapter);

        String[] dessertItems = {"Cake","Ice-cream",};

        listView = (ListView) view.findViewById(R.id.listViewDessert);

        ArrayAdapter<String> dessertlistViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dessertItems);

        listView.setAdapter(dessertlistViewAdapter);

        return view;
    }
}
