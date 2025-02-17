import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.openlauncherlib.util.explorer.FileList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    private static GameInfos gameInfos = new GameInfos("GlowClient", new GameVersion("GlowClient 1.2.0-beta", GameType.V1_13_HIGHER_FORGE), new GameTweak[]{GameTweak.FORGE});
    private static Path path = gameInfos.getGameDir();
    public static File crashfile = new File(String.valueOf(path), "crashes");
    public static File launcherFile = new File(String.valueOf(path), "launchers");
    private static CrashReporter reporter = new CrashReporter(String.valueOf(crashfile), path);


    public static void main(String[] args) throws Exception {
        crashfile.mkdirs();
        launcherFile.mkdirs();
        System.out.printf("GlowClient BOOTSTRAP CONSOLE");
        showSplashScreen();
        doUpdate();
    }
    public static void showSplashScreen() throws IOException {
        SplashScreen splashScreen = new SplashScreen("GlowClient Updater", ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/updater.png"))));
        splashScreen.displayFor(5000L);
    }
    public static void doUpdate() throws Exception {
        FlowUpdater flowUpdater = new FlowUpdater.FlowUpdaterBuilder().withExternalFiles(ExternalFile.getExternalFilesFromJson("https://raw.githubusercontent.com/AntyleYT/Glow-Launcher/refs/heads/master/GLOW-updates/update.json")).build();
        flowUpdater.update(Paths.get(path + "/launchers"));
    }
    public static void launchLauncher() throws LaunchException {
        ClasspathConstructor constructor = new ClasspathConstructor();
        FileList fileList = new FileList();
        fileList.add(new File(launcherFile, "launcher.jar").toPath());
        constructor.add(fileList);
        ExternalLaunchProfile profile = new ExternalLaunchProfile("fr.antyle.glowclient.Frame", constructor.make());
        ExternalLauncher launcher = new ExternalLauncher(profile);

        Process p = launcher.launch();

    }
}