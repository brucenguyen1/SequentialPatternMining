package org.processmining.plugins.signaturediscovery.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.processmining.plugins.signaturediscovery.metrics.Metrics;
import org.processmining.plugins.signaturediscovery.swingx.ScrollableGridLayout;

import com.fluxicon.slickerbox.factory.SlickerFactory;

import corejava.PrintfFormat;

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
public class RuleComponent extends JPanel {
	protected String rule;
	protected String decodedRule;
	protected JCheckBox activeCheckBox;
	protected JList conjunctiveConstraintList;
	protected JLabel classLabel;
	protected Metrics metrics;
	protected JPanel metricsPanel;
	DecimalFormat decimalFormat = new DecimalFormat("0.00");
	Color backgroundColor;
	public RuleComponent(Color backgroundColor, String rule, String decodedRule, Metrics metrics){
		setBackground(backgroundColor);
		setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.backgroundColor = backgroundColor;
		this.rule = rule;
		this.decodedRule = decodedRule;
		this.metrics = metrics;
	
		final GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 0, 10);
		
		activeCheckBox = SlickerFactory.instance().createCheckBox("", false);
		this.add(activeCheckBox);

		conjunctiveConstraintList = new JList();
		conjunctiveConstraintList.setBorder(BorderFactory.createEmptyBorder());
		conjunctiveConstraintList.setAutoscrolls(true);
		conjunctiveConstraintList.setBackground(this.getBackground());
		conjunctiveConstraintList.setAlignmentX(LEFT_ALIGNMENT);
		
		MouseListener[] mouseListeners = conjunctiveConstraintList.getMouseListeners();
		for(MouseListener mouseListerner : mouseListeners)
			conjunctiveConstraintList.removeMouseListener(mouseListerner);
		
		conjunctiveConstraintList.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent e) {
				 	int index  = conjunctiveConstraintList.locationToIndex(e.getPoint());
				 	if(conjunctiveConstraintList.isSelectedIndex(index)){
				 		conjunctiveConstraintList.removeSelectionInterval(index, index);
				 	}else{
				 		conjunctiveConstraintList.addSelectionInterval(index, index);
				 	}
			    }
		});
		
		classLabel = SlickerFactory.instance().createLabel("");
		
		buildMetricsPanel();
		
		this.add(Box.createHorizontalStrut(15));
		prepareRuleConjunctiveConstraintList(decodedRule);
		this.add(conjunctiveConstraintList);
		this.add(Box.createGlue());
		this.add(classLabel);
		this.add(Box.createHorizontalStrut(15));
		this.add(metricsPanel);
		this.add(Box.createHorizontalStrut(15));
	}
	
	public void prepareRuleConjunctiveConstraintList(String rule){
		String[] ruleSplit = rule.replaceAll("IF ", "").split(" THEN ");
		String[] antecedantSplit = ruleSplit[0].split(" AND ");
		int noAntecedants = antecedantSplit.length;
		
		for(int i = 0; i < noAntecedants-1; i++)
			antecedantSplit[i] = antecedantSplit[i].concat(" AND ");
		
		conjunctiveConstraintList.setListData(antecedantSplit);
		classLabel.setText("   "+ruleSplit[1]+"  ");

		revalidate();
		repaint();
	}
	
	public void showPresenceConstraintsOnly(boolean showPresenceConstraints){
		String[] ruleSplit = decodedRule.replaceAll("IF ", "").split(" THEN ");
		String[] antecedantSplit = ruleSplit[0].split(" AND ");
		List<String> presenceAntecedantList = new ArrayList<String>();
		
		String[] constraintSplit;
		if(showPresenceConstraints){
			for(String antecedant : antecedantSplit){
				if(antecedant.contains(">=")){
					presenceAntecedantList.add(antecedant);
				}else if(antecedant.contains("<=")){
					presenceAntecedantList.add(antecedant);
				}else if(antecedant.contains("=")){
					constraintSplit = antecedant.split("=");
					if(!constraintSplit[1].equals("0")){
						presenceAntecedantList.add(antecedant);
					}
				}else if(antecedant.contains("<")){
					constraintSplit = antecedant.split("=");
					if(!constraintSplit[1].equals("0")){
						presenceAntecedantList.add(antecedant);
					}
				}else{
					presenceAntecedantList.add(antecedant);
				}
			}
			
			int noAntecedants = presenceAntecedantList.size();
			for(int i = 0; i < noAntecedants-1; i++)
				presenceAntecedantList.set(i, presenceAntecedantList.get(i).concat(" AND "));
			
			conjunctiveConstraintList.setListData(presenceAntecedantList.toArray());
			if(presenceAntecedantList.size() == 0)
				this.setVisible(false);
		}else{
			int noAntecedants = antecedantSplit.length;
			
			for(int i = 0; i < noAntecedants-1; i++)
				antecedantSplit[i] = antecedantSplit[i].concat(" AND ");
			
			conjunctiveConstraintList.setListData(antecedantSplit);
			this.setVisible(true);
		}
		classLabel.setText("   "+ruleSplit[1]+"  ");

		revalidate();
		repaint();
	}
	
	private void buildMetricsPanel(){
		metricsPanel = SlickerFactory.instance().createRoundedPanel();
		metricsPanel.setBackground(backgroundColor);
		ScrollableGridLayout metricsPanelLayout = new ScrollableGridLayout(metricsPanel, 8, 1, 0, 0);
		metricsPanelLayout.setColumnFixed(0, true);
		metricsPanelLayout.setColumnFixed(1, true);
		metricsPanelLayout.setColumnFixed(2, true);
		metricsPanelLayout.setColumnFixed(3, true);
		metricsPanelLayout.setColumnFixed(4, true);
		metricsPanelLayout.setColumnFixed(5, true);
		metricsPanelLayout.setColumnFixed(6, true);
		metricsPanelLayout.setColumnFixed(7, true);
		metricsPanel.setLayout(metricsPanelLayout);

		JLabel noTPLabel = SlickerFactory.instance().createLabel(new PrintfFormat("%3d").sprintf(metrics.getTP())+"   ");
		JLabel noFPLabel = SlickerFactory.instance().createLabel(new PrintfFormat("%3d").sprintf(metrics.getFP())+"   ");
		JLabel noTNLabel = SlickerFactory.instance().createLabel(new PrintfFormat("%3d").sprintf(metrics.getTN())+"   ");
		JLabel noFNLabel = SlickerFactory.instance().createLabel(new PrintfFormat("%3d").sprintf(metrics.getFN())+"   ");
		
		JLabel tprLabel = SlickerFactory.instance().createLabel(decimalFormat.format(metrics.truePositiveRate())+"   ");
		JLabel tnrLabel = SlickerFactory.instance().createLabel(decimalFormat.format(metrics.trueNegativeRate())+"   ");
		JLabel precisionLabel = SlickerFactory.instance().createLabel(decimalFormat.format(metrics.precision())+"            ");
		JLabel f1ScoreLabel = SlickerFactory.instance().createLabel(decimalFormat.format(metrics.getF1Score())+"         ");
		
		metricsPanelLayout.setPosition(noTPLabel, 0, 0);
		metricsPanel.add(noTPLabel);
		
		metricsPanelLayout.setPosition(noFPLabel, 1, 0);
		metricsPanel.add(noFPLabel);
		
		metricsPanelLayout.setPosition(noTNLabel, 2, 0);
		metricsPanel.add(noTNLabel);
		
		metricsPanelLayout.setPosition(noFNLabel, 3, 0);
		metricsPanel.add(noFNLabel);
		
		metricsPanelLayout.setPosition(tprLabel, 4, 0);
		metricsPanel.add(tprLabel);
		
		metricsPanelLayout.setPosition(tnrLabel, 5, 0);
		metricsPanel.add(tnrLabel);
		
		metricsPanelLayout.setPosition(precisionLabel, 6, 0);
		metricsPanel.add(precisionLabel);
		
		metricsPanelLayout.setPosition(f1ScoreLabel, 7, 0);
		metricsPanel.add(f1ScoreLabel);
		
		
	}
	
	public boolean isSelected(){
		return activeCheckBox.isSelected();
	}
	
	public String getRule(){
		return rule;
	}
	
	public String getDecodedRule(){
		return decodedRule;
	}
	
	public void setSelected(boolean isSelected){
		activeCheckBox.setSelected(isSelected);
	}

}
