package com.udacity.catpoint.application;

import static javax.swing.SwingUtilities.invokeLaterâ€‹;

/**
 * This is the main class that launches the application.
 */
public class CatpointApp
{
    public static void main(String[] args) {
		invokeLaterâ€‹( () -> CatpointGui.create() );
    }
}
