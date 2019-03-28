package com.minsudongp.servicerecognition;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class Recoginition extends RecognitionService {

    public static final int MSG_VOICE_RECO_READY=0;
    public static final int MSG_VOICE_RECO_END=1;
    public static final int MSG_VOICE_RECO_RESTART=2;
    private SpeechRecognizer mSrRecognizer;
    boolean mBoolVoiceRecoStarted;

    @Override
    public void onCreate() {
        super.onCreate();
        startListening();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent==null)
        {
            return Service.START_STICKY;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service","destory");
    }

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {

    }

    private Handler mHdrVoiceRecoState = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_VOICE_RECO_READY	: break;
                case MSG_VOICE_RECO_END		:
                {
                    stopListening();
                    sendEmptyMessageDelayed(MSG_VOICE_RECO_RESTART, 1000);
                    break;
                }
                case MSG_VOICE_RECO_RESTART	: startListening();	break;
                default:
                    super.handleMessage(msg);
            }

        }
    };
    public void startListening()
    {
        if(mBoolVoiceRecoStarted == false)
        {
            if(mSrRecognizer == null)
            {
                mSrRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                mSrRecognizer.setRecognitionListener(mClsRecoListener);
            }
            if(mSrRecognizer.isRecognitionAvailable(getApplicationContext()))
            {
                Intent itItent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                itItent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
                itItent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN.toString());
                itItent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 50);
                mSrRecognizer.startListening(itItent);
            }
        }
        mBoolVoiceRecoStarted = true;
    }

    public void stopListening()
    {
        try
        {
            if (mSrRecognizer != null && mBoolVoiceRecoStarted == true)
            {
                mSrRecognizer.stopListening();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        mBoolVoiceRecoStarted = false;
    }



    @Override
    protected void onCancel(Callback listener) {
        mSrRecognizer.cancel();
    }

    @Override
    protected void onStopListening(Callback listener) {
        try
        {
            if (mSrRecognizer != null && mBoolVoiceRecoStarted == true)
            {
                mSrRecognizer.stopListening();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        mBoolVoiceRecoStarted = false;
    }
    private RecognitionListener mClsRecoListener = new RecognitionListener() {
        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d("sound",""+rmsdB);
        }


        @Override
        public void onResults(Bundle results) {
            mHdrVoiceRecoState.sendEmptyMessage(MSG_VOICE_RECO_END);
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            Log.d("key",rs[0]);
            if(rs[0].compareTo("프라미스")==0||rs[0].compareTo("프로미스")==0||rs[0].compareTo("푸로미스")==0)
            {
                stopListening();
            }

            //((TextView)(findViewById(R.id.text))).setText("" + rs[index]);
        }


        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int intError) {
            mHdrVoiceRecoState.sendEmptyMessage(MSG_VOICE_RECO_END);
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }
    };
    }
