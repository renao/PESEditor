package pes.editor.player;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class WhitespacedShirtnameFormatterTest {


    @ParameterizedTest
    @CsvSource({
            "a ,A",
            "ab, A        B",
            "abc, A    B    C",
            "abcd, A  B  C  D",
            "abcde, A  B  C  D  E",
            "abcdef, A B C D E F",
            "abcdefg, A B C D E F G",
            "abcdefgh, A B C D E F G H",
            "abcdefghi, ABCDEFGHI",
            "abcdefghij, ABCDEFGHIJ",
            "abcdefghijk, ABCDEFGHIJK",
            "abcdefghijkl, ABCDEFGHIJKL",
            "abcdefghijklm, ABCDEFGHIJKLM",
            "abcdefghijklmn, ABCDEFGHIJKLMN",
            "abcdefghijklmno, ABCDEFGHIJKLMNO"
    })
    void Strechtes_the_Shirtname_up_to_15_chars_by_adding_whitespace(String playername, String expectedShirtname) {
        var formatter = new WhitespacedShirtnameFormatter();

        assertEquals(
                expectedShirtname,
                formatter.format(playername));
    }

    @Test
    @Disabled("needs better implementation idea")
    void Only_keeps_allowed_characters_replacing_invalid_ones_by_a_whitespace() {
        var formatter = new WhitespacedShirtnameFormatter();

        for (int i = 0; i < Character.MAX_VALUE; i++) {
            var invalidName = Character.toString((char) i);

            assertEquals(
                    " ",
                    formatter.format(invalidName));
        }
    }
}