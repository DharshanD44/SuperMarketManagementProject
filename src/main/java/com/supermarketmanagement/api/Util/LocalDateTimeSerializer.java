package com.supermarketmanagement.api.Util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	@Override
	public void serialize(LocalDateTime localDateTime, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {

		if (localDateTime != null) {
			arg1.writeString(localDateTime
					.format(DateTimeFormatter.ofPattern(DateTimeUtil.MYSQL_DATE_TIME_WITH_MILL_SEC)));
		}

	}

}

