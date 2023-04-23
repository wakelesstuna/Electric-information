package net.wakelesstuna.electricinformation.entites.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.wakelesstuna.electricinformation.clients.enums.PriceArea;

@Converter(autoApply = true)
public class PriceAreaConverter implements AttributeConverter<PriceArea, String> {
    @Override
    public String convertToDatabaseColumn(PriceArea attribute) {
        return attribute.getCode();
    }

    @Override
    public PriceArea convertToEntityAttribute(String dbData) {
        return PriceArea.getEnumFromCode(dbData);
    }
}
