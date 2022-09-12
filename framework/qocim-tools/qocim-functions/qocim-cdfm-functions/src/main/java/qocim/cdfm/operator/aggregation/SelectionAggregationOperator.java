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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import qocim.cdfm.function.ICDFMOperator;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public abstract class SelectionAggregationOperator extends ArithmeticAggregationOperator {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the parameter to select the QoC metric definitions to
	 * aggregate.
	 */
	public final static String PARAM_QOC_DEFINITION_ID_SELECTION = "qoc_definition_id";
	/**
	 * The separator of two value of parameter.
	 */
	public final static String ELEMENT_SEPARATOR = ";";
	/**
	 * A key value to aggregate all elements of context observable or QoC metric
	 * definition.
	 */
	public final static String KEYWORD_ALL_ELEMENTS = "ALL";

	// # # # # # PROTECTED VARIABLES # # # # #

	/**
	 * The list of id of QoC metric definition used to aggregate QoC meta-data.
	 */
	protected final LinkedList<String> list_qoCMetricDefinitionId;

	// # # # # # CONSTRUCTORS # # # # #

	protected SelectionAggregationOperator() {
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCMetricDefinitionId = new LinkedList<String>();
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public ICDFMOperator setParameters(final Map<String, String> _map_parameter) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "SelectionOperator.setConfiguration(Map<String, String>): the argument _map_parameter is null.";
			ConstraintChecker.notNull(_map_parameter, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		setListQoCMetricDefinitionId(
				splitConfigurationParameter(_map_parameter.get(PARAM_QOC_DEFINITION_ID_SELECTION)));
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The result of the method: a copy of <i>map_configuration</i>.
		 */
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_mapParameter.put(PARAM_QOC_DEFINITION_ID_SELECTION,
				concatConfigurationParameter(list_qoCMetricDefinitionId));
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}

	/**
	 * This method sets the list of the id of the QoC metric definition used to
	 * aggregate the QoC meta-data.
	 *
	 * @param _list_qoCMetricDefinitionId
	 *            The list of id of the QoC metric definition.
	 * @return this.
	 */
	public SelectionAggregationOperator setListQoCMetricDefinitionId(final List<String> _list_qoCMetricDefinitionId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "SelectionAggregationOperator setListQoCMetricDefinitionId(List<String>): the argument _list_observableUri is empty";
			ConstraintChecker.notNull(_list_qoCMetricDefinitionId, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCMetricDefinitionId.clear();
		for (final String loop_qoCMetricDefinitionId : _list_qoCMetricDefinitionId) {
			list_qoCMetricDefinitionId.add(new String(loop_qoCMetricDefinitionId));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	// # # # # # PROTECTED METHODS # # # # #

	/**
	 * This method analyze the value of a configuration parameter.
	 *
	 * @param _configurationValue
	 *            The configuration value.
	 * @return The list of context observable URI or QoC metric definition id to
	 *         aggregate.
	 */
	protected List<String> splitConfigurationParameter(final String _configurationValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The resulting list of configuration parameter.
		 */
		final List<String> ret_listParameter = new ArrayList<String>();
		// - - - - - CORE OF THE METHOD - - - - -
		if (_configurationValue != null) {
			for (final String loop_configuration : _configurationValue.split(ELEMENT_SEPARATOR)) {
				ret_listParameter.add(loop_configuration);
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_listParameter;
	}

	/**
	 * This method concat a list of configuration parameter into one String.
	 *
	 * @param _list_configurationValue
	 *            The list of parameter to concat.
	 * @return The result of the concatenation.
	 */
	protected String concatConfigurationParameter(final LinkedList<String> _list_configurationValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The resulting parameter.
		 */
		String ret_configurationParameter = "";
		// - - - - - CORE OF THE METHOD - - - - -
		for (final String loop_parameter : _list_configurationValue) {
			ret_configurationParameter += loop_parameter;
			if (!loop_parameter.equals(_list_configurationValue.getLast())) {
				ret_configurationParameter += ELEMENT_SEPARATOR;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_configurationParameter;
	}
}
