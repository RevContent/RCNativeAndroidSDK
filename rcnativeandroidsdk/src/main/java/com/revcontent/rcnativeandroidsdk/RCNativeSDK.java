package com.revcontent.rcnativeandroidsdk;

final public class RCNativeSDK {
    private static final RCNativeSDK instance = new RCNativeSDK();
    private RCNativeSDK(){
    }
    public static void setup(){
    }
    private static boolean validateSDK(){
        if(instance == null){
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
