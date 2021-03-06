import java.io.File;

//Detalle de la clase: Se encarga de leer los archivos del directorio del set de pruebas

enum TypeReadResult{END_OF_FILE,OK}

public final class DirectoryReader {

	//constantes
	private static String SVN_FOLDER = ".svn";
	
	//Atributos
	private String[] arrayFiles;
	private int indexArrayFiles;
	public TypeReadResult readResult;
	
	//Metodos
	DirectoryReader(String absDirectoryPath)
	{	
		//Abrimos el directorio y lo leemos
		File fileDirectory = new File(absDirectoryPath);
		arrayFiles = fileDirectory.list();
		indexArrayFiles = 0;
	}	
	
	void resetRead()
	{
		indexArrayFiles = 0;
	}
	
	String getProxNameDoc()
	{
		//Nos da el proximo nombre archivo
		String nameDoc;
		
		if (indexArrayFiles < arrayFiles.length)
		{
			if (arrayFiles[indexArrayFiles].toString().compareTo(SVN_FOLDER.toString()) == 0)
			{
				++indexArrayFiles;
				return getProxNameDoc();
			}
			nameDoc = arrayFiles[indexArrayFiles];
			++indexArrayFiles;
			readResult = TypeReadResult.OK;
		}
		else
		{
			nameDoc = "";
			readResult = TypeReadResult.END_OF_FILE;
		}
		
		return nameDoc;
	}
}
