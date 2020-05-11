package com.revcontent.rcnativeandroidsdk;

final public class RCNativeSDK {
    private static RCNativeSDK instance = null;
    private RCNativeSDK(){
    }
    public static void setup(){
        if(instance == null){
            instance = new RCNativeSDK();
        }
    }
    private static boolean validateSDK(){
        if(instance != null){
            return true;
        }else{
            return false;
        }
    }
    public static boolean initialized(){
        if(validateSDK()){
            return true;
        }else{
            return false;
        }
    }
}
