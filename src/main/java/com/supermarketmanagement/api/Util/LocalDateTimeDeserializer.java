package com.supermarketmanagement.api.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		String date = jsonParser.getText();
		return (date == null || date.trim().length() < 1 || (date != null && date.equalsIgnoreCase("null"))) ? null
				: LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DateTimeUtil.MYSQL_DATE_TIME_WITH_MILL_SEC));
	}

}
