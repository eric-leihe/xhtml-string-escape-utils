import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XHTMLStringEscapeUtilsTest {


    public static Collection testDataSetForEscape() {
        return Arrays.asList(new Object[][] {
                { "They're quite varied", "They&apos;re quite varied"},
                {
                    "Sometimes the string ∈ XML standard, sometimes ∈ HTML4 standard",
                    "Sometimes the string &isin; XML standard, sometimes &isin; HTML4 standard"
                },
                {
                    "Therefore -> I need an XHTML entity decoder.",
                    "Therefore -&gt; I need an XHTML entity decoder."
                }
        });
    }

    public static Collection testDataSetForUnescape() {
        return Stream.concat(Arrays.stream(testDataSetForEscape().toArray()), Arrays.stream(new Object[][]{
                {
                        "Sadly, some strings are not valid XML & are not-quite-so-valid HTML <- but I want them to work, too.",
                        "Sadly, some strings are not valid XML & are not-quite-so-valid HTML <- but I want them to work, too."
                },
                {
                        "&&xyzzy;", "&&xyzzy;"
                }
        })).collect(Collectors.toList());
    }

    @ParameterizedTest
    @MethodSource("testDataSetForEscape")
    public void escapeTest(String unescaped, String escaped) {
        Assertions.assertEquals(escaped, XHTMLStringEscapeUtils.escape(unescaped));
    }

    @ParameterizedTest
    @MethodSource("testDataSetForUnescape")
    public void unescapeTest(String unescaped, String escaped) {
        Assertions.assertEquals(unescaped, XHTMLStringEscapeUtils.unescape(escaped));
    }

    // Test StringEscapeUtils.unescapeHtml4 cannot correctly handle &apos;
    public static Collection testDataSetForUnescapeHtml4() {
        return Arrays.asList(new Object[][] {
                { "They're quite varied", "They&apos;re quite varied"}
        });
    }
    @ParameterizedTest
    @MethodSource("testDataSetForUnescapeHtml4")
    public void unescapeHtml4Test(String unescaped, String escaped) {
        Assertions.assertNotEquals(unescaped, StringEscapeUtils.unescapeHtml4(escaped));
    }


    // Test StringEscapeUtils.unescapeXml cannot correctly handle &isin;
    public static Collection testDataSetForUnescapeXML() {
        return Arrays.asList(new Object[][] {
                {
                        "Sometimes the string ∈ XML standard, sometimes ∈ HTML4 standard",
                        "Sometimes the string &isin; XML standard, sometimes &isin; HTML4 standard"
                }
        });
    }
    @ParameterizedTest
    @MethodSource("testDataSetForUnescapeXML")
    public void unescapeXmlTest(String unescaped, String escaped) {
        Assertions.assertNotEquals(unescaped, StringEscapeUtils.unescapeXml(escaped));
    }
}
