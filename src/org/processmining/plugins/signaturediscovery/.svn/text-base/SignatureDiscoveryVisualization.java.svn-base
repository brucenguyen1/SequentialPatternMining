package org.processmining.plugins.signaturediscovery;

import javax.swing.JComponent;

import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.plugins.signaturediscovery.ui.SignaturePatternsFrame;

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

@Plugin(name = "Signature Discovery Visualization", 
		returnLabels = { "Visualizion of Signature Patterns" }, 
		returnTypes = { JComponent.class }, 
		parameterLabels = {"SignaturePatternsFrame"},
		userAccessible = false)
@Visualizer
public class SignatureDiscoveryVisualization {
	@PluginVariant(requiredParameterLabels = {0})
	public JComponent visualize(PluginContext context, 
			SignaturePatternsFrame frame){
		return frame;
	}
}
