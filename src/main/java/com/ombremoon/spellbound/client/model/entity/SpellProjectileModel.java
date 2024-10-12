package com.ombremoon.spellbound.client.model.entity;

import com.ombremoon.spellbound.CommonClass;
import com.ombremoon.spellbound.common.content.entity.SpellEntity;
import com.ombremoon.spellbound.common.content.entity.SpellProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SpellProjectileModel<T extends SpellProjectile> extends GeoModel<T> {

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return CommonClass.customLocation("geo/entity/" + getName(animatable) + ".geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return CommonClass.customLocation("textures/entity/" + getName(animatable) + ".png");

    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return CommonClass.customLocation("animations/entity/" + getName(animatable) + ".animation.json");

    }

    protected String getName(T spell) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(spell.getType()).getPath();
    }
}
