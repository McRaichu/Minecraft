package com.mcraichu.obeliskoflight.blockgag;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityGag extends TileEntity{
    //The coordinates of our primary block will be stored in these variables.
    public int primary_x;
    public int primary_y;
    public int primary_z;
    public boolean all_placed;
   
    public void setValues(int x, int y, int z){
    	primary_x = x;
    	primary_y = y;
    	primary_z = z;
    	all_placed = false;
    }
    
    @Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{	    
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("ap", this.all_placed);
		par1NBTTagCompound.setInteger("px", this.primary_x);
	    par1NBTTagCompound.setInteger("py", this.primary_y);
	    par1NBTTagCompound.setInteger("pz", this.primary_z);
	    
	}
    
    @Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	    super.readFromNBT(par1NBTTagCompound);
	    this.all_placed = par1NBTTagCompound.getBoolean("ap");
	    this.primary_x = par1NBTTagCompound.getInteger("px");
	    this.primary_y = par1NBTTagCompound.getInteger("py");
	    this.primary_z = par1NBTTagCompound.getInteger("pz");
	    
	}
    
//    @Override
//	public Packet getDescriptionPacket() {
//		NBTTagCompound nbtTag = new NBTTagCompound();
//		writeToNBT(nbtTag);
//		return new S35PacketUpdateTileEntity(this.getPos(), 1, nbtTag);
//	}

//	@Override
//	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
//		readFromNBT(packet.getNbtCompound());
//	} 
}
