package models;

import java.io.Serializable;
import java.util.HashSet;

public class Candidate implements Serializable {
    private final String id;
    private final String name;
    private final String party;
    private final HashSet<String> votes;

    public Candidate(String id, String name, String party) {
        this.votes = new HashSet<>();

        this.id = id;
        this.name = name;
        this.party = party;
    }

    public void addVote(String vote) {
        votes.add(vote);
    }

    public int getVotesQuantity() {
        return votes.size();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }

    @Override
    public String toString() {
        return String.format("%d | (%s) %s - %s", getVotesQuantity(), getId(), getParty(), getName());
    }
}
