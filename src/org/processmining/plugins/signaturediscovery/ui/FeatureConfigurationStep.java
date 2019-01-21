package org.processmining.plugins.signaturediscovery.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.processmining.plugins.signaturediscovery.settings.SignatureDiscoverySettingsListener;
import org.processmining.plugins.signaturediscovery.swingx.ScrollableGridLayout;
import org.processmining.plugins.signaturediscovery.types.FeatureType;

import com.fluxicon.slickerbox.factory.SlickerFactory;

/**
 * @author R.P. Jagadeesh Chandra 'JC' Bose
 * @date 14 July 2010 
 * @since 01 July 2010
 * @version 1.0
 * @email j.c.b.rantham.prabhakara@tue.nl
 * @copyright R.P. Jagadeesh Chandra 'JC' Bose
 * 			  Architecture of Information Systems Group (AIS) 
 * 			  Department of Mathematics and Computer Science
 * 			  University of Technology, Eindhoven, The Netherlands
 */

@SuppressWarnings("serial")
public class FeatureConfigurationStep extends myStep {
	
	JPanel featureTypePanel;
	
	JRadioButton chooseBestFeatureTypeRadioButton;
	JRadioButton sequenceFeatureTypeRadioButton;
	JRadioButton alphabetFeatureTypeRadioButton;
	JCheckBox chooseBaseFeatureCheckBox;
	
	JPanel featureCountTypePanel;
	JPanel sequenceFeaturePanel;
	JPanel alphabetFeaturePanel;
	
	JCheckBox individualEventSequenceCheckBox;
	JCheckBox kGramCheckBox;
	JCheckBox tandemRepeatSequenceCheckBox;
	JCheckBox maximalRepeatSequenceCheckBox;
	JCheckBox superMaximalRepeatSequenceCheckBox;
	JCheckBox nearSuperMaximalRepeatSequenceCheckBox;
	
	JCheckBox individualEventAlphabetCheckBox;
	JCheckBox tandemRepeatAlphabetCheckBox;
	JCheckBox maximalRepeatAlphabetCheckBox;
	JCheckBox superMaximalRepeatAlphabetCheckBox;
	JCheckBox nearSuperMaximalRepeatAlphabetCheckBox;
	boolean alphabetFeaturePanelAdded, sequenceFeaturePanelAdded;
	boolean isNominalFeatureCount = false;
	JRadioButton nominalFeatureRadioButton;
	
	/*
	 * Bruce 04.06.2014: frequency of one feature in the entire event log
	 * This is to limit the number of features created if there are too many (hundreds)
	 */
//	JTextField supportCountTextField; 
	
	int noSelectedFeatures;
	int kGramValue;
	
	SignatureDiscoverySettingsListener listener;
	
	public FeatureConfigurationStep(){
		initComponents();
	}
	
	private void initComponents(){
		final ScrollableGridLayout featureConfigurationLayout = new ScrollableGridLayout(this, 1, 9, 0, 0);
		
		featureConfigurationLayout.setRowFixed(0, true);
		featureConfigurationLayout.setRowFixed(1, true);
//		featureConfigurationLayout.setRowFixed(2, true);
//		featureConfigurationLayout.setRowFixed(3, true);
		featureConfigurationLayout.setRowFixed(2, true);
		featureConfigurationLayout.setRowFixed(5, true);
		
		this.setLayout(featureConfigurationLayout);
		
		JLabel headerLabel = SlickerFactory.instance().createLabel("<html><h1>Feature Configuration Step</h1>");
		featureConfigurationLayout.setPosition(headerLabel, 0, 0);
		add(headerLabel);
		
		buildFeatureTypePanel();
		featureConfigurationLayout.setPosition(featureTypePanel, 0, 1);
		add(featureTypePanel);
		
		Component verticalStrut1 = Box.createVerticalStrut(10);
		featureConfigurationLayout.setPosition(verticalStrut1, 0, 2);
		add(verticalStrut1);
		
		buildSequenceFeaturePanel();
		buildAlphabetFeaturePanel();
		
		featureConfigurationLayout.setPosition(sequenceFeaturePanel, 0, 3);
		add(sequenceFeaturePanel);
		
		featureConfigurationLayout.setPosition(alphabetFeaturePanel, 0, 4);
		add(alphabetFeaturePanel);
		
		Component verticalStrut2 = Box.createVerticalStrut(10);
		featureConfigurationLayout.setPosition(verticalStrut2, 0, 5);
		add(verticalStrut2);
		
		buildFeatureCountTypePanel();
		featureConfigurationLayout.setPosition(featureCountTypePanel, 0, 6);
		add(featureCountTypePanel);
		
		Component verticalStrut3 = Box.createVerticalStrut(10);
		featureConfigurationLayout.setPosition(verticalStrut3, 0, 7);
		add(verticalStrut3);
		
		chooseBaseFeatureCheckBox = SlickerFactory.instance().createCheckBox("Consider Base Features", true);
		chooseBaseFeatureCheckBox.setVisible(false);
		featureConfigurationLayout.setPosition(chooseBaseFeatureCheckBox, 0, 8);
		add(chooseBaseFeatureCheckBox);
		
		
	}
	
	private void buildFeatureTypePanel(){
		featureTypePanel = SlickerFactory.instance().createRoundedPanel();
		featureTypePanel.setBorder(BorderFactory.createTitledBorder("Select Feature Type"));
		ScrollableGridLayout featureTypePanelLayout = new ScrollableGridLayout(featureTypePanel,1,3,0,0); //Bruce 3->5
		featureTypePanelLayout.setRowFixed(0, true);
		featureTypePanelLayout.setRowFixed(1, true);
		featureTypePanelLayout.setRowFixed(2, true);
		
		featureTypePanel.setLayout(featureTypePanelLayout);
		
		chooseBestFeatureTypeRadioButton = SlickerFactory.instance().createRadioButton("Choose Best Feature Automatically");
		sequenceFeatureTypeRadioButton = SlickerFactory.instance().createRadioButton("Sequence Feature");
		alphabetFeatureTypeRadioButton = SlickerFactory.instance().createRadioButton("Alphabet Feature");
		
		ButtonGroup featureTypeButtonGroup = new ButtonGroup();
		featureTypeButtonGroup.add(chooseBestFeatureTypeRadioButton);
		featureTypeButtonGroup.add(sequenceFeatureTypeRadioButton);
		featureTypeButtonGroup.add(alphabetFeatureTypeRadioButton);
		
		chooseBestFeatureTypeRadioButton.setSelected(true);
		
		featureTypePanelLayout.setPosition(chooseBestFeatureTypeRadioButton, 0, 0);
		featureTypePanel.add(chooseBestFeatureTypeRadioButton);
		
		featureTypePanelLayout.setPosition(sequenceFeatureTypeRadioButton, 0, 1);
		featureTypePanel.add(sequenceFeatureTypeRadioButton);
		
		featureTypePanelLayout.setPosition(alphabetFeatureTypeRadioButton, 0, 2);
		featureTypePanel.add(alphabetFeatureTypeRadioButton);
		
		/*
		 * Bruce added supportCount field
		 */
//		final JLabel supportCountLabel = SlickerFactory.instance().createLabel("<HTML>Feature Support Count </HTML>");
//		supportCountTextField = new JTextField("0.1  ");
//		featureTypePanelLayout.setPosition(supportCountLabel, 0, 4);
//		featureTypePanel.add(supportCountLabel);
//		featureTypePanelLayout.setPosition(supportCountTextField, 0, 5);
//		featureTypePanel.add(supportCountTextField);
		
		
		chooseBestFeatureTypeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chooseBestFeatureTypeRadioButton.isSelected()){
					sequenceFeaturePanel.setVisible(false);
					alphabetFeaturePanel.setVisible(false);
					featureCountTypePanel.setVisible(false);
					chooseBaseFeatureCheckBox.setVisible(false);
					revalidate();
					repaint();
				}
			}
		});
		
		sequenceFeatureTypeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sequenceFeatureTypeRadioButton.isSelected()){
					alphabetFeaturePanel.setVisible(false);
					sequenceFeaturePanel.setVisible(true);
					featureCountTypePanel.setVisible(true);
					chooseBaseFeatureCheckBox.setVisible(true);
					revalidate();
					repaint();
				}
			}
		});
		
		alphabetFeatureTypeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(alphabetFeatureTypeRadioButton.isSelected()){
					sequenceFeaturePanel.setVisible(false);
					alphabetFeaturePanel.setVisible(true);
					featureCountTypePanel.setVisible(true);
					chooseBaseFeatureCheckBox.setVisible(true);
					revalidate();
					repaint();
				}
				
			}
		});
		
	}

	private void buildSequenceFeaturePanel(){
		sequenceFeaturePanel = SlickerFactory.instance().createRoundedPanel();
		sequenceFeaturePanel.setBorder(BorderFactory.createTitledBorder("Select Sequence Features"));
		
		ScrollableGridLayout sequenceFeaturePanelLayout = new ScrollableGridLayout(sequenceFeaturePanel, 2, 6, 0, 0);
		sequenceFeaturePanelLayout.setColumnFixed(0, true);
		
		sequenceFeaturePanelLayout.setRowFixed(0, true);
		sequenceFeaturePanelLayout.setRowFixed(1, true);
		sequenceFeaturePanelLayout.setRowFixed(2, true);
		sequenceFeaturePanelLayout.setRowFixed(3, true);
		sequenceFeaturePanelLayout.setRowFixed(4, true);
		sequenceFeaturePanelLayout.setRowFixed(5, true);
		sequenceFeaturePanel.setLayout(sequenceFeaturePanelLayout);
		
		
		final JPanel kGramPanel = SlickerFactory.instance().createRoundedPanel();
		final JSlider kGramValueSlider = SlickerFactory.instance().createSlider(JSlider.HORIZONTAL);
		final JLabel kGramValueLabel = SlickerFactory.instance().createLabel("<HTML>  3  </HTML>");
		kGramValueSlider.setMinimum(2);
		kGramValueSlider.setMaximum(6);
		kGramValueSlider.setValue(3);
		kGramValue = 3;
		kGramPanel.add(kGramValueSlider);
		kGramPanel.add(kGramValueLabel);
		kGramPanel.setVisible(false);
		
		
		individualEventSequenceCheckBox = SlickerFactory.instance().createCheckBox("Individual Event", false); 
		kGramCheckBox = SlickerFactory.instance().createCheckBox("K-Gram", false);
		tandemRepeatSequenceCheckBox = SlickerFactory.instance().createCheckBox("Tandem Repeat", false);
		maximalRepeatSequenceCheckBox = SlickerFactory.instance().createCheckBox("Maximal Repeat", false);
		superMaximalRepeatSequenceCheckBox = SlickerFactory.instance().createCheckBox("Super Maximal Repeat", false);
		nearSuperMaximalRepeatSequenceCheckBox = SlickerFactory.instance().createCheckBox("Near Super Maximal Repeat", false);
		
		tandemRepeatSequenceCheckBox.setSelected(true);
		maximalRepeatSequenceCheckBox.setSelected(true);
		superMaximalRepeatSequenceCheckBox.setEnabled(false);
		nearSuperMaximalRepeatSequenceCheckBox.setEnabled(false);
		
		final Color enabledForeGroundColor = maximalRepeatSequenceCheckBox.getForeground();
		final Color disabledForeGroundColor = Color.gray; 
		superMaximalRepeatSequenceCheckBox.setForeground(disabledForeGroundColor);
		nearSuperMaximalRepeatSequenceCheckBox.setForeground(disabledForeGroundColor);
		
		sequenceFeaturePanelLayout.setPosition(individualEventSequenceCheckBox, 0, 0);
		sequenceFeaturePanel.add(individualEventSequenceCheckBox);
		
		sequenceFeaturePanelLayout.setPosition(kGramCheckBox, 0, 1);
		sequenceFeaturePanel.add(kGramCheckBox);
		
		sequenceFeaturePanelLayout.setPosition(kGramPanel, 1, 1);
		sequenceFeaturePanel.add(kGramPanel);
		
		sequenceFeaturePanelLayout.setPosition(tandemRepeatSequenceCheckBox, 0, 2);
		sequenceFeaturePanel.add(tandemRepeatSequenceCheckBox);
		
		sequenceFeaturePanelLayout.setPosition(maximalRepeatSequenceCheckBox, 0, 3);
		sequenceFeaturePanel.add(maximalRepeatSequenceCheckBox);
		
		sequenceFeaturePanelLayout.setPosition(superMaximalRepeatSequenceCheckBox, 0, 4);
		sequenceFeaturePanel.add(superMaximalRepeatSequenceCheckBox);
		
		sequenceFeaturePanelLayout.setPosition(nearSuperMaximalRepeatSequenceCheckBox, 0, 5);
		sequenceFeaturePanel.add(nearSuperMaximalRepeatSequenceCheckBox);
		
		kGramCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(kGramCheckBox.isSelected()){
					kGramPanel.setVisible(true);
				}else{
					kGramPanel.setVisible(false);
				}
				revalidate();
				repaint();
			}
		});
		
		kGramValueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!kGramValueSlider.getValueIsAdjusting()){
					kGramValue = kGramValueSlider.getValue();
					kGramValueLabel.setText("<HTML>  "+kGramValue+"  </HTML>");
				}
			}
		});
		
		maximalRepeatSequenceCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(maximalRepeatSequenceCheckBox.isSelected()){
					superMaximalRepeatSequenceCheckBox.setSelected(false);
					nearSuperMaximalRepeatSequenceCheckBox.setSelected(false);
					superMaximalRepeatSequenceCheckBox.setEnabled(false);
					nearSuperMaximalRepeatSequenceCheckBox.setEnabled(false);
					superMaximalRepeatSequenceCheckBox.setForeground(disabledForeGroundColor);
					nearSuperMaximalRepeatSequenceCheckBox.setForeground(disabledForeGroundColor);
				}else{
					superMaximalRepeatSequenceCheckBox.setEnabled(true);
					nearSuperMaximalRepeatSequenceCheckBox.setEnabled(true);
					superMaximalRepeatSequenceCheckBox.setForeground(enabledForeGroundColor);
					nearSuperMaximalRepeatSequenceCheckBox.setForeground(enabledForeGroundColor);
				}
			}
		});
		
		nearSuperMaximalRepeatSequenceCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nearSuperMaximalRepeatSequenceCheckBox.isSelected()){
					superMaximalRepeatSequenceCheckBox.setSelected(false);
					superMaximalRepeatSequenceCheckBox.setEnabled(false);
					superMaximalRepeatSequenceCheckBox.setForeground(disabledForeGroundColor);
				}else{
					superMaximalRepeatSequenceCheckBox.setEnabled(true);
					superMaximalRepeatSequenceCheckBox.setForeground(enabledForeGroundColor);
				}
			}
		});
		
		sequenceFeaturePanel.setVisible(false);
	}
	
	private void buildAlphabetFeaturePanel(){
		alphabetFeaturePanel = SlickerFactory.instance().createRoundedPanel();
		alphabetFeaturePanel.setBorder(BorderFactory.createTitledBorder("Select Alphabet Features"));
		
		ScrollableGridLayout alphabetFeaturePanelLayout = new ScrollableGridLayout(alphabetFeaturePanel, 1, 5, 0, 0);
		alphabetFeaturePanelLayout.setColumnFixed(0, true);
		
		alphabetFeaturePanelLayout.setRowFixed(0, true);
		alphabetFeaturePanelLayout.setRowFixed(1, true);
		alphabetFeaturePanelLayout.setRowFixed(2, true);
		alphabetFeaturePanelLayout.setRowFixed(3, true);
		alphabetFeaturePanelLayout.setRowFixed(4, true);
		
		alphabetFeaturePanel.setLayout(alphabetFeaturePanelLayout);
		
		individualEventAlphabetCheckBox = SlickerFactory.instance().createCheckBox("Individual Event", false); 
		tandemRepeatAlphabetCheckBox = SlickerFactory.instance().createCheckBox("Tandem Repeat Alphabet", false);
		maximalRepeatAlphabetCheckBox = SlickerFactory.instance().createCheckBox("Maximal Repeat Alphabet", false);
		superMaximalRepeatAlphabetCheckBox = SlickerFactory.instance().createCheckBox("Super Maximal Repeat Alphabet", false);
		nearSuperMaximalRepeatAlphabetCheckBox = SlickerFactory.instance().createCheckBox("Near Super Maximal Repeat Alphabet", false);
		
		tandemRepeatAlphabetCheckBox.setSelected(true);
		maximalRepeatAlphabetCheckBox.setSelected(true);
		
		final Color enabledForeGroundColor = maximalRepeatAlphabetCheckBox.getForeground();
		final Color disabledForeGroundColor = Color.gray; 
		superMaximalRepeatAlphabetCheckBox.setForeground(disabledForeGroundColor);
		nearSuperMaximalRepeatAlphabetCheckBox.setForeground(disabledForeGroundColor);
		
		alphabetFeaturePanelLayout.setPosition(individualEventAlphabetCheckBox, 0, 0);
		alphabetFeaturePanel.add(individualEventAlphabetCheckBox);
		
		alphabetFeaturePanelLayout.setPosition(tandemRepeatAlphabetCheckBox, 0, 1);
		alphabetFeaturePanel.add(tandemRepeatAlphabetCheckBox);
		
		alphabetFeaturePanelLayout.setPosition(maximalRepeatAlphabetCheckBox, 0, 2);
		alphabetFeaturePanel.add(maximalRepeatAlphabetCheckBox);
		
		alphabetFeaturePanelLayout.setPosition(superMaximalRepeatAlphabetCheckBox, 0, 3);
		alphabetFeaturePanel.add(superMaximalRepeatAlphabetCheckBox);
		
		alphabetFeaturePanelLayout.setPosition(nearSuperMaximalRepeatAlphabetCheckBox, 0, 4);
		alphabetFeaturePanel.add(nearSuperMaximalRepeatAlphabetCheckBox);
		
		maximalRepeatAlphabetCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(maximalRepeatAlphabetCheckBox.isSelected()){
					superMaximalRepeatAlphabetCheckBox.setSelected(false);
					nearSuperMaximalRepeatAlphabetCheckBox.setSelected(false);

					superMaximalRepeatAlphabetCheckBox.setEnabled(false);
					nearSuperMaximalRepeatAlphabetCheckBox.setEnabled(false);
					superMaximalRepeatAlphabetCheckBox.setForeground(disabledForeGroundColor);
					nearSuperMaximalRepeatAlphabetCheckBox.setForeground(disabledForeGroundColor);
				}else{
					superMaximalRepeatAlphabetCheckBox.setEnabled(true);
					nearSuperMaximalRepeatAlphabetCheckBox.setEnabled(true);
					superMaximalRepeatAlphabetCheckBox.setForeground(enabledForeGroundColor);
					nearSuperMaximalRepeatAlphabetCheckBox.setForeground(enabledForeGroundColor);
				}
			}
		});
		
		nearSuperMaximalRepeatAlphabetCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nearSuperMaximalRepeatAlphabetCheckBox.isSelected()){
					superMaximalRepeatAlphabetCheckBox.setSelected(false);
					
					superMaximalRepeatAlphabetCheckBox.setEnabled(false);
					superMaximalRepeatAlphabetCheckBox.setForeground(disabledForeGroundColor);
				}else{
					superMaximalRepeatAlphabetCheckBox.setEnabled(true);
					superMaximalRepeatAlphabetCheckBox.setForeground(enabledForeGroundColor);
				}
			}
		});
		
		alphabetFeaturePanel.setVisible(false);
	}
	
	private void buildFeatureCountTypePanel(){
		featureCountTypePanel = SlickerFactory.instance().createRoundedPanel();
		featureCountTypePanel.setBorder(BorderFactory.createTitledBorder("Select Feature Count Type"));
	
		ScrollableGridLayout featureCountTypePanelLayout = new ScrollableGridLayout(featureCountTypePanel, 3, 1, 0, 0);

		featureCountTypePanelLayout.setColumnFixed(0, true);
		featureCountTypePanelLayout.setRowFixed(0, true);

		featureCountTypePanel.setLayout(featureCountTypePanelLayout);
		
		nominalFeatureRadioButton = SlickerFactory.instance().createRadioButton("Nominal");
		final JRadioButton numericFeatureRadioButton = SlickerFactory.instance().createRadioButton("Numeric");
		
		
		ButtonGroup featureCountTypeButtonGroup = new ButtonGroup();
		featureCountTypeButtonGroup.add(nominalFeatureRadioButton);
		featureCountTypeButtonGroup.add(numericFeatureRadioButton);
		nominalFeatureRadioButton.setSelected(true);
		
		featureCountTypePanelLayout.setPosition(nominalFeatureRadioButton, 0, 0);
		featureCountTypePanel.add(nominalFeatureRadioButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		featureCountTypePanelLayout.setPosition(horizontalStrut, 1, 0);
		featureCountTypePanel.add(horizontalStrut);
		
		featureCountTypePanelLayout.setPosition(numericFeatureRadioButton, 2, 0);
		featureCountTypePanel.add(numericFeatureRadioButton);

		nominalFeatureRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nominalFeatureRadioButton.isSelected()){
					listener.setNominalFeatureCount(true);
				}
			}
		});
		
		numericFeatureRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(numericFeatureRadioButton.isSelected())
					listener.setNominalFeatureCount(false);
			}
		});
		
		featureCountTypePanel.setVisible(false);
	}
	
	public boolean precondition() {
		return true;
	}

	public void readSettings() {
		readSelectedFeatures();
		listener.setFrequencyCount(nominalFeatureRadioButton.isSelected());
		listener.setBaseFeatures(chooseBaseFeatureCheckBox.isSelected());
	}
	
	private void readSelectedFeatures(){
		listener.clearFeatureSelection();
		if(sequenceFeatureTypeRadioButton.isSelected()){
			listener.setFeatureType(FeatureType.Sequence);
			readSelectedSequenceFeatures();
		}else if(alphabetFeatureTypeRadioButton.isSelected()){
			listener.setFeatureType(FeatureType.Alphabet);
			readSelectedAlphabetFeatures();
		}else{
			listener.setFeatureType(FeatureType.Best);
			/*
			 * Add all sequence features
			 */
			listener.featureSelectionChanged(individualEventSequenceCheckBox.getText(), true);
//			listener.featureSelectionChanged(kGramCheckBox.getText(), true);
//			listener.setKGramValue(kGramValue);
			listener.featureSelectionChanged(tandemRepeatSequenceCheckBox.getText(), true);
			listener.featureSelectionChanged(maximalRepeatSequenceCheckBox.getText(), true);
			
			/*
			 * Add all alphabet features
			 */
			listener.featureSelectionChanged(tandemRepeatAlphabetCheckBox.getText(), true);
			listener.featureSelectionChanged(maximalRepeatAlphabetCheckBox.getText(), true);
		}
	}
	
	private void readSelectedSequenceFeatures(){
		if(individualEventSequenceCheckBox.isSelected()){
			listener.featureSelectionChanged(individualEventSequenceCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(individualEventSequenceCheckBox.getText(), false);
		}
		
		if(kGramCheckBox.isSelected()){
			listener.featureSelectionChanged(kGramCheckBox.getText(), true);
			listener.setKGramValue(kGramValue);
		}else{
			listener.featureSelectionChanged(kGramCheckBox.getText(), false);
		}
		
		if(tandemRepeatSequenceCheckBox.isSelected()){
			listener.featureSelectionChanged(tandemRepeatSequenceCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(tandemRepeatSequenceCheckBox.getText(), false);
		}
		
		if(maximalRepeatSequenceCheckBox.isSelected()){
			listener.featureSelectionChanged(maximalRepeatSequenceCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(maximalRepeatSequenceCheckBox.getText(), false);
		}
		
		if(superMaximalRepeatSequenceCheckBox.isSelected()){
			listener.featureSelectionChanged(superMaximalRepeatSequenceCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(superMaximalRepeatSequenceCheckBox.getText(), false);
		}
		
		if(nearSuperMaximalRepeatSequenceCheckBox.isSelected()){
			listener.featureSelectionChanged(nearSuperMaximalRepeatSequenceCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(nearSuperMaximalRepeatSequenceCheckBox.getText(), false);
		}
	}
	
	private void readSelectedAlphabetFeatures(){
		if(individualEventAlphabetCheckBox.isSelected()){
			listener.featureSelectionChanged(individualEventAlphabetCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(individualEventAlphabetCheckBox.getText(), false);
		}
		
		if(tandemRepeatAlphabetCheckBox.isSelected()){
			listener.featureSelectionChanged(tandemRepeatAlphabetCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(tandemRepeatAlphabetCheckBox.getText(), false);
		}
		
		if(maximalRepeatAlphabetCheckBox.isSelected()){
			listener.featureSelectionChanged(maximalRepeatAlphabetCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(maximalRepeatAlphabetCheckBox.getText(), false);
		}
		
		if(superMaximalRepeatAlphabetCheckBox.isSelected()){
			listener.featureSelectionChanged(superMaximalRepeatAlphabetCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(superMaximalRepeatAlphabetCheckBox.getText(), false);
		}
		
		if(nearSuperMaximalRepeatAlphabetCheckBox.isSelected()){
			listener.featureSelectionChanged(nearSuperMaximalRepeatAlphabetCheckBox.getText(), true);
		}else{
			listener.featureSelectionChanged(nearSuperMaximalRepeatAlphabetCheckBox.getText(), false);
		}
	}
	
	public void setListener(SignatureDiscoverySettingsListener listener){
		this.listener = listener;
	}
	
	public int noSelectedFeatures(){
		noSelectedFeatures = 0;
		if(sequenceFeatureTypeRadioButton.isSelected()){
			if(individualEventSequenceCheckBox.isSelected())
				noSelectedFeatures++;
			if(kGramCheckBox.isSelected())
				noSelectedFeatures++;
			if(tandemRepeatSequenceCheckBox.isSelected())
				noSelectedFeatures++;
			if(maximalRepeatSequenceCheckBox.isSelected())
				noSelectedFeatures++;
			if(nearSuperMaximalRepeatSequenceCheckBox.isSelected())
				noSelectedFeatures++;
			if(superMaximalRepeatSequenceCheckBox.isSelected())
				noSelectedFeatures++;
		}else if(alphabetFeatureTypeRadioButton.isSelected()){
			if(individualEventAlphabetCheckBox.isSelected())
				noSelectedFeatures++;
			if(tandemRepeatAlphabetCheckBox.isSelected())
				noSelectedFeatures++;
			if(maximalRepeatAlphabetCheckBox.isSelected())
				noSelectedFeatures++;
			if(nearSuperMaximalRepeatAlphabetCheckBox.isSelected())
				noSelectedFeatures++;
			if(superMaximalRepeatAlphabetCheckBox.isSelected())
				noSelectedFeatures++;
		}
		return noSelectedFeatures;
	}

}
