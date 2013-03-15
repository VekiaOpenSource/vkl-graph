/*
 * File: $URL: https://vklgraph.googlecode.com/svn/trunk/src/main/java/fr/vekia/VkGraph/client/datas/DataGraph.java $
 * $Id: DataGraph.java 25 2012-08-16 14:04:01Z steeve.vandecappelle@gmail.com $
 * Licence MIT
 * 
 * Last change:
 * $Date: 2012-08-16 16:04:01 +0200 (jeu., 16 août 2012) $
 * $Author: steeve.vandecappelle@gmail.com $
 */
package fr.vekia.VkGraph.client.datas;

import java.util.List;

/**
 * @author Steeve Vandecappelle (SVA)
 * @since 31 mai 2012. GWTQuery Vekia Showcase
 * @version 1.0
 * 
 *          {@inheritDoc} An utility class to load data on chart.
 */
public class DataGraph<T> {

    private List<T> data;
    private boolean multiple;

    /**
     * Set the DataGraph value to convert.
     * 
     * @param convertData
     *            data to convert.
     */
    public void setValue(List<T> convertData) {
	this.data = convertData;
    }

    /**
     * Get the data.
     * 
     * @return the data.
     */
    public List<T> getData() {
	return data;
    }

    /**
     * Set the data is multi-dimension.
     * 
     * @param isMultiple
     *            <code>true</code> to set multi.
     */
    public void setMultiple(boolean isMultiple) {
	this.multiple = isMultiple;
    }

    /**
     * Get the data is multi-dimension.
     * 
     * @return <code>true</code> if data are muli dimension.
     */
    public boolean isMultiple() {
	return multiple;
    }

}
