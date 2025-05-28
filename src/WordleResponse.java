public class WordleResponse {
    private final char c;
    private final int index;
    private final LetterResponse resp;

    public WordleResponse(char c, int index, LetterResponse resp) {
        this.c = c;
        this.index = index;
        this.resp = resp;
    }

    public char getC() {
        return c;
    }

    public int getIndex() {
        return index;
    }

    public LetterResponse getResp() {
        return resp;
    }
}