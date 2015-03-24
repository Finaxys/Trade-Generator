package Generals;

	public class Book 
	{
		public String name;
		public String cur;
		public String ins;
		public int ratio;
		public	Portfolio pt;
		
		public Book (Portfolio pt, String name, String cur, String ins, int ratio)
		{
			this.pt = pt;
			this.name = name;
			this.cur = cur;
			this.ins = ins;
			this.ratio = ratio;
		}
	}