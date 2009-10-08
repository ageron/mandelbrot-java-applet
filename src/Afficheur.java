/* Decompiled by Mocha from Afficheur.class */
/* Originally compiled from Fractales.java */

import java.awt.Component;

class Afficheur extends Thread
{
    ZoneFractale zone;
    boolean arreter;

    Afficheur(ZoneFractale zone)
    {
        arreter = false;
        this.zone = zone;
        start();
    }

    void arreter()
    {
        arreter = true;
    }

    public void run()
    {
        while (!arreter)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
            }
            zone.paint(zone.getGraphics());
        }
    }
}
