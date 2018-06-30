package EncryptionAlgorithm;

/**
 * 字节码转化十六进制
 * @author WilsonSong
 * @version 2018-05-21
 */
public class BytesAndHex {

	/*
	 * 字节码转换为16进制
	 */
	public static String fromBytesToHex(byte[] resultBytes){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < resultBytes.length; i++){
			if(Integer.toHexString(0xFF & resultBytes[i]).length() == 1){
				builder.append("0").append(Integer.toHexString(0xFF & resultBytes[i]));
			}else{
				builder.append(Integer.toHexString(0xFF & resultBytes[i]));
			}
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.toHexString(0xFF & 15));
	}

	/*
	 * 16进制转换为字节码
	 */
	public static byte[] fromHexToBytes(String hexStr) {  
	if (hexStr.length() < 1)  
	    return null;  
	byte[] result = new byte[hexStr.length()/2];  
	for (int i = 0;i< hexStr.length()/2; i++) {  
		int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
		int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
		result[i] = (byte) (high * 16 + low);  
	}  
	return result;  
	}  
}