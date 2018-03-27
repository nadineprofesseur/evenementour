package accesseur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;

import modele.Local;

public class LocalDAO { // Fichier
	
	public List<Local> listerLocalSimulation()
	{
		List<Local> listeLocal = new ArrayList<Local>();
		
		listeLocal.add(new Local("O-114", 28));
		listeLocal.add(new Local("B-127", 35));
		listeLocal.add(new Local("O-155", 35));
		
		return listeLocal;
	}
	
	public void enregistrerLocal(Local local)
	{
		String cheminFichier = "locaux.data";
		FileOutputStream flux = null;
		try {
			flux = new FileOutputStream(cheminFichier);
			if(flux == null) System.out.println("Flux est null");			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(flux == null) return;
		
		try {
			byte[] localBinaire = local.exporterBinaire();
			flux.write(localBinaire);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public Local lireLocal()
	{
		String cheminFichier = "locaux.data";
		FileInputStream flux = null;
		try {
			flux = new FileInputStream(cheminFichier);
			if(flux == null) System.out.println("Flux est null");			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(flux == null) return null;
		
		try {
			byte[] futurLocal = new byte[5];
			flux.read(futurLocal);			
			return Local.interpreterBinaire(futurLocal);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}

// Recherche : write binary file java site:oracle.com
// https://docs.oracle.com/javase/tutorial/essential/io/rafs.html
//https://docs.oracle.com/javase/tutorial/essential/io/bytestreams.html
// https://docs.oracle.com/javase/tutorial/essential/io/datastreams.html
