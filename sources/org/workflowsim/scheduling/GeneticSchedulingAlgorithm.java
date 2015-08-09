/*
 * 
 */
package org.workflowsim.scheduling;

import org.custom.Algorithm;
import org.custom.FitnessCalc;
import org.custom.models.Population;

/**
 * The Class GA.
 */
public class GeneticSchedulingAlgorithm {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		FitnessCalc.setSolution("Pseudo code for the Proposed HHL algorithms, This algorithm should call the other following algorithms".getBytes());

		Population myPop = new Population(50, true);

		int generationCount = 0;
		while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
			generationCount++;
			System.out.println("Generation: " + generationCount + " Fittest: "
					+ myPop.getFittest().getFitness());
			myPop = Algorithm.evolvePopulation(myPop);
		}
		System.out.println("Solution Display!");
		System.out.println("Generation Value: " + generationCount);
		System.out.println(myPop.getFittest());

	}
}
