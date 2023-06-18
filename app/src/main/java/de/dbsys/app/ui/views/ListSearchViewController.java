package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * UIController containing a search bar that is used to filter a list of elements (for use in a ListView).
 * Nomenclature: filtering refers to only including elements of a given structure (e.g. course name), whereas searching refers to including all elements that contain a given string.
 * @param <T> Type of the elements in the list
 */
public class ListSearchViewController<T> extends GenericUIController {
    /**
     * Called whenever the elements should be filtered. The predicate passed to the consumer should be used to filter the elements.
     */
    private Consumer<Predicate<T>> filterInvoker;
    @FXML private TextField tfSearchString;
    /**
     * Source list of items to be filtered (and sorted).
     */
    @FXML private List<T> sourceItems;
    /**
     * The list of filtered (and sorted) items for use in a ListView.
     */
    @FXML private ObservableList<T> filteredItems;
    /**
     * Comparator specifying the sort order of the elements.
     */
    private Comparator<T> comparator;
    /**
     * Predicate for applying the filter option
     */
    private Predicate<T> filter;

    public void setFilter(Predicate<T> filter) {
        this.filter = filter;
    }

    public void setSourceItems(List<T> sourceItems) {
        this.sourceItems = sourceItems;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Return the list of filtered (and sorted) items for use in a ListView.
     * @return list of filtered (and sorted) items
     */
    public ObservableList<T> getFilteredItems() {
        return filteredItems;
    }

    @Override
    protected void setRootVisible(boolean visible) {}

    /**
     * Register a callback that is called whenever the elements should be filtered.
     * @param filterInvoker callback that is called whenever the elements should be filtered. The predicate passed to the consumer should be used to filter the elements.
     */
    public void registerApplyCallback(Consumer<Predicate<T>> filterInvoker) {
        this.filterInvoker = filterInvoker;
    }

    /**
     * Called when the filter/sorting mechanism needs to be applied.
     */
    @FXML private void onApply() {
        if(filterInvoker != null) {
            filterInvoker.accept(this::filterExpression);
        }
    }

    /**
     * Filter expression used to filter the elements, similar to a lambda, but reusable.
     * @param t element to be filtered
     * @return true if the element should be included in the filtered list, false otherwise
     */
    private boolean filterExpression(T t) {
        if (filter != null && !filter.test(t))
            return false;
        if (tfSearchString.getText().isEmpty())
            return true;
        return t.toString().toLowerCase().contains(tfSearchString.getText().toLowerCase());
    }

    /**
     * Filter the elements using the given filter and sort by the comparator.
     * @param filter filter predicate to be used
     */
    private void filterElements(Predicate<T> filter) {
        if (sourceItems == null)
            return;
        Stream<T> stream = sourceItems.stream()
                .filter(filter);
        if (comparator != null)
            stream = stream.sorted(comparator);
        List<T> elements = stream.toList();
        if (filteredItems == null)
            filteredItems = FXCollections.observableList(new LinkedList<>());
        filteredItems.clear();
        filteredItems.addAll(elements);
    }

    /**
     * Filter and sort the elements in filteredItems.
     */
    public void filterItems() {
        filterElements(this::filterExpression);
    }

    /**
     * Initialize the controller with the given source elements and comparator.
     * @param sourceElements source elements to be filtered/sorted
     * @param comparator comparator to be used for sorting
     */
    public void initialize(List<T> sourceElements, Comparator<T> comparator) {
        setComparator(comparator);
        setSourceItems(sourceElements);
        this.filteredItems = FXCollections.observableList(new LinkedList<>());
        registerApplyCallback(this::filterElements);
        filterElements(this::filterExpression);
    }
}
