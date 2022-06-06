package tech.fastool.core.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void getIfEmpty() {
        String str = StringUtil.getIfEmpty(null, null);
        assertNull(str);
        str = StringUtil.getIfEmpty(null, StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);
        str = StringUtil.getIfEmpty("", StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);
        str = StringUtil.getIfEmpty(" ", StringUtil.NULL);
        assertEquals(" ", str);
        str = StringUtil.getIfEmpty("\\w", StringUtil.NULL);
        assertEquals("\\w", str);
    }

    @Test
    public void getIfBlank() {
        String str = StringUtil.getIfBlank(null, null);
        assertNull(str);
        str = StringUtil.getIfBlank(null, StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);
        str = StringUtil.getIfBlank("", StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);
        str = StringUtil.getIfBlank(" ", StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);
        str = StringUtil.getIfBlank("\\w", StringUtil.NULL);
        assertEquals("\\w", str);
        str = StringUtil.getIfBlank("\t\r", StringUtil.NULL);
        assertEquals(StringUtil.NULL, str);

    }

    @Test
    public void trimLeft() {
        CharSequence cs = null;
        assertNull(StringUtil.trimLeft(cs));
        cs = "";
        assertEquals(cs, StringUtil.trimLeft(cs));
        cs = "\t\t\t\t";
        assertEquals(StringUtil.EMPTY_STRING, StringUtil.trimLeft(cs));
        cs = "\na";
        assertEquals("a", StringUtil.trimLeft(cs));
        cs = "a\r";
        assertEquals(cs, StringUtil.trimLeft(cs));

    }

    @Test
    public void trimRight() {
        CharSequence cs = null;
        assertNull(StringUtil.trimRight(cs));
        cs = "";
        assertEquals(cs, StringUtil.trimRight(cs));
        cs = "\t\t\t\t";
        assertEquals(StringUtil.EMPTY_STRING, StringUtil.trimRight(cs));
        cs = "\na";
        assertEquals(cs, StringUtil.trimRight(cs));
        cs = "a\r";
        assertEquals("a", StringUtil.trimRight(cs));
        cs = "\ba\r";
        assertEquals("\ba", StringUtil.trimRight(cs));

    }

    @Test
    public void testTrim() {
        CharSequence[] array = null;
        assertArrayEquals(ArrayUtil.EMPTY_STRING_ARRAY, StringUtil.trim(array));
        array = new CharSequence[0];
        assertArrayEquals(ArrayUtil.EMPTY_STRING_ARRAY, StringUtil.trim(array));
        array = new CharSequence[2];
        assertArrayEquals(new String[]{null, null}, StringUtil.trim(array));
        array = new CharSequence[]{"", " ", " \t", "\r\n\t"};
        assertArrayEquals(new String[]{"", "", "", ""}, StringUtil.trim(array));
        array = new CharSequence[]{"", " ", "\t", "\r author: miles \n"};
        assertArrayEquals(new String[]{"", "", "", "author: miles"}, StringUtil.trim(array));

    }

    @Test
    public void trimToList() {
        List<CharSequence> array = null;
        assertEquals(ListUtil.immutableEmptyList(), StringUtil.trim(array));
        array = new ArrayList<>();
        assertEquals(ListUtil.immutableEmptyList(), StringUtil.trim(array));
        array.add("");
        array.add(" ");
        array.add("\t\tauthor: miles\r");
        List<CharSequence> expected = Arrays.asList("", "", "author: miles");
        assertEquals(expected, StringUtil.trim(array));

    }

    @Test
    public void trimAllWhitespace() {
        CharSequence cs = null;
        assertNull(StringUtil.trimAllWhitespace(cs));
        cs = "";
        assertEquals(StringUtil.EMPTY_STRING, StringUtil.trimAllWhitespace(cs));
        cs = " \t \r \n ";
        assertEquals(StringUtil.EMPTY_STRING, StringUtil.trimAllWhitespace(cs));
        cs = "miles tang";
        assertEquals("milestang", StringUtil.trimAllWhitespace(cs));

    }

    @Test
    public void endsWithIgnoreCase() {
        CharSequence cse = null;
        CharSequence suffix = null;
        assertFalse(StringUtil.endsWithIgnoreCase(cse, suffix));
        cse = "";
        assertFalse(StringUtil.endsWithIgnoreCase(cse, suffix));
        suffix = "";
        assertTrue(StringUtil.endsWithIgnoreCase(cse, suffix));
        cse = "miles tang";
        assertTrue(StringUtil.endsWithIgnoreCase(cse, suffix));
        suffix = "G";
        assertTrue(StringUtil.endsWithIgnoreCase(cse, suffix));
        cse = "author: miles.tang\n";
        suffix = "\n";
        assertTrue(StringUtil.endsWithIgnoreCase(cse, suffix));
    }

    @Test
    public void testEquals() {
        CharSequence str1 = null, str2 = null;
        assertTrue(StringUtil.equals(str1, str2));
        str1 = "";
        assertFalse(StringUtil.equals(str1, str2));
        str2 = "";
        assertTrue(StringUtil.equals(str1, str2));
        str1 = "author";
        assertFalse(StringUtil.equals(str1, str2));
        str2 = "author";
        assertTrue(StringUtil.equals(str1, str2));
        str2 = "Author";
        assertFalse(StringUtil.equals(str1, str2));
        str1 = "\t";
        str2 = "\t";
        assertTrue(StringUtil.equals(str1, str2));

    }

    @Test
    public void equalsIgnoreCase() {
        CharSequence str1 = null, str2 = null;
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));
        str1 = "";
        assertFalse(StringUtil.equalsIgnoreCase(str1, str2));
        str2 = "";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));
        str1 = "author";
        assertFalse(StringUtil.equalsIgnoreCase(str1, str2));
        str2 = "author";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));
        str2 = "Author";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));
        str1 = "\t";
        str2 = "\t";
        assertTrue(StringUtil.equalsIgnoreCase(str1, str2));

    }

    @Test
    public void containsAll() {
        CharSequence cse = null;
        char[] matchChars = null;
        assertFalse(StringUtil.containsAll(cse, matchChars));
        cse = "";
        assertFalse(StringUtil.containsAll(cse, matchChars));
        // 空字符串不等价于 \u0000
        matchChars = new char[]{'\0', '\0'};
        assertFalse(StringUtil.containsAll(cse, matchChars));
        cse = "miles ";
        matchChars = new char[]{' '};
        assertTrue(StringUtil.containsAll(cse, matchChars));
        matchChars = new char[]{' ', 'i'};
        assertTrue(StringUtil.containsAll(cse, matchChars));
        matchChars = new char[]{' ', 'a'};
        assertFalse(StringUtil.containsAll(cse, matchChars));
        cse = "author\n\tmiles";
        matchChars = new char[]{'\n', '\t'};
        assertTrue(StringUtil.containsAll(cse, matchChars));

    }

    @Test
    public void testContains() {
        CharSequence str = null;
        CharSequence[] array = null;
        assertFalse(StringUtil.containsAny(str, array));
        array = new CharSequence[0];
        assertFalse(StringUtil.containsAny(str, array));
        str = "";
        assertFalse(StringUtil.containsAny(str, array));
        array = new CharSequence[]{""};
        assertTrue(StringUtil.containsAny(str, array));
        str = "author:miles;\nlocation:hangzhou";
        assertFalse(StringUtil.containsAny(str, array));
        str = "loc";
        array = new CharSequence[]{"\n", "location"};
        assertTrue(StringUtil.containsAny(str, array));
        str = "\n";
        array = new CharSequence[]{" ", "\n"};
        assertTrue(StringUtil.containsAny(str, array));
        str = "au";
        array = new CharSequence[]{"AUTHOR"};
        assertFalse(StringUtil.containsAny(str, array));

    }

    @Test
    public void containsIgnoreCase() {
        CharSequence str = null;
        CharSequence[] array = null;
        assertFalse(StringUtil.containsAnyIgnoreCase(str, array));
        array = new CharSequence[0];
        assertFalse(StringUtil.containsAnyIgnoreCase(str, array));
        str = "";
        assertFalse(StringUtil.containsAnyIgnoreCase(str, array));
        array = new CharSequence[]{""};
        assertTrue(StringUtil.containsAnyIgnoreCase(str, array));
        str = "author";
        assertFalse(StringUtil.containsAnyIgnoreCase(str, array));
        array = new CharSequence[]{"\n", "location"};
        assertFalse(StringUtil.containsAnyIgnoreCase(str, array));
        str = "\n";
        array = new CharSequence[]{"", "\n"};
        assertTrue(StringUtil.containsAnyIgnoreCase(str, array));
        str = " ";
        array = new CharSequence[]{" ", "\n"};
        assertTrue(StringUtil.containsAnyIgnoreCase(str, array));
        str = "author";
        array = new CharSequence[]{"AUTHOR"};
        assertTrue(StringUtil.containsAnyIgnoreCase(str, array));

    }

    @Test
    public void containsAllIgnoreCase() {
        CharSequence cse = null;
        CharSequence[] array = null;
        assertFalse(StringUtil.containsAllIgnoreCase(cse, array));
        array = new CharSequence[0];
        assertFalse(StringUtil.containsAllIgnoreCase(cse, array));
        cse = "ta";
        assertFalse(StringUtil.containsAllIgnoreCase(cse, array));
        array = new CharSequence[]{"Ta", "ata"};
        assertTrue(StringUtil.containsAllIgnoreCase(cse, array));
    }

    @Test
    public void hideLength() {
        CharSequence cse = null;
        int startInclude = 0;
        int length = 4;
        assertNull(StringUtil.hideLength(cse, startInclude, length));
        cse = "";
        assertEquals(StringUtil.EMPTY_STRING, StringUtil.hideLength(cse, startInclude, length));
        cse = "          ";
        assertEquals("****      ", StringUtil.hideLength(cse, startInclude, length));
        cse = "  ";
        assertEquals("**", StringUtil.hideLength(cse, startInclude, length));

    }

    @Test
    public void delete() {
        CharSequence cse = null;
        CharSequence deleteStr = null;
        assertNull(StringUtil.delete(cse, deleteStr));
        deleteStr = "a";
        assertNull(StringUtil.delete(cse, deleteStr));
        cse = "";
        assertEquals("", StringUtil.delete(cse, deleteStr));
        cse = "thank";
        assertEquals("thnk", StringUtil.delete(cse, deleteStr));
        deleteStr = "A";
        assertEquals("thank", StringUtil.delete(cse, deleteStr));
        deleteStr = "\\w";
        assertEquals("thank", StringUtil.delete(cse, deleteStr));

    }

    @Test
    void testDelete() {
        CharSequence cse = null;
        int start = 0, end = 0;
        assertNull(StringUtil.delete(cse, start, end));
        cse = "";
        assertEquals("", StringUtil.delete(cse, start, end));
        cse = "AaBbCc";
        assertEquals("aBbCc", StringUtil.delete(cse, start, end));
        end = -1;
        assertEquals("", StringUtil.delete(cse, start, end));
        start = 10;
        assertEquals(cse.toString(), StringUtil.delete(cse, start, end));
        end = -20;
        assertEquals(cse.toString(), StringUtil.delete(cse, start, end));
    }

    @Test
    public void deletePrefixIgnoreCase() {
        CharSequence cse = null;
        CharSequence prefix = null;
        assertNull(StringUtil.deletePrefixIgnoreCase(cse, prefix));
        cse = "author";
        assertEquals(cse, StringUtil.deletePrefixIgnoreCase(cse, prefix));
        prefix = "az";
        assertEquals(cse, StringUtil.deletePrefixIgnoreCase(cse, prefix));
        prefix = "\\w";
        assertEquals(cse, StringUtil.deletePrefixIgnoreCase(cse, prefix));
        prefix = "author";
        assertEquals("", StringUtil.deletePrefixIgnoreCase(cse, prefix));
        prefix = "AUTHOR";
        assertEquals("", StringUtil.deletePrefixIgnoreCase(cse, prefix));

    }

    @Test
    public void deletePrefix() {
        CharSequence cse = null;
        CharSequence prefix = null;
        assertNull(StringUtil.deletePrefix(cse, prefix));
        cse = "author: miles";
        assertEquals(cse, StringUtil.deletePrefix(cse, prefix));
        prefix = "";
        assertEquals(cse, StringUtil.deletePrefix(cse, prefix));
        prefix = "auth";
        assertEquals("or: miles", StringUtil.deletePrefix(cse, prefix));
        prefix = "AUTH";
        assertEquals(cse, StringUtil.deletePrefix(cse, prefix));
        cse = "\ta\ruthor";
        prefix = "\t";
        assertEquals("a\ruthor", StringUtil.deletePrefix(cse, prefix));

    }

    @Test
    public void deleteSuffix() {
        CharSequence cse = null;
        CharSequence suffix = null;
        assertNull(StringUtil.deleteSuffix(cse, suffix));
        cse = "author: miles";
        assertEquals(cse, StringUtil.deleteSuffix(cse, suffix));
        suffix = "";
        assertEquals(cse, StringUtil.deleteSuffix(cse, suffix));
        suffix = "auth";
        assertEquals("author: miles", StringUtil.deleteSuffix(cse, suffix));
        suffix = "les";
        assertEquals("author: mi", StringUtil.deleteSuffix(cse, suffix));
        suffix = "AUTH";
        assertEquals(cse, StringUtil.deleteSuffix(cse, suffix));
        cse = "\ta\ruthor";
        suffix = "\t";
        assertEquals("\ta\ruthor", StringUtil.deleteSuffix(cse, suffix));
        cse = "\ta\ruthor\t";
        assertEquals("\ta\ruthor", StringUtil.deleteSuffix(cse, suffix));
    }

    @Test
    public void capitalize() {
        CharSequence cse = null;
        assertNull(StringUtil.capitalize(cse));
        cse = "";
        assertEquals(cse, StringUtil.capitalize(cse));
        cse = "\tau";
        assertEquals(cse, StringUtil.capitalize(cse));
        cse = "author";
        assertEquals("Author", StringUtil.capitalize(cse));
        cse = "An";
        assertEquals(cse, StringUtil.capitalize(cse));
    }

    @Test
    public void unCapitalize() {
        CharSequence cse = null;
        assertNull(StringUtil.unCapitalize(cse));
        cse = "";
        assertEquals(cse, StringUtil.unCapitalize(cse));
        cse = "\tau";
        assertEquals(cse, StringUtil.unCapitalize(cse));
        cse = "author";
        assertEquals("author", StringUtil.unCapitalize(cse));
        cse = "An";
        assertEquals("an", StringUtil.unCapitalize(cse));

    }

    @Test
    public void changeCharacterCase() {
    }

    @Test
    public void camelCaseToUnderline() {
        CharSequence cse = null;
        assertNull(StringUtil.camelCaseToUnderline(cse));
        cse = "";
        assertEquals(cse, StringUtil.camelCaseToUnderline(cse));
        cse = "USA";

    }


}