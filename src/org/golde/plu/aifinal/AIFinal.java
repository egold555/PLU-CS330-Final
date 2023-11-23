package org.golde.plu.aifinal;

import java.io.IOException;
import org.golde.plu.aifinal.ai.EnumResponseType;
import org.golde.plu.aifinal.ui.TestUI;

public class AIFinal implements Runnable {

    private EnumResponseType RESPONSE_GENERATOR = EnumResponseType.GPT3_5;

    private static AIFinal instance;

    public static AIFinal getInstance() {
        if(instance == null) {
            instance = new AIFinal();
        }
        return instance;
    }

    @Override
    public void run() {

//        List<Setting> settings = RESPONSE_GENERATOR.getInstance().getSettings().getAllSettings();
//        Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Setting.class, Setting.NULL_SETTING).create();
//        System.out.println(GSON.toJson(settings));


        try {
            new TestUI().create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
