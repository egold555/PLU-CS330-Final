package org.golde.plu.aifinal.ai;

import com.google.gson.JsonObject;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ResponseGenerator {

        void generateResponse(String product1, String product2, AsyncCallback<String> callback);

//        default void initalize(AsyncCallback<Void> callback) {
//                callback.onSuccess(null);
//        }

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
