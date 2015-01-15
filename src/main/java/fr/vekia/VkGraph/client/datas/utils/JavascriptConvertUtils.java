/*
 * File: $URL: https://vklgraph.googlecode.com/svn/trunk/src/main/java/fr/vekia/VkGraph/client/datas/utils/JavascriptConvertUtils.java $
 * $Id: JavascriptConvertUtils.java 37 2012-09-07 07:35:08Z steeve.vandecappelle@gmail.com $
 * Licence MIT
 * 
 * Last change:
 * $Date: 2012-09-07 09:35:08 +0200 (ven., 07 sept. 2012) $
 * $Author: steeve.vandecappelle@gmail.com $
 */
package fr.vekia.VkGraph.client.datas.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import fr.vekia.VkGraph.client.datas.OptionSerie;
import fr.vekia.VkGraph.client.datas.SeriesData;
import fr.vekia.VkGraph.client.options.FunctionOption;
import fr.vekia.VkGraph.client.options.SubOption;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 25 mai 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc}
 */
public final class JavascriptConvertUtils {

	private JavascriptConvertUtils() {
	}

	/**
	 * Convert the List of string in JsonArrayString representation.
	 * 
	 * @param arg
	 *            the list of options.
	 * @return a JSON cast string value.
	 */
	public static String optionArrayStringToString(List<?> arg) {
		JSONArray arrayData = new JSONArray();
		int i = 0;
		for (Object optionData : arg) {
			if (optionData instanceof Integer) {
				arrayData.set(i, new JSONNumber((Integer) optionData));
			} else if (optionData instanceof Double) {
				arrayData.set(i, new JSONNumber(((Double) optionData)));
			} else if (optionData instanceof Float) {
				arrayData.set(i, new JSONNumber(((Float) optionData)));
			} else if (optionData instanceof Number) {
				arrayData.set(i, new JSONNumber(((Number) optionData).doubleValue()));
			} else {
				arrayData.set(i, new JSONString(optionData.toString()));
			}
			i += 1;
		}
		return arg.toString();
	}

	/**
	 * Convert the chart data series into a JSONArray used to build the chart.
	 * 
	 * @param series
	 *            the data series
	 * @return a JSON value.
	 */
	public static JSONArray convert(SeriesData series) {
		JSONArray arrayData = new JSONArray();
		int i = 0;
		for (OptionSerie optionsSeries : series.getSeriesLinked()) {
			arrayData.set(i, convert(optionsSeries));
			i += 1;
		}
		return arrayData;
	}

	/**
	 * @param optionsSeries
	 * @return
	 */
	private static JSONValue convert(OptionSerie optionsSeries) {
		JSONObject objectJson = new JSONObject();

		for (Entry<SubOption, String> optionsEntry : optionsSeries.getOptionsMapped().entrySet()) {
			objectJson.put(optionsEntry.getKey().name(), getJsonObject(optionsEntry.getValue()));
		}

		for (Entry<SubOption, Map<SubOption, String>> subOptionsEntry : optionsSeries.getSubSubOptionsMapped().entrySet()) {
			JSONObject objectSubOptionJson = new JSONObject();
			for (Entry<SubOption, String> subSubOptionEntry : subOptionsEntry.getValue().entrySet()) {
				objectSubOptionJson.put(subSubOptionEntry.getKey().name(), getJsonObject(subSubOptionEntry.getValue()));
			}
			objectJson.put(subOptionsEntry.getKey().name(), objectSubOptionJson);

		}
		for (Entry<SubOption, Map<SubOption, JSONValue>> subOptionsEntry : optionsSeries.getSubSubOptionsMappedWithJson().entrySet()) {

			if (objectJson.containsKey(subOptionsEntry.getKey().name())) {
				JSONObject existingJson = (JSONObject) objectJson.get(subOptionsEntry.getKey().name());
				for (Entry<SubOption, JSONValue> subSubOptionEntry : subOptionsEntry.getValue().entrySet()) {
					existingJson.put(subSubOptionEntry.getKey().name(), subSubOptionEntry.getValue());
				}

			} else {
				JSONObject objectSubOptionJson = new JSONObject();
				for (Entry<SubOption, JSONValue> subSubOptionEntry : subOptionsEntry.getValue().entrySet()) {
					objectSubOptionJson.put(subSubOptionEntry.getKey().name(), subSubOptionEntry.getValue());
				}

				objectJson.put(subOptionsEntry.getKey().name(), objectSubOptionJson);
			}

		}

		return objectJson;
	}

	/**
	 * Get the JSONValue for a string object.
	 * 
	 * @param value
	 *            the value to convert.
	 * @return the JSON converted.
	 */
	private static JSONValue getJsonObject(String value) {

		JSONValue valueJso;
		if (value.startsWith("$.jqplot")) {
			valueJso = new JSONObject(eval(value));
		} else {
			if (value.startsWith("'")) {
				valueJso = new JSONString(value);
			} else if (value.startsWith("[")) {
				JSONArray arrayData = new JSONArray();
				valueJso = arrayData;
			} else {
				try {
					valueJso = JSONParser.parseLenient(value);
				} catch (JSONException e) {
					valueJso = new JSONString(value);
				}
			}
		}

		return valueJso;
	}

	/**
	 * Evaluate jQplot renderer
	 * 
	 * @param value
	 *            the renderer JS string variable name value
	 * @return the Renderer JavaScript object.
	 */
	private static JavaScriptObject eval(String value) {
		return RendererFactory.getRendererInstance(value);
	}

	public static JavaScriptObject toFunction(FunctionOption functionOption) {
		return convertToJavascriptFunction(functionOption);
	}
	
	// @formatter:off
	public static native JavaScriptObject convertToJavascriptFunction(FunctionOption functionOption)/*-{
		return function (){
			return functionOption.@fr.vekia.VkGraph.client.options.FunctionOption::execute([Ljava/lang/Object;)(arguments);
		};
	}-*/;
	// @formatter:on
}
