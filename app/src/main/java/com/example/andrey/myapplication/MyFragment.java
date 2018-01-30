package com.example.andrey.myapplication;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andrey on 26.12.2016.
 */

public class MyFragment extends Fragment {
    private String jsonStr = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //jsonStr = ProgressFragment.resultsJSON;//"null";

    }
    TextView statusView, textView123;
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        statusView = (TextView)view.findViewById(R.id.status5);

        //statusView.setText(jsonStr);
        /////////////////////////////////////////////////////////////////////////////////////////
        try{
            AssetManager assetManager = MainActivity.assets;
            InputStream stream = assetManager.open("testReadingTextFile.txt");
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            jsonStr = sb.toString();

            ///////////////////////////
            jsonStr = ProgressFragment.resultsJSON;
            ////////////////

            //statusView.setText(jsonStr);
            ///////////////////////////////////



            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<OptimalWay>>(){}.getType();
            ArrayList<OptimalWay> optimalWayList = gson.fromJson(jsonStr, type);



            if(optimalWayList != null){
                StringBuilder my_text = new StringBuilder();
                for (OptimalWay currentWay: optimalWayList) {
                    //OptimalWay currentWay = optimalWayList.get(0);
                    String tmpView = "Total time seconds: " + currentWay.totalTimeSeconds
                            + "\nTotal Going Time:" + currentWay.totalGoingTimeSeconds
                            + "\nКоличество пересадок:" + currentWay.totalTransportChangingCount;

                    //StringBuilder my_text = new StringBuilder();
                    my_text.append("\n\n"+tmpView+"\n");
                    for (int i = 1; i < currentWay.points.size(); i++) {
                        if (currentWay.points.get(i).route == null) {
                            if (currentWay.points.get(i).station == null)
                                my_text.append("Идите пешком к пункту назначения.");
                            else {
                                my_text.append("Идите к остановке \"");
                                if (currentWay.points.get(i).station.nameRus.toString() != "")
                                    my_text.append(currentWay.points.get(i).station.nameRus.toString());
                                else
                                    my_text.append(currentWay.points.get(i).station.name.toString());
                                my_text.append("\"");
                            }
                        } else {
                            if (i + 1 < currentWay.points.size() && currentWay.points.get(i + 1).route != null && currentWay.points.get(i).route.type == currentWay.points.get(i + 1).route.type && currentWay.points.get(i).route.number == currentWay.points.get(i + 1).route.number)
                                continue;
                            my_text.append("Доедьте до остановки \"");
                            if (currentWay.points.get(i).station.nameRus.toString() != "")
                                my_text.append(currentWay.points.get(i).station.nameRus.toString());
                            else my_text.append(currentWay.points.get(i).station.name.toString());
                            my_text.append("\" на транспорте \"" + currentWay.points.get(i).route.type.toString() + " " + currentWay.points.get(i).route.number.toString() + "\"");
                        }
                        my_text.append("\n");
                    }
                }

                statusView.setText(my_text.toString());//tmpView+"\n"+
            }

            ///////////////////////////////////



        } catch (Exception e) {
            e.printStackTrace();
            statusView.setText("err: "+e.toString()+e.getMessage());
        }
        ///////////////////////////////////////////////////////////////////////////////////////////

        //statusView.setText("11111111111111111111");
        return view;
    }
}
