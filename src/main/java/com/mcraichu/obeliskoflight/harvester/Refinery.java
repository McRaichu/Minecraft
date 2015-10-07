package com.mcraichu.obeliskoflight.harvester;


import java.util.Iterator;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

public class Refinery extends Block implements ITileEntityProvider{

	public static String unl_name = "refinery";

	public static final PropertyEnum STATUS = PropertyEnum.create("status", Refinery.EnumType.class);
	public static final String name = "Refinery";
	private int counter = 1;
	private int duration = 7;
	public Item[] output = new Item[5];
	public int[] costs = new int[5];
	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{STATUS}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});


	protected Refinery(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STATUS, Refinery.EnumType.ON));
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
		output[0] = Items.coal;
		output[1] = Items.iron_ingot;
		output[2] = Items.gold_ingot;
		output[3] = Items.diamond;
		output[4] = null;
		costs[0] = 2;
		costs[1] = 4;
		costs[2] = 8;
		costs[3] = 16;
		costs[4] = 0;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityRefinery(0);
	}

	public static void addMessage(String msg) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2478[\247cObeliskOfLight\2478] \247f" + msg));
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			if(!playerIn.isSneaking()){
				ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);

				if (ilockablecontainer != null)
				{
					playerIn.displayGUIChest(ilockablecontainer);
				}

				return true;
			}else{
				TileEntityRefinery tileentity = (TileEntityRefinery)worldIn.getTileEntity(pos);
				if(tileentity == null)
				{
					return true;
				}
				int co = tileentity.currentOutput;
				co = co == (output.length-1) ? 0 : co+1;
				tileentity.currentOutput = co;
				String name;
				if(output[co] == null){
					name = "No Output!";
				}else{
					name = net.minecraftforge.fml.common.registry.LanguageRegistry.instance().getStringLocalization(output[co].getUnlocalizedName()+".name");
				}
				String msg = "Output: " + name + " Costs: " + costs[co];
				addMessage(msg);
				return true;
			}
		}
	}

	public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (!(tileentity instanceof TileEntityRefinery))
		{
			return null;
		}
		else
		{
			Object object = (TileEntityRefinery)tileentity;
			return (ILockableContainer)object;
		}
	}

	@Override
	public boolean isOpaqueCube() { return false; }

	@Override
	public boolean isFullCube() { return false; }

	@Override
	public boolean isVisuallyOpaque() { return false; }

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(STATUS, Refinery.EnumType.ON);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STATUS, Refinery.EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Refinery.EnumType) state.getValue(STATUS)).getMetadata();
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		//Only return an IExtendedBlockState from this method and createState(), otherwise block placement might break!
		B3DLoader.B3DState newState = new B3DLoader.B3DState(null, counter);
		return ((IExtendedBlockState) state).withProperty(B3DLoader.B3DFrameProperty.instance, newState);
	}

	@Override
	public BlockState createBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[]{STATUS}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});
	}

	public static enum EnumType implements IStringSerializable
	{
		ON(0, "on"),
		OFF(1, "off");
		/** Array of the Block's BlockStates */
		private static final Refinery.EnumType[] META_LOOKUP = new Refinery.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name)
		{
			this(meta, name, name);
		}

		private EnumType(int meta, String name, String unlocalizedName)
		{
			this.meta = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		/**
		 * Returns the EnumType's metadata value.
		 */
		public int getMetadata()
		{
			return this.meta;
		}

		public String toString()
		{
			return this.name;
		}

		/**
		 * Returns an EnumType for the BlockState from a metadata value.
		 */
		public static Refinery.EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		public String getName()
		{
			return this.name;
		}

		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}

		static
		{
			Refinery.EnumType[] var0 = values();
			int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2)
			{
				Refinery.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}

}
