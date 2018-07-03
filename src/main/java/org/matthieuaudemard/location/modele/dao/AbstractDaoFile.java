package org.matthieuaudemard.location.modele.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.matthieuaudemard.location.modele.dao.interfaces.IDaoFile;
import org.matthieuaudemard.location.modele.entitee.AbstractEntitee;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public abstract class AbstractDaoFile<T extends AbstractEntitee<N>, N extends Comparable<N>> implements IDaoFile<T, N> {
	
	private static final Logger logger = LogManager.getLogger(Class.class);

	protected List<T> elements = null;
	File file = null;
	
	protected AbstractDaoFile(String filename) {
		elements = new ArrayList<>();
		file = new File(filename);
		load();
	}

	@Override
	public T get(N id) {
		if(elements == null || elements.isEmpty()) return null;
		return elements.stream()
				.filter(element -> element.getPrimaryKey().compareTo(id) == 0)
				.findFirst()
				.orElse(null);
	}	
	
	@Override
	public List<T> getAll() { return elements; }
	
	@Override
	public String jsonEncode() {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    return gson.toJson(elements);
	}
	
	@Override
	public final void save() {
		try (Scanner scanFile = new Scanner(file);
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream)) {
			out.write(jsonEncode());
		} catch (IOException e1) {
			logger.error("Exception levée lors de la tentative d'écriture dans " + file.getAbsolutePath());
		}
	}
}
