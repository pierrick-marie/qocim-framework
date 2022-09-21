package qocim.format.json;

import com.google.gson.JsonObject;

import qocim.model.QoCIndicator;
import qocim.model.QoCMetricDefinition;
import qocim.model.QoCMetricValue;

public final class JsonQoCIMImport {

	public static QoCIndicator qocMetaData(final JsonObject jsonMetaData) {

		final QoCIndicator qocIndicator = new QoCIndicator();

		jsonMetaData.entrySet().stream().forEach(streamMetaData -> {
			// final JsonArray array = ;
			/* Get the key of the meta-data: the qualified information */
			final Object information = streamMetaData.getKey();
			/*
			 * Get the value of the meta-data: an array of one element {definitionId :
			 * metricValue}
			 */
			streamMetaData.getValue().getAsJsonArray().get(0).getAsJsonObject().entrySet().stream()
					.forEach(streamValue -> {
						final QoCMetricDefinition definition = new QoCMetricDefinition();
						definition.setId(streamValue.getKey());
						final QoCMetricValue value = new QoCMetricValue();
						value.setObjectValue(streamValue.getValue().getAsString());
						value.setMetricDefinition(definition);
						definition.addMetricValues(value);
						qocIndicator.addMetricValue(value);
					});
		});
		return qocIndicator;
	}

}
