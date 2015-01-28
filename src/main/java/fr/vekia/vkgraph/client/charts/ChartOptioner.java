/*
 * File: $URL: https://vklgraph.googlecode.com/svn/trunk/src/main/java/fr/vekia/VkGraph/client/charts/ChartOptioner.java $
 * $Id: ChartOptioner.java 48 2012-09-26 15:37:39Z steeve.vandecappelle@gmail.com $
 * Licence MIT
 *
 * Last change:
 * $Date: 2012-09-26 17:37:39 +0200 (mer., 26 sept. 2012) $
 * $Author: steeve.vandecappelle@gmail.com $
 */
package fr.vekia.vkgraph.client.charts;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import fr.vekia.vkgraph.client.options.ChartOption;
import fr.vekia.vkgraph.client.options.SubOption;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 3 sept. 2012. VklGraph version 1.2
 * @version 2.1
 * 
 *          {@inheritDoc}
 */
public class ChartOptioner {

	// options {option: {sub-option : value}}
	private Map<ChartOption, Map<SubOption, String>> optionsMapped;
	// options {option: {sub-option : {sub-sub-option: value}}}
	private Map<ChartOption, Map<SubOption, Map<SubOption, String>>> subSubOptionsMapped;
	// options {option: {sub-option : {sub-sub-option: value}}}
	private Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, String>>>> subSubSubOptionsMapped;

	// in javascript
	// options {option: {sub-option : {sub-sub-option: value}}}
	private Map<ChartOption, Map<SubOption, Map<SubOption, JavaScriptObject>>> subSubOptionsMappedInJavascript;
	private Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, JavaScriptObject>>>> subSubSubOptionsMappedInJavascript;

	/**
	 * @return the optionsMapped
	 */
	public Map<ChartOption, Map<SubOption, String>> getOptionsMapped() {
		return optionsMapped;
	}

	/**
	 * @return the subSubOptionsMapped
	 */
	public Map<ChartOption, Map<SubOption, Map<SubOption, String>>> getSubSubOptionsMapped() {
		return subSubOptionsMapped;
	}

	/**
	 * @return the subSubSubOptionsMapped
	 */
	public Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, String>>>> getSubSubSubOptionsMapped() {
		return subSubSubOptionsMapped;
	}

	/**
	 * @param optionsMapped
	 *            the optionsMapped to set
	 */
	protected void setOptionsMapped(Map<ChartOption, Map<SubOption, String>> optionsMapped) {
		this.optionsMapped = optionsMapped;
	}

	/**
	 * @param subSubOptionsMapped
	 *            the subSubOptionsMapped to set
	 */
	protected void setSubSubOptionsMapped(Map<ChartOption, Map<SubOption, Map<SubOption, String>>> subSubOptionsMapped) {
		this.subSubOptionsMapped = subSubOptionsMapped;
	}

	/**
	 * @param subSubSubOptionsMapped
	 *            the subSubSubOptionsMapped to set
	 */
	protected void setSubSubSubOptionsMapped(Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, String>>>> subSubSubOptionsMapped) {
		this.subSubSubOptionsMapped = subSubSubOptionsMapped;
	}

	protected void setSubSubSubOptionsMappedInJavascript(Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, JavaScriptObject>>>> subSubSubOptionsMappedInJavascript) {
		this.subSubSubOptionsMappedInJavascript = subSubSubOptionsMappedInJavascript;
	}

	protected Map<ChartOption, Map<SubOption, Map<SubOption, Map<SubOption, JavaScriptObject>>>> getSubSubSubOptionsMappedInJavascript() {
		return subSubSubOptionsMappedInJavascript;
	}

	public Map<ChartOption, Map<SubOption, Map<SubOption, JavaScriptObject>>> getSubSubOptionsMappedInJavascript() {
		return subSubOptionsMappedInJavascript;
	}

	public void setSubSubOptionsMappedInJavascript(Map<ChartOption, Map<SubOption, Map<SubOption, JavaScriptObject>>> subSubOptionsMappedInJavascript) {
		this.subSubOptionsMappedInJavascript = subSubOptionsMappedInJavascript;
	}

	/**
     * 
     */
	public final static String getStringNumberValue(Number value, NumberType typeOfValue) {
		String output = null;
		switch (typeOfValue) {
		case DOUBLE:
			output = Double.toString(value.doubleValue());
			break;
		case INT:
			output = Integer.toString(value.intValue());
			break;
		case FLOAT:
			output = Float.toString(value.floatValue());
			break;
		case LONG:
			output = Long.toString(value.longValue());
			break;
		case SHORT:
			output = Short.toString(value.shortValue());
			break;
		default:
			break;

		}
		return output;
	}
}