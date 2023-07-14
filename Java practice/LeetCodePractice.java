import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LeetCodePractice {
    /**
     * 有效数字（按顺序）可以分成以下几个部分：
     * 一个 小数 或者 整数
     * （可选）一个 'e' 或 'E' ，后面跟着一个 整数
     * 小数（按顺序）可以分成以下几个部分：
     * （可选）一个符号字符（'+' 或 '-'）
     * 下述格式之一：
     * 至少一位数字，后面跟着一个点 '.'
     * 至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
     * 一个点 '.' ，后面跟着至少一位数字
     * 整数（按顺序）可以分成以下几个部分：
     * （可选）一个符号字符（'+' 或 '-'）
     * 至少一位数字
     * 部分有效数字列举如下：["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"]
     * 部分无效数字列举如下：["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"]
     * 给你一个字符串 s ，如果 s 是一个 有效数字 ，请返回 true 。
     * <p>
     * 例1：
     * 输入：s = "0"
     * 输出：true
     * 例2：
     * 输入：s = "e"
     * 输出：false
     * 例3：
     * 输入：s = "."
     * 输出：false
     * 例4：
     * 输入：s = ".1"
     * 输出：true
     * </p>
     *
     * @param s 字符串
     * @return 是否是有效数字
     * @see <a href="https://leetcode-cn.com/problems/valid-number/">...</a>
     */
    private boolean isNumber(String s) {
        if (s == null || s.length() == 0) return false;
        s = s.trim();
        if (s.length() == 0) return false;
        char[] sChars = s.toCharArray();
        boolean hasE = false, hasDot = false, hasNum = false;
        for (int i = 0; i < sChars.length; i++) {
            if (sChars[i] == '+' || sChars[i] == '-') {
                if ((i != 0 && sChars[i - 1] != 'e' && sChars[i - 1] != 'E') || i == sChars.length - 1) return false;
            } else if (sChars[i] == 'e' || sChars[i] == 'E') {
                if (i == 0 || i == sChars.length - 1 || hasE || !hasNum) return false;
                else hasE = true;
            } else if (sChars[i] == '.') {
                if (hasDot || hasE) return false;
                else hasDot = true;
            } else if (sChars[i] >= '0' && sChars[i] <= '9') hasNum = true;
            else return false;
        }
        return hasNum;
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testIsNumber() {
        String[] testCases = {"6+1", ".e1", "0", "e", ".", ".1", "+6e-1", "53.5e93", "-123.456e789", "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53", "2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "4e+."};
        boolean[] expected = {false, false, true, false, false, true, true, true, true, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, false};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], isNumber(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期结果：" + expected[i] + "\n实际结果：" + isNumber(testCases[i]));
        }
    }

    /**
     * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * I 可以放在 V (5) 和 X (10) 的左边，即IV表示 4，IX表示9。
     * X 可以放在 L (50) 和 C (100) 的左边，即XL表示 40，XC表示90。
     * C 可以放在 D (500) 和 M (1000) 的左边，即CD表示 400，CM表示900。
     * <p>
     * 例1：
     * 输入：num = 3
     * 输出："III"
     * 例2：
     * 输入：num = 4
     * 输出："IV"
     * </p>
     *
     * @param num 整数
     * @return 罗马数字
     * @see <a href="https://leetcode-cn.com/problems/integer-to-roman/">...</a>
     */
    private String intToRoman(int num) {
        if (num < 1 || num > 3999) return "";
        StringBuilder roman = new StringBuilder("M".repeat(num / 1000));
        if (num / 100 % 10 == 9) roman.append("CM");
        else if (num / 100 % 10 == 4) roman.append("CD");
        else {
            if (num / 100 % 10 >= 5) roman.append('D');
            roman.append("C".repeat(num / 100 % 5));
        }
        if (num / 10 % 10 == 9) roman.append("XC");
        else if (num / 10 % 10 == 4) roman.append("XL");
        else {
            if (num / 10 % 10 >= 5) roman.append('L');
            roman.append("X".repeat(num / 10 % 5));
        }
        if (num % 10 == 9) roman.append("IX");
        else if (num % 10 == 4) roman.append("IV");
        else {
            if (num % 10 >= 5) roman.append('V');
            roman.append("I".repeat(num % 5));
        }
        return roman.toString();
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testIntToRoman() {
        int[] testCases = {3, 4, 9, 58, 1994, 3999, 0, 4000, 1328, 440};
        String[] expected = {"III", "IV", "IX", "LVIII", "MCMXCIV", "MMMCMXCIX", "", "", "MCCCXXVIII", "CDXL"};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], intToRoman(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期结果：" + expected[i] + "\n实际结果：" + intToRoman(testCases[i]));
        }
    }

    /**
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 返回容器可以储存的最大水量。
     * <p>
     * 例1：
     * 输入：height = [1,8,6,2,5,4,8,3,7]
     * 输出：49
     * 例2：
     * 输入：height = [1,1]
     * 输出：1
     * </p>
     *
     * @param height 高度数组
     * @return 最大容量
     * @see <a href="https://leetcode-cn.com/problems/container-with-most-water/">...</a>
     */
    private int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;
        // 双指针法，时间复杂度O(n)。从两端开始，每次移动较小的一端，因为移动较大的一端，容器的容量只会减小。
        int maxArea = 0, left = 0, right = height.length - 1;
        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) left++;
            else right--;
        }
        /* 暴力解法，时间复杂度O(n^2)。计算每个可能的面积并找出最大值。
        for (int i = 0; i < height.length - 1; i++) {
            for (int j = 1; j < height.length; j++) {
                if (height[i] < height[j]) maxArea = Math.max(maxArea, height[i] * (j - i));
                else maxArea = Math.max(maxArea, height[j] * (j - i));
            }
        }
         */
        return maxArea;
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testMaxArea() {
        int[][] testCases = {{1, 1}, {1, 8, 6, 2, 5, 4, 8, 3, 7}, {4, 3, 2, 1, 4}, {1, 2, 1}};
        int[] expected = {1, 49, 16, 2};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], maxArea(testCases[i]), "\n测试用例：" + Arrays.toString(testCases[i]) + "\n预期结果：" + expected[i] + "\n实际结果：" + maxArea(testCases[i]));
        }
    }

    /**
     * 给你一个字符串 s(只包含从a-z的小写字母)和一个字符规律 p(只包含从a-z的小写字母)，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * <p>
     * 例1：
     * 输入：s = "aa" p = "a"
     * 输出：false
     * 解释："a" 无法匹配 "aa" 整个字符串。
     * 例2：
     * 输入：s = "aa" p = "a*"
     * 输出：true
     * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
     * 例3：
     * 输入：s = "ab" p = ".*"
     * 输出：true
     * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
     * </p>
     *
     * @param s 字符串
     * @param p 正则表达式
     * @return 是否匹配
     * @see <a href="https://leetcode-cn.com/problems/regular-expression-matching/">...</a>
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return true;
        else if (p.charAt(0) == '*') return false;
        else {
            // @see <a href="https://leetcode.cn/problems/regular-expression-matching/solutions/1/shou-hui-tu-jie-wo-tai-nan-liao-by-hyj8/">...</a>
            int sLen = s.length(), pLen = p.length();
            char[] sChars = s.toCharArray(), pChars = p.toCharArray();
            boolean[][] dp = new boolean[sLen + 1][pLen + 1];   // dp[i][j]表示s的前i个字符，p的前j个字符是否能够匹配。
            dp[0][0] = true;    // 当s和p都为空时，即“”时，匹配成功。
            for (int j = 1; j <= pLen; j++) {   // s为空，p不为空，由于*可以匹配0个字符，所以有可能为true。
                if (pChars[j - 1] == '*') {
                    dp[0][j] = dp[0][j - 2];
                }
            }
            for (int i = 1; i <= sLen; i++) {
                for (int j = 1; j <= pLen; j++) {
                    // 若文本串和正则表达式串末位字符能匹配上，则两个字符串指针各自前移一位，继续匹配。
                    if (sChars[i - 1] == pChars[j - 1] || pChars[j - 1] == '.') dp[i][j] = dp[i - 1][j - 1];
                    else if (pChars[j - 1] == '*') {    // 正则表达式串末位是*
                        // 正则表达式串*的前一个字符能够跟文本串的末位匹配上，则正则表达式串*可以匹配0次或多次。
                        if (sChars[i - 1] == pChars[j - 2] || pChars[j - 2] == '.')
                            dp[i][j] = dp[i][j - 2]     // *匹配0次或多次的情况：由于匹配不成功，因此s指针不移动，p指针移动2位，继续匹配。
                                    || dp[i - 1][j];     // *匹配1次或多次的情况：由于匹配成功，因此s指针移动1位，p指针不移动，继续匹配。
                            // 正则表达式串*的前一个字符不能够跟文本串的末位匹配，即匹配0次。
                        else dp[i][j] = dp[i][j - 2];     // *匹配0次的情况：由于未匹配成功，因此s指针不移动，p指针移动2位，继续匹配。
                    }
                }
            }
            return dp[sLen][pLen];
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testIsMatch() {
        String[] testCases1 = {"aa", "aa", "ab", "aab", "mississipp", "ab", "ab", "aaa", "aaaaaaaaaaaaaaaaaaab"};
        String[] testCases2 = {"a", "a*", ".*", "c*a*b", "mis*is*p*.", ".*c", ".*c*c*", "a*a", "a*a*a*a*a*a*a*a*a*a*a*a*a*a*a*"};
        boolean[] expected = {false, true, true, true, false, false, true, true, false};
        for (int i = 0; i < testCases1.length; i++) {
            assertEquals(expected[i], isMatch(testCases1[i], testCases2[i]), "\n测试用例：" + testCases1[i] + "\n正则表达式：" + testCases2[i] + "\n预期结果：" + expected[i] + "\n实际结果：" + isMatch(testCases1[i], testCases2[i]));
        }
    }

    /**
     * 把字符串中的数字转化成 32 位的有符号整数 x ，如果没有数字则返回 0。
     * 规则：
     * 1. 字符串开头的空格要去掉
     * 2. 如果第一个非空字符是正号或负号，将该符号与后面尽可能多的连续数字组合起来，作为该整数的正负号
     * 3. 如果第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数
     * 4. 如果字符串中的第一个非空字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则返回0
     * 5. 如果整数数超过 32 位有符号整数范围 [−2^31,  2^31 − 1] ，则固定返回最大值或最小值
     * 6. 假设环境只能存储 32 位有符号整数，不能存储 64 位整数
     * <p>
     * 例1：
     * 输入：s = "   42acd"
     * 输出：42
     * 例2：
     * 输入：s = "   -42- brad"
     * 输出：-42
     * 例3：
     * 输入：s = " with words 4193 "
     * 输出：0
     * </p>
     *
     * @param s 字符串
     * @return 转换后的整数
     * @see <a href="https://leetcode-cn.com/problems/string-to-integer-atoi/">...</a>
     */
    private int strToInt(String s) {
        if (s == null || s.length() == 0) return 0;
        s = s.trim();
        if (s.length() == 0) return 0;
        char[] chars = s.toCharArray();
        if (chars[0] != '-' && chars[0] != '+' && (chars[0] < '0' || chars[0] > '9')) return 0;
        int len = s.length(), result = 0;
        boolean isNegative = false;
        for (int i = 0; i < len; i++) {
            if (chars[i] == '-' && i == 0) isNegative = true;
            else if (chars[i] == '+' && i == 0) continue;
            else if (chars[i] >= '0' && chars[i] <= '9') {
                if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && chars[i] > '7'))
                    return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                result = result * 10 + chars[i] - '0';
            } else break;
        }
        return isNegative ? -result : result;
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testStrToInt() {
        String[] testCases = {"42", "   -42", "4193 with words", "words and 987", "-91283472332", "+-12", "00000-42a1234", "2147483648", "-2147483648", "3.14159", "+1", "1a23", " ", " 00012345678", "-5-", "  -0012a42", "+1+1"};
        int[] expected = {42, -42, 4193, 0, -2147483648, 0, 0, 2147483647, -2147483648, 3, 1, 1, 0, 12345678, -5, -12, 1};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], strToInt(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期转换整数：" + expected[i] + "\n实际转换整数：" + strToInt(testCases[i]));
        }
    }

    /**
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−2^31,  2^31 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     * <p>
     * 例1：
     * 输入：x = 123
     * 输出：321
     * 例2：
     * 输入：x = -120
     * 输出：-21
     * </p>
     *
     * @param x 整数
     * @return 反转后的整数
     * @see <a href="https://leetcode-cn.com/problems/reverse-integer/">...</a>
     */
    private int reverse(int x) {
        if (x < 10 && x > -10) return x;
        while (x % 10 == 0) x /= 10;
        int result = 0;
        while (x != 0) {
            if (result > Integer.MAX_VALUE / 10 || result < Integer.MIN_VALUE / 10) return 0; // 溢出判断
            else {
                result = result * 10 + x % 10;
                x /= 10;
            }
        }
        return result;
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testReverse() {
        int[] intCases = {123, -123, 120, 0, 1534236469, -2147483648};
        int[] expected = {321, -321, 21, 0, 0, 0};
        for (int i = 0; i < intCases.length; i++) {
            assertEquals(expected[i], reverse(intCases[i]), "\n测试用例：" + intCases[i] + "\n预期反转整数：" + expected[i] + "\n实际反转整数：" + reverse(intCases[i]));
        }
    }

    /**
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     * <p>
     * 例1：
     * 输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 例2：
     * 输入字符串为 "PAYPALISHIRING" 行数为 4 时，排列如下：
     * P     I    N
     * A   L S  I G
     * Y A   H R
     * P     I
     * 例3：
     * 输入字符串为 "A" 行数为 1 时，排列如下：
     * A
     * 例4：
     * 输入字符串为 "AB" 行数为 1 时，排列如下：
     * AB
     * </p>
     *
     * @param s       字符串
     * @param numRows 行数
     * @return 转换后的字符串
     * @see <a href="https://leetcode-cn.com/problems/zigzag-conversion/">...</a>
     */
    private String convert(String s, int numRows) {
        if (s == null || s.length() == 0) return "";
        else if (numRows == 1 || numRows >= s.length()) return s;
        else {
            char[] chars = s.toCharArray();
            int len = s.length();
            StringBuilder result = new StringBuilder(len);
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j <= len / 2; j++) {
                    if (((j % (numRows - 1) == 0) || (j % (numRows - 1) == numRows - 1 - i)) && j * 2 + i < len)
                        result.append(chars[j * 2 + i]);
                }
            }
            return result.toString();
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testConvert() {
        String[] stringCases = {"PAYPALISHIRING", "PAYPALISHIRING", "A", "AB", "PAYPALISHIRING", "A", "ABC"};
        int[] intCases = {3, 4, 1, 2, 5, 2, 2};
        String[] expected = {"PAHNAPLSIIGYIR", "PINALSIGYAHRPI", "A", "AB", "PHASIYIRPLIGAN", "A", "ACB"};
        for (int i = 0; i < stringCases.length; i++) {
            assertEquals(expected[i], convert(stringCases[i], intCases[i]), "\n测试用例：" + stringCases[i] + "    " + intCases[i] + "\n预期转换字符串：" + expected[i] + "\n实际转换字符串：" + convert(stringCases[i], intCases[i]));
        }
    }

    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     * <p>
     * 例1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba"也正确，但为了测试简单化，只返回第一个最长的子串。
     * 例2：
     * 输入：s = "cbbd"
     * 输出："bb"
     * </p>
     *
     * @param s 字符串
     * @return 最长回文子串
     * @see <a href="https://leetcode-cn.com/problems/longest-palindromic-substring/">...</a>
     */
    private String longestPalindrome(String s) {
        if (s == null || s.length() == 0) return "";
        else if (s.length() == 1) return s;
        else {
            String longestPalindrome = "";
            char[] chars = s.toCharArray();
            int len = s.length();
            for (int i = 0; i < len - longestPalindrome.length() / 2; i++) {
                /* 暴力解法，时间复杂度O(n^4)。设置两个指针，不断截取子串，判断其是否为回文子串。若是且比当前发现的最长回文子串长，则更新最长回文子串。
                for (int j = i + longestPalindrome.length() + 1; j <= len; j++) { // j = i + longestPalindrome.length() + 1，是因为比当前最长回文子串短的子串不需要再判断了。
                    if (s.substring(i, j).contentEquals(new StringBuilder(s.substring(i, j)).reverse())) {
                        longestPalindrome = longestPalindrome.length() >= s.substring(i, j).length() ? longestPalindrome : s.substring(i, j);
                    }
                }
                */
                // 中心扩散法，时间复杂度O(n^2)。遍历字符串，以每个字符为中心，向两边扩散，判断是否为回文子串。若是且比当前发现的最长回文子串长，则更新最长回文子串。
                int left = i, right = i;
                while (left >= 0 && chars[left] == chars[i]) left--;

                while (right < len && chars[right] == chars[i]) right++;


                while (left >= 0 && right < len && chars[left] == chars[right]) {
                    left--;
                    right++;
                }
                if (right - left - 1 > longestPalindrome.length()) longestPalindrome = s.substring(left + 1, right);
            }
            return longestPalindrome;
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testLongestPalindrome() {
        String[] testCases = new String[]{"babad", "acdbbdaa", "a", "ac", "bb", "ccc", "caba", "ababababa", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "wsgdzojcrxtfqcfkhhcuxxnbwtxzkkeunmpdsqfvgfjhusholnwrhmzexhfqppatkexuzdllrbaxygmovqwfvmmbvuuctcwxhrmepxmnxlxdkyzfsqypuroxdczuilbjypnirljxfgpuhhgusflhalorkcvqfknnkqyprxlwmakqszsdqnfovptsgbppvejvukbxaybccxzeqcjhmnexlaafmycwopxntuisxcitxdbarsicvwrvjmxsapmhbbnuivzhkgcrshokkioagwidhmfzjwwywastecjsolxmhfnmgommkoimiwlgwxxdsxhuwwjhpxxgmeuzhdzmuqhmhnfwwokgvwsznfcoxbferdonrexzanpymxtfojodcfydedlxmxeffhwjeegqnagoqlwwdctbqnuxngrgovrjesrkjrfjawknbrsfywljscfvnjhczhyeoyzrtbkvvfvofykkwoiclgxyaddhpdoztdhcbauaagjmfzkkdqexkczfsztckdlujgqzjyuittnudpldjvsbwbzcsazjpxrwfafievenvuetzcxynnmskoytgznvqdlkhaowjtetveahpjguiowkiuvikwewmgxhgfjuzkgrkqhmxxavbriftadtogmhlatczusxkktcsyrnwjbeshifzbykqibghmmvecwwtwdcscikyzyiqlgwzycptlxiwfaigyhrlgtjocvajcnqyenxrnjgogeqtvkxllxpuoxargzgcsfwavwbnktchwjebvwwhfghqkcjhuhuqwcdsixrkfjxuzvhjxlyoxswdlwfytgbtqbeimzzogzrlovcdpseoafuxfmrhdswwictsctawjanvoafvzqanvhaohgndbsxlzuymvfflyswnkvpsvqezekeidadatsymbvgwobdrixisknqpehddjrsntkqpsfxictqbnedjmsveurvrtvpvzbengdijkfcogpcrvwyf", "borcdubqiupahpwjizjjbkncelfazeqynfbrnzuvbhjnyvrlkdyfyjjxnprfocrmisugizsgvhszooktdwhehojbrdbtgkiwkhpfjfcstwcajiuediebdhukqnroxbgtvottummbypokdljjvnthcbesoyigscekrqswdpalnjnhcnqrarxuufzzmkwizptyvjhpfidgmskuaggtpxqisdlyloznkarxofzeeyvteynluofuhbllyiszszbwnsglqjkignusarxsjvctpgiwnhdufmfpanfwxjwlmhgllzsmdpncbwnsbdtsvrjybynifywkulqnzprcxockbhrhvnwxrkvwumyieazclcviumvormynbryaziijpdinwatwqppamfiqfiojgwkvfzyxadyfjrgmtttvlgkqghgbcfhkigfojjkhskzenlpasyozcsuccdxhulcubsgomvcrbqwakrraesfifecmoztayrdjicypakrrneulfbqqxtrdelggedvloebqaztmfyfkhuonrognejxpesmwgnmnnnamvkxqvzrgzdqtvuhccryeowywmbixktnkqnwzlzfvloyqcageugmjqihyjxhssmhkfzxpzxmgpjgsduogfolnkkqizitbvvgrkczmojxnabruwwzxxqcevdwvtiigwckpxnnxxxdhxpgomncttjutrsvyrqcfwxhexhaguddkiesmfrqfjfdaqbkeqinwicphktffuwcazlmerdhraufbpcznbuipmymipxbsdhuesmcwnkdvilqbnkaglloswcpqzdggnhjdkkumfuzihilrpcvemwllicoqiugobhrwdxtoefynqmccamhdtxujfudbglmiwqklriolqfkknbmindxtljulnxhipsieyohnczddabrxzjmompbtnnxvivmoyfzfrxlufowtqzojfclmatthjtbhotdoheusnpirwicbtyrcuojsdmfcx"}, expected = new String[]{"bab", "dbbd", "a", "a", "bb", "ccc", "aba", "ababababa", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "knnk", "xnnx"};
        for (int i = 0; i < testCases.length; i++)
            assertEquals(expected[i], longestPalindrome(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期最长回文子串：" + expected[i] + "\n实际最长回文子串：" + longestPalindrome(testCases[i]) + "\n");
    }

    /**
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1和nums2。请你找出并返回这两个正序数组的中位数 。
     * 算法的时间复杂度应该为 O(log (m+n))。
     * <p>
     * 例1：
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 例2：
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     * </p>
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 中位数
     * @see <a href="https://leetcode-cn.com/problems/median-of-two-sorted-arrays/">寻找两个正序数组的中位数</a>
     */
    private double findMedianSortedArrays(int[] nums1, int[] nums2) {
        /* 解法一：暴力合并两个数组，再排序，取中位数。时间复杂度O((m+n)log(m+n))
        int[] merged = new int[nums1.length + nums2.length];
        System.arraycopy(nums1, 0, merged, 0, nums1.length);
        System.arraycopy(nums2, 0, merged, nums1.length, nums2.length);
        Arrays.sort(merged);
        if (merged.length % 2 == 0) {
            return (merged[merged.length / 2] + merged[merged.length / 2 - 1]) / 2.0;
        } else return merged[merged.length / 2];
        */
        // 解法二：二分查找，时间复杂度O(log(m+n))
        int len = nums1.length + nums2.length;
        if (len % 2 == 0) return (getKthNumber(nums1, nums2, len / 2) + getKthNumber(nums1, nums2, len / 2 + 1)) / 2.0;
        else return getKthNumber(nums1, nums2, len / 2 + 1);
    }

    /**
     * 辅助类，用于辅助findMedianSortedArrays(int[] nums1, int[] nums2)方法，获取两个数组中第k小的数。
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @param k     第k小的值
     * @return 第k小的值
     */
    private double getKthNumber(int[] nums1, int[] nums2, int k) {
        if (nums1.length == 0) return nums2[k - 1];
        else if (nums2.length == 0) return nums1[k - 1];
        else if (k == 1) return Math.min(nums1[0], nums2[0]);
        else {
            if (nums1.length <= k / 2 - 1) {
                if (nums1[nums1.length / 2] < nums2[k / 2 - 1]) {
                    if (nums1[nums1.length - 1] < nums2[k / 2 - 1]) return nums2[k - nums1.length - 1];
                    else
                        return getKthNumber(Arrays.copyOfRange(nums1, nums1.length / 2, nums1.length), nums2, k - nums1.length / 2);
                } else return getKthNumber(nums1, Arrays.copyOfRange(nums2, k / 2, nums2.length), k - k / 2);
            } else if (nums2.length <= k / 2 - 1) {
                if (nums2[nums2.length / 2] <= nums1[k / 2 - 1]) {
                    if (nums2[nums2.length - 1] <= nums1[k / 2 - 1]) return nums1[k - nums2.length - 1];
                    else
                        return getKthNumber(Arrays.copyOfRange(nums2, nums2.length / 2, nums2.length), nums1, k - nums2.length / 2);
                } else return getKthNumber(Arrays.copyOfRange(nums1, k / 2, nums1.length), nums2, k - k / 2);
            } else if (nums1[k / 2 - 1] < nums2[k / 2 - 1])
                return getKthNumber(Arrays.copyOfRange(nums1, k / 2, nums1.length), nums2, k - k / 2);
            else return getKthNumber(nums1, Arrays.copyOfRange(nums2, k / 2, nums2.length), k - k / 2);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testFindMedianSortedArrays() {
        int[][] nums1 = {{1, 2}, {1, 3}, {0, 0}, {}, {1},}, nums2 = {{3, 4}, {2}, {0, 0}, {1, 2, 3, 4}, {2, 3, 4, 5, 6},};
        double[] excepted = {2.5, 2.0, 0.0, 2.5, 3.5,};
        for (int i = 0; i < nums1.length; i++) {
            assertEquals(excepted[i], findMedianSortedArrays(nums1[i], nums2[i]));
        }
    }

    /**
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
     * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
     * 返回复制链表的头节点。
     * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
     * val：一个表示 Node.val 的整数。
     * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
     * 你的代码只接受原链表的头节点 head 作为传入参数。
     * <p>
     * 例 1：
     * <img src="https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/09/e1.png" />
     * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 例 2：
     * <img src="https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/09/e2.png" />
     * 输入：head = [[1,1],[2,1]]
     * 输出：[[1,1],[2,1]]
     * </p>
     *
     * @param head 链表头节点
     * @return 复制的链表头节点
     * @see <a href="https://leetcode-cn.com/problems/copy-list-with-random-pointer/">复制带随机指针的链表</a>
     */
    private Node copyRandomList(Node head) {

    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testCopyRandomList() {
        Node node1 = new Node(1);
        node1.next = new Node(2);
        node1.next.next = new Node(3);
        node1.next.next.next = new Node(4);
        node1.next.next.next.next = new Node(5);
        assertEquals(copyRandomList(new Node(7)), new Node(7));
        assertNull(copyRandomList(null));
        assertEquals(copyRandomList(node1), node1);
    }

    /**
     * 给定两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以0开头。
     * <p>
     * 例1：
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     * 例2：输入：l1 = [0], l2 = [0]
     * 输出：[0]
     * 例 3：
     * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * 输出：[8,9,9,9,0,0,0,1]
     * </p>
     *
     * @param l1 链表1
     * @param l2 链表2
     * @return 相加后的链表
     * @see <a href="https://leetcode.cn/problems/add-two-numbers">...</a>
     */
    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum = 0;
        boolean isl1 = false, isl2 = false;
        if (l1 != null) {
            sum += l1.val;
            isl1 = l1.next != null;
        }
        if (l2 != null) {
            sum += l2.val;
            isl2 = l2.next != null;
        }
        if (isl1 && isl2) {
            if (sum >= 10) {
                l2.next.val += 1;
                return new ListNode(sum - 10, addTwoNumbers(l1.next, l2.next));
            } else return new ListNode(sum, addTwoNumbers(l1.next, l2.next));
        } else if (isl1) {
            if (sum >= 10) {
                l1.next.val += 1;
                return new ListNode(sum - 10, addTwoNumbers(l1.next, null));
            } else return new ListNode(sum, addTwoNumbers(l1.next, null));
        } else if (isl2) {
            if (sum >= 10) {
                l2.next.val += 1;
                return new ListNode(sum - 10, addTwoNumbers(null, l2.next));
            } else return new ListNode(sum, addTwoNumbers(null, l2.next));
        } else {
            if (sum >= 10) return new ListNode(sum - 10, new ListNode(1));
            else return new ListNode(sum);
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testAddTwoNumbers() {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        assertEquals(addTwoNumbers(l1, l2), new ListNode(7, new ListNode(0, new ListNode(8))));
    }

    /**
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     * <p>
     * 例1：
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     * </p>
     *
     * @param head 链表头节点
     * @return 反转后的链表头节点
     * @see <a href="https://leetcode-cn.com/problems/reverse-linked-list/">...</a>
     */
    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        else {
            // 递归解法，时间复杂度O(n)，空间复杂度O(1)。
            ListNode result = reverseList(head.next); // 递归调用，找到最后一个节点
            head.next.next = head; // 将head的下一个节点的next指向head,即将head的下一个节点变成了head的上一个节点
            head.next = null; // 根据上一条指令，此指令将换位后的head的next指向null。
            /* 递归解法，时间复杂度O(n)，空间复杂度O(n)。
            ListNode result = reverseList(head.next), p = result;
            while (p.next != null) p = p.next; // 找到最后一个节点
            p.next = new ListNode(head.val);
             */
            return result;
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testReverseList() {
        assertEquals(reverseList(new ListNode(7)), new ListNode(7));
        assertNull(reverseList(null));
        assertEquals(reverseList(new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), new ListNode(5, new ListNode(4, new ListNode(3, new ListNode(2)))));
    }

    /**
     * 给定一个罗马数字，将其转换成整数。
     * <p>
     * 例1：
     * 输入：s = "III"
     * 输出：3
     * 例2：
     * 输入：s = "IV"
     * 输出：4
     * 例3：
     * 输入：s = "IX"
     * 输出：9
     * </p>
     *
     * @param s 罗马数字
     * @return 整数
     * @see <a href="https://leetcode-cn.com/problems/roman-to-integer/">...</a>
     */
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] sChars = s.toCharArray();
        int result = 0;
        for (int i = 0; i < sChars.length; i++) {
            switch (sChars[i]) {
                case 'I' -> {
                    if (i + 1 < s.length() && sChars[i + 1] == 'V') {
                        result += 4;
                        i++;
                    } else if (i + 1 < s.length() && sChars[i + 1] == 'X') {
                        result += 9;
                        i++;
                    } else result += 1;
                }
                case 'X' -> {
                    if (i + 1 < s.length() && sChars[i + 1] == 'L') {
                        result += 40;
                        i++;
                    } else if (i + 1 < s.length() && sChars[i + 1] == 'C') {
                        result += 90;
                        i++;
                    } else result += 10;
                }
                case 'C' -> {
                    if (i + 1 < s.length() && sChars[i + 1] == 'D') {
                        result += 400;
                        i++;
                    } else if (i + 1 < s.length() && sChars[i + 1] == 'M') {
                        result += 900;
                        i++;
                    } else result += 100;
                }
                case 'V' -> result += 5;
                case 'L' -> result += 50;
                case 'D' -> result += 500;
                case 'M' -> result += 1000;
            }
        }
        return result;
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testRomanToInt() {
        String[] testCases = {"III", "IV", "IX", "LVIII", "MCMXCIV", "MDCXCV", "MMCCCXCIX", "MMMCMXCIX"};
        int[] expected = {3, 4, 9, 58, 1994, 1695, 2399, 3999};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], romanToInt(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期结果：" + expected[i] + "\n实际结果：" + romanToInt(testCases[i]));
        }
    }

    /**
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * <p>
     * 例1：
     * 输入：x = 121
     * 输出：true
     * 例2：
     * 输入：x = -121
     * 输出：false
     * </p>
     *
     * @param x 整数
     * @return 是否是回文数
     * @see <a href="https://leetcode-cn.com/problems/palindrome-number/">...</a>
     */
    private boolean isPalindrome(int x) {
        if (x < 0) return false;
        else if (x < 10) return true;
        else if (x % 10 == 0) return false;
        else {
            // 解法1：整数倒置法。时间复杂度O(n)，空间复杂度O(1)
            int reverse = 0, input = x;
            while (x > 0) {
                if (reverse > Integer.MAX_VALUE / 10 + 7) return false;
                reverse = reverse * 10 + x % 10;
                x /= 10;
            }
            return input == reverse;
            /*
            // 解法2：int转string，然后用字符串倒置法。时间复杂度O(n)，空间复杂度O(n)
            String str = String.valueOf(x);
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != chars[chars.length - 1 - i]) return false;
            }
            return true;
            */
        }
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void testIsPalindrome() {
        int[] testCases = {123, -121, 121, 1210, 11, 0, 1001};
        boolean[] expected = {false, false, true, false, true, true, true};
        for (int i = 0; i < testCases.length; i++) {
            assertEquals(expected[i], isPalindrome(testCases[i]), "\n测试用例：" + testCases[i] + "\n预期转换整数：" + expected[i] + "\n实际转换整数：" + isPalindrome(testCases[i]));
        }
    }

    /**
     * Definition for singly-linked list.
     */
    private static class ListNode {
        int val;
        ListNode next;

        private ListNode(int val) {
            this.val = val;
        }

        private ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListNode listNode = (ListNode) o;
            return val == listNode.val && Objects.equals(next, listNode.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, next);
        }
    }

    private static class Node {
        int val;
        Node next;
        Node random;

        private Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
