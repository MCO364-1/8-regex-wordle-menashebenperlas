import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class RegExUtilsTest {

    @Test public void testProperNameValid() {
        assertTrue(RegExUtils.properName("Bob"));
        assertTrue(RegExUtils.properName("Smith"));
        assertTrue(RegExUtils.properName("Joey"));
    }
    @Test public void testProperNameInvalid() {
        assertFalse(RegExUtils.properName("bob"));
        assertFalse(RegExUtils.properName("BOB"));
        assertFalse(RegExUtils.properName("Sm1th"));
        assertFalse(RegExUtils.properName("A"));
    }

    @Test public void testIntegerValid() {
        assertTrue(RegExUtils.integer("12"));
        assertTrue(RegExUtils.integer("43.23"));
        assertTrue(RegExUtils.integer("-34.5"));
        assertTrue(RegExUtils.integer("+98.7"));
        assertTrue(RegExUtils.integer("0"));
        assertTrue(RegExUtils.integer("0.0230"));
    }
    @Test public void testIntegerInvalid() {
        assertFalse(RegExUtils.integer("023"));
        assertFalse(RegExUtils.integer("5."));
        assertFalse(RegExUtils.integer(".5"));
        assertFalse(RegExUtils.integer("abc"));
        assertFalse(RegExUtils.integer("++5"));
    }

    @Test public void testAncestorValid() {
        assertTrue(RegExUtils.ancestor("father"));
        assertTrue(RegExUtils.ancestor("mother"));
        assertTrue(RegExUtils.ancestor("grandmother"));
        assertTrue(RegExUtils.ancestor("great-grandfather"));
        assertTrue(RegExUtils.ancestor("great-great-grandmother"));
    }
    @Test public void testAncestorInvalid() {
        assertFalse(RegExUtils.ancestor("greatgrandmother"));
        assertFalse(RegExUtils.ancestor("grandmom"));
        assertFalse(RegExUtils.ancestor("great- uncle"));
        assertFalse(RegExUtils.ancestor("fatherly"));
    }

    @Test public void testPalindromeValid() {
        assertTrue(RegExUtils.palindrome("asdfggfdsa"));
        assertTrue(RegExUtils.palindrome("AsDfGgFdSa"));
    }
    @Test public void testPalindromeInvalid() {
        assertFalse(RegExUtils.palindrome("abcdefgfed"));  // 11 chars
        assertFalse(RegExUtils.palindrome("abcdffdcba"));  // 10 but no match
        assertFalse(RegExUtils.palindrome("1234567890"));
        assertFalse(RegExUtils.palindrome("abcba"));
    }

    @Test public void testWordleMatches() {

        String g1 = "TRAIN";
        List<WordleResponse> r1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            r1.add(new WordleResponse(g1.charAt(i), i, LetterResponse.WRONG_LETTER));
        }

        String g2 = "COUGH";
        List<WordleResponse> r2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            r2.add(new WordleResponse(g2.charAt(i), i, LetterResponse.WRONG_LOCATION));
        }

        List<List<WordleResponse>> history = Arrays.asList(r1, r2);
        List<String> matches = RegExUtils.wordleMatches(history);

        List<String> expected = Arrays.asList("OGUCH","UHCOG","GCHOU","HUGOC");
        assertEquals(expected, matches);
    }
}