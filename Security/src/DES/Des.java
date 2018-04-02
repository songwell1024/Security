package DES;

import java.util.Scanner;

/**
 * @This program test the DES algorithm
 * @author Wilson song
 * @version 1.0 2018-03-22
 */
public class Des {
	public static void main(String[] args) {
        System.out.println("DES加解密 : ");
        System.out.println();
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        System.out.print("请输入明文(支持任意长度字母数字组合)：");
        String mmmmmm = in.nextLine();
        System.out.print("请输入密钥(支持任意格式输入)：");
        String kkkkkk = in.nextLine();
        String[] M = Function.all2string(mmmmmm);
        char[] K = Function.keypre(kkkkkk);
        DesAlgorithm descore = new  DesAlgorithm();
        String mimi = descore.en(M, K);
        String demimi = descore.de(Function.all222string(mimi), K);
        String demimistring = Function.byte2string(demimi);
        System.out.println("*************************************************");
        System.out.println("**********************结果***********************");
        System.out.println("明文："+mmmmmm);
        System.out.println("------------------------------------------------------------");
        System.out.println("加密结果：");
        System.out.println("二进制："+ mimi);
        System.out.println("十六进制："+Function.two2hex(mimi));
        System.out.println("------------------------------------------------------------");
        System.out.println("解密结果：");
        System.out.println("二进制："+demimi);
        System.out.println("十六进制："+Function.two2hex(demimi));
        System.out.println("编码后："+demimistring);
	}
}
