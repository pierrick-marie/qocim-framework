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
package qocim.datamodel.utils;

import java.util.logging.Level;

/**
 * The QoCIMCapsuleLogLevel class defines a set of logging levels for the
 * QoCIM-capsules.
 *
 * The logging levels are ordered. Enabling logging at a given level also
 * enables logging at all higher levels. This class extends the LogLevel class
 * provided by Apache: org.apache.log4j.lf5.LogLevel.
 *
 * @author Pierrick MARIE
 */
public class QoCIMPerformanceLogLevel extends Level {

    // # # # # # CONSTANTS # # # # #

    // A unique id for the serialization.
    private static final long serialVersionUID = 2876721197848050569L;

    /**
     * ALL_MESSAGE: Display information concerning the hardware (configuration,
     * memory and CPU usage) + the time messages + traces. It is equivalent to
     * HARDWARE8MESSAGE.
     */
    public final static QoCIMPerformanceLogLevel ALL = new QoCIMPerformanceLogLevel(EQoCIMPerformanceLogLevel.ALL.name(), Level.INFO.intValue());
    /**
     * HARDWARE_MESSAGE: Display information concerning the hardware
     * (configuration, memory and CPU usage) + the time messages + trace
     * message.
     */
    public final static QoCIMPerformanceLogLevel HARDWARE = new QoCIMPerformanceLogLevel(EQoCIMPerformanceLogLevel.HARDWARE.name(), ALL.intValue() + 1);
    /**
     * TIME_MESSAGE: Display information concerning the date (current time) of
     * the events.
     */
    public final static QoCIMPerformanceLogLevel TIME = new QoCIMPerformanceLogLevel(EQoCIMPerformanceLogLevel.TIME.name(), HARDWARE.intValue() + 1);
    /**
     * EVENT_MESASGE: Display information concerning the event that appears.
     */
    public final static QoCIMPerformanceLogLevel EVENT = new QoCIMPerformanceLogLevel(EQoCIMPerformanceLogLevel.EVENT.name(), TIME.intValue() + 1);
    /**
     * NONE: don't display any performance messages.
     */
    public final static QoCIMPerformanceLogLevel NONE = new QoCIMPerformanceLogLevel(EQoCIMPerformanceLogLevel.NONE.name(), TIME.intValue() + 1);

    // # # # # # PRIVATE VARIABLES # # # # #

    /**
     * This enum is used to declare all the available events.
     * 
     * @author Pierrick MARIE
     */
    private enum EQoCIMPerformanceLogLevel {

	NONE("NONE"), EVENT("EVENT"), TIME("TIME"), HARDWARE("HARDWARE"), ALL("ALL");

	String logLevel;

	private EQoCIMPerformanceLogLevel(final String _logLevel) {
	    logLevel = _logLevel;
	}

	@Override
	public String toString() {
	    return logLevel;
	}
    }

    // # # # # # CONSTRUCTORS # # # # #

    protected QoCIMPerformanceLogLevel(final String name, final int value) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	super(name, value);
    }

    // # # # # # PUBLIC METHODS # # # # #

    public static QoCIMPerformanceLogLevel valueOf(final String _value) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	QoCIMPerformanceLogLevel ret_logLevel = null;
	// - - - - - CORE OF THE METHOD - - - - -
	switch (EQoCIMPerformanceLogLevel.valueOf(_value)) {
	case EVENT:
	    ret_logLevel = EVENT;
	    break;
	case TIME:
	    ret_logLevel = TIME;
	    break;
	case HARDWARE:
	    ret_logLevel = HARDWARE;
	    break;
	case ALL:
	    ret_logLevel = ALL;
	    break;
	default:
	    ret_logLevel = NONE;
	    break;
	}
	// - - - - - RETURN STATEMENT - - - - -
	return ret_logLevel;
    }
}
