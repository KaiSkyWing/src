package com.example.examplemod.OriginalProject;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.phys.Vec3;


public class EntityCar extends Entity {

    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;
    private float deltaRotation;

    private static final EntityDataAccessor<Boolean> DATA_ID_CAR_LEFT = SynchedEntityData.defineId(EntityCar.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_ID_CAR_RIGHT = SynchedEntityData.defineId(EntityCar.class, EntityDataSerializers.BOOLEAN);

    public EntityCar(EntityType<?> type, Level level) {
        super(type, level);
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        System.out.println("interacted");
        if (pPlayer.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }else {
            if (!this.level.isClientSide) {
                return pPlayer.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    public boolean isControlledByLocalInstance() {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof Player) {
            return ((Player)entity).isLocalPlayer();
        } else {
            return !this.level.isClientSide;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getControllingPassenger() instanceof Player) {
            if (this.level.isClientSide) {
                this.controlCar();
            }
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    public void setInput(boolean pLeftInputDown, boolean pRightInputDown, boolean pForwardInputDown, boolean pBackInputDown) {
        this.inputLeft = pLeftInputDown;
        this.inputRight = pRightInputDown;
        this.inputUp = pForwardInputDown;
        this.inputDown = pBackInputDown;
    }
    /*
    public void setCarState(boolean pLeft, boolean pRight) {
        this.entityData.set(DATA_ID_CAR_LEFT, pLeft);
        this.entityData.set(DATA_ID_CAR_RIGHT, pRight);
    }
     */

    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        if (this.hasPassenger(pPassenger)) {
            float f = 0.0F;
            float f1 = (float)((this.isRemoved() ? (double)0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(pPassenger);
                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (pPassenger instanceof Animal) {
                    f = (float)((double)f + 0.2D);
                }
            }

            Vec3 vec3 = (new Vec3((double)f, 0.0D, 0.0D)).yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            pPassenger.setPos(this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
            pPassenger.setYRot(pPassenger.getYRot() + this.deltaRotation);
            pPassenger.setYHeadRot(pPassenger.getYHeadRot() + this.deltaRotation);
            this.clampRotation(pPassenger);
            if (pPassenger instanceof Animal && this.getPassengers().size() > 1) {
                int j = pPassenger.getId() % 2 == 0 ? 90 : 270;
                pPassenger.setYBodyRot(((Animal)pPassenger).yBodyRot + (float)j);
                pPassenger.setYHeadRot(pPassenger.getYHeadRot() + (float)j);
            }

        }
    }

    protected void clampRotation(Entity pEntityToUpdate) {
        pEntityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(pEntityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        pEntityToUpdate.yRotO += f1 - f;
        pEntityToUpdate.setYRot(pEntityToUpdate.getYRot() + f1 - f);
        pEntityToUpdate.setYHeadRot(pEntityToUpdate.getYRot());
    }

    public void controlCar() {

        this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        this.deltaRotation *= 0.8F;

        float f = 0.0F;

        if (this.inputLeft) {
            --this.deltaRotation;
        }

        if (this.inputRight) {
            ++this.deltaRotation;
        }

        if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
            f += 0.005F;
        }

        this.setYRot(this.getYRot() + this.deltaRotation);

        if (this.inputUp) {
            f += 0.04F;
        }

        if (this.inputDown) {
            f -= 0.005F;
        }

        this.setDeltaMovement(this.getDeltaMovement().add((double)(Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f), 0.0D, (double)(Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f)));
    }



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