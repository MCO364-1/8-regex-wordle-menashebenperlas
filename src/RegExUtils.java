import java.util.*;
import java.util.regex.*;

public class RegExUtils {

    private static final List<String> DICTIONARY = Arrays.asList(
            "COUGH","OGUCH","UHCOG","GCHOU","HUGOC"
    );

    public static boolean properName(String s) {
        return Pattern.matches("^[A-Z][a-z]+$", s);
    }

    public static boolean integer(String s) {
        return Pattern.matches("^[+-]?(?:0|[1-9]\\d*)(?:\\.\\d+)?$", s);
    }

    public static boolean ancestor(String s) {
        return Pattern.matches("^(?:great-)*(?:grandmother|grandfather|mother|father)$", s);
    }

    public static boolean palindrome(String s) {
        return Pattern.matches(
                "(?i)^([A-Za-z])([A-Za-z])([A-Za-z])([A-Za-z])([A-Za-z])\\5\\4\\3\\2\\1$",
                s
        );
    }

    public static List<String> wordleMatches(List<List<WordleResponse>> responses) {
        StringBuilder negClass = new StringBuilder();
        List<Character> required = new ArrayList<>();
        Map<Integer,Character> correct = new HashMap<>();
        List<int[]> wrongLocs = new ArrayList<>();

        for (List<WordleResponse> guess : responses) {
            for (WordleResponse wr : guess) {
                char c = Character.toUpperCase(wr.getC());
                int i = wr.getIndex();
                switch (wr.getResp()) {
                    case WRONG_LETTER:
                        if (negClass.indexOf(String.valueOf(c)) < 0) {
                            negClass.append(c);
                        }
                        break;
                    case CORRECT_LOCATION:
                        correct.put(i, c);
                        break;
                    case WRONG_LOCATION:
                        required.add(c);
                        wrongLocs.add(new int[]{i, c});
                        break;
                }
            }
        }

        StringBuilder regex = new StringBuilder("^");

        if (negClass.length() > 0) {
            regex.append("(?!.*[").append(negClass).append("])");
        }

        for (char c : required) {
            regex.append("(?=.*").append(c).append(")");
        }

        for (int[] wl : wrongLocs) {
            regex.append("(?!.{").append(wl[0]).append("}").append((char)wl[1]).append(")");
        }

        for (int pos = 0; pos < 5; pos++) {
            if (correct.containsKey(pos)) {
                regex.append(correct.get(pos));
            } else {
                regex.append(".");
            }
        }
        regex.append("$");

        Pattern p = Pattern.compile(regex.toString());
        List<String> out = new ArrayList<>();
        for (String w : DICTIONARY) {
            if (p.matcher(w).matches()) out.add(w);
        }
        return out;
    }
}