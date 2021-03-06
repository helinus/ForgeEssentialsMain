package com.ForgeEssentials.permission.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.WorldServer;

import com.ForgeEssentials.core.network.IForgeEssentialsPacket;
import com.ForgeEssentials.permission.ModulePermissions;
import com.ForgeEssentials.util.OutputHandler;

import cpw.mods.fml.common.network.Player;

public class PacketPermNodeList implements IForgeEssentialsPacket {

	public static final byte		packetID	= 1;
	
	private Packet250CustomPayload packet;
	private static ModulePermissions sendthru;
	
	public PacketPermNodeList(Set<String> permissions){
		
		packet = new Packet250CustomPayload();

		ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(streambyte);

		try
		{
			stream.write(packetID);

			for (String perm : permissions){
			stream.writeBytes(perm + ":");
			}

			stream.close();
			streambyte.close();

			packet.channel = FECHANNEL;
			packet.data = streambyte.toByteArray();
			packet.length = packet.data.length;
		}

		catch (Exception e)
		{
			OutputHandler.felog.info("Error creating packet >> " + this.getClass());
		}
	}
	
	@Override
	public Packet250CustomPayload getPayload() {

		return packet;
	}

	public static void readServer(DataInputStream stream, WorldServer world,
			EntityPlayer player) {
		sendthru.sendPermList((Player) player);
		
	}

}
