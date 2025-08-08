package com.phantomtracker.mixin;

import com.phantomtracker.PhantomTrackerClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    
    @Inject(method = "onEntitySpawn", at = @At("HEAD"))
    private void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci) {
        // Check if the spawned entity is a phantom
        if (packet.getEntityType() == EntityType.PHANTOM) {
            Vec3d position = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
            PhantomTrackerClient.onPhantomSpawn(position);
        }
    }
}