package skal.tools.DHBot;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.sikuli.basics.HotkeyEvent;
import org.sikuli.basics.HotkeyListener;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;
import org.sikuli.script.Sikulix;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.SwingWorker.StateValue;

import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

	private ScoutDataHolder huntDataHolder;
	private ScoutWorker huntProcess = null;

	
	protected void frameInit() {
	    super.frameInit();
	    huntDataHolder = new ScoutDataHolder();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -3034481041716714910L;
	private JPanel contentPane;
	private JTextField tfMinGlory;
	private JTextField tfMaxGold;
	private JTextField tfAmountOfGold;
	private JTextField tfGlory;
	private JTextField tfTotalGold;
	private JCheckBox chckbxSkipChecks;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Key.removeHotkey("c", KeyModifier.ALT + KeyModifier.CTRL);
			}
			@Override
			public void windowOpened(WindowEvent e) {
				Key.addHotkey("c", KeyModifier.ALT + KeyModifier.CTRL, new HotkeyListener() {		
					@Override
					public void hotkeyPressed(HotkeyEvent e) {
						if (huntProcess == null) return;
						if (huntProcess.isDone()) return;
						huntProcess.cancel(false);						
					}
				});			
			}
		});
		setResizable(false);
		setBackground(new Color(176, 196, 222));
		setForeground(new Color(95, 158, 160));
		setTitle("Decky Rose's Bot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 339);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlRaids = new JPanel();
		pnlRaids.setBackground(new Color(95, 158, 160));
		tabbedPane.addTab("Raids", null, pnlRaids, null);
		pnlRaids.setLayout(new MigLayout("", "[110px,left][110px,grow][70px,center]", "[30px:n:30px][30px:n:30px][10px][30px:n:30px][30px:n:30px][30px:n:30px][30px:n:30px][30px:n:30px]"));
		
		JLabel lblMinGloryTo = new JLabel("Min glory to raid for:");
		lblMinGloryTo.setForeground(new Color(255, 255, 255));
		pnlRaids.add(lblMinGloryTo, "cell 0 0,alignx trailing");
		
		tfMinGlory = new JTextField();
		tfMinGlory.setForeground(new Color(255, 255, 255));
		tfMinGlory.setBackground(new Color(100, 149, 237));
		tfMinGlory.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlRaids.add(tfMinGlory, "cell 1 0,growx");
		tfMinGlory.setColumns(10);
		
		JButton btnSaveMinGlory = new JButton("Save");
		btnSaveMinGlory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblStatus.setText("..."); 
			}
		});
		btnSaveMinGlory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveMinGlory();
			}
		});
		pnlRaids.add(btnSaveMinGlory, "cell 2 0");
		
		JLabel lblMaxGoldTo = new JLabel("Max gold to spend:");
		lblMaxGoldTo.setForeground(new Color(255, 255, 255));
		pnlRaids.add(lblMaxGoldTo, "cell 0 1,alignx trailing");
		
		tfMaxGold = new JTextField();
		tfMaxGold.setForeground(new Color(255, 255, 255));
		tfMaxGold.setBackground(new Color(100, 149, 237));
		tfMaxGold.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlRaids.add(tfMaxGold, "cell 1 1,growx");
		tfMaxGold.setColumns(10);
		
		JButton btnSaveMaxGold = new JButton("Save");
		btnSaveMaxGold.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				lblStatus.setText("..."); 
			}
		});
		btnSaveMaxGold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveMaxGoldGlory();
			}
		});		
		pnlRaids.add(btnSaveMaxGold, "cell 2 1");
		
		JLabel lblSkipRegionsCheck = new JLabel("Skip regions check:");
		lblSkipRegionsCheck.setForeground(Color.WHITE);
		pnlRaids.add(lblSkipRegionsCheck, "cell 0 3,alignx trailing");
		
		chckbxSkipChecks = new JCheckBox("");
		chckbxSkipChecks.setEnabled(false);
		chckbxSkipChecks.setBackground(new Color(95, 158, 160));
		pnlRaids.add(chckbxSkipChecks, "cell 1 3");
		
		JButton btnHunt = new JButton("Hunt");
		pnlRaids.add(btnHunt, "cell 2 3");
		
		JLabel lblAmountOfGold = new JLabel("Amount of gold:");
		lblAmountOfGold.setForeground(new Color(255, 255, 255));
		pnlRaids.add(lblAmountOfGold, "cell 0 4,alignx trailing");
		
		tfAmountOfGold = new JTextField();
		tfAmountOfGold.setForeground(new Color(255, 255, 255));
		tfAmountOfGold.setBackground(new Color(100, 149, 237));
		tfAmountOfGold.setHorizontalAlignment(SwingConstants.TRAILING);
		tfAmountOfGold.setEditable(false);
		pnlRaids.add(tfAmountOfGold, "cell 1 4,growx");
		tfAmountOfGold.setColumns(10);
		
		JLabel lblGlory = new JLabel("Amount of glory:");
		lblGlory.setForeground(new Color(255, 255, 255));
		pnlRaids.add(lblGlory, "cell 0 5,alignx trailing");
		
		tfGlory = new JTextField();
		tfGlory.setForeground(new Color(255, 255, 255));
		tfGlory.setBackground(new Color(100, 149, 237));
		tfGlory.setHorizontalAlignment(SwingConstants.TRAILING);
		tfGlory.setEditable(false);
		tfGlory.setColumns(10);
		pnlRaids.add(tfGlory, "cell 1 5,growx");
		
		JLabel lblTotalGold = new JLabel("Total gold:");
		lblTotalGold.setForeground(new Color(255, 255, 255));
		pnlRaids.add(lblTotalGold, "cell 0 6,alignx trailing");
		
		tfTotalGold = new JTextField();
		tfTotalGold.setForeground(new Color(255, 255, 255));
		tfTotalGold.setBackground(new Color(100, 149, 237));
		tfTotalGold.setHorizontalAlignment(SwingConstants.TRAILING);
		tfTotalGold.setEditable(false);
		tfTotalGold.setColumns(10);
		pnlRaids.add(tfTotalGold, "cell 1 6,growx");
		
		JPanel pnlTrials = new JPanel();
		pnlTrials.setBackground(new Color(95, 158, 160));
		tabbedPane.addTab("Trials", null, pnlTrials, null);
		pnlTrials.setLayout(new MigLayout("", "[110px,left][110px,grow][70px,center]", "[30px]"));
		tabbedPane.remove(pnlTrials);
		
		JPanel pnlStatus = new JPanel();
		pnlStatus.setToolTipText("");
		pnlStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(pnlStatus, BorderLayout.SOUTH);
		pnlStatus.setLayout(new BoxLayout(pnlStatus, BoxLayout.X_AXIS));
		
		lblStatus = new JLabel("...");
		pnlStatus.add(lblStatus);
		
		btnHunt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				huntDataHolder.resetGoldSpend();
				lblStatus.setText("Scouting...");
				huntProcess = new ScoutWorker(huntDataHolder);
				huntProcess.addPropertyChangeListener(new ScoutWorkerPropertyChangeListener());				
				huntProcess.execute();
			}
		});			
		InitMinGloryTextBox();
		InitMaxGoldTextBox();
		initDataBindings();		
	}

	private class ScoutWorkerPropertyChangeListener implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt) {
			if (!"state".equals( evt.getPropertyName()))
				return;
			
			if (((StateValue)evt.getNewValue()) != StateValue.DONE)
				return;
			
			java.awt.Toolkit.getDefaultToolkit().beep();
			
			switch (huntDataHolder.getHuntResultStatus())
			{
			case ERROR_DEFINE_REGIONS:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popError("Could not define raid regions");
				break;
			case ERROR_MAX_GOLD_SPENT:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popup("Max amount of gold per raid was reached ("+ Integer.toString(huntDataHolder.getGoldSpent()) + ")");					
				break;
			case ERROR_READ_GLORY:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popError("Could not read glory");
				break;
			case ERROR_SETUP_EMULATOR_WINDOW:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popError("Could not setup emulator window");
				break;
			case ERROR_UNDEFINED:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popError("An unexpected error occurs");
				break;
			case CANCELLED:
				lblStatus.setText("Scouting aborted.");
				Sikulix.popError("Scouting was cancelled");
				chckbxSkipChecks.setEnabled(true);
				break;				
			case SUCCESS:
				lblStatus.setText("Scouting successful.");

				Sikulix.popup("Hey! I spent "+ Integer.toString(huntDataHolder.getGoldSpent()) + " gold to find a guy with "+ Integer.toString(huntDataHolder.getGlory()) + " glory!");
				
				if (!chckbxSkipChecks.isEnabled())
				{
					chckbxSkipChecks.setEnabled(true);
					huntDataHolder.setSkipRegionChecks(true);
				}
				break;
			}
		}
	}
	
	private void InitMinGloryTextBox() {
		tfMinGlory.setText(Integer.toString(PrefsHelper.loadRaidsMinGlory()));
	
	}

	private void InitMaxGoldTextBox() {
		tfMaxGold.setText(Integer.toString(PrefsHelper.loadRaidsMaxGold()));
	}
	
	private void SaveMinGlory() {
		lblStatus.setText("Saving...");
		String value= tfMinGlory.getText();
		boolean hasError= ! Helpers.isInteger(value);
		int amount = 0;
		if (! hasError) {
			amount=Integer.parseInt(value);
			hasError=amount < 1000 || amount > 500000;
		}
		if (hasError) {
			Sikulix.popError("Please enter a number between 1000 and 500000", "Error with specified glory amount");
			InitMinGloryTextBox();
		}
		else {
			PrefsHelper.storeRaidsMinGlory(amount);
		}
		lblStatus.setText("Saved");
	}
	
	private void SaveMaxGoldGlory() {
		lblStatus.setText("Saving...");
		String value= tfMaxGold.getText();
		boolean hasError= ! Helpers.isInteger(value);
		int amount = 0;
		if (! hasError) {
			amount=Integer.parseInt(value);
			hasError=amount < 1000 || amount > 50000000;
		}
		if (hasError) {
			Sikulix.popError("Please enter a number between 1000 and 50000000", "Raids - Error with specified gold amount");
			InitMaxGoldTextBox();
		}
		else {
			PrefsHelper.storeRaidsMaxGold(amount);
		}
		lblStatus.setText("Saved");
	}
	
	protected void initDataBindings() {
		BeanProperty<ScoutDataHolder, Integer> huntDataHolderBeanProperty = BeanProperty.create("goldSpent");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<ScoutDataHolder, Integer, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, huntDataHolder, huntDataHolderBeanProperty, tfAmountOfGold, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<ScoutDataHolder, String> huntDataHolderBeanProperty_1 = BeanProperty.create("gloryAsString");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<ScoutDataHolder, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, huntDataHolder, huntDataHolderBeanProperty_1, tfGlory, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<ScoutDataHolder, Integer> scoutDataHolderBeanProperty = BeanProperty.create("totalGoldSpent");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<ScoutDataHolder, Integer, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, huntDataHolder, scoutDataHolderBeanProperty, tfTotalGold, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<ScoutDataHolder, Boolean> scoutDataHolderBeanProperty_1 = BeanProperty.create("skipRegionChecks");
		BeanProperty<JCheckBox, Boolean> jCheckBoxBeanProperty_1 = BeanProperty.create("selected");
		AutoBinding<ScoutDataHolder, Boolean, JCheckBox, Boolean> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, huntDataHolder, scoutDataHolderBeanProperty_1, chckbxSkipChecks, jCheckBoxBeanProperty_1);
		autoBinding_4.bind();
	}
}
