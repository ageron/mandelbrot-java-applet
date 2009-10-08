/* Decompiled by Mocha from Fractales.class */
/* Originally compiled from Fractales.java */

import java.awt.*;
import java.applet.Applet;

public class Fractales extends Applet
{
    double x1;
    double y1;
    double x2;
    double y2;
    static final double maxRayon = 1000.0;
    static final int maxIterations = 20000;
    ZoneFractale zone;

    void zoomIn()
    {
        zone.zoomIn();
    }

    void zoomOut()
    {
        zone.zoomOut();
    }

    void couleurs()
    {
        zone.couleurs();
    }

    public void init()
    {
        setBackground(Color.black);
        setLayout(new BorderLayout(1, 1));
        add(new BarreOutils(this), "North");
        zone = new ZoneFractale(this);
        add(zone, "Center");
        setVisible(true);
    }

    void terminer()
    {
        zone.arreter();
    }

    public Insets getInsets()
    {
        Insets normal = super.getInsets();
        return new Insets(normal.top + 1, normal.left + 1, normal.bottom + 1, normal.right + 1);
    }

    public void destroy()
    {
        terminer();
    }

    public Fractales()
    {
        x1 = -2.0;
        y1 = -2.0;
        x2 = 3.0;
        y2 = 2.0;
    }
}
