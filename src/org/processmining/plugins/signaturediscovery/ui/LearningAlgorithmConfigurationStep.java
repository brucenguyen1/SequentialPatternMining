package org.processmining.plugins.signaturediscovery.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.processmining.plugins.signaturediscovery.settings.SignatureDiscoverySettingsListener;
import org.processmining.plugins.signaturediscovery.swingx.ScrollableGridLayout;
import org.processmining.plugins.signaturediscovery.types.EvaluationOptionType;
import org.processmining.plugins.signaturediscovery.types.LearningAlgorithmType;

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
public class LearningAlgorithmConfigurationStep extends myStep {
	JPanel learningAlgorithmPanel, algorithmParameterPanel;
	JRadioButton chooseBestRadioButton, decisionTreeRadioButton, associationRuleRadioButton;
	JRadioButton J48RadioButton, ID3RadioButton;
	JPanel J48ParameterPanel, J48PruningPanel;
	
	JCheckBox usePruningCheckBox;
	JRadioButton pessimisticErrorPruningRadioButton, reducedErrorPruningRadioButton;
	JTextField confidenceFactorTextField, pruningNoFoldsTextField;
	
	JPanel associationRuleParameterPanel;
	JCheckBox classAssociationRulesCheckBox;
	JComboBox associationRuleSortOrderComboBox;
	JTextField minMetricTextField, maxMetricTextField;
	
	JPanel boundsMinSupportPanel;
	JTextField lowerBoundMinSupportTextField, upperBoundMinSupportTextField;
	
	JPanel testOptionsPanel;
	JRadioButton useTrainingSetRadioButton, useTestSetRadioButton, crossValidationRadioButton, percentageSplitRadioButton;
	JLabel noFoldsLabel, percentageSplitLabel;
	JTextField noFoldsTextField, percentageSplitTextField;
	
	JPanel noRulesPanel;
	JTextField noRulesTextField;
	
	boolean isNominalFeatureCount = true;
	
	SignatureDiscoverySettingsListener listener;
	
	public LearningAlgorithmConfigurationStep(boolean isNominalFeatureCount){
		this.isNominalFeatureCount = isNominalFeatureCount;
		initComponents();
	}
	
	private void initComponents(){
		ScrollableGridLayout learningAlgorithmConfigurationPanelLayout = new ScrollableGridLayout(this, 1, 8, 0, 0);
		
		learningAlgorithmConfigurationPanelLayout.setRowFixed(0, true);
		learningAlgorithmConfigurationPanelLayout.setRowFixed(1, true);
		learningAlgorithmConfigurationPanelLayout.setRowFixed(2, true);
		learningAlgorithmConfigurationPanelLayout.setRowFixed(3, true);
		learningAlgorithmConfigurationPanelLayout.setRowFixed(4, true);
		learningAlgorithmConfigurationPanelLayout.setRowFixed(5, true);
		
		this.setLayout(learningAlgorithmConfigurationPanelLayout);
		
		JLabel headerLabel = SlickerFactory.instance().createLabel("<HTML><h1>Learning Algorithm Configuration Step</h1></HTML>");
		learningAlgorithmConfigurationPanelLayout.setPosition(headerLabel, 0, 0);
		add(headerLabel);
		
		buildLearningAlgorithmPanel();
		buildAlgorithmParametersPanel();
		buildTestOptionPanel();
		
		learningAlgorithmConfigurationPanelLayout.setPosition(learningAlgorithmPanel, 0, 1);
		add(learningAlgorithmPanel);
		
		Component verticalStrut1 = Box.createVerticalStrut(15);
		learningAlgorithmConfigurationPanelLayout.setPosition(verticalStrut1, 0, 2);
		add(verticalStrut1);
		
		learningAlgorithmConfigurationPanelLayout.setPosition(algorithmParameterPanel, 0, 3);
		add(algorithmParameterPanel);
//		
		Component verticalStrut2 = Box.createVerticalStrut(15);
		learningAlgorithmConfigurationPanelLayout.setPosition(verticalStrut2, 0, 4);
		add(verticalStrut2);
		
		learningAlgorithmConfigurationPanelLayout.setPosition(testOptionsPanel, 0, 5);
		add(testOptionsPanel);
	}
	
	private void buildLearningAlgorithmPanel(){
		learningAlgorithmPanel = SlickerFactory.instance().createRoundedPanel();
		learningAlgorithmPanel.setBorder(BorderFactory.createTitledBorder("Choose Learning Algorithm"));
		
		final ScrollableGridLayout learningAlgorithmPanelLayout = new ScrollableGridLayout(learningAlgorithmPanel, 3, 3, 0, 0);

		learningAlgorithmPanelLayout.setRowFixed(0, true);
		learningAlgorithmPanelLayout.setRowFixed(1, true);
		learningAlgorithmPanelLayout.setRowFixed(2, true);
		
		learningAlgorithmPanel.setLayout(learningAlgorithmPanelLayout);
		
		
		chooseBestRadioButton = SlickerFactory.instance().createRadioButton("Choose Best Fitting Algorithm");
		decisionTreeRadioButton = SlickerFactory.instance().createRadioButton("Decision Tree");
		decisionTreeRadioButton.setSelected(true);
		associationRuleRadioButton = SlickerFactory.instance().createRadioButton("Association Rules");
		
		ButtonGroup learningAlgorithmButtonGroup = new ButtonGroup();
		learningAlgorithmButtonGroup.add(chooseBestRadioButton);
		learningAlgorithmButtonGroup.add(decisionTreeRadioButton);
		learningAlgorithmButtonGroup.add(associationRuleRadioButton);
		
		J48RadioButton = SlickerFactory.instance().createRadioButton("J48");
		J48RadioButton.setSelected(true);
		
		ID3RadioButton = SlickerFactory.instance().createRadioButton("ID3");
		if(!isNominalFeatureCount){
			ID3RadioButton.setVisible(false);
			associationRuleRadioButton.setVisible(false);
		}
		
		ButtonGroup decisionTreeAlgorithmButtonGroup = new ButtonGroup();
		decisionTreeAlgorithmButtonGroup.add(J48RadioButton);
		decisionTreeAlgorithmButtonGroup.add(ID3RadioButton);
		
		
		learningAlgorithmPanelLayout.setPosition(chooseBestRadioButton, 0, 0);
		learningAlgorithmPanel.add(chooseBestRadioButton);
		
		learningAlgorithmPanelLayout.setPosition(decisionTreeRadioButton, 0, 1);
		learningAlgorithmPanel.add(decisionTreeRadioButton);
		
		learningAlgorithmPanelLayout.setPosition(J48RadioButton, 1, 1);
		learningAlgorithmPanel.add(J48RadioButton);
		
		learningAlgorithmPanelLayout.setPosition(ID3RadioButton, 2, 1);
		learningAlgorithmPanel.add(ID3RadioButton);
		
		learningAlgorithmPanelLayout.setPosition(associationRuleRadioButton, 0, 2);
		learningAlgorithmPanel.add(associationRuleRadioButton);
		
		chooseBestRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(chooseBestRadioButton.isSelected()){
					J48RadioButton.setVisible(false);
					ID3RadioButton.setVisible(false);
					algorithmParameterPanel.setVisible(false);
					testOptionsPanel.setVisible(false);
				}
			}
		});
		
		decisionTreeRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(decisionTreeRadioButton.isSelected()){
					J48RadioButton.setVisible(true);
					J48RadioButton.setEnabled(true);
					ID3RadioButton.setVisible(true);
					if(isNominalFeatureCount)
						ID3RadioButton.setEnabled(true);
				}else{
					J48RadioButton.setVisible(false);
					ID3RadioButton.setVisible(false);
					J48RadioButton.setEnabled(false);
					ID3RadioButton.setEnabled(false);
				}
				algorithmParameterPanel.removeAll();
				if(J48RadioButton.isSelected()){
					J48ParameterPanel.setVisible(true);
					algorithmParameterPanel.add(J48ParameterPanel);
				}
				algorithmParameterPanel.setVisible(true);
				testOptionsPanel.setVisible(true);
				revalidate();
				repaint();
			}
		});
		
		associationRuleRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(associationRuleRadioButton.isSelected()){
					J48RadioButton.setVisible(false);
					ID3RadioButton.setVisible(false);
					
					J48RadioButton.setEnabled(false);
					ID3RadioButton.setEnabled(false);
				}
				algorithmParameterPanel.removeAll();
				algorithmParameterPanel.add(associationRuleParameterPanel);
				algorithmParameterPanel.setVisible(true);
				testOptionsPanel.setVisible(false);
				revalidate();
				repaint();
			}
		});
		
		J48RadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(J48RadioButton.isSelected()){
					J48ParameterPanel.setVisible(true);
					algorithmParameterPanel.setVisible(true);
					algorithmParameterPanel.removeAll();
					algorithmParameterPanel.add(J48ParameterPanel);
				}else{
					J48ParameterPanel.setVisible(false);
					algorithmParameterPanel.setVisible(false);
				}
				revalidate();
				repaint();
			}
		});
		
		ID3RadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(ID3RadioButton.isSelected()){
					J48ParameterPanel.setVisible(false);
					algorithmParameterPanel.setVisible(false);
				}else{
					J48ParameterPanel.setVisible(true);
					algorithmParameterPanel.setVisible(true);
				}
				revalidate();
				repaint();
			}
		});
	}
	
	private void buildAlgorithmParametersPanel(){
		algorithmParameterPanel = SlickerFactory.instance().createRoundedPanel();
		algorithmParameterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		algorithmParameterPanel.setBorder(BorderFactory.createEtchedBorder());
		buildJ48ParameterPanel();
		buildAssociationRuleParameterPanel();
		algorithmParameterPanel.add(J48ParameterPanel);
	}
	
	private void buildJ48ParameterPanel(){
		J48ParameterPanel = SlickerFactory.instance().createRoundedPanel();
		J48ParameterPanel.setBorder(BorderFactory.createTitledBorder("J48 Parameter Settings"));
		
		ScrollableGridLayout J48ParameterPanelLayout = new ScrollableGridLayout(J48ParameterPanel, 2, 2, 0, 0);
		J48ParameterPanelLayout.setRowFixed(0, true);
		J48ParameterPanelLayout.setRowFixed(1, true);
		
		J48ParameterPanel.setLayout(J48ParameterPanelLayout);
		
		usePruningCheckBox = SlickerFactory.instance().createCheckBox("Prune Trees", true);
		usePruningCheckBox.setPreferredSize(new Dimension(learningAlgorithmPanel.getPreferredSize().width,20));

		usePruningCheckBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(usePruningCheckBox.isSelected()){
					J48PruningPanel.setVisible(true);
				}else{
					J48PruningPanel.setVisible(false);
				}
			}
		});
		
		J48ParameterPanelLayout.setPosition(usePruningCheckBox, 0, 0);
		J48ParameterPanel.add(usePruningCheckBox);
		
		buildJ48PruningPanel();
		J48ParameterPanelLayout.setPosition(J48PruningPanel, 0, 1);
		J48ParameterPanel.add(J48PruningPanel);
	}
	
	private void buildJ48PruningPanel(){
		J48PruningPanel = SlickerFactory.instance().createRoundedPanel();
		ScrollableGridLayout J48PruningPanelLayout = new ScrollableGridLayout(J48PruningPanel, 4, 2, 0, 0);

		J48PruningPanelLayout.setRowFixed(0, true);
		J48PruningPanelLayout.setRowFixed(1, true);
		
//		J48PruningPanelLayout.setColumnFixed(3, true);
		
		J48PruningPanel.setLayout(J48PruningPanelLayout);
		
		pessimisticErrorPruningRadioButton = SlickerFactory.instance().createRadioButton("Pessimistic Error Pruning");
		pessimisticErrorPruningRadioButton.setSelected(true);
		reducedErrorPruningRadioButton = SlickerFactory.instance().createRadioButton("Reduced Error Pruning");
		
		ButtonGroup pruningButtonGroup = new ButtonGroup();
		pruningButtonGroup.add(pessimisticErrorPruningRadioButton);
		pruningButtonGroup.add(reducedErrorPruningRadioButton);
		
		JLabel blankLabel1 = SlickerFactory.instance().createLabel("    ");
		
		final JLabel confidenceFactorLabel = SlickerFactory.instance().createLabel("<HTML>Confidence Factor </HTML>");
		confidenceFactorTextField = new JTextField("0.25  ");
		
		final JLabel pruningNoFoldsLabel = SlickerFactory.instance().createLabel("No. Folds");
		pruningNoFoldsLabel.setEnabled(false);
		pruningNoFoldsTextField = new JTextField("3  ");
		pruningNoFoldsTextField.setEnabled(false);
		
		J48PruningPanelLayout.setPosition(pessimisticErrorPruningRadioButton, 0, 0);
		J48PruningPanel.add(pessimisticErrorPruningRadioButton);
		
		J48PruningPanelLayout.setPosition(blankLabel1, 1, 0);
		J48PruningPanel.add(blankLabel1);
		
		J48PruningPanelLayout.setPosition(confidenceFactorLabel, 2, 0);
		J48PruningPanel.add(confidenceFactorLabel);
		
		J48PruningPanelLayout.setPosition(confidenceFactorTextField, 3, 0);
		J48PruningPanel.add(confidenceFactorTextField);
		
		J48PruningPanelLayout.setPosition(reducedErrorPruningRadioButton, 0, 1);
		J48PruningPanel.add(reducedErrorPruningRadioButton);
		
		J48PruningPanelLayout.setPosition(pruningNoFoldsLabel, 2, 1);
		J48PruningPanel.add(pruningNoFoldsLabel);
		
		J48PruningPanelLayout.setPosition(pruningNoFoldsTextField, 3, 1);
		J48PruningPanel.add(pruningNoFoldsTextField);
		
		pessimisticErrorPruningRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(pessimisticErrorPruningRadioButton.isSelected()){
					pruningNoFoldsLabel.setText("<HTML><font color=gray>No. Folds</HTML>");
					pruningNoFoldsTextField.setEnabled(false);
					
					confidenceFactorLabel.setText("<HTML><font color=black>Confidence Factor </HTML>");
					confidenceFactorTextField.setEnabled(true);
				}
			}
		});
		
		reducedErrorPruningRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(reducedErrorPruningRadioButton.isSelected()){
					confidenceFactorLabel.setText("<HTML><font color=gray>Confidence Factor </HTML>");
					confidenceFactorTextField.setEnabled(false);
					
					pruningNoFoldsLabel.setText("<HTML><font color=black>No. Folds</HTML>");
					pruningNoFoldsTextField.setEnabled(true);
				}
			}
		});
	}
	
	private void buildAssociationRuleParameterPanel(){
		associationRuleParameterPanel = SlickerFactory.instance().createRoundedPanel();
		associationRuleParameterPanel.setBorder(BorderFactory.createTitledBorder("Association Rules Parameter Settings"));
	
		ScrollableGridLayout associationRuleParameterPanelLayout = new ScrollableGridLayout(associationRuleParameterPanel, 2, 2, 0, 0);
		associationRuleParameterPanelLayout.setRowFixed(0, true);
		associationRuleParameterPanelLayout.setRowFixed(1, true);
		
		associationRuleParameterPanel.setLayout(associationRuleParameterPanelLayout);
		JLabel minSupportLabel = SlickerFactory.instance().createLabel("Min. Support                     ");
		lowerBoundMinSupportTextField = new JTextField("0.2    ");
		
		JLabel minConfidenceLabel = SlickerFactory.instance().createLabel("Min. Confidence                       ");
		minMetricTextField = new JTextField("0.9  ");
		
		associationRuleParameterPanelLayout.setPosition(minSupportLabel, 0, 0);
		associationRuleParameterPanel.add(minSupportLabel);
		
		associationRuleParameterPanelLayout.setPosition(lowerBoundMinSupportTextField, 1, 0);
		associationRuleParameterPanel.add(lowerBoundMinSupportTextField);
		
		associationRuleParameterPanelLayout.setPosition(minConfidenceLabel, 0, 1);
		associationRuleParameterPanel.add(minConfidenceLabel);
		
		associationRuleParameterPanelLayout.setPosition(minMetricTextField, 1, 1);
		associationRuleParameterPanel.add(minMetricTextField);
	}
	
	@SuppressWarnings("unused")
	private void buildAssociationRuleWekaParameterPanel(){
		associationRuleParameterPanel = SlickerFactory.instance().createRoundedPanel();
		associationRuleParameterPanel.setBorder(BorderFactory.createTitledBorder("Association Rules Parameter Settings"));
		
		ScrollableGridLayout associationRuleParameterPanelLayout = new ScrollableGridLayout(associationRuleParameterPanel, 2, 6, 0, 0);

		associationRuleParameterPanelLayout.setRowFixed(0, true);
		associationRuleParameterPanelLayout.setRowFixed(1, true);
		associationRuleParameterPanelLayout.setRowFixed(2, true);
		associationRuleParameterPanelLayout.setRowFixed(3, true);
		associationRuleParameterPanelLayout.setRowFixed(4, true);
		associationRuleParameterPanelLayout.setRowFixed(5, true);
		
		associationRuleParameterPanel.setLayout(associationRuleParameterPanelLayout);
		
		classAssociationRulesCheckBox = SlickerFactory.instance().createCheckBox("Find Associations for Class Attribute", true);
		
		JPanel sortAssociationRulesPanel = SlickerFactory.instance().createRoundedPanel();
		sortAssociationRulesPanel.setLayout(new BoxLayout(sortAssociationRulesPanel, BoxLayout.X_AXIS));
		
		JLabel sortAssociationRulesLabel = SlickerFactory.instance().createLabel("  Sort rules by  ");
		String[] associationRuleSortOptions = {"Confidence", "Lift", "Leverage", "Conviction"};
		associationRuleSortOrderComboBox = SlickerFactory.instance().createComboBox(associationRuleSortOptions);
		associationRuleSortOrderComboBox.setSelectedIndex(0);
		
		sortAssociationRulesPanel.add(sortAssociationRulesLabel);
		sortAssociationRulesPanel.add(Box.createHorizontalStrut(5));
		sortAssociationRulesPanel.add(associationRuleSortOrderComboBox);
		
		JPanel minMetricPanel = SlickerFactory.instance().createRoundedPanel();
		minMetricPanel.setLayout(new BoxLayout(minMetricPanel, BoxLayout.X_AXIS));
		
		JLabel minMetricLabel = SlickerFactory.instance().createLabel("  Min. Metric Value: ");
		minMetricTextField = new JTextField("0.9   ");
		
		minMetricPanel.add(minMetricLabel);
		sortAssociationRulesPanel.add(Box.createHorizontalStrut(25));
		minMetricPanel.add(minMetricTextField);
	
		buildBoundsMinSupportPanel();
		
		associationRuleParameterPanelLayout.setPosition(classAssociationRulesCheckBox, 0, 0);
		associationRuleParameterPanel.add(classAssociationRulesCheckBox);
		
		associationRuleParameterPanelLayout.setPosition(sortAssociationRulesPanel, 0, 1);
		associationRuleParameterPanel.add(sortAssociationRulesPanel);
		
		associationRuleParameterPanelLayout.setPosition(minMetricPanel, 1,1);
		associationRuleParameterPanel.add(minMetricPanel);
		
		Component comp1 = Box.createVerticalStrut(10);
		
		associationRuleParameterPanelLayout.setPosition(comp1, 0, 2);
		associationRuleParameterPanel.add(comp1);
		
		associationRuleParameterPanelLayout.setPosition(boundsMinSupportPanel, 0, 3);
		associationRuleParameterPanel.add(boundsMinSupportPanel);
	}
	
	private void buildBoundsMinSupportPanel(){
		boundsMinSupportPanel = SlickerFactory.instance().createRoundedPanel();
		boundsMinSupportPanel.setBorder(BorderFactory.createTitledBorder("Min Support Bounds"));
		
		GridBagLayout boundsMinSupportGBLayout = new GridBagLayout();
		boundsMinSupportPanel.setLayout(boundsMinSupportGBLayout);
		
		JLabel lowerBoundMinSupportLabel = SlickerFactory.instance().createLabel("  Lower Bound   ");
		JLabel upperBoundMinSupportLabel = SlickerFactory.instance().createLabel("  Upper Bound   ");
		
		lowerBoundMinSupportTextField = new JTextField("0.1    ");
		upperBoundMinSupportTextField = new JTextField("1.0    ");
		
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		boundsMinSupportPanel.add(lowerBoundMinSupportLabel, c);
	
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		boundsMinSupportPanel.add(lowerBoundMinSupportTextField, c);

		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		boundsMinSupportPanel.add(Box.createHorizontalStrut(50), c);
		
		c.gridx = 3;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		boundsMinSupportPanel.add(upperBoundMinSupportLabel, c);
		
		c.gridx = 4;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		boundsMinSupportPanel.add(upperBoundMinSupportTextField, c);	
	}
	
	private void buildTestOptionPanel(){
		testOptionsPanel = SlickerFactory.instance().createRoundedPanel();
		testOptionsPanel.setBorder(BorderFactory.createTitledBorder("Test Options"));
		
		ScrollableGridLayout testOptionsPanelLayout = new ScrollableGridLayout(testOptionsPanel, 4, 3, 0, 0);
		testOptionsPanel.setLayout(testOptionsPanelLayout);
		testOptionsPanelLayout.setRowFixed(0, true);
		testOptionsPanelLayout.setRowFixed(1, true);
		testOptionsPanelLayout.setRowFixed(2, true);
		
		testOptionsPanelLayout.setColumnFixed(3, true);
		
		useTrainingSetRadioButton = SlickerFactory.instance().createRadioButton("Use Training Set");
		useTrainingSetRadioButton.setSelected(false);
		
		useTrainingSetRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(useTrainingSetRadioButton.isSelected()){
					noFoldsLabel.setEnabled(false);
					noFoldsTextField.setEnabled(false);
					percentageSplitLabel.setEnabled(false);
					percentageSplitTextField.setEnabled(false);
				}
			}
		});
		
		crossValidationRadioButton = SlickerFactory.instance().createRadioButton("Cross Validation");
		crossValidationRadioButton.setSelected(true);
		noFoldsLabel = SlickerFactory.instance().createLabel("Folds");
		noFoldsTextField = new JTextField("10");

		percentageSplitRadioButton = SlickerFactory.instance().createRadioButton("Percentage Split");
		percentageSplitRadioButton.setSelected(false);
		percentageSplitLabel = SlickerFactory.instance().createLabel("%");
		percentageSplitTextField = new JTextField("66");
		
		percentageSplitLabel.setEnabled(false);
		percentageSplitTextField.setEnabled(false);
		
		ButtonGroup testOptionsButtonGroup = new ButtonGroup();
		testOptionsButtonGroup.add(useTrainingSetRadioButton);
		testOptionsButtonGroup.add(crossValidationRadioButton);
		testOptionsButtonGroup.add(percentageSplitRadioButton);
		
		testOptionsPanelLayout.setPosition(useTrainingSetRadioButton, 0, 0);
		testOptionsPanel.add(useTrainingSetRadioButton);
		
		testOptionsPanelLayout.setPosition(crossValidationRadioButton, 0, 1);
		testOptionsPanel.add(crossValidationRadioButton);

		final JLabel blankLabel1 = SlickerFactory.instance().createLabel("         ");
		testOptionsPanelLayout.setPosition(blankLabel1, 1, 1);
		testOptionsPanel.add(blankLabel1);

		testOptionsPanelLayout.setPosition(noFoldsLabel, 2, 1);
		testOptionsPanel.add(noFoldsLabel);
		testOptionsPanelLayout.setPosition(noFoldsTextField, 3, 1);
		testOptionsPanel.add(noFoldsTextField);
		
		crossValidationRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(crossValidationRadioButton.isSelected()){
					percentageSplitLabel.setEnabled(false);
					percentageSplitTextField.setEnabled(false);
					
					noFoldsLabel.setEnabled(true);
					noFoldsTextField.setEnabled(true);
				}
			}
		});
		
		
		testOptionsPanelLayout.setPosition(percentageSplitRadioButton, 0, 2);
		testOptionsPanel.add(percentageSplitRadioButton);

		testOptionsPanelLayout.setPosition(percentageSplitLabel, 2, 2);
		testOptionsPanel.add(percentageSplitLabel);
		testOptionsPanelLayout.setPosition(percentageSplitTextField, 3, 2);
		testOptionsPanel.add(percentageSplitTextField);
		
		percentageSplitRadioButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(percentageSplitRadioButton.isSelected()){
					noFoldsLabel.setEnabled(false);
					noFoldsTextField.setEnabled(false);
					
					percentageSplitLabel.setEnabled(true);
					percentageSplitTextField.setEnabled(true);
				}
			}
		});
	}
	
	public boolean precondition() {
		return true;
	}

	public void readSettings() {
		readLearningAlgorithmType();
		readLearningAlgorithmParameterSettings();
		readTestOptionsSettings();
	}
	
	private void readLearningAlgorithmType(){
		if(chooseBestRadioButton.isSelected())
			listener.setLearningAlgorithmType(LearningAlgorithmType.Best);
		else if(decisionTreeRadioButton.isSelected() && J48RadioButton.isSelected())
			listener.setLearningAlgorithmType(LearningAlgorithmType.J48);
		else if(decisionTreeRadioButton.isSelected() && ID3RadioButton.isSelected())
			listener.setLearningAlgorithmType(LearningAlgorithmType.Id3);
		else if(associationRuleRadioButton.isSelected())
			listener.setLearningAlgorithmType(LearningAlgorithmType.AssociationRules);
	}
	
	private void readLearningAlgorithmParameterSettings(){
		if(decisionTreeRadioButton.isSelected() && J48RadioButton.isSelected()){
			String confidenceFactorFoldsStr;
			if(pessimisticErrorPruningRadioButton.isSelected())
				confidenceFactorFoldsStr = confidenceFactorTextField.getText().trim();
			else
				confidenceFactorFoldsStr = pruningNoFoldsTextField.getText().trim();
			
			listener.setJ48Parameters(usePruningCheckBox.isSelected(), pessimisticErrorPruningRadioButton.isSelected(), confidenceFactorFoldsStr);
		}else if(associationRuleRadioButton.isSelected()){
//			listener.setAssociationRuleParameters(classAssociationRulesCheckBox.isSelected(), associationRuleSortOrderComboBox.getSelectedItem().toString(), minMetricTextField.getText().trim(), lowerBoundMinSupportTextField.getText().trim(), upperBoundMinSupportTextField.getText().trim());
			listener.setAssociationRuleParameters(true, "Confidence", minMetricTextField.getText(), lowerBoundMinSupportTextField.getText(), "1.0");
		}
	}

	private void readTestOptionsSettings(){
		if(useTrainingSetRadioButton.isSelected()){
			listener.setEvaluationOptions(EvaluationOptionType.TrainingSet, "");
		}else if(crossValidationRadioButton.isSelected()){
			listener.setEvaluationOptions(EvaluationOptionType.CrossValidation, noFoldsTextField.getText().trim());
		}else if(percentageSplitRadioButton.isSelected()){
			listener.setEvaluationOptions(EvaluationOptionType.PercentageSplit, percentageSplitTextField.getText().trim());
		}
	}
	
	public void setListener(SignatureDiscoverySettingsListener listener){
		this.listener = listener;
	}
	
	public void setNominalFeatureCount(boolean isNominalFeatureCount){
		this.isNominalFeatureCount = isNominalFeatureCount;
		if(isNominalFeatureCount){
			ID3RadioButton.setVisible(true);
			associationRuleRadioButton.setVisible(true);
		}else{
			ID3RadioButton.setVisible(false);
			associationRuleRadioButton.setVisible(false);
		}
		revalidate();
		repaint();
	}
}
