/**
 * This file is part of the QoCIM middleware.
 *
 * Copyright (C) 2014 IRIT, Télécom SudParis
 *
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 *
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package collector.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextEntity;
import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;

/**
 * This class is used to create a random context observation. To help the
 * simulation of the freshness measurement, the class waits before providing its
 * context observation (see the end of the method
 * <i>getRandomContextObservation</i>).
 *
 * @author Pierrick MARIE
 */
public class RandomContextObservation {

    public static Double getRandomValue(final Integer minValue, final Integer maxValue) {

	return Math.random() * (maxValue - minValue) + minValue;
    }

    /**
     * The facade used to create the context reports.
     */
    private final ContextDataModelFacade facade;

    /**
     * Minimal random value of the context information.
     */
    private static final int MIN_RANDOM_OBSERVATION_VALUE = 1;

    /**
     * Maximal random value of the context information.
     */
    private static final int MAX_RANDOM_OBSERVATION_VALUE = 5000;

    public RandomContextObservation(final ContextDataModelFacade facade) {
	this.facade = facade;
    }

    public ContextObservation<Double> getRandomContextObservation() {

	ContextEntity userEntity;
	ContextObservable observable;
	URI entityUri;
	URI observableUri;
	ContextObservation<Double> ret_contextObservation = null;
	final List<ContextEntity> entities = new ArrayList<ContextEntity>();
	final String uriPath = "mucontext://localhost:3000";

	try {
	    entityUri = new URI(uriPath + "/user/Somebody");
	    observableUri = new URI(uriPath + "/Something");
	    userEntity = facade.createContextEntity("Somebody", entityUri);
	    observable = facade.createContextObservable("Something", observableUri, userEntity);
	    ret_contextObservation = (ContextObservation<Double>) facade.<Double> createContextObservation("o",
		    getRandomValue(MIN_RANDOM_OBSERVATION_VALUE, MAX_RANDOM_OBSERVATION_VALUE), observable);

	    entities.add(userEntity);

	} catch (final URISyntaxException e) {
	    e.printStackTrace();
	}

	return ret_contextObservation;
    }

}
