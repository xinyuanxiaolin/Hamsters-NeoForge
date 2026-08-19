package com.starfish_studios.hamsters.client;

import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import java.util.HashMap;
import java.util.Map;

public final class HamsterRenderState extends LivingEntityRenderState implements GeoRenderState {
    private final Hamster hamster;
    private final Map<DataTicket<?>, Object> geckolibData = new HashMap<>();

    public HamsterRenderState(Hamster hamster) {
        this.hamster = hamster;
    }

    @Override
    public Map<DataTicket<?>, Object> getDataMap() {
        return geckolibData;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D> D getGeckolibData(DataTicket<D> dataTicket) {
        if (dataTicket == DataTickets.ANIMATABLE_MANAGER) {
            long instanceId = hamster.getId();
            AnimatableManager<?> manager = hamster.getAnimatableInstanceCache().getManagerForId(instanceId);
            return (D) manager;
        }
        return GeoRenderState.super.getGeckolibData(dataTicket);
    }
}