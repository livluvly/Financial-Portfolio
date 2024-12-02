package interface_adapter;

import entity.Asset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import interface_adapter.portfolio.PortfolioState;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioOutputData;

public class PortfolioViewModel extends ViewModel<PortfolioState> {
//    private final List<Asset> assets;

    public PortfolioViewModel() {
        super("Portfolio");
        this.setState(new PortfolioState(List.of()));
    }
    public void updatePortfolio(List<Asset> assets) {
        PortfolioState newState = new PortfolioState(assets);
        this.setState(newState);
        this.firePropertyChanged();
    }

    public List<Asset> getAssets() {
        return this.getState().getAssets();
    }

    public void sortAssets(String criterion) {
        List<Asset> modifiableAssets = new ArrayList<>(this.getAssets());

        switch (criterion) {
            case "Symbol":
                modifiableAssets.sort(Comparator.comparing(Asset::getSymbol));
                break;
            case "Quantity":
                modifiableAssets.sort(Comparator.comparingDouble(Asset::getQuantity));
                break;
            case "Total Value":
                modifiableAssets.sort(Comparator.comparingDouble(Asset::getTotalValue));
                break;
            default: // "Daily Gain (%)":
                modifiableAssets.sort(Comparator.comparingDouble(Asset::getDailyGain));
        }

        // Update the portfolio state with the sorted assets
        updatePortfolio(modifiableAssets);
    }

}