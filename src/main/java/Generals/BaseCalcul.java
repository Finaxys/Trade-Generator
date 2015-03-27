package Generals;

import java.util.Random;

public enum BaseCalcul
{
	methode1, methode2, methode3;

	public static BaseCalcul getRandom()
	{
		// TODO Auto-generated method stub
		Random random = new Random();
		return BaseCalcul.values()[random
				.nextInt(BaseCalcul.values().length - 1)];
	}
}
