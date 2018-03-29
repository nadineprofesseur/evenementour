package modele;

public class Local 
{
	public enum TYPE_PROJECTEUR {VGA, DIGITAL, INTELLIGENT, AUCUN};
	
	protected String numero; // 5 char
	protected float largeur;
	protected float profondeur;	
	
	protected boolean reconfigurable = false;
	protected boolean fenetres = true;
	protected boolean internet = false;
	protected boolean telephone = true;
	
	protected int imprimantes = 0; // 0 à 15 sur 4 bits
	
	protected TYPE_PROJECTEUR projecteur = TYPE_PROJECTEUR.DIGITAL;
		
	protected int nombreOrdinateurs = 0; // max 255
	protected int nombrePlaces; // max 255
	
	protected String couleur; // convertir pour fichier

	public Local(String numero, int nombrePlaces) {
		this.numero = numero;
		this.nombrePlaces = nombrePlaces;
		this.largeur = 5;
		this.profondeur = 5;
	}

	public Local(String numero, int nombrePlaces, float largeur, float profondeur) {
		super();
		this.numero = numero;
		this.largeur = largeur;
		this.profondeur = profondeur;
		this.nombrePlaces = nombrePlaces;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isInternet() {
		return internet;
	}

	public void setInternet(boolean internet) {
		this.internet = internet;
	}

	public boolean isFenetres() {
		return fenetres;
	}

	public void setFenetres(boolean fenetres) {
		this.fenetres = fenetres;
	}

	public boolean isTelephone() {
		return telephone;
	}

	public void setTelephone(boolean telephone) {
		this.telephone = telephone;
	}

	public int getImprimantes() {
		return imprimantes;
	}

	public void setImprimantes(int imprimantes) {
		this.imprimantes = imprimantes;
	}

	public boolean isReconfigurable() {
		return reconfigurable;
	}

	public void setReconfigurable(boolean reconfigurable) {
		this.reconfigurable = reconfigurable;
	}

	public float getLargeur() {
		return largeur;
	}

	public void setLargeur(float largeur) {
		this.largeur = largeur;
	}

	public float getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(float profondeur) {
		this.profondeur = profondeur;
	}

	public TYPE_PROJECTEUR getProjecteur() {
		return projecteur;
	}

	public void setProjecteur(TYPE_PROJECTEUR projecteur) {
		this.projecteur = projecteur;
	}

	public int getNombrePlaces() {
		return nombrePlaces;
	}

	public void setNombrePlaces(int nombrePlaces) {
		this.nombrePlaces = nombrePlaces;
	}

	public int getNombreOrdinateurs() {
		return nombreOrdinateurs;
	}

	public void setNombreOrdinateurs(int nombreOrdinateurs) {
		this.nombreOrdinateurs = nombreOrdinateurs;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	public String exporterXML()
	{
		
		String xml = "";
		xml += "<numero>" + this.numero + "</numero>";
		xml += "<reconfigurable>" + this.reconfigurable + "</reconfigurable>";
		xml += "<fenetres>" + this.fenetres + "</fenetres>";
		xml += "<internet>" + this.internet + "</internet>";
		xml += "<telephone>" + this.telephone + "</telephone>";
		xml += "<imprimantes>" + this.imprimantes + "</imprimantes>";
		return "<local>" + xml + "</local>";
	}
	
	public String exporterCSV()
	{
		String csv = "";		
		csv += this.numero + "," + this.fenetres + "," + this.internet + "," + this.imprimantes;
		return csv;
	}
	
	public static byte DRAPEAU_RECONFIGURABLE = 1; // 0000 0001
	public static byte DRAPEAU_FENETRES = 2; // 0000 0010
	public static byte DRAPEAU_INTERNET = 4; // 0000 0100
	public static byte DRAPEAU_TELEPHONE = 8; // 0000 1000
	
	protected interface Binaire
	{
		public static int TAILLE_NUMERO = 5;		
	}
	
	public byte[] exporterBinaire()
	{
		byte[] binaire = new byte[6];

		// Le local a 5 bytes
		char[] numeroEnLettre = this.numero.toCharArray();
		for(int position = 0; position < Binaire.TAILLE_NUMERO; position++)
			binaire[position] = (byte) numeroEnLettre[position];
					
		byte drapeaux = 0; // 0000 0000
		if(this.reconfigurable) drapeaux = (byte) (drapeaux | DRAPEAU_RECONFIGURABLE); // pourrait donner 0000 0001
		if(this.fenetres) drapeaux = (byte) (drapeaux | DRAPEAU_FENETRES); // pourrait donner 0000 0011
		if(this.internet) drapeaux = (byte) (drapeaux | DRAPEAU_INTERNET); // pourrait donner 0000 0111 
		if(this.telephone) drapeaux = (byte) (drapeaux | DRAPEAU_TELEPHONE); // pourrait donner 0000 1111 

		int drapeauxGauche = (this.imprimantes << 4);
		binaire[5] = (byte) (drapeaux | drapeauxGauche);
		
		return binaire;
	}
	
	public static Local interpreterBinaire(byte[] localBinaire)
	{		
		byte[] numeroBinaire = new byte[Binaire.TAILLE_NUMERO];
		for(int position = 0; position < Binaire.TAILLE_NUMERO; position++)
			numeroBinaire[position] = localBinaire[position];
		String numero = new String(numeroBinaire); 
		
		Local local = new Local(numero,0);	
		
		byte drapeaux = localBinaire[Binaire.TAILLE_NUMERO];

		local.reconfigurable = ((drapeaux & DRAPEAU_RECONFIGURABLE) != 0);
		local.fenetres = ((drapeaux & DRAPEAU_FENETRES) != 0);
		local.internet = ((drapeaux & DRAPEAU_INTERNET) != 0);
		local.telephone = ((drapeaux & DRAPEAU_TELEPHONE) != 0);
		
		local.imprimantes = (drapeaux & 0b11110000);
		local.imprimantes = local.imprimantes >>> 4;
		
		return local;
	}
	
	
}
