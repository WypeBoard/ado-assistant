package io.github.wypeboard.adoassistant.view.metric;

import javafx.scene.Parent;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class AllActivePullRequestsMetric implements DashboardMetrics {

    private final ListView<String> listView;
    private final List<String> data;

    public AllActivePullRequestsMetric() {
        this.listView = new ListView<>();
        this.data = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return "Active pull requests";
    }

    @Override
    public Parent getWidget() {
        return this.listView;
    }

    @Override
    public void consumeData(List<?> data) {

    }

    @Override
    public void updateWidget() {
        this.listView.getItems().clear();
        this.listView.getItems().addAll(data);
    }

    @Override
    public DataNeeds getDataNeeds() {
        return DataNeeds.PULL_REQUESTS;
    }
}
