/*
 * File: $URL: svn+ssh://chimay/home/svn/VkGraph-showcase/VkGraph-showcase/src/main/java/fr.vekia.tools.showcase.vkgraph/client/showcase/application/components/menu/groups/plots/AllPlotsTree.java $
 * $Id: AllPlotsTree.java 45 2012-09-07 07:35:41Z svandecappelle $
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
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.BarChartExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.BezierCurveExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.BlockPlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.BubblePlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.DualValuesPlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.GaugeExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.LineChartExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.PyramidPlotExempleItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.simple.VerticalBarChartExempleItem;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 27 avr. 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
public class AllPlotsTree extends AbstractShowcaseTreeMenuItem {

    /**
     * Default constructor
     * 
     */
    public AllPlotsTree() {
	super("Available plots");
    }

    @Override
    public boolean hasChildren() {
	return true;
    }

    @Override
    public List<AbstractShowcaseTreeMenuItem> getChildrenItems() {
	List<AbstractShowcaseTreeMenuItem> childrens = new ArrayList<AbstractShowcaseTreeMenuItem>();
	childrens.add(new LineChartExempleItem("LineChart plots"));
	childrens.add(new DualValuesPlotExempleItem("Dual values plot"));
	childrens.add(new VerticalBarChartExempleItem("Vertical and Horizontal Bar Charts"));
	childrens.add(new BarChartExempleItem("Bar Charts"));
	childrens.add(new BezierCurveExempleItem("Bezier Curve Plots"));
	childrens.add(new BlockPlotExempleItem("Block Plots"));
	childrens.add(new BubblePlotExempleItem("Bubble Plots"));
	childrens.add(new PyramidPlotExempleItem("Pyramid plot"));
	childrens.add(new GaugeExempleItem("Gauge plot"));
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