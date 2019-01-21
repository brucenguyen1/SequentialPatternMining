package org.processmining.plugins.signaturediscovery.ui;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.processmining.plugins.signaturediscovery.settings.SignatureDiscoverySettingsListener;
import org.processmining.plugins.signaturediscovery.swingx.ScrollableGridLayout;

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
public class FeatureCombinationConfigurationStep extends myStep {
	JPanel combinationConfigurationPanel;
	JRadioButton chooseBestCombinationRadioButton;
	JRadioButton mixFeatureCombinationRadioButton;
	JRadioButton considerAllCombinationRadioButton;
	
	SignatureDiscoverySettingsListener listener;
	
	public FeatureCombinationConfigurationStep(){
		initComponents();
	}
	
	private void initComponents(){
		ScrollableGridLayout featureCombinationConfigurationStepPanelLayout = new ScrollableGridLayout(this, 1, 2, 0, 0);
		featureCombinationConfigurationStepPanelLayout.setRowFixed(0, true);

		setLayout(featureCombinationConfigurationStepPanelLayout);
		
		JLabel headerLabel = SlickerFactory.instance().createLabel("<HTML><h1>Feature Combination Configuration Step</h1><BR></HTML>");
		featureCombinationConfigurationStepPanelLayout.setPosition(headerLabel, 0, 0);
		add(headerLabel);
		
		buildCombinationConfigurationPanel();
		featureCombinationConfigurationStepPanelLayout.setPosition(combinationConfigurationPanel, 0, 1);
		add(combinationConfigurationPanel);
	}
	
	private void buildCombinationConfigurationPanel(){
		combinationConfigurationPanel = SlickerFactory.instance().createRoundedPanel();
		combinationConfigurationPanel.setBorder(BorderFactory.createTitledBorder("Select Feature Combination Type for Multiple Features"));
		
		ScrollableGridLayout combinationConfigurationPanelLayout = new ScrollableGridLayout(combinationConfigurationPanel, 1, 4, 0, 0);
		combinationConfigurationPanelLayout.setRowFixed(0, true);
		combinationConfigurationPanel.setLayout(combinationConfigurationPanelLayout);
		
		chooseBestCombinationRadioButton = SlickerFactory.instance().createRadioButton("Choose Best Feature Combination");
		chooseBestCombinationRadioButton.setSelected(true);
		
		mixFeatureCombinationRadioButton = SlickerFactory.instance().createRadioButton("Mix Features");
		considerAllCombinationRadioButton = SlickerFactory.instance().createRadioButton("Consider All Possibilities Separately");
		
		ButtonGroup combinatonButtonGroup = new ButtonGroup();
		combinatonButtonGroup.add(chooseBestCombinationRadioButton);
		combinatonButtonGroup.add(mixFeatureCombinationRadioButton);
		combinatonButtonGroup.add(considerAllCombinationRadioButton);
		
		combinationConfigurationPanelLayout.setPosition(chooseBestCombinationRadioButton, 0, 0);
		combinationConfigurationPanel.add(chooseBestCombinationRadioButton);
		
		combinationConfigurationPanelLayout.setPosition(mixFeatureCombinationRadioButton, 0, 1);
		combinationConfigurationPanel.add(mixFeatureCombinationRadioButton);
		
		combinationConfigurationPanelLayout.setPosition(considerAllCombinationRadioButton, 0, 2);
		combinationConfigurationPanel.add(considerAllCombinationRadioButton);
		
	}

	public void setListener(SignatureDiscoverySettingsListener listener){
		this.listener = listener;
	}
	
	public boolean precondition() {
		return true;
	}

	public void readSettings() {
		if(chooseBestCombinationRadioButton.isSelected())
			listener.setFeatureCombination("Best");
		else if(mixFeatureCombinationRadioButton.isSelected())
			listener.setFeatureCombination("Mix");
		else
			listener.setFeatureCombination("All");
	}

}
