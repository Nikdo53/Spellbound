package com.ombremoon.spellbound.common.magic.acquisition.transfiguration;

import com.mojang.serialization.Dynamic;
import com.ombremoon.spellbound.common.content.world.multiblock.Multiblock;
import com.ombremoon.spellbound.main.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

import java.util.UUID;

public class RitualInstance {
    protected static final Logger LOGGER = Constants.LOG;
    private final Holder<TransfigurationRitual> ritualHolder;
    private final UUID ownerID;
    private final BlockPos blockPos;
    private final Multiblock.MultiblockPattern pattern;
    private boolean active;
    public int ticks;

    public RitualInstance(Holder<TransfigurationRitual> ritualHolder, UUID ownerID, BlockPos blockPos, Multiblock.MultiblockPattern pattern) {
        this.ritualHolder = ritualHolder;
        this.ownerID = ownerID;
        this.blockPos = blockPos;
        this.pattern = pattern;
    }

    public void tick(ServerLevel level) {
        Player player = level.getPlayerByUUID(this.ownerID);
        TransfigurationRitual ritual = ritualHolder.value();
        if (this.ticks >= ritual.definition().duration()) {
            ritual.effects().forEach(ritualEffect -> ritualEffect.onActivated(level, ritual.definition().tier(), player, this.blockPos, this.pattern));
            this.active = false;
        }

        this.ticks++;
    }

    public void toggleRitual() {
        this.active = !this.active;
    }

    public boolean isActive() {
        return this.active;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        TransfigurationRitual.CODEC
                .encodeStart(NbtOps.INSTANCE, this.ritualHolder)
                .resultOrPartial(LOGGER::error)
                .ifPresent(nbt -> tag.put("Ritual", nbt));
        tag.putUUID("Owner", this.ownerID);
        tag.putInt("X", blockPos.getX());
        tag.putInt("Y", blockPos.getY());
        tag.putInt("Z", blockPos.getZ());
        tag.put("Pattern", this.pattern.save());
        tag.putBoolean("Active", this.active);
        tag.putInt("Ticks", this.ticks);
        return tag;
    }

    public static RitualInstance load(CompoundTag tag) {
        if (tag.contains("Ritual", 10)) {
            var optional = TransfigurationRitual.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, tag.get("Ritual"))).resultOrPartial(LOGGER::error);
            if (optional.isPresent()) {
                UUID owner = tag.getUUID("Owner");
                BlockPos blockPos = new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
                Multiblock.MultiblockPattern pattern = Multiblock.MultiblockPattern.load(tag);
                RitualInstance instance = new RitualInstance(optional.orElseThrow(), owner, blockPos, pattern);
                instance.active = tag.getBoolean("Active");
                instance.ticks = tag.getInt("Ticks");
                return instance;
            }
        }

        return null;
    }
}
