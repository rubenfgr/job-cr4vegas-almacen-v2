package es.rfgr.cr4vegas.vista.utileria;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Filtrador {

	public static <T> void filtrarTablasGenericas(ObservableList<T> listaGenerica, TableView<T> tvGenerica,
			TextField tfGenerico) {
		FilteredList<T> genericosFiltrados = new FilteredList<>(listaGenerica, p -> true);

		tfGenerico.textProperty().addListener((ov, oldValue, newValue) -> {
			genericosFiltrados.setPredicate(generico -> {
				boolean encontrado = false;
				if (newValue == null || newValue.isEmpty())
					return true;

				try {
					if (generico.toString().toLowerCase().contains(newValue.toLowerCase())) {
						encontrado = true;
					}
				} catch (NullPointerException e) {
					// O_x
				}
				return encontrado;
			});
		});

		SortedList<T> genericosOrdenados = new SortedList<>(genericosFiltrados);
		genericosOrdenados.comparatorProperty().bind(tvGenerica.comparatorProperty());

		tvGenerica.setItems(genericosOrdenados);
	}

	public static <T> void filtrarListasGenericas(ObservableList<T> listaGenerica, ListView<T> lvGeneria,
			TextField tfGenerico) {
		FilteredList<T> genericosFiltrados = new FilteredList<>(listaGenerica, p -> true);

		tfGenerico.textProperty().addListener((ov, oldValue, newValue) -> {
			genericosFiltrados.setPredicate(generico -> {
				boolean encontrado = false;
				if (newValue == null || newValue.isEmpty())
					return true;

				try {
					if (generico.toString().toLowerCase().contains(newValue.toLowerCase())) {
						encontrado = true;
					}
				} catch (NullPointerException e) {
					// O_x
				}
				return encontrado;
			});
		});

		SortedList<T> genericosOrdenados = new SortedList<>(genericosFiltrados);

		lvGeneria.setItems(genericosOrdenados);
	}
}
