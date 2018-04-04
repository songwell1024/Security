package EncryptionAlgorithm;

import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Scanner;  
/** 
 * hash�ļ��ּ��ܷ�ʽ 
 */  
public class HashUtil {  
    public static void main(String[] args) {  
        Scanner in = new Scanner(System.in);  
        String next = in.next();
        in.close();
        String md5str = string2MD5(next);            
        String sha1str11 = SHA1(next);
        String sha256 = SHA256(next);
        String sha512 = SHA512(next);
//        String md5string = toStringHex(md5str);
//        String sha1str1 = toStringHex(sha1str);
//        String sha256Javastr1 = toStringHex(sha256Javastr);
        System.out.println("next");  
        System.out.println("md5str:"+md5str); 
        System.out.println("sha1str11:"+sha1str11);
        System.out.println("sha256Javastr��"+sha256); 
        System.out.println("sha256Javastr��"+sha512); 
    }  
    /** 
     * MD5���� ����32λmd5�� 
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
     * SHA����  
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
     * �����ı����ݣ����� SHA-1 �� 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA1(String strText)  
    {  
      return SHA(strText, "SHA-1");  
    }  
    
    /** 
     * �����ı����ݣ����� SHA-256 �� 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA256(final String strText)  
    {  
      return SHA(strText, "SHA-256");  
    }  
    
    /** 
     * �����ı����ݣ����� SHA-512 �� 
     *  
     * @param strText 
     * @return 
     */  
    public static String SHA512(final String strText)  
    {  
      return SHA(strText, "SHA-512");  
    }  
    /** 
     * ��byteתΪ16���� 
     * @param bytes 
     * @return 
     */  
    private static String byte2Hex(byte[] bytes){  
        StringBuffer stringBuffer = new StringBuffer();  
        String temp = null;  
        for (int i=0;i<bytes.length;i++){  
            temp = Integer.toHexString(bytes[i] & 0xFF);  
            if (temp.length()==1){  
                //1�õ�һλ�Ľ��в�0����  
                stringBuffer.append("0");  
            }  
            stringBuffer.append(temp);  
        }  
        return stringBuffer.toString();  
    }
    //16����ת�ַ���
    public static String toStringHex(String s) {  
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            s = new String(baKeyword, "utf-8");  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }
    
}  