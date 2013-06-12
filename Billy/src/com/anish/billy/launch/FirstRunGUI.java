package com.anish.billy.launch;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.LastOwnerException;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.anish.billy.Global;

public class FirstRunGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstRunGUI frame = new FirstRunGUI();
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
	public FirstRunGUI() {
		
		setTitle("Billy - First Run");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(FirstRunGUI.class.getResource("/com/alee/extended/ninepatch/icons/copy.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Screen 1:
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 252, 449, 2);
		contentPane.add(separator);
		setResizable(false);
		
		JLabel lblBillyc = new JLabel("Billy - (c) Anish Basu");
		lblBillyc.setForeground(Color.GRAY);
		lblBillyc.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblBillyc.setBounds(12, 297, 142, 16);
		contentPane.add(lblBillyc);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.removeAll();
				setDbGUI();
			}
		});
		btnNext.setBounds(379, 265, 80, 26);
		contentPane.add(btnNext);
		
		JLabel lblWelcomeToBilly = new JLabel("Welcome to Billy");
		lblWelcomeToBilly.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcomeToBilly.setFont(new Font("Dialog", Font.BOLD, 17));
		lblWelcomeToBilly.setBounds(20, 11, 189, 26);
		contentPane.add(lblWelcomeToBilly);
		
		JLabel lblWeAreHere = new JLabel("<html>We are here to guide  you through your first run!<br>Click Next to Continue.</html>");
		lblWeAreHere.setBounds(20, 48, 439, 37);
		contentPane.add(lblWeAreHere);
		
	}
	//Screen 2:
	JTextField textField;
	public void setDbGUI(){
		setTitle("Billy - First Run: Set up Database");

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 252, 449, 2);
		contentPane.add(separator);
		setResizable(false);
		
		final JLabel lblBillyc = new JLabel("Billy - (c) Anish Basu");
		lblBillyc.setForeground(Color.GRAY);
		lblBillyc.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblBillyc.setBounds(12, 297, 142, 16);
		contentPane.add(lblBillyc);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText()!=""|| textField.getText()!=null){
				Global.setDb(textField.getText(), true);
				contentPane.removeAll();
				setRate();
				}
			}
		});
		btnNext.setBounds(379, 265, 80, 26);
		contentPane.add(btnNext);
		
		JLabel lblWelcomeToBilly = new JLabel("Billy - Set up your Database");
		lblWelcomeToBilly.setHorizontalAlignment(SwingConstants.LEFT);
		lblWelcomeToBilly.setFont(new Font("Dialog", Font.BOLD, 17));
		lblWelcomeToBilly.setBounds(20, 11, 303, 26);
		contentPane.add(lblWelcomeToBilly);
		
		JLabel SetDB = new JLabel("<html>Select the Database if it exists, or create a new one!</html>");
		SetDB.setBounds(20, 48, 439, 26);
		contentPane.add(SetDB);
		
		textField = new JTextField();
		textField.setBounds(20, 86, 347, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//DbStorage:
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choose = new JFileChooser();
				if(choose.showDialog(getParent(),"Open / Save") == JFileChooser.APPROVE_OPTION){
					textField.setText(choose.getSelectedFile().getAbsolutePath());
					
				}
			}
		});
		btnBrowse.setBounds(379, 85, 89, 23);
		contentPane.add(btnBrowse);
		
		contentPane.repaint();
		contentPane.revalidate();
		
	}
	
	//Screen 3:
	void setRate(){
		setTitle("Billy - First Run: Set monthly rate");
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 252, 449, 2);
		contentPane.add(separator);
		setResizable(false);
		
		final JLabel lblBillyc = new JLabel("Billy - (c) Anish Basu");
		lblBillyc.setForeground(Color.GRAY);
		lblBillyc.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblBillyc.setBounds(12, 297, 142, 16);
		contentPane.add(lblBillyc);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText()!=""|| textField.getText()!=null){
				double rate = Double.parseDouble(textField.getText());
				Global.set(rate, true);
				contentPane.removeAll();
				finalScreen();
				}
			}
		});
		btnNext.setBounds(379, 265, 80, 26);
		contentPane.add(btnNext);
		
		JLabel SetRateH = new JLabel("Billy - Set your Monthly Subscription Rate");
		SetRateH.setHorizontalAlignment(SwingConstants.LEFT);
		SetRateH.setFont(new Font("Dialog", Font.BOLD, 17));
		SetRateH.setBounds(20, 11, 357, 26);
		contentPane.add(SetRateH);
		
		JLabel SetMonthlyRate = new JLabel("<html>Set your monthly subscription rate to be charged to customers.</html>");
		SetMonthlyRate.setBounds(20, 48, 439, 26);
		contentPane.add(SetMonthlyRate);
		
		textField = new JTextField();
		textField.setBounds(60, 85, 347, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblRs = new JLabel("Rs.");
		lblRs.setBounds(34, 88, 16, 14);
		contentPane.add(lblRs);
		
		contentPane.repaint();
		contentPane.revalidate();
		
	}
	
	void finalScreen(){
		
		setTitle("Billy - First Run: Finished");
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FirstRunGUI.class.getResource("/com/alee/extended/ninepatch/icons/copy.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 252, 449, 2);
		contentPane.add(separator);
		setResizable(false);
		
		final JLabel lblBillyc = new JLabel("Billy - (c) Anish Basu");
		lblBillyc.setForeground(Color.GRAY);
		lblBillyc.setFont(new Font("Dialog", Font.PLAIN, 9));
		lblBillyc.setBounds(12, 297, 142, 16);
		contentPane.add(lblBillyc);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ServGUI.start();
				}
		});
		btnNext.setBounds(379, 265, 80, 26);
		contentPane.add(btnNext);
		
		JLabel SetRateH = new JLabel("Billy - Finish");
		SetRateH.setHorizontalAlignment(SwingConstants.LEFT);
		SetRateH.setFont(new Font("Dialog", Font.BOLD, 17));
		SetRateH.setBounds(20, 11, 357, 26);
		contentPane.add(SetRateH);
		
		JLabel SetMonthlyRate = new JLabel("<html>That's all we need(for now)! Go ahead and click next to start using Billy!</html>");
		SetMonthlyRate.setBounds(20, 48, 439, 26);
		contentPane.add(SetMonthlyRate);
		
		contentPane.repaint();
		contentPane.revalidate();
	}
	
}
