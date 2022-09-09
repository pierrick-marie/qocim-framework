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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import qocim.capsule.configuration.IQoCIMCapsuleConfiguration;
import qocim.capsule.configuration.QoCIMCapsuleFunction;
import qocim.capsule.configuration.impl.QoCIMCapsuleConfiguration;
import qocim.cdfm.function.ICDFMFunction;
import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.function.impl.CDFMFunction;
import qocim.cdfm.operator.utils.EOperator;
import qocim.cdfm.operator.utils.OperatorFactory;
import qocim.cdfm.operator.utils.OperatorNotSupportedException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.datamodel.utils.QoCIMPerformanceLogLevel;
import qocim.functions.IQoCIMFunction;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.QoCManagementFunctionFactory;
import qocim.qocmanagement.functions.utils.QoCManagementFunctionNotSupportedException;

/**
 * The QoCIMCapsuleConfigurationSerializer class is used serialize and
 * unserialize a QoCIM-capsule configuration.
 *
 * @author Pierrick MARIE
 */
public class QoCIMCapsuleConfigurationUnSerializer extends AbstractQoCIMCapsuleConfigurationIO {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * A reloading strategy. It provide a method to know if the configuration
	 * file must be reload or not.
	 */
	private final FileChangedReloadingStrategy reloadingStrategy;

	// # # # # # CONSTRUCTORS # # # # #

	/**
	 * The constructor.
	 *
	 * @param _configurationFilePath
	 *            The file path of the configuration file.
	 */
	public QoCIMCapsuleConfigurationUnSerializer(final String _configurationFilePath) {
		// - - - - - CORE OF THE METHOD - - - - -
		super(_configurationFilePath);
		reloadingStrategy = new FileChangedReloadingStrategy();
		xmlConfigurationIO.setReloadingStrategy(reloadingStrategy);
		try {
			xmlConfigurationIO.load();
		} catch (final ConfigurationException _exception) {
			QoCIMLogger.logger.log(Level.WARNING, "Fail to load the XML QoCIM capsule configuration file.", _exception);
			_exception.printStackTrace();
		}
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * @return True if the configuration if have to be reload.
	 */
	public Boolean reloadConfigurationFileRequired() {
		// - - - - - RETURN STATEMENT - - - - -
		return reloadingStrategy.reloadingRequired();
	}

	/**
	 * This method unserialize a xml QoCIM capsule configuration file.
	 *
	 * @return The QoCIM capsule configuration.
	 */
	public IQoCIMCapsuleConfiguration unSerialize() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The QoCIM capsule configuration returned by the method.
		 */
		final IQoCIMCapsuleConfiguration ret_configurationCapsule = new QoCIMCapsuleConfiguration();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.logger.info("Starting QoCIM capsule configuration unserialization");
		// Parse the advertisement filter
		ret_configurationCapsule.setAdvertisementFilter(
				xmlConfigurationIO.getString(QoCIMCapsuleConfigurationConstants.ADVERTISEMENT_FILTER));
		// Parse the subscription filter
		ret_configurationCapsule.setSubscriptionFilter(
				xmlConfigurationIO.getString(QoCIMCapsuleConfigurationConstants.SUBSCRIPTION_FILTER));
		// Parse the log level filter
		ret_configurationCapsule.setLogLevel(QoCIMPerformanceLogLevel
				.valueOf(xmlConfigurationIO.getString(QoCIMCapsuleConfigurationConstants.LOG_LEVEL).toUpperCase()));
		// Parse the functions
		for (final HierarchicalConfiguration loop_configuration : xmlConfigurationIO
				.configurationsAt(QoCIMCapsuleConfigurationConstants.ROOT_FUNCTIONS + "."
						+ QoCIMCapsuleConfigurationConstants.ROOT_FUNCTION)) {
			ret_configurationCapsule.addFunction(getQoCIMCapsuleFunction(loop_configuration));
		}
		QoCIMLogger.logger.info("End of QoCIM capsule configuration unserialization");
		// - - - - - RETURN STATEMENT - - - - -
		return ret_configurationCapsule;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * This method returns a new QoCManagementFunction created with the values
	 * of the configuration file.
	 *
	 * @param _configuration
	 *            The parser of the configuration file.
	 * @return The new QoCManagementFunction.
	 */
	private IQoCManagementFunction getQoCManagementFunction(final HierarchicalConfiguration _configuration) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The QoC manegement function returned by the method.
		 */
		IQoCManagementFunction qoCManagementFunction = null;
		// - - - - - CORE OF THE METHOD - - - - -
		final EQoCManagementFunction functionType = EQoCManagementFunction
				.valueOf(_configuration.getString(QoCIMCapsuleConfigurationConstants.FUNCTION_NAME).toUpperCase());
		try {
			qoCManagementFunction = QoCManagementFunctionFactory.createQoCManagementFunction(functionType);
		} catch (final QoCManagementFunctionNotSupportedException _exception) {
			QoCIMLogger.logger.log(Level.WARNING, "Fail to read the configuration file.", _exception);
		}
		// - - - - - RETURN STATEMENT - - - - -
		return qoCManagementFunction;
	}

	/**
	 * This method splits an XML expression with the character "." and returns
	 * the last element of the expression.
	 *
	 * @param _xmlExpression
	 *            The XML expression analyzed by the method.
	 * @return The last element of the expression.
	 */
	private String getAttributeValue(final String _xmlExpression) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The variable returned by the method and containing the last element
		 * of the XML expression.
		 */
		String ret_attributeValue = "";
		/*
		 * The result of splitting the <i>_xmlExpression</i> with ".".
		 */
		final String[] splitedExpression = _xmlExpression.split("\\.");
		// - - - - - CORE OF THE METHOD - - - - -
		if (splitedExpression.length != 0) {
			ret_attributeValue = splitedExpression[splitedExpression.length - 1];
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_attributeValue;
	}

	/**
	 * This method sets the parameter of a function.
	 *
	 * @param _function
	 *            The function to configure.
	 * @param _configuration
	 *            The parser of the configuration file.
	 *
	 * @see IQoCIMFunction.setParameters(Map<String, String>)
	 */
	private void setFunctionParameters(final IQoCIMFunction _function, final HierarchicalConfiguration _configuration) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * A iterator to parse all the parameters of the function.
		 */
		final Iterator<String> iterator_function = _configuration
				.getKeys(QoCIMCapsuleConfigurationConstants.FUNCTION_PARAMETERS);
		/*
		 * The current XML expression returned by the iterator.
		 */
		String xmlExpression = "";
		/*
		 * A map containing all the parameters. This map is used to configure
		 * the function (@see IQoCIMFunction.setParameters(Map<String,
		 * String>)).
		 */
		final Map<String, String> parameters = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_function.hasNext()) {
			xmlExpression = iterator_function.next();
			parameters.put(getAttributeValue(xmlExpression), _configuration.getString(xmlExpression));
		}
		_function.setParameters(parameters);
	}

	/**
	 * This method returns the operator to associate to an ICDFMFunction.
	 *
	 * @param _configuration
	 *            The parser of the configuration file.
	 * @return The operator.
	 */
	private ICDFMOperator getOperator(final HierarchicalConfiguration _configuration) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The operator returned by the method.
		 */
		ICDFMOperator ret_operator = null;
		/*
		 * Looking for the name of the operator to get the corresponding enum
		 * (EOperator).
		 */
		final EOperator eOperator = EOperator
				.valueOf(_configuration.getString(QoCIMCapsuleConfigurationConstants.FUNCTION_OPERATOR + "[@"
						+ QoCIMCapsuleConfigurationConstants.OPERATOR_NAME + "]").toUpperCase());
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			ret_operator = OperatorFactory.createOperator(eOperator);
		} catch (final OperatorNotSupportedException _exception) {
			QoCIMLogger.logger.log(Level.WARNING, "Fail to create a new ICDFMOperator", _exception);
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_operator;
	}

	/**
	 * This method configures the operator of an ICDFMFunction.
	 *
	 * @param _function
	 *            The ICDFMFunction to configure.
	 * @param _configuration
	 *            The parser of the configuration file.
	 */
	private void setOperatorParameters(final ICDFMFunction _function, final HierarchicalConfiguration _configuration) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The operator used by <i>_function</i>.
		 */
		final ICDFMOperator operator = getOperator(_configuration);
		/*
		 * An iterator to get all the parameters of the operator.
		 */
		final Iterator<String> iterator_function = _configuration
				.getKeys(QoCIMCapsuleConfigurationConstants.FUNCTION_OPERATOR);
		/*
		 * The current XML expression returned by the operator.
		 */
		String xmlExpression = "";
		/*
		 * The map containing all the parameters of the operator.
		 */
		final Map<String, String> parameters = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		while (iterator_function.hasNext()) {
			xmlExpression = iterator_function.next();
			/*
			 * The name of the operator have to be ignore, it is not recognize
			 * as a configuration parameter.
			 */
			if (!xmlExpression.equals(QoCIMCapsuleConfigurationConstants.FUNCTION_OPERATOR + "[@"
					+ QoCIMCapsuleConfigurationConstants.OPERATOR_NAME + "]")) {
				parameters.put(getAttributeValue(xmlExpression), _configuration.getString(xmlExpression));
			}
		}
		operator.setParameters(parameters);
		_function.setOperator(operator);
	}

	/**
	 * This method returns a <b>QoCIMCapsuleFunction</b> created from the
	 * <b>IQoCManagementFunction</b> or <b>ICDFMFunction</b> configured in the
	 * XML file.
	 *
	 * @param _configuration
	 *            The parser of the configuration file.
	 * @return A new <b>QoCIMCapsuleFunction</b>.
	 */
	private QoCIMCapsuleFunction getQoCIMCapsuleFunction(final HierarchicalConfiguration _configuration) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The function returned by the method.
		 */
		QoCIMCapsuleFunction ret_capsuleFunction = null;
		/*
		 * The <b>IQoCIMFunction</b> used to instantiate
		 * <i>ret_capsuleFunction</i>.
		 */
		IQoCIMFunction function = null;
		// - - - - - CORE OF THE METHOD - - - - -
		/*
		 * If the current function is a QoC management function, else, the
		 * current function is a CDFM function.
		 */
		if (_configuration.getString(QoCIMCapsuleConfigurationConstants.FUNCTION_TYPE)
				.equals(IQoCManagementFunction.class.getSimpleName())) {
			function = getQoCManagementFunction(_configuration);
		} else {
			function = new CDFMFunction();
			setOperatorParameters((ICDFMFunction) function, _configuration);
		}
		ret_capsuleFunction = new QoCIMCapsuleFunction(function);
		setFunctionParameters(function, _configuration);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_capsuleFunction;
	}
}
