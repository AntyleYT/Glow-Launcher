package fr.antyle.glowclient;

import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.CurseFileInfo;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.ForgeVersionType;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.openlauncherlib.NoFramework;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.util.CrashReporter;

import java.io.File;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class launcher {
    private static GameInfos gameInfos = new GameInfos("GlowClient", new GameVersion("GlowClient 1.2.0-beta", GameType.V1_13_HIGHER_FORGE), new GameTweak[]{GameTweak.FORGE});
    private static Path path = gameInfos.getGameDir();
    public static File crashfile = new File(String.valueOf(path), "crashes");
    private static CrashReporter reporter = new CrashReporter(String.valueOf(crashfile), path);
    private static AuthInfos authInfos;

    public static void auth() throws MicrosoftAuthenticationException {
        MicrosoftAuthenticator microsoftAuthenticator = new MicrosoftAuthenticator();
        final String refresh_token = Frame.getSaver().get("refresh_token");
        MicrosoftAuthResult result = null;
        if (refresh_token != null && !refresh_token.isEmpty()) {
            result = microsoftAuthenticator.loginWithRefreshToken(refresh_token);
            authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
        } else {
            result = microsoftAuthenticator.loginWithWebview();
            Frame.getSaver().set("refresh_token", result.getRefreshToken());
            authInfos = new AuthInfos(result.getProfile().getName(), result.getAccessToken(), result.getProfile().getId());
        }
    }
    public static void update () throws Exception {
        VanillaVersion vanillaVersion = new VanillaVersion.VanillaVersionBuilder().withName("Glow-1.16.5").build();
        UpdaterOptions options = new UpdaterOptions.UpdaterOptionsBuilder().build();

        List<CurseFileInfo> curseFileInfos = new ArrayList<>();
        curseFileInfos.add(new CurseFileInfo(821790, 4594802));
        curseFileInfos.add(new CurseFileInfo(372196, 3391448));

        AbstractForgeVersion version = new ForgeVersionBuilder(ForgeVersionType.NEW).withCurseMods(curseFileInfos).withForgeVersion("36.2.39").build();

        FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVanillaVersion(vanillaVersion).withUpdaterOptions(options).withModLoaderVersion(version).build();
        updater.update(path);

    }

    public static void launch () throws Exception {
        NoFramework noFramework = new NoFramework(path , authInfos, GameFolder.FLOW_UPDATER);
        noFramework.getAdditionalArgs().addAll(List.of(Frame.getInstance().getPanel().getRamSelector().getRamArguments()));
        noFramework.launch("1.16.5","36.2.39" , NoFramework.ModLoader.FORGE);

    }
    public static CrashReporter getReporter() {
        return reporter;
    }

    public static Path getPath() {
        return path;
    }

}