package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Register_Form extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_Form frame = new Register_Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register_Form() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 364);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		
		JLabel label = new JLabel("Create New User");
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 30));
		
		JLabel label_1 = new JLabel("User Name:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		usernameField = new JTextField();
		usernameField.setColumns(10);
		
		JLabel label_2 = new JLabel("Password:");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_3 = new JLabel("Confirm Password:");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		passwordField = new JPasswordField();
		
		passwordField_1 = new JPasswordField();
		
		JButton button = new JButton("Create");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registerUser();
				
			}

			private void registerUser() {
				// TODO Auto-generated method stub
				String user = usernameField.getText();
				String pass = new String(passwordField.getPassword());
				String pass_confirm = new String(passwordField_1.getPassword());
				if(!pass.equals(pass_confirm)){
					JOptionPane.showMessageDialog(null, "Password and Confirmed Password mismatch!");
				}
				else {
					Socket sk;
					try {
						sk = new Socket("localhost", 12345);
						PrintWriter pr = new PrintWriter(sk.getOutputStream(), true);
			            BufferedReader in = new BufferedReader(new InputStreamReader(sk.getInputStream(), "utf-8"));
						String info = "register:" + user + "," + pass;
						pr.println(info);
						String msg = in.readLine();
						JOptionPane.showMessageDialog(null, msg);
						if(msg.equals("Successfully Registered!")) {
							Chat_Interface chat = new Chat_Interface(sk, in, pr, user);
							chat.setVisible(true);
							dispose();
						}
					} catch (UnknownHostException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		JButton button_1 = new JButton("Clear");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usernameField.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(47)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label_3, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addGap(88))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(passwordField_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
								.addComponent(passwordField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
								.addComponent(usernameField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
							.addGap(37))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(124)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addGap(115))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(44)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(button)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}