package com.example.andrey.myapplication;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ProgressFragment extends Fragment  {

    public static String resultsJSON = null;

    int[] integers=null;
    ProgressBar indicatorBar;
    TextView statusView, statusView2;
    GPSTracker gps;
    //MyLocationActivity myLocationActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //myLocationActivity = new MyLocationActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_progress, container, false);
        /*integers = new int[100];
        for(int i=0;i>100;i++) {
            integers[i] = i + 1;
        }*/
        //indicatorBar = (ProgressBar) view.findViewById(R.id.indicator);
        statusView = (TextView) view.findViewById(R.id.status);
        statusView2 = (TextView) view.findViewById(R.id.status2);


        //////////////////////////////////////////////////////////////////////////////////////////////
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            double altitude = gps.getAltitude();
            double speed = gps.getSpeed();
            double bearing = gps.getBearing();
            double speedLimitA = ((10*1000)/(60*60));

            statusView2.setText(String.valueOf(latitude)+";"+String.valueOf(longitude)+";"+String.valueOf(altitude)+";"+String.valueOf(longitude)+";"+String.valueOf(longitude));
        }else{
            gps.showSettingsAlert();
            statusView2.setText("err2222222");
        }

        //statusView2.setText(Double.toString(myLocationActivity.myLat));
        //statusView2.setText(TAG);


        //new ProgressTask().execute("https://maps.googleapis.com/maps/api/distancematrix/json?mode=walking");
        //"http://192.168.0.103:6960/api/points?from=гродно+ожешко+22&to=53.7084,23.8030&startTime=19:35"


        Button btnFetch = (Button)view.findViewById(R.id.button1);
        btnFetch.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    String fromStr = (((EditText)view.findViewById(R.id.editText)).getText()).toString().replace('&','+');//.replace(' ','+')
                    String toStr = (((EditText)view.findViewById(R.id.editText2)).getText()).toString().replace('&','+');//.replace(' ','+')

                    if (!fromStr.isEmpty() && !toStr.isEmpty()) {

                        boolean canUseBus = ((CheckBox)view.findViewById(R.id.checkBox)).isChecked();
                        boolean canUseTrolleyus = ((CheckBox)view.findViewById(R.id.checkBox3)).isChecked();
                        boolean canUseExpress_bus = ((CheckBox)view.findViewById(R.id.checkBox2)).isChecked();
                        boolean canUseMarsh = ((CheckBox)view.findViewById(R.id.checkBox4)).isChecked();
                        StringBuilder tmpTransportTypes = new StringBuilder();
                        if (canUseBus) tmpTransportTypes.append("bus,");
                        if (canUseTrolleyus) tmpTransportTypes.append("trolleybus,");
                        if (canUseExpress_bus) tmpTransportTypes.append("express_bus,");
                        if (canUseMarsh) tmpTransportTypes.append("marsh,");
                        String transportTypes = null;
                        if (tmpTransportTypes.length() == 0) transportTypes = "null";
                        else transportTypes = tmpTransportTypes.deleteCharAt(tmpTransportTypes.length()-1).toString();


                        TimePicker timePicker = (TimePicker)view.findViewById(R.id.timePicker);
                        String hourMinute = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();

                        int dopTimeMinutes = ((SeekBar)view.findViewById(R.id.seekBar)).getProgress();
                        int goingSpeed = ((SeekBar)view.findViewById(R.id.seekBar2)).getProgress() + 1;

                        // k4745270-001-site1.gtempurl.com //192.168.0.101:6967
                        String reqUrlTmp = "http://adsghdfsjgdj-001-site1.gtempurl.com/api/OptimalRoute?from=" + URLEncoder.encode(fromStr, "UTF8")
                                + "&to=" + URLEncoder.encode(toStr, "UTF8")
                                + "&startTime=" + hourMinute
                                + "&dopTimeMinutes=" + dopTimeMinutes
                                + "&goingSpeed=" + goingSpeed
                                + "&transportTypes=" + transportTypes;

                        /*Charset cset = Charset.forName("UTF-8");
                        ByteBuffer buf = cset.encode(reqUrlTmp);
                        byte[] b = buf.array();
                        String reqUrl = new String(b);*/
                        String reqUrl = reqUrlTmp;// new String(reqUrlTmp.getBytes("Windows-1251"),"UTF8");//ISO-8859-1

                        //reqUrl = "http://192.168.0.101:6967/api/OptimalRoute?from=53.7083,23.8029&to=53.6845,23.8391&startTime=18:02&dopTimeMinutes=2&goingSpeed=5&transportTypes=bus,trolleybus";
                        statusView2.setText(reqUrl);
                        new ProgressTask().execute(reqUrl);
                        //new ProgressTask().execute("https://maps.googleapis.com/maps/api/geocode/json?address=Grodno&bounds=53.5,23.5|53.5,23.5");

                    }
                    else Toast.makeText(getActivity(), "Не все поля заполнены!", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex)
                {
                    statusView2.setText(ex.getMessage());
                }
            }
        });
        return view;
    }

    class ProgressTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... myurl) {
            try {
                URL url = new URL(myurl[0]);

                HttpURLConnection c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET"); // установка метода получения данных -GET
                c.setReadTimeout(60000); // установка таймаута перед выполнением - 10 000 миллисекунд
                c.connect(); // подключаемся к ресурсу
                BufferedReader reader= new BufferedReader(new InputStreamReader(c.getInputStream()));

                StringBuilder sb = new StringBuilder();
                for (String tmp = reader.readLine(); tmp!=null; tmp = reader.readLine())
                {
                    sb.append(tmp);
                }
                String result = sb.toString();

                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        /*@Override
        protected void onProgressUpdate(Integer... items) {
            indicatorBar.setProgress(items[0]+1);
            statusView.setText("Статус: " + String.valueOf(items[0]+1));
        }*/
        @Override
        protected void onPostExecute(String resultData) {

            Intent intent = new Intent(getActivity(), ResultsViewActivity.class);
            startActivity(intent);
            if(resultData != null) {
                Toast.makeText(getActivity(), "Запрос отправлен", Toast.LENGTH_SHORT).show();
                //...
                //statusView.setText(resultData);
                resultsJSON = resultData;

            }
            else Toast.makeText(getActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show();

        }
    }
}