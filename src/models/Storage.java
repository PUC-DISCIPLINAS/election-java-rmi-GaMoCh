package models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Storage implements Serializable {
    private Map<String, Candidate> candidates;
    private Set<String> votersWhoVoted;

    public Storage(Map<String, Candidate> candidates) {
        setVotersWhoVoted(ConcurrentHashMap.newKeySet());
        setCandidates(candidates);
    }

    public Map<String, Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Map<String, Candidate> candidates) {
        this.candidates = candidates;
    }

    public Set<String> getVotersWhoVoted() {
        return votersWhoVoted;
    }

    public void setVotersWhoVoted(Set<String> votersWhoVoted) {
        this.votersWhoVoted = votersWhoVoted;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", Arrays.deepToString(getVotersWhoVoted().toArray()),
            Arrays.deepToString(getCandidates().values().toArray()));
    }
}
