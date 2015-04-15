package generals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Referential;
import domain.TradeEvent;

public class Report {
    Referential ref;
    private List<TradeEvent> liste = null;
    private static final Logger LOGGER = Logger.getLogger(Report.class
            .getName());

    private static final String OUTPUT_PATH = "report.csv";
    private static final String OUTPUT_ENCODING = "UTF-8";
    private PrintWriter writer;
    private static Report INSTANCE = new Report();

    private Report() {
        super();

        liste = new ArrayList<TradeEvent>();
        try {
            writer = new PrintWriter(OUTPUT_PATH, OUTPUT_ENCODING);
            writer.write("Date" + "," + "BussinessUnit" + "," + "Portfolio"
                    + "," + "Book" + "," + "Instrument" + "," + "Sens" + ","
                    + "Nombre de transactions" + "," + "Montant engage" + " EU"
                    + "," + System.lineSeparator());

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, "Could not open Report file", e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.log(Level.WARNING, "Encoding Report file failed", e);
        }

    }
    
    public static Report getInstance() {
        return INSTANCE;
    }

    public void add(TradeEvent te) {

        liste.add(te);
    }

    public void concatSortOutput() {

        Collections.sort(liste);
    }

    public void writeCsTrade(TradeEvent te, int nombreOp, double montant)
            throws Exception {

        String s = System.lineSeparator();

        String date = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        DecimalFormat df = new DecimalFormat();

        df.setRoundingMode(RoundingMode.HALF_UP);
        df.applyPattern("###.##");
        df.setMaximumFractionDigits(2); // arrondi à 2 chiffres apres la
                                        // virgules
        String montantstr = df.format(montant);
        montantstr = montantstr.replaceAll(",", ".");

        date = formater.format(te.getDate());
        writer.write(date + ","
                + te.getBook().getPortFolios().getBu().getName() + ","
                + te.getBook().getPortFolios().getName() + ","
                + te.getBook().getName() + "," + te.getInstrument().getName()
                + "," + te.getWay() + "," + nombreOp + "," + montantstr + ","
                + s);
    }

    public String printDate(Date d) 
    {
        return MessageFormat.format("{0}-{1}-{2}", d.getDate(), d.getMonth(),
                d.getYear());
    }

    public void report(List<TradeEvent> lists) {

        File check = new File(OUTPUT_PATH);

        if (!check.exists())
            check.getParentFile().mkdirs();

        try {
            TradeEvent te;
            te = lists.get(0);
            int nombreTransaction = 1;
            double ms = 0;
            double me = 0;
            int ne = 0;
            int ns = 0;
            double montant = te.getAmount();
            int i = 0;

            while (i < lists.size()) {
                if (i == lists.size() - 1) {
                    montant = montant + lists.get(i).getAmount();
                    nombreTransaction++;
                    writeCsTrade(te, nombreTransaction, montant);

                    if (!"sell".equalsIgnoreCase(lists.get(i).getWay().name()))
                    {
                        ms = ms + montant;
                        ns = ns + nombreTransaction;
                    } else {
                        me = me + montant;
                        ne = ne + nombreTransaction;
                    }
                }
                else
                {
                    if (lists.get(i).compareTo(te) == 0) {
                        montant = montant + lists.get(i).getAmount();
                        nombreTransaction++;
                    } else {
                        writeCsTrade(te, nombreTransaction, montant);

                        if (!"sell".equalsIgnoreCase(lists.get(i).getWay().name()))
                        {
                            ms = ms + montant;
                            ns = ns + nombreTransaction;
                        } else {
                            me = me + montant;
                            ne = ne + nombreTransaction;
                        }
                        nombreTransaction = 1;
                        montant = lists.get(i).getAmount();

                    }

                    te = lists.get(i);
                }
                i++;
            }

            ns = ns + ne;

            writer.close();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not create Report", e);
        }
    }
}
