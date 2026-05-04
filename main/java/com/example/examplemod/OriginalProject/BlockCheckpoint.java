package com.example.examplemod.OriginalProject;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCheckpoint extends Block {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public BlockCheckpoint() {
        super(BlockBehaviour.Properties
                .of(Material.STRUCTURAL_AIR)
                .noCollission());

        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

//    @Override
//    public RenderShape getRenderShape(BlockState state) {
//        return RenderShape.INVISIBLE;
//    }

    /**
     * なんか攻撃とかの当たり判定のために必要っぽい？
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }

    /**
     * 右クリックで Activated を false に
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(ACTIVATED, false), 3);
        }

        return InteractionResult.SUCCESS;
    }

    /**
     * 中に入って Activated を true に
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!state.getValue(ACTIVATED)) {

            if (level.isClientSide) {
                for (int i = 0; i < 20; i++) {
                    level.addParticle(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            pos.getX() + 0.5 + (level.random.nextDouble() - 0.5),
                            pos.getY() + 1.0 + (level.random.nextDouble() * 0.5),
                            pos.getZ() + 0.5 + (level.random.nextDouble() - 0.5),
                            0, 0.1, 0
                    );
                }
            } else {
                level.setBlock(pos, state.setValue(ACTIVATED, true), 3);
                System.out.println("interacted");
            }
        }

        super.entityInside(state, level, pos, entity);
    }

    /**
     * チェックポイントブロックを所持時のみモデルを描画
     */
    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (net.minecraft.client.Minecraft.getInstance().player != null) {

            var player = net.minecraft.client.Minecraft.getInstance().player;

            if (player.getMainHandItem().getItem() == ExampleMod.BLOCK_CHECKPOINT.asItem()) {
                return RenderShape.MODEL;
            }
        }

        return RenderShape.INVISIBLE;
    }
}