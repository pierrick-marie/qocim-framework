package qocim.format.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import qocim.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class JsonQoCIMExport {

	public static final  String NAME_PROPERTY = "name";
	public static final  String CRITERIA_PROPERTY = "criteria";
	public static final  String DEFINITIONS_PROPERTY = "definitions";
	public static final  String VALUES_PROPERTY = "values";

	public static JsonObject exportQoCCriterion(final QoCCriterion criterion) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(NAME_PROPERTY, criterion.name);
		jsonObject.addProperty(QoCCriterion.ID, criterion.id());

		return jsonObject;
	}

	public static JsonObject exportQoCDefinition(final QoCDefinition definition) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(NAME_PROPERTY, definition.name);
		jsonObject.addProperty(QoCDefinition.ID, definition.id());
		jsonObject.addProperty(QoCDefinition.IS_INVARIANT, definition.isInvariant());
		jsonObject.addProperty(QoCDefinition.DIRECTION, definition.direction().toString());
		jsonObject.addProperty(QoCDefinition.PROVIDER_URI, definition.providerUri().toString());
		jsonObject.addProperty(QoCDefinition.UNIT, definition.unit().toString());

		jsonObject.add(QoCDefinition.DESCRIPTION, exportQoCDescription(definition.desription()));

		return jsonObject;
	}

	public static JsonObject exportQoCDescription(final QoCDescription description) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(NAME_PROPERTY, description.name);
		jsonObject.addProperty(QoCDescription.DESCRIPTION, description.description());

		JsonArray jsonKeywords = new JsonArray();
		for(String keyword: description.keywords()) {
			jsonKeywords.add(keyword);
		}
		jsonObject.add(QoCDescription.KEYWORDS, jsonKeywords);

		return jsonObject;
	}

	public static JsonObject exportQoCValue(final QoCValue qocValue) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(NAME_PROPERTY, qocValue.name);
		jsonObject.addProperty(QoCValue.ID, qocValue.id());

		Date creationDate = qocValue.creationDate();
		DateFormat dateFormat = new SimpleDateFormat(QoCValue.DATE_FORMAT);
		jsonObject.addProperty(QoCValue.CREATION_DATE, dateFormat.format(creationDate));

		jsonObject.addProperty(QoCValue.DEFINITION_ID, qocValue.definitionId());
		jsonObject.addProperty(QoCValue.VALUE, qocValue.value().toString());

		return jsonObject;
	}

	public static JsonObject exportQoCIndicator(final QoCIndicator indicator) {

		final JsonObject jsonObject = new JsonObject();

		jsonObject.addProperty(NAME_PROPERTY, indicator.name);
		jsonObject.addProperty(QoCIndicator.ID, indicator.id());

		return jsonObject;
	}

	public static JsonObject exportEntireIndicator(QoCIndicator indicator) {

		final JsonObject jsonIndicator = new JsonObject();

		jsonIndicator.addProperty(NAME_PROPERTY, indicator.name);
		jsonIndicator.addProperty(QoCIndicator.ID, indicator.id());

		JsonArray jsonCriteria = new JsonArray();
		JsonArray jsonDefinitions = new JsonArray();
		for (QoCCriterion criterion: indicator.qocCriteria() ) {
			jsonCriteria.add(exportQoCCriterion(criterion));
			for (QoCDefinition definition: criterion.qocDefinitions() ) {
				jsonDefinitions.add(exportQoCDefinition(definition));
			}
		}
		jsonIndicator.add(CRITERIA_PROPERTY, jsonCriteria);
		jsonIndicator.add(DEFINITIONS_PROPERTY, jsonDefinitions);

		JsonArray jsonValues = new JsonArray();
		for (QoCValue<?> value: indicator.qocValues() ) {
			jsonValues.add(exportQoCValue(value));
		}
		jsonIndicator.add(VALUES_PROPERTY, jsonValues);

		return jsonIndicator;
	}
}
