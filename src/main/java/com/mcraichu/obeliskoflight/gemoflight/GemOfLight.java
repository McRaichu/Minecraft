package com.mcraichu.obeliskoflight.gemoflight;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GemOfLight extends Block implements ITileEntityProvider{

	public static String unl_name = "gem_of_light";

	private Item drop;

	public GemOfLight(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);   // the block will appear on the Blocks tab in creative
		this.setStepSound(soundTypeStone);
		this.setHardness(10.0f);
		this.setResistance(20.0f);
		this.setHarvestLevel("pickaxe", 2);
		//this.drop = drop;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityGemOfLight();
	}

	// Called just after the player places a block.  Sets the TileEntity's colour
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityGemOfLight) { // prevent a crash if not the right type, or is null
			TileEntityGemOfLight tileEntityMBE21 = (TileEntityGemOfLight)tileentity;

			// chose a random colour for the gem:
			//Color [] colorChoices = {Color.BLUE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.WHITE, Color.ORANGE, Color.RED};
			//Random random = new Random();
			//Color gemColor = colorChoices[random.nextInt(colorChoices.length)];
			tileEntityMBE21.setGemColour(Color.RED);//gemColor);
		}
	}

	// -----------------
	// The following methods aren't particularly relevant to this example.  See MBE01, MBE02, MBE03 for more information.
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

//	@Override
//	public Item getItemDropped(IBlockState blockstate, Random random, int fortune) {
//		return this.drop;
//	}
//
//	@Override
//	public int damageDropped(IBlockState blockstate) {
//		return 0;
//	}
//
//	@Override
//	public int quantityDropped(IBlockState blockstate, int fortune, Random random) {
//		return 1 + fortune;
//	}
//
//	@Override
//	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune) {
//		ArrayList drops = new ArrayList();
//		drops.add(new ItemStack(Items.redstone, RANDOM.nextInt(2) + 1));
//		drops.add(new ItemStack(this.drop, 1));
//		if (RANDOM.nextFloat() < 0.05F)
//			drops.add(new ItemStack(Items.hopper_minecart));
//		return drops;
//	}
}
