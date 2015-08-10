package org.workflowsim.scheduling;

import org.cloudbus.cloudsim.Cloudlet;
import org.workflowsim.CondorVM;
import org.workflowsim.WorkflowSimTags;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Class PSOSchedulingAlgorithm.
 */
public class PSOSchedulingAlgorithm extends BaseSchedulingAlgorithm {
	
	/** The Constant TARGET. */
	private static final int TARGET = 50;
	
	/** The Constant MAX_INPUTS. */
	private static final int MAX_INPUTS = 3;
	
	/** The Constant MAX_PARTICLES. */
	private static final int MAX_PARTICLES = 20;
	
	/** The Constant V_MAX. */
	private static final int V_MAX = 10;

	/** The Constant MAX_EPOCHS. */
	private static final int MAX_EPOCHS = 200;

	/** The Constant START_RANGE_MIN. */
	private static final int START_RANGE_MIN = 140;
	
	/** The Constant START_RANGE_MAX. */
	private static final int START_RANGE_MAX = 190;

	/** The particles. */
	private ArrayList<Particle> particles = new ArrayList<>();

	/**
	 * Initialize.
	 */
	private void initialize() {
		for (int i = 0; i < MAX_PARTICLES; i++) {
			Particle newParticle = new Particle();
			int total = 0;
			for (int j = 0; j < MAX_INPUTS; j++) {
				newParticle.data(j,
						getRandomNumber(START_RANGE_MIN, START_RANGE_MAX));
				total += newParticle.data(j);
			}
			newParticle.pBest(total);
			particles.add(newParticle);
		}
	}

	/**
	 * Instantiates a new PSO algorithm.
	 */
	public PSOSchedulingAlgorithm() {

	}

	@Override
	public void run() throws Exception {
		int gBest = 0;
		int gBestTest;
		Particle aParticle;
		int epoch = 0;
		boolean done = false;

		initialize();

		while (!done) {
			if (epoch < MAX_EPOCHS) {
				for (int i = 0; i < MAX_PARTICLES; i++) {
					aParticle = particles.get(i);
					for (int j = 0; j < MAX_INPUTS; j++) {
						if (j < MAX_INPUTS - 1) {
							System.out.print(aParticle.data(j) + " + ");
						} else {
							System.out.print(aParticle.data(j) + " = ");
						}
					}

					System.out.print(testProblem(i) + "\n");
					if (testProblem(i) == TARGET) {
						done = true;
					}
				}

				gBestTest = minimum();
				aParticle = particles.get(gBest);
				if (Math.abs(TARGET - testProblem(gBestTest)) < Math.abs(TARGET
						- testProblem(gBest))) {
					gBest = gBestTest;
				}

				getVelocity(gBest);

				updateparticles(gBest);

				System.out.println("epoch number: " + epoch);

				epoch += 1;

			} else {
				done = true;
			}
		}

		int vmSize = getVmList().size();
		CondorVM firstIdleVm = null;
		for (int j = 0; j < vmSize; j++) {
			CondorVM vm = (CondorVM) getVmList().get(j);
			if (vm.getState() == WorkflowSimTags.VM_STATUS_IDLE) {
				firstIdleVm = vm;
				break;
			}
		}
		if (firstIdleVm != null) {
			for (int j = 0; j < vmSize; j++) {
				CondorVM vm = (CondorVM) getVmList().get(j);
				if ((vm.getState() == WorkflowSimTags.VM_STATUS_IDLE)
						&& vm.getCurrentRequestedTotalMips() > firstIdleVm.getCurrentRequestedTotalMips()) {
					firstIdleVm = vm;
				}
			}
			firstIdleVm.setState(WorkflowSimTags.VM_STATUS_BUSY);
			minCloudlet.setVmId(firstIdleVm.getId());
			this.getScheduledList().add(minCloudlet);
		}











		int size = getCloudletList().size();
		hasChecked.clear();
		for (int t = 0; t < size; t++) {
			hasChecked.add(false);
		}
		for (int i = 0; i < size; i++) {
			int minIndex = 0;
			Cloudlet minCloudlet = null;
			for (int j = 0; j < size; j++) {
				Cloudlet cloudlet = (Cloudlet) getCloudletList().get(j);
				if (!hasChecked.get(j)) {
					minCloudlet = cloudlet;
					minIndex = j;
					break;
				}
			}
			if (minCloudlet == null) {
				break;
			}


			for (int j = 0; j < size; j++) {
				Cloudlet cloudlet = (Cloudlet) getCloudletList().get(j);
				if (hasChecked.get(j)) {
					continue;
				}
				long length = cloudlet.getCloudletLength();
				if (length < minCloudlet.getCloudletLength()) {
					minCloudlet = cloudlet;
					minIndex = j;
				}
			}
			hasChecked.set(minIndex, true);

			for (int j = 0; j < vmSize; j++) {
				CondorVM vm = (CondorVM) getVmList().get(j);
				if (vm.getState() == WorkflowSimTags.VM_STATUS_IDLE) {
					firstIdleVm = vm;
					break;
				}
			}
			if (firstIdleVm == null) {
				break;
			}
			for (int j = 0; j < vmSize; j++) {
				CondorVM vm = (CondorVM) getVmList().get(j);
				if ((vm.getState() == WorkflowSimTags.VM_STATUS_IDLE)
						&& vm.getCurrentRequestedTotalMips() > firstIdleVm.getCurrentRequestedTotalMips()) {
					firstIdleVm = vm;
				}
			}
			firstIdleVm.setState(WorkflowSimTags.VM_STATUS_BUSY);
			minCloudlet.setVmId(firstIdleVm.getId());
			this.getScheduledList().add(minCloudlet);
		}
	}

	/**
	 * Gets the velocity.
	 *
	 * @param gBestindex the g bestindex
	 * @return the velocity
	 */
	private static void getVelocity(int gBestindex) {

		int testResults = 0;
		int bestResults = 0;
		double vValue = 0.0;
		Particle aParticle = null;

		bestResults = testProblem(gBestindex);

		for (int i = 0; i < MAX_PARTICLES; i++) {
			testResults = testProblem(i);
			aParticle = particles.get(i);
			vValue = aParticle.velocity() + 2 * new Random().nextDouble()
					* (aParticle.pBest() - testResults) + 2
					* new Random().nextDouble() * (bestResults - testResults);

			if (vValue > V_MAX) {
				aParticle.velocity(V_MAX);
			} else if (vValue < -V_MAX) {
				aParticle.velocity(-V_MAX);
			} else {
				aParticle.velocity(vValue);
			}
		}
		
	}

	/**
	 * Updateparticles.
	 *
	 * @param gBestindex the g bestindex
	 */
	private static void updateparticles(int gBestindex) {
		Particle gBParticle = particles.get(gBestindex);

		for (int i = 0; i < MAX_PARTICLES; i++) {
			for (int j = 0; j < MAX_INPUTS; j++) {
				if (particles.get(i).data(j) != gBParticle.data(j)) {
					particles.get(i).data(
							j,
							particles.get(i).data(j)
									+ (int) Math.round(particles.get(i)
											.velocity()));
				}
			} // j

			int total = testProblem(i);
			if (Math.abs(TARGET - total) < particles.get(i).pBest()) {
				particles.get(i).pBest(total);
			}

		} // i
		
	}

	/**
	 * Test problem.
	 *
	 * @param index the index
	 * @return the int
	 */
	private static int testProblem(int index) {
		int total = 0;
		Particle aParticle = null;

		aParticle = particles.get(index);

		for (int i = 0; i < MAX_INPUTS; i++) {
			total += aParticle.data(i);
		}
		return total;
	}

	/**
	 * Prints the solution.
	 */
	private static void printSolution() {
		int i = 0;
		for (; i < particles.size(); i++) {
			if (testProblem(i) == TARGET) {
				break;
			}
		}
		// Print it.
		System.out.println("Particle " + i + " has achieved target.");
		for (int j = 0; j < MAX_INPUTS; j++) {
			if (j < MAX_INPUTS - 1) {
				System.out.print(particles.get(i).data(j) + " + ");
			} else {
				System.out.print(particles.get(i).data(j) + " = " + TARGET);
			}
		} // j
		System.out.print("\n");
		
	}

	/**
	 * Gets the random number.
	 *
	 * @param low the low
	 * @param high the high
	 * @return the random number
	 */
	private static int getRandomNumber(int low, int high) {
		return (int) ((high - low) * new Random().nextDouble() + low);
	}

	/**
	 * Minimum.
	 *
	 * @return the int
	 */
	private static int minimum() {
		// Returns an array index.
		int winner = 0;
		boolean foundNewWinner;
		boolean done = false;

		while (!done) {
			foundNewWinner = false;
			for (int i = 0; i < MAX_PARTICLES; i++) {
				if (i != winner) {

					if (Math.abs(TARGET - testProblem(i)) < Math.abs(TARGET
							- testProblem(winner))) {
						winner = i;
						foundNewWinner = true;
					}
				}
			}

			done = !foundNewWinner;
		}

		return winner;
	}

	/**
	 * The Class Particle.
	 */
	private static class Particle {
		
		/** The m data. */
		private int mData[] = new int[MAX_INPUTS];
		
		/** The mp best. */
		private int mpBest = 0;
		
		/** The m velocity. */
		private double mVelocity = 0.0;

		/**
		 * Instantiates a new particle.
		 */
		public Particle() {
			this.mpBest = 0;
			this.mVelocity = 0.0;
		}

		/**
		 * Data.
		 *
		 * @param index the index
		 * @return the int
		 */
		public int data(int index) {
			return this.mData[index];
		}

		/**
		 * Data.
		 *
		 * @param index the index
		 * @param value the value
		 */
		public void data(int index, int value) {
			this.mData[index] = value;
		}

		/**
		 * P best.
		 *
		 * @return the int
		 */
		public int pBest() {
			return this.mpBest;
		}

		/**
		 * P best.
		 *
		 * @param value the value
		 */
		public void pBest(int value) {
			this.mpBest = value;
		}

		/**
		 * Velocity.
		 *
		 * @return the double
		 */
		public double velocity() {
			return this.mVelocity;
		}

		/**
		 * Velocity.
		 *
		 * @param velocityScore the velocity score
		 */
		public void velocity(double velocityScore) {
			this.mVelocity = velocityScore;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		PSOSchedulingAlgorithm pos = new PSOSchedulingAlgorithm();
//		PSOSchedulingAlgorithm.printSolution();
	}

}
