/*
 * File: $URL: svn+ssh://chimay/home/svn/VkGraph-showcase/VkGraph-showcase/src/main/java/fr.vekia.tools.showcase.vkgraph/client/showcase/application/components/menu/groups/interactive/items/AreaChartInteractiveItem.java $
 * $Id: AreaChartInteractiveItem.java 37 2012-08-22 16:29:14Z svandecappelle $
 * Licence MIT
 *
 * Last change:
 * $Date: 2012-08-22 18:29:14 +0200 (mer., 22 août 2012) $
 * $Author: svandecappelle $
 */
package fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.items.interactives;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

import fr.vekia.tools.showcase.vkgraph.client.showcase.application.components.menu.AbstractShowcaseTreeMenuItem;
import fr.vekia.tools.showcase.vkgraph.client.showcase.application.demo.screens.interactives.AreaChartInteractiveWidgetScreen;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 17 août 2012. VklGraph version 1.2
 * @version 2.1
 * 
 *          {@inheritDoc}
 */
public class AreaChartInteractiveItem extends AbstractShowcaseTreeMenuItem {

	/**
	 * Default constructor
	 * 
	 * @param text
	 */
	public AreaChartInteractiveItem(String text) {
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
		return new AreaChartInteractiveWidgetScreen();
	}

}