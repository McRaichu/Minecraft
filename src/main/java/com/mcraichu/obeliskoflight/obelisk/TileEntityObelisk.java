package com.mcraichu.obeliskoflight.obelisk;

import java.awt.Color;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityObelisk extends TileEntity{
	
	public static final Color INVALID_COLOR = null;

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

		// our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+2, z+1]
		AxisAlignedBB aabb = new AxisAlignedBB(getPos(), getPos().add(1, 2, 1));
		return aabb;
	}

	private Color gemColour = INVALID_COLOR;  // the RGB colour of the gem

	private final long INVALID_TIME = 0;
	private long lastTime = INVALID_TIME;  // used for animation
	private double lastAngularPosition; // used for animation
}