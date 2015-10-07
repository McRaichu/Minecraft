package com.mcraichu.obeliskoflight.nodturret;

import java.awt.Color;
import java.util.Random;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NodTurret extends Block implements ITileEntityProvider{

	public static String unl_name = "nod_turret";
	
	protected NodTurret(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);   // the block will appear on the Blocks tab in creative	
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		this.setHardness(10.0f);
		this.setResistance(20.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new TileEntityNodTurret();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityNodTurret) { // prevent a crash if not the right type, or is null
			TileEntityNodTurret tileEntityMBE21 = (TileEntityNodTurret)tileentity;

			//tileEntityMBE21.setGemColour(Color.RED);
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
	
}
