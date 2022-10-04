package qocim.format.json;

import com.google.gson.JsonObject;
import qocim.model.*;


import java.util.List;

public final class JsonQoCIMExport {

	public static JsonObject exportQoCCriterion(final QoCCriterion criterion) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", criterion.name);
		jsonObject.addProperty("id", criterion.id());

		return jsonObject;
	}

	public static JsonObject exportQoCDefinition(final QoCDefinition definition) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", definition.name);
		jsonObject.addProperty("id", definition.id());
		jsonObject.addProperty("is invariant", definition.isInvariant());
		jsonObject.addProperty("direction", definition.direction().toString());
		jsonObject.addProperty("provider uri", definition.providerUri().toString());
		jsonObject.addProperty("unit", definition.unit().toString());

		return jsonObject;
	}

	public static JsonObject exportQoCDescription(final QoCDescription description) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("informal description", description.description());
		jsonObject.addProperty("keywords", description.keywords().toString());

		return jsonObject;
	}

	public static JsonObject exportQoCValue(final QoCValue qocValue) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", qocValue.name);
		jsonObject.addProperty("id", qocValue.id());
		jsonObject.addProperty("creation date", qocValue.creationDate().toString());
		jsonObject.addProperty("definition id", qocValue.definitionId());
		jsonObject.addProperty("indicator name", qocValue.container().name);
		jsonObject.addProperty("value", qocValue.value().toString());

		return jsonObject;
	}

	public static JsonObject exportIndicator(final QoCIndicator indicator) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty("name", indicator.name);
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
	public static JsonObject qocMetaData(final List<QoCIndicator> QoCIndicators) {
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
		return exportIndicator(indicator);
	}
}
