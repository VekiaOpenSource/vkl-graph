/*
 * File: $URL: svn+ssh://chimay/home/svn/VkGraph-showcase/VkGraph-showcase/src/main/java/fr.vekia.tools.showcase.vkgraph/client/showcase/application/components/menu/groups/plots/screens/GaugePlotScreen.java $
 * $Id: GaugePlotScreen.java 52 2012-09-26 15:26:35Z svandecappelle $
 * Licence MIT
 *
 * Last change:
 * $Date: 2012-09-26 17:26:35 +0200 (mer., 26 sept. 2012) $
 * $Author: svandecappelle $
 */
package fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.screens.simple;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.SimplePanel;

import fr.vekia.VkGraph.client.charts.Gauge;
import fr.vekia.VkGraph.client.options.ChartOption;
import fr.vekia.VkGraph.client.options.SubOption;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 21 août 2012. VklGraph version 1.2
 * @version 2.1
 * 
 *          {@inheritDoc}
 */
public class GaugePlotScreen extends SimplePanel {

	/**
	 * Default constructor
	 * 
	 */
	public GaugePlotScreen() {
		Gauge gaugeExemple = new Gauge();
		gaugeExemple.setData(34);
		gaugeExemple.toAbstractChart().setOption(ChartOption.title,
				"Temperature");
		gaugeExemple.toAbstractChart().setOption(ChartOption.seriesDefaults,
				SubOption.rendererOptions, SubOption.label, "°C");

		List<Number> intervals = new ArrayList<Number>();
		intervals.add(30);
		intervals.add(70);
		intervals.add(100);
		//
		List<String> intervalColors = new ArrayList<String>();
		intervalColors.add("rgba(0,190,0,0.7)");
		intervalColors.add("rgba(190,190,0,0.7)");
		intervalColors.add("rgba(190,0,0,0.7)");

		gaugeExemple.toAbstractChart().setOption(ChartOption.seriesDefaults,
				SubOption.rendererOptions, SubOption.intervals, intervals);
		gaugeExemple.toAbstractChart().setOption(ChartOption.seriesDefaults,
				SubOption.rendererOptions, SubOption.intervalColors,
				intervalColors);
		gaugeExemple.toAbstractChart().setNumberOption(
				ChartOption.seriesDefaults, SubOption.rendererOptions,
				SubOption.min, 0);
		gaugeExemple.toAbstractChart().setNumberOption(
				ChartOption.seriesDefaults, SubOption.rendererOptions,
				SubOption.max, 100);
		gaugeExemple.toAbstractChart().setOption(ChartOption.seriesDefaults,
				SubOption.rendererOptions, SubOption.labelPosition, "bottom");
		gaugeExemple.toAbstractChart().setNumberOption(
				ChartOption.seriesDefaults, SubOption.rendererOptions,
				SubOption.labelHeightAdjust, 18);
		gaugeExemple.toAbstractChart().setNumberOption(
				ChartOption.seriesDefaults, SubOption.rendererOptions,
				SubOption.intervalOuterRadius, 185);

		// gaugeExemple.toAbstractChart().setBooleanOption(ChartOption.seriesDefaults,
		// SubOption.rendererOptions, SubOption.showTickLabels, false);

		setWidget(gaugeExemple);
	}
}
