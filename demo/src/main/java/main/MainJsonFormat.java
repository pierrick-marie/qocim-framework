package main;

import qocim.datamodel.freshness.FreshnessFacade;
import qocim.datamodel.precision.PrecisionFacade;
import qocim.format.json.JsonQoCIMExport;
import qocim.information.InformationImpl;
import qocim.information.QInformation;
import qocim.model.QoCIndicator;
import qocim.model.QoCMetricValue;
import qocim.utils.logs.QoCIMLogger;

public class MainJsonFormat {

	private final static Integer TIME_TO_SLEEP = 50;

	public static void main(String[] args) {
		new MainJsonFormat();
	}

	public MainJsonFormat() {
		QoCIMLogger.loadDefaultConfig();

		// A new freshness facade used to compute QoC value with dates
		final FreshnessFacade facade = initNewSimpleFacade();

		// A new information (Information.class)
		final QInformation information = initNewInformation("Temperature = 40°C");

		// Waiting TIME_TO_SLEEP milliseconds to get a "real" QoC value
		try {
			Thread.sleep(TIME_TO_SLEEP);
		} catch (final InterruptedException exception) {
			QoCIMLogger.logger.warning(exception.toString());
		}

		final QoCMetricValue value = facade.qualifyInformation(information);

		QoCIMLogger.logger.info(JsonQoCIMExport.exportMetricValue(value).toString());

		QoCIMLogger.logger.info(JsonQoCIMExport.indicator(value.getIndicator()).toString());

		QoCIMLogger.logger.info(facade.getIndicator().toString());

		QoCIMLogger.logger.info(facade.getCriterion().toString());

		QoCIMLogger.logger.info(facade.getDescription().toString());
		QoCIMLogger.logger.info(facade.getDescription().getListKeywords().toString());

		QoCIMLogger.logger.info(facade.getDefinition().toString());
		QoCIMLogger.logger.info(facade.getDefinition().direction().toString());
		QoCIMLogger.logger.info(facade.getDefinition().providerUri().toString());
		QoCIMLogger.logger.info(facade.getDefinition().unit().toString());
		QoCIMLogger.logger.info(facade.getDefinition().isInvariant().toString());
		QoCIMLogger.logger.info(facade.getDefinition().defaultDefinition().toString());
		QoCIMLogger.logger.info(facade.getDefinition().getMinValue().toString());
		QoCIMLogger.logger.info(facade.getDefinition().getMaxValue().toString());
		QoCIMLogger.logger.info(facade.getDefinition().getDescription().toString());
		QoCIMLogger.logger.info(value.toString());

		System.exit(0);
	}

	/**
	 * It creates a new Information
	 *
	 * @param data
	 *            The value of the information.
	 * @return The new information.
	 */
	private QInformation initNewInformation(final Object data) {

		final InformationImpl information = new InformationImpl();
		information.setData(data);
		information.setName("Toto");

		return information;
	}

	/**
	 * It created a new Freshness facade and setup the listener that will receive
	 * the asynchronous QoC evaluations. The facade uses the SimpleDefinition of
	 * freshness. This definition computes the freshness with current time.
	 *
	 * @return The freshness facade.
	 */
	private FreshnessFacade initNewSimpleFacade() {

		final FreshnessFacade facade = new FreshnessFacade();

		// Setup the QoC listener
		facade.addQoCListener(new QoCListener());

		return facade;
	}
}
