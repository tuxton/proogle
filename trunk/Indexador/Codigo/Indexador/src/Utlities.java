
//Provee solo funciones utilies como eliminar las stopWords

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utlities 
{

	public static void removeStopWordsFrom(ArrayList<String> arrayTerms)
	{

		File fileStopWords = new File( "Codigo" + File.separator + "Indexador" + File.separator + "bin" + File.separator + "FilesUtilities" + File.separator + "StopWords" );
		fileStopWords.getAbsolutePath();
		if ( fileStopWords.exists() )
		{
			
			try
			{
				BufferedReader bufferRead = new BufferedReader( new FileReader( fileStopWords ) );
				
				//Creamos un array con todos los StopWords
				String termStopWord;
				ArrayList<String> arrayStopWords = new ArrayList<String>();
				do
				{
					termStopWord = bufferRead.readLine();
					arrayStopWords.add(termStopWord);
				}
				while (termStopWord != null);
				
				//Eliminamos los Stop Words del array de terminos
				if (arrayTerms.removeAll(arrayStopWords))
					System.out.println("Se han removido StopWords");
			}
			catch (FileNotFoundException f)
			{
				f.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		else
			System.out.println("El archivo de StopWords no se encuentra."+ fileStopWords);
	}
	
	public static String fixUrl(String url){
		url = url.replace("_", ".");
		url = url.replace("-", "/");
		return url;
	} 

}
