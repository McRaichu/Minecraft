package com.mcraichu.obeliskoflight.harvester;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.LockCode;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityRefinery extends TileEntityLockable implements IUpdatePlayerListBox, IInventory{

	private ItemStack[] chestContents = new ItemStack[27];
	/** The number of players currently using this chest */
	public int numPlayersUsing;
	/** Server sync counter (once per 20 ticks) */
	private int ticksSinceSync;
	private int cachedChestType;
	private String customName = "Refinery";
	public int currentOutput;


	public TileEntityRefinery()
	{
		this.cachedChestType = -1;
	}

	@SideOnly(Side.CLIENT)
	public TileEntityRefinery(int chestType)
	{
		this.cachedChestType = chestType;
	}

	public boolean removeItemsInInventory(ItemStack stack)
	{
		/*
		 * checks if itemstack with more or equal amount is in the inventory
		 * and removes the amount given by the input
		 * */
		if(stack == null)
		{
			return false;
		}else{
			if(stack.stackSize == 0)
			{
				return false;
			}
			//get total amount of item == stack.getItem
			int totalAmount = 0;
			int removeAmount = stack.stackSize;
			int[] stackIndexToRemove = new int[27];
			int counter = 0;
			for(int i = 0; i < 27; i++)
			{
				//same item
				ItemStack itemStack = chestContents[i]; 
				if(itemStack != null)
				{
					if(itemStack.getItem() == stack.getItem())
					{
						int currentStackSize = itemStack.stackSize; 
						totalAmount += currentStackSize;
						if(totalAmount < removeAmount)
						{
							stackIndexToRemove[counter] = i;
							counter++;
						}else if(currentStackSize >= removeAmount){
							chestContents[i].stackSize -= removeAmount;
							if(chestContents[i].stackSize == 0)
							{
								chestContents[i] = null;
							}
							return true;
						}else{
							stackIndexToRemove[counter] = i;
							
							for(int j = 0; j < counter; j++)
							{
								removeAmount -= chestContents[stackIndexToRemove[j]].stackSize;
								chestContents[j] = null;
							}
							if(chestContents[stackIndexToRemove[counter]].stackSize == removeAmount)
							{
								chestContents[stackIndexToRemove[counter]] = null;
							}else{
								chestContents[stackIndexToRemove[counter]].stackSize -= removeAmount;
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean addItemsToInventory(ItemStack stack)
	{
		if(stack == null)
		{
			return false;
		}else
		{
			if(stack.stackSize == 0)
			{
				return false;
			}
			//check for already existing stack with less the maxStackSize
			for(int i = 0; i < 27; i++)
			{
				//same item
				ItemStack itemStack = chestContents[i]; 
				if(itemStack != null){
					if(itemStack.getItem() == stack.getItem())
					{
						if((itemStack.stackSize + stack.stackSize) <= getInventoryStackLimit())
						{
							chestContents[i].stackSize += stack.stackSize;
							return true;
						}
					}
				}
				else
				{
					chestContents[i] = stack;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return 27;
	}

	/**
	 * Returns the stack in slot i
	 */
	public ItemStack getStackInSlot(int index)
	{
		return this.chestContents[index];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.chestContents[index] != null)
		{
			ItemStack itemstack;

			if (this.chestContents[index].stackSize <= count)
			{
				itemstack = this.chestContents[index];
				this.chestContents[index] = null;
				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = this.chestContents[index].splitStack(count);

				if (this.chestContents[index].stackSize == 0)
				{
					this.chestContents[index] = null;
				}

				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (this.chestContents[index] != null)
		{
			ItemStack itemstack = this.chestContents[index];
			this.chestContents[index] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly "Rcon")
	 */
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.chest";
	}

	/**
	 * Returns true if this thing is named
	 */
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		currentOutput = compound.getInteger("CurrentOutput");
		
	}

	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}
		
		compound.setInteger("CurrentOutput", currentOutput);
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
	 * this more of a set than a get?*
	 */
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void updateContainingBlockInfo()
	{
		super.updateContainingBlockInfo();
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory,
			EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}

	@Override
	public String getGuiID() {
		return "minecraft:chest";
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.isSpectator())
		{
			if (this.numPlayersUsing < 0)
			{
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockChest)
		{
			--this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
			this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
		}
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.chestContents.length; ++i)
		{
			this.chestContents[i] = null;
		}
	}

	@Override
	public void update() {
		int i = this.pos.getX();
		int j = this.pos.getY();
		int k = this.pos.getZ();
		++this.ticksSinceSync;
		float f;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			f = 5.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)i - f), (double)((float)j - f), (double)((float)k - f), (double)((float)(i + 1) + f), (double)((float)(j + 1) + f), (double)((float)(k + 1) + f)));
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityPlayer entityplayer = (EntityPlayer)iterator.next();

				if (entityplayer.openContainer instanceof ContainerChest)
				{
					IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();

					if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))
					{
						++this.numPlayersUsing;
					}
				}
			}
		}
		if (!this.worldObj.isRemote && (this.ticksSinceSync % 200 == 0))
		{
			Refinery ref = (Refinery)this.worldObj.getBlockState(this.pos).getBlock();
			ItemStack itemStackOutput = new ItemStack(ref.output[currentOutput],1);
			Item tiberium = com.mcraichu.obeliskoflight.tiberium.StartupClient.itemTiberium;
			ItemStack itemStackCosts = new ItemStack(tiberium,ref.costs[currentOutput]);
			if(removeItemsInInventory(itemStackCosts))
			{
				if(addItemsToInventory(itemStackOutput))
				{
					for (int x = 0; x <= 50; x++) {
						Random random = new Random();
						this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.pos.getX() + 1.0 + (random.nextGaussian() / 10), this.pos.getY() + 1.0 + (random.nextGaussian() / 10),
								this.pos.getZ() + 0.5 + (random.nextGaussian() / 10), (0), (0), (0));
					}
					//Block.spawnAsEntity(this.worldObj, this.pos.up(), itemStackOutput);
				}else{
					addItemsToInventory(itemStackCosts);
				}
			}
		}
	}

	
}
