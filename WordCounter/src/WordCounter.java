import java.io.Serializable;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program processes a text file and generates an HTML page containing a
 * table of words from the text and their corresponding counts.
 *
 * @author Selin Kirbas
 *
 */
public final class WordCounter {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s. Taken from CSE 2221 lab 22.
     */
    private static class SortString
            implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
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

        //Sort through text for separators and words
        while (i < text.length()
                && separators.contains(text.charAt(i)) == isSeparator) {
            i++;
        }

        return text.substring(position, i);
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
            charSet.add(str.charAt(i));
        }
    }

    /**
     * Updates the word count in {@code Map}.
     *
     * @param word
     *            the word to update
     * @param wordCount
     *            the {@code Map} holding words and their counts
     * @updates {@code wordCount}
     */
    private static void updateWordCount(String word,
            Map<String, Integer> wordCount) {

        //Check if the Map already has the word, add it or increase count
        //depending on if it is or isn't in the Map already
        if (!wordCount.hasKey(word)) {
            wordCount.add(word, 1);
        } else {
            int count = wordCount.value(word);
            wordCount.replaceValue(word, count + 1);
        }
    }

    /**
     * Scans the provided input text ({@code SimpleReader}) and adds to
     * {@code Map} with words & their counts.
     *
     * @param input
     *            the input text source ({@code SimpleReader})
     * @param separatorSet
     *            the {@code Set} containing separator characters
     * @param wordCount
     *            the {@code Map} holding words and their counts
     * @param separator
     *            the {@code String} of separators
     * @updates {@code wordCount}
     * @ensures <pre>
     * input's wordCount = {@code Map}'s Key and Value
     * </pre>
     */
    public static void count(SimpleReader input, Set<Character> separatorSet,
            Map<String, Integer> wordCount, String separator) {
        assert input != null : "Violation of: input is not null";
        assert wordCount != null : "Violation of: wordCount is not null";

        generateElements(separator, separatorSet);

        //Create a loop that stops at the end of the file
        while (!input.atEOS()) {
            String line = input.nextLine();

            int position = 0;

            while (position < line.length()) {
                String wordOrSeparator = nextWordOrSeparator(line, position,
                        separatorSet);
                //Make sure word case doesn't affect counting
                wordOrSeparator = wordOrSeparator.toLowerCase();

                position += wordOrSeparator.length();

                if (!separatorSet.contains(wordOrSeparator.charAt(0))) {
                    updateWordCount(wordOrSeparator, wordCount);
                }
            }
        }
    }

    /**
     * Generates an HTML table with a list of words in alphabetical order along
     * with how many times each word is used in the text.
     *
     * @param output
     *            the output text file used {@code SimpleWriter}
     * @param wordCount
     *            the {@code Map} containing all the words and their counts in
     *            textInput
     * @param wordQueue
     *            the {@code Queue} containing the {@code wordCount}'s keys
     * @param input
     *            the input text file read in by {@code SimpleReader}
     * @param comparator
     *            the {@code Comparator} used to compare the words
     * @ensures <pre>
     * {@code HTML code table elements = entries(word)}
     * </pre>
     *
     */
    public static void createHTML(SimpleWriter output,
            Map<String, Integer> wordCount, Queue<String> wordQueue,
            SimpleReader input, Comparator<String> comparator) {
        assert wordCount != null : "Violation of: words is not null";
        assert wordQueue != null : "Violation of: keyQueue is not null";
        assert output.isOpen() : "Violation of: outFile is open";
        assert input.isOpen() : "Violation of: inFile is open";

        //Create header for HTML
        output.println("<html>");
        output.println("<head>");
        output.println("<title>Words Counted in " + input.name() + "</title>");
        output.println("<body>");

        //Create title & table
        output.println("<h2>Words Counted in " + input.name() + "</h2>");
        output.println("<hr />");

        output.println("<table border=\"1\">");
        output.println("<tr>");
        output.println("<th>Words</th>");
        output.println("<th>Counts</th>");
        output.println("</tr>");

        //Collect all words from the map & sort
        Queue<String> sortedWords = new Queue1L<>();

        for (Map.Pair<String, Integer> entry : wordCount) {
            sortedWords.enqueue(entry.key());
        }
        sortedWords.sort(comparator);

        //Process & add sorted words to table
        for (String key : sortedWords) {
            output.println("<tr>");
            output.println("<td>" + key + "</td>");

            if (wordCount.hasKey(key)) {
                output.println("<td>" + wordCount.value(key) + "</td>");
            } else {
                output.println("<td>0</td>");
            }
            output.println("</tr>");
        }

        //Output closing tags
        output.println("</table>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        /*
         * Open input and output streams
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        String separator = "\t\n\r,-.!?[]';:/() ";
        Comparator<String> comparator = new SortString();

        //Ask for input file
        out.println("Write the path of your input file.");
        SimpleReader input = new SimpleReader1L(in.nextLine());

        //Ask for name & create output file
        out.println(
                "Write a name for your output file including the HTML extension.");
        SimpleWriter output = new SimpleWriter1L(in.nextLine());

        //Set up to count
        Map<String, Integer> wordCount = new Map1L<String, Integer>();
        Set<Character> sepSet = new Set1L<>();
        count(input, sepSet, wordCount, separator);

        //Generate the HTML
        Queue<String> wordQueue = new Queue1L<String>();
        createHTML(output, wordCount, wordQueue, input, comparator);

        //Indicate program ran successfully
        out.println("Finished.");

        /*
         * Close input and output streams
         */
        input.close();
        output.close();
        in.close();
        out.close();
    }

}
