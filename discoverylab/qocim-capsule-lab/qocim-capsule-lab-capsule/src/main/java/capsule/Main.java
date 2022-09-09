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
package capsule;

import mudebs.common.algorithms.OperationalMode;
import qocim.capsule.QoCIMConsumerContract;
import qocim.capsule.QoCIMProducerContract;
import qocim.capsule.impl.QoCIMCapsule;
import qocim.datamodel.freshnessindicator.FreshnessFactory;
import qocim.datamodel.freshnessindicator.FreshnessQoCMetricDefinition;
import qocim.datamodel.freshnessindicator.RandomFreshnessFactory;
import qocim.datamodel.freshnessindicator.RandomFreshnessQoCMetricDefinition;
import qocim.datamodel.precisionindicator.PercentPrecisionFactory;
import qocim.datamodel.precisionindicator.PercentPrecisionQoCMetricDefinition;
import qocim.datamodel.precisionindicator.PerthousandPrecisionFactory;
import qocim.datamodel.precisionindicator.PerthousandPrecisionQoCMetricDefinition;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.utils.QoCManagementFunctionFactory;

public class Main {

	/**
	 * Time to wait during polling (in ms).
	 */
	private static final long TIME_TO_WAIT = 2000;

	public static void main(final String[] _args) throws Exception {
		QoCManagementFunctionFactory.map_availableQoCIMFacade.put(new FreshnessQoCMetricDefinition(),
				FreshnessFactory.instance());
		QoCManagementFunctionFactory.map_availableQoCIMFacade.put(new RandomFreshnessQoCMetricDefinition(),
				RandomFreshnessFactory.instance());
		QoCManagementFunctionFactory.map_availableQoCIMFacade.put(new PercentPrecisionQoCMetricDefinition(),
				PercentPrecisionFactory.instance());
		QoCManagementFunctionFactory.map_availableQoCIMFacade.put(new PerthousandPrecisionQoCMetricDefinition(),
				PerthousandPrecisionFactory.instance());
		QoCIMLogger.loadDefaultConfig();
		// CREATE THE CAPSULE
		final QoCIMProducerContract producerContract = new QoCIMProducerContract(OperationalMode.LOCAL, null);
		final QoCIMConsumerContract consumerContract = new QoCIMConsumerContract(OperationalMode.GLOBAL, null);
		new QoCIMCapsule(_args, null, consumerContract, producerContract);

		while (true) {
			Thread.sleep(TIME_TO_WAIT);
		}
	}
}
