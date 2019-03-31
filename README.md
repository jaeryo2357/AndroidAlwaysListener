# AndroidAlwaysListener
 *  0328일자 Android Pie
 
## Service
 * **Service**는 안드로이드 4가지 구성요소 중 하나입니다. Acvitiy가 화면을 보여주면서 특정 기능을 수행하는 것이라면 Service는 **화면이 보이지 않는 백 그라운드**에서도 동작합니다.
#### RecognitionService
 * RecognitionService는 안드로이드 Service 라이브러리 중 하나로 **핸드폰 기기의 음성을 받아들이는 서비스**입니다.
 ###### 본 프로젝트에서는 음성인식을 가능하게 해주는 Service라 생각하여 사용하였지만 코드 상으로 볼때에는 잘 모르겠다.
 
  ``` java
 
 public class MyServie extends RecognitionService{
 
    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {

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
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service","destory");
    }
  }
   ```
* 기본적으로 3개의 **Override** 함수를 가지고 있으며,
   현재 프로젝트에서는 onStopListening만 사용되었습니다.

## SpeechRecognizer

 * SpeechRecognizer은 **구글에서 제공해준 음성인식 기능**으로 설정해준 언어를 인식하여 String으로 반환해줍니다.
 
 ``` java
 
   if(mSrRecognizer == null)
            {
                mSrRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
                mSrRecognizer.setRecognitionListener(mClsRecoListener);
            }
            if(mSrRecognizer.isRecognitionAvailable(getApplicationContext()))
            {
                Intent itItent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                itItent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
                itItent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN.toString()); //한국어
                itItent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 50);
                mSrRecognizer.startListening(itItent);
            }
 ```
 
 
 
