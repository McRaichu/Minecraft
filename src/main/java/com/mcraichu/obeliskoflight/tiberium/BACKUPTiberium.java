package com.mcraichu.obeliskoflight.tiberium;

import java.util.Iterator;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
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

public class BACKUPTiberium extends Block implements ITileEntityProvider{

	public static String unl_name = "tiberium";

	public static final PropertyEnum SIZE = PropertyEnum.create("size", BACKUPTiberium.EnumType.class);
	public static final String name = "CustomModelBlock";
	private int counter = 1;
	private int duration = 7;
	public ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[]{SIZE}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});

	protected BACKUPTiberium(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIZE, BACKUPTiberium.EnumType.SMALL));
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
		this.setTickRandomly(true);
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

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityLivingBase){
			effectPlayer((EntityLivingBase) entityIn, Potion.poison, 2);
			//entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
		}
	}

	private void effectPlayer(EntityLivingBase entity, Potion potion, int amplifier) {
		//Always effect for 8 seconds, then refresh
		if (entity.getActivePotionEffect(potion) == null || entity.getActivePotionEffect(potion).getDuration() <= 1)
			entity.addPotionEffect(new PotionEffect(potion.id, duration, amplifier, true, true));
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
		return this.getDefaultState().withProperty(SIZE, BACKUPTiberium.EnumType.SMALL);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SIZE, BACKUPTiberium.EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BACKUPTiberium.EnumType) state.getValue(SIZE)).getMetadata();
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
		return new ExtendedBlockState(this, new IProperty[]{SIZE}, new IUnlistedProperty[]{B3DLoader.B3DFrameProperty.instance});
	}

	public static enum EnumType implements IStringSerializable
	{
		SMALL(0, "small"),
		MEDIUM(1, "medium"),
		LARGE(2, "large");
		/** Array of the Block's BlockStates */
		private static final BACKUPTiberium.EnumType[] META_LOOKUP = new BACKUPTiberium.EnumType[values().length];
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
		public static BACKUPTiberium.EnumType byMetadata(int meta)
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
			BACKUPTiberium.EnumType[] var0 = values();
			int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2)
			{
				BACKUPTiberium.EnumType var3 = var0[var2];
				META_LOOKUP[var3.getMetadata()] = var3;
			}
		}
	}
}
