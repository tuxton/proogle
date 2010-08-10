
//Esta clase solo tiene metodos que utlizan las clases que agrupan por host
public class HostService {
	
	//Metodos
	public static String getHost(String pageName)
	{
		//Extrae el host de un sitio
		StringBuilder strPageName = new StringBuilder(pageName.toLowerCase());
		pageName = pageName.toLowerCase();
		
		if (pageName.startsWith("www"))
			strPageName.delete(0,4);
		
		int posBar = strPageName.indexOf("-");
		if (posBar != -1)
			strPageName.delete(posBar, strPageName.length());
		
		return strPageName.toString();
	}

}
