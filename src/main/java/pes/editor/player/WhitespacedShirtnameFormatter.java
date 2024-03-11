package pes.editor.player;

/**
 * Fills the players shirtname with spaces. The PES Editor classic variant.
 */
public class WhitespacedShirtnameFormatter {

    /**
     * Generates a shirtname in capital letters from a given playername.
     * Only allowing A-Z, space, point and _ (underscore)
     * @param player_name
     * @return
     */
    public String format(String player_name) {
        var result = new StringBuilder();
        var spaces = "";
        int len = player_name.length();
        if (len < 9 && len > 5) {
            spaces = " ";
        } else if (len < 6 && len > 3) {
            spaces = "  ";
        } else if (len == 3) {
            spaces = "    ";
        } else if (len == 2) {
            spaces = "        ";
        }
        player_name = player_name.toUpperCase();
        byte[] nb = player_name.getBytes();
        for (int i = 0; i < nb.length; i++) {
            if ((nb[i] < 65 || nb[i] > 90) && nb[i] != 46 && nb[i] != 32
                    && nb[i] != 95) {
                nb[i] = 32;
            }
        }
        player_name = new String(nb);
        for (int i = 0; i < player_name.length() - 1; i++) {
            result.append(player_name.charAt(i)).append(spaces);
        }
        result.append(player_name.substring(player_name.length() - 1));
        return result.toString();
    }
}
