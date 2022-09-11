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
package qocim.datamodel.precisionindicator;

import java.util.Date;
import java.util.List;

import qocim.datamodel.Order;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * <em><b>PerthousandQoCMetricDefinition</b></em>. <!-- end-user-doc -->
 *
 * @generated
 **/
public class PerthousandPrecisionQoCMetricDefinition extends QoCMetricDefinition {

	// # # # # # CONSTANTS # # # # #

	public static final String ID_DEFAULTVALUE = "10.2";
	public static final boolean IS_INVARIANT_DEFAULTVALUE = false;
	public static final String UNIT_DEFAULTVALUE = "1/1000";
	public static final String PROVIDER_URI_DEFAULTVALUE = "//sensor";
	public static final double MIN_VALUE_DEFAULTVALUE = 1;
	public static final double MAX_VALUE_DEFAULTVALUE = 1000;
	public static final Order DIRECTION_DEFAULTVALUE = Order.UPPER;

	// # # # # # PRIVATE VARIABLES # # # # #

	private final PerthousandPrecisionQoCMetricValueGenerator valueGenerator;

	// # # # # # CONSTRUCTORS # # # # #

	/**
	 * @generated
	 **/
	public PerthousandPrecisionQoCMetricDefinition() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		super();
		id(ID_DEFAULTVALUE);
		isInvariant(IS_INVARIANT_DEFAULTVALUE);
		unit(UNIT_DEFAULTVALUE);
		minValue(MIN_VALUE_DEFAULTVALUE);
		maxValue(MAX_VALUE_DEFAULTVALUE);
		direction(DIRECTION_DEFAULTVALUE);
		providerUri(PROVIDER_URI_DEFAULTVALUE);
		description(new PrecisionDescription());
		description().container(this);
		valueGenerator = new PerthousandPrecisionQoCMetricValueGenerator();
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public Double computeQoCMetricValue(final QInformation<?> information,
										final List<QoCMetaData> _list_qoCMetaData) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "PerthousandPrecisionFactory.computeQoCMetricValue(String, String,  Date, Integer,  List<QoCMetaData>): the argument _contextObservationValue is null.";
			ConstraintChecker.notNull(information.data(), message);
		} catch (final ConstraintCheckerException e) {
			return 0.0;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return valueGenerator.generateMetricValue((Double) information.data());
	}
}
