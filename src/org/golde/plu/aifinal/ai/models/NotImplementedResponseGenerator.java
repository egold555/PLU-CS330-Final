package org.golde.plu.aifinal.ai.models;

import lombok.AllArgsConstructor;
import org.golde.plu.aifinal.ai.ResponseGenerator;

/**
 * Response generator that is used when the AI has not been implemented yet.
 * This is used to prevent the program from crashing when the AI is not implemented.
 *
 * All this does is return an error message.
 */
@AllArgsConstructor
public class NotImplementedResponseGenerator implements ResponseGenerator {

    private final String name;

    @Override
    public void generateResponse(String product, String product2, AsyncCallback<String> callback) {
        callback.onFailure(name + " AI has not implemented!");
    }
}
