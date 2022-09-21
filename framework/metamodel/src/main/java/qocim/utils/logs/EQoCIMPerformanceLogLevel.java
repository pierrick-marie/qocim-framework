package qocim.utils.logs;

/**
 * This enum is used to declare all the available events.
 *
 * @author Pierrick MARIE
 */
public enum EQoCIMPerformanceLogLevel {

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