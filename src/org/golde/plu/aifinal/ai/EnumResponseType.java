package org.golde.plu.aifinal.ai;

import org.golde.plu.aifinal.ai.models.NotImplementedResponseGenerator;
import org.golde.plu.aifinal.ai.models.GPTResponseGenerator;
import org.golde.plu.aifinal.ai.models.LLamaCPPResponseGenerator;

/**
 * An enum that contains a single instance of each ResponseGenerator.
 */
public enum EnumResponseType {

    GPT3_5("GPT 3.5", new GPTResponseGenerator(GPTResponseGenerator.MODEL_3_5)),
    GPT4("GPT 4", new GPTResponseGenerator(GPTResponseGenerator.MODEL_4_0)),
    LLAMA_7("LLaMA 7B", new LLamaCPPResponseGenerator("llama-2-7b")),
    LLAMA_13("LLaMA 13B", new LLamaCPPResponseGenerator("llama-2-13b")),
    LLAMA_70("LLaMA 70B", new LLamaCPPResponseGenerator("llama-2-70b")),
    WIZARD_MEGA_13B("Wizard Mega 13B", new LLamaCPPResponseGenerator("wizard-mega-13b")),
    ORCA_7B("ORCA 7B", new LLamaCPPResponseGenerator("orca-2-7b")),
    ORCA_13B("ORCA 13B", new LLamaCPPResponseGenerator("orca-2-13b")),
    OPEN_HERMES_7B("Open Hermes 2.5 7B", new LLamaCPPResponseGenerator("openhermes-2.5-mistral-7b")),
    ;

    private final String niceName;
    private ResponseGenerator responseGeneratorInstance;

    EnumResponseType(String niceName) {
        this(niceName, null);
    }

    EnumResponseType(String niceName, ResponseGenerator responseGeneratorInstance) {
        this.niceName = niceName;

        //If response generator, then use NotImplementedResponseGenerator
        this.responseGeneratorInstance = (responseGeneratorInstance == null ? new NotImplementedResponseGenerator(niceName) : responseGeneratorInstance);
    }

    public String getNiceName() {
        return niceName;
    }

    public ResponseGenerator getInstance() {
        return responseGeneratorInstance;
    }

    public static EnumResponseType fromNiceName(String niceName) {
        for(EnumResponseType type : values()) {
            if(type.getNiceName().equalsIgnoreCase(niceName)) {
                return type;
            }
        }
        return null;
    }
}
