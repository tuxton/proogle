import java.util.HashMap;
import java.util.HashSet;


public class DomIndegre {
	//Atributos
	private HashMap<String,HashSet<String> > ranking; //Va a guardar par <pagina,lista de bloques que hacen referencia a la misma>
	
	
	//Metodos publicos
	DomIndegre()
	{
		ranking = new HashMap<String,HashSet<String> >();
	}
	
	public void processInfoPage(String namePage,Object[] arrayPageNamesRef)
	{
		//Recive una pagina y un array con las paginas a las cuales hace referencia
		//Con esto arma el ranking
		String DomSourcePage = DomService.getDom(namePage);
		String DomDestPage;
		String nameDestPage;
		for (int i = 0 ; i < arrayPageNamesRef.length ; ++i)
		{
			nameDestPage = (String)arrayPageNamesRef[i];
			DomDestPage = DomService.getDom(nameDestPage);
			if (!(DomDestPage.equals(DomSourcePage)) )
			{
				//Si el host fuente ya existe entre los que ya se encontraron para la pagina destino, no lo agrego
				if ( ranking.containsKey(nameDestPage) )
				{
					HashSet<String> hashDomDestPage = ranking.get(nameDestPage);
					hashDomDestPage.add(DomSourcePage);
				}
				else
				{
					HashSet<String> hashDomDestPage = new HashSet<String>();
					hashDomDestPage.add(DomSourcePage);
					ranking.put(nameDestPage, hashDomDestPage );
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
			System.out.println(Utlities.fixUrl(namePage) + "	" + countBlocks);
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
