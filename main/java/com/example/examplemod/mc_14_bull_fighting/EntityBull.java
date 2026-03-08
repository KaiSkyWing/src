package com.example.examplemod.mc_14_bull_fighting;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EntityBull extends Animal{

    public EntityBull(EntityType<? extends Animal> entityTypeIn, Level level){
        super(entityTypeIn, level);
    }

    @Override
    protected void registerGoals(){
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0d, false));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder registerAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.FOLLOW_RANGE, 35.0d)
                .add(Attributes.MAX_HEALTH, 20.0d)
                .add(Attributes.ARMOR, 2.0d)
                .add(Attributes.ATTACK_DAMAGE, 2.0d);
    }

    @Override
    public void aiStep(){
        super.aiStep();
        if(this.getTarget() == null){
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2f);
        }else{
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6f);
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob){
        return null;
    }

    @Override
    protected SoundEvent getAmbientSound(){
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource){
        return SoundEvents.COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound(){
        return SoundEvents.COD_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState){
        this.playSound(SoundEvents.COW_STEP, 0.15f, 1.0f);
    }

    @Override
    protected float getSoundVolume(){
        return 0.4f;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize){
        return this.isBaby() ? pSize.height * 0.95f : 1.3f;
    }
}
