package com.example.examplemod.OriginalProject;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EntityCar extends Entity {

    /**
     * Input State
     */
    private boolean inputLeft;
    private boolean inputRight;
    private boolean inputUp;
    private boolean inputDown;

    private float deltaRotation;

    /**
     * Boost System
     */
    private int boostTicks = 0;
    private static final int MAX_BOOST_TICKS = 20;
    private static final float BOOST_MULTIPLIER = 2F;

    //好きなブロックをブーストブロックに
    private static final Block BOOST_BLOCK = Blocks.GOLD_BLOCK;

    /**
     * Constructor
     */
    public EntityCar(EntityType<?> type, Level level) {
        super(type, level);
    }

    /**
     * Interaction
     */
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        System.out.println("interacted");

        //シフトの場合
        //ボートからそのままとってきたけど別になくてもいい
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }

        if (!this.level.isClientSide) {
            return player.startRiding(this)
                    ? InteractionResult.CONSUME
                    : InteractionResult.PASS;
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    /**
     * Tick
     */
    @Override
    public void tick() {
        super.tick();

        if (this.getControllingPassenger() instanceof Player) {
            if (this.level.isClientSide) {

                if (this.isOnBoostBlock()) {
                    this.boostTicks = MAX_BOOST_TICKS;
                }

                this.addParticles();

                if (this.boostTicks > 0) {
                    this.boostTicks--;
                }

                this.controlCar();
                this.level.sendPacketToServer(new ServerboundMovePlayerPacket.Pos(this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z, this.onGround));
            }
            //重力
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08D, 0));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            
        } else {
            this.setDeltaMovement(Vec3.ZERO);
        }
    }

    /**
     * Input
     */
    //こいつの呼び方がいまいち完全に理解できてない
    //PlayerInputOnCarを作ったけどこれだけはAIに強く頼らざるを得なかった
    public void setInput(boolean left, boolean right, boolean forward, boolean back) {
        this.inputLeft = left;
        this.inputRight = right;
        this.inputUp = forward;
        this.inputDown = back;
    }

    /**
     * Boost Logic
     */
    private boolean isOnBoostBlock() {
        BlockPos pos = this.blockPosition().below();
        BlockState state = this.level.getBlockState(pos);
        return state.is(BOOST_BLOCK);
    }

    /**
     * Movement Logic
     */
    public void controlCar() {

        //摩擦
        this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        this.deltaRotation *= 0.8F;

        float f = 0.0F;

        float speedMultiplier = (this.boostTicks > 0)
                ? BOOST_MULTIPLIER
                : 1.0F;

        if (this.inputLeft) {
            --this.deltaRotation;
        }
        if (this.inputRight) {
            ++this.deltaRotation;
        }
        this.setYRot(this.getYRot() + this.deltaRotation);

        if (this.inputUp) {
            f += 0.04F * speedMultiplier;
        }
        if (this.inputDown) {
            f -= 0.005F;
        }

        //静止状態で車体を回すと少し前に進む(?)
        if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
            f += 0.005F;
        }

        this.setDeltaMovement(this.getDeltaMovement().add(
                Mth.sin(-this.getYRot() * ((float)Math.PI / 180F)) * f,
                0.0D,
                Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * f
        ));
    }

    /**
     * Visual Effects
     */
    private void addParticles() {
        if (this.boostTicks > 0) {

            double x = this.getX();
            double y = this.getY() + 0.2;
            double z = this.getZ();

            this.level.addParticle(
                    ParticleTypes.FLAME,
                    x, y, z,
                    0, 0, 0
            );
        }
    }

    /**
     * Rider Positioning
     */
    //ずれない方が好ましいから0
    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    //positionRiderとclampRotationはあえてBoatからコピペ
    //Animalの対応とかは正直必要ない
    @Override
    public void positionRider(Entity passenger) {
        if (!this.hasPassenger(passenger)) return;

        float f = 0.0F;
        float yOffset = (float)(
                (this.isRemoved() ? 0.01F : this.getPassengersRidingOffset())
                        + passenger.getMyRidingOffset()
        );

        if (this.getPassengers().size() > 1) {
            int i = this.getPassengers().indexOf(passenger);
            f = (i == 0) ? 0.2F : -0.6F;

            if (passenger instanceof Animal) {
                f += 0.2D;
            }
        }

        Vec3 offset = new Vec3(f, 0.0D, 0.0D)
                .yRot(-this.getYRot() * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));

        passenger.setPos(
                this.getX() + offset.x,
                this.getY() + yOffset,
                this.getZ() + offset.z
        );

        passenger.setYRot(passenger.getYRot() + this.deltaRotation);
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);

        this.clampRotation(passenger);

        if (passenger instanceof Animal && this.getPassengers().size() > 1) {
            int j = passenger.getId() % 2 == 0 ? 90 : 270;
            passenger.setYBodyRot(((Animal) passenger).yBodyRot + j);
            passenger.setYHeadRot(passenger.getYHeadRot() + j);
        }
    }

    protected void clampRotation(Entity entity) {
        entity.setYBodyRot(this.getYRot());

        float f = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float clamped = Mth.clamp(f, -105.0F, 105.0F);

        entity.yRotO += clamped - f;
        entity.setYRot(entity.getYRot() + clamped - f);
        entity.setYHeadRot(entity.getYRot());
    }

    /**
     * ようわからんやつら
     */
    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}
}