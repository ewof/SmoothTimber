package com.syntaxphoenix.spigot.smoothtimber.compatibility.coreprotect;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;

import com.syntaxphoenix.spigot.smoothtimber.version.manager.VersionChanger;

import net.coreprotect.v1.CoreProtect;
import net.coreprotect.v1.CoreProtectAPI;

public class CoreCompat_v1_8_x implements ICoreCompat {

    private final CoreProtectAPI api;
    private final VersionChanger changer;

    protected CoreCompat_v1_8_x(final Plugin plugin, final VersionChanger changer) {
        api = ((CoreProtect) plugin).getAPI();
        this.changer = changer;
    }

    @Override
    public void logRemoval(final String user, final Location location, final BlockState block) {
        api.logRemoval(user, location, block.getType(), changer.getData(block));
    }

    @Override
    public boolean isPlayerPlaced(BlockState state) {
        final List<String[]> list = api.blockLookup(state.getBlock(), 0);
        if (list == null || list.isEmpty()) {
            return false;
        }
        final String[] array = list.get(0);
        final CoreProtectAPI.ParseResult parseResult = api.parseResult(array);

        if (parseResult.getPlayer().isEmpty() || parseResult.getPlayer().startsWith("#") || parseResult.isRolledBack()) {
            return false;
        }
        return parseResult.getActionId() != 0;
    }

}
