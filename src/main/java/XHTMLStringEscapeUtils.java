import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.*;

public class XHTMLStringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_XHTML =
            new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_ESCAPE),
                    new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
                    new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE)
            ).with(StringEscapeUtils.ESCAPE_XML11);

    public static final CharSequenceTranslator UNESCAPE_XHTML =
            new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_UNESCAPE),
                    new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE),
                    new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE),
                    new NumericEntityUnescaper(),
                    new LookupTranslator(EntityArrays.APOS_UNESCAPE)
            );

    public static final String escape(final String input) {
        return ESCAPE_XHTML.translate(input);
    }

    public static final String unescape(final String input) {
        return UNESCAPE_XHTML.translate(input);
    }
}
