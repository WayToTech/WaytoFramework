package com.wayto.android.vendor.baiduTTS;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.wayto.android.BuildConfig;
import com.wayto.android.utils.ILog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 百度语音合成工具类
 * @author yangdu
 * @date 16/6/22
 * @time 下午7:08
 **/
public class BaiduTTSUtils {
    public static SpeechSynthesizer mSpeechSynthesizer;
    private static String mTTSDirPath;
    private static final String TTS_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";

    private static final String LICENSE_FILE_NAME = "temp_license";
    private static boolean INITED_PARAMETER=false;
    private static Context context=null;
    private static final String TAG="BaiduTTSUtils";

    private static void initialEnv() {
        if (mTTSDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mTTSDirPath = sdcardPath + "/" + TTS_DIR_NAME;
        }
        makeDir(mTTSDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mTTSDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mTTSDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mTTSDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mTTSDirPath + "/" + LICENSE_FILE_NAME);
    }

    private static void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 将工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private static void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        if(context==null)return;
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = context.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 初始化语音合成参数，有三种模式：在线、离线、在线和离线结合，目前只测试了在线
     * @param APPId
     * @param APIKey
     * @param SecretKey
     * @param mode
     */
    private static void initialTts(String APPId,String APIKey,String SecretKey,TtsMode mode) {
        if(context==null){
            ILog.e(TAG,"initialTts:context is null");
            return;
        }
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        // 文本模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mTTSDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mTTSDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，
        //仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mTTSDirPath + "/"
//                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        mSpeechSynthesizer.setAppId(APPId);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        mSpeechSynthesizer.setApiKey(APIKey, SecretKey);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");//设置音量
//        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "9");//设置音调
        // 授权检测接口
        AuthInfo authInfo = mSpeechSynthesizer.auth(mode);
        if (authInfo.isSuccess()) {
//            Toast.makeText(context,"auth success",Toast.LENGTH_SHORT).show();
            //发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
            mSpeechSynthesizer.initTts(mode);
        } else {
            Toast.makeText(context,"auth failed",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 合成语音
     * @param text
     * @param utteranceId
     * @return
     */
    private static SpeechSynthesizeBag getSpeechSynthesizeBag(String APPId, String APIKey, String SecretKey, TtsMode mode,
                                                              String text, String utteranceId) {
        SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
        speechSynthesizeBag.setText(text);
        speechSynthesizeBag.setUtteranceId(utteranceId);
        return speechSynthesizeBag;
    }

    /**
     * 百度语音文字转换语音播报
     * @param mcontext
     * @param text 要播报的文字
     */
    public static void speak(Context mcontext,String text) {
        context=mcontext;
        if(INITED_PARAMETER==false){
            initialEnv();
            initialTts(BuildConfig.BAIDUTTS_APP_ID,BuildConfig.BAIDUTTS_APP_KEY,BuildConfig.BAIDUTTS_SECRET_KEY,TtsMode.MIX);
            INITED_PARAMETER=true;
        }
        int result = mSpeechSynthesizer.speak(text);
        if (result < 0) {
            Toast.makeText(context,"error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 "
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 批量合成语音
     * @param mcontext
     * @param APPId
     * @param APIKey
     * @param SecretKey
     * @param mode
     * @param texts
     */
    public static void batchSpeak(Context mcontext,
                                  String APPId,String APIKey,String SecretKey,TtsMode mode,List<String> texts) {
        if(INITED_PARAMETER==false){
            initialEnv();
            initialTts(APPId,APIKey,SecretKey,mode);
            INITED_PARAMETER=true;
        }
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        if(texts==null||texts.size()<=0)return;
        context=mcontext;
        for(int i=0;i<texts.size();i++){
            bags.add(getSpeechSynthesizeBag(APPId,APIKey,SecretKey,mode,texts.get(i), i+""));
        }
        int result = mSpeechSynthesizer.batchSpeak(bags);
        if (result < 0) {
            Toast.makeText(context,"error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 "
                    ,Toast.LENGTH_SHORT).show();
        }
    }
    public static void pause() {
        if(mSpeechSynthesizer==null)return;
        mSpeechSynthesizer.pause();
    }

    public static void resume() {
        if(mSpeechSynthesizer==null)return;
        mSpeechSynthesizer.resume();
    }

    public static void stop() {
        if(mSpeechSynthesizer==null)return;
        mSpeechSynthesizer.stop();
    }
    public static void onDestroy() {
        if(mSpeechSynthesizer!=null){
            mSpeechSynthesizer.release();
        }
        mSpeechSynthesizer=null;
        INITED_PARAMETER=false;
        context=null;
    }
}
