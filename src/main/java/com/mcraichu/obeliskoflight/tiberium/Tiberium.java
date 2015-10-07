package com.mcraichu.obeliskoflight.tiberium;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.blockgag.TileEntityGag;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.b3d.B3DLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class Tiberium extends Block implements ITileEntityProvider{

	public static String unl_name = "tiberium";

	public static final PropertyEnum SIZE = PropertyEnum.create("size", Tiberium.EnumType.class);
	public static final String name = "CustomModelBlock";
	private int counter = 1;
	private int duration = 160; //in ticks (20 * 8 sec)
	//	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{SIZE}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});

	protected Tiberium(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIZE, Tiberium.EnumType.SMALL));
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
		this.setTickRandomly(true);
		this.setHardness(5.0f);
		this.setResistance(10.0f);
		this.setHarvestLevel("shovel", 1);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		int i = getMetaFromState(state);

		if (i < 2 && (rand.nextInt(10) == 0))
		{
			state = getStateFromMeta(i + 1);
			worldIn.setBlockState(pos, state, 2);
		}

		super.updateTick(worldIn, pos, state, rand);
	}


	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(!worldIn.isRemote){
			if(entityIn instanceof EntityLivingBase){
				if(entityIn instanceof com.mcraichu.obeliskoflight.harvester.Harvester)
				{
					return;
				}
				effectPlayer((EntityLivingBase) entityIn, Potion.poison, 2);
			}
		}
	}

	private void effectPlayer(EntityLivingBase entity, Potion potion, int amplifier) {
		if (entity.getActivePotionEffect(potion) == null || entity.getActivePotionEffect(potion).getDuration() <= 1)
			entity.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, true, true));
	}


	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
	{

		float f = 0.0625F;

		int meta = this.getMetaFromState(worldIn.getBlockState(pos));

		int width = 3 - meta + 2;
		int height = 10 - 5 * meta + 2;

		this.setBlockBounds(f*width, 0.0F, f*width, 1.0F - (f*width), 1.0F - (f*height), 1.0F - (f*width));

		super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);

		width = 3 - meta;
		height = 10 - 5 * meta;
		this.setBlockBounds(f*width, 0.0F, f*width, 1.0F - (f*width), 1.0F - (f*height), 1.0F - (f*width));
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{

		float f = 0.0625F;
		/*
		 * large = 1 * f (height = 1 - 0 * f)
		 * medium = 3 * f (height = 1 - 2 * f)
		 * small = 5 * f (height = 1 - 4 * f)
		 * */
		int meta = this.getMetaFromState(worldIn.getBlockState(pos));

		int width = 3 - meta;
		int height = 10 - 5 * meta;

		this.setBlockBounds(f*width, 0.0F, f*width, 1.0F - (f*width), 1.0F - (f*height), 1.0F - (f*width));
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return  new TileEntityTiberium();
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
		return this.getDefaultState().withProperty(SIZE, Tiberium.EnumType.SMALL);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SIZE, Tiberium.EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Tiberium.EnumType) state.getValue(SIZE)).getMetadata();
	}

	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {SIZE});
	}

	public static enum EnumType implements IStringSerializable
	{
		SMALL(0, "small"),
		MEDIUM(1, "medium"),
		LARGE(2, "large");
		/** Array of the Block's BlockStates */
		private static final Tiberium.EnumType[] META_LOOKUP = new Tiberium.EnumType[values().length];
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
		public static Tiberium.EnumType byMetadata(int meta)
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
			Tiberium.EnumType[] var0 = values();
			int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2)
			{
				Tiberium.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
}
