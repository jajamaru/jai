package entity;

public class StringTools {

	/**
	 * Remplace toutes les occurences de ' par "
	 * et toutes occurences de " par \".
	 * @param str Chaine de r�f�rence
	 * @return La chaine transform�e
	 */
	public static String quotemeta(String str) {
		return str.replaceAll("'+", "\"").replaceAll("\"+", "\\\"");
	}
	
}
