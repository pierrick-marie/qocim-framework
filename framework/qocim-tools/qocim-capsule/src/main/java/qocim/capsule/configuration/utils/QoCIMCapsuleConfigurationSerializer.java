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
package qocim.capsule.configuration.utils;

import java.util.Map.Entry;

import java.util.logging.Level;

import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.cdfm.function.ICDFMFunction;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

/**
 * The QoCIMCapsuleConfigurationSerializer class is used serialize a
 * QoCIM-capsule configuration.
 *
 * @author Pierrick MARIE
 */
public class QoCIMCapsuleConfigurationSerializer extends AbstractQoCIMCapsuleConfigurationIO {

	// # # # # # CONSTRUCTORS # # # # #

	public QoCIMCapsuleConfigurationSerializer(final String _configurationFilePath) {
		// - - - - - CORE OF THE METHOD - - - - -
		super(_configurationFilePath);
		/*
		 * Using the xpath expression to save the properties in the XML
		 * configuration file.
		 */
		xmlConfigurationIO.setExpressionEngine(new XPathExpressionEngine());
		xmlConfigurationIO.setAutoSave(true);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method serialize into a xml file QoCIM-capsule configuration.
	 *
	 * @param _configurationCapsule
	 *            The configuration to serialize.
	 */
	public void serialize(final IQoCIMCapsuleConfiguration _configurationCapsule) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "QoCIMCapsuleConfigurationSerializer.serialize(IQoCIMCapsuleConfiguration): the argument _configurationCapsule is null.";
			ConstraintChecker.notNull(_configurationCapsule, message);
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			QoCIMLogger.logger.log(Level.INFO, "Saving the configuration of the capsule.");
			setProperties(_configurationCapsule);
			xmlConfigurationIO.save();
		} catch (final ConfigurationException _exception) {
			QoCIMLogger.logger.log(Level.WARNING, "Fail to save the configuration: " + _exception);
		}
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * This method saves into the xml file the properties of a
	 * <b>ICDFMFunction</b>.
	 *
	 * @param _function
	 *            The function to save.
	 * @param _functionId
	 *            The id of the function (the id used by the XPath engine).
	 */
	private void setFunctionProperties(final ICDFMFunction _function, final Integer _functionId) {
		// - - - - - CORE OF THE METHOD - - - - -
		// Saving the function type
		xmlConfigurationIO.addProperty("//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId
				+ "] " + QoCIMCapsuleConfigurationConstants.FUNCTION_TYPE, ICDFMFunction.class.getSimpleName());

		// Saving the function parameters
		for (final Entry<String, String> entry_parameter : _function.parameters().entrySet()) {
			xmlConfigurationIO.addProperty(
					"//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId + "]/"
							+ QoCIMCapsuleConfigurationConstants.FUNCTION_PARAMETERS + "/" + entry_parameter.getKey(),
					entry_parameter.getValue());
		}

		// Saving the operator name
		xmlConfigurationIO.addProperty("//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId
				+ "]/" + QoCIMCapsuleConfigurationConstants.FUNCTION_OPERATOR + "@"
				+ QoCIMCapsuleConfigurationConstants.OPERATOR_NAME, _function.operator().getName());

		// Saving the operator parameters
		for (final Entry<String, String> entry_parameter : _function.operator().parameters().entrySet()) {
			xmlConfigurationIO.addProperty(
					"//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId + "]/"
							+ QoCIMCapsuleConfigurationConstants.FUNCTION_OPERATOR + "/" + entry_parameter.getKey(),
					entry_parameter.getValue());
		}
	}

	/**
	 * This method saves into the xml file the properties of a
	 * <b>IQoCManagementFunction</b>.
	 *
	 * @param _function
	 *            The function to save.
	 * @param _functionId
	 *            The id of the function (the id used by the XPath engine).
	 */
	private void setFunctionProperties(final IQoCManagementFunction _function, final Integer _functionId) {
		// - - - - - CORE OF THE METHOD - - - - -
		// Saving the function type
		xmlConfigurationIO.addProperty(
				"//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId + "] "
						+ QoCIMCapsuleConfigurationConstants.FUNCTION_TYPE,
				IQoCManagementFunction.class.getSimpleName());

		// Saving the function name
		xmlConfigurationIO.addProperty("//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId
				+ "] " + QoCIMCapsuleConfigurationConstants.FUNCTION_NAME, _function.getName());

		// Saving the function parameters
		for (final Entry<String, String> entry_parameter : _function.parameters().entrySet()) {
			xmlConfigurationIO.addProperty(
					"//" + QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "[" + _functionId + "]/"
							+ QoCIMCapsuleConfigurationConstants.FUNCTION_PARAMETERS + "/" + entry_parameter.getKey(),
					entry_parameter.getValue());
		}
	}

	/**
	 * This method fills the xml file with the values of the configuration.
	 *
	 * @param _configurationCapsule
	 *            The configuration to serialize.
	 * @param _xmlConfigurationWriter
	 *            The xml file writer.
	 */
	private void setProperties(final IQoCIMCapsuleConfiguration _configurationCapsule) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The counter of the XPath engine (starts at 1).
		 */
		Integer counter_functionId = 1;
		// - - - - - CORE OF THE METHOD - - - - -

		// Saving the root element
		xmlConfigurationIO.setRootElementName(QoCIMCapsuleConfigurationConstants.ROOT_ELEMENT_NAME);
		// Saving the log level
		xmlConfigurationIO.setProperty(QoCIMCapsuleConfigurationConstants.LOG_LEVEL,
				_configurationCapsule.logLevel().toString());
		// Saving the advertisement filter
		xmlConfigurationIO.setProperty(QoCIMCapsuleConfigurationConstants.ADVERTISEMENT_FILTER,
				_configurationCapsule.advertisementFilter());
		// Saving the subscription filter
		xmlConfigurationIO.setProperty(QoCIMCapsuleConfigurationConstants.SUBSCRIPTION_FILTER,
				_configurationCapsule.subscriptionFilter());

		// Analyzing all the functions
		for (final QoCIMCapsuleFunction loop_function : _configurationCapsule.orderedListFunction()) {
			// Saving the function id
			xmlConfigurationIO.addProperty(QoCIMCapsuleConfigurationConstants.ROOT_FUNCTIONS + "/"
					+ QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION + "@"
					+ QoCIMCapsuleConfigurationConstants.FUNCTION_ID, counter_functionId - 1);

			// Saving the properties of the function.
			// Apply different treatment according to the type of the function
			if (loop_function.isICDFMFunction()) {
				setFunctionProperties((ICDFMFunction) loop_function.function, counter_functionId);
			} else {
				setFunctionProperties((IQoCManagementFunction) loop_function.function, counter_functionId);
			}
			counter_functionId++;
		}
	}
}
