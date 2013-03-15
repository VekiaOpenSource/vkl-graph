/*
 * File: $URL$
 * $Id$
 * Copyright: Vekia
 *
 * Last change:
 * $Date$
 * $Author$
 */
package fr.vekia.gquery.client.showcase.activities;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import fr.vekia.gquery.client.showcase.activities.places.MenuPlace;
import fr.vekia.gquery.client.showcase.activities.places.PresentationPlace;

/**
 * @author svandecappelle
 * @since Dec 11, 2012. VklGraph version 1.2
 * @version 2.1
 * 
 *          {@inheritDoc}
 */
public class MyActivityMapper implements ActivityMapper {

    @Override
    public Activity getActivity(Place place) {
	// This is begging for GIN
	if (place instanceof MenuPlace)
	    return new MenuActivity((MenuPlace) place);
	else if (place instanceof PresentationPlace)
	    return new PresentationActivity(place);
	return null;
    }

}