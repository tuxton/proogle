
import java.util.HashMap;
import java.util.HashSet;

public class HostIndegre 
{
	//Atributos
	private HashMap<String,HashSet<String> > ranking; //Va a guardar par <pagina,lista de bloques que hacen referencia a la misma>
	
	
	//Metodos publicos
	HostIndegre()
	{
		ranking = new HashMap<String,HashSet<String> >();
	}
	
	public void processInfoPage(String namePage,Object[] arrayPageNamesRef)
	{
		//Recive una pagina y un array con las paginas a las cuales hace referencia
		//Con esto arma el ranking
		String hostSourcePage = HostService.getHost(namePage); 
		String hostDestPage;
		String nameDestPage;
		for (int i = 0 ; i < arrayPageNamesRef.length ; ++i)
		{
			nameDestPage = (String)arrayPageNamesRef[i];
			hostDestPage = HostService.getHost(nameDestPage);    
			if (!(hostDestPage.equals(hostSourcePage)) )
			{
				//Si el host fuente ya existe entre los que ya se encontraron para la pagina destino, no lo agrego
				if ( ranking.containsKey(nameDestPage) )
				{
					HashSet<String> hashHostDestPage = ranking.get(nameDestPage);	
					hashHostDestPage.add(hostSourcePage);	 	
				}
				else
				{
					HashSet<String> hashHostDestPage = new HashSet<String>();
					hashHostDestPage.add(hostSourcePage);
					ranking.put(nameDestPage, hashHostDestPage );
				}
			}
		}
		
	}
	
	public void showRanking()
	{
		Object[] arrayNamesPages = ranking.keySet().toArray();
		
		for (int i = 0 ; i < arrayNamesPages.length ; ++i)
		{
			String namePage = (String) arrayNamesPages[i];
			int countBlocks = ranking.get(namePage).size();
			System.out.println(namePage + ":        " + countBlocks);
		}
	}
	
	public HashMap<String,Double> getRanking()
	{
		Object[] arrayNamesPages = ranking.keySet().toArray();
		HashMap<String,Double> results = new HashMap<String,Double>();
		
		for (int i = 0 ; i < arrayNamesPages.length ; ++i)
		{
			String namePage = (String) arrayNamesPages[i];
			int countBlocks = ranking.get(namePage).size();
			results.put(namePage, new Double(countBlocks));
		}
		
		return results;
	}
}
