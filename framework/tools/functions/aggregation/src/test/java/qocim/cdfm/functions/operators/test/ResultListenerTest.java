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
package qocim.cdfm.functions.operators.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricValue;
import qocim.functions.IQoCIMFunctionListener;

public class ResultListenerTest implements IQoCIMFunctionListener {

	private final ContextReport expectedContextReport;

	public ResultListenerTest(final ContextReport _expectedContextReport) {
		expectedContextReport = _expectedContextReport;
	}

	private ContextObservation<?> searchContextObservation(final ContextObservation<?> _searchedContextObservation,
			final Set<ContextObservation<?>> _set_contextObservation) {
		// - - - - - INIT VARIABLES - - - - -
		ContextObservation<?> contextObservation;
		final Iterator<ContextObservation<?>> iterator_contextObserVation = _set_contextObservation.iterator();
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_contextObserVation.hasNext()) {
			contextObservation = iterator_contextObserVation.next();
			if (contextObservation.observable.uri.equals(_searchedContextObservation.observable.uri)
					&& contextObservation.value.equals(contextObservation.value)) {
				return contextObservation;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return null;
	}

	private void compareListQoCIndicator(final List<QoCIndicator> list_expectedQoCIndicators,
			final List<QoCIndicator> list_resultQoCIndicators) {
		// - - - - - INIT VARIABLES - - - - -
		QoCIndicator resultQoCIndicator;
		// - - - - - CORE OF THE METHOD - - - - -
		assertEquals(list_expectedQoCIndicators.size(), list_resultQoCIndicators.size());
		for (final QoCIndicator loop_expectedQoCIndicator : list_expectedQoCIndicators) {
			resultQoCIndicator = QoCIMFacade.searchFirstQoCIndicator(list_resultQoCIndicators,
					loop_expectedQoCIndicator.id());
			if (resultQoCIndicator == null) {
				fail("Expected QoCIndicator not found: " + loop_expectedQoCIndicator);
			}
			assertEquals(loop_expectedQoCIndicator.listQocMetricValue.size(),
					resultQoCIndicator.listQocMetricValue.size());
			for (final QoCMetricValue loop_expectedQoCMetricValue : loop_expectedQoCIndicator.listQocMetricValue) {
				assertTrue(searchQoCMetricValue(loop_expectedQoCMetricValue, resultQoCIndicator.listQocMetricValue));
			}
		}
	}

	private Boolean searchQoCMetricValue(final QoCMetricValue _searchedQoCMetricValue,
			final List<QoCMetricValue> listQocMetricValue) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetricValue loopqocMetricValue : listQocMetricValue) {
			if (((QoCIndicator) _searchedQoCMetricValue.container()).id()
					.equals(((QoCIndicator) loopqocMetricValue.container()).id())
					&& _searchedQoCMetricValue.value().equals(loopqocMetricValue.value()) && _searchedQoCMetricValue
							.qocMetricDefinition().id().equals(loopqocMetricValue.qocMetricDefinition().id())) {
				return true;
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return false;
	}

	@Override
	public void newInformation(final ContextReport _newContextReport) {
		final Set<ContextObservation<?>> list_resultContextObservation = _newContextReport.observations;
		final Set<ContextObservation<?>> list_expectedContextObservation = expectedContextReport.observations;
		ContextObservation<?> resultContextObservation;
		// - - - - - CORE OF THE METHOD - - - - -
		assertEquals(list_expectedContextObservation.size(), list_resultContextObservation.size());
		for (final ContextObservation<?> loop_expectedContextObservation : list_expectedContextObservation) {
			/*
			 * Verify if the resulting context observations match the expected
			 * observations.
			 */
			resultContextObservation = searchContextObservation(loop_expectedContextObservation,
					list_resultContextObservation);
			if (resultContextObservation == null) {
				fail("Expected context observation not found: " + loop_expectedContextObservation);
			}
			/*
			 * Verify if the resulting QoC indicator match the expected QoC
			 * meta-data.
			 */
			compareListQoCIndicator(loop_expectedContextObservation.listQocIndicator,
					resultContextObservation.listQocIndicator);
		}
	}
}