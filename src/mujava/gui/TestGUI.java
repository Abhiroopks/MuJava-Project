package mujava.gui;

import java.util.concurrent.Executors;

import mujava.MutationSystem;

public class TestGUI {

	public static void main(String[] args) throws Exception {
		
		// let JVM decide parallel level for the GUI
		// Default behavior of GUI is to use multithreading
		MutationSystem.executorService = Executors.newWorkStealingPool();
		GenMutantsMain.main(null);
		RunTestMain.main(null);
		
		
	}

}
