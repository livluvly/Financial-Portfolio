package use_case.statistics;

import entity.Asset;
import data_access.InMemoryStatsDataAccessObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsInteractorTest {

    @Test
    void successCalculationTest() {
        StatsInputData statsInputData = new StatsInputData("alice");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        // For the success test, need to add assets to repository for alice.
        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset("AAPL", 10, 150.0, 5.0));
        assets.add(new Asset("GOOGL", 5, 140.0, 7.0));
        statsRepository.savePortfolio("alice", assets);

        StatsOutputBoundary successPresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                assertNotNull(outputData);
                assertEquals(2, outputData.getAssets().size());
                assertEquals(2200.0, outputData.getTotalBalance());
                assertEquals(85.0, outputData.getTotalDailyGain(), 0.01);
                assertEquals(0.038, outputData.getTotalDailyPercentageGain(), 0.01);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        StatsInputBoundary interactor = new StatsInteractor(statsRepository, successPresenter);
        interactor.execute(statsInputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        StatsInputData statsInputData = new StatsInputData("NoUser");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        StatsOutputBoundary failurePresenter = new StatsOutputBoundary() {
            @Override
            public void prepareSuccessView(StatsOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("NoUser: Account does not exist", error);
            }
        };

        StatsInputBoundary interactor = new StatsInteractor(statsRepository, failurePresenter);
        interactor.execute(statsInputData);
    }

    @Test
    void successNoAssetsTest() {
        StatsInputData statsInputData = new StatsInputData("EmptyUser");
        StatsDataAccessInterface statsRepository = new InMemoryStatsDataAccessObject();

        // Save a list of empty assets for empty user.
        statsRepository.savePortfolio("EmptyUser", new ArrayList<>());

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
        interactor.execute(statsInputData);
    }
}

