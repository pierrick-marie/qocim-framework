package qocim.datamodel.freshness.definitions.values;

import qocim.datamodel.freshness.definitions.values.FreshnessValue;

import java.util.Date;

public final class MaxFreshnessValue extends FreshnessValue {

	public static final Integer ID = -1;

	public static final Integer VALUE = 3600;

	public MaxFreshnessValue() {
		super(ID, VALUE);
	}
}
