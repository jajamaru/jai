package entity;

public class StringTools {

	/**
	 * Remplace toutes les occurences de ' par "
	 * et toutes occurences de " par \".
	 * @param str Chaine de référence
	 * @return La chaine transformée
	 */
	public static String quotemeta(String str) {
		return str.replaceAll("'+", "\"").replaceAll("\"+", "\\\"");
	}
	
}
