import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.Reporter;

/**
 * After counting the number of times a word appears in a given input file, the
 * program generates an HTML document with a tag cloud of all the words listed
 * in alphabetical order with varying font sizes.
 *
 * @author Selin Kirbas & [Removed for privacy]
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
    }

    /**
     * The maximum font size used in the tag cloud.
     */
    private static final int MAX_FONT_SIZE = 48;

    /**
     * The minimum font size used in the tag cloud.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * Comparator for comparing {@code Map.Pair<String, Integer>} objects based
     * on their values in descending order.
     */
    private static class CountComparator implements Comparator<Map.Pair
    <String, Integer>>, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Comparator for comparing {@code Map.Pair<String, Integer>} objects based
     * on their keys (case-insensitive).
     */
    private static class WordComparator
    implements Comparator<Map.Pair<String, Integer>>, Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
    }

    /**
     * Adds the words and number of instances from the file to the Map.
     *
     * @param inputFile
     *            the {@code SimpleReader} to read the file
     * @param wordCounts
     *            the terms and counts to put in {@code Map}
     * @param separators
     *            the {@code Set} that has the characters from generateElements
     * @ensures Map<String, String> has the terms and definitions that were in
     *          the file
     */
    private static void createMap(SimpleReader inputFile, Map<String, Integer> wordCounts,
            Set<Character> separators) {
        while (!inputFile.atEOS()) {
            String line = inputFile.nextLine();
            int position = 0;

            while (position < line.length()) {
                String wordOrSeparator = nextWordOrSeparator(line, position, separators);
                position += wordOrSeparator.length();

                //update wordCounts Map
                if (!separators.contains(wordOrSeparator.charAt(0))) {
                    String word = wordOrSeparator.toLowerCase();
                    if (wordCounts.hasKey(word)) {
                        int count = wordCounts.value(word);
                        wordCounts.replaceValue(word, count + 1);
                    } else {
                        wordCounts.add(word, 1);
                    }
                }
            }
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}. From CSE 2221 homework [String – Words and
     * Separators].
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}. From CSE 2221 homework [String – Words and
     * Separators]
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        char c = text.charAt(position);
        boolean isSeparator = separators.contains(c);

        int i = position;

        //sort through text for separators and words
        while (i < text.length()
                && separators.contains(text.charAt(i)) == isSeparator) {
            i++;
        }

        return text.substring(position, i);
    }

    /**
     * Generates separators and returns the {@code Set}.
     *
     * @return Set of separators
     */
    private static Set<Character> generateSeparators() {
        Set<Character> sep = new Set1L<>();

        String separatorStr = " \t\n\r,-.!?[]';:/() ";

        generateElements(separatorStr, sep);

        return sep;
    }

    /**
     * Generates the header section of the HTML output.
     *
     * @param out
     *            The {@code SimpleWriter} for the output
     * @param fileName
     *            The name of the input file
     * @param num
     *            The number of words being shown
     * @ensures The output file contains the header section for the HTML
     */
    private static void generateHTMLHeader(SimpleWriter out, String fileName, int num) {
        out.print("<html> <head>");
        out.println("<title>Top " + num + " words in " + fileName + "</title>");
        out.print("<link href=\"http://web.cse.ohio-state.edu/"
                + "software/2231/web-sw2/assignments/"
                + "projects/tag-cloud-generator/data/tagcloud.css\""
                + "rel=\"stylesheet\" type=\"text/css\">\n");
        out.println("</head> <body>");
        out.println("<h2>Top " + num + " words in " + fileName + "</h2>");
        out.println("<hr>");
        out.println("<div class= \"cdiv\">");
        out.println(
                "<p class=\"cbox>\" style=\"background-color: lightgray;\">");
    }

    /**
     * Generates the body section of the HTML output containing the tag cloud.
     *
     * @param in
     *            The {@code SimpleReader} for the input file
     * @param out
     *            The {@code SimpleWriter} for the output
     * @param topNWords
     *            The {@code Map} of top words and their counts
     * @param n
     *            The number of words being shown
     * @param word
     *            The {@code SortingMachine} for words in ascending order
     * @ensures The output file contains the body section with the tag cloud
     */
    private static void generateHTMLBody(SimpleReader in, SimpleWriter out,
            Map<String, Integer> topNWords, int n,
            SortingMachine<Map.Pair<String, Integer>> word) {
        //change to extraction mode to retrieve sorted pairs
        word.changeToExtractionMode();

        int maxCount = Integer.MIN_VALUE;
        int minCount = Integer.MAX_VALUE;

        //go through sorted pairs to find max and min counts
        for (Map.Pair<String, Integer> pair : word) {
            maxCount = Math.max(maxCount, pair.value());
            minCount = Math.min(minCount, pair.value());
        }

        //print tags to create gray box for tag cloud
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\" style=\"background-color: lightgray;\">");

        //output sorted words with appropriate font sizes
        while (word.size() > 0) {
            Map.Pair<String, Integer> pair = word.removeFirst();

            //calculate font size based on frequency relative to min and max counts
            int fontSize = MIN_FONT_SIZE + ((MAX_FONT_SIZE - MIN_FONT_SIZE)
                    * (pair.value() - minCount) / (maxCount - minCount + 1));

            out.println("<span style=\"cursor:default\" class=\"f" + fontSize
                    + "\" title=\"count: " + pair.value() + "\">" + pair.key()
                    + "</span>");
        }

        //print closing tags
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Retrieves the top number of words from the word count {@code Map}.
     *
     * @param wordCount The {@code Map} containing word counts
     * @param n         The number of top words to retrieve
     * @return A {@code Map} containing the top words and their counts
     */
    private static Map<String, Integer> getTopWords(Map<String, Integer> wordCount,
            int n) {
        //sort Map
        Map<String, Integer> sortedWords = getSortedWords(wordCount);

        //get top words from sorted Map
        Map<String, Integer> topWords = new Map1L<>();
        int count = 0;

        //go through sortedWords to retrieve top words
        for (Map.Pair<String, Integer> pair : sortedWords) {
            if (count < n) {
                //add word and its count to Map
                topWords.add(pair.key(), pair.value());
                count++;
            }
        }

        return topWords;
    }

    /**
     * Sorts the word count {@code Map} in alphabetical order.
     *
     * @param wordCount
     *            The {@code Map} containing word counts
     * @return A sorted map of words and their counts
     */
    private static Map<String, Integer> getSortedWords(Map<String, Integer> wordCount) {
        SortingMachine<Map.Pair<String, Integer>> sort =
                new SortingMachine1L<>(new WordComparator());

        //add each pair from wordCount to SortingMachine
        for (Map.Pair<String, Integer> pair : wordCount) {
            sort.add(pair);
        }

        //change to extraction mode for sorting
        sort.changeToExtractionMode();

        Map<String, Integer> sortedWords = new Map1L<>();

        //get sorted pairs add to sortedWords
        for (Map.Pair<String, Integer> pair : sort) {
            sortedWords.add(pair.key(), pair.value());
        }

        return sortedWords;
    }

    /**
     * Outputs the tag cloud in HTML format based on the provided parameters.
     *
     * @param m
     *            The {@code Map} containing the terms and counts
     * @param in
     *            The {@code SimpleReader} for the input file
     * @param out
     *            The {@code SimpleWriter} for the output
     * @param num
     *            The number of words being shown
     * @param topWords
     *            The {@code Map} of top words and their counts
     * @param fileName
     *            The name of the input file
     * @ensures The output file contains the tag cloud
     */
    private static void outputTagCloud(Map<String, Integer> m,
            SimpleReader in, SimpleWriter out,
            int num, Map<String, Integer> topWords, String fileName) {
        //print header
        generateHTMLHeader(out, fileName, num);

        //create sorting machines
        SortingMachine<Map.Pair<String, Integer>> count = new SortingMachine1L<>(
                new CountComparator());
        SortingMachine<Map.Pair<String, Integer>> word = new SortingMachine1L<>(
                new WordComparator());

        //move pairs from Map to count
        for (Map.Pair<String, Integer> pair : m) {
            count.add(pair);
        }

        //change count to extraction mode
        count.changeToExtractionMode();

        //move top words from count to word
        int n = Math.min(num, count.size());
        for (int i = 0; i < n; i++) {
            Map.Pair<String, Integer> pair = count.removeFirst();
            word.add(pair);
        }

        //print rest of HTML
        generateHTMLBody(in, out, topWords, num, word);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //ask for input file
        out.println("Write the name of your input file");
        SimpleReader inputFile = new SimpleReader1L(in.nextLine());

        //ask for output file
        out.println("Write the name of your output file");
        String outputFile = in.nextLine();

        //ask for number of words
        out.println("How many words would you like to be included in the generated"
                + " tag cloud?");
        int num = in.nextInteger();

        Reporter.assertElseFatalError(num > 0,
                "Number of Words Needs to be Positive");

        //create SimpleWriter with output file where tag cloud is made
        SimpleWriter outputPage = new SimpleWriter1L(outputFile);

        //create Set where characters are going to be stored
        Set<Character> sep = generateSeparators();

        //create Map where terms and number of appearances are stored
        Map<String, Integer> wordCounts = new Map1L<>();
        createMap(inputFile, wordCounts, sep);

        Map<String, Integer> topWords;
        topWords = getTopWords(wordCounts, num);

        //call methods with opening tags, tag cloud, and closing tags
        outputTagCloud(wordCounts, in, outputPage, num, topWords, inputFile.name());

        //close the files
        in.close();
        out.close();
        outputPage.close();
    }

}
