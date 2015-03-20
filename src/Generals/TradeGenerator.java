package Generals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class TradeGenerator
{	
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();


		Referential ref = new Referential();	
		Generals gen = null;

		gen = LoadXML.init(ref);

		int simulate_days = Integer.parseInt(args[0]);
		int amount_per_book;
		int j;

		for (j = 0; j <= simulate_days; j++)
			for (Businessunit bu : gen.bu)
				for (Portfolio port :  bu.lpor)
					for (Book b : port.lb)
					{
						if (b.ins.equalsIgnoreCase("equity"))
						{
							Instrument t = bu.getInstrument(b.ins);
							amount_per_book = (int) (gen.total_buget * bu.ratio * port.ratio * b.ratio / 1000000);

							t.generate(gen, ref, amount_per_book, j);
						}  
					}

		// Create file BATCH MODE
		FileOutputStream fop = null;
		File file;
		try
		{
			file = new File("trades.xml");
			fop = new FileOutputStream(file);
			if (!file.exists())
				file.createNewFile();

			for (Tradeevents trade : gen.te)
			{
				String xmlString = trade.toXml();
				byte[] contentInBytes = xmlString.getBytes();
				fop.write(contentInBytes);
			}

			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println(estimatedTime / 1000);

	}	
}