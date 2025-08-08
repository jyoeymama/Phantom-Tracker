package com.phantomtracker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PhantomTrackerClient implements ClientModInitializer {
    public static final String MOD_ID = "phantomtracker";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static final Path LOG_FILE_PATH = Paths.get("phantom_spawns.txt");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void onInitializeClient() {
        LOGGER.info("PhantomTracker mod initialized!");
        
        // Create log file if it doesn't exist
        try {
            if (!Files.exists(LOG_FILE_PATH)) {
                Files.createFile(LOG_FILE_PATH);
                LOGGER.info("Created phantom spawn log file: {}", LOG_FILE_PATH.toAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create phantom spawn log file", e);
        }
    }
    
    public static void onPhantomSpawn(Vec3d position) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        String logMessage = String.format("Phantom spawned at X: %.1f, Y: %.1f, Z: %.1f", 
                                        position.x, position.y, position.z);
        
        // Log to chat (client-side only, no operator needed)
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.world != null) {
            // Use inGameHud to display message locally without sending to server
            client.inGameHud.getChatHud().addMessage(Text.literal("§6[PhantomTracker] §f" + logMessage));
        }
        
        // Log to file (completely local)
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH.toFile(), true)) {
            writer.write(String.format("[%s] %s%n", timestamp, logMessage));
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Failed to write phantom spawn to log file", e);
        }
        
        // Log to console (local only)
        LOGGER.info(logMessage);
    }
}