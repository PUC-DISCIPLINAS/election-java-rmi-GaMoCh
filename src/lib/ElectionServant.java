package lib;

import constants.Config;
import constants.Dialog;
import constants.Fail;
import models.Candidate;
import models.Storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ElectionServant extends UnicastRemoteObject implements Election {
    private Storage storage;

    public ElectionServant() throws RemoteException {
        super();

        try (FileInputStream fileInputStream = new FileInputStream(new File(Config.STORAGE_PATH).getAbsolutePath());
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            this.storage = (Storage) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            this.storage = new Storage(readCandidatesFile());
            saveStorage();
        }
    }

    private static Map<String, Candidate> readCandidatesFile() {
        final Map<String, Candidate> candidates = new HashMap<>();
        final Path filePath = Paths.get(new File(Config.FILE_PATH).getAbsolutePath());

        try {
            Files.lines(filePath).skip(Config.FILE_HAS_HEADER ? 1 : 0).forEach(line -> {
                if (!line.isEmpty()) {
                    final String[] candidateData = line.split(Config.FILE_SEPARATOR);
                    final Candidate candidate = new Candidate(candidateData[0], candidateData[1], candidateData[2]);
                    candidates.put(candidateData[0], candidate);
                }
            });
        } catch (IOException exception) {
            System.err.print(Dialog.FILE_ERROR);
            System.exit(1);
        }

        return candidates;
    }

    public void saveStorage() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(Config.STORAGE_PATH).getAbsolutePath());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(storage);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String vote(String voter, String candidateId) {
        final Candidate candidate = storage.getCandidates().get(candidateId);

        if (storage.getVotersWhoVoted().contains(voter)) {
            return String.valueOf(Fail.VOTE_ALREADY_COUNTED);
        }

        if (candidate == null) {
            return String.valueOf(Fail.CANDIDATE_NOT_EXISTS);
        }

        storage.getVotersWhoVoted().add(voter);
        candidate.addVote(voter);
        saveStorage();

        return String.format(Dialog.VOTE, candidate.getParty(), candidate.getName());
    }

    public String result(String candidateId) {
        final Candidate candidate = storage.getCandidates().get(candidateId);

        if (candidate == null) {
            return String.valueOf(Fail.CANDIDATE_NOT_EXISTS);
        }

        return String.format(Dialog.RESULT, candidate.getVotesQuantity(), candidate.getParty(), candidate.getName());
    }
}
