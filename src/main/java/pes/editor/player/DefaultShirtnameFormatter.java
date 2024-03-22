package pes.editor.player;

/**
 * Creates a players shirtname. Filters non-supported characters.
 */
public class DefaultShirtnameFormatter {

    /**
     * Generates a shirtname in capital letters from a given playername.
     * Only allowing A-Z, space, point and _ (underscore)
     * @param playerName The players ingame name
     * @return the players shirtname
     */
    public String format(String playerName) {
        return replaceUnsupportedCharacters(playerName.toUpperCase());
    }

    private String replaceUnsupportedCharacters(String playerName) {
        var nameBytes = playerName.getBytes();

        for (int i = 0; i < nameBytes.length; i++) {
            if ((nameBytes[i] < 65 || nameBytes[i] > 90)
                && nameBytes[i] != 46 && nameBytes[i] != 32
                && nameBytes[i] != 95) {
                nameBytes[i] = 32;
            }
        }
        return new String(nameBytes);
    }
}
