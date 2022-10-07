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
package qocim.aggregation.operator;

@Deprecated
public abstract class SelectionAggregationOperator extends ArithmeticAggregationOperator {
//
//	// # # # # # CONSTANTS # # # # #
//
//	/**
//	 * The name of the parameter to select the context observables to aggregate.
//	 */
//	public final static String PARAM_CONTEXT_OBSERVABLE_URI_SELECTION = "observable_uri";
//	/**
//	 * The name of the parameter to select the QoC metric definitions to
//	 * aggregate.
//	 */
//	public final static String PARAM_QOC_DEFINITION_ID_SELECTION = "qoc_definition_id";
//	/**
//	 * The separator of two value of parameter.
//	 */
//	public final static String ELEMENT_SEPARATOR = ";";
//	/**
//	 * A key value to aggregate all elements of context observable or QoC metric
//	 * definition.
//	 */
//	public final static String KEYWORD_ALL_ELEMENTS = "ALL";
//
//	// # # # # # PROTECTED VARIABLES # # # # #
//
//	/**
//	 * The list of uri of the observable used to aggregate the informations.
//	 */
//	protected final LinkedList<String> list_contextObservableUri;
//
//	/**
//	 * The list of id of QoC metric definition used to aggregate QoC meta-data.
//	 */
//	protected final LinkedList<String> listQocMetricDefinitionId;
//
//	// # # # # # CONSTRUCTORS # # # # #
//
//	protected SelectionAggregationOperator() {
//		// - - - - - CORE OF THE METHOD - - - - -
//		counter_contextObservationId = 0;
//		counter_contextReportId = 0;
//		information = new ContextDataModelFacade("SelectionOperator - context data model facade");
//		list_contextObservableUri = new LinkedList<String>();
//		listQocMetricDefinitionId = new LinkedList<String>();
//	}
//
//	// # # # # # PUBLIC METHODS # # # # #
//
//	@Override
//	public qocim.cdfm.function.IAgregationOperator setParameters(final Map<String, String> _map_parameter) {
//		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
//		try {
//			final String message = "SelectionOperator.setConfiguration(Map<String, String>): the argument _map_parameter is null.";
//			ConstraintChecker.notNull(_map_parameter, message);
//		} catch (final ConstraintCheckerException e) {
//			return this;
//		}
//		// - - - - - CORE OF THE METHOD - - - - -
//		setListContextObservableUri(
//				splitConfigurationParameter(_map_parameter.get(PARAM_CONTEXT_OBSERVABLE_URI_SELECTION)));
//		setListQoCMetricDefinitionId(
//				splitConfigurationParameter(_map_parameter.get(PARAM_QOC_DEFINITION_ID_SELECTION)));
//		// - - - - - RETURN STATEMENT - - - - -
//		return this;
//	}
//
//	@Override
//	public Map<String, String> parameters() {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The result of the method: a copy of <i>map_configuration</i>.
//		 */
//		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
//		// - - - - - CORE OF THE METHOD - - - - -
//		ret_mapParameter.put(PARAM_CONTEXT_OBSERVABLE_URI_SELECTION,
//				concatConfigurationParameter(list_contextObservableUri));
//		ret_mapParameter.put(PARAM_QOC_DEFINITION_ID_SELECTION,
//				concatConfigurationParameter(listQocMetricDefinitionId));
//		// - - - - - RETURN STATEMENT - - - - -
//		return ret_mapParameter;
//	}
//
//	/**
//	 * This method sets the list of the uri os the observable that are aggregate
//	 * by the operator.
//	 *
//	 * @param list_contextObservableUri
//	 *            The list of the uri.
//	 * @return this.
//	 */
//	public SelectionAggregationOperator setListContextObservableUri(final List<String> list_contextObservableUri) {
//		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
//		try {
//			final String message = "SelectionAggregationOperator setListObservableId(List<String>): the argument list_observableUri is empty";
//			ConstraintChecker.notNull(list_contextObservableUri, message);
//		} catch (final ConstraintCheckerException e) {
//			return this;
//		}
//		// - - - - - CORE OF THE METHOD - - - - -
//		list_contextObservableUri.clear();
//		for (final String loop_observableUri : list_contextObservableUri) {
//			list_contextObservableUri.add(new String(loop_observableUri));
//		}
//		// - - - - - RETURN STATEMENT - - - - -
//		return this;
//	}
//
//	/**
//	 * This method sets the list of the id of the QoC metric definition used to
//	 * aggregate the QoC meta-data.
//	 *
//	 * @param listQocMetricDefinitionId
//	 *            The list of id of the QoC metric definition.
//	 * @return this.
//	 */
//	public SelectionAggregationOperator setListQoCMetricDefinitionId(final List<String> listQocMetricDefinitionId) {
//		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
//		try {
//			final String message = "SelectionAggregationOperator setListQoCMetricDefinitionId(List<String>): the argument list_observableUri is empty";
//			ConstraintChecker.notNull(listQocMetricDefinitionId, message);
//		} catch (final ConstraintCheckerException e) {
//			return this;
//		}
//		// - - - - - CORE OF THE METHOD - - - - -
//		listQocMetricDefinitionId.clear();
//		for (final String loopqocMetricDefinitionId : listQocMetricDefinitionId) {
//			listQocMetricDefinitionId.add(new String(loopqocMetricDefinitionId));
//		}
//		// - - - - - RETURN STATEMENT - - - - -
//		return this;
//	}
//
//	// # # # # # PROTECTED METHODS # # # # #
//
//	@Override
//	protected Boolean validateInput(final List<ContextReport> _input) {
//		// - - - - - RETURN STATEMENT - - - - -
//		return !_input.isEmpty();
//	}
//
//	@Override
//	protected void parseContextReport(final ContextReport _contextReport,
//			final List<AggregatorContextDataFacade> list_contextData) {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The <b>AggregatorContextDataFacade</b> used to store the information
//		 * for the aggregation.
//		 */
//		AggregatorContextDataFacade aggregationContextData;
//		// - - - - - CORE OF THE METHOD - - - - -
//		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
//			aggregationContextData = parseContextObservation(loop_contextObservation, list_contextData);
//			if (aggregationContextData != null) {
//				parseQoCIndicators(loop_contextObservation, aggregationContextData);
//			}
//		}
//	}
//
//	@Override
//	protected AggregatorContextDataFacade parseContextObservation(final ContextObservation<?> _contextObservation,
//			final List<AggregatorContextDataFacade> list_aggregatorContextFacade) {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The <b>AggregatorContextDataFacade</b> returned by the method.
//		 */
//		AggregatorContextDataFacade ret_aggregatorContextFacade = null;
//		// - - - - - CORE OF THE METHOD - - - - -
//		if (list_contextObservableUri.iterator().next().equals(KEYWORD_ALL_ELEMENTS)
//				|| list_contextObservableUri.contains(_contextObservation.observable.uri.toString())) {
//			ret_aggregatorContextFacade = super.parseContextObservation(_contextObservation,
//					list_aggregatorContextFacade);
//		}
//		// - - - - - RETURN STATEMENT - - - - -
//		return ret_aggregatorContextFacade;
//	}
//
//	@Override
//	protected void parseQoCIndicators(final ContextObservation<?> _contextObservation,
//			final AggregatorContextDataFacade _aggregatorContextFacade) {
//		// - - - - - CORE OF THE METHOD - - - - -
//		for (final QoCIndicator loopqocIndicator : _contextObservation.listQocIndicator) {
//			for (final QoCMetaData loopqocMetaData : QoCIMFacade.createListQoCMetaData(loopqocIndicator)) {
//				if (listQocMetricDefinitionId.iterator().next().equals(KEYWORD_ALL_ELEMENTS)
//						|| listQocMetricDefinitionId.contains(loopqocMetaData.qocMetricDefinitionId())) {
//					_aggregatorContextFacade.addQoCMetricValue(loopqocMetaData.qocMetricDefinition(),
//							loopqocMetaData.qocMetricValue());
//				}
//			}
//		}
//	}
//
//	/**
//	 * This method analyze the value of a configuration parameter.
//	 *
//	 * @param _configurationValue
//	 *            The configuration value.
//	 * @return The list of context observable URI or QoC metric definition id to
//	 *         aggregate.
//	 */
//	protected List<String> splitConfigurationParameter(final String _configurationValue) {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The resulting list of configuration parameter.
//		 */
//		final List<String> retlistParameter = new ArrayList<String>();
//		// - - - - - CORE OF THE METHOD - - - - -
//		if (_configurationValue != null) {
//			for (final String loop_configuration : _configurationValue.split(ELEMENT_SEPARATOR)) {
//				retlistParameter.add(loop_configuration);
//			}
//		}
//		// - - - - - RETURN STATEMENT - - - - -
//		return retlistParameter;
//	}
//
//	/**
//	 * This method concat a list of configuration parameter into one String.
//	 *
//	 * @param list_configurationValue
//	 *            The list of parameter to concat.
//	 * @return The result of the concatenation.
//	 */
//	protected String concatConfigurationParameter(final LinkedList<String> list_configurationValue) {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The resulting parameter.
//		 */
//		String ret_configurationParameter = "";
//		// - - - - - CORE OF THE METHOD - - - - -
//		for (final String loop_parameter : list_configurationValue) {
//			ret_configurationParameter += loop_parameter;
//			if (!loop_parameter.equals(list_configurationValue.getLast())) {
//				ret_configurationParameter += ELEMENT_SEPARATOR;
//			}
//		}
//		// - - - - - RETURN STATEMENT - - - - -
//		return ret_configurationParameter;
//	}
}
