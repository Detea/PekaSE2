package deta.pk.util;

public final class UnknownSpriteFormatException extends Exception {
    public UnknownSpriteFormatException(String formatString) {
        super("Unknown sprite format: " + formatString + "");
    }
}
