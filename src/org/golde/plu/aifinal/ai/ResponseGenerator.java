package org.golde.plu.aifinal.ai;

/**
 * Represents an AI that can generate a response to a prompt.
 */
public interface ResponseGenerator {

        void generateResponse(String product1, String product2, AsyncCallback<String> callback);

        default void shutdown(AsyncCallback<Void> callback) {
                callback.onSuccess(null);
        }

        default AISettings getSettings() {
                return new AISettings();
        }

        interface AsyncCallback<T> {
                void onSuccess(T result);
                void onFailure(String error);
        }

}
