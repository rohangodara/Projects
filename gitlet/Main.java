package gitlet;
import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Prithvi Singh & Rohan Godara
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        Repository bigDaddy = new Repository();
        String firstArg;
        try {firstArg=args[0];}
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a command.");
            return;
        }
        File f = new File(".gitlet/");
        if (!firstArg.equals("init") && !f.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        switch(firstArg) {
            case "init":
                bigDaddy.init();
                break;
            case "add":
                bigDaddy.add(args[1]);
                break;
            case "commit":
                bigDaddy.commit(args[1]);
                break;
            case "rm":
                bigDaddy.rm(args[1]);
                break;
            case "log":
                bigDaddy.log();
                break;
            case "global-log":
                bigDaddy.globallog();
                break;
            case "find":
                bigDaddy.find(args[1]);
                break;
            case "status":
                bigDaddy.status();
                break;
            case "checkout":
                CheckOutChooser(args, bigDaddy);
                break;
            case "branch":
                bigDaddy.branch(args[1]);
                break;
            case "rm-branch":
                bigDaddy.rmBranch(args[1]);
                break;
            case "reset":
                bigDaddy.reset(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }

    private static void CheckOutChooser(String[] args, Repository
                                        bigDaddy) {
        if (args.length == 3) {
            bigDaddy.checkout1(args[2]);
        } else if (args.length == 4 && args[2].equals("--")) {
            bigDaddy.checkout2(args[1], args[3]);
        } else if (args.length == 2){
            bigDaddy.checkout3(args[1]);
        } else {
            System.out.println("Incorrect operands.");
        }
    }
}
