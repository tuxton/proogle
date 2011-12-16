
import java.util.ArrayList;
public final class Indexator {

	/**
	 * @param args
	 */
	
	//Atributos
	private DirectoryReader dirReader;
	private XMLParser xmlParser;
	private String pathIndex;
	private Indegre ind;
	private HostIndegre hostInd;
	private DomIndegre domInd;
	private PageRank pr;
	private HostPageRank hostPR;
	private DomPageRank domPR;
	
	//Metodos
	//Constructor
	Indexator(String path)
	{
		dirReader = new DirectoryReader(path);
		xmlParser = new XMLParser();
		pathIndex = path;
		ind = new Indegre();
		hostInd = new HostIndegre();
		domInd = new DomIndegre();
		pr = new PageRank();
		hostPR = new HostPageRank();
		domPR = new DomPageRank();
	}
	
	void startIndex()
	{
		DataBaseService DBServ = new DataBaseService("proogle");
		ArrayList<String> arrayNamePages = new ArrayList<String>();
		
		String absFilePath;
		String namePage;
		do
		{
			namePage = dirReader.getProxNameDoc();
			absFilePath = pathIndex + "/" + namePage;
			if (dirReader.readResult != TypeReadResult.END_OF_FILE)
			{
				arrayNamePages.add(namePage);
				System.out.println("Parseando: " + absFilePath);
				xmlParser.parseHTML(absFilePath);
				if (xmlParser.arrayOfOutLinks.size() > 0)
				{
					System.out.println("Links Encontrados: "+ xmlParser.arrayOfOutLinks.toString());
					ind.processInfoPage(xmlParser.arrayOfOutLinks.toArray());
					hostInd.processInfoPage(namePage, xmlParser.arrayOfOutLinks.toArray());
					domInd.processInfoPage(namePage, xmlParser.arrayOfOutLinks.toArray());
					pr.processInfoPage(namePage, xmlParser.arrayOfOutLinks.toArray());
					hostPR.processInfoPage(namePage, xmlParser.arrayOfOutLinks.toArray());
					domPR.processInfoPage(namePage, xmlParser.arrayOfOutLinks.toArray());
				}
				else
				{
					pr.processInfoPage(namePage, null);
					hostPR.processInfoPage(namePage, null);
					domPR.processInfoPage(namePage, null);
				}
				
				ArrayList<String> arrayTermsFinal = xmlParser.getTerms(); 
				Utlities.removeStopWordsFrom(arrayTermsFinal);
				DBServ.writeToBD(namePage,arrayNamePages.size(), arrayTermsFinal );
			}
		}
		while (dirReader.readResult != TypeReadResult.END_OF_FILE);
		
		pr.calculatePR();
		hostPR.calculateHostPR();
		domPR.calculateDomPR();
		
		System.out.println("Escribiendo a la BD de indegree");
		ind.showRanking();
		System.out.println("Escribiendo a la BD de HostIndegree");
		hostInd.showRanking();
		System.out.println("Escribiendo a la BD de DomIndegree");
		domInd.showRanking();
		System.out.println("Escribiendo a la BD de PR");
		pr.showRanking();
		System.out.println("Escribiendo a la BD de HostPR");
		hostPR.showRanking();
		System.out.println("Escribiendo a la BD de DomPR");
		domPR.showRanking();
		
		DBServ.writeToBD(arrayNamePages.toArray(), ind.getRanking(), hostInd.getRanking(), domInd.getRanking(), pr.getRanking(), hostPR.getRanking(), domPR.getRanking());
		DBServ.closeConection();
	}
	
	//Main
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(args[0]);
		Indexator myIndex = new Indexator(args[0]);
		myIndex.startIndex();
		
	}

}
