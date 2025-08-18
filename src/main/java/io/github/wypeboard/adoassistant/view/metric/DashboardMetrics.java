package io.github.wypeboard.adoassistant.view.metric;

import javafx.scene.Parent;

import java.util.List;

public interface DashboardMetrics {

    /**
     * Title refers to the Metrics overall UI label
     *
     * @return Card label
     */
    String getTitle();

    /**
     * Widget defines what should be presented in the UI; A list view, graphs, static text etc.
     *
     * @return The main UI element of the metric
     */
    Parent getWidget();

    /**
     * Allows the data inside the class to be updated.
     * Method is stricly for updating data.
     * <p>
     * For updating the UI the {@linkplain DashboardMetrics#updateWidget()} should be called instead
     *
     * @param data Any object data.
     */
    void consumeData(List<?> data);

    /**
     * Method that allows for the metric card to be updated
     * Only once this method is called with the card be populated with data
     */
    void updateWidget();

    /**
     * Method that indicates where the metric is being fetched from. As it could be general pullrequest data, threads etc.
     * @return
     */
    DataNeeds getDataNeeds();
}
