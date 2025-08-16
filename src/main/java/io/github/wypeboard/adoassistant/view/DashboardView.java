package io.github.wypeboard.adoassistant.view;

import io.github.wypeboard.adoassistant.ado.AdoController;
import io.github.wypeboard.adoassistant.ado.model.AdoGitPullRequest;
import io.github.wypeboard.adoassistant.view.metric.AllActivePullRequestsMetric;
import io.github.wypeboard.adoassistant.view.metric.AllMandatoryVotesApprovedMetric;
import io.github.wypeboard.adoassistant.view.metric.DataNeeds;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import io.github.wypeboard.adoassistant.view.metric.DashboardMetrics;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DashboardView {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardView.class);
    private GridPane root;
    private ProgressBar progressBar;
    private Button refreshButton;
    private AdoController adoController;
    private List<DashboardMetrics> metrics;

    public DashboardView() {
        this.metrics = new ArrayList<>();
        this.adoController = new AdoController();
        setupUi();
        setupMetrics();
    }

    private void setupUi() {
        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(20);
        root.setVgap(20);

        // Create bootstrap like columns
        for (int i = 0; i < 12; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 12);
            column.setMinWidth(80);
            root.getColumnConstraints().add(column);
        }

        // Top progressbar + refresh
        buildRefreshSection();
    }

    private void buildRefreshSection() {
        progressBar = new ProgressBar();
        progressBar.setMaxWidth(Integer.MAX_VALUE);
        progressBar.setProgress(0);
        GridPane.setColumnSpan(progressBar, 10);
        root.add(progressBar, 0, 0);

        refreshButton = new Button("Refresh data");
        refreshButton.setMaxWidth(Integer.MAX_VALUE);
        refreshButton.setOnAction(e -> refreshData());
        GridPane.setColumnSpan(refreshButton, 2);
        root.add(refreshButton, 10, 0);
    }

    private void refreshData() {
        LOGGER.debug("");
        LOGGER.info("Fetching data");

        this.refreshButton.setDisable(true);
        this.refreshButton.setText("Fetching...");
        this.progressBar.setProgress(0);

        // Start data fetching chain
        fetchPullRequestData();
    }

    private void fetchPullRequestData() {
        Task<List<AdoGitPullRequest>> fetchTask = new Task<List<AdoGitPullRequest>>() {
            @Override
            protected List<AdoGitPullRequest> call() throws Exception {
                return adoController.fetchPullRequests();
            }

            @Override
            protected void succeeded() {
                List<AdoGitPullRequest> pullRequests = getValue();

                updateMetrics(pullRequests, DataNeeds.PULL_REQUESTS);
                progressBar.setProgress(0.33);


            }

            @Override
            protected void failed() {
                refreshButton.setDisable(false);
                refreshButton.setText("Error happened");
                progressBar.setProgress(1.0);
            }
        };
    }

    private void runTask(Task<?> runableTask) {
        Thread backgroundThread = new Thread(runableTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    private void setupMetrics() {
        addMetric(new AllActivePullRequestsMetric(), 1, 0, 3, 6); // Row 1, Col 0 => Height 3, width 6
        addMetric(new AllMandatoryVotesApprovedMetric(), 1, 6, 3, 6); // Row 1, Col 6 => Height 3, width 6
    }

    private void addMetric(DashboardMetrics metric, int row, int col, int rowSpan, int colSpan) {
        metrics.add(metric);

        VBox container = new VBox();
        container.setSpacing(5);

        Label titleLabel = new Label(metric.getTitle());

        Parent metricWidget = metric.getWidget();

        container.getChildren().addAll(titleLabel, metricWidget);

        GridPane.setRowSpan(container, rowSpan);
        GridPane.setColumnSpan(container, colSpan);
        root.add(container, col, row);
    }

    private void updateMetrics(List<AdoGitPullRequest> pullRequests, DataNeeds dataNeeds) {
        for (DashboardMetrics metric : metrics) {
            if (metric.getDataNeeds() != dataNeeds) {
                continue;
            }
            metric.updateWidget();
        }
    }

    public Parent getRoot() {
        return root;
    }
}
