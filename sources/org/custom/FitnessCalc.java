package org.custom;

import org.custom.models.Individual;

/**
 * The Class FitnessCalc.
 */
public class FitnessCalc {

	/** The solution. */
	static byte[] solution = new byte[64];

	/**
	 * Sets the solution.
	 *
	 * @param newSolution the new solution
	 */
	public static void setSolution(byte[] newSolution) {
		solution = newSolution;
	}

	/**
	 * Sets the solution.
	 *
	 * @param newSolution the new solution
	 */
	static void setSolution(String newSolution) {
		solution = new byte[newSolution.length()];

		for (int i = 0; i < newSolution.length(); i++) {
			String character = newSolution.substring(i, i + 1);
			if (character.contains("0") || character.contains("1")) {
				solution[i] = Byte.parseByte(character);
			} else {
				solution[i] = 0;
			}
		}
	}

	/**
	 * Gets the fitness.
	 *
	 * @param individual the individual
	 * @return the fitness
	 */
	public static int getFitness(Individual individual) {
		int fitness = 0;

		for (int i = 0; i < individual.size() && i < solution.length; i++) {
			if (individual.getGene(i) == solution[i]) {
				fitness++;
			}
		}
		return fitness;
	}

	/**
	 * Gets the max fitness.
	 *
	 * @return the max fitness
	 */
	public static int getMaxFitness() {
		int maxFitness = solution.length;
		return maxFitness;
	}
}
