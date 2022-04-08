package by.c7d5a6.languageparser.rest.model;

import java.util.List;

public class LanguageSoundClusters {

    private List<String> constClustersStart;
    private List<String> constClustersEnd;
    private List<String> constClusters;

    private List<String> vowelClustersStart;
    private List<String> vowelClustersEnd;
    private List<String> vowelClusters;

    public List<String> getConstClustersStart() {
        return constClustersStart;
    }

    public void setConstClustersStart(List<String> constClustersStart) {
        this.constClustersStart = constClustersStart;
    }

    public List<String> getConstClustersEnd() {
        return constClustersEnd;
    }

    public void setConstClustersEnd(List<String> constClustersEnd) {
        this.constClustersEnd = constClustersEnd;
    }

    public List<String> getConstClusters() {
        return constClusters;
    }

    public void setConstClusters(List<String> constClusters) {
        this.constClusters = constClusters;
    }

    public List<String> getVowelClustersStart() {
        return vowelClustersStart;
    }

    public void setVowelClustersStart(List<String> vowelClustersStart) {
        this.vowelClustersStart = vowelClustersStart;
    }

    public List<String> getVowelClustersEnd() {
        return vowelClustersEnd;
    }

    public void setVowelClustersEnd(List<String> vowelClustersEnd) {
        this.vowelClustersEnd = vowelClustersEnd;
    }

    public List<String> getVowelClusters() {
        return vowelClusters;
    }

    public void setVowelClusters(List<String> vowelClusters) {
        this.vowelClusters = vowelClusters;
    }
}
