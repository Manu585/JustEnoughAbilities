package com.github.manu585.bending.air;

import com.github.manu585.JustEnoughAbilities;
import com.github.manu585.bending.JeaAbility;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AirAbility;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class VortexStep extends AirAbility implements AddonAbility, JeaAbility {
    public VortexStep(Player player) {
        super(player);
    }

    @Override
    public void progress() {

    }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return 0;
    }

    @Override
    public String getName() {
        return "VortexStep";
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getAuthor() {
        return getJeaAuthorsColored("Manunu_");
    }

    @Override
    public String getVersion() {
        return getJeaVersion();
    }

    @Override
    public boolean isEnabled() {
        return getJeaConfig().getBoolean("Air.Air.VortexStep.Enabled", true);
    }
}
