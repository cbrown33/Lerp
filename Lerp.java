import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * A class that contains the static methods that provides an entry
 * point and textual user interface for the lerp compiler.
 *
 * 
 * @author Arthur Nunes-Harwitt
 */
public class Lerp {

    private static Machine m = new Machine();

    private static void compile(String codeText){
        m.clear();
        ANFVarExp.reset();
        Expression e = Parser.parse(codeText);
        ANFExp exp = e.toANF();
        exp.compile(m);
    }

    private static void repl(){
        Scanner sc = new Scanner(System.in);
        String s;
        while(true){
            System.out.print("Lerp] ");
            s = sc.nextLine();
            if (s.equals("")){
                return ;
            }
            else{
                compile(s);
                m.execute();
                }
        }
    }

    /**
     * Load the file associated with the specified file name into
     * memory as a string.
     *
     * @param filename a String representing the name of a file
     * @return the String that contains the text in the file
     */
    public static String load(String filename){
        String text = "";
        try (FileInputStream fileStr = new FileInputStream(filename)) {
            Scanner in = new Scanner(fileStr);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                text = text + " " + line;
            }
        } catch(IOException ioe) {
            Errors.error( "Could not open file " + filename, null );
        }
        return text;
    }

    /**
     * No command line arguments entail interactive mode.  A single
     * command line argument is treated as a file name and entails
     * non-interactive mode: the file is read in compiled and the
     * generated code is displayed.
     *
     * @param args the String array corresponding to the command line
     * arguments.
     */
    public static void main(String[] args){
        try {
            if (args.length == 0) {
                repl();
            } else if (args.length == 1) {
                compile(load(args[0]));
                m.displayInstructions();
            } else {
                System.out.println("Usage: java Lerp [filename]");
            }
        } catch (Exception e){
            Errors.error("Unexpected error ", e);
        }
    }

}
