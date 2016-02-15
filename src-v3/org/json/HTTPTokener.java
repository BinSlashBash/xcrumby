package org.json;

public class HTTPTokener extends JSONTokener {
    public HTTPTokener(String string) {
        super(string);
    }

    public String nextToken() throws JSONException {
        char c;
        StringBuffer sb = new StringBuffer();
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c == '\"' || c == '\'') {
            char q = c;
            while (true) {
                c = next();
                if (c < ' ') {
                    break;
                } else if (c == q) {
                    return sb.toString();
                } else {
                    sb.append(c);
                }
            }
            throw syntaxError("Unterminated string.");
        }
        while (c != '\u0000' && !Character.isWhitespace(c)) {
            sb.append(c);
            c = next();
        }
        return sb.toString();
    }
}
