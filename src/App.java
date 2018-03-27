import java.util.Iterator;
import java.util.List;

import accesseur.LocalDAO;
import modele.Local;

public class App {

	public static void main(String[] args) 
	{

		LocalDAO localDAO = new LocalDAO();
		List<Local> listeLocal = localDAO.listerLocalSimulation();

		Iterator<Local> visiteurLocal = listeLocal.iterator();
		while( visiteurLocal.hasNext())
		{
			Local local = visiteurLocal.next();
			//System.out.println("Local " + local.getNumero());
			System.out.println(local.exporterXML());
			System.out.println(local.exporterCSV());
			
			Local localReinterprete = Local.interpreterBinaire(local.exporterBinaire());
			System.out.println("Local reinterprete est " + local.getNumero());
			
			localDAO.enregistrerLocal(local);
		}
		
		
		
	}

}
