package test.qocim.model;

import qocim.model.QoCIndicator;

public class MyIndicator extends QoCIndicator {

	public static final String NAME = "My Indicator";
	public static final Integer ID = 1;

	public MyIndicator() {
		super(NAME, ID);
	}
}
