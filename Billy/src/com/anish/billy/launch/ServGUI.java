package com.anish.billy.launch;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import com.anish.billy.FIOS.monthlyRate;
import com.anish.billy.FIOS.setDB;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ServGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtHttplocalhost;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServGUI frame = new ServGUI();
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
	public ServGUI() {
		setTitle("Billy");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 378, 136);
		//WebLookAndFeel.install();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		JMenuItem mntmSetDatabase = new JMenuItem("Set Database");
		mntmSetDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choose = new JFileChooser();
				int OK = choose.showOpenDialog(getParent());
				if(OK == JFileChooser.APPROVE_OPTION)
					setDB.set(choose.getSelectedFile());
				
		}});
		mntmSetDatabase.setIcon(new ImageIcon(ServGUI.class.getResource("/com/alee/utils/icons/extensions/16/file_extension_log.png")));
		mnSettings.add(mntmSetDatabase);
		
		JMenuItem mntmSetMonthlyRate = new JMenuItem("Set Monthly Rate");
		mntmSetMonthlyRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthlyRate.setMonthlyRateGUI();
			}
		});
		mntmSetMonthlyRate.setIcon(new ImageIcon(ServGUI.class.getResource("/com/alee/extended/filechooser/icons/ok.png")));
		mnSettings.add(mntmSetMonthlyRate);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtHttplocalhost = new JTextField();
		
		txtHttplocalhost.setBackground(new Color(255, 255, 255));
		txtHttplocalhost.setHorizontalAlignment(SwingConstants.CENTER);
		txtHttplocalhost.setEditable(false);
		txtHttplocalhost.setText("http://localhost:1337");
		GridBagConstraints gbc_txtHttplocalhost = new GridBagConstraints();
		gbc_txtHttplocalhost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHttplocalhost.gridwidth = 11;
		gbc_txtHttplocalhost.insets = new Insets(0, 0, 5, 5);
		gbc_txtHttplocalhost.gridx = 2;
		gbc_txtHttplocalhost.gridy = 2;
		contentPane.add(txtHttplocalhost, gbc_txtHttplocalhost);
		txtHttplocalhost.setColumns(10);
		
		
		final JButton btnStopServer = new JButton("Stop Server");
		btnStopServer.setEnabled(false);
		
		final JButton btnStartServer = new JButton("Start Server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initialize.startServer();
				btnStartServer.setEnabled(false);
				btnStopServer.setEnabled(true);
				txtHttplocalhost.setBackground(new Color(144,238,144));
				//Set TextArea as Clickable
				txtHttplocalhost.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(java.awt.event.MouseEvent evt) {
			        	 try {
							Desktop.getDesktop().browse(new URI("http://localhost:1337"));
						} catch (IOException | URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
			}
		});
		GridBagConstraints gbc_btnStartServer = new GridBagConstraints();
		gbc_btnStartServer.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartServer.gridx = 2;
		gbc_btnStartServer.gridy = 3;
		contentPane.add(btnStartServer, gbc_btnStartServer);
		
		
		btnStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initialize.stopServer();
				btnStopServer.setEnabled(false);
				btnStartServer.setEnabled(true);
				txtHttplocalhost.setBackground(new Color(255,255,255));
				//Remove MouseListener
				for(java.awt.event.MouseListener mL : txtHttplocalhost.getMouseListeners())
				txtHttplocalhost.removeMouseListener(mL);
			}
		});
		GridBagConstraints gbc_btnStopServer = new GridBagConstraints();
		gbc_btnStopServer.insets = new Insets(0, 0, 5, 5);
		gbc_btnStopServer.gridx = 12;
		gbc_btnStopServer.gridy = 3;
		contentPane.add(btnStopServer, gbc_btnStopServer);
	}

}
