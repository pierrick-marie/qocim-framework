package qocim.datamodel.freshness.definitions.values;

import qocim.datamodel.freshness.definitions.values.FreshnessValue;

import java.util.Date;

public final class MinFreshnessValue extends FreshnessValue {

	public static final Integer ID = -2;

	public static final Integer VALUE = 0;

	public MinFreshnessValue() {
		super(ID, VALUE);
	}
}
