package EncryptionAlgorithm;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;  
import javax.crypto.IllegalBlockSizeException;  

/**
 * DES加密算法
 * @author WilsonSong
 * @version 2018-05-21
 */
public class DESUtil {
	/**
	 * 生成密钥
	 * @throws Exception 
	 */
	public static byte[] initKey() {
		
		try {
			//密钥生成
			KeyGenerator keyGen;
			keyGen = KeyGenerator.getInstance("DES");
			//初始化密钥生成器
			keyGen.init(56);
			//生成密钥

			SecretKey  secretKey = keyGen.generateKey();
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加密
	 * @throws Exception 
	 */
	public static byte[] encryptDES(byte[] data, byte[] key){
		try {
          	//获得密钥
			SecretKey secretKey = new SecretKeySpec(key, "DES");
			//Cipher完成加密
			Cipher cipher;
			cipher = Cipher.getInstance("DES");
			//初始化cipher
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(BadPaddingException e) {
			e.printStackTrace();
		} catch(IllegalBlockSizeException e) {
			e.printStackTrace();
		} 
		return null;
		
	}
	
	/**
	 * 解密
	 */
	public static byte[] decryptDES(byte[] data, byte[] key){
		try {
			//恢复密钥
			SecretKey secretKey = new SecretKeySpec(key, "DES");
			//Cipher完成解密
			Cipher cipher;
			cipher = Cipher.getInstance("DES");
			//初始化cipher
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			//解密
			byte[] plain = cipher.doFinal(data);
			
			return plain;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(BadPaddingException e) {
			e.printStackTrace();
		} catch(IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
