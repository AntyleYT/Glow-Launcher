package fr.antyle.glowclient;

import fr.antyle.glowclient.utils.Animation;
import fr.antyle.glowclient.utils.MicrosoftThread;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


import static fr.antyle.glowclient.Frame.getBuffuredImage;
import static fr.antyle.glowclient.Frame.getImage;

public class Panel extends JPanel implements SwingerEventListener {

    private Image background = getImage("launcherbackground.png");
    private STexturedButton settings = new STexturedButton(getBuffuredImage("settings.png"), getBuffuredImage("settings.png"));

    private STexturedButton play = new STexturedButton(getBuffuredImage("Button1.png"), getBuffuredImage("button2.png"));
    private STexturedButton microsoft = new STexturedButton(getBuffuredImage("microsofticon.png"),getBuffuredImage("microsofticon.png"));
    private STexturedButton altf4 = new STexturedButton(getBuffuredImage("altf4.png"),getBuffuredImage("altf4.png"));
    private RamSelector ramSelector = new RamSelector(Frame.getRamFile());

    public Panel() throws IOException {
        this.setLayout(null);

        play.setBounds(180,90);
        play.setLocation(390,320);
        play.addEventListener(this);
        this.add(play);

        microsoft.setBounds(90,90);
        microsoft.setLocation(850,380);
        microsoft.addEventListener(this);
        this.add(microsoft);

        settings.setBounds(75,75);
        settings.setLocation(890,270);
        settings.addEventListener(this);
        this.add(settings);

        altf4.setBounds(50,50);
        altf4.setLocation(905,5);
        altf4.addEventListener(this);
        this.add(altf4);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        g.drawImage(background ,0,0, this.getWidth(), this.getHeight(),this);
    }


    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == microsoft) {
            Thread t = new Thread(new MicrosoftThread());
            t.start();
        } else if (swingerEvent.getSource()== play) {
            ramSelector.save();
            try {
                launcher.update();
            } catch (Exception e) {
                launcher.getReporter().catchError(e , "Impossible de mettre à jour le launcher!");
            }

            try {
                launcher.launch();
            } catch (Exception e) {
                launcher.getReporter().catchError(e, "Impossible de démarrer le jeu");
        }

        }else if (swingerEvent.getSource() == settings) {
            ramSelector.display();
        } else if (swingerEvent.getSource() == altf4) {
            Animation.fadeOutFrame(Frame.getInstance(), 30, () -> System.exit(0));
            
        }

    }

    public RamSelector getRamSelector() {
        return ramSelector;
    }
}
