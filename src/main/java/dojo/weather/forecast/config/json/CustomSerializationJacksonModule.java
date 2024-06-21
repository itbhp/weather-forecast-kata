package dojo.weather.forecast.config.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;


public class CustomSerializationJacksonModule extends SimpleModule {

    public CustomSerializationJacksonModule() {
        super("CustomSerializationJacksonModule");
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        var simpleSerializers = new SimpleSerializers();
        simpleSerializers
            .addSerializer(new CityToStringSerializer());
        context.addSerializers(simpleSerializers);
    }
}
