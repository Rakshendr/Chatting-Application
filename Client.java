package chatting.application;

import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.*;
import java.net.*;

public class Client implements ActionListener{

	static JPanel chats;
	JTextField msg;
	static JFrame f = new JFrame();	
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream opt;
	
	Client(){
		f.setLayout(null);
		
		f.setSize(400, 700);
		f.getContentPane().setBackground(Color.white);
		f.setLocation(1000, 50);
		
		JPanel info = new JPanel();
		info.setBackground(new Color(7, 100, 84));
		info.setBounds(0, 0, 400, 70);
		info.setLayout(null);
		f.add(info);
		
		ImageIcon arrow1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3.png"));
		Image arrowimg = arrow1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon arrow2 =new ImageIcon(arrowimg);
		JLabel arrow = new JLabel(arrow2);
		arrow.setBounds(5, 10, 30, 40);
		info.add(arrow);
		
		arrow.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				f.setVisible(false);
			}
		});
		
		ImageIcon dp1 = new ImageIcon(ClassLoader.getSystemResource("Icons/ClientDP.jpg"));
		Image dpimg = dp1.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
		ImageIcon dp2 = new ImageIcon(dpimg);
		JLabel dp = new JLabel(dp2);
		dp.setBounds(35, 10, 50, 40);
		info.add(dp);
		
		JLabel name = new JLabel("Rakshendra");
		name.setBounds(90, 2, 100, 50);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		info.add(name);
		
		JLabel status = new JLabel("Online");
		status.setBounds(90, 20, 50, 50);
		status.setForeground(Color.white);
		status.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		info.add(status);
		
		ImageIcon vdcl1 = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
		Image vdcl2 = vdcl1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon vdcl3 = new ImageIcon(vdcl2);
		JLabel videoCall = new JLabel(vdcl3);
		videoCall.setBounds(280, 10, 30, 40);
		info.add(videoCall);
		
		ImageIcon phncl1 = new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
		Image phncl2 = phncl1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon phncl3 = new ImageIcon(phncl2);
		JLabel phoneCall = new JLabel(phncl3);
		phoneCall.setBounds(320, 10, 30, 40);
		info.add(phoneCall);
		
		ImageIcon menu1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3icon.png"));
		Image menu2 = menu1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon menu3 = new ImageIcon(menu2);
		JLabel menu = new JLabel(menu3);
		menu.setBounds(365, 10, 5, 40);
		info.add(menu);
		
		chats = new JPanel();
		chats.setBounds(0, 70, 400, 700);
		chats.setLayout(null);
		chats.setBackground(Color.DARK_GRAY);
		f.add(chats);
		
		msg = new JTextField();
		msg.setBounds(5, 585, 310, 40);
		msg.setForeground(Color.white);
		msg.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		msg.setBackground(Color.black);
		chats.add(msg);
		
		JButton send = new JButton("Send");
		send.setBounds(320, 585, 75, 40);
		send.setBackground(new Color(7, 100, 84));
		send.setForeground(Color.white);
		send.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		send.addActionListener(this);
		chats.add(send);
		
		
		f.setUndecorated(true);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try {			
			String message = msg.getText();
			JPanel messages = formate(message);
			
			chats.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(messages, BorderLayout.LINE_END);
			
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(2));
			
			chats.add(vertical, BorderLayout.PAGE_START);
			msg.setText("");
			
			opt.writeUTF(message);
			
			f.repaint();
			f.invalidate();
			f.validate();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
	
	public static JPanel formate(String message) {
		JPanel mesgfrmt = new JPanel();
		mesgfrmt.setLayout(new BoxLayout(mesgfrmt, BoxLayout.Y_AXIS));
		
		JLabel showMsg = new JLabel("<html><p style=\"width: 220px\">" + message +"</p> </html>");
		showMsg.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
		showMsg.setBorder(new EmptyBorder(15, 5, 5, 50));
		showMsg.setBackground(new Color(7, 100, 84));
		showMsg.setForeground(Color.white);
		showMsg.setOpaque(true);
		
		mesgfrmt.add(showMsg);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat time_date = new SimpleDateFormat("HH:mm");
		
		JLabel time = new JLabel();
		time.setText(time_date.format(cal.getTime()));
		mesgfrmt.add(time);
		
		
		
		
		return mesgfrmt;
	}
	
	public static void main(String[] args) {
		new Client();
		
		try {
			Socket s = new Socket("127.0.0.1", 6001);
			
			DataInputStream inpt = new DataInputStream(s.getInputStream());
			opt = new DataOutputStream(s.getOutputStream());
			
			while(true) {
				chats.setLayout(new BorderLayout());
				String message = inpt.readUTF();
				JPanel lpanel = formate(message);
				JPanel left = new JPanel(new BorderLayout());
				left.add(lpanel, BorderLayout.LINE_START);
				vertical.add(left);
				
				vertical.add(Box.createVerticalStrut(2));
				chats.add(vertical, BorderLayout.PAGE_START);
				
				f.validate();
				
				
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}

















