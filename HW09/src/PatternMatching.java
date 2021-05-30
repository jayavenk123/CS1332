import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Jayanee Venkat
 * @version 1.0
 * @userid jvenkat8
 * @GTID 903628863
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("do not enter null parameters and make "
                    + "sure pattern length is greater than zero");
        }
        List<Integer> occurrences = new ArrayList<>();
        if (text.length() == 0 || text.length() < pattern.length()) {
            return occurrences;
        }

        int i = 0;
        int j = 0;
        int[] f = buildFailureTable(pattern, comparator);

        while (i <= text.length() - pattern.length()) {
            while (j < pattern.length()
                    && comparator.compare(text.charAt(i + j),
                            pattern.charAt(j)) == 0) {
                ++j;
            }

            if (j == 0) {
                i++;
            }  else {
                if (j == pattern.length()) {
                    occurrences.add(i);
                }

                int next = f[j - 1];
                i = i + j - next;
                j = next;
            }
        }

        return occurrences;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("do not enter null parameters for this method");
        }
        int[] f = new int[pattern.length()];
        f[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                f[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                f[j] = 0;
                j++;
            } else {
                i = f[i - 1];
            }
        }
        return f;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || text == null || comparator == null) {
            throw new IllegalArgumentException("do not enter a null value for the Comparator, "
                    + "text or pattern parameter. Make sure Pattern has a length greater than zero");
        }
        List<Integer> occurences = new ArrayList<>();
        Map<Character, Integer> last = buildLastTable(pattern);
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j = j - 1;
            }
            if (j == -1) {
                occurences.add(i);
                i++;
            } else {
                int shift;
                if (last.get(text.charAt(i + j)) == null) {
                    shift = -1;
                } else {
                    shift = last.get(text.charAt(i + j));
                }
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i = i + 1;
                }
            }
        }
        return occurences;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("do not enter a null parameter");
        }
        HashMap<Character, Integer> h = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            h.put(pattern.charAt(i), i);
        }
        return h;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0 || comparator == null || text == null) {
            throw new IllegalArgumentException("one of the parameters is null or the length of pattern is zero");
        }
        List<Integer> occurences = new ArrayList<>();
        int patternH = rHash(pattern, pattern.length());
        int textH = rHash(text, pattern.length());
        if (textH == -1) {
            return occurences;
        }
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternH == textH) {

                int j = 0;
                while (j < pattern.length()
                        && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    occurences.add(i);
                }

            }

            int len = pattern.length();

            if (i + len < text.length()) {
                textH = update(textH, len, text.charAt(i),
                        text.charAt(i + len));
            }
            ++i;
        }

        return occurences;
    }

    /**
     * computes the hash for rabinKarp function
     * @param text current text sequence
     * @param length length of original pattern
     * @return int type (the hash value)
     */
    private static int rHash(CharSequence text, int length) {
        if (text == null) {
            throw new IllegalArgumentException("Cannot generate hash from"
                    + "null string");
        }

        if (length <= 0 || length > text.length()) {
            return -1;
        }
        int temp = 0;
        for (int i = 0; i < length; i++) {
            temp += text.charAt(i) * pow(BASE, length - 1 - i);
        }
        return  temp;
    }

    /**
     * updates the hash value
     * @param old int type; old hash
     * @param length int type; length of pattern
     * @param oldC old characterl char type
     * @param newC new character; char type
     * @return int type the updated hash value
     */
    private static int update(int old, int length, char oldC, char newC) {
        if (length <= 0) {
            throw  new IllegalArgumentException("length cannot be zero"
                    + "or negative");
        }
        int newH = 0;
        old = old - oldC * pow(BASE, length - 1);
        old *= BASE;
        newH = old + newC;
        return newH;

    }

    /**
     * computes exponents for rabinKarp
     * @param base base of the exponent
     * @param exp power the base is raised to
     * @return int value
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}

