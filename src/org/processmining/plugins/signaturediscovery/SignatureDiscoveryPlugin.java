//Om Ganesayanamaha
package org.processmining.plugins.signaturediscovery;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.plugins.signaturediscovery.ui.SignatureDiscoveryUI;
import org.processmining.plugins.signaturediscovery.ui.SignaturePatternsFrame;

import tool.DatasetWriter;
import tool.SelectionDialog;

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

//@Plugin(name = "Signature Discovery", parameterLabels = {"Log"}, returnLabels = {"Signature Patterns"}, returnTypes = {SignaturePatternsFrame.class}, userAccessible = true)
@Plugin(name = "Sequential Pattern Extraction", parameterLabels = {"Log"}, returnLabels = {"Business Process Sequential Pattern Minining"}, returnTypes = {SignaturePatternsFrame.class}, userAccessible = true)
public class SignatureDiscoveryPlugin {
	//@UITopiaVariant(affiliation = UITopiaVariant.EHV, author = "R.P. Jagadeesh Chandra 'JC' Bose", email = "j.c.b.rantham.prabhakara@tue.nl")
	@UITopiaVariant(affiliation = UITopiaVariant.EHV, author = "Based on Signature Discovery plug-in and Discriminative Pattern", email = "")
	@PluginVariant(variantLabel = "Select options to use", requiredParameterLabels = { 0 })
	public static SignaturePatternsFrame main(UIPluginContext context, final XLog log) {
		// Set the look at feel for the alignment frame
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		SignaturePatternsFrame frame=null;
		
		SelectionDialog dialog = new SelectionDialog();
		
		/*
		 * When select discriminative pattern
		 */
		if (dialog.getSelection() == 1) {
			dialog.setVisible(false);
			dialog.dispose();
			
			String folder = JOptionPane.showInputDialog("Please input folder path to CSV sample files. It is also where dataset files will be created.", 
															"M:\\SIGKDD09\\Suncorp");
			if (folder != null) {
				System.out.println(folder);
				
				Boolean errorHappen = false;
				
				try {
					DatasetWriter writer = new DatasetWriter(folder, log);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorHappen = true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorHappen = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorHappen = true;
				}
				
				if (errorHappen) {
					JOptionPane.showMessageDialog(new JFrame(), "Error happened, please check Java console and folder " + folder);
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Datasets have been created at " + folder);
				}
			}
			
			context.getFutureResult(0).cancel(true);
			context.getFutureResult(1).cancel(true);
			
			
		/*
		 * When select sequential patterns generation	
		 */
		} else if (dialog.getSelection() == 0) {
			SignatureDiscoveryUI signatureDiscoveryUI = new SignatureDiscoveryUI(context, true);
			frame = signatureDiscoveryUI.discover(log);
		
		/*
		 * When select to run signature discovery plug-in
		 */
		} else if (dialog.getSelection() == 2) {
			SignatureDiscoveryUI signatureDiscoveryUI = new SignatureDiscoveryUI(context, false);
			frame = signatureDiscoveryUI.discover(log);
		} 
		
		
		
		return frame;

		/*
		 * Bruce: Comment out this
		 */
//		SignatureDiscoveryUI signatureDiscoveryUI = new SignatureDiscoveryUI(context);
//		frame = signatureDiscoveryUI.discover(log);		
	}
}
