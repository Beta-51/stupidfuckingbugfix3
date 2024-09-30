package nourmodz.fixies.mixin;

import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.weather.Weather;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
@Mixin(value = EntitySkeleton.class,remap = false)
public class EntitySkeletonMixin extends EntityMonster {
	public EntitySkeletonMixin(World world) {
		super(world);
	}

	@Overwrite
	public void onLivingUpdate() {
		if (this.world.isClientSide) {
			this.attackTime = this.entityData.getInt(15);
		} else {
			this.entityData.set(15, this.attackTime);
		}

		if (this.world.isDaytime()) {
			float f = this.getBrightness(1.0F);
			if (f > 0.5F && this.world.canBlockSeeTheSky(MathHelper.floor_double(this.x), MathHelper.floor_double(this.y), MathHelper.floor_double(this.z)) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && (this.world.getCurrentWeather() != Weather.overworldFog || this.world.weatherManager.getWeatherPower() < 0.75F)) {
				this.remainingFireTicks = 300;
			}
		}

		super.onLivingUpdate();
	}


}
