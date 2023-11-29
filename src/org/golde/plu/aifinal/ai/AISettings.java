package org.golde.plu.aifinal.ai;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * A class to store settings for an AI.
 */
public class AISettings {

    private List<Setting> settings = new ArrayList<>();

    public AISettings addString(String name, String defaultValue) {
        settings.add(new Setting<String>(name, String.class, defaultValue));
        return this;
    }

    public AISettings addInteger(String name, int defaultValue) {
        return addInteger(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public AISettings addInteger(String name, int defaultValue, int min, int max) {
        settings.add(new Setting<Integer>(name, Integer.class, defaultValue, min, max, 1));
        return this;
    }

    public AISettings addDouble(String name, double defaultValue, double min, double max, double incrementAmount) {
        settings.add(new Setting<Double>(name, Double.class, defaultValue, min, max, incrementAmount));
        return this;
    }

    public AISettings addBoolean(String name, boolean defaultValue) {
        settings.add(new Setting<Boolean>(name, Boolean.class, defaultValue));
        return this;
    }

    public List<Setting> getAllSettings() {
        return settings;
    }

    public void resetToDefault() {
        for(Setting setting : settings) {
            setting.setValue(setting.getDefaultValue());
        }
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();

        for(Setting setting : settings) {
            sb.append("  - " + setting.getName()).append(": ").append(setting.getValue()).append("\n");
        }

        return sb.toString();
    }

    public Setting getSettingByName(String name) {
    	for(Setting setting : settings) {
    		if(setting.getName().equals(name)) {
    			return setting;
    		}
    	}
    	return null;
    }

    @Getter
    public static class Setting<T> implements JsonSerializer<Setting>, JsonDeserializer<Setting> {

        public static final Setting<?> NULL_SETTING = new Setting<>("NULL_SETTING", Object.class, "NULL_SETTING");

        private final String name;
        private final Class<T> type;
        private final T defaultValue;

        @Setter
        private T value;

        private T min = null;
        private T max = null;

        private T incrementAmount = null;

        public Setting(String name, Class<T> type, T defaultValue) {
            this.name = name;
            this.type = type;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
        }

        public Setting(String name, Class<T> type, T defaultValue, T min, T max, T incrementAmount) {
            this.name = name;
            this.type = type;
            this.defaultValue = defaultValue;
            this.min = min;
            this.max = max;
            this.incrementAmount = incrementAmount;
            this.value = defaultValue;
        }

        public boolean hasMinMax() {
            return min != null && max != null && incrementAmount != null && (type == Double.class || type == Integer.class);
        }

        public String getFormattedName() {
            return name + ": (" + value.toString() + ")";
        }

        @Override
        public Setting deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            String iName = json.getAsJsonObject().get("name").getAsString();
            Class iType = null;


            try {
                iType = Class.forName(json.getAsJsonObject().get("type").getAsString());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            if(iType == String.class) {

                String iDefaultValue = json.getAsJsonObject().get("defaultValue").getAsString();

                return new Setting<String>(iName, iType, iDefaultValue);
            }
            else if(iType == Integer.class) {

                Integer iDefaultValue = json.getAsJsonObject().get("defaultValue").getAsInt();

                if(json.getAsJsonObject().has("min") && json.getAsJsonObject().has("max")){
                    Integer iMin = json.getAsJsonObject().get("min").getAsInt();
                    Integer iMax = json.getAsJsonObject().get("max").getAsInt();

                    return new Setting<Integer>(iName, iType, iDefaultValue, iMin, iMax, 1);
                }
                else {
                    return new Setting<Integer>(iName, iType, iDefaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
                }


            }
            else if(iType == Double.class) {

                Double iDefaultValue = json.getAsJsonObject().get("defaultValue").getAsDouble();
                Double iMin = json.getAsJsonObject().get("min").getAsDouble();
                Double iMax = json.getAsJsonObject().get("max").getAsDouble();
                Double iIncrementAmount = json.getAsJsonObject().get("incrementAmount").getAsDouble();

                return new Setting<Double>(iName, iType, iDefaultValue, iMin, iMax, iIncrementAmount);
            }
            else if(iType == Boolean.class) {

                Boolean iDefaultValue = json.getAsJsonObject().get("defaultValue").getAsBoolean();

                return new Setting<Boolean>(iName, iType, iDefaultValue);
            }
            else {
                throw new JsonParseException("Unknown type: " + iType.getName());
            }

        }

        @Override
        public JsonElement serialize(Setting src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();

            obj.addProperty("name", src.name);
            obj.addProperty("type", src.type.getName());

            if(src.type == String.class) {
                obj.addProperty("defaultValue", (String) src.defaultValue);
            }
            else if(src.type == Integer.class) {
                obj.addProperty("defaultValue", (Integer) src.defaultValue);

                if((int)src.min != Integer.MIN_VALUE && (int)src.max != Integer.MAX_VALUE) {
                    obj.addProperty("min", (Integer) src.min);
                    obj.addProperty("max", (Integer) src.max);
                }

            }
            else if(src.type == Double.class) {
                obj.addProperty("defaultValue", (Double) src.defaultValue);
                obj.addProperty("min", (Double) src.min);
                obj.addProperty("max", (Double) src.max);
                obj.addProperty("incrementAmount", (Double) src.incrementAmount);
            }
            else if(src.type == Boolean.class) {
                obj.addProperty("defaultValue", (boolean) src.defaultValue);
            }

            else {
                throw new JsonParseException("Unknown type: " + src.type.getName());
            }

            return obj;
        }
    }

}
