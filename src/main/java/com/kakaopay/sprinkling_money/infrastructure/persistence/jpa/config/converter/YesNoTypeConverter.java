package com.kakaopay.sprinkling_money.infrastructure.persistence.jpa.config.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YesNoTypeConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean b) {
        if (b == true) {
            return 1;
        }

        return 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer s) {
        if (s == 1) {
            return true;
        }

        return false;
    }

}