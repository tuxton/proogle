import java.util.HashMap;
import java.util.HashSet;

public class PageRank {
	
	//Atributos
	public double c; //dampening factor
	private int countDocuments; //||v||
	private HashMap<String,HashSet<String> > hashPagePointTo; //I(p)
	private HashMap<String,Double> hashPR; //PR(p)
	private HashMap<String,Integer> hashCountPagesPointedBy; //||o(q)||

	//Metodos
	PageRank()
	{
		hashPagePointTo = new  HashMap<String,HashSet<String> >();
		hashPR = new HashMap<String,Double>();
		hashCountPagesPointedBy = new HashMap<String,Integer>();
		c = 0.85;
		countDocuments = 0;
	}
	
	private void setIP(String nameSourcePage,Object[] arrayPageNamesRef)
	{
		String nameDestPage;
		for ( int i = 0 ; i < arrayPageNamesRef.length ; ++i )
		{
			nameDestPage = (String)arrayPageNamesRef[i];
			if (hashPagePointTo.containsKey(nameDestPage))
				hashPagePointTo.get(nameDestPage).add(nameSourcePage);
			else
			{
				HashSet<String> hashPages = new HashSet<String>();
				hashPages.add(nameSourcePage);
				hashPagePointTo.put(nameDestPage, hashPages);
			}
		}
	}
	
	private void setInitialPR()
	{
		Object[] arrayNamePages = hashPR.keySet().toArray();
		String namePage;
		for (int i = 0 ; i < arrayNamePages.length ; ++i)
		{
			namePage = (String) arrayNamePages[i];
			hashPR.put(namePage, new Double(1/countDocuments));
		}
	}
	
	public void processInfoPage(String nameSourcePage,Object[] arrayPageNamesRef)
	{
		//Set ||V||
		countDocuments++;
		
		if (arrayPageNamesRef != null)
		{
			//Set I(p)
			setIP(nameSourcePage,arrayPageNamesRef);
		
			//Set ||O(q)||
			int total = arrayPageNamesRef.length;
			hashCountPagesPointedBy.put(nameSourcePage, new Integer(total));
		}
		
		hashPR.put(nameSourcePage, new Double(0.0));
			
	}
	
	public void calculatePR()
	{
		setInitialPR();
		
		Object[] arrayNamePages = hashPR.keySet().toArray();
		String namePage;
		double newPR;
		Object[] arrayIP; //I(p)
		double distPR = 0.0; //Lo usamos para ver cuando se estan estacionando los valores
		double oldPR = 0.0;
		
		do
		{
			distPR = 0.0;
			for (int i = 0 ; i < arrayNamePages.length ; ++i)
			{
				namePage = (String) arrayNamePages[i];
				
				//---
				//System.out.println("Probando:" + namePage + "en:" + hashPagePointTo );
				//---
				
				if (hashPagePointTo.containsKey(namePage))
				{	
					arrayIP = hashPagePointTo.get(namePage).toArray();
					newPR = 0.0;
					String q;
					double PRQ; //PR(q)
					int OQ;//||O(q)||
					for (int j = 0 ; j < arrayIP.length ; ++j)
					{
						q = (String) arrayIP[j];
						PRQ = hashPR.get(q).doubleValue();
						OQ = hashCountPagesPointedBy.get(q).intValue();
						newPR += (PRQ/OQ) + (c/countDocuments);
					}
					newPR = (1 - c) * newPR;
					oldPR = hashPR.get(namePage).doubleValue();
					distPR += Math.pow(Math.abs(newPR - oldPR),2.0);
					hashPR.put(namePage, new Double(newPR));
				}
			}
			distPR = Math.sqrt(distPR);
		}
		while (distPR > 0.01);
	}
	
	public void showRanking()
	{
		Object[] arrayNamesPages = hashPR.keySet().toArray();
		
		for (int i = 0 ; i < arrayNamesPages.length ; ++i)
		{
			String namePage = (String) arrayNamesPages[i];
			double PR = hashPR.get(namePage).doubleValue();
			System.out.println(namePage + ":        " + PR);
		}
	}
	
	public HashMap<String,Double> getRanking()
	{
		return hashPR;
	}
}
