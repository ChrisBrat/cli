package za.co.ckb.cli;

import picocli.CommandLine;
import za.co.ckb.cli.command.QueryCommitsCommand;
import za.co.ckb.cli.command.ConfigCommand;

/**
 * https://www.baeldung.com/java-picocli-create-command-line-program
 */
@CommandLine.Command(name = "App",
        subcommands = {
                ConfigCommand.class,
                QueryCommitsCommand.class
        }
)
public class App implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    public static void main( String[] args ) {

        CommandLine commandLine = new CommandLine(new App());
        commandLine.parseArgs(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }


    @Override
    public void run() {


        System.out.println("Subcommand required. Display help with '-h' option");
    }
}
