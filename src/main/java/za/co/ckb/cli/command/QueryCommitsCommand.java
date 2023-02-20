package za.co.ckb.cli.command;

import picocli.CommandLine;

@CommandLine.Command(
        name = "query-commit"
)
public class QueryCommitsCommand implements Runnable {

    @CommandLine.Option(names = {"-p", "--project"}, required = true, defaultValue = "DEFAULT_PROJECT", description = "The default project name")
    private String project;

    @CommandLine.Option(names = {"-r", "--repo"}, required = true, defaultValue = "DEFAULT_REPO")
    private String repo;

    @CommandLine.Option(names = {"-b", "--branch"}, required = true, defaultValue = "develop")
    private String branch;

    @Override
    public void run() {
        System.out.println("Query commits");
        
    }
}
