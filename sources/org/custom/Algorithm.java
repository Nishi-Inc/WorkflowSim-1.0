package org.custom;

import org.custom.models.Individual;
import org.custom.models.Population;

/**
 * The Class Algorithm.
 */
public class Algorithm {

	/** The Constant uniformRate. */
	private static final double uniformRate = 0.5;
	
	/** The Constant mutationRate. */
	private static final double mutationRate = 0.015;
	
	/** The Constant tournamentSize. */
	private static final int tournamentSize = 5;
	
	/** The Constant elitism. */
	private static final boolean elitism = true;

	/**
	 * Evolve population.
	 *
	 * @param pop the pop
	 * @return the population
	 */
	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.size(), false);

		if (elitism) {
			newPopulation.saveIndividual(0, pop.getFittest());
		}

		int elitismOffset;
		if (elitism) {
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}

		for (int i = elitismOffset; i < pop.size(); i++) {
			Individual indiv1 = tournamentSelection(pop);
			Individual indiv2 = tournamentSelection(pop);
			Individual newIndiv = crossover(indiv1, indiv2);
			newPopulation.saveIndividual(i, newIndiv);
		}

		for (int i = elitismOffset; i < newPopulation.size(); i++) {
			mutate(newPopulation.getIndividual(i));
		}

		return newPopulation;
	}

	/**
	 * crossover.
	 *
	 * @param indiv1 the indiv1
	 * @param indiv2 the indiv2
	 * @return the individual
	 */
	private static Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newSol = new Individual();

		for (int i = 0; i < indiv1.size(); i++) {

			if (Math.random() <= uniformRate) {
				newSol.setGene(i, indiv1.getGene(i));
			} else {
				newSol.setGene(i, indiv2.getGene(i));
			}
		}
		return newSol;
	}

	/**
	 * mutate.
	 *
	 * @param indiv the indiv
	 */
	private static void mutate(Individual indiv) {

		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {

				byte gene = (byte) Math.round(Math.random());
				indiv.setGene(i, gene);
			}
		}
	}

	/**
	 * Tournament selection.
	 *
	 * @param pop the pop
	 * @return the individual
	 */
	private static Individual tournamentSelection(Population pop) {
		Population tournament = new Population(tournamentSize, false);
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.size());
			tournament.saveIndividual(i, pop.getIndividual(randomId));
		}
		Individual fittest = tournament.getFittest();
		return fittest;
	}
}
