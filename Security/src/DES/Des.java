package DES;

import java.util.Scanner;

/**
 * @This program test the DES algorithm
 * @author Wilson song
 * @version 1.0 2018-03-22
 */
public class Des {
	public static void main(String[] args) {
        System.out.println("DES�ӽ��� : ");
        System.out.println();
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        System.out.print("����������(֧�����ⳤ����ĸ�������)��");
        String mmmmmm = in.nextLine();
        System.out.print("��������Կ(֧�������ʽ����)��");
        String kkkkkk = in.nextLine();
        String[] M = Function.all2string(mmmmmm);
        char[] K = Function.keypre(kkkkkk);
        DesAlgorithm descore = new  DesAlgorithm();
        String mimi = descore.en(M, K);
        String demimi = descore.de(Function.all222string(mimi), K);
        String demimistring = Function.byte2string(demimi);
        System.out.println("*************************************************");
        System.out.println("**********************���***********************");
        System.out.println("���ģ�"+mmmmmm);
        System.out.println("------------------------------------------------------------");
        System.out.println("���ܽ����");
        System.out.println("�����ƣ�"+ mimi);
        System.out.println("ʮ�����ƣ�"+Function.two2hex(mimi));
        System.out.println("------------------------------------------------------------");
        System.out.println("���ܽ����");
        System.out.println("�����ƣ�"+demimi);
        System.out.println("ʮ�����ƣ�"+Function.two2hex(demimi));
        System.out.println("�����"+demimistring);
	}
}