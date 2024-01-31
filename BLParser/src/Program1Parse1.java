import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Selin Kirbas & [Removed for privacy]
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
        + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        String[] primitives = { "move", "turnleft", "turnright", "infect", "skip" };

        //check "INSTRUCTION" token
        Reporter.assertElseFatalError(tokens.dequeue().equals("INSTRUCTION"),
                "Error: Expected INSTRUCTION");

        //get instruction
        String name = tokens.dequeue();

        //check if instruction is not primitive
        for (String primitive : primitives) {
            Reporter.assertElseFatalError(!primitive.equals(name),
                    "Error: Name of this instruction cannot equal the name of"
                            + "a primitive instruction");
        }

        //check "IS" token
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Error: Expected IS");

        //parse body of instruction
        body.parseBlock(tokens);

        //check "END" token
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Error: Expected END");

        //check ending matches beginning
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(name.equals(end),
                "Error: Ending name of instruction does not equal its beginning name");

        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
        + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //check "PROGRAM" token
        String pToken = tokens.dequeue();
        Reporter.assertElseFatalError(pToken.equals("PROGRAM"),
                "Error: Expected PROGRAM");

        //get name
        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "Error: Invalid Program Name");

        //check "IS" token
        String check = tokens.dequeue();
        Reporter.assertElseFatalError(check.equals("IS"), "Error: Expected IS");

        Map<String, Statement> context = this.newContext();

        while (tokens.front().equals("INSTRUCTION")) {
            Statement instructionBody = this.newBody();
            String instructionName = parseInstruction(tokens, instructionBody);

            //check if instruction is defined
            Reporter.assertElseFatalError(!context.hasKey(instructionName),
                    "Error: Instruction \"" + instructionName
                    + "\" has already been defined");

            //add instruction to context
            context.add(instructionName, instructionBody);
        }

        //check "BEGIN" token
        check = tokens.dequeue();
        Reporter.assertElseFatalError(check.equals("BEGIN"), "Error: Expected BEGIN");

        Statement programBody = this.newBody();
        programBody.parseBlock(tokens);

        //check "END" token
        String endToken = tokens.dequeue();
        Reporter.assertElseFatalError(endToken.equals("END"), "Error: Expected END");

        //check ending matches beginning
        String endProgramName = tokens.dequeue();
        Reporter.assertElseFatalError(endProgramName.equals(programName),
                "Error: Ending name of program does not match the beginning name");

        //check for end
        String endOfInput = tokens.dequeue();
        Reporter.assertElseFatalError(endOfInput.equals(Tokenizer.END_OF_INPUT),
                "Error: Expected END-OF-INPUT");

        this.setName(programName);
        this.swapBody(programBody);
        this.swapContext(context);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
