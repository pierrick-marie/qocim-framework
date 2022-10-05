package qocim.format.json;

import com.google.gson.JsonObject;
import qocim.model.QoCDefinition;
import qocim.model.QoCIndicator;

public final class JsonQoCIMImport {

	public static QoCIndicator qocMetaData(final JsonObject jsonMetaData) {

		final QoCIndicator qocIndicator = new QoCIndicator("JSON name", -1);

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
						final QoCDefinition definition = new QoCDefinition("JSON definition", streamValue.getKey());
//						definition.setId();
//						final QoCMetricValue value = new QoCMetricValue();
//						value.setObjectValue(streamValue.getValue().getAsString());
//						value.setMetricDefinition(definition);
//						definition.addMetricValues(value);
//						qocIndicator.addMetricValue(value);
					});
		});
		return qocIndicator;
	}

}
