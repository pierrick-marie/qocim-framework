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
package qocim.capsule.parameters.tests;

import java.util.HashMap;

import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.capsule.configuration.impl.QoCIMCapsuleConfiguration;
import qocim.cdfm.function.impl.CDFMFunction;
import qocim.cdfm.operator.aggregation.impl.MeanAggregator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.freshnessindicator.FreshnessQoCCriterion;
import qocim.datamodel.freshnessindicator.FreshnessQoCIndicator;
import qocim.datamodel.freshnessindicator.RandomFreshnessQoCMetricDefinition;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.QoCIMPerformanceLogLevel;
import qocim.qocmanagement.functions.impl.AddQoCIndicator;

class StubQoCIMCapsuleConfiguration {

	static IQoCIMCapsuleConfiguration getSimpleConfiguration() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final IQoCIMCapsuleConfiguration configuration = new QoCIMCapsuleConfiguration();
		final AddQoCIndicator qoCFunction = new AddQoCIndicator(new HashMap<QoCMetricDefinition, IQoCIMFactory>());
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFunction.setUp(FreshnessQoCIndicator.ID_DEFAULTVALUE, FreshnessQoCCriterion.ID_DEFAULTVALUE,
				RandomFreshnessQoCMetricDefinition.ID_DEFAULTVALUE);
		configuration.addFunction(new QoCIMCapsuleFunction(qoCFunction));
		configuration.setAdvertisementFilter(new AdvertisementFilter().precisionsAndFreshnessFilter());
		configuration.setSubscriptionFilter(new SubscriptionFilter().precisionsFilter());
		configuration.setLogLevel(QoCIMPerformanceLogLevel.HARDWARE);
		// - - - - - RETURN STATEMENT - - - - -
		return configuration;
	}

	static IQoCIMCapsuleConfiguration getDefaultConfiguration() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final IQoCIMCapsuleConfiguration configuration = new QoCIMCapsuleConfiguration();
		final AddQoCIndicator qoCFunction = new AddQoCIndicator(new HashMap<QoCMetricDefinition, IQoCIMFactory>());
		final CDFMFunction cdfmFunction = new CDFMFunction();
		final MeanAggregator operator = new MeanAggregator();
		// - - - - - CORE OF THE METHOD - - - - -
		cdfmFunction.setOperator(operator);
		cdfmFunction.setNbHandledContextReport(10);
		cdfmFunction.setTimerOnly(false);
		configuration.addFunction(new QoCIMCapsuleFunction(cdfmFunction));
		qoCFunction.setUp(FreshnessQoCIndicator.ID_DEFAULTVALUE, FreshnessQoCCriterion.ID_DEFAULTVALUE,
				RandomFreshnessQoCMetricDefinition.ID_DEFAULTVALUE);
		configuration.addFunction(new QoCIMCapsuleFunction(qoCFunction));
		configuration.setAdvertisementFilter(new AdvertisementFilter().precisionAndFreshnessFilter());
		configuration.setSubscriptionFilter(new SubscriptionFilter().precisionsFilter());
		configuration.setLogLevel(QoCIMPerformanceLogLevel.HARDWARE);
		// - - - - - RETURN STATEMENT - - - - -
		return configuration;
	}
}
