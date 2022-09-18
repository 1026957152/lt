
package com.lt.dom.equip.demo;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.lt.dom.equip.api.Api;
//import org.omg.Messaging.SyncScopeHelper;



public class Demo {
	Api V = new Api();

	public static Demo vguangSample = null;
	private JFrame frame;
	private JTextArea decodeTextArea;
	private JButton buttonLightOn;
	private JButton buttonLightOff;
	private JButton buttonOpendev;
	private JButton buttonQuit;
	private JButton buttonopendecode;
	private JButton buttonenddecode;



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vguangSample = new Demo();
					vguangSample.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Demo() throws UnsupportedEncodingException {
		//初始化控件
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 742, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		//打开设备
		buttonOpendev = new JButton("打开设备");
		buttonOpendev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(V.vbarOpen())
				{
					JOptionPane.showMessageDialog(null, "连接设备成功 ", "连接设备状态", JOptionPane.ERROR_MESSAGE);
					startdecodeThread();

				}
				else
				{
					JOptionPane.showMessageDialog(null, "连接设备失败 ", "连接设备状态", JOptionPane.ERROR_MESSAGE);
				}
				//buttonQuit.setEnabled(false);
			}
		});
		buttonOpendev.setBounds(110, 413, 93, 23);
		frame.getContentPane().add(buttonOpendev);

		buttonopendecode = new JButton("扫码开");
		buttonopendecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				V.controlScan(true);
				//buttonQuit.setEnabled(false);
			}
		});
		buttonopendecode.setBounds(210, 413, 93, 23);
		frame.getContentPane().add(buttonopendecode);

		buttonenddecode = new JButton("扫码关");
		buttonenddecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				V.controlScan(false);
				//buttonQuit.setEnabled(false);
			}
		});
		buttonenddecode.setBounds(310, 413, 93, 23);
		frame.getContentPane().add(buttonenddecode);




		//退出
		buttonQuit = new JButton("退出");
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarClose();
				System.exit(0);
			}
		});
		buttonQuit.setBounds(410, 413, 93, 23);
		frame.getContentPane().add(buttonQuit);

		decodeTextArea = new JTextArea();
		decodeTextArea.setRows(5);
		decodeTextArea.setLineWrap(true);
		decodeTextArea.setColumns(10);
		decodeTextArea.setBounds(113, 78, 400, 280);

		frame.getContentPane().add(decodeTextArea);




		JLabel label = new JLabel("解码输出框：");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("宋体", Font.BOLD, 14));
		label.setBounds(10, 78, 93, 23);
		frame.getContentPane().add(label);



		buttonLightOn = new JButton("开灯");
		buttonLightOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				V.vbarLight(true);
			}
		});
		buttonLightOn.setBounds(200, 380, 66, 23);
		frame.getContentPane().add(buttonLightOn);

		buttonLightOff = new JButton("关灯");
		buttonLightOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				V.vbarLight(false);
			}
		});
		buttonLightOff.setBounds(289, 380, 66, 23);
		frame.getContentPane().add(buttonLightOff);
	}


	class Devices implements Runnable{
		public void run(){


			while(true)
			{
				String decode = null;
				decode = V.getResultStr();
				if(decode != null)
				{
					decodeTextArea.setText(decode);

				}
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		}
	}


	Devices devices = new Devices();
	Thread device;


	public void startdecodeThread(){
		device = new Thread(devices);
		device.start();

	}
}








