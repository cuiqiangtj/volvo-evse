package com.volvo.evse.common;

public class EvseCodeUnit {

    /**
     * 判断标识符符合OCPI-compliant电动车辆供电设备ID格式，由国家代码、参与方ID和当地电动车辆供电设备标识符组成
     * @param code
     * @return
     */
    public static boolean iSOCPICompliant(String code){

        if(code==null){
            return false;
        }
        String[] codes = code.split("\\*");

        if(codes.length!=3){
            return false;
        }
        if(!isCountryCode(codes[0])){
            return false;
        }
        //参与方ID 长度验证
        if(codes[1].length()!=3){
            return false;
        }
        // 设备标识符长度验证
        if(codes[2].isEmpty()){
            return false;
        }
        if(codes[2].length()>=30){
            return false;
        }
        return true;
    }

    /**
     * 判断国际代码是否符合ISO 3166
     * @param countryCode
     * @return
     */
    public static boolean isCountryCode(String countryCode){

        if(countryCode == null || countryCode.length()!=2){
            return false;
        }
        //(e.g., "US", "NL", "CN")

        return true;
    }
}
