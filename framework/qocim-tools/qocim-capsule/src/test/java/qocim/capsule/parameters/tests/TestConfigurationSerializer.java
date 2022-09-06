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

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.logging.Level;

import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.capsule.configuration.utils.QoCIMCapsuleConfigurationSerializer;
import qocim.capsule.configuration.utils.QoCIMCapsuleConfigurationUnSerializer;
import qocim.datamodel.utils.QoCIMLogger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestConfigurationSerializer {

	/**
	 * The path of the configuration file.
	 */
	private final static String FOLDER_PATH = "/tmp/";
	/**
	 * The serializer object.
	 */
	private static QoCIMCapsuleConfigurationSerializer serializer;
	/**
	 * The unserializer object.
	 */
	private static QoCIMCapsuleConfigurationUnSerializer unSerializer;

	private static void compareConfigurations(final IQoCIMCapsuleConfiguration _configurationExpected,
			final IQoCIMCapsuleConfiguration _configurationReceived) {

		// Compare the log level.
		assertEquals(_configurationExpected.logLevel(), _configurationReceived.logLevel());

		// Compare the advertisement filters.
		assertEquals(_configurationExpected.advertisementFilter(), _configurationReceived.advertisementFilter());

		// Compare the subscription filters.
		assertEquals(_configurationExpected.subscriptionFilter(), _configurationReceived.subscriptionFilter());

		assertEquals(_configurationExpected.orderedListFunction().size(),
				_configurationReceived.orderedListFunction().size());

		final Iterator<QoCIMCapsuleFunction> iteraotr_expectedFunction = _configurationExpected.orderedListFunction()
				.iterator();
		final Iterator<QoCIMCapsuleFunction> iteraotr_receivedFunction = _configurationReceived.orderedListFunction()
				.iterator();
		QoCIMCapsuleFunction expectedFunction;
		QoCIMCapsuleFunction receivedFunction;

		/*
		 * Comparing the type of the functions that compose the chain.
		 */
		while (iteraotr_expectedFunction.hasNext()) {
			expectedFunction = iteraotr_expectedFunction.next();
			receivedFunction = iteraotr_receivedFunction.next();
			assertEquals(expectedFunction.isICDFMFunction(), receivedFunction.isICDFMFunction());
			assertEquals(expectedFunction.isIQoCManagementFunction(), receivedFunction.isIQoCManagementFunction());
			/*
			 * If iterator has not next element, there is not next function to
			 * compare.
			 */
			if (iteraotr_expectedFunction.hasNext()) {
				assertEquals(expectedFunction.nextFunction().isICDFMFunction(),
						receivedFunction.nextFunction().isICDFMFunction());
				assertEquals(expectedFunction.nextFunction().isIQoCManagementFunction(),
						receivedFunction.nextFunction().isIQoCManagementFunction());
			}
		}
	}

	/**
	 * The configuration to serialize.
	 */
	private IQoCIMCapsuleConfiguration serializedConfiguration;

	/**
	 * The configuration resulting from the unserialization.
	 */
	private IQoCIMCapsuleConfiguration unSerializedConfiguration;

	@Before
	public final void initConfigurationFile() {
		QoCIMLogger.loadDefaultConfig();
		QoCIMLogger.logger.setLevel(Level.WARNING);
	}

	@Test
	public final void serializeDefaultConfig() {
		final String fileName = FOLDER_PATH + "test_qocim_capsule_configuration.xml";
		serializedConfiguration = StubQoCIMCapsuleConfiguration.getDefaultConfiguration();
		serializer = new QoCIMCapsuleConfigurationSerializer(fileName);
		serializer.serialize(serializedConfiguration);
		unSerializer = new QoCIMCapsuleConfigurationUnSerializer(fileName);
	}

	@After
	public final void testEquals() {
		unSerializedConfiguration = unSerializer.unSerialize();
		compareConfigurations(serializedConfiguration, unSerializedConfiguration);
	}

	@Test
	public final void testSimpleConfig() {
		final String fileName = FOLDER_PATH + "test_qocim_capsule_simple_configuration.xml";
		serializedConfiguration = StubQoCIMCapsuleConfiguration.getSimpleConfiguration();
		serializer = new QoCIMCapsuleConfigurationSerializer(fileName);
		serializer.serialize(serializedConfiguration);
		unSerializer = new QoCIMCapsuleConfigurationUnSerializer(fileName);
	}
}
