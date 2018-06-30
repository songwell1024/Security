package System;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.color.*;
import java.awt.font.*;
import java.awt.image.*;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit.*;
import java.awt.print.*;

import java.util.Calendar;
import java.util.Properties;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.BorderFactory;  
import javax.swing.ButtonGroup;  
import javax.swing.JButton;   
import javax.swing.JColorChooser;  
import javax.swing.JDialog;  
import javax.swing.JFileChooser;  
import javax.swing.JLabel;  
import javax.swing.JMenu;  
import javax.swing.JMenuBar;  
import javax.swing.JMenuItem;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import javax.swing.JPopupMenu;  
import javax.swing.JRadioButton;  
import javax.swing.JScrollPane;  
import javax.swing.JTextArea;  
import javax.swing.JTextField;  
import javax.swing.KeyStroke;  
import Font.MyFont;

import java.io.File.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.PrintWriter;

import EncryptionAlgorithm.BytesAndHex;
import EncryptionAlgorithm.AESUtil;
import EncryptionAlgorithm.DESede;
import EncryptionAlgorithm.DESUtil;
import EncryptionAlgorithm.HashUtil;
import EncryptionAlgorithm.RSAUtil;

import java.lang.NullPointerException;
import java.util.Map;

/**
 * UI
 * @author WilsonSong
 * @version 2018-05-21 
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener, DocumentListener {
	private String EncryptionAlgorithm = "DES";  //��ż����㷨, Ĭ��DES
	private String HashAlgorithm = "MD5";   //���Hash����, Ĭ��MD5
	private String EncryptionStr;   //����
	private String DecryptionStr;  //����
	private String EncryptionKeyStr;  //��Կ
	private String JDecryptionStr;   //���ܵ�����
	int PlanetLength;   //Hash�������ܺ�����ݵĳ���
	String  publicKey1,publicKey2;   //��Կ
    String  privateKey1,privateKey2; //˽Կ
	private String RKey;    //�ֽ�����Կ
	byte[] byteResult; //�ֽ�������
	String Base64Str; //��������
	JButton Encryption, Decryption, Help; 
	JTextArea PlainText, EncryptionKey, EncryptionText, DecryptionText;
	JComboBox<String> SecutityCombo, HashCombo;
	
	//�˵���
	JMenu fileMenu, editMenu, formatMenu, viewMenu, helpMenu;
	JTextArea editArea;  //�ı��༭��
	//�ļ����˵�ѡ��
	JMenuItem fileNew, fileOpen, fileSave, fileSaveAs, filePageSet, filePrint, fileExit ;
	//�༭���Ĳ˵�ѡ��
	JMenuItem editCut, editCopy, editPaste, editDelete, editFind, editUndo, 
					editFindNext, editGoTo, editDate, editSelectAll, editReplace;
	//��ʽ���˵�ѡ��
	JMenuItem formatFont, formatBackGround, formatFontColor;
    JCheckBoxMenuItem formatLineWrap;  	//�Զ�����ע����Ҫ���ɿɲ鿴ѡ��״̬��
	//�鿴���˵�ѡ��
    JCheckBoxMenuItem stateItem;
	//�������˵�ѡ��
	JMenuItem aboutNote, lookHelp;
	//�����˵���
	JPopupMenu popupMenu;
	//�����˵���ѡ��
	JMenuItem popupMenuUndo, popupMenuCut, popupMenuCopy, popupMenuPaste, popupMenuDelete, popupMenuSelectAll;
	//��ǩ��
	JLabel statusLabel1, statusLabel2;
	String oldText;//��ű༭��ԭ�������ݣ����ڱȽ��ı��Ƿ��иĶ�   
	File currentFile;      //���浱ǰ���ļ���
	//����������
	protected UndoManager undo = new UndoManager();    
    protected UndoableEditListener undoHandler = new UndoHandler();
    
    boolean isNewFile = true;      //�ж��Ƿ������ļ�
    
    PrintJob  p=null;//����һ��Ҫ��ӡ�Ķ���  
    Graphics  g=null;//Ҫ��ӡ�Ķ���  
    
    //ϵͳ������
    Toolkit toolkit=Toolkit.getDefaultToolkit();    
    Clipboard clipBoard = toolkit.getSystemClipboard();
	
	
	String aejbchopcEStr;
	
	/*
	 * UI��ʼ��
	 */
	public MyFrame() throws Exception{
		initFrame();
		initMenu();
		initTextEditArea();
		
	}
	
	/*
	 * UI��ʼ������
	 */
	public void initFrame() throws Exception {
		//Toolkit kit = Toolkit.getDefaultToolkit();
        this.setSize(680,541);  
        this.setLocationRelativeTo(null);  
        //�������Ի�����Ա��ر� 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //����һ������
        Container con = this.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        //��ǩ
        JLabel searchContentLabel = new JLabel("���� :");  
        JLabel SecurityLabel = new JLabel("����:");  
        JLabel JieMiLabel = new JLabel("���� :");
        JLabel AlgorithmContentLabel = new JLabel("�㷨:");
        JLabel HashContentLabel = new JLabel("Hash:");
        //�ı��༭��
        PlainText = new JTextArea(7, 55);  //�����ı���
        PlainText.setWrapStyleWord(true);  //���ֳ��ȳ���һ��ʱ�Զ�����
        PlainText.setLineWrap(true);  //�ı��༭��Ĭ���Զ�����
        
        EncryptionText = new JTextArea(8, 55);  // �����ı���
        EncryptionText.setWrapStyleWord(true);  //���ֳ��ȳ���һ��ʱ�Զ�����
        EncryptionText.setLineWrap(true);  //�ı��༭��Ĭ���Զ�����
        
        DecryptionText = new JTextArea(7, 55);  //�����ı��ı���
        DecryptionText.setWrapStyleWord(true);  //���ֳ��ȳ���һ��ʱ�Զ�����
        DecryptionText.setLineWrap(true);  //�ı��༭��Ĭ���Զ�����

        //��ť
        JButton Encryption = new JButton("����");  
        JButton Decryption = new JButton("����");
        JButton Help = new JButton("����");  
        Encryption.setPreferredSize(new Dimension(110, 22));   
        Decryption.setPreferredSize(new Dimension(110, 22));
        Help.setPreferredSize(new Dimension(110, 22));
        Encryption.addActionListener(this);
        Encryption.addActionListener (new ActionListener() {   //�����¼�����
            @Override  
            public void actionPerformed(ActionEvent e) {  
            	PlainText.requestFocus();   //�����ı��༭��
            	DecryptionStr = PlainText.getText();    //��ȡ���ĵ�����
            	Encryption(PlainText.getText());   //����ѡ��
            	if(!PlainText.getText().isEmpty()) {
            		EncryptionText.setText(EncryptionStr);
            	}
            	if(PlainText.getText().isEmpty()) {
            		EncryptionText.setText("");
            		DecryptionText.setText("");   //�����ܵ�������ʾ�����ܵ��ı�����
            	}
            }  
        });  
        
        Decryption.addActionListener(new ActionListener() {   //�����¼�����
            @Override  
            public void actionPerformed(ActionEvent e) {  
            	DecryptionText.requestFocus();   //�����ı��༭��
            	if(!PlainText.getText().isEmpty()) {
            		Decryption(EncryptionKeyStr);   //����   EncryptionKeyStr��Կ�����ȥ
            		DecryptionText.setText(JDecryptionStr);   //�����ܵ�������ʾ�����ܵ��ı�����
            	}
            	if(PlainText.getText().isEmpty()) {
            		remind();       
            	}	
            }  
        }); 
        
        Help.addActionListener(new ActionListener() {   //�����¼�����
            @Override  
            public void actionPerformed(ActionEvent e) {  
            	Help();
            }  
        }); 
        
        //�����˵�
        JComboBox<String> SecutityCombo = new JComboBox<>();
        SecutityCombo.addItem("DES");
        SecutityCombo.addItem("3DES");
        SecutityCombo.addItem("AES");
        SecutityCombo.setPreferredSize(new Dimension(110, 22));
        SecutityCombo.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                // TODO Auto-generated method stub  
            	EncryptionAlgorithm = SecutityCombo.getItemAt(SecutityCombo.getSelectedIndex()); 
            }  
        });  
        JComboBox<String> HashCombo = new JComboBox<>();
        HashCombo.addItem("MD5");
        HashCombo.addItem("SHA1");
        HashCombo.addItem("SHA256");
        HashCombo.addItem("SHA512");
        HashCombo.setPreferredSize(new Dimension(110, 22));
        HashCombo.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                // TODO Auto-generated method stub  
            	HashAlgorithm = HashCombo.getItemAt(HashCombo.getSelectedIndex()); 
            }  
        });

        JPanel bottomPanel = new JPanel(); 
        JPanel topPanel1 = new JPanel();
        JPanel topPanel3 = new JPanel();
        JPanel topPanel4 = new JPanel();
  
        topPanel1.add(searchContentLabel);  
        topPanel1.add(PlainText);
        topPanel3.add(SecurityLabel);  
        topPanel3.add(EncryptionText);
        topPanel4.add(JieMiLabel);  
        topPanel4.add(DecryptionText);

        bottomPanel.add(AlgorithmContentLabel);
        bottomPanel.add(SecutityCombo);
        bottomPanel.add(HashContentLabel);
        bottomPanel.add(HashCombo);
        bottomPanel.add(Encryption);
        bottomPanel.add(Decryption);
        bottomPanel.add(Help);
        
        con.add(topPanel1);
        con.add(topPanel3);
        con.add(topPanel4); 
        con.add(bottomPanel); 
      //����ͼ��  
        Image img = new ImageIcon("image/logo.jpg").getImage();  
        this.setIconImage(img);  
      //���ñ�������  
        this.setTitle("����ϵͳ  by 170816");  
	}   
	
	/*
	 * ϵͳ�����㷨ѡ��
	 */
	public void Encryption(String PlainAreaText) {      	
		if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("MD5") ) {
			//MD5 ����	 
	        String HStr = HashUtil.string2MD5(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//DES����
			byte[] byteRKA1 = DESUtil.initKey();   //������Կ
			byteResult = DESUtil.encryptDES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		 }
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("MD5") ) {
			//MD5 ����	 
	        String HStr = HashUtil.string2MD5(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//3DES����
			byte[] byteRKA1 = DESede.initKey();   //������Կ
			byteResult = DESede.encrypt3DES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("MD5") ) {
			//MD5 ����	 
	        String HStr = HashUtil.string2MD5(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//AES����
			byte[] byteRKA1 = AESUtil.initKey();   //������Կ
			byteResult = AESUtil.encryptAES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������ 
		    
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA1") ) {
			//SHA1 ����	 
	        String HStr = HashUtil.SHA1(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//DES����
			byte[] byteRKA1 = DESUtil.initKey();   //������Կ
			byteResult = DESUtil.encryptDES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA1") ) {
			//SHA1 ����	 
	        String HStr = HashUtil.SHA1(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//3DES����
			byte[] byteRKA1 = DESede.initKey();   //������Կ
			byteResult = DESede.encrypt3DES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA1") ) {
			//SHA1 ����	 
	        String HStr = HashUtil.SHA1(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//AES����
			byte[] byteRKA1 = AESUtil.initKey();   //������Կ
			byteResult = AESUtil.encryptAES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������ 
		    
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA256") ) {
			//SHA256 ����	 
	        String HStr = HashUtil.SHA256(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//DES����
			byte[] byteRKA1 = DESUtil.initKey();   //������Կ
			byteResult = DESUtil.encryptDES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA256") ) {
			//SHA256 ����	 
	        String HStr = HashUtil.SHA256(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//3DES����
			byte[] byteRKA1 = DESede.initKey();   //������Կ
			byteResult = DESede.encrypt3DES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������
		    
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA256") ) {
			//SHA256 ����	 
	        String HStr = HashUtil.SHA256(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//AES����
			byte[] byteRKA1 = AESUtil.initKey();   //������Կ
			byteResult = AESUtil.encryptAES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������ 
		    
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA512") ) {
			//SHA512 ����	 
	        String HStr = HashUtil.SHA512(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//DES����
			byte[] byteRKA1 = DESUtil.initKey();   //������Կ
			byteResult = DESUtil.encryptDES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������    			
			
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA512") ) {
			//SHA512 ����	 
	        String HStr = HashUtil.SHA512(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//3DES����
			byte[] byteRKA1 = DESede.initKey();   //������Կ
			byteResult = DESede.encrypt3DES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA512") ) {
			//SHA512 ����	 
	        String HStr = HashUtil.SHA512(PlainAreaText);   //MD5����  16������ʾ
	    	
			//RSA˽Կ����
			Map<String, String> keyMap = RSAUtil.createKeys(1024);
			publicKey1 = keyMap.get("publicKey");
		    privateKey1 = keyMap.get("privateKey");
		    EncryptionKeyStr = RSAUtil.privateEncrypt(HStr, RSAUtil.getPrivateKey(privateKey1));
		   	
		    //H(M)||M
			String EncryptionString = EncryptionKeyStr.concat(DecryptionStr);  //�ַ���ƴ��  HM + M

			//AES����
			byte[] byteRKA1 = AESUtil.initKey();   //������Կ
			byteResult = AESUtil.encryptAES(EncryptionString.getBytes(), byteRKA1);
			//���ܺ������
			EncryptionStr = BytesAndHex.fromBytesToHex(byteResult);
			
			String StrRKA1 = BytesAndHex.fromBytesToHex(byteRKA1);
			//RSA��Կ����			
			Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
			publicKey2 = keyMap2.get("publicKey");
		    privateKey2 = keyMap2.get("privateKey");
		    //�ԶԳ���Կ���м���
		    RKey = RSAUtil.publicEncrypt(StrRKA1, RSAUtil.getPublicKey(publicKey2));  //16������ʾ[H(m)+m]���ܺ������ 
		    
		}
	}
	
	/*
	 * ϵͳ�����㷨ѡ��
	 */
	public void Decryption(String EncryptionAreaText) {
		if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("MD5") ) {
			try {
				
				///RSA���ܳ�DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//DES ����
				byte[] plain1 = DESUtil.decryptDES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.string2MD5(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("MD5") ) {
			try {				
				///RSA˽Կ���ܳ�3DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//3DES ����
				byte[] plain1 = DESede.decrypt3DES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.string2MD5(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
			
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("MD5") ) {
			try {				
				///RSA˽Կ���ܳ�AES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//AES ����
				byte[] plain1 = AESUtil.decryptAES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.string2MD5(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA1") ) {
			try {				
				///RSA˽Կ���ܳ�DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//DES ����
				byte[] plain1 = DESUtil.decryptDES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA1(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA1") ) {
			try {				
				///RSA˽Կ���ܳ�3DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//3DES ����
				byte[] plain1 = DESede.decrypt3DES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA1(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA1") ) {
			try {				
				///RSA˽Կ���ܳ�AES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//AES ����
				byte[] plain1 = AESUtil.decryptAES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA1(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA256") ) {
			try {				
				///RSA˽Կ���ܳ�DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//DES ����
				byte[] plain1 = DESUtil.decryptDES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA256(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA256") ) {
			try {			
				///RSA˽Կ���ܳ�3DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//3DES ����
				byte[] plain1 = DESede.decrypt3DES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA256(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA256") ) {
			try {				
				///RSA˽Կ���ܳ�AES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//AES ����
				byte[] plain1 = AESUtil.decryptAES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA256(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("DES") && HashAlgorithm.equals("SHA512") ) {
			try {				
				///RSA˽Կ���ܳ�DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//DES ����
				byte[] plain1 = DESUtil.decryptDES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA512(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("3DES") && HashAlgorithm.equals("SHA512") ) {
			try {
				///RSA˽Կ���ܳ�3DES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//3DES ����
				byte[] plain1 = DESede.decrypt3DES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA512(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if( EncryptionAlgorithm.equals("AES") && HashAlgorithm.equals("SHA512") ) {
			try {	
				///RSA˽Կ���ܳ�AES����Կ
				String StrKey =RSAUtil.privateDecrypt(RKey, RSAUtil.getPrivateKey(privateKey2));

				//AES ����
				byte[] plain1 = AESUtil.decryptAES(byteResult,BytesAndHex.fromHexToBytes( StrKey));				
				String FirstJDecryptionStr = HashUtil.HexToString(BytesAndHex.fromBytesToHex(plain1));				
				String HmStr = FirstJDecryptionStr.substring(0,EncryptionKeyStr.length());  //�ַ������  ���ܵ�����
				//���ܵ�����
				String JDecryptionString = FirstJDecryptionStr.substring(EncryptionKeyStr.length(),FirstJDecryptionStr.length());  //�ַ������  ���ܵ�����
							
				//RSA����hash(m)
				String DeHmStr =RSAUtil.publicDecrypt(HmStr, RSAUtil.getPublicKey(publicKey1));
				
				//HASH����
				String EnHmStr  = HashUtil.SHA512(JDecryptionString);   //MD5����  16������ʾ
							
				if(DeHmStr.equals(EnHmStr)) {            //��֤H(m)
					JOptionPane.showConfirmDialog(this, "��֤�ɹ�", "������", JOptionPane.WARNING_MESSAGE);
					JDecryptionStr = JDecryptionString;   //��������Ϣ����
				}				
			} catch(NullPointerException N) {
				JOptionPane.showConfirmDialog(this, "Ϊ����������ݻ�δ���������ݽ��м���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	public void Help() {
		JOptionPane.showMessageDialog(this, "*****************************************\n"
				+ "* ����ʹ����Կ������ʽ��������  *\n"
				+ "*(H(m)||m)���ܵ�����16������ʾ*\n"
				+ "*****************************************", "����", JOptionPane.QUESTION_MESSAGE);
	}
	

	public void remind() {
		JOptionPane.showConfirmDialog(this, "δ�����������", "��ʾ", JOptionPane.WARNING_MESSAGE);
	}
	
	public void initMenu() {
		JMenuBar menuBar = new JMenuBar();  //����һ���˵���
		setJMenuBar(menuBar);  //���˵������ӵ������

		//�����ļ��˵�
		fileMenu = new JMenu("�ļ�(F)");
		fileMenu.setMnemonic('F');  //���ÿ�ݼ�Alt+F
		menuBar.add(fileMenu);
		
		fileNew = new JMenuItem("�½�(N)");  //�½��˵���
		//���ÿ�ݼ�
		fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		fileNew.addActionListener(this);  //�����¼�����
		fileMenu.add(fileNew);   //���Ӳ˵���
		
		fileOpen = new JMenuItem("��(O)");
		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileOpen.addActionListener(this);
		fileMenu.add(fileOpen);
		
		fileSave = new JMenuItem("����(S)");
		fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		fileSave.addActionListener(this);
		fileMenu.add(fileSave);
		
		fileSaveAs = new JMenuItem("����Ϊ(A)...");
		fileSaveAs.addActionListener(this);
		fileMenu.add(fileSaveAs);
		fileMenu.addSeparator();
		
		filePageSet = new JMenuItem("ҳ������(U)...");
		filePageSet.addActionListener(this);
		fileMenu.add(filePageSet);
		
		filePrint = new JMenuItem("��ӡ(P)");
		filePrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		filePrint.addActionListener(this);
		fileMenu.add(filePrint);
		fileMenu.addSeparator();
		
		fileExit = new JMenuItem("�˳���X");
		fileExit.addActionListener(this);
		fileMenu.add(fileExit);
		
		//�����༭�˵�
		editMenu = new JMenu("�༭(E)");
		editMenu.setMnemonic('E');
		menuBar.add(editMenu);
		
		//��ѡ��༭�˵�ʱ�����ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����  
		editMenu.addMenuListener(new MenuListener(){
			public void menuCanceled(MenuEvent e) {  //��ȡ���˵���ʱ�����
				checkMenuItemEnabled();   //���ù��ܵĿ�����    
			}
			public void menuDeselected(MenuEvent e) { //��ȡ��ѡ��ĳ���˵�ʱѡ��ʹ��
				checkMenuItemEnabled();
			}
			public void menuSelected(MenuEvent e) {  //��ѡ��ĳ���˵�ʱ����
				checkMenuItemEnabled();
			}
		});
		
		editUndo = new JMenuItem("����(U)");
		editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		editUndo.addActionListener(this);
		editUndo.setEnabled(false);  //����û��д������֮ǰ������
		editMenu.add(editUndo);
		
		editCut = new JMenuItem("����(T)");
		editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		editCut.addActionListener(this);
		editCut.setEnabled(false);
		editMenu.add(editCut);
		
		editCopy = new JMenuItem("����(C)");
		editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		editCopy.addActionListener(this);
		editCopy.setEnabled(false);
		editMenu.add(editCopy);
		
		editPaste = new JMenuItem("ճ��(P)");
		editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		editPaste.addActionListener(this);
		editPaste.setEnabled(false);
		editMenu.add(editPaste);
		
		editDelete = new JMenuItem("ɾ��(L)");
		editDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		editDelete.addActionListener(this);
		editMenu.add(editDelete);
		editMenu.addSeparator();
		
		editFind = new JMenuItem("����(F)");
		editFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		editFind.addActionListener(this);
		editMenu.add(editFind);
		
		editFindNext = new JMenuItem("������һ��(N)");
		editFindNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		editFindNext.addActionListener(this);
		editMenu.add(editFindNext);
		
		editReplace = new JMenuItem("�滻(R)...");
		editReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		editReplace.addActionListener(this);
		editMenu.add(editReplace);
		
		editGoTo = new JMenuItem("ת��(G)...");
		editGoTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		editGoTo.addActionListener(this);
		editMenu.add(editGoTo);
		editMenu.addSeparator();
		
		editSelectAll = new JMenuItem("ȫѡ(A)");
		editSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		editSelectAll.addActionListener(this);
		editMenu.add(editSelectAll);
		
		editDate = new JMenuItem("ʱ��/����(D)");
		editDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		editDate.addActionListener(this);
		editMenu.add(editDate);
		
		//������ʽ�˵�
		formatMenu = new JMenu("��ʽ(O)");
		formatMenu.setMnemonic('O');
		menuBar.add(formatMenu);
		
		formatLineWrap = new JCheckBoxMenuItem("�Զ�����(W)");
		formatLineWrap.setMnemonic('W');
		formatLineWrap.setState(true);
		formatLineWrap.addActionListener(this);
		formatMenu.add(formatLineWrap);
		
		formatFont = formatMenu.add(new JMenuItem("����(F)"));
		formatFont.addActionListener(this);
		
		formatBackGround = formatMenu.add(new JMenuItem("����(B)"));
		formatBackGround.addActionListener(this);
		
		formatFontColor = formatMenu.add(new JMenuItem("������ɫ(F)"));
		formatFontColor.addActionListener(this);
				
		
		//�����鿴�˵�
		viewMenu = menuBar.add(new JMenu("�鿴(V)"));
		viewMenu.setMnemonic('V');
		
		stateItem = new JCheckBoxMenuItem("״̬��(S)");  //״̬��һ��ʼ�ͱ�ѡ��
		stateItem.setState(true);
		stateItem.addActionListener(this);
		viewMenu.add(stateItem);
	
		
		//���������˵�
		helpMenu =menuBar.add( new JMenu("����(H)"));
		helpMenu.setMnemonic('H');
		
		lookHelp = helpMenu.add(new JMenuItem("�鿴����(H)"));
		lookHelp.addActionListener(this);
		helpMenu.addSeparator();
		
		aboutNote = helpMenu.add(new JMenuItem("���ڼ���ϵͳ(A)"));
		aboutNote.addActionListener(this);
	}
	
	//��ʼ���ı��༭��
	public void initTextEditArea() {
		//�����ı��༭��
		editArea = PlainText;
		oldText = editArea.getText();   //��ȡ�ı��༭��������
		//�������������¼�����
		editArea.getDocument().addUndoableEditListener(undoHandler);
		editArea.getDocument().addDocumentListener(this);
		
		//�����Ҽ������˵�
		popupMenu = new JPopupMenu();
		
		popupMenuUndo = new JMenuItem("����(U)");
		popupMenuUndo.addActionListener(this);
		popupMenuUndo.setEnabled(false);   
		popupMenu.add(popupMenuUndo);
		popupMenu.addSeparator();
		
		popupMenuCut = new JMenuItem("����(T)");
		popupMenuCut.addActionListener(this);
		popupMenu.add(popupMenuCut);
		
		popupMenuCopy = new JMenuItem("����(C)");
		popupMenuCopy.addActionListener(this);
		popupMenu.add(popupMenuCopy);
		
		popupMenuPaste = new JMenuItem("ճ��(P)");
		popupMenuPaste.addActionListener(this);
		popupMenu.add(popupMenuPaste);
		
		popupMenuDelete = new JMenuItem("ɾ��(D)");
		popupMenuDelete.addActionListener(this);
		popupMenu.add(popupMenuDelete);
		popupMenu.addSeparator();
		
		popupMenuSelectAll = new JMenuItem("ȫѡ(A)");
		popupMenuSelectAll.addActionListener(this);
		popupMenu.add(popupMenuSelectAll);
		
		//�ı��༭��ע������Ҽ��¼�  //mouse����д���飬��Ϊ����ͷ�����״̬
		editArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()) {    //�ж��Ƿ��Ǳ༭������������Ҽ��¼�,�����˵�������
					popupMenu.show(e.getComponent(), e.getX(), e.getY());  //����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
				}
				checkMenuItemEnabled();   //���ò˵����ܵĿ�����    
				editArea.requestFocus();  //�༭����ȡ����
			}
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {    //�ж��Ƿ��Ǳ༭������������Ҽ��¼�,�����˵�������
					popupMenu.show(e.getComponent(), e.getX(), e.getY());  //����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
				}
				checkMenuItemEnabled();   //���ò˵����ܵĿ�����    
				editArea.requestFocus();  //�༭����ȡ����
			}
		});
		//component.setComponentPopupMenu(popupMenu);
		
		//����������״̬��
		JPanel panel1 = new JPanel();
		statusLabel1 = new JLabel("");
		statusLabel2 = new JLabel("�ļ�״̬");
		
		panel1.add(statusLabel1);
		panel1.add(statusLabel2);  
		
		this.add(panel1, BorderLayout.SOUTH);//�򴰿�����״̬����ǩ  
		
		//���Ӵ��ڼ�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitWindowChoose();
			}
		});
		
		checkMenuItemEnabled();
		editArea.requestFocus();
				
	}
	
	//��������������ʵ��
	public void actionPerformed(ActionEvent e) {  
		//***********************************�ļ��˵�������************************************//
		//�½��ı�����
		String currentText = editArea.getText();   //��ȡ��ǰ�ı��е�����
		boolean isTextChange = (currentText.equals(oldText))?true:false;;   //�����ж��ı������Ƿ����仯
		if(e.getSource() == fileNew) {
			if(!isTextChange) {    //�ı����ݷ����仯
				int saveChoose = JOptionPane.showConfirmDialog(this,"�ļ���δ���棬�Ƿ񱣴棿", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
				if(saveChoose == JOptionPane.YES_OPTION) {
					saveAs();
				}
				else if(saveChoose == JOptionPane.NO_OPTION) {
					fileNew();   //�½��ı�
					//return;
				}
				else {
					statusLabel2.setText("δѡ�񱣴��κ��ļ�");
					return;
				}	
			}
			else {
				fileNew();
			}
		}
		//���ļ�����
		else if(e.getSource() == fileOpen) {
			
			if(!isTextChange) {
				int saveChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ���棬�Ƿ񱣴�", "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
				if(saveChoose == JOptionPane.YES_OPTION) {
					saveAs();
				}
				else if(saveChoose == JOptionPane.NO_OPTION) {
					statusLabel2.setText("δѡ�񱣴��κ��ļ�");
					fileOpen();
				}
				else {
					return;
				}
			}
			else {
				fileOpen();
			}
		}
		//�ļ�����
		else if(e.getSource() == fileSave) {
			if(isNewFile) {
				saveNewFile();
			}
			else {
				saveNotNewFile();
			}
		}
		//�ļ�����Ϊ
		else if(e.getSource() == fileSaveAs) {
			saveAs();
		}
		//ҳ������
		else if(e.getSource() == filePageSet) {
			//JOptionPane.showConfirmDialog(this, "ҳ�����ù��ܴ�ʵ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
			 PageFormat pf = new PageFormat();  
	         PrinterJob.getPrinterJob().pageDialog(pf);
		}
		//��ӡ����
		else if(e.getSource() == filePrint) {
			print();
		}
		//�˳�����
		else if(e.getSource() == fileExit ){
			exitWindowChoose(); 
		}
		
		//*******************************�༭�˵�������ʵ��************************************************//
		//��������
		else if(e.getSource() == editUndo || e.getSource() == popupMenuUndo) {
			if(undo.canUndo()) {     //�����������
				try {
					undo.undo();
				}catch(CannotUndoException cux) {
					System.out.println("Unable to undo" + cux);
				}
			}
			else {         //������������ʱ�����ù��ܽ���
				editUndo.setEnabled(false);
				popupMenuUndo.setEnabled(false);
			}
		}
		//���й���
		else if(e.getSource() == editCut || e.getSource() == popupMenuCut) {
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);  //��ѡ�е��ı����ӵ�ϵͳ������
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());   //���ı��еĴ�start��end�������滻
			checkMenuItemEnabled();  //����ʱ���ܵĿ�����
		}
		//���ƹ���
		else if(e.getSource() == editCopy || e.getSource() == popupMenuCopy) {
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			checkMenuItemEnabled();
		}
		//ճ������
		else if(e.getSource() == editPaste || e.getSource() == popupMenuPaste) {
			Transferable contents = clipBoard.getContents(this);   //��ȡճ�����ϵ�����
			if(contents == null) {
				return;
			}
			String text = "";
			try {
				text = (String) contents.getTransferData(DataFlavor.stringFlavor);  //��ʾ Java Unicode String ��  
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			editArea.replaceRange(text, editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();
		}
		//ɾ������
		else if(e.getSource() == editDelete || e.getSource() == popupMenuDelete) {
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();
		}
		//���ҹ���
		else if(e.getSource() == editFind) {
			find();
		}
		//������һ��
		else if(e.getSource() == editFindNext) {
			find();
		}
		//�滻����
		else if(e.getSource() == editReplace) {
			replace();
		}
		//ת������
		else if(e.getSource() == editGoTo) {
			turnTo();
		}
		//ȫѡ����
		else if(e.getSource() == editSelectAll || e.getSource()==popupMenuSelectAll) {
			editArea.selectAll();
		}
		//���ڹ���
		else if(e.getSource() == editDate) {
			Calendar now = Calendar.getInstance();
			Date date = now.getTime();
			editArea.insert(date.toString(), editArea.getCaretPosition());         //��ȡ��ǰ�ı��༭����λ�ò�����
		}
		//*****************************��ʽ���˵�ѡ���ʵ��****************************************************//
		//�Զ�����
		else if(e.getSource() == formatLineWrap) {
			if(formatLineWrap.getState()) {
				editArea.setLineWrap(true);
			}
			else {
				editArea.setLineWrap(false);
			}
		}
		//��������
		else if(e.getSource() == formatFont) {
			MyFont font = new MyFont(editArea.getFont());
			int returnValue = font.showFontDialog(this);
			if(returnValue == font.APPROVE_OPTION) {
				Font f = font.getSelectFont();
				editArea.setFont(f);
			}
			else {
				statusLabel2.setText("δѡ��������");
				return;
			}
		}
		//����ɫ����
		else if(e.getSource() == formatBackGround) {
			Color color = JColorChooser.showDialog(this, "��ɫѡ��", Color.BLACK);
			editArea.setBackground(color);
		}
		//������ɫ����
		else if(e.getSource() == formatFontColor) {
			Color color = JColorChooser.showDialog(this, "��ɫѡ��", Color.BLACK);
			editArea.setForeground(color);
		}
		//********************************�鿴�˵������ܵ�ʵ��**************************************************//
		//״̬��
		else if (e.getSource()== stateItem) {  
            if(stateItem.getState()){  
                statusLabel1.setVisible(true);  
                statusLabel2.setVisible(true);  
            }    
            else{  
                statusLabel1.setVisible(false);  
                statusLabel2.setVisible(false);  
            }     
        }
		//*********************************�����˵���ѡ��ܵ�ʵ��***********************************************//
		//�鿴����
		else if(e.getSource() == lookHelp) {
			JOptionPane.showConfirmDialog(this, "ûʲô�ܰ����ģ������������ÿ��Լ�", "��ʾ", JOptionPane.WARNING_MESSAGE);
		}
		//���ڼ��±�
		else if(e.getSource() == aboutNote) {
			JOptionPane.showMessageDialog(this, "******************************\n"
					+ "* Created by Wilson song *\n"
					+ "*            2018-05-22             *\n"
					+ "******************************", "����", JOptionPane.QUESTION_MESSAGE);
		}
		
		else if(DecryptionStr.isEmpty()) {
			JOptionPane.showConfirmDialog(this, "δ�����������", "��ʾ", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void removeUpdate(DocumentEvent r) {
		editUndo.setEnabled(true);
		popupMenuUndo.setEnabled(true);
	}
	
	public void changedUpdate(DocumentEvent c) {
		editUndo.setEnabled(true);
		popupMenuUndo.setEnabled(true);
	}
	
	public void insertUpdate(DocumentEvent i) {
		editUndo.setEnabled(true);
		popupMenuUndo.setEnabled(true);
		
	}
	
	//���ò˵���Ŀ�����
	public void checkMenuItemEnabled() {
		//����ճ��ɾ������
		String selectText = editArea.getSelectedText();
		if(selectText == null) {
			editCut.setEnabled(false);
			editCopy.setEnabled(false);
			editDelete.setEnabled(false);
			
			popupMenuCopy.setEnabled(false);
			popupMenuCut.setEnabled(false);
			popupMenuDelete.setEnabled(false);
		}
		else {
			editCut.setEnabled(true);
			editCopy.setEnabled(true);
			editDelete.setEnabled(true);
			
			popupMenuCopy.setEnabled(true);
			popupMenuCut.setEnabled(true);
			popupMenuDelete.setEnabled(true);
		}
		//ճ������
		Transferable contents=clipBoard.getContents(this);     //��ȡ�����������
		if(contents == null) {
			editPaste.setEnabled(false);
			popupMenuPaste.setEnabled(false);
		}
		else {
			editPaste.setEnabled(true);
			popupMenuPaste.setEnabled(true);
		}
	}
	
	public void exitWindowChoose() {
		editArea.requestFocus();   //�����ı��༭��
		String currentText = editArea.getText();   //��ȡ��ǰ�༭��������
		if(currentText.equals(oldText)) {
			setMemory();            //�����ļ�������
			System.exit(0);
		}
		else {
			int exitChoose = JOptionPane.showConfirmDialog(this, "�ļ���δ����", 
					"�˳���ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
			if(exitChoose == JOptionPane.YES_OPTION) {
				if(isNewFile) {
					saveNewFile();
				}
				else {
					saveNotNewFile();
				}
				setMemory();
				System.exit(0);
			}
			else if (exitChoose == JOptionPane.NO_OPTION){
				setMemory();
				System.exit(0);
			}
			else if (exitChoose == JOptionPane.CANCEL_OPTION) {
				statusLabel2.setText("�ļ�δ����");
				return;
			}
			else {
				return;
			}
		}
	}
	
	//�����ļ�������
	public  void setMemory() {
		Properties size = new Properties();
		size.setProperty("x", this.getBounds().x + "");
		size.setProperty("y", this.getBounds().y + "");
		size.setProperty("width", this.getBounds().width + "");
		size.setProperty("height", this.getBounds().height + "");
		size.setProperty("fontName", this.editArea.getFont().getFamily() + "");
		size.setProperty("fontStyle", this.editArea.getFont().getStyle() + "");
		size.setProperty("fontSize", this.editArea.getFont().getSize() + "");
		size.setProperty("foreGround", this.editArea.getForeground() + "");
		size.setProperty("backGround", this.editArea.getBackground() + "");
		
		FileWriter fr;
		try {
			fr = new FileWriter("src/size.properties");
			size.store(fr, "Size Info");
			fr.close();
		} catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	//�����ļ�����ʱ�������ļ�
	public void saveNewFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);    //ֻ����ʽ��
		fileChooser.setApproveButtonText("ȷ��");
		fileChooser.setDialogTitle("����Ϊ");
		
		int result = fileChooser.showSaveDialog(this);
		if(result == JFileChooser.CANCEL_OPTION) {
			statusLabel2.setText("�ļ�δ����");
			return;
		}
		
		File saveFileName = fileChooser.getSelectedFile();
		if(saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "������ļ���", "������ļ���", JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {        //���ļ�����
				OutputStream os = new FileOutputStream(saveFileName);   //�����ļ�
				OutputStreamWriter osw = new OutputStreamWriter(os);   //OutputStreamWriter�Ľ���������OutputStream���ֽ�ת��Ϊ�ַ���
				PrintWriter pw = new PrintWriter(osw);  //�ַ������
				pw.write(editArea.getText());  //��ȡ�ı��༭���е����ݲ�д���ı�
				pw.flush();     //�������������������
				pw.close();    //�ر����й������������
				
				isNewFile  = false;
				currentFile = saveFileName;
				this.setTitle(saveFileName.getName());
				statusLabel2.setText("��ǰ�ļ���Ϊ��" + saveFileName.getAbsolutePath());   //��ȡ�ļ��ľ���·��
				
			}catch(IOException ioe) {
				ioe.printStackTrace();     //�׳��쳣������λ�ú�ԭ��
			}
		}
	}
	
	//���ļ�����û�з����ı�ʱ���������ļ�
	public void saveNotNewFile() {
		try {
			OutputStream os = new FileOutputStream(currentFile);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw);
			pw.write(editArea.getText());
			pw.flush();
			pw.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//�ļ�����Ϊ
	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setApproveButtonText("ȷ��");
		fileChooser.setDialogTitle("����Ϊ");
		
		int result = fileChooser.showSaveDialog(this);
		if(result == JFileChooser.CANCEL_OPTION) {
			statusLabel2.setText("�ļ�δ����");
			return;
		}
		
		File saveFileName = fileChooser.getSelectedFile();
		if(saveFileName == null || saveFileName.getName().equals("")) {
			JOptionPane.showMessageDialog(this, "������ļ���", "������ļ���", JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {
				OutputStream os = new FileOutputStream(currentFile);
				OutputStreamWriter osw = new OutputStreamWriter(os);
				PrintWriter pw = new PrintWriter(osw);
				pw.write(editArea.getText());
				pw.flush();
				pw.close();
				
				isNewFile = false;
				currentFile = saveFileName;
				this.setTitle(saveFileName.getName());
				statusLabel2.setText("��ǰ�ļ���  " + saveFileName.getAbsolutePath());
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
	}
	
	//�½��ļ�
	public void fileNew() {
		editArea.replaceRange("", 0, editArea.getText().length());
		statusLabel2.setText("�½��ļ�");
		this.setTitle("�ޱ���");
		isNewFile = true;
		undo.discardAllEdits();  //�������еĲ���
		editUndo.setEnabled(false);
		oldText = editArea.getText();
	}
	
	//���ļ�
	public void fileOpen() {
		JFileChooser fileChooser = new JFileChooser();
		//�ļ�������
		javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
			@Override
            public String getDescription() {
                // TODO Auto-generated method stub
                return ".txt";
            }
			@Override
            public boolean accept(File f) {
                // TODO Auto-generated method stub
                String name = f.getName();
                return f.isDirectory() || name.toLowerCase().endsWith(".txt"); // ����ʾĿ¼��txt�ļ�
                                                                                // ;
            }
	    };
	    fileChooser.addChoosableFileFilter(filter);
	    fileChooser.setFileFilter(filter);
	    
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);   //ֻ���ļ�
		fileChooser.setDialogTitle("���ļ�");
		
		int result = fileChooser.showOpenDialog(this);
		if(result == JFileChooser.CANCEL_OPTION) {
			statusLabel2.setText("δѡ���κ��ļ�");
			return;
		}
		File fileName = fileChooser.getSelectedFile();
		if(fileName == null || fileName.getName().equals("")) {  //�ļ������Ϸ�
			JOptionPane.showConfirmDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
		}
		else {
			try {
				FileInputStream fileInputStream = new FileInputStream(fileName);
				byte[] content = new byte[fileInputStream.available()];        //??????????????????????????????
				fileInputStream.read(content);
				editArea.setText(new String (content));
				editArea.setCaretPosition(0);  //????????????????????????
				fileInputStream.close();
				
				this.setTitle(fileName.getName());
				statusLabel2.setText("��ǰ���ļ���" + fileName.getAbsoluteFile());  //�ļ���+�ļ�·��
				isNewFile = false;
				currentFile = fileName;
				oldText = editArea.getText();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}	
	}
	
	//���ҹ���
	public void find() {
		final JDialog findDialog = new JDialog(this,"����",false);  //falseʱ������������ͬʱ���ڼ���״̬(����ģʽ)����������Ĵ����ǿ��Ե�����
		Container con = findDialog.getContentPane();    //���ش˶Ի����contentPane����
		con.setLayout(new FlowLayout(FlowLayout.LEFT));   //????????????
		JLabel findContentLabel = new JLabel("��������(N): ");
		final JTextField findText = new JTextField(15);
		JButton findNextButton = new JButton("������һ��(F): ");
		final JCheckBox matchCheckBox = new JCheckBox("���ִ�Сд(C): ");
		ButtonGroup buttonGroup = new ButtonGroup();
		final JRadioButton upButton = new JRadioButton("����(U)");
		final JRadioButton downButton = new JRadioButton("����(D)");
		downButton.setSelected(true);
		buttonGroup.add(upButton);
		buttonGroup.add(downButton);
		/*ButtonGroup��������Ϊһ�鰴ť����һ����⣨multiple-exclusion��������  
        	ʹ����ͬ�� ButtonGroup ���󴴽�һ�鰴ť��ζ�š�����������һ����ťʱ�����ر����е��������а�ť��*/    
		/*JRadioButton����ʵ��һ����ѡ��ť���˰�ť��ɱ�ѡ���ȡ��ѡ�񣬲���Ϊ�û���ʾ��״̬��  
        	�� ButtonGroup �������ʹ�ÿɴ���һ�鰴ť��һ��ֻ��ѡ�����е�һ����ť��  
        	������һ�� ButtonGroup �������� add ������ JRadioButton ��������ڴ����С���*/ 
		JButton cancel = new JButton("ȡ��");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findDialog.dispose();                //???????????????????
			}
		});
		//������һ��
		findNextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���ִ�Сд(C) ��JCheckBox�Ƿ�ѡ��
				int k=0, m=0;
				final String str1, str2, str3, str4, strA, strB;
				str1 = editArea.getText();
				str2 = editArea.getText();
				str3 = str1.toUpperCase();  //ת��Ϊ��д
				str4 = str2.toUpperCase();
				if(matchCheckBox.isSelected()) {    //���ִ�Сд
					strA = str1;
					strB = str2;
				}
				else {  //�����ִ�Сд��ȫ��ת��Ϊ��д
					strA = str3;
					strB = str4;
				}
				//���ϲ���
				if(upButton.isSelected()) {
					if(editArea.getSelectedText() == null) {
						k = strA.lastIndexOf(strB, editArea.getCaretPosition()-1);
					}
					else {
						k = strB.lastIndexOf(strB, editArea.getCaretPosition() - findText.getText().length() - 1);
					}
					if(k > -1) {
						editArea.setCaretPosition(k);      //��λ��ѡ�е�λ��
						editArea.select(k,  k + strB.length());   //ѡ�е�����
					}
					else {
						JOptionPane.showConfirmDialog(null, "δ�ҵ���������", "����", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(downButton.isSelected()) {
					if(editArea.getSelectedText() == null) {
						k = strA.indexOf(strB, editArea.getCaretPosition() + 1);
					}
					else {
						k = strA.indexOf(strB, editArea.getCaretPosition() - findText.getText().length() - 1);
					}
					if(k > -1) {
						editArea.setCaretPosition(k);
						editArea.select(k, k + strB.length());
					}
					else {
						JOptionPane.showConfirmDialog(null, "δ�ҵ���������", "����", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		//���ҽ���
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel directionPanel = new JPanel();
		directionPanel.setBorder(BorderFactory.createTitledBorder("����"));
		//����directionPanel�ı߿�
		directionPanel.add(upButton);
		directionPanel.add(downButton);
		panel1.setLayout(new GridLayout(2,1));
		panel1.add(findNextButton);
		panel1.add(cancel);
		panel2.add(findContentLabel);
		panel2.add(findText);
		panel2.add(panel1);
		panel3.add(matchCheckBox);
		panel3.add(directionPanel);
		con.add(panel2);
		con.add(panel3);
		findDialog.setSize(410, 180);
		findDialog.setResizable(false);
		findDialog.setLocation(230, 280);
		findDialog.setVisible(true);
		
	}
	
	//�滻���ܵ�ʵ��
	 public void replace() {  
        final JDialog findDialog = new JDialog(this, "�滻", true);  
        Container con = findDialog.getContentPane();  
        con.setLayout(new FlowLayout(FlowLayout.LEFT));  
        JLabel searchContentLabel = new JLabel("��������(N) :");  
        JLabel replaceContentLabel = new JLabel("�滻Ϊ(P)�� :");  
        final JTextField findText = new JTextField(22);  
        final JTextField replaceText = new JTextField(22);  
        final JCheckBox matchcase = new JCheckBox("���ִ�Сд");  
        ButtonGroup bGroup = new ButtonGroup();  
        final JRadioButton up = new JRadioButton("����(U)");  
        final JRadioButton down = new JRadioButton("����(D)");  
        down.setSelected(true);  
        bGroup.add(up);  
        bGroup.add(down);  
        JButton searchNext = new JButton("������һ��(F)");  
        JButton replace = new JButton("�滻(R)");  
        final JButton replaceAll = new JButton("ȫ���滻(A)");  
        searchNext.setPreferredSize(new Dimension(110, 22));  
        replace.setPreferredSize(new Dimension(110, 22));  
        replaceAll.setPreferredSize(new Dimension(110, 22));  
        // "�滻"��ť���¼�����  
        replace.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
  
                if (replaceText.getText().length() == 0 && editArea.getSelectedText() != null)  
                    editArea.replaceSelection("");  
                if (replaceText.getText().length() > 0 && editArea.getSelectedText() != null)  
                    editArea.replaceSelection(replaceText.getText());  
            }  
        });  
  
        // "�滻ȫ��"��ť���¼�����  
        replaceAll.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
  
                editArea.setCaretPosition(0); // �����ŵ��༭����ͷ  
                int a = 0, b = 0, replaceCount = 0;  
  
                if (findText.getText().length() == 0) {  
                    JOptionPane.showMessageDialog(findDialog, "����д��������!", "��ʾ", JOptionPane.WARNING_MESSAGE);  
                    findText.requestFocus(true);  
                    return;  
                }  
                while (a > -1) {  
  
                    int FindStartPos = editArea.getCaretPosition();  
                    String str1, str2, str3, str4, strA, strB;  
                    str1 = editArea.getText();  
                    str2 = str1.toLowerCase();  
                    str3 = findText.getText();  
                    str4 = str3.toLowerCase();  
  
                    if (matchcase.isSelected()) {  
                        strA = str1;  
                        strB = str3;  
                    } else {  
                        strA = str2;  
                        strB = str4;  
                    }  
  
                    if (up.isSelected()) {  
                        if (editArea.getSelectedText() == null) {  
                            a = strA.lastIndexOf(strB, FindStartPos - 1);  
                        } else {  
                            a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);  
                        }  
                    } else if (down.isSelected()) {  
                        if (editArea.getSelectedText() == null) {  
                            a = strA.indexOf(strB, FindStartPos);  
                        } else {  
                            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);  
                        }  
  
                    }  
  
                    if (a > -1) {  
                        if (up.isSelected()) {  
                            editArea.setCaretPosition(a);  
                            b = findText.getText().length();  
                            editArea.select(a, a + b);  
                        } else if (down.isSelected()) {  
                            editArea.setCaretPosition(a);  
                            b = findText.getText().length();  
                            editArea.select(a, a + b);  
                        }  
                    } else {  
                        if (replaceCount == 0) {  
                            JOptionPane.showMessageDialog(findDialog, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);  
                        } else {  
                            JOptionPane.showMessageDialog(findDialog, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�", JOptionPane.INFORMATION_MESSAGE);  
                        }  
                    }  
                    if (replaceText.getText().length() == 0 && editArea.getSelectedText() != null) {  
                        editArea.replaceSelection("");  
                        replaceCount++;  
                    }  
                    if (replaceText.getText().length() > 0 && editArea.getSelectedText() != null) {  
                        editArea.replaceSelection(replaceText.getText());  
                        replaceCount++;  
                    }  
                }// end while  
            }  
        }); /* "�滻ȫ��"��ť���¼��������� */  
  
        // "������һ��"��ť�¼�����  
        searchNext.addActionListener(new ActionListener() {  
  
            public void actionPerformed(ActionEvent e) {  
                int a = 0, b = 0;  
                int FindStartPos = editArea.getCaretPosition();  
                String str1, str2, str3, str4, strA, strB;  
                str1 = editArea.getText();  
                str2 = str1.toLowerCase();  
                str3 = findText.getText();  
                str4 = str3.toLowerCase();  
                // "���ִ�Сд"��CheckBox��ѡ��  
                if (matchcase.isSelected()) {  
                    strA = str1;  
                    strB = str3;  
                } else {  
                    strA = str2;  
                    strB = str4;  
                }  
  
                if (up.isSelected()) {  
                    if (editArea.getSelectedText() == null) {  
                        a = strA.lastIndexOf(strB, FindStartPos - 1);  
                    } else {  
                        a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);  
                    }  
                } else if (down.isSelected()) {  
                    if (editArea.getSelectedText() == null) {  
                        a = strA.indexOf(strB, FindStartPos);  
                    } else {  
                        a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);  
                    }  
  
                }  
                if (a > -1) {  
                    if (up.isSelected()) {  
                        editArea.setCaretPosition(a);  
                        b = findText.getText().length();  
                        editArea.select(a, a + b);  
                    } else if (down.isSelected()) {  
                        editArea.setCaretPosition(a);  
                        b = findText.getText().length();  
                        editArea.select(a, a + b);  
                    }  
                } else {  
                    JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "����ϵͳ", JOptionPane.INFORMATION_MESSAGE);  
                }  
  
            }  
        });/* "������һ��"��ť�¼��������� */  
        // "ȡ��"��ť���¼�����  
        JButton cancel = new JButton("ȡ��");  
        cancel.setPreferredSize(new Dimension(110, 22));  
        cancel.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                findDialog.dispose();  
            }  
        });  
  
        // ����"�������滻"�Ի���Ľ���  
        JPanel bottomPanel = new JPanel();  
        JPanel centerPanel = new JPanel();  
        JPanel topPanel = new JPanel();  
  
        JPanel direction = new JPanel();  
        direction.setBorder(BorderFactory.createTitledBorder("���� "));  
        direction.add(up);  
        direction.add(down);  
        direction.setPreferredSize(new Dimension(170, 60));  
        JPanel replacePanel = new JPanel();  
        replacePanel.setLayout(new GridLayout(2, 1));  
        replacePanel.add(replace);  
        replacePanel.add(replaceAll);  
  
        topPanel.add(searchContentLabel);  
        topPanel.add(findText);  
        topPanel.add(searchNext);  
        centerPanel.add(replaceContentLabel);  
        centerPanel.add(replaceText);  
        centerPanel.add(replacePanel); 
        bottomPanel.add(matchcase);  
        bottomPanel.add(direction);  
        bottomPanel.add(cancel);  
  
        con.add(topPanel);  
        con.add(centerPanel);  
        con.add(bottomPanel);  
  
        // ����"�������滻"�Ի���Ĵ�С���ɸ��Ĵ�С(��)��λ�úͿɼ���  
        
        findDialog.setSize(480, 220);  
        findDialog.setResizable(false);  
        findDialog.setLocation(660, 450);  
        findDialog.setVisible(true);  
    }   
	 
	 //��ӡ����
	 public void print(){  
        try{  
            p = getToolkit().getPrintJob(this,"ok",null);//����һ��Printfjob ���� p  
            g = p.getGraphics();//p ��ȡһ�����ڴ�ӡ�� Graphics �Ķ���  
            //g.translate(120,200);//�ı��齨��λ��   
            this.editArea.printAll(g);  
            p.end();//�ͷŶ��� g    
        }  
        catch(Exception a){  
         
        }   
	  }  
	
	 //ת������
	 private void turnTo() {  
        final JDialog gotoDialog = new JDialog(this, "ת��������");  
        JLabel gotoLabel = new JLabel("����(L):");  
        final JTextField linenum = new JTextField(5);  
        linenum.setText("1");  
        linenum.selectAll();  
  
        JButton okButton = new JButton("ȷ��");  
        okButton.addActionListener(new ActionListener() {  
  
            public void actionPerformed(ActionEvent e) {  
                int totalLine = editArea.getLineCount();  
                int[] lineNumber = new int[totalLine + 1];  
                String s = editArea.getText();  
                int pos = 0, t = 0;  
  
                while (true) {  
                    pos = s.indexOf('\12', pos);  
                    // System.out.println("����pos:"+pos);  
                    if (pos == -1)  
                        break;  
                    lineNumber[t++] = pos++;  
                }  
  
                int gt = 1;  
                try {  
                    gt = Integer.parseInt(linenum.getText());  
                } catch (NumberFormatException efe) {  
                    JOptionPane.showMessageDialog(null, "����������!", "��ʾ", JOptionPane.WARNING_MESSAGE);  
                    linenum.requestFocus(true);  
                    return;  
                }  
  
                if (gt < 2 || gt >= totalLine) {  
                    if (gt < 2)  
                        editArea.setCaretPosition(0);  
                    else  
                        editArea.setCaretPosition(s.length());  
                } else  
                    editArea.setCaretPosition(lineNumber[gt - 2] + 1);  
  
                gotoDialog.dispose();  
            }  
  
        });  
  
        JButton cancelButton = new JButton("ȡ��");  
        cancelButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                gotoDialog.dispose();  
            }  
        });  
  
        Container con = gotoDialog.getContentPane();  
        con.setLayout(new FlowLayout());  
        con.add(gotoLabel);  
        con.add(linenum);  
        con.add(okButton);  
        con.add(cancelButton);  
  
        gotoDialog.setSize(200, 110);  
        gotoDialog.setResizable(false);  
        gotoDialog.setLocation(300, 280);  
        gotoDialog.setVisible(true);  
    }  
	//�����࣬ʵ�ֽӿ�UndoableEditListener����UndoHandler(�볷�������й�)
	class UndoHandler implements UndoableEditListener    
	{     
	    public void undoableEditHappened(UndoableEditEvent uee)    
	    {     
	        undo.addEdit(uee.getEdit());    
	    }    
	}  
	//�ڲ�����ʾʱ��
	class MyTask extends TimerTask{
		public void run() {
			statusLabel1.setText(new Date().toString());
			System.gc();
		}
	}
}