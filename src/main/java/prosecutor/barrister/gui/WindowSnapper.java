package prosecutor.barrister.gui;
///////////////////////////////////////////////////////////////////////////////
//
//Author: Jiri Fryc
//
//Licence: AGPL v3
//
//This file is part of Barrister, which is part of Prosecutor. 
///////////////////////////////////////////////////////////////////////////////


import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowSnapper extends ComponentAdapter {

    private boolean locked = false;

    // feel free to modify; set based on my own preferences
    // incorporate as user option?
    private int sd = 30;
    private GraphicsDevice[] screenList  = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices();

    public void componentMoved(ComponentEvent evt) {
        Window myWindow = new Window((Frame) evt.getComponent());
        GraphicsConfiguration config = myWindow.getGraphicsConfiguration();
        GraphicsDevice myScreen = config.getDevice();

        for(GraphicsDevice gd : getScreenList()) {
            if(gd.equals(myScreen)) {
                myScreen = gd;
                break;
            }
        }

        int screenWidth = myScreen.getDefaultConfiguration().getBounds().width;
        int screenHeight = myScreen.getDefaultConfiguration().getBounds().height;
        int compWidth = evt.getComponent().getWidth();
        int compHeight = evt.getComponent().getHeight();
        int nx = evt.getComponent().getX();
        int ny = evt.getComponent().getY();

        int currentX = myScreen.getDefaultConfiguration().getBounds().x;
        int currentY = myScreen.getDefaultConfiguration().getBounds().y;


        if(locked
                || nx == currentX + 5
                || ny == currentY + 5
                || nx == currentX + screenWidth - compWidth - 5
                || ny == currentY + screenHeight - compHeight - 5)
            return;

        locked = true;

        // left
        if(nx < (currentX + sd) && nx > (currentX + 5)) {
            evt.getComponent().setSize(screenWidth/2,screenHeight);
            evt.getComponent().setLocation(0,0);
        }

        // top
        if(ny < (currentY + sd) && ny > (currentY + 5)) {
            ny = currentY + 5;
        }

        // right
        if(nx > currentX + screenWidth - compWidth - sd
                && nx < currentX + screenWidth - compWidth - 5) {
            nx = currentX + screenWidth - compWidth - 5;
        }

        // bottom
        if(ny > currentY + screenHeight - compHeight - sd
                && ny < currentY + screenHeight - compHeight - 5) {
            ny = currentY + screenHeight - compHeight - 5;
        }
        locked = false;
    }

    public int returnSD() {
        return sd;
    }

    public void setSD(int sd) {
        this.sd = sd;
    }

    public GraphicsDevice[] getScreenList() {
        return screenList;
    }

    public void setScreenList(GraphicsDevice[] screenList) {
        this.screenList = screenList;
    }

}
