package generals;

import java.io.File;
import java.util.logging.Logger;

import domain.Businessunit;

public class OutputManager {
    private static OutputManager instance = new OutputManager();
    static final String OUTPUT_PATH = "trades/";
    static final String OUTPUT_ENCODING = "UTF-8";

    private OutputManager() {
        File check = new File(OUTPUT_PATH + "test");

        if (!check.exists())
            check.getParentFile().mkdirs();
    }

    public static OutputManager getInstance() {
        return instance;
    }

    public void outputTrades()
    {
        for (Businessunit bu : Generals.getInstance().getBusinessunits())
            for (Output output : bu.getOutputs())
                output.outputTrades();
    }
}
