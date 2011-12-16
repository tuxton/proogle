
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import org.xml.sax.Attributes;
import java.util.ArrayList;

//Esta clase se encarga de parsear un html e informar de los terminos encontrados y las referencias

public final class XMLParser 
{
	//Atributos
	private SAXParserFactory saxParserFact;
	private SAXParser saxParser;
	public ArrayList<String> arrayOfOutLinks;//Luego de cada llamada a parserHTML nos deja los lonks salientes del documento parseado
	private ArrayList<String> arrayOfTerms;//Contiene los terminos encontrados
	
	//Metodos
	XMLParser()
	{
		arrayOfOutLinks = new ArrayList<String>();
		arrayOfTerms = new ArrayList<String>();
		
		//Se construye el parser
		try
		{
			saxParserFact = SAXParserFactory.newInstance();  
			saxParser = saxParserFact.newSAXParser();
		}
		catch(ParserConfigurationException e)
		{
			System.out.println("Error al crear la factoria del Parser");
		}
		catch(SAXException e2)
		{
			System.out.println("Error al crear el parser");
		}
		
	}
	
	public void parseHTML(String absPathHTML)
	{
		if (arrayOfOutLinks.size() > 0)
			arrayOfOutLinks.clear();
		if (arrayOfTerms.size() > 0)
			arrayOfTerms.clear();
		
		try
		{
			saxParser.parse("file:///"+absPathHTML, new XMLReader() );
		}
		catch (SAXException e2)
		{
			System.out.println("Error al parsear el html: " + absPathHTML); 
			System.out.println(e2.getMessage());
		}
		catch (IOException e3)
		{
			System.out.println("Error de entrada salida al parsear el el html: " + absPathHTML);
			System.out.println(e3.getMessage());
		}
	}
	
	public  ArrayList<String> getTerms()
	{
		return arrayOfTerms;
	}
	
	private final class XMLReader extends DefaultHandler
	{
		//Atributos
		
		
		//Metodos
		private String processContent(String content)
		{
			//Le quita al contenido todos las ,. etc cambiandolos por espacios
			StringBuilder strTemp = new StringBuilder(content);
			for (int i = 0 ; i < strTemp.length() ; ++i)
			{
				char c = Character.toLowerCase(strTemp.charAt(i));
				if (!( ((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) //))//
						|| (c == 'á') || (c == 'é') || (c == 'í') || (c == 'ó') || (c == 'ú') || (c == 'ñ')))
					strTemp.setCharAt(i,' ');
			}
			return strTemp.toString();
		}
		
		public void startElement(String namespaceURI, String localName,String qName, Attributes atts)
		{
			//Buscamos solamente los atributos href para saber los link de las pagina
			int countAtr = atts.getLength();
			
			for (int i = 0 ; i < countAtr ; ++i)
			{
				String nameAt = atts.getQName(i); 
				if ( (nameAt.equals("href")) )
				{
					//System.out.println("Encontrado href = " + atts.getValue(i));
					arrayOfOutLinks.add(atts.getValue(i));
				}
					
			}
		}
		
		public void characters(char buf[], int offset, int len) throws SAXException
        {
			//Obtenemos los terminos de cada documento
        	String content = new String(new String(buf, offset, len));
        	content = processContent(content);
        	//System.out.println("Contenido: ");
        	String[] arrayWithTerms = content.split(" ");
        	for (int i = 0 ; i < arrayWithTerms.length ; ++i )
        		arrayOfTerms.add(arrayWithTerms[i]);
        	
        }
	}
}