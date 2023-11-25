package org.golde.plu.aifinal.ai.models;

import lombok.AllArgsConstructor;
import org.golde.plu.aifinal.ai.ResponseGenerator;

@AllArgsConstructor
public class NotImplementedResponseGenerator implements ResponseGenerator {

    private final String name;

    @Override
    public void generateResponse(String product, String product2, AsyncCallback<String> callback) {
        callback.onFailure(name + " AI has not implemented!");
    }
}
