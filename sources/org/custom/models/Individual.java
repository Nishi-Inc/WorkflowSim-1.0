package org.custom.models;

import org.custom.FitnessCalc;

/**
 * The Class Individual.
 */
public class Individual {

    /** The default gene length. */
    static int defaultGeneLength = 64;
    
    /** The genes. */
    private byte[] genes = new byte[defaultGeneLength];
    // Cache
    /** The fitness. */
    private int fitness = 0;

    // Create a random individual
    /**
     * Generate individual.
     */
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    /**
     * Sets the default gene length.
     *
     * @param length the new default gene length
     */
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
    
    /**
     * Gets the gene.
     *
     * @param index the index
     * @return the gene
     */
    public byte getGene(int index) {
        return genes[index];
    }

    /**
     * Sets the gene.
     *
     * @param index the index
     * @param value the value
     */
    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    /**
     * Size.
     *
     * @return the int
     */
    public int size() {
        return genes.length;
    }

    /**
     * Gets the fitness.
     *
     * @return the fitness
     */
    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
