package com.ombremoon.spellbound.client.renderer.entity;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Pig;

public class PlaceholderRenderer<T extends PathfinderMob> extends MobRenderer<T, PigModel<T>> {
    private static final ResourceLocation PIG_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/pig/pig.png");

    public PlaceholderRenderer(EntityRendererProvider.Context p_174340_) {
        super(p_174340_, new PigModel(p_174340_.bakeLayer(ModelLayers.PIG)), 0.7F);
    }

    public ResourceLocation getTextureLocation(T entity) {
        return PIG_LOCATION;
    }
}
