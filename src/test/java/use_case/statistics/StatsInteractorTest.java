package use_case.statistics;

import entity.Asset;
import data_access.InMemoryStatsDataAccessObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsInteractorTest {

    @Test
    void successTest() {
        StatsInputData inputData = new StatsInputData("alice");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        // Populate the repository with assets for JohnDoe.
        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset("AAPL", 10, 1500.0, 1.1));
        assets.add(new Asset("GOOGL", 5, 1400.0, 0.8));
        statsRepository.saveAssetsForUser("alice", assets);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(2, outputData.getAssets().size());
                assertEquals(2900.0, outputData.getTotalBalance());
                assertEquals(1.9, outputData.getTotalDailyGain(), 0.01);
                assertEquals(0.01, outputData.getTotalDailyPercentageGain(), 0.01);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        StatsInputData inputData = new StatsInputData("UnknownUser");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("UnknownUser: Account does not exist", error);
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void successNoAssetsTest() {
        StatsInputData inputData = new StatsInputData("EmptyUser");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        // Save an empty asset list for the user.
        statsRepository.saveAssetsForUser("EmptyUser", new ArrayList<>());

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertNotNull(outputData);
                assertTrue(outputData.getAssets().isEmpty());
                assertEquals(0.0, outputData.getTotalBalance());
                assertEquals(0.0, outputData.getTotalDailyGain());
                assertEquals(0.0, outputData.getTotalDailyPercentageGain());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(inputData);
    }
}

