package ParserClass;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;

public class MainApplication implements ActionListener {

	JTextArea txtrWaitingForPort;
	private JFrame frmUoeSerialParser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication window = new MainApplication();
					window.frmUoeSerialParser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUoeSerialParser = new JFrame();
		frmUoeSerialParser.setResizable(false);
		frmUoeSerialParser.setTitle("UOE Serial Parser");
		frmUoeSerialParser.setBounds(100, 100, 456, 368);
		frmUoeSerialParser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUoeSerialParser.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 24, 450, 304);
		frmUoeSerialParser.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Reader", null, panel, null);
		panel.setLayout(null);

		txtrWaitingForPort = new JTextArea();
		txtrWaitingForPort.setText("Waiting for port input.");
		txtrWaitingForPort.setBounds(0, 0, 445, 194);
		panel.add(txtrWaitingForPort);

		JButton btnStartListening = new JButton("Start Listening");
		btnStartListening.setBounds(0, 195, 226, 33);
		panel.add(btnStartListening);
		btnStartListening.addActionListener(this);

		JButton btnStopListening = new JButton("Stop Listening");
		btnStopListening.setBounds(225, 195, 220, 33);
		panel.add(btnStopListening);
		btnStopListening.addActionListener(this);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Graphing", null, panel_1, null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 450, 21);
		frmUoeSerialParser.getContentPane().add(menuBar);

		JMenu mnPorts = new JMenu("File");
		menuBar.add(mnPorts);

		JMenu mnPort = new JMenu("Port");
		mnPorts.add(mnPort);

		JMenuItem mntmCom = new JMenuItem("COM3");
		mnPort.add(mntmCom);

		JMenuItem mntmCom_1 = new JMenuItem("COM4");
		mnPort.add(mntmCom_1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		System.out.print("yo");
		// if(command.equals("Start Listening")) {
		SerialPort comPort = SerialPort.getCommPorts()[0];
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		InputStream in = comPort.getInputStream();
		try {
			for (int j = 0; j < 1000; ++j)
				System.out.print((char) in.read());
				txtrWaitingForPort.setText(txtrWaitingForPort.getText() + "\n");
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		comPort.closePort();
	}
	// }
}
