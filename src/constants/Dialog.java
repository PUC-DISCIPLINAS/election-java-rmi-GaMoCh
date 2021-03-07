package constants;

public final class Dialog {
    public static final String SERVER_RUNNING = "Server running\n";
    public static final String SERVER_PORT_ERROR = "Port %d already in use\n";

    public static final String USERNAME_INPUT = "Insert your name: ";
    public static final String INVALID_COMMAND = "Invalid command\n";

    public static final String CONNECTION_ATTEMPT_ERROR = "Trying to connect to the server | %d/%d\n";
    public static final String CONNECTION_TIMEOUT_ERROR = "Server connection timeout expired\n";

    public static final String CANDIDATE_NOT_EXISTS = "Candidate not exists\n";
    public static final String CANDIDATE_NOT_PROVIDED = "Candidate not provided\n";

    public static final String NO_SUCH_ALGORITHM_ERROR = "%s not available in the environment\n";
    public static final String FILE_ERROR = "Cannot access the file\n";

    public static final String VOTE = "Vote: %s - %s\n";
    public static final String RESULT = "Votes: %s | %s - %s\n";

    public static final String VOTE_ALREADY_COUNTED = "Vote already counted\n";

    public static final String HELP = "Type '%s%s' to %s\n";

    public static final String CLIENT_PS1 = String.format(
        "\nType '%s' to help \u276F ", Command.HELP);
    public static final String SERVER_PS1 = String.format(
        "\nType '%s' to exit \u276F ", Command.EXIT);

    private Dialog() {
    }
}
