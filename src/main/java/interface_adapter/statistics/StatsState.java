package interface_adapter.statistics;

import use_case.statistics.StatsOutputData;

public class StatsState {
    private final StatsOutputData statsOutputData;
    private String statsErrorMessage;

    public StatsState(StatsOutputData statsOutputData) {
        this.statsOutputData = statsOutputData;
        this.statsErrorMessage = null;
    }

    public StatsOutputData getStatsOutputData() {
        return statsOutputData;
    }

    public String getStatsErrorMessage() {
        return statsErrorMessage;
    }

    public void setStatsErrorMessage(String statsErrorMessage) {
        this.statsErrorMessage = statsErrorMessage;
    }
}
