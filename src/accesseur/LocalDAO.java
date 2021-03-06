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
		Local localO127 = new Local("B-127", 35);
		localO127.setInternet(true);
		localO127.setImprimantes(10);
		listeLocal.add(localO127);
		listeLocal.add(new Local("O-155", 35));
		
		return listeLocal;
	}
	
	public String cheminFichier = "locaux.data";
	protected FileOutputStream flux = null;
	protected boolean enregistrementPret = false;
	protected void preparerEnregistrement()
	{
		try {
			flux = new FileOutputStream(cheminFichier);
			if(flux == null) System.out.println("Flux est null");			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		
		this.enregistrementPret = true;
	}
	
	public void enregistrerLocal(Local local)
	{
		if(! this.enregistrementPret) this.preparerEnregistrement();
		if(flux == null) return;
		
		try {
			byte[] localBinaire = local.exporterBinaire();
			flux.write(localBinaire);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	protected FileInputStream fluxLecture = null;
	protected boolean lecturePrete = false;
	protected void preparerLecture()
	{
		try {
			fluxLecture = new FileInputStream(cheminFichier);
			if(fluxLecture == null) System.out.println("Flux est null");			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.lecturePrete = true;
	}
	
	public Local lireLocal()
	{
		if(!this.lecturePrete) this.preparerLecture();
		
		try {
			if(fluxLecture.available() < 5) return null;
			byte[] futurLocal = new byte[6];
			fluxLecture.read(futurLocal);	
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
