import java.io.Serializable;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * After counting the number of times a word appears in a given input file, the
 * program generates an HTML document with a tag cloud of all the words listed
 * in alphabetical order with varying font sizes.
 *
 * @author JT Vendetti & Selin Kirbas
 */
public final class TagCloudGeneratorWJavaComp {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorWJavaComp() {
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
     * Comparator for comparing {@code Map.Entry<String, Integer>} objects based
     * on their values in descending order.
     */

    public static class CountComparator implements Comparator<Map.Entry<String, Integer>>,
    Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Comparator for comparing {@code Map.Entry<String, Integer>} objects based
     * on their keys (case-insensitive).
     */
    public static class WordComparator implements Comparator<Map.Entry<String, Integer>>,
    Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Adds the words and number of instances from the file to the Map.
     *
     * @param inputFile
     *            the {@code BufferedReader} to read the file
     * @param wordCounts
     *            the terms and counts to put in {@code Map}
     * @param separators
     *            the {@code Set} that has the characters from generateElements
     * @ensures Map<String, Integer> has the terms and definitions that were in
     *          the file
     */
    private static void createMap(BufferedReader inputFile,
            Map<String, Integer> wordCounts, Set<Character> separators) {
        try {
            String line;
            while ((line = inputFile.readLine()) != null) {
                int position = 0;

                while (position < line.length()) {
                    String wordOrSeparator = nextWordOrSeparator(line, position,
                            separators);
                    position += wordOrSeparator.length();

                    if (!separators.contains(wordOrSeparator.charAt(0))) {
                        String word = wordOrSeparator.toLowerCase();
                        if (wordCounts.containsKey(word)) {
                            int count = wordCounts.get(word);
                            wordCounts.put(word, count + 1);
                        } else {
                            wordCounts.put(word, 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file");
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
        char c = text.charAt(position);
        boolean isSeparator = separators.contains(c);

        int i = position;

        //sort through text for separators and words
        while (i < text.length() && separators.contains(text.charAt(i)) == isSeparator) {
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
        Set<Character> sep = new TreeSet<>();

        String separatorStr = " \t\n\r,-.!?[]';:/() ";

        generateElements(separatorStr, sep);

        return sep;
    }

    /**
     * Generates the header section of the HTML output.
     *
     * @param out
     *            The {@code BufferedWriter} for the output
     * @param fileName
     *            The name of the input file
     * @param num
     *            The number of words being shown
     * @ensures The output file contains the header section for the HTML
     */
    private static void generateHTMLHeader(BufferedWriter out, String fileName,
            int num) throws IOException {
        out.write("<html> <head>");
        out.write("<title>Top " + num + " words in " + fileName + "</title>");
        out.write("<link href=\"http://web.cse.ohio-state.edu/"
                + "software/2231/web-sw2/assignments/"
                + "projects/tag-cloud-generator/data/tagcloud.css\""
                + "rel=\"stylesheet\" type=\"text/css\">\n");
        out.write("</head> <body>");
        out.write("<h2>Top " + num + " words in " + fileName + "</h2>");
        out.write("<hr>");
        out.write("<div class= \"cdiv\">");
        out.write(
                "<p class=\"cbox>\" style=\"background-color: lightgray;\">");
    }

    /**
     * Generates the body section of the HTML output containing the tag cloud.
     *
     * @param in
     *            The {@code BufferedReader} for the input file
     * @param out
     *            The {@code BufferedWriter} for the output
     * @param topNWords
     *            The {@code Map} of top words and their counts
     * @param num
     *            The number of words being shown
     * @param wordList
     *            The {@code List} for words in ascending order
     * @ensures The output file contains the body section with the tag cloud
     */
    private static void generateHTMLBody(BufferedReader in, BufferedWriter out,
            Map<String, Integer> topNWords, int num,
            List<Map.Entry<String, Integer>> wordList) throws IOException {

        int maxCount = Integer.MIN_VALUE;
        int minCount = Integer.MAX_VALUE;

        //go through sorted pairs to find max and min counts
        for (Map.Entry<String, Integer> entry : wordList) {
            maxCount = Math.max(maxCount, entry.getValue());
            minCount = Math.min(minCount, entry.getValue());
        }

        //print tags to create gray box for tag cloud
        out.write("<div class=\"cdiv\">");
        out.write("<p class=\"cbox\" style=\"background-color: lightgray;\">");

        //output sorted words with appropriate font sizes
        for (Map.Entry<String, Integer> entry : wordList) {
            //calculate font size based on frequency relative to min and max counts
            int fontSize = MIN_FONT_SIZE + ((MAX_FONT_SIZE - MIN_FONT_SIZE)
                    * (entry.getValue() - minCount) / (maxCount - minCount + 1));

            out.write("<span style=\"cursor:default\" class=\"f" + fontSize
                    + "\" title=\"count: " + entry.getValue() + "\">" + entry.getKey()
                    + "</span> ");
        }

        //print closing tags
        out.write("</p>");
        out.write("</div>");
        out.write("</body>");
        out.write("</html>");
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
        Map<String, Integer> topWords = new HashMap<>();
        int count = 0;

        //go through sortedWords to retrieve top words
        for (Map.Entry<String, Integer> entry : sortedWords.entrySet()) {
            if (count < n) {
                //add word and its count to Map
                topWords.put(entry.getKey(), entry.getValue());
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
        List<Map.Entry<String, Integer>> entryList =
                new ArrayList<>(wordCount.entrySet());

        entryList.sort(new WordComparator());

        Map<String, Integer> sortedWords = new LinkedHashMap<>();

        //get sorted pairs add to sortedWords
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedWords.put(entry.getKey(), entry.getValue());
        }

        return sortedWords;
    }

    /**
     * Outputs the tag cloud in HTML format based on the provided parameters.
     *
     * @param m
     *            The {@code Map} containing the terms and counts
     * @param in
     *            The {@code BufferedReader} for the input file
     * @param out
     *            The {@code BufferedWriter} for the output
     * @param num
     *            The number of words being shown
     * @param topWords
     *            The {@code Map} of top words and their counts
     * @param fileName
     *            The name of the input file
     * @ensures The output file contains the tag cloud
     */
    private static void outputTagCloud(Map<String, Integer> m,
            BufferedReader in, BufferedWriter out,
            int num, Map<String, Integer> topWords, String fileName) throws IOException {

        //print header
        generateHTMLHeader(out, fileName, num);

        //sort by count in descending order
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(m.entrySet());
        entryList.sort(new CountComparator());

        //move top words from count to word
        int n = Math.min(num, entryList.size());

        List<Map.Entry<String, Integer>> wordList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            wordList.add(entryList.get(i));
        }

        //sort by word (case insensitive)
        wordList.sort(new WordComparator());

        //print rest of HTML
        generateHTMLBody(in, out, topWords, num, wordList);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command-line arguments
     */
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        BufferedReader inputFile = null;
        BufferedWriter outputFile = null;
        PrintWriter outputPage = null;

        try {
            //ask for input file
            System.out.println("Write the name of your input file");
            String inputFileName = in.readLine();
            inputFile = new BufferedReader(new FileReader(inputFileName));

            //ask for output file
            System.out.println("Write the name of your output file");
            String outputFileName = in.readLine();
            outputFile = new BufferedWriter(new FileWriter(outputFileName));

            //create output page with output file where tag cloud is made
            outputPage = new PrintWriter(outputFile);

            //ask for number of words
            int num;
            System.out.println("How many words would you like to be included in the "
                    + "generated tag cloud?");
            num = Integer.parseInt(in.readLine());

            if (num <= 0) {
                throw new IllegalArgumentException(
                        "Number of Words Needs to be Positive");
            }

            //create Set where characters are going to be stored
            Set<Character> separators = generateSeparators();

            //create Map where terms and number of appearances are stored
            Map<String, Integer> wordCounts = new HashMap<>();
            createMap(inputFile, wordCounts, separators);

            Map<String, Integer> topWords = getTopWords(wordCounts, num);

            //call methods with opening tags, tag cloud, and closing tags
            outputTagCloud(wordCounts, inputFile, outputFile, num,
                    topWords, inputFileName);

        } catch (IOException e) {
            System.err.println("Error processing files");
        } finally {
            //close the files
            try {
                if (outputPage != null) {
                    outputPage.close();
                }
                if (outputFile != null) {
                    outputFile.close();
                }
                if (inputFile != null) {
                    inputFile.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing files");
            }
        }
    }

}
