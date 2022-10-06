package qocim.format.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import qocim.metamodel.QClass;
import qocim.model.*;
import qocim.utils.logs.QoCIMLogger;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public final class JsonQoCIMImport {

	public static QoCIndicator importQoCIndicator(final JsonObject jsonIndicator) {
		return new QoCIndicator(
			jsonIndicator.get(JsonQoCIMExport.NAME_PROPERTY).getAsString(),
			parseInt(jsonIndicator.get(QoCIndicator.ID).getAsString()));
	}

	public static QoCCriterion importQoCCriterion(final JsonObject jsonQoCCriterion) {
		return new QoCCriterion(
			jsonQoCCriterion.get(JsonQoCIMExport.NAME_PROPERTY).getAsString(),
			jsonQoCCriterion.get(QoCCriterion.ID).getAsString());
	}

	public static QoCDescription importQoCDescription(JsonObject jsonQoCDescription) {
		QoCDescription description = new QoCDescription(jsonQoCDescription.get(JsonQoCIMExport.NAME_PROPERTY).getAsString());

		for (JsonElement element : jsonQoCDescription.get(QoCDescription.KEYWORDS).getAsJsonArray()) {
			description.keywords().add(element.getAsString());
		}
		description.setDescription(jsonQoCDescription.get(QoCDescription.DESCRIPTION).getAsString());

		return description;
	}

	public static QoCValue<?> importQoCValue(final JsonObject jsonQoCValue) {

		QoCValue<String> value = new QoCValue<>(
			jsonQoCValue.get(JsonQoCIMExport.NAME_PROPERTY).getAsString(),
			parseInt(jsonQoCValue.get(QoCValue.ID).getAsString()),
			jsonQoCValue.get(QoCValue.VALUE).getAsString());

		JsonElement jsonElement = jsonQoCValue.get(QoCValue.CREATION_DATE);
		if (null != jsonElement) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(QoCValue.DATE_FORMAT);
				value.set(QoCValue.CREATION_DATE, formatter.parse(jsonElement.getAsString()));
			} catch (ParseException e) {
				QoCIMLogger.error(e.getMessage());
			}
		}

		setStringProperty(QoCValue.DEFINITION_ID, jsonQoCValue, value);

		return value;
	}

	public static QoCDefinition importQoCDefinition(final JsonObject jsonQoCDefinition) {

		JsonElement jsonElement;

		QoCDefinition definition = new QoCDefinition(
			jsonQoCDefinition.get(JsonQoCIMExport.NAME_PROPERTY).getAsString(),
			jsonQoCDefinition.get(QoCValue.ID).getAsString());

		setBoolProperty(QoCDefinition.IS_DEFAULT, jsonQoCDefinition, definition);
		setBoolProperty(QoCDefinition.IS_INVARIANT, jsonQoCDefinition, definition);

		// set direction
		jsonElement = jsonQoCDefinition.get(QoCDefinition.DIRECTION);
		if (null != jsonElement) {
			definition.setDirection(Direction.getEnum(jsonElement.getAsString()));
		} else {
			definition.setDirection(Direction.UNKNOWN);
		}

		// set provider uri
		try {
			jsonElement = jsonQoCDefinition.get(QoCDefinition.PROVIDER_URI);
			if (null != jsonElement) {
				definition.setProviderUri(new URI(jsonElement.getAsString()));
			} else {
				definition.setProviderUri(new URI(""));
			}
		} catch (URISyntaxException e) {
			QoCIMLogger.error(e.getMessage());
		}

		// set unit
		setStringProperty(QoCDefinition.UNIT, jsonQoCDefinition, definition);

		// set description
		jsonElement = jsonQoCDefinition.get(QoCDefinition.DESCRIPTION);
		if (null != jsonElement) {
			definition.setDescription(importQoCDescription(jsonElement.getAsJsonObject()));
		}
		// set min value
		jsonElement = jsonQoCDefinition.get(QoCDefinition.MIN_VALUE);
		if (null != jsonElement) {
			definition.setMinValue(importQoCValue(jsonElement.getAsJsonObject()));
		}
		// set max value
		jsonElement = jsonQoCDefinition.get(QoCDefinition.MAX_VALUE);
		if (null != jsonElement) {
			definition.setMaxValue(importQoCValue(jsonElement.getAsJsonObject()));
		}

		return definition;
	}

	private static void setStringProperty(final String propertyName, final JsonObject jsonObject, QClass element) {
		String property = "";
		JsonElement jsonProperty = jsonObject.get(propertyName);
		if (null != jsonProperty) {
			property = jsonProperty.getAsString();
		}
		element.set(propertyName, property);
	}

	private static void setBoolProperty(final String propertyName, final JsonObject jsonObject, QClass element) {
		Boolean property = false;
		JsonElement jsonProperty = jsonObject.get(propertyName);
		if (null != jsonProperty) {
			property = jsonProperty.getAsBoolean();
		}
		element.set(propertyName, property);
	}

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
