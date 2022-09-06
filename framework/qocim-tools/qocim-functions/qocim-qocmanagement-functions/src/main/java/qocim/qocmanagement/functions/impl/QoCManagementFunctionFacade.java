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
package qocim.qocmanagement.functions.impl;

import java.util.HashMap;
import java.util.Map;

import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.tool.functions.utils.QoCFilter;

public class QoCManagementFunctionFacade {

	/**
	 * This map is used to initialize the QoC management functions with all
	 * available QoCIM factory. Its key is the <i>id</i> of the
	 * <b>QoCMetricDefinition</b> and its value is the corresponding
	 * <b>QoCIMFactory</b> used to produce the QoC meta-data.
	 */
	public static final Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade = new HashMap<QoCMetricDefinition, IQoCIMFactory>();

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method adds a new QoC indicator into the QoC meta-data of a context
	 * report.
	 *
	 * @param _message
	 *            The context report that will receive the new QoC indicator.
	 * @param _qoCIndicatorId
	 *            The <i>id</i> of the QoC indicator inserted into the QoC
	 *            meta-data of the context report.
	 * @param _qoCCriterionId
	 *            The <i>id</i> of the QoC criterion inserted into the QoC
	 *            meta-data of the context report.
	 * @param _qoCMetricDefinitionId
	 *            The <i>id</i> of the QoC metric definition inserted into the
	 *            QoC meta-data of the context report.
	 * @return The context report modified by the method.
	 */
	public static ContextReport addQoCIndicator(final ContextReport _message, final Integer _qoCIndicatorId,
			final String _qoCCriterionId, final String _qoCMetricDefinitionId) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final AddQoCIndicator addQoCIndicator = new AddQoCIndicator(map_availableQoCIMFacade);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(_qoCIndicatorId, _qoCCriterionId, _qoCMetricDefinitionId);
		// - - - - - RETURN STATEMENT - - - - -
		return addQoCIndicator.exec(_message);
	}

	/**
	 * The method removes the QoC meta-data of a context report that does not
	 * respect the constraints expressed into a QoC filter.
	 *
	 * @param _message
	 *            The context report analyzed by the method.
	 * @param _qoCFilter
	 *            The QoC filter that contains the constraints.
	 * @return The context report modified by the method.
	 */
	public static ContextReport filterQoCMetaData(final ContextReport _message, final QoCFilter _qoCFilter) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final FilterQoCMetaData filterQoCMetaData = new FilterQoCMetaData();
		// - - - - - CORE OF THE METHOD - - - - -
		filterQoCMetaData.setUp(_qoCFilter);
		// - - - - - RETURN STATEMENT - - - - -
		return filterQoCMetaData.exec(_message);
	}

	/**
	 * The method removes all the QoC meta-data of a context report.
	 *
	 * @param _message
	 *            The context report with QoC meta-data.
	 * @return The context report without QoC meta-data.
	 */
	public static ContextReport removeQoCMetaData(final ContextReport _message) {
		// - - - - - CORE OF THE METHOD - - - - -
		final RemoveQoCMetaData removeQoCMetaData = new RemoveQoCMetaData();
		// - - - - - RETURN STATEMENT - - - - -
		return removeQoCMetaData.exec(_message);
	}

	/**
	 * The method removes one QoC metric value from the QoC meta-data of a
	 * context report.
	 *
	 * @param _message
	 *            The context report that contains the QoC metric value that
	 *            will be removed.
	 * @param _qoCMetricValueId
	 *            The <i>id</i> of the QoC metric value to be removed.
	 * @param _qoCIndicatorId
	 *            The <i>id</i> of the QoC indicator associated to the QoC
	 *            metric value that will be removed.
	 * @return The context report modified by the method.
	 */
	public static ContextReport removeQoCMetricValue(final ContextReport _message, final String _qoCMetricValueId,
			final Integer _qoCIndicatorId) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final RemoveQoCMetricValue removeQoCMetricValue = new RemoveQoCMetricValue();
		// - - - - - CORE OF THE METHOD - - - - -
		removeQoCMetricValue.setUp(_qoCMetricValueId, _qoCIndicatorId);
		// - - - - - RETURN STATEMENT - - - - -
		return removeQoCMetricValue.exec(_message);
	}

	/**
	 * The method updates the value of all QoC meta-data of a context report.
	 *
	 * @param _message
	 *            The context report with QoC meta-data.
	 * @return The context report with the updated QoC meta-data.
	 */
	public static ContextReport updateQoCMetaData(final ContextReport _message) {
		// - - - - - CORE OF THE METHOD - - - - -
		final UpdateQoCMetaData updateQoCMetaData = new UpdateQoCMetaData(map_availableQoCIMFacade);
		// - - - - - RETURN STATEMENT - - - - -
		return updateQoCMetaData.exec(_message);
	}

	/**
	 * The method updates the value of one QoC metric value from the QoC
	 * meta-data of a context report.
	 *
	 * @param _message
	 * @param _qoCMetricValueId
	 * @param _qoCIndicatorId
	 * @return
	 */
	public static ContextReport updateQoCMetricValue(final ContextReport _message, final String _qoCMetricValueId,
			final Integer _qoCIndicatorId) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final UpdateQoCMetricValue updateQoCMetricValue = new UpdateQoCMetricValue(map_availableQoCIMFacade);
		// - - - - - CORE OF THE METHOD - - - - -
		updateQoCMetricValue.setUp(_qoCMetricValueId, _qoCIndicatorId);
		// - - - - - RETURN STATEMENT - - - - -
		return updateQoCMetricValue.exec(_message);
	}
}
