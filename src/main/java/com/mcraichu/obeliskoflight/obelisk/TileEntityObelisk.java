package com.mcraichu.obeliskoflight.obelisk;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.utilities.Utilities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityObelisk extends TileEntity implements IUpdatePlayerListBox{

	public static final Color INVALID_COLOR = null;

	private float damage = 10.0f;
	private float distance = 16.0f;
	private float player_distance = 5.0f;
	public int ticks = 0;
	public int ticksUntilReady = 0;
	public int untilReady = 30;
	public int ticksUntilCharged = 0;
	public int untilCharged = 60;
	public boolean shot = false;
	public boolean rendered = false;
	EntityMob target = null;
	Vec3 target_pos;
	//Here all the stuff about attacking is done
	@Override
	public void update() {
		//make sure the block is updated
		if (ticks % 5 == 0) {
			worldObj.markBlockForUpdate(this.pos);
		}
		if((shot == true)&&(ticks > (int)(untilReady/2))){
			shot = false;
		}

		if(ticksUntilCharged == 1){
			this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), Reference.MODID + ":" + "sound_charge", 1.0f, 1.0f, false);
		}

		//increase the ticks
		ticks++;
		
		if((ticks < (int)(untilReady/2))){
			return;
		}

		if(target != null){
			if(target.getDistanceSqToCenter(this.pos) > (((distance+2)*(distance+2))*3)){ //16*16 + 16*16 + 16*16 aka 256 * 3 
				//out of range
				target = null;
				shot = false;
			}else if(target.isDead){
				//target died
				target = null;
				shot = false;
			}else if(Utilities.inLineOfSight(this, new Vec3(0.5,3,0.5), target)){
				//can't see the target
				target = null;
				shot = false;
			}

		}

		if(target == null){
			AxisAlignedBB bb = new AxisAlignedBB(this.pos, this.pos.add(1, 1, 1));
			List list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, bb.expand((double)distance, (double)distance, (double)distance));

			//EntityEnderCrystal entityendercrystal = null;
			double d0 = Double.MAX_VALUE;
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityMob targetMob = (EntityMob)iterator.next();
				double d1 = targetMob.getDistanceSqToCenter(this.pos);
				
				if(Utilities.inLineOfSight(this, new Vec3(0.5,3,0.5), targetMob)){
					if (d1 < d0)
					{
						d0 = d1;
						target = targetMob;
					}
				}				
			}
		}

		if(target == null){
			//nothing to kill
			ticksUntilCharged = 0;
			ticksUntilReady = 0;
			return;
		}

		//at this point we have a target (new or old)
		ticksUntilCharged++;
		ticksUntilReady++;

		if(ticksUntilCharged < (untilCharged)){
			return;
		}

		if(ticksUntilReady < (untilReady)){
			return;
		}

		//KILL IT WITH FIRE 
		// give exp if player nearby
		DamageSource source = DamageSource.onFire.setDamageBypassesArmor();
		EntityPlayer player = worldObj.getClosestPlayer(this.pos.getX(), this.pos.getY(), this.pos.getZ(), player_distance);
		if(player != null){
			source = new EntityDamageSource("onFire", player).setDamageBypassesArmor();
		}		

		target.attackEntityFrom(source, damage);
		target.hurtResistantTime = 0;



		target.setFire(1);
		shot = true;
		ticksUntilReady = 0;
		ticks = 0;
		this.worldObj.playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), Reference.MODID + ":" + "sound_fire", 1.0f, 1.0f, false);
	}

	public int currentCharge(){
		return ticksUntilCharged;
	}

	public int maxCharge(){
		return untilCharged;
	}

	// get the colour of the gem.  returns INVALID_COLOR if not set yet.
	public Color getGemColour() {
		return gemColour;
	}

	public void setGemColour(Color newColour)
	{
		gemColour = newColour;
	}

	/**
	 * Calculate the next angular position of the gem, given its current speed.
	 * @param revsPerSecond
	 * @return the angular position in degrees (0 - 360)
	 */
	public double getNextAngularPosition(double revsPerSecond)
	{
		// we calculate the next position as the angular speed multiplied by the elapsed time since the last position.
		// Elapsed time is calculated using the system clock, which means the animations continue to
		//  run while the game is paused.
		// Alternatively, the elapsed time can be calculated as
		//  time_in_seconds = (number_of_ticks_elapsed + partialTick) / 20.0;
		//  where your tileEntity's update() method increments number_of_ticks_elapsed, and partialTick is passed by vanilla
		//   to your TESR renderTileEntityAt() method.
		long timeNow = System.nanoTime();
		if (lastTime == INVALID_TIME) {   // automatically initialise to 0 if not set yet
			lastTime = timeNow;
			lastAngularPosition = 0.0;
		}
		final double DEGREES_PER_REV = 360.0;
		final double NANOSECONDS_PER_SECOND = 1e9;
		double nextAngularPosition = lastAngularPosition + (timeNow - lastTime) * revsPerSecond * DEGREES_PER_REV / NANOSECONDS_PER_SECOND;
		nextAngularPosition = nextAngularPosition % DEGREES_PER_REV;
		lastAngularPosition = nextAngularPosition;
		lastTime = timeNow;
		return nextAngularPosition;
	}

	// When the world loads from disk, the server needs to send the TileEntity information to the client
	//  it uses getDescriptionPacket() and onDataPacket() to do this
	// In this case, we need it for the gem colour.  There's no need to save the gem angular position because
	//  the player will never notice the difference and the client<-->server synchronisation lag will make it
	//  inaccurate anyway
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	// This is where you save any data that you don't want to lose when the tile entity unloads
	// In this case, we only need to store the gem colour.  For examples with other types of data, see MBE20
	@Override
	public void writeToNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		if (gemColour != INVALID_COLOR) {
			parentNBTTagCompound.setInteger("gemColour", gemColour.getRGB());
		}
	}

	// This is where you load the data that you saved in writeToNBT
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound)
	{
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location

		// important rule: never trust the data you read from NBT, make sure it can't cause a crash

		final int NBT_INT_ID = 3;					// see NBTBase.createNewByType()
		Color readGemColour = INVALID_COLOR;
		if (parentNBTTagCompound.hasKey("gemColour", NBT_INT_ID)) {  // check if the key exists and is an Int. You can omit this if a default value of 0 is ok.
			int colorRGB = parentNBTTagCompound.getInteger("gemColour");
			readGemColour = new Color(colorRGB);
		}
		gemColour = readGemColour;
	}

	/**
	 * Don't render the gem if the player is too far away
	 * @return the maximum distance squared at which the TESR should render
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared()
	{
		final int MAXIMUM_DISTANCE_IN_BLOCKS = 32;
		return MAXIMUM_DISTANCE_IN_BLOCKS * MAXIMUM_DISTANCE_IN_BLOCKS;
	}

	/** Return an appropriate bounding box enclosing the TESR
	 * This method is used to control whether the TESR should be rendered or not, depending on where the player is looking.
	 * The default is the AABB for the parent block, which might be too small if the TESR renders outside the borders of the
	 *   parent block.
	 * If you get the boundary too small, the TESR may disappear when you aren't looking directly at it.
	 * @return an appropriately size AABB for the TileEntity
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		// if your render should always be performed regardless of where the player is looking, use infinite
		AxisAlignedBB infiniteExample = INFINITE_EXTENT_AABB;

		// our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+3, z+1]
		AxisAlignedBB aabb = new AxisAlignedBB(getPos(), getPos().add(1, 3, 1));
		return infiniteExample;
	}

	private Color gemColour = INVALID_COLOR;  // the RGB colour of the gem

	private final long INVALID_TIME = 0;
	private long lastTime = INVALID_TIME;  // used for animation
	private double lastAngularPosition; // used for animation


}
