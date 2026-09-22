package datasource.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DSGameFieldConverter implements AttributeConverter<DSGameField, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DSGameField attribute) {
        try {
            return mapper.writeValueAsString(attribute.getField());
        } catch (Exception e) {
            throw new RuntimeException("Error of serializing " + e.getMessage());
        }
    }

    @Override
    public DSGameField convertToEntityAttribute(String dbData) {
        try {
            int[][] field = mapper.readValue(dbData, int[][].class);
            DSGameField gameField = new DSGameField();
            gameField.setField(field);
            return gameField;
        }
        catch (Exception e) {
            throw new RuntimeException("Error of deserealizing DSGameField " + e.getMessage());
        }
    }
}
