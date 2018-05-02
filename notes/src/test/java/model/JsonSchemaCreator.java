package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.junit.Test;
import pl.dkatszer.inz.notes.model.CreateNoteRequest;

/**
 * Created by doka on 2018-01-09.
 */
public class JsonSchemaCreator {

    @Test
    public void printBody() throws JsonProcessingException {
        printJsonSchema(CreateNoteRequest.class);
    }

    private void printJsonSchema(Class<?> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        final JsonSchema jsonSchema = schemaGen.generateSchema(clazz);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
    }
}
