
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseService {
	
	//Atributos
	private Connection conexion;
	private static Integer MaxCharacterReg = 40100; //cantidad maxima de caracteres en un campo de texto
	
	//Metodos
	DataBaseService(String nameBD)
	{
		try
		{
		   Class.forName("com.mysql.jdbc.Driver");
		   String paramOne = "jdbc:mysql://localhost/" + nameBD;
		   conexion = DriverManager.getConnection (paramOne,"root", "");
		   
		   Statement st = conexion.createStatement();
		   st.executeUpdate("DROP TABLE IF EXISTS `articulos`"); 
		   st.executeUpdate("CREATE TABLE `articulos` (`idArticulo` int(10) unsigned NOT NULL auto_increment,`idPagina` int(11) unsigned NOT NULL,`texto` text NOT NULL,PRIMARY KEY  (`idArticulo`),FULLTEXT KEY `texto` (`texto`),FULLTEXT KEY `texto_2` (`texto`),FULLTEXT KEY `texto_3` (`texto`)) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;"); 
		}
		catch (SQLException s)
		{
		   s.printStackTrace();
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
		
	}
	
	public void writeToBD(Object[] arrayNamePages,HashMap<String,Double> rankingInd,
			              HashMap<String,Double> rankingHostInd,
			              HashMap<String,Double> rankingDomInd,
			              HashMap<String,Double> rankingPR,
			              HashMap<String,Double> rankingHostPR,
			              HashMap<String,Double> rankingDomPR)
	{	
		try
		{
			Statement st = conexion.createStatement();
			st.executeUpdate("DROP TABLE IF EXISTS `paginas`"); 
			st.executeUpdate("DROP TABLE IF EXISTS `ranking`");
			
			String sentenceTablePaginas = "CREATE TABLE `paginas` (`idPagina` int(10) unsigned NOT NULL auto_increment,`dominio` text NOT NULL,PRIMARY KEY  (`idPagina`),FULLTEXT KEY `NewIndex1` (`dominio`)) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;";
			st.executeUpdate(sentenceTablePaginas);
			
			String sentenceTableRanking = "CREATE TABLE `ranking` (`idPagina` int(11) unsigned NOT NULL,`Ind` float unsigned NOT NULL,`HyHostInd` float unsigned NOT NULL,`HyDomInd` float unsigned NOT NULL,`PageRank` float unsigned NOT NULL,`HyHostPageRank` float unsigned NOT NULL,`HyDomPageRank` float unsigned NOT NULL,PRIMARY KEY  (`idPagina`)) ENGINE=MyISAM DEFAULT CHARSET=latin1;";
			st.executeUpdate(sentenceTableRanking);
			
			//LLenamos la tabla de paginas y ranking a la vez
			String namePage;
			String sentence;
			double indValue;
			double hostIndValue;
			double domIndValue;
			double PRValue;
			double domPRValue;
			double hostPRValue;
			
			for (int i = 0 ; i < arrayNamePages.length ; ++i)
			{
				namePage = (String)arrayNamePages[i];
				sentence = "insert  into `paginas`(`idPagina`,`dominio`) values (" + (i+1) + ",'" + Utlities.fixUrl(namePage) + "');";
				st.executeUpdate(sentence);
				
				indValue   	 = getRankingValueFor(rankingInd, namePage);
				hostIndValue = getRankingValueFor(rankingHostInd, namePage);
				domIndValue  = getRankingValueFor(rankingDomInd, namePage);
				PRValue 	 = getRankingValueFor(rankingPR, namePage);
				domPRValue 	 = getRankingValueFor(rankingHostPR, namePage);
				hostPRValue  = getRankingValueFor(rankingDomPR, namePage);
				
				sentence = "insert  into `ranking`(`idPagina`,`Ind`,`HyHostInd`,`HyDomInd`,`PageRank`,`HyHostPageRank`,`HyDomPageRank`) values (" + (i+1) + "," + indValue + "," + hostIndValue + "," + domIndValue + "," + PRValue + "," + hostPRValue + "," + domPRValue +");" ;
				st.executeUpdate(sentence);
				
			}
			 
		}
		catch (SQLException s)
		{
		   s.printStackTrace();
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
	}

	private double getRankingValueFor(HashMap<String, Double> ranking, String namePage) {
		double value = 0;
		if (ranking.containsKey(namePage))
			value = ranking.get(namePage).doubleValue();
		return value;
	}
	
	public void writeToBD(String namePage,int idPage,ArrayList<String> arrayOfTerms)
	{	
		//Primero ponemos los terminos del array en el string que se pondra en el campo de la BD
		StringBuilder strings = new StringBuilder(); //Aca van los campos de texto que se graban en la BD
		
		String term;
		
		for (int i = 0 ; i < arrayOfTerms.size() ; ++i)
		{
			term = arrayOfTerms.get(i);
			if ((strings.length() + term.length()) < MaxCharacterReg) 
				{
					strings.append(term);
					if ((strings.length() + 1 ) < MaxCharacterReg)
						strings.append(" ");
				}
		}
		
		//Grabamos en la BD
		String sentence;
		try
		{
			Statement st = conexion.createStatement();

			sentence = "insert  into `articulos`(`idPagina`,`texto`) values (" + idPage + "," + "'" + strings.toString() + "');";
			st.executeUpdate(sentence);
		}
		catch (SQLException s)
		{
		   s.printStackTrace();
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
		
	}
	
	public void closeConection()
	{
		try
		{
			conexion.close();
		}
		catch (SQLException s)
		{
		   s.printStackTrace();
		}
	}
}
