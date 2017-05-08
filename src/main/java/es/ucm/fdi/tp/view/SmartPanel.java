package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class SmartPanel extends JPanel {

	private static final long serialVersionUID = 2396384271986958886L;
	
	public interface SmartPanelListener{
		public void changeNumThreads();
		public void changeTime();
		public void stopSearch();
	}
	
	private Logger log;
	private int id;
	private JLabel brain;
	private SpinnerNumberModel numThreads;
	private SpinnerNumberModel time;
	private ButtonUI stop;
	private SmartPanelListener listener;

	public SmartPanel(int id, SmartPanelListener listener){
		this.log = Logger.getLogger("log");
		this.listener = listener;
		this.brain = new JLabel();
		this.numThreads = new SpinnerNumberModel(Runtime.getRuntime().availableProcessors(), 1, 
												Runtime.getRuntime().availableProcessors(), 1);
		this.time = new SpinnerNumberModel(5000, 500, 5000, 500);
		this.stop = new ButtonUI();
		
		
		initGUI();
	}

	private void initGUI() {
		// ThreadsPanel
		brain.setIcon(new ImageIcon("src/main/resources/brain.png"));
		brain.setBackground(Color.BLACK);
		
		JSpinner numThreadsSpin = new JSpinner(numThreads);
		numThreadsSpin.setToolTipText("Number of threads available to the SmartPlayer algorithm.");
		numThreadsSpin.addChangeListener((e) -> listener.changeNumThreads());
		
		JLabel threadsText = new JLabel("threads");
		
		JPanel threadsPanel = new JPanel();
		threadsPanel.add(brain);
		threadsPanel.add(numThreadsSpin);
		threadsPanel.add(threadsText);
		
		// TimePanel
		JLabel clock = new JLabel();
		clock.setIcon(new ImageIcon("src/main/resources/timer.png"));
		
		JSpinner timeSpin = new JSpinner(time);
		timeSpin.setToolTipText("Maximum time to make the smart movement.");
		timeSpin.addChangeListener((e) -> listener.changeTime());
		
		JLabel timeText = new JLabel("ms.");
		
		JPanel timePanel = new JPanel();
		timePanel.add(clock);
		timePanel.add(timeSpin);
		timePanel.add(timeText);
		
		// Stop
		stop = new ButtonUI("stop", "Stop the current calculations to obtain a smart movement", (e) ->{
			log.info("Player " + id + " clicked stop smart movement");
			listener.stopSearch();
		});
		
		// SmartPanel
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(threadsPanel);
		this.add(timePanel);
		this.add(stop);
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2, false),
				"Smart Moves", TitledBorder.CENTER, TitledBorder.TOP));
	}
	
	public int getThreads(){
		return numThreads.getNumber().intValue();
	}
	
	public int getTime(){
		return time.getNumber().intValue();
	}
	
	public void changeBrainColor(){
		if(brain.getBackground().equals(Color.BLACK))
			brain.setBackground(Color.YELLOW);
		else
			brain.setBackground(Color.BLACK);
	}
	
}
