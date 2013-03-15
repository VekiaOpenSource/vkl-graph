/*
 * File: $URL: svn+ssh://chimay/home/svn/VkGraph-showcase/VkGraph-showcase/src/main/java/fr/vekia/gquery/client/showcase/application/components/menu/groups/plots/items/LineChartExempleItem.java $
 * $Id: LineChartExempleItem.java 45 2012-09-07 07:35:41Z svandecappelle $
 * Licence MIT
 * 
 * Last change:
 * $Date: 2012-09-07 09:35:41 +0200 (ven., 07 sept. 2012) $
 * $Author: svandecappelle $
 */
package fr.vekia.gquery.client.showcase.application.demo.items.simple;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import fr.vekia.gquery.client.showcase.application.components.menu.AbstractShowcaseTreeMenuItem;
import fr.vekia.gquery.client.showcase.application.demo.screens.simple.SimplePlotScreen;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 27 avr. 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
public class LineChartExempleItem extends AbstractShowcaseTreeMenuItem {

    /**
     * Default constructor
     * 
     * @param text
     */
    public LineChartExempleItem(String text) {
	super(text);
    }

    @Override
    public boolean hasChildren() {
	return false;
    }

    @Override
    public List<AbstractShowcaseTreeMenuItem> getChildrenItems() {
	return null;
    }

    @Override
    public boolean hasScreen() {
	return true;
    }

    @Override
    public IsWidget getScreen() {
	return new SimplePlotScreen();
    }

}