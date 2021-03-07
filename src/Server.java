import constants.Command;
import constants.Config;
import constants.Dialog;
import lib.ElectionServant;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Server {
    private Server() {
    }

    public static void main(String[] args) {
        final Thread prompt = new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);

            try {
                while (true) {
                    System.out.print(Dialog.SERVER_PS1);
                    final String command = scanner.nextLine().trim().toLowerCase(Locale.ROOT);

                    if (command.equals(Command.EXIT)) {
                        System.exit(0);
                    } else {
                        System.out.print(Dialog.INVALID_COMMAND);
                    }
                }
            } catch (NoSuchElementException exception) {
                System.exit(1);
            }
        });

        try {
            final ElectionServant election = new lib.ElectionServant();
            final Registry registry = LocateRegistry.getRegistry();

            LocateRegistry.createRegistry(Config.SERVER_PORT);
            registry.rebind(Config.SERVER_NAME, election);

            System.out.print(Dialog.SERVER_RUNNING);
            prompt.start();
        } catch (ExportException exception) {
            System.err.printf(Dialog.SERVER_PORT_ERROR, Config.SERVER_PORT);
            System.exit(1);
        } catch (RemoteException exception) {
            exception.printStackTrace();
        }
    }
}
