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
package qocim.cdfm.operator.aggregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mucontext.datamodel.context.ContextObservable;
import mucontext.datamodel.context.ContextObservation;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;

/**
 * AggregatorContextDataFacade is a facade to store all the useful information
 * required to aggregate context information and QoC meta-data.
 *
 * @author Pierrick MARIE
 */
class AggregatorContextDataFacade {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The context observable that reference all context observations in
	 * <i>list_contextObservation</i>.
	 */
	private final ContextObservable contextObservable;
	/**
	 * The list of context observation that will be aggregated.
	 */
	private final List<ContextObservation<?>> list_contextObservation;
	/**
	 * A map of <b>QoCMetricDefinition</b> <b>List QoCMetricValue</b>. The list
	 * <b>QoCMetricValue</b> are gather by the <b>QoCMetricDefinition</b> that
	 * have been used to create them.
	 */
	private final Map<QoCMetricDefinition, List<QoCMetricValue>> map_qoCMetricValue;

	// # # # # # CONSTRUCTORS # # # # #

	protected AggregatorContextDataFacade(final ContextObservable _contextObservable) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		contextObservable = _contextObservable;
		list_contextObservation = new ArrayList<ContextObservation<?>>();
		map_qoCMetricValue = new HashMap<QoCMetricDefinition, List<QoCMetricValue>>();
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * @return The context observable.
	 */
	protected ContextObservable contextObservable() {
		// - - - - - RETURN STATEMENT - - - - -
		return contextObservable;
	}

	/**
	 * @return The map of QoC metric definition, list of QoC metric value.
	 */
	protected Map<QoCMetricDefinition, List<QoCMetricValue>> mapQoCDefinitionValue() {
		// - - - - - RETURN STATEMENT - - - - -
		return map_qoCMetricValue;
	}

	/**
	 * @return The list of context observation.
	 */
	protected List<ContextObservation<?>> listContextObservation() {
		// - - - - - RETURN STATEMENT - - - - -
		return list_contextObservation;
	}

	/**
	 * This method is used to add a new context observation
	 *
	 * @param _contextObservation
	 *            The context observation to add.
	 * @return this.
	 */
	protected AggregatorContextDataFacade addContextObservation(final ContextObservation<?> _contextObservation) {
		// - - - - - CORE OF THE METHOD - - - - -
		list_contextObservation.add(_contextObservation);
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	/**
	 * This method is used to add a new QoC metric value.
	 *
	 * @param _qoCMetricDefinition
	 *            The QoC metric definition used to produce the
	 *            <i>_qoCMetricValue</i>.
	 * @param _qoCMetricValue
	 *            The QoC metric value to add.
	 * @return this.
	 */
	protected AggregatorContextDataFacade addQoCMetricValue(final QoCMetricDefinition _qoCMetricDefinition,
			final QoCMetricValue _qoCMetricValue) {
		// - - - - - CORE OF THE METHOD - - - - -
		if (!map_qoCMetricValue.containsKey(_qoCMetricDefinition)) {
			map_qoCMetricValue.put(_qoCMetricDefinition, new ArrayList<QoCMetricValue>());
		}
		map_qoCMetricValue.get(_qoCMetricDefinition).add(_qoCMetricValue);
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public boolean equals(final Object _comparable) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		AggregatorContextDataFacade contextDataFacade;
		// - - - - - CORE OF THE METHOD - - - - -
		if (_comparable != null && _comparable instanceof AggregatorContextDataFacade) {
			contextDataFacade = (AggregatorContextDataFacade) _comparable;
			return contextObservable.uri.equals(contextDataFacade.contextObservable.uri);
		}
		// - - - - - RETURN STATEMENT - - - - -
		return false;
	}
}
