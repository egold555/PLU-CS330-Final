package org.golde.plu.aifinal.ai.models.dummy;

import org.golde.plu.aifinal.ai.AISettings;

public class DummyResponseGenerator2 extends DummyResponseGenerator{

    @Override
    public AISettings getSettings() {

        AISettings settings = new AISettings();

        for(int i = 0; i < 100; i++) {
            settings.addString("Test String " + i, "Hello World!");
        }

        return settings;
    }
}
