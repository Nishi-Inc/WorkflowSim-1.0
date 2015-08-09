package org.workflowsim.scheduling;

import org.custom.models.Children;
import org.custom.models.Parents;
import org.custom.models.Population;
import org.custom.models.Sbest;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class WorkFlowSimHHLAlgorithm.
 */
public class HLHSchedulingAlgorithm {
	
	/** The Constant Child1. */
	private static final Children[] Child1 = null;
	
	/** The Constant Parent1. */
	private static final Parents Parent1 = null;
	
	/** The Constant Parent2. */
	private static final Parents Parent2 = null;
	
	/** The Populationsize. */
	static int Populationsize = 2;
	
	/** The Problemsize. */
	int Problemsize = 2;

	/** The Pcrossover. */
	static String Pcrossover = null;
	
	/** The Pmutation. */
	static String Pmutation = null;

	/** The population. */
	static Population population = new Population(Populationsize, false);

	/** The children. */
	Children[] children = null;
	
	/** The parents. */
	static Parents[] parents = null;

	/** The sbest. */
	Sbest sbest = null;

	/**
	 * Initialize population.
	 *
	 * @param Populationsize the populationsize
	 * @param Problemsize the problemsize
	 * @return the population
	 */
	public Population InitializePopulation(int Populationsize, int Problemsize) {

		population.setPopulationsize(Populationsize);
		population.setProblemsize(Problemsize);
		return population;

	}

	/**
	 * Evaluate population.
	 *
	 * @param population the population
	 */
	public void EvaluatePopulation(Population population) {

		System.out.println(population.getPopulationsize());
		System.out.println(population.getProblemsize());

	}

	/**
	 * Evaluate population.
	 *
	 * @param children the children
	 */
	public void EvaluatePopulation(Children children) {
		children = new Children();

	}

	/**
	 * Gets the best solution.
	 *
	 * @param population the population
	 * @return the sbest
	 */
	public Sbest GetBestSolution(Population population) {

		sbest.setPsoluation(population);
		return sbest;

	}

	/**
	 * Stop condition.
	 *
	 * @return true, if successful
	 */
	public static boolean StopCondition() {
		return true;
	}

	/**
	 * Select parents.
	 *
	 * @param population the population
	 * @param Populationsize the populationsize
	 * @return the parents[]
	 */
	public Parents[] SelectParents(Population population, int Populationsize) {

		return parents;
	}

	/**
	 * crossover.
	 *
	 * @param parent1 the parent1
	 * @param parent2 the parent2
	 * @param pcrossover the pcrossover
	 * @return the children[]
	 */
	public Children[] crossover(Parents parent1, Parents parent2,
								String pcrossover) {

		children[0] = (Children) parent1;
		children[1] = (Children) parent2;

		parent1.add(children[0]);
		parent1.add(children[1]);
		return children;

	}

	/**
	 * mutate.
	 *
	 * @param Child1 the child1
	 * @param Pmutation the pmutation
	 * @return the children[]
	 */
	public Children[] mutate(Children[] Child1, String Pmutation) {
		Children ch = new Children();
		Child1[0]=ch;
		ch.add(Child1[0]);
		children[0]=ch;
		return children;

	}

	/**
	 * Gets the best solution.
	 *
	 * @param ch the ch
	 * @return the sbest
	 */
	public Sbest GetBestSolution(Children ch) {

		sbest.setCsoluation(ch);

		return sbest;

	}

	/**
	 * Replace.
	 *
	 * @param population the population
	 * @param children the children
	 * @return the population
	 */
	public Population Replace(Population population, Children children) {

		population.getPopulationsize();
		children.add(children);
		return population;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		HLHSchedulingAlgorithm hlhAlgorithm = new HLHSchedulingAlgorithm();
		List<Parents[]> parrentList = new ArrayList();

		while (StopCondition()) {
			hlhAlgorithm.SelectParents(population, Populationsize);
			parrentList.add(parents);

			for (Parents[] parrentListResult : parrentList) {

				System.out.print(" parrentListResult Sbest Result "
						+ parrentListResult);

				hlhAlgorithm.mutate(Child1, Pmutation);
				hlhAlgorithm.crossover(Parent1, Parent2, Pcrossover);
			}
		}

	}

}
