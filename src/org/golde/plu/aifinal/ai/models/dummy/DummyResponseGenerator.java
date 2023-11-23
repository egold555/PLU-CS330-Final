package org.golde.plu.aifinal.ai.models.dummy;

import com.google.gson.JsonObject;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.golde.plu.aifinal.ai.AISettings;
import org.golde.plu.aifinal.ai.ResponseGenerator;

public class DummyResponseGenerator implements ResponseGenerator {

    private final AISettings SETTINGS = new AISettings()
            .addString("Test String", "Hello World!")
            .addInteger("Test Integer", 5, 0, 10)
            .addDouble("Test Double", 5.5, 0, 10, 0.1)
            .addBoolean("Test Boolean", true);

    @Override
    public void generateResponse(String product, String product2, AsyncCallback<String> callback) {
        callback.onSuccess("This is a dummy response!");
    }

    @Override
    public AISettings getSettings() {
        return SETTINGS;

    }
}
