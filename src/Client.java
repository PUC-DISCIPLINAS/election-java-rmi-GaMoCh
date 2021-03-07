import constants.*;
import lib.Election;
import utils.EncryptionUtils;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

public class Client {
    private static String voter;

    private static void showHelp() {
        final StringBuilder stringBuilder = new StringBuilder();

        final Function<String, String> argumentsHandler = arguments -> !arguments.isEmpty()
            ? String.format(" %s", arguments)
            : arguments;

        Arrays.asList(Helper.values()).forEach(helper -> stringBuilder.append(
            String.format(Dialog.HELP, helper.getMethod(), argumentsHandler.apply(
                helper.getArguments()), helper.getDescription())));

        System.out.print(stringBuilder.toString());
    }

    private static void runServerCommand(String command, String argument) {
        for (int i = 1; i <= Config.ATTEMPTS; i++) {
            try {
                final Registry registry = LocateRegistry.getRegistry();
                final Election election = (Election) registry.lookup(Config.SERVER_NAME);
                final String response;

                switch (command) {
                    case Command.VOTE:
                        response = election.vote(voter, argument);
                        if (response.equals(String.valueOf(Fail.VOTE_ALREADY_COUNTED))) {
                            System.out.print(Dialog.VOTE_ALREADY_COUNTED);
                        } else if (response.equals(String.valueOf(Fail.CANDIDATE_NOT_EXISTS))) {
                            System.out.print(Dialog.CANDIDATE_NOT_EXISTS);
                        } else {
                            System.out.print(response);
                        }
                        break;
                    case Command.RESULT:
                        response = election.result(argument);
                        if (response.equals(String.valueOf(Fail.CANDIDATE_NOT_EXISTS))) {
                            System.out.print(Dialog.CANDIDATE_NOT_EXISTS);
                        } else {
                            System.out.print(response);
                        }
                        break;
                }
                return;
            } catch (RemoteException | NotBoundException exception) {
                System.out.printf(Dialog.CONNECTION_ATTEMPT_ERROR, i, Config.ATTEMPTS);
                try {
                    Thread.sleep(Config.TIMEOUT / Config.ATTEMPTS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.print(Dialog.CONNECTION_TIMEOUT_ERROR);
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        try {
            System.out.print(Dialog.USERNAME_INPUT);
            final String voterName = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

            try {
                voter = EncryptionUtils.generateHash(Config.ALGORITHM, voterName);
            } catch (NoSuchAlgorithmException e) {
                System.err.printf(Dialog.NO_SUCH_ALGORITHM_ERROR, Config.ALGORITHM);
                System.exit(1);
            }

            while (!exit) {
                System.out.print(Dialog.CLIENT_PS1);
                final String message = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
                final String[] messageData = message.split("\\s", 2);

                final String command = messageData[0];
                final String argument = messageData.length > 1 ? messageData[1].trim() : null;

                switch (command) {
                    case Command.VOTE:
                    case Command.RESULT:
                        if (argument != null) {
                            runServerCommand(command, argument);
                        } else {
                            System.out.print(Dialog.CANDIDATE_NOT_PROVIDED);
                        }
                        break;
                    case Command.HELP:
                        showHelp();
                        break;
                    case Command.EXIT:
                        exit = true;
                        break;
                    default:
                        System.out.print(Dialog.INVALID_COMMAND);
                        break;
                }
            }
        } catch (NoSuchElementException exception) {
            System.exit(1);
        }
    }
}
