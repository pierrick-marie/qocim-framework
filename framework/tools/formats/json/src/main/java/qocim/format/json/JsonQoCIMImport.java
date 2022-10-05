package qocim.format.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import qocim.model.*;
import qocim.utils.logs.QoCIMLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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

		for(JsonElement element : jsonQoCDescription.get(QoCDescription.KEYWORDS).getAsJsonArray()) {
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

		try {
			SimpleDateFormat formatter =new SimpleDateFormat(QoCValue.DATE_FORMAT);
			value.set(QoCValue.CREATION_DATE, formatter.parse(jsonQoCValue.get(QoCValue.CREATION_DATE).getAsString()));
		} catch (ParseException e) {
			QoCIMLogger.error(e.getMessage());
		}

		value.set(QoCValue.DEFINITION_ID, jsonQoCValue.get(QoCValue.DEFINITION_ID).getAsString());

		return value;
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
