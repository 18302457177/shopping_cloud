package com.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SpeakTools {
    public static void main(String[] args) {
        speakingText("您有一笔新的订单，请注意查收");
    }
    /**【语音播报方法】**/
    public static boolean speakingText(String readText){
        boolean isFinish = true;
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        try {
            sap.setProperty("Volume",new Variant(100));              // 音量 0-100
            sap.setProperty("Rate",new Variant(3));                 // 语音朗读速度 -10 到 +10
            Dispatch sapo = sap.getObject();                         // 获取执行对象
            Dispatch.call(sapo,"Speak",new Variant(readText));    	// 执行朗读
            sapo.safeRelease();                                     // 关闭执行对象
        }catch (Exception e){
            isFinish = false;
            e.printStackTrace();
        }finally {
            sap.safeRelease();                                      // 关闭执行对象
        }
        return isFinish;
    }
}
