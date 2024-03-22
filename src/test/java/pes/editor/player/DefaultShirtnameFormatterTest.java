package pes.editor.player;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DefaultShirtnameFormatterTest {

    @ParameterizedTest
    @CsvSource({
            "a ,A",
            "ab, AB",
            "abc, ABC",
            "abcd, ABCD",
            "abcde, ABCDE",
            "abcdef, ABCDEF",
            "abcdefg, ABCDEFG",
            "abcdefgh, ABCDEFGH",
            "abcdefghi, ABCDEFGHI",
            "abcdefghij, ABCDEFGHIJ",
            "abcdefghijk, ABCDEFGHIJK",
            "abcdefghijkl, ABCDEFGHIJKL",
            "abcdefghijklm, ABCDEFGHIJKLM",
            "abcdefghijklmn, ABCDEFGHIJKLMN",
            "abcdefghijklmno, ABCDEFGHIJKLMNO"
    })
    void Capitalizes_Player_Name(String playername, String expectedShirtname) {
        var formatter = new DefaultShirtnameFormatter();

        assertEquals(
                expectedShirtname,
                formatter.format(playername));
    }
}