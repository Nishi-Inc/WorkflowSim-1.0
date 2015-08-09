package org.custom.models;

/**
 * The Class Population.
 */
public class Population {

	/** The individuals. */
	Individual[] individuals;

	/**
	 * Instantiates a new population.
	 *
	 * @param populationSize the population size
	 * @param initialise the initialise
	 */
	public Population(int populationSize, boolean initialise) {
		individuals = new Individual[populationSize];

		if (initialise) {

			for (int i = 0; i < size(); i++) {
				Individual newIndividual = new Individual();
				newIndividual.generateIndividual();
				saveIndividual(i, newIndividual);
			}
		}
	}

	/**
	 * Gets the individual.
	 *
	 * @param index the index
	 * @return the individual
	 */
	public Individual getIndividual(int index) {
		return individuals[index];
	}

	/**
	 * Gets the fittest.
	 *
	 * @return the fittest
	 */
	public Individual getFittest() {
		Individual fittest = individuals[0];

		for (int i = 0; i < size(); i++) {
			if (fittest.getFitness() <= getIndividual(i).getFitness()) {
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return individuals.length;
	}

	/**
	 * Save individual.
	 *
	 * @param index the index
	 * @param indiv the indiv
	 */
	public void saveIndividual(int index, Individual indiv) {
		individuals[index] = indiv;
	}

	/**
	 * Gets the populationsize.
	 *
	 * @return the populationsize
	 */
	public int getPopulationsize() {
		return Populationsize;
	}

	/**
	 * Sets the populationsize.
	 *
	 * @param populationsize the new populationsize
	 */
	public void setPopulationsize(int populationsize) {
		Populationsize = populationsize;
	}

	/**
	 * Gets the problemsize.
	 *
	 * @return the problemsize
	 */
	public int getProblemsize() {
		return Problemsize;
	}

	/**
	 * Sets the problemsize.
	 *
	 * @param problemsize the new problemsize
	 */
	public void setProblemsize(int problemsize) {
		Problemsize = problemsize;
	}

	/** The Populationsize. */
	int Populationsize = 0;
	
	/** The Problemsize. */
	int Problemsize = 0;

}
