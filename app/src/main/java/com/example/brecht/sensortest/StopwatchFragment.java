package com.example.brecht.sensortest;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Brecht on 3/03/2015.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private View view;

    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 100;
    private String hours,minutes,seconds="00";
    private long secs,mins,hrs, lastmin;
    private boolean stopped, start,stop,reset = false;
    private TextToSpeech SayTime;

    @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        startButton = (Button) view.findViewById(R.id.btnStart);
        startButton.setOnClickListener(this);
        stopButton = (Button) view.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
        resetButton = (Button) view.findViewById(R.id.btnReset);
        resetButton.setOnClickListener(this);

            SayTime = new TextToSpeech(getActivity().getApplicationContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                SayTime.setLanguage(Locale.US);
                            }
                        }
                    });


        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnStart:
                startClick();
                break;
            case R.id.stopButton:
                stopClick();
                break;
            case R.id.btnReset:
                resetClick();
                break;
        }
    }

    public void startClick (){
        showStopButton();
        (view.findViewById(R.id.Height)).setVisibility(View.VISIBLE);
        start=true;
        stop=false;
        if(stopped){
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else{
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    public void stopClick (){
        hideStopButton();
        start=false;
        stop=true;
        mHandler.removeCallbacks(startTimer);
        stopped = true;
    }

    public void resetClick (){
        stopped = false;
        start=false;
        stop=false;
        ((TextView)getView().findViewById(R.id.timer)).setText("00:00:00");
        ((TextView) view.findViewById(R.id.Height)).setVisibility(View.GONE);
    }

    private void showStopButton(){
        startButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
    }

    private void hideStopButton(){
        startButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

    private void updateTimer (float time){
        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);

		/* Convert the seconds to String
		 * and format to ensure it has
		 * a leading zero when required
		 */

        secs = secs % 60;
            seconds=String.valueOf(secs);
            if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

		/* Convert the minutes to String and format the String */

        mins = mins % 60;
        minutes=String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+minutes;
        }

    	/* Convert the hours to String and format the String */

        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }

		/* Setting the timer text to the elapsed time */
        ((TextView) view.findViewById(R.id.timer)).setText(hours + ":" + minutes + ":" + seconds);
        ((TextView) view.findViewById(R.id.Height)).setText("Current Height:"+ System.getProperty("line.separator")+"meter");


        //Check if we need to speak the minutes
        //@ the moment it's every minute
        if(lastmin!=mins) {
            speakText();
            lastmin=mins;
        }

    }


    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this,REFRESH_RATE);
        }
    };

    public void speakText(){
        if (mins > 0) {
            String toSpeak;
            if (mins == 1)
                toSpeak = "You have been climbing for " + String.valueOf(mins) + " minute";
            else
                toSpeak = "You have been climbing for " + String.valueOf(mins) + " minutes";

            //Toast.makeText(getActivity().getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
            SayTime.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
