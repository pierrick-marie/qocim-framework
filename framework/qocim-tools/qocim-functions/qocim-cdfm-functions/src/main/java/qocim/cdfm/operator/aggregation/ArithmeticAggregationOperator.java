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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mucontext.datamodel.context.ContextDataModelFacade;
import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.operator.utils.NotValidInformationException;
import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.tool.functions.impl.ContextReportComparable;

public abstract class ArithmeticAggregationOperator implements ICDFMOperator {

	// # # # # # PROTECTED VARIABLES # # # # #

	/**
	 * The facade used to create the context information.
	 */
	protected ContextDataModelFacade contextDataFacade;
	/**
	 * A counter to create unique context observations.
	 */
	protected Integer counter_contextObservationId;
	/**
	 * A counter to create unique context reports.
	 */
	protected Integer counter_contextReportId;

	// # # # # # CONSTRUCTORS # # # # #

	protected ArithmeticAggregationOperator() {
		// - - - - - CORE OF THE METHOD - - - - -
		counter_contextObservationId = 0;
		counter_contextReportId = 0;
		contextDataFacade = new ContextDataModelFacade("ArithmeticAggregationOperator - context data model facade");
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method aggregated a list of double values into another double.
	 *
	 * @param _listValue
	 *            The list of double to aggregate.
	 * @return The resulting double value.
	 */
	public abstract Double aggregateListValue(final List<Double> _listValue);

	@Override
	public List<ContextReport> applyOperator(final List<ContextReport> _input) throws NotValidInformationException {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "ArithmeticAggregationOperator.applyOperator(List<ContextReport>): the argument _input is not valid.";
			ConstraintChecker.assertTrue(validateInput(_input), message);
		} catch (final ConstraintCheckerException _exception) {
			throw new NotValidInformationException(_exception.getMessage());
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The resulting list of context report. For the aggregation the list
		 * contains only one context report.
		 */
		final List<ContextReport> ret_listContextReport = new ArrayList<ContextReport>();
		/*
		 * The new context report added into <i>ret_listContextReport</i>.
		 */
		final ContextReport new_contextReport = contextDataFacade
				.createContextReport("Aggregated context report: " + counter_contextReportId++);
		/*
		 * A context observation to add in the resulting context report.
		 */
		ContextObservation<?> contextObservation;
		/*
		 * The list of <b>AggregatorContextDataFacade</b> used to store the
		 * information parsed the function before the aggregation.
		 */
		final List<AggregatorContextDataFacade> list_aggregatorContextFacade = new ArrayList<AggregatorContextDataFacade>();
		// - - - - - CORE OF THE METHOD - - - - -
		/*
		 * Analyze and parse the input values.
		 */
		for (final ContextReport loop_bufferContextReport : _input) {
			parseContextReport(loop_bufferContextReport, list_aggregatorContextFacade);
		}
		/*
		 * Clear the input values.
		 */
		clearListContextReport(_input);
		/*
		 * Aggregate the information (context and QoC).
		 */
		for (final AggregatorContextDataFacade loop_contextDataFacade : list_aggregatorContextFacade) {
			contextObservation = aggregateContextObservation(loop_contextDataFacade);
			contextObservation.addQoCIndicators(aggregateQoCMetaData(loop_contextDataFacade));
			contextDataFacade.addContextObservation(new_contextReport, contextObservation);
		}
		ret_listContextReport.add(new_contextReport);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_listContextReport;
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}

	// # # # # # PROTECTED METHODS # # # # #

	/**
	 * This method is used to clear the context information and QoC meta-data of
	 * a list of context reports.
	 *
	 * @param _list_contextReport
	 *            The list of context report to clear.
	 */
	protected void clearListContextReport(final List<ContextReport> _list_contextReport) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final ContextReport loop_contextReport : _list_contextReport) {
			for (final ContextObservation<?> loop_contextObservation : loop_contextReport.observations) {
				loop_contextObservation.observable.observations.clear();
				loop_contextObservation.list_qoCIndicator.clear();
			}
		}
	}

	/**
	 * This method is used to verify if the input can be used to execute the
	 * operation.
	 *
	 * @param _input
	 *            The information handled by the operator.
	 * @return True if the operator can handle the information.
	 */
	protected Boolean validateInput(final List<ContextReport> _input) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of context report is valid ?
		 */
		Boolean ret_listContextReportIsValid = true;
		/*
		 * The class used to compare the list of context report.
		 */
		final ContextReportComparable contextReportComparable = new ContextReportComparable();
		/*
		 * The first context report of the list. It will be compared with all
		 * other context reports.
		 */
		final ContextReport currentContextReport = _input.iterator().next();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final ContextReport loop_contextReport : _input) {
			contextReportComparable.setUp(currentContextReport, loop_contextReport);
			if (!(Boolean) contextReportComparable.exec()) {
				ret_listContextReportIsValid = false;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_listContextReportIsValid;
	}

	/**
	 * This method analyze a context report and produced a corresponding list of
	 * <b>AggregatorContextDataFacade</b>.
	 *
	 * @param _contextReport
	 *            The context report to analyze.
	 * @param _list_contextData
	 *            The list of <b>AggregatorContextDataFacade</b>.
	 */
	protected void parseContextReport(final ContextReport _contextReport,
			final List<AggregatorContextDataFacade> _list_contextData) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The <b>AggregatorContextDataFacade</b> used to store the information
		 * for the aggregation.
		 */
		AggregatorContextDataFacade aggregationContextData;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
			aggregationContextData = parseContextObservation(loop_contextObservation, _list_contextData);
			parseQoCIndicators(loop_contextObservation, aggregationContextData);
		}
	}

	/**
	 * This method stores a context information into an
	 * <b>AggregatorContextDataFacade</b>. The purpose of this method is gather
	 * all context observations associated to the same context observable into
	 * the same <b>AggregatorContextDataFacade</b>.
	 *
	 * @param _contextObservation
	 *            The context observation analyzed by the method.
	 * @param _list_aggregatorContextFacade
	 *            The list of existing <b>AggregatorContextDataFacade</b>. The
	 *            method searches in this list if the <i>_contextObservation</i>
	 *            have to be store in a new <b>AggregatorContextDataFacade</b>
	 *            or in an existing <b>AggregatorContextDataFacade</b>.
	 * @return The <b>AggregatorContextDataFacade</b> that contains the
	 *         <i>_contextObservation</i>. The
	 *         <b>AggregatorContextDataFacade</b> can be created or coming from
	 *         <i>_list_aggregatorContextFacade</i>.
	 */
	protected AggregatorContextDataFacade parseContextObservation(final ContextObservation<?> _contextObservation,
			final List<AggregatorContextDataFacade> _list_aggregatorContextFacade) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * First step: creating a new instance
		 * <b>AggregatorContextDataFacade</>. Second step: the method will
		 * search in <i>_list_aggregatorContextFacade</i> if there is not
		 * another same instance of <b>AggregatorContextDataFacade</>. If true,
		 * replace the new instance. Third step: add the
		 * <i>_contextObservation</i> in the <b>AggregatorContextDataFacade</>
		 */
		AggregatorContextDataFacade ret_aggregatorContextFacade = new AggregatorContextDataFacade(
				_contextObservation.observable);
		// - - - - - CORE OF THE METHOD - - - - -
		if (_list_aggregatorContextFacade.contains(ret_aggregatorContextFacade)) {
			ret_aggregatorContextFacade = _list_aggregatorContextFacade
					.get(_list_aggregatorContextFacade.indexOf(ret_aggregatorContextFacade));
		} else {
			_list_aggregatorContextFacade.add(ret_aggregatorContextFacade);
		}
		ret_aggregatorContextFacade.addContextObservation(_contextObservation);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_aggregatorContextFacade;
	}

	/**
	 * This method analyzes and adds the QoC meta-data of a context observation
	 * into an <b>AggregatorContextDataFacade</b>.
	 *
	 * @param _contextObservation
	 *            The context observation analyzed by the method.
	 * @param _aggregatorContextFacade
	 *            The aggregator context data facade that will receive the QoC
	 *            meta-data.
	 */
	protected void parseQoCIndicators(final ContextObservation<?> _contextObservation,
			final AggregatorContextDataFacade _aggregatorContextFacade) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : _contextObservation.list_qoCIndicator) {
			for (final QoCMetaData loop_qoCMetaData : QoCIMFacade.createListQoCMetaData(loop_qoCIndicator)) {
				_aggregatorContextFacade.addQoCMetricValue(loop_qoCMetaData.qoCMetricDefinition(),
						loop_qoCMetaData.qoCMetricValue());
			}
		}
	}

	/**
	 * This method aggregates the context information of an
	 * <b>AggregatorContextDataFacade</b>
	 * (<b>AggregatorContextDataFacade.list_contextObservation</b>).
	 *
	 * @param _aggregatorContextFacade
	 *            The facade that contains the context information.
	 * @param _aggregationOperator
	 *            The aggregator used to produce the context information.
	 * @return The new context informations.
	 */
	protected ContextObservation<?> aggregateContextObservation(
			final AggregatorContextDataFacade _aggregatorContextFacade) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of values aggregated by the operator.
		 */
		final List<Double> list_contextInformationValue = new ArrayList<Double>();
		/*
		 * The result of the aggregation.
		 */
		final String aggregatedObservationValue;
		// - - - - - CORE OF THE METHOD - - - - -
		for (final ContextObservation<?> loop_observation : _aggregatorContextFacade.listContextObservation()) {
			list_contextInformationValue.add((Double) loop_observation.value);
		}
		aggregatedObservationValue = "" + aggregateListValue(list_contextInformationValue);
		// - - - - - RETURN STATEMENT - - - - -
		return contextDataFacade.createContextObservation("" + counter_contextObservationId++,
				aggregatedObservationValue, _aggregatorContextFacade.contextObservable());
	}

	/**
	 * This method aggregates the QoC meta-data of an
	 * <b>AggregatorContextDataFacade</b>
	 * (<b>AggregatorContextDataFacade.map_qoCMetricValue</b>).
	 *
	 * @param _aggregatorContextFacade
	 *            The facade that contains the QoC meta-data.
	 * @param _aggregationOperator
	 *            The aggregator used to produce the QoC meta-data.
	 * @return The list of aggregated QoC meta-data.
	 */
	protected List<QoCIndicator> aggregateQoCMetaData(final AggregatorContextDataFacade _aggregatorContextFacade) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of QoC indicator produced by the method.
		 */
		final List<QoCIndicator> ret_listQoCIndicator = new ArrayList<QoCIndicator>();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final Entry<QoCMetricDefinition, List<QoCMetricValue>> entry_qoCMetricValue : _aggregatorContextFacade
				.mapQoCDefinitionValue().entrySet()) {
			ret_listQoCIndicator
					.add(aggregateListQoCMetricValue(entry_qoCMetricValue.getValue(), entry_qoCMetricValue.getKey()));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_listQoCIndicator;
	}

	/**
	 * This method aggregate a list of of QoC metric value.
	 *
	 * @param _list_qoCMetricValue
	 *            The list of QoC metric value that will be aggregated.
	 * @param _qoCMetricDefinition
	 *            The Qoc metric definition used to create the QoC metric
	 *            values.
	 * @param _aggregationOperator
	 *            The operator used to aggregate the QoC metric value.
	 * @return The QoC indicator that will contains the resulting value.
	 */
	protected QoCIndicator aggregateListQoCMetricValue(final List<QoCMetricValue> _list_qoCMetricValue,
			final QoCMetricDefinition _qoCMetricDefinition) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The result of the aggregation of the QoC metric value.
		 */
		final Double aggregatedValue;
		/*
		 * The list of the QoC metric value.
		 */
		final List<Double> list_qoCMetricValue = new ArrayList<Double>();
		/*
		 * The first QoC metric value of the list. This instance will be used to
		 * contain the resulting aggregated value.
		 */
		final QoCMetricValue firstQoCMetricValue = new QoCMetricValue(_list_qoCMetricValue.get(0));
		/*
		 * The QoC indicator that reference the <i>firstQoCMetricValue</i>.
		 */
		final QoCIndicator ret_qoCIndicator = (QoCIndicator) firstQoCMetricValue.container();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetricValue loop_qoCMetricValue : _list_qoCMetricValue) {
			list_qoCMetricValue.add(loop_qoCMetricValue.value());
		}
		aggregatedValue = aggregateListValue(list_qoCMetricValue);
		firstQoCMetricValue.value(aggregatedValue);
		firstQoCMetricValue.modificationDate(new Date());
		QoCIMFacade.removeAllQoCMetricValue(ret_qoCIndicator);
		QoCIMFacade.removeAllQoCMetricValue(_qoCMetricDefinition);
		QoCIMFacade.addQoCMetricValue(firstQoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricValue(firstQoCMetricValue, _qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
