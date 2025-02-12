/**
 * ArealInterpolation - Callback for FastTilePlot button.
 * @author Mary Ann Bitz 
 * @version $Revision$ $Date$
 **/

package anl.verdi.plot.gui.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;		// 2014
import org.apache.logging.log4j.Logger;			// 2014 replacing System.out.println with logger messages

import saf.core.ui.actions.AbstractSAFAction;
import anl.verdi.area.AreaTilePlot;
import anl.verdi.area.target.Target;
import anl.verdi.area.target.TargetCalculator;
import anl.verdi.core.VerdiApplication;
import anl.verdi.data.ArrayReader;
import anl.verdi.data.DataFrame;
import anl.verdi.data.Dataset;
import anl.verdi.data.MPASDataFrameIndex;
import anl.verdi.data.MeshCellInfo;
import anl.verdi.data.MeshDataReader;
import anl.verdi.formula.Formula;
import anl.verdi.plot.data.IMPASDataset;
import anl.verdi.plot.gui.FastAreaTilePlot;
import anl.verdi.plot.gui.PlotPanel;

public class ArealInterpolation extends AbstractSAFAction<VerdiApplication> {

	// FastTilePlot button callback:

	/**
	 * 
	 */
	private static final long serialVersionUID = 5898580138071998181L;
	static final Logger Logger = LogManager.getLogger(ArealInterpolation.class.getName());

	public void actionPerformed( ActionEvent unused ) {

		final VerdiApplication application = workspace.getApplicationMediator();
		if ( application.getProject().getAreaFiles() == null || application.getProject().getAreaFiles().getSize()<=0) {
			application.getGui().showMessage("Areal Interpolation Plot", "No areal files selected! Please select one!");
			return;
		}
		if ( application.getProject().getSelectedFormula() == null) {
			application.getGui().showMessage("Areal Interpolation Plot", "No formula selected! Please select one!");
			return;
		}
		Logger.debug("in ArealInterpolation, ready to check for a formula");
		application.getGui().showBusyCursor();	
		if ( application.getProject().getSelectedFormula() != null ) {
			final DataFrame dataFrame = application.evaluateFormula( Formula.Type.TILE );

			if ( dataFrame != null ) {
				
		    	  List<Dataset> datasets = dataFrame.getDataset();
				
				// calculate the areas 
				TargetCalculator calc = new TargetCalculator();
				
				
				Logger.debug("ready to call TargetCalculator.calculateIntersections");
				
		    	  if (datasets != null && datasets.size() > 0 && datasets.get(0).getClass().getName().toLowerCase().indexOf("mpas") != -1) {
		    		  MeshPlot.performAction(application, dataFrame, calc);
		    		  application.getGui().defaultCursor();
		    		  return;
		    	  }
		    	  
					final FastAreaTilePlot plot = new FastAreaTilePlot(application, dataFrame);
		    	  
				boolean retValue = calc.calculateIntersections(Target.getTargets(),dataFrame,(AreaTilePlot)plot.getTilePlot());
				Logger.debug("back from calculateIntersections, retValue = " + retValue);
				if(!retValue)
				{
					// 2014 added in message dialog to show message to user
					String aMessage = "Problem with areal interpolation calculations. Check if polygons intersect grid cells.";
					JOptionPane.showMessageDialog(null, aMessage, "Areal Interpolation Issue", JOptionPane.WARNING_MESSAGE);
				}
				final PlotPanel panel = new PlotPanel( plot, "ArealInterpolation" );
				application.getGui().addPlot( panel );
				panel.addPlotListener( application );
		        new Thread(
		                new Runnable() {
		                    public void run() {
		                        try {
		                            Thread.sleep(50);
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
//		        				plot.draw();
		        				plot.init();
		        				plot.draw();
		                    }
		                }).start();
			}
		}
		application.getGui().restoreCursor();
	}
}




