package System;

import EncryptionAlgorithm.AESUtil;
import EncryptionAlgorithm.DESede;
import EncryptionAlgorithm.DESUtil;
import EncryptionAlgorithm.HashUtil;
import EncryptionAlgorithm.RSAUtil;
import EncryptionAlgorithm.BytesToHex;
import java.util.Scanner;

public class Security {
	public static void main(String[] args){
        System.out.println();
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        String md5str = HashUtil.string2MD5(str);
        //String mmmmmm = HashUtil.toStringHex(md5str);
        System.out.println(md5str);
        //String hmstr= mmmmmm.concat(str);   //�ַ���ƴ��
	}

}
