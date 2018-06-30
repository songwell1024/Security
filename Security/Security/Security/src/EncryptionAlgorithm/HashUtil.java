package EncryptionAlgorithm;

import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;   

/**
 * Hash加密算法
 * @author WilsonSong
 * @version 2018-05-21
 */
public class HashUtil {  
    /** 
     * MD5加密 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toUpperCase();  
    } 

    /** 
     * SHA加密  
     * @param str 
     * @return 
     */   
    public static String SHA(String str, String strText){  
        MessageDigest messageDigest;  
        String encodeStr = "";
        if (strText != null && strText.length() > 0) {
	        try {  
	            messageDigest = MessageDigest.getInstance(strText);  
	            messageDigest.update(str.getBytes("UTF-8"));  
	            encodeStr = byte2Hex(messageDigest.digest());  
	        } catch (NoSuchAlgorithmException e) {  
	            e.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }
        }
	    return encodeStr;  
    }
    
    /** 
     * 传入文本内容，返回 SHA-1 串 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA1(String strText)  
    {  
      return SHA(strText, "SHA-1");  
    }  
    
    /** 
     * 传入文本内容，返回 SHA-256 串 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA256(final String strText)  
    {  
      return SHA(strText, "SHA-256");  
    }  
    
    /** 
     * 传入文本内容，返回 SHA-512 串 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA512(final String strText)  
    {  
      return SHA(strText, "SHA-512");  
    }  
    /** 
     * 将byte转为16进制 
     * @param bytes 
     * @return 
     */  
    public static String byte2Hex(byte[] bytes){  
        StringBuffer stringBuffer = new StringBuffer();  
        String temp = null;  
        for (int i=0;i<bytes.length;i++){  
            temp = Integer.toHexString(bytes[i] & 0xFF);  
            if (temp.length()==1){  
                //1得到一位的进行补0操作  
                stringBuffer.append("0");  
            }  
            stringBuffer.append(temp);  
        }  
        return stringBuffer.toString();  
    }
    //16进制转字符串
    public static String HexToString(String s) {  
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            //s = new String(baKeyword, "utf-8");
        	s = new String(baKeyword, "gbk");
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }
    /* 字符串转换为16进制字符串 
    *  
    * @param s 
    * @return 
    */  
    public static String StringToHex(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString();  
    }  
}  