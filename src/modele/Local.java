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
	protected boolean imprimante = false;
	
	protected TYPE_PROJECTEUR projecteur = TYPE_PROJECTEUR.DIGITAL;
		
	protected int nombreOrdinateurs = 0;
	protected int nombrePlaces;
	
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

	public boolean isImprimante() {
		return imprimante;
	}

	public void setImprimante(boolean imprimante) {
		this.imprimante = imprimante;
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
		xml += "<fenetres>" + this.fenetres + "</fenetres>";
		xml += "<internet>" + this.internet + "</internet>";
		return "<local>" + xml + "</local>";
	}
	
	public String exporterCSV()
	{
		String csv = "";		
		csv += this.numero + "," + this.fenetres + "," + this.internet;
		return csv;
	}
	
	public static byte DRAPEAU_FENETRES = 2;
	public static byte DRAPEAU_INTERNET = 4;

	public byte[] exporterBinaire()
	{
		byte[] binaire = new byte[6];

		// Le local a 5 bytes
		char[] numeroEnLettre = this.numero.toCharArray();
		for(int position = 0; position < 5; position++)
			binaire[position] = (byte) numeroEnLettre[position];
					
		byte drapeaux = 0; // 0000 0000
		if(this.fenetres) drapeaux = (byte) (drapeaux | DRAPEAU_FENETRES); // pourrait donner 0000 0010
		if(this.internet) drapeaux = (byte) (drapeaux | DRAPEAU_INTERNET); // pourrait donner 0000 0110 

		binaire[5] = drapeaux;
		
		return binaire;
	}
	
	public static Local interpreterBinaire(byte[] localBinaire)
	{		
		byte[] numeroBinaire = new byte[5];
		for(int position = 0; position < 5; position++)
			numeroBinaire[position] = localBinaire[position];
		String numero = new String(numeroBinaire); 
		
		Local local = new Local(numero,0);	
		
		byte drapeaux = localBinaire[5];
		
		local.fenetres = ((drapeaux & DRAPEAU_FENETRES) != 0);
		local.internet = ((drapeaux & DRAPEAU_INTERNET) != 0);
		
		return local;
	}
	
	
}
