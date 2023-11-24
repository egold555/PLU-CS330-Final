package org.golde.plu.aifinal.ai.models.llama;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.swing.SwingUtilities;
import org.golde.plu.aifinal.ai.AISettings;
import org.golde.plu.aifinal.ai.ResponseGenerator;

public class LLamaCPPResponseGenerator implements ResponseGenerator {

    private static final String EXE_LOCATION;
    private static final File MODEL_LOCATION;

    private static final String MODEL_FILE_NAME = "ggml-model-q4_0.gguf";

    private final String MODEL_NAME;

    private final String THE_PROMPT = "Transcript of a dialog, where the User interacts with an Assistant named Bob. Bob is helpful, kind, honest, good at writing, and never fails to answer the User's requests immediately and with precision.\n" +
            "\n" +
            "User: Hello, Bob.\n" +
            "Bob: Hello. How may I help you today?\n" +
            "\n" +
            "User: I will give you two different product descriptions. Please tell me the difference between the two products as accurately as you can, without making anything up. I would like the differences to include a bullet point list per product, and a summary of the sifferences between the two products.\n" +
            "\n" +
            "**Product 1**\n" +
            "%product1%\n" +
            "\n" +
            "**Product 2**\n" +
            "%product2%\n" +
            "\n" +
            "Bob: "

            ;

    static {
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        EXE_LOCATION = "\"" + dir + "\\llama.cpp\\main.exe\"";

        MODEL_LOCATION = new File(dir, "llama.cpp\\models");

        System.out.println("EXE_LOCATION = " + EXE_LOCATION);
        System.out.println("MODEL_LOCATION = " + MODEL_LOCATION.getAbsolutePath());
    }

    private final File TMP_FILE = new File("tmp/llama_prompt.txt");

    private final AISettings SETTINGS = new AISettings()
            .addInteger("Max Words (N)", -1)
            .addDouble("Repeat Penalty", 1.0, 0.0, 5.0, 0.1)
            .addDouble("Temperature", 0.8, 0.0, 5.0, 0.1)
            .addBoolean("GPU", true)
            .addInteger("Threads", 6)
            .addInteger("Seed", -1)
            .addInteger("top-k", 40)
            .addDouble("top-p", 0.9, 0.0, 1.0, 0.1)
            .addDouble("min-p", 0.1, 0.0, 1.0, 0.1)
            .addInteger("repeat-last-n", 64)
            ;

    private Process llamaCPP;
    private boolean llamaThreadIsRunning = false;

    public LLamaCPPResponseGenerator(String modelFolder) {
        this.MODEL_NAME = modelFolder;
    }

    @Override
    public void generateResponse(String product1, String product2, AsyncCallback<String> callback) {

        try {

            final int maxWords = (int) SETTINGS.getSettingByName("Max Words (N)").getValue();
            final int GPU = (boolean) SETTINGS.getSettingByName("GPU").getValue() ? 100 : 0;
            final double repeatPenalty = (double) SETTINGS.getSettingByName("Repeat Penalty").getValue();
            final double temperature = (double) SETTINGS.getSettingByName("Temperature").getValue();
            final int repeatLastN = (int) SETTINGS.getSettingByName("repeat-last-n").getValue();
            final int threads = (int) SETTINGS.getSettingByName("Threads").getValue();
            final int seed = (int) SETTINGS.getSettingByName("Seed").getValue();
            final int topK = (int) SETTINGS.getSettingByName("top-k").getValue();
            final double topP = (double) SETTINGS.getSettingByName("top-p").getValue();
            final double minP = (double) SETTINGS.getSettingByName("min-p").getValue();

            //Write the prompt to a file
            String prompt = THE_PROMPT.replace("%product1%", product1).replace("%product2%", product2);
            PrintWriter pw = new PrintWriter(TMP_FILE);
            pw.write(prompt);
            pw.flush();
            pw.close();

            //main.exe -m ../llama.cpp/models/wizard-mega-13b/ggml-model-q4_0.gguf -n -1 -c 4096 --repeat_penalty 1.0 --gpu-layers 100 --color -f ../llama.cpp/prompts/eg-hd-4.txt

            String cmd = EXE_LOCATION +
                    " -m \"" + MODEL_LOCATION + "/" + MODEL_NAME + "/" + MODEL_FILE_NAME + "\" " + //MODEL
                    "-n " + maxWords + " " + //MAX WORDS
                    "--gpu-layers " + GPU + " " + //GPU
                    "-c 4096" + " " + //CTX SIZE
                    "--repeat_penalty " + repeatPenalty + " " +
                    "--repeat-last-n " + repeatLastN + " " +
                    "-t " + threads + " " + //THREADS
                    "--temp " + temperature + " " + //TEMPERATURE
                    "-s " + seed + " " + //SEED
                    "--top-k " + topK + " " + //TOP K
                    "--top-p " + topP + " " + //TOP P
                    "--min-p " + minP + " " + //MIN P
                    "-f \"" + TMP_FILE.getAbsolutePath() + "\"" //PROMPT FILE
                    ;

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "\"" + cmd + "\"");
            pb.redirectErrorStream(true);



            System.out.println("Running: " + cmd);

            llamaCPP = pb.start();
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(llamaCPP.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(llamaCPP.getErrorStream()));

            llamaThreadIsRunning = true;

            new Thread(() -> {

                System.out.println("> Starting LLaMa output thread");
                callback.onSuccess("> Starting LLaMa output thread");

                while(llamaThreadIsRunning) {
                    try {
                        String line = null;
                        while ((line = stdInput.readLine()) != null) {
                            System.out.println(line);

                            //shove back to the UI thread
                            final String finalLine = line;
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    callback.onSuccess(finalLine);
                                }
                            });

                        }

                        while ((line = stdError.readLine()) != null) {
                            System.err.println(line);
                            final String finalLine = line;
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    callback.onFailure(finalLine);
                                }
                            });
                        }

                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess("> Process Finished!");
            }, "LLama output thread").start();

        }
        catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        }

    }

    @Override
    public void shutdown(AsyncCallback<Void> callback) {

        llamaThreadIsRunning = false;

        if(llamaCPP != null) {
            llamaCPP.destroy();
            callback.onSuccess(null);
        }
        else {
            callback.onFailure("Llama is not running!");
        }


    }

    @Override
    public AISettings getSettings() {
        return SETTINGS;
       // return new AISettings().addBoolean("NOT IMPLEMENTED", true);
    }

}
