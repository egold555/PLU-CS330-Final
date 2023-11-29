package org.golde.plu.aifinal.ai.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.golde.plu.aifinal.ai.AISettings;
import org.golde.plu.aifinal.ai.ResponseGenerator;

/**
 * This response generator uses the GPT-3.5 and GPT-4 API to generate responses.
 */
public class GPTResponseGenerator implements ResponseGenerator {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static String API_KEY = null;
    private static final String PROMPT = "I will give you two different product descriptions. Please tell me the difference between the two products as accurately as you can, without making anything up. I would like the differences to include a bullet point list per product, and a summary of the sifferences between the two products.\n" +
            "\n" +
            "Product 1:\n" +
            "%product1%" +
            "\n" +
            "Product 2:\n" +
            "%product2%";

    public static final String MODEL_3_5 = "gpt-3.5-turbo-1106";
    public static final String MODEL_4_0 = "gpt-4-0613";

    private final String MODEL;

    private final AISettings SETTINGS = new AISettings()
            .addDouble("Temperature", 1, 0d, 2d, 0.1)
            .addInteger("Max Tokens", 256, 1, 4096)
            .addDouble("Top P", 1, 0d, 1d, 0.1)
            .addDouble("Frequency Penalty", 0, 0d, 1d, 0.1)
            .addDouble("Presence Penalty", 0, 0d, 1d, 0.1)
            ;

    // Load the API Key from the file.
    static {
        File apiKeyFile = new File("llama.cpp/models/GPT/api_key.txt");
        if(apiKeyFile.exists()) {
            try {
                API_KEY = Files.readString(apiKeyFile.toPath());
                if(API_KEY.toUpperCase().contains("YOUR API KEY")) {
                    System.err.println("GPT API Key file exists but is not set up. Please set up the API Key file. " + apiKeyFile.getAbsolutePath());
                }
                else {
                    System.out.println("Successfully loaded GPT API Key from file");
                }
            } catch (IOException e) {
                System.out.println("Failed to load API Key from file: " + e.getMessage());
                e.printStackTrace();
            }
        }
        else {
            System.err.println("API Key file does not exist: " + apiKeyFile.getAbsolutePath());
        }
    }

    public GPTResponseGenerator(String model) {
        this.MODEL = model;
    }

    @Override
    public void generateResponse(String product1, String product2, AsyncCallback<String> callback) {

        final double temperature = (double) SETTINGS.getSettingByName("Temperature").getValue();
        final int maxTokens = (int) SETTINGS.getSettingByName("Max Tokens").getValue();
        final double topP = (double) SETTINGS.getSettingByName("Top P").getValue();
        final double frequencyPenalty = (double) SETTINGS.getSettingByName("Frequency Penalty").getValue();
        final double presencePenalty = (double) SETTINGS.getSettingByName("Presence Penalty").getValue();


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        JsonObject json = new JsonObject();
        json.addProperty("model", MODEL);
        json.addProperty("temperature", temperature);
        if(maxTokens != -1) {
            json.addProperty("max_tokens", maxTokens);
        }
        json.addProperty("top_p", topP);
        json.addProperty("frequency_penalty", frequencyPenalty);
        json.addProperty("presence_penalty", presencePenalty);


        JsonArray jsonArray = new JsonArray();
        JsonObject inside = new JsonObject();
        inside.addProperty("role", "user");

        String prompt = PROMPT.replace("%product1%", product1).replace("%product2%", product2);
//        System.out.println(prompt);

        inside.addProperty("content", prompt);
        jsonArray.add(inside);

        json.add("messages", jsonArray);

        System.out.println(json.toString());

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);

                    JsonObject jsonResp = new Gson().fromJson(responseBody, JsonObject.class);
                    JsonArray choices = jsonResp.get("choices").getAsJsonArray();
                    JsonElement text = choices.get(0).getAsJsonObject().get("message");
                    String theText = text.getAsJsonObject().get("content").getAsString();
                    callback.onSuccess(theText);

                } else {
                    System.out.println("Request failed");
                    System.out.println(response.body().string());
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure(e.getMessage());
            }
        });

    }

    @Override
    public AISettings getSettings() {
        return SETTINGS;
    }
}
