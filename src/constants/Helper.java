package constants;

public enum Helper {
    VOTE(Command.VOTE, "<candidateId>", "vote for the candidate"),
    RESULT(Command.RESULT, "<candidateId>", "see the candidate's number of votes"),
    EXIT(Command.EXIT, "", "exit the system");

    private final String method;
    private final String arguments;
    private final String description;

    Helper(String method, String arguments, String description) {
        this.method = method;
        this.arguments = arguments;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public String getArguments() {
        return arguments;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.method;
    }
}
