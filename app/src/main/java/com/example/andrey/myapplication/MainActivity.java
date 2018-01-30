package com.example.andrey.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.*;

import java.util.ArrayList;

/*import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;*/




public class MainActivity extends AppCompatActivity {
    public static AssetManager assets = null;
    public ArrayList<String> myList = new ArrayList<String>();
    private RecyclerView myRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assets = getAssets();

        /*myList.add("text000");



        myList.add("ok 1");


        myList.add("text3");
        myList.add("text4");
        myList.add("text5");

        myRecView = (RecyclerView) findViewById(R.id.recycleview);

        myRecView.setAdapter(new ArticleViewAdapter());
        myRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));*/
    }


    /*class ArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView viewById;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            viewById = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    String s = myList.get(adapterPosition);
                    Snackbar.make(myRecView, s, Snackbar.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(String str) {
            viewById.setText(str);
        }
    }


    public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View tmp = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_old, parent, false);
            return new ArticleViewHolder(tmp);
        }

        @Override
        public void onBindViewHolder(ArticleViewHolder holder, int position) {
            holder.bind(myList.get(position));
        }

        @Override
        public int getItemCount() {
            return myList.size();
        }
    }*/
}

