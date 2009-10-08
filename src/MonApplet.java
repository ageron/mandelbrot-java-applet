/* Decompiled by Mocha from MonApplet.class */
/* Originally compiled from MonApplet.java */

import java.applet.Applet;
import java.awt.Graphics;

public class MonApplet extends Applet
{
    public void paint(Graphics g)
    {
        g.draw3DRect(10, 10, 30, 30, true);
    }

    public MonApplet()
    {
    }
}
