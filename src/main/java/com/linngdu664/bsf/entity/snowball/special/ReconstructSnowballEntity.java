package com.linngdu664.bsf.entity.snowball.special;

import com.linngdu664.bsf.block.BlockRegister;
import com.linngdu664.bsf.block.entity.LooseSnowBlockEntity;
import com.linngdu664.bsf.entity.EntityRegister;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReconstructSnowballEntity extends AbstractBSFSnowballEntity {
    private static final int generatingDistance = 1;
    private static final int posNum = 10 + generatingDistance;
    private static final int growthConstraint = 3;

    private static final int trySummonIcePickMaxTimes = 20;
    private static final int icePickMaxNum = 15;

    private static final int trySummonIcePickDetectionRadius = 3;

    private int stockSnow = 0;
    private boolean buildWall = true;
    private int flag =0;

    private boolean icePickBuilding = false;
    private int icePicksNum=0;


    private BlockPos[] passingPosArr = new BlockPos[posNum];
    private IcePick[] icePicks=new IcePick[icePickMaxNum];
    private BlockPos impactPoint;
    private class IcePick{

        private Vec3 icePickVec;
        private double icePickStepSize;
        private double icePickStepRadium;

        private ArrayList<IcePickPoint> path = new ArrayList<>();
        private class IcePickPoint{
            private Vec3 point;
            private double radium=0;
            public IcePickPoint(Vec3 point) {
                this.point = point;
            }
            public void pointGenerate(Level level){
                for (int i = 0; i < Math.floor(radium)+1; i++) {
                    Vec3 a = icePickVec.cross(new Vec3(0, 1, 0)).normalize();
                    Vec3 b = a.cross(icePickVec).normalize();
                    double x = BSFMthUtil.randDouble(0,2*Math.PI);
                    Vec3 c = a.scale(Math.cos(x)).add(b.scale(Math.sin(x))).normalize().scale(radium);
                    placeLooseSnowBlock(level,new BlockPos(BSFMthUtil.vec3ToI(point.add(c))));

                }
                radium+=icePickStepRadium;
            }
        }

        public IcePick(Vec3 icePickVec, double icePickStepSize, double icePickStepRadium) {
            this.icePickVec = icePickVec;
            this.icePickStepSize = icePickStepSize;
            this.icePickStepRadium = icePickStepRadium;
            path.add(new IcePickPoint(impactPoint.getCenter().add(icePickVec)));

        }
        public void generate(Level level){
            placeLooseSnowBlock(level,new BlockPos(BSFMthUtil.vec3ToI(impactPoint.getCenter().add(icePickVec))));
            icePickVec=icePickVec.add(icePickVec.normalize().scale(icePickStepSize));
            path.add(new IcePickPoint(impactPoint.getCenter().add(icePickVec)));
            for (IcePickPoint icePickPoint:path){
                icePickPoint.pointGenerate(level);
            }
        }

    }


    public ReconstructSnowballEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ReconstructSnowballEntity(LivingEntity pShooter, Level pLevel, ILaunchAdjustment launchAdjustment, int stockSnow,boolean buildWall) {
        super(EntityRegister.RECONSTRUCT_SNOWBALL.get(), pShooter, pLevel, launchAdjustment);
        this.stockSnow = stockSnow;
        this.buildWall = buildWall;
    }
    private void icePickInit(Level level){
        fixed();
        this.setNoGravity(true);
        //Determine the direction of the ice pick
        //init ice pick
        for (int i = 0; i < trySummonIcePickMaxTimes; i++) {
            double theta = BSFMthUtil.randDouble(0,2*Math.PI);
            double phi = Math.acos(BSFMthUtil.randDouble(-0.999999,0.999999));
            System.out.println(theta+" "+phi);
            Vec3 direction = BSFMthUtil.rotationToVector(trySummonIcePickDetectionRadius, theta, phi);
            BlockPos blockPos1 = impactPoint.offset(Mth.floor(direction.x), Mth.floor(direction.y), Mth.floor(direction.z));
            if ((level.getBlockState(blockPos1).canBeReplaced()||level.getBlockEntity(blockPos1) instanceof LooseSnowBlockEntity) && icePicksNum< icePickMaxNum){
                icePicks[icePicksNum++]= new IcePick(direction.normalize(), BSFMthUtil.randDouble(0.3, 1), BSFMthUtil.randDouble(0.1, 0.2));
            }
        }
        if (icePicksNum==0||stockSnow<=0){
            this.discard();
        }
        icePickBuilding = true;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Level level = level();
        if (!level.isClientSide && !icePickBuilding) {
            impactPoint = new BlockPos(BSFMthUtil.vec3ToI(pResult.getLocation()));
            icePickInit(level);
        }
        super.onHitEntity(pResult);

    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = level();
        if (!level.isClientSide && !icePickBuilding) {
            if (!(level.getBlockEntity(result.getBlockPos()) instanceof LooseSnowBlockEntity)){
                impactPoint = result.getBlockPos();
                icePickInit(level);
            }
        }
        super.onHitBlock(result);
    }
    private void handleBuildIcePick(Level level){
        if (stockSnow<=0) return;
        for (int i = 0; i < icePicksNum; i++) {
            icePicks[i].generate(level);
        }
    }

    @Override
    public void tick() {
        super.tick();
//        if (stockSnow<=0){
//            this.discard();
//            return;
//        }
        Level level = level();
        if (icePickBuilding){
            fixed();
            if (!level.isClientSide){
                handleBuildIcePick(level);
            }
        }else{
            if (!level.isClientSide&&buildWall) {
                if (flag%growthConstraint==0){
                    handleSetBlock(level);
                }
                posArrMove(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ())));
                flag++;

            }
//            this.push(0,0.01,0);
        }


    }

    /**
     * Handle wall building tasks
     * @param level
     */
    private void handleSetBlock(Level level){
        if (stockSnow<=0) return;
        for (int i = generatingDistance; i < posNum; i++) {
            if (passingPosArr[i]!=null){
                placeLooseSnowBlock(level,passingPosArr[i].offset(0,(i- generatingDistance)/growthConstraint,0));
                placeLooseSnowBlock(level,passingPosArr[i].offset(0,-(i- generatingDistance)/growthConstraint,0));
                //Fill a gap in the wall
                if (i+1<posNum && passingPosArr[i+1]!=null){
                    int dx=passingPosArr[i].getX()-passingPosArr[i+1].getX();
                    int dz=passingPosArr[i].getZ()-passingPosArr[i+1].getZ();
                    int adx=Math.abs(dx);
                    int adz=Math.abs(dz);
                    if (adx>1||adz>1){
                        int x1=passingPosArr[i].getX();
                        int x2=passingPosArr[i+1].getX();
                        int z1=passingPosArr[i].getZ();
                        int z2=passingPosArr[i+1].getZ();
                        if (adx>adz){
                            float k=(float)((z2-z1)/(x2-x1));
                            float b= z1-k*x1;
                            int h=0;
                            if (dx<0){
                                for (int j = 1; j < adx; j++) {
                                    if (k>0){
                                        h=k*(x1+j)+b>z1+h?h+1:h;
                                    }else{
                                        h=k*(x1+j)+b<z1+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(j,(i- generatingDistance)/growthConstraint,h));
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(j,-(i- generatingDistance)/growthConstraint,h));
                                }
                            }else{
                                for (int j = 1; j < adx; j++) {
                                    if (k>0){
                                        h=k*(x2+j)+b>z2+h?h+1:h;
                                    }else{
                                        h=k*(x2+j)+b<z2+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(j,(i- generatingDistance)/growthConstraint,h));
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(j,-(i- generatingDistance)/growthConstraint,h));
                                }
                            }
                        }else{
                            float k=(float)((x2-x1)/(z2-z1));
                            float b= x1-k*z1;
                            int h=0;
                            if (dz<0){
                                for (int j = 1; j < adz; j++) {
                                    if (k>0){
                                        h=k*(z1+j)+b>x1+h?h+1:h;
                                    }else{
                                        h=k*(z1+j)+b<x1+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(h,(i- generatingDistance)/growthConstraint,j));
                                    placeLooseSnowBlock(level,passingPosArr[i].offset(h,-(i- generatingDistance)/growthConstraint,j));
                                }
                            }else{
                                for (int j = 1; j < adz; j++) {
                                    if (k>0){
                                        h=k*(z2+j)+b>x2+h?h+1:h;
                                    }else{
                                        h=k*(z2+j)+b<x2+h?h-1:h;
                                    }
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(h,(i- generatingDistance)/growthConstraint,j));
                                    placeLooseSnowBlock(level,passingPosArr[i+1].offset(h,-(i- generatingDistance)/growthConstraint,j));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void fixed(){
        Vec3 vec3 = this.getDeltaMovement();
        this.push(-vec3.x, -vec3.y, -vec3.z);
    }
    private void placeLooseSnowBlock(Level level, BlockPos blockPos){
        if (stockSnow>0){
            if (level.getBlockState(blockPos).canBeReplaced()) {
                level.setBlock(blockPos, BlockRegister.LOOSE_SNOW_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                stockSnow--;
            } else if(level.getBlockEntity(blockPos) instanceof LooseSnowBlockEntity blockEntity){
                blockEntity.setAge(0);
                blockEntity.setChanged();
            }

        }else{
            this.discard();
        }

    }
    private void posArrMove(BlockPos newPos){
        System.arraycopy(passingPosArr, 0, passingPosArr, 1, posNum - 1);
        passingPosArr[0]=newPos;
    }


    @Override
    public boolean canBeCaught() {
        return false;
    }

    @Override
    public float getBasicDamage() {
        return Float.MIN_NORMAL;
    }

    @Override
    public float getBasicBlazeDamage() {
        return 3;
    }

    @Override
    public int getBasicWeaknessTicks() {
        return 0;
    }

    @Override
    public int getBasicFrozenTicks() {
        return 0;
    }

    @Override
    public double getBasicPunch() {
        return 0;
    }

    @Override
    public float getSubspacePower() {
        return 4F;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ItemRegister.RECONSTRUCT_SNOWBALL.get();
    }
}
