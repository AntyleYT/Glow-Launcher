package fr.antyle.glowclient;


import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.antyle.glowclient.utils.Animation;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Frame extends JFrame {


    private static Frame instance;
    private Panel panel;
    private static File ramFile = new File(String.valueOf(launcher.getPath()),"settings.glow");
    private static File saverFile = new File(String.valueOf(launcher.getPath()),"do_not_share.this");
    private static Saver saver = new Saver(saverFile);

    public Frame() throws IOException {
        instance = this;
        this.setTitle("GlowClient");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(960,509);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("Icon.png"));
        this.setContentPane(panel = new Panel());

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);
        Animation.fadeInFrame(this, 10);

        this.setVisible(true);
    }

    public static void main(String[] arg) throws IOException {
        launcher.crashfile.mkdirs();
        if (!ramFile.exists()) {
            ramFile.createNewFile();
        }
        if (!saverFile.exists()) {
            saverFile.createNewFile();
        }

        instance = new Frame();

        launchrpc();
    }

    public static void launchrpc() {
        final DiscordRPC lib = DiscordRPC.INSTANCE;
        final String appID = "1268975373081645056";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(appID, handlers, true, "");
        DiscordRichPresence discordRichPresence = new DiscordRichPresence();
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000;
        discordRichPresence.details = "Playing GlowClient";
        discordRichPresence.state = "GlowClient";

        lib.Discord_UpdatePresence(discordRichPresence);
    }

    public static Image getImage(String fichier) throws IOException{
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return ImageIO.read(inputStream);

    }

    public static BufferedImage getBuffuredImage(String fichier) throws IOException{
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return ImageIO.read(inputStream);

    }
    public static Frame getInstance() {
        return instance;
    }

    public Panel getPanel() {
        return panel;
    }

    public static File getRamFile() {
        return ramFile;
    }

    public static Saver getSaver() {
        return saver;
    }
}
