package qocim.format.json;

import com.google.gson.JsonObject;

import qocim.model.QoCCriterion;
import qocim.model.QoCDescription;
import qocim.model.QoCIndicator;
import qocim.model.QoCMetricDefinition;
import qocim.model.QoCMetricValue;

import java.util.List;

public final class JsonQoCIMExport {

	public static JsonObject criterion(QoCCriterion criterion) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", criterion.name());
		jsonObject.addProperty("id", criterion.id());

		return jsonObject;
	}

	public static JsonObject definition(QoCMetricDefinition definition) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", definition.name());
		jsonObject.addProperty("id", definition.id());
		jsonObject.addProperty("is-invariant", definition.isInvariant());
		jsonObject.addProperty("direction", definition.direction().toString());
		jsonObject.addProperty("provider-uri", definition.providerUri().toString());
		jsonObject.addProperty("unit", definition.unit().toString());

		return jsonObject;
	}

	public static JsonObject description(QoCDescription description) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("informal-description", description.informalDescription());
		jsonObject.addProperty("list-keywords", description.listKeywords().toString());

		return jsonObject;
	}

	public static JsonObject exportMetricValue(QoCMetricValue metricValue) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("id", metricValue.id());
		jsonObject.addProperty("creation date", metricValue.creationDate().toString());
		jsonObject.addProperty("modification date", metricValue.modificationDate().toString());
		jsonObject.addProperty("definition name", metricValue.getDefinition().name());
		jsonObject.addProperty("indicator name", metricValue.getIndicator().name());
		jsonObject.addProperty("value", metricValue.value().toString());

		return jsonObject;
	}

	public static JsonObject indicator(QoCIndicator indicator) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", indicator.name());
		jsonObject.addProperty("id", indicator.id());

		return jsonObject;
	}

	/**
	 * A method to export QoCEventMetaData as JsonObject
	 *
	 * @param QoCIndicators the meta-data to transform
	 *
	 * @return the expected JsonObject
	 */
	public static JsonObject qocMetaData(List<QoCIndicator> QoCIndicators) {
		// TODO fix the function
		return entireIndicator(QoCIndicators.get(0));
		/*
		final JsonObject jsonMetaData = new JsonObject();
		// Using try catch to get exceptions if some properties return null when getting the properties of the QoC objects in the map - It should not happened...
		try {
			qocMetaData.getQoCMetaData().entrySet().forEach(entry -> {
				// The object that will contain the QoC mata-data
				final JsonArray informationList = new JsonArray();
				// Loop over the QoC metric values
				for (final QoCMetricValue value : entry.getValue()) {
					// Get the definition that provided the value
					final QoCMetricDefinition definition = (QoCMetricDefinition) value.getDefinition().getObject();
					// The object to store the value
					final JsonObject qocList = new JsonObject();
					// Stores the values
					qocList.addProperty(definition.getId().getObject().toString(), value.value().toString());
					// Put the Json object in the list
					informationList.add(qocList);
				}
				// Index the current QoC metric values
				jsonMetaData.add(entry.getKey(), informationList);
			});
		} catch (final Exception exception) {
			QoCIMLogger.debug("QoCEventMetaData.toJson: " + exception.getMessage());
		}
		return jsonMetaData;
		*/
	}


	public static JsonObject entireIndicator(QoCIndicator indicator) {
		// TODO fix the function
		return indicator(indicator);
	}
}
