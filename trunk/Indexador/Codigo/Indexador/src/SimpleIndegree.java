import java.util.HashMap;


public class SimpleIndegree extends Indegre {

	//Atributos
	private HashMap<String,Integer> ranking;//Tendra <pagina,puntaje>

	//Metodos
	public SimpleIndegree() 
	{
		ranking = new HashMap<String,Integer>();
		
	}
	
	public void processInfoPage(Object[] arrayPageNamesRef)
	{
		//Un array con todas las paginas a las cuales hay una referencia entrante para las mismas
		String pageName;
		for (int i = 0 ; i < arrayPageNamesRef.length ; ++i)
		{
			pageName = (String)arrayPageNamesRef[i];
			if (ranking.containsKey(pageName))
			{
				Integer score = ranking.get(pageName);
				ranking.put(pageName, new Integer(score.intValue()+1) );
			}
			else
				ranking.put(pageName, new Integer(1));
		}
	}
	
	public void showRanking()
	{
		//Escribe a la Base de Datos los puntajes que tiene hasta el momento
		Object[] arrayKeys = ranking.keySet().toArray();
		String namePage;
		for (int i = 0 ; i < arrayKeys.length ; ++i)
		{
			namePage = (String)arrayKeys[i];
			System.out.println(Utlities.fixUrl(namePage) + "	" + ranking.get(namePage).intValue());
		}
	}
	
	public HashMap<String,Double> getRanking()
	{
		Object[] arrayNamesPages = ranking.keySet().toArray();
		HashMap<String,Double> results = new HashMap<String,Double>();
		
		for (int i = 0 ; i < arrayNamesPages.length ; ++i)
		{
			String namePage = (String) arrayNamesPages[i];
			int val = ranking.get(namePage).intValue();
			results.put(namePage, new Double(val));
		}
		
		return results;
	}
}
