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
package qocim.utils.logs;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class is the formatter of the log messages produced by the class
 * <b>QoCIMLogger</b>. The class adds to the end of messages a double new line
 * to easily read the logs.
 *
 * @author Pierrick MARIE
 */
public class LogFormatter extends Formatter {

	@Override
	public String format(final LogRecord record) {
		return "\n";
	}
}