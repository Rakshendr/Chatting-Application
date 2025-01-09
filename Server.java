package chatting.application;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.text.*;
import java.net.*;

public class Server implements ActionListener {
	static JTextField msg;
	static JPanel chats;
	
	static JFrame f = new JFrame();
	
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream opt;
	
	
	Server(){
		f.setLayout(null);
		
		f.setSize(400, 700);
		f.getContentPane().setBackground(Color.darkGray);
		f.setLocation(200, 50);
		
		JPanel p1=new JPanel();
		p1.setBackground(new Color(7, 100, 84));
		p1.setBounds(0, 0, 400, 70);
		p1.setLayout(null);
		f.add(p1);
		
		
//		ImageIcon i=new ImageIcon(ClassLoader.getSystemResource("icons/MainBackground.jpg"));
//		JLabel back=new JLabel(i);
//		back.setBounds(0,70,400, 700);
//		add(back);
		
		
///////////////////////////////////Arrow/////////////////////////////////////////////
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3=new ImageIcon(i2);
		JLabel icn=new JLabel(i3);
		icn.setBounds(5, 10, 30, 40);
		p1.add(icn);
		
		icn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.setVisible(false);
			}
		});
		
		
		ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("Icons/SpiderMan.jpg"));
		Image i5 = i4.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel prflpc = new JLabel(i6);
		prflpc.setBounds(35, 10, 50, 40);
		p1.add(prflpc);
		
		JLabel name=new JLabel("Ritik Rane");
		name.setBounds(90, 2, 100, 50);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		p1.add(name);
		
		JLabel status = new JLabel("Online");
		status.setBounds(90, 20, 50, 50);
		status.setForeground(Color.white);
		status.setFont(new Font("SAN_SARIF", Font.PLAIN, 12));
		p1.add(status);
		
		
		ImageIcon vdcall1 = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
		Image vdcall2 = vdcall1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon vdcall3 = new ImageIcon(vdcall2);
		JLabel vdcall = new JLabel(vdcall3);
		vdcall.setBounds(280, 10, 30, 40);
		p1.add(vdcall);
		
		ImageIcon phncl1 = new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
		Image phncl2 = phncl1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon phncl3 = new ImageIcon(phncl2);
		JLabel phncl = new JLabel(phncl3);
		phncl.setBounds(320, 10, 30, 40);
		p1.add(phncl);
		
		ImageIcon menu1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3icon.png"));
		Image menu2 = menu1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon menu3 = new ImageIcon(menu2);
		JLabel menu = new JLabel(menu3);
		menu.setBounds(365, 10, 5, 40);
		p1.add(menu);
		
		
		chats = new JPanel();
		chats.setBounds(0, 70, 400, 700);
		chats.setLayout(null);
		chats.setBackground(Color.darkGray);
		f.add(chats);
		
		msg = new JTextField();
//		msg.setLayout(null);
		msg.setBounds(5, 585, 310, 40);
		msg.setBackground(Color.black);
		msg.setForeground(Color.white);
		msg.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		chats.add(msg);
		
		JButton sen = new JButton("Send");
		sen.setBounds(320, 585, 75, 40);
		sen.setBackground(new Color(7, 100, 84));
		sen.setForeground(Color.white);
		sen.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		sen.addActionListener(this);
		chats.add(sen);
		
		
		f.setUndecorated(true);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			
			String message = msg.getText();
//		JLabel messages1 = new JLabel(message);
			JPanel messages = formatLabel(message);
//		messages.add(messages1);
			
			
			chats.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(messages, BorderLayout.LINE_END);
			
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(2));
			
			chats.add(vertical, BorderLayout.PAGE_START);
			
			opt.writeUTF(message);
			
			msg.setText("");
			
			f.repaint();
			f.invalidate();
			f.validate();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static JPanel formatLabel(String message) {
		JPanel msgfrmt = new JPanel();
		msgfrmt.setLayout(new BoxLayout(msgfrmt, BoxLayout.Y_AXIS));
		
		JLabel showmsg = new JLabel("<html><p style=\"width: 220px\">"+message+"</p></html>");
		showmsg.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		showmsg.setBorder(new EmptyBorder(15, 5, 5, 50));
		showmsg.setBackground(new Color(7, 100, 84));
		showmsg.setForeground(Color.white);
		showmsg.setOpaque(true);
		
		msgfrmt.add(showmsg);		
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat time_date = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(time_date.format(cal.getTime()));
		msgfrmt.add(time);
		
		
		return msgfrmt;
	}
	
	public static void main(String[] args) {
		new Server();

		try {
			ServerSocket skt = new ServerSocket(6001);
			while(true) {
				Socket s = skt.accept();
				DataInputStream inpt = new DataInputStream(s.getInputStream());
				opt = new DataOutputStream(s.getOutputStream());
				
				while(true) {
					String message = inpt.readUTF();
					JPanel lpanel = formatLabel(message);
					JPanel left = new JPanel(new BorderLayout());
					left.add(lpanel, BorderLayout.LINE_START);
					vertical.add(left);
					f.validate();
					
					
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}













