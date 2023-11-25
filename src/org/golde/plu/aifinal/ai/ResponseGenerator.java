package org.golde.plu.aifinal.ai;

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
