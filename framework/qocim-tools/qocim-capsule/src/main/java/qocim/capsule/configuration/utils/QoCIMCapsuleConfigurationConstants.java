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

/**
 * This class contains all the constants required by the xml configuration
 * reader and xml configuration writer to analyse the configuration files. The
 * constants represent the name of the xml attributes.
 *
 * @author Pierrick MARIE
 */
public final class QoCIMCapsuleConfigurationConstants {

    // # # # # # CONSTANTS # # # # #

    /**
     * The root element of the configuration file.
     */
    public static final String ROOT_ELEMENT_NAME = "qocim_capsule_configuration";
    /**
     * The level of the logs produced by the capsule.
     */
    public static final String LOG_LEVEL = "log_level";
    /**
     * The subscription filter.
     */
    public static final String SUBSCRIPTION_FILTER = "subscription_filter";
    /**
     * The advertisement filter.
     */
    public static final String ADVERTISEMENT_FILTER = "advertisement_filter";
    /**
     * The root of the elements describing all the functions executed by the
     * capsule.
     */
    public static final String ROOT_FUNCTIONS = "functions";
    /**
     * The root of one function.
     */
    public static final String ROOT_FUNCTION = "function";
    /**
     * The type of the function (ICDFMFunction or IQoCManagementFunction).
     */
    public static final String FUNCTION_TYPE = "type";
    /**
     * The verbose name of the function. Only available for
     * IQoCManagementFunction.
     */
    public static final String FUNCTION_NAME = "name";
    /**
     * The unique id of a function.
     */
    public static final String FUNCTION_ID = "id";
    /**
     * The root of the parameters of a function.
     */
    public static final String FUNCTION_PARAMETERS = "parameters";
    /**
     * The root of the operator of the function. Only available for
     * ICDFMFunction.
     */
    public static final String FUNCTION_OPERATOR = "operator";
    /**
     * The name of the operator.
     */
    public static final String OPERATOR_NAME = "name";
}
