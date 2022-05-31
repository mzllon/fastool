package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@linkplain StringUtil}的测试
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class StringUtilTest {

    @Test
    public void isBlank() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank(" "));
        assertTrue(StringUtil.isBlank("  "));
        assertTrue(StringUtil.isBlank("  \t"));
        assertTrue(StringUtil.isBlank("  \t\n"));
        assertTrue(StringUtil.isBlank("\n"));
        assertTrue(StringUtil.isBlank("\t"));
        assertFalse(StringUtil.isBlank("  \t\n."));
        assertFalse(StringUtil.isBlank("-"));
        assertFalse(StringUtil.isBlank("\\n"));
    }

    @Test
    public void isNotBlank() {
        assertFalse(StringUtil.isNotBlank(null));
        assertFalse(StringUtil.isNotBlank(""));
        assertFalse(StringUtil.isNotBlank(" "));
        assertFalse(StringUtil.isNotBlank("  "));
        assertFalse(StringUtil.isNotBlank("\t"));
        assertFalse(StringUtil.isNotBlank("\n"));
        assertFalse(StringUtil.isNotBlank("  \t"));
        assertFalse(StringUtil.isNotBlank("  \t\n"));
        assertTrue(StringUtil.isNotBlank("  \t\n."));
        assertTrue(StringUtil.isNotBlank("\\n"));
    }

    @Test
    public void isNotBlankInWebEnv() {
        assertFalse(StringUtil.isNotBlankInWebEnv(null));
        assertFalse(StringUtil.isNotBlankInWebEnv(""));
        assertFalse(StringUtil.isNotBlankInWebEnv(" "));
        assertFalse(StringUtil.isNotBlankInWebEnv("  "));
        assertFalse(StringUtil.isNotBlankInWebEnv("\t"));
        assertTrue(StringUtil.isNotBlankInWebEnv(" a "));
        assertFalse(StringUtil.isNotBlankInWebEnv(StringUtil.NULL));
        assertFalse(StringUtil.isNotBlankInWebEnv(StringUtil.UNDEFINED));
    }


    @Test
    public void isAnyBlank() {
        CharSequence[] csa = null;
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[0];
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[1];
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n", "-"};
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertTrue(StringUtil.isAnyBlank(csa));
        csa = new CharSequence[]{"-"};
        assertFalse(StringUtil.isAnyBlank(csa));
    }

    @Test
    public void isAllBlank() {
        CharSequence[] csa = null;
        assertTrue(StringUtil.isAllBlank(csa));
        csa = new CharSequence[0];
        assertTrue(StringUtil.isAllBlank(csa));
        csa = new CharSequence[1];
        assertTrue(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertTrue(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertTrue(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertFalse(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertFalse(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertFalse(StringUtil.isAllBlank(csa));
        csa = new CharSequence[]{"-"};
        assertFalse(StringUtil.isAllBlank(csa));
    }

    @Test
    public void isAnyNotBlank() {
        CharSequence[] csa = null;
        assertFalse(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[0];
        assertFalse(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[1];
        assertFalse(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertFalse(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertFalse(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertTrue(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertTrue(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertTrue(StringUtil.isAnyNotBlank(csa));
        csa = new CharSequence[]{"-"};
        assertTrue(StringUtil.isAnyNotBlank(csa));
    }

    @Test
    public void testIsAnyNotBlank() {
        List<CharSequence> list = null;
        assertFalse(StringUtil.isAnyNotBlank(list));
        list = new ArrayList<>();
        assertFalse(StringUtil.isAnyNotBlank(list));
        list = new ArrayList<>(10);
        assertFalse(StringUtil.isAnyNotBlank(list));
        list = new ArrayList<>();
        list.add(null);
        assertFalse(StringUtil.isAnyNotBlank(list));
        list.clear();
        list.add("");
        list.add(" ");
        list.add("\t");
        list.add("\n");
        list.add("\r");
        list.add("\n");
        list.add("\r\n");
        assertFalse(StringUtil.isAnyNotBlank(list));
        list.add("-");
        assertTrue(StringUtil.isAnyNotBlank(list));
    }

    @Test
    public void isEmpty() {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertFalse(StringUtil.isEmpty(" "));
        assertFalse(StringUtil.isEmpty("\t"));
        assertFalse(StringUtil.isEmpty("\n"));
        assertFalse(StringUtil.isEmpty("-"));
    }

    @Test
    void isAnyEmpty() {
        CharSequence[] csa = null;
        assertTrue(StringUtil.isAnyEmpty(csa));
        csa = new CharSequence[0];
        assertTrue(StringUtil.isAnyEmpty(csa));
        csa = new CharSequence[1];
        assertTrue(StringUtil.isAnyEmpty(csa));
        csa = new CharSequence[]{"", "", null};
        assertTrue(StringUtil.isAnyEmpty(csa));
        csa = new CharSequence[]{" ", "", null};
        assertTrue(StringUtil.isAnyEmpty(csa));
        csa = new CharSequence[]{" ", "  "};
        assertFalse(StringUtil.isAnyEmpty(csa));
    }

    @Test
    public void isAllEmpty() {
        CharSequence[] csa = null;
        assertTrue(StringUtil.isAllEmpty(csa));
        csa = new CharSequence[0];
        assertTrue(StringUtil.isAllEmpty(csa));
        csa = new CharSequence[1];
        assertTrue(StringUtil.isAllEmpty(csa));
        csa = new CharSequence[]{"", "", null};
        assertTrue(StringUtil.isAllEmpty(csa));
        csa = new CharSequence[]{" ", "", null};
        assertFalse(StringUtil.isAllEmpty(csa));

    }

    @Test
    public void isNotEmpty() {

    }

    @Test
    public void hasLength() {
    }

    @Test
    public void hasText() {
        assertFalse(StringUtil.hasText(null));
        assertFalse(StringUtil.hasText(""));
        assertFalse(StringUtil.hasText(" "));
        assertFalse(StringUtil.hasText("  "));
        assertFalse(StringUtil.hasText("\t"));
        assertFalse(StringUtil.hasText("\n"));
        assertFalse(StringUtil.hasText("  \t"));
        assertFalse(StringUtil.hasText("  \t\n"));
        assertTrue(StringUtil.hasText("  \t\n."));
        assertTrue(StringUtil.hasText("\\n"));
    }


    @Test
    public void isAllNotBlank() {
        CharSequence[] csa = null;
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[0];
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[1];
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{null, null, null};
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{"", " ", "\t", "\n"};
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{" ", "\t", "\n", "-"};
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{"\t", "\n", "-"};
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{"\n", "-"};
        assertFalse(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{"-"};
        assertTrue(StringUtil.isAllNotBlank(csa));
        csa = new CharSequence[]{"\\t", "-"};
        assertTrue(StringUtil.isAllNotBlank(csa));
    }

    @Test
    public void testIsAllNotBlank() {
        List<CharSequence> list = null;
        assertFalse(StringUtil.isAllNotBlank(list));
        list = new ArrayList<>();
        assertFalse(StringUtil.isAllNotBlank(list));
        list = new ArrayList<>(10);
        assertFalse(StringUtil.isAllNotBlank(list));
        list = new ArrayList<>();
        list.add(null);
        assertFalse(StringUtil.isAllNotBlank(list));
        list.clear();
        list.add("");
        list.add(" ");
        list.add("\t");
        list.add("\n");
        list.add("\r");
        list.add("\n");
        list.add("\r\n");
        assertFalse(StringUtil.isAllNotBlank(list));
        list.add("-");
        assertFalse(StringUtil.isAllNotBlank(list));
        list.clear();
        list.add("-");
        list.add("0");
        assertTrue(StringUtil.isAllNotBlank(list));
    }

    @Test
    void isNoneEmpty() {
    }


    @Test
    void containsBlank() {
    }


    @Test
    public void containsWhitespace() {
        CharSequence cs = null;
        assertFalse(StringUtil.containsWhitespace(cs));
        cs = "";
        assertFalse(StringUtil.containsWhitespace(cs));
        cs = "author";
        assertFalse(StringUtil.containsWhitespace(cs));
        cs = " author ";
        assertTrue(StringUtil.containsWhitespace(cs));
        cs = "hello world";
        assertTrue(StringUtil.containsWhitespace(cs));
    }

}