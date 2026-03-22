package com.example.examplemod.OriginalProject;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;


public class EntityCar extends Entity {

    public EntityCar(EntityType<?> type, Level level) {
        super(type, level);
    }

    /**
     * 1st Step: Make it rideable
     *
     * interact()
     * getControllingPassenger()
     * canAddPassenger()
     */

    /**
     * 2nd Step: Add basic synced data
     */

    /**
     * 3rd Step: Input system
     * inputLeft, inputRight, inputUp, inputDown
     */

    /**
     * 4th Step: Movement
     * A. Steering
     * change Y rotation when left/right
     * B. Forward movement
     * use sin/cos of rotation (same idea as Boat)
     * C. Friction
     * slow down when no input
     *
     * controlBoat(), floatBoat()
     */

    /**
     * 5th Step: Tick
     * tick():
     *     update state
     *     handle input
     *     apply physics
     *     move
     */

    /**
     * 6th Step: Passenger positioning
     * positionRider()
     */

    /**
     * 7th Step: ground behavior
     * getGroundFriction()
     */

    /**
     * 8th Step:
     * Networking for multi-players
     * ServerboundPaddleBoatPacket
     */


    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {}
}