/*
 * File: $URL: svn+ssh://chimay/home/svn/VkGraph-showcase/VkGraph-showcase/src/main/java/fr.vekia.tools.showcase.vkgraph/client/showcase/application/components/menu/groups/plots/AdvancedOptionsPlotTree.java $
 * $Id: AdvancedOptionsPlotTree.java 45 2012-09-07 07:35:41Z svandecappelle $
 * Licence MIT
 * 
 * Last change:
 * $Date: 2012-09-07 09:35:41 +0200 (ven., 07 sept. 2012) $
 * $Author: svandecappelle $
 */
package fr.vekia.tools.showcase.vkgraph.client.showcase.application.components.menu.groups.plots;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import fr.vekia.tools.showcase.vkgraph.client.showcase.application.components.menu.AbstractShowcaseTreeMenuItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.AxisRotatedExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.BuildYoursExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.CalendarExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.ConfidanceBandsExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.DraggingPointsExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.DualValuesPlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.DualValuesPlotWithOptionsExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.SingleValueWithDrawPlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.ThemingPlotExempleItem;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 27 avr. 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
public class AdvancedOptionsPlotTree extends AbstractShowcaseTreeMenuItem {

    /**
     * Default constructor
     * 
     */
    public AdvancedOptionsPlotTree() {
	super("Advanced options");
    }

    @Override
    public boolean hasChildren() {
	return true;
    }

    @Override
    public List<AbstractShowcaseTreeMenuItem> getChildrenItems() {
	List<AbstractShowcaseTreeMenuItem> childrens = new ArrayList<AbstractShowcaseTreeMenuItem>();
	childrens.add(new SingleValueWithDrawPlotExempleItem("Draw lines plot"));
	childrens.add(new CalendarExempleItem("Calendar plot"));
	childrens.add(new DualValuesPlotExempleItem("Dual values plot"));
	childrens.add(new DualValuesPlotWithOptionsExempleItem("Dual values plot with options"));
	childrens.add(new AxisRotatedExempleItem("Axis Labels and Rotated Text"));
	childrens.add(new ConfidanceBandsExempleItem("Error Bands and Confidence Intervals"));
	childrens.add(new DraggingPointsExempleItem("Dragging plot"));
	childrens.add(new ThemingPlotExempleItem("Theming plot"));
	childrens.add(new BuildYoursExempleItem("Build your own"));
	
	return childrens;
    }

    @Override
    public boolean hasScreen() {
	return false;
    }

    @Override
    public IsWidget getScreen() {
	return null;
    }

}