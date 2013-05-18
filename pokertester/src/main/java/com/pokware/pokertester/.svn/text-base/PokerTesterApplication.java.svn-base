package com.pokware.pokertester;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import com.pokware.client.netty.PokerClientManager;

/**
 * The main class of the application.
 */
public class PokerTesterApplication extends SingleFrameApplication {
	
	public static PokerClientManager pokerClientManager = new PokerClientManager();

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new PokerTesterApplicationView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of DesktopApplication1
     */
    public static PokerTesterApplication getApplication() {
        return Application.getInstance(PokerTesterApplication.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(PokerTesterApplication.class, args);
    }
}
