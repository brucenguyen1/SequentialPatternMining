package org.processmining.plugins.signaturediscovery.ui;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
public class SignatureClassConfigurationStep extends myStep {
	Set<String> classSet;
	JPanel signatureClassCheckBoxPanel;
	JPanel noRulesPanel;
	JTextField noRulesTextField;
	JCheckBox[] signatureClassCheckBoxArray;
	
	SignatureDiscoverySettingsListener listener;
		
	public SignatureClassConfigurationStep(Set<String> classSet){
		this.classSet = classSet;
		initComponents();
	}
	
	private void initComponents(){
		ScrollableGridLayout signatureClassConfigurationPanelLayout = new ScrollableGridLayout(this, 1, 5, 0, 0);
		signatureClassConfigurationPanelLayout.setRowFixed(0, true);
		signatureClassConfigurationPanelLayout.setRowFixed(1, true);
		signatureClassConfigurationPanelLayout.setRowFixed(2, true);
		signatureClassConfigurationPanelLayout.setRowFixed(3, true);
		signatureClassConfigurationPanelLayout.setRowFixed(4, true);
		
		setLayout(signatureClassConfigurationPanelLayout);
		
		JLabel headerLabel = SlickerFactory.instance().createLabel("<HTML><h1>Signature Pattern Configuration Step<BR></h1></HTML>");
		signatureClassConfigurationPanelLayout.setPosition(headerLabel, 0, 0);
		add(headerLabel);
		
		Component verticalStrut1 = Box.createVerticalStrut(15);
		signatureClassConfigurationPanelLayout.setPosition(verticalStrut1, 0, 1);
		add(verticalStrut1);
		
		buildSignatureClassCheckBoxPanel();
	
		signatureClassConfigurationPanelLayout.setPosition(signatureClassCheckBoxPanel, 0, 2);
		add(signatureClassCheckBoxPanel);
		
		Component verticalStrut2 = Box.createVerticalStrut(15);
		signatureClassConfigurationPanelLayout.setPosition(verticalStrut2, 0, 3);
		add(verticalStrut2);
		
		buildNoRulesPanel();
		signatureClassConfigurationPanelLayout.setPosition(noRulesPanel, 0, 4);
		add(noRulesPanel);
	}
	
	private void buildSignatureClassCheckBoxPanel(){
		int noRows, noCols;
		if(classSet.size() < 10){
			noRows = classSet.size();
			noCols = 1;
		}else{
			noRows = classSet.size()/2;
			noCols = 2;
		}
		
		signatureClassCheckBoxPanel = SlickerFactory.instance().createRoundedPanel();
		signatureClassCheckBoxPanel.setBorder(BorderFactory.createTitledBorder("Select classes for which signature patterns are to be discovered"));
	
		ScrollableGridLayout signatureClassCheckBoxPanelLayout = new ScrollableGridLayout(signatureClassCheckBoxPanel, noCols, noRows, 0, 0);
		for(int i = 0; i < noRows; i++){
			signatureClassCheckBoxPanelLayout.setRowFixed(i, true);
		}
		signatureClassCheckBoxPanel.setLayout(signatureClassCheckBoxPanelLayout);
		
		signatureClassCheckBoxArray = new JCheckBox[classSet.size()];
		int index = 0;
		for(String classValue : classSet){
			signatureClassCheckBoxArray[index++] = SlickerFactory.instance().createCheckBox(classValue, true); 
		}
		
		index = 0;
		for(int i = 0; i < noRows; i++){
			for(int j = 0; j < noCols; j++){
				signatureClassCheckBoxPanelLayout.setPosition(signatureClassCheckBoxArray[index], j, i);
				signatureClassCheckBoxPanel.add(signatureClassCheckBoxArray[index]);
				index++;
			}
		}
		
	}
	
	private void buildNoRulesPanel(){
		noRulesPanel = SlickerFactory.instance().createRoundedPanel();

		ScrollableGridLayout noRulesPanelLayout = new ScrollableGridLayout(noRulesPanel, 3, 1, 0, 0);
		noRulesPanelLayout.setRowFixed(0, true);
		
		noRulesPanelLayout.setColumnFixed(0, true);
		noRulesPanelLayout.setColumnFixed(1, true);
		
		noRulesPanel.setLayout(noRulesPanelLayout);
		
		JLabel noRulesLabel = SlickerFactory.instance().createLabel("Number of Signature Pattern Rules to Generate:  ");
		noRulesTextField = new JTextField("10   ");

		noRulesPanelLayout.setPosition(noRulesLabel, 0, 0);
		noRulesPanel.add(noRulesLabel);
		
		noRulesPanelLayout.setPosition(noRulesTextField, 1, 0);
		noRulesPanel.add(noRulesTextField);
	}
	
	public boolean precondition() {
		return true;
	}

	public void readSettings() {
		Set<String> generateSignaturesForClassLabelSet = new HashSet<String>();
		for(JCheckBox classLabelCheckBox : signatureClassCheckBoxArray){
			if(classLabelCheckBox.isSelected())
				generateSignaturesForClassLabelSet.add(classLabelCheckBox.getText().trim());
		}
		listener.setSignatureClassOptions(generateSignaturesForClassLabelSet, new Integer(noRulesTextField.getText().trim()).intValue());
	}
	
	public void setListener(SignatureDiscoverySettingsListener listener){
		this.listener = listener;
	}
}
