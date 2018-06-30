package EncryptionAlgorithm;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;  
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * AES算法编程实现
 * @author WilsonSong
 * @version 2018-05-21
 */
public class AESUtil {

	/**
	 * 生成密钥
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception 
	 */
	public static byte[] initKey() {  
        try {  
        	KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    		//初始化密钥生成器
    		keyGen.init(128);  //默认128，获得无政策权限后可选择192或256
    		//生成密钥
    		SecretKey secretKey = keyGen.generateKey();
    		return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace(); 
        }
        return null;  
    }  
	
	/**
	 * 加密
	 * @throws Exception 
	 */
	public static byte[] encryptAES(byte[] data, byte[] key){
		//恢复密钥
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		//Cipher完成加密
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			//根据密钥对cipher进行初始化
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			//加密
			byte[] encrypt = cipher.doFinal(data);
			return encrypt;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }
		return null;  
	}
	/**
	 * 解密
	 */
	public static byte[] decryptAES(byte[] data, byte[] key) {
		//恢复密钥生成
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		//Cipher完成解密
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			//根据密钥对cipher进行初始化
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] plain = cipher.doFinal(data);
			return plain;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InvalidKeyException e) {  
            e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
        } catch (BadPaddingException e) {  
            e.printStackTrace();  
        }  
        return null; 	
	}
} 