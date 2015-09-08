package com.mcraichu.obeliskoflight.blockgag;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGag extends BlockContainer {

	public static String unl_name = "block_gag"; 

	public BlockGag(){
		super(Material.iron);
		this.setHardness(10.0f);
		this.setResistance(20.0f);
		this.setLightLevel(2.0f);
	}
	//This block is called when block is broken and destroys the primary block.
		
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		//Reading the gag's tile entity.
		TileEntityGag tileEntity = (TileEntityGag)worldIn.getTileEntity(pos);
		//If not make this check, the game may crash if there's no tile entity at i, j, k.
		if (tileEntity != null){
			//Actually destroys primary block.
			BlockPos pos0 = new BlockPos(new Vec3(tileEntity.primary_x, tileEntity.primary_y, tileEntity.primary_z));
			worldIn.destroyBlock(pos0, false);
			//Forces removing tile entity from primary block coordinates,
			//cause sometimes minecraft forgets to do that.
			worldIn.removeTileEntity(pos0);
		}
		//Same as above, but for the gag block tile entity.
		worldIn.removeTileEntity(pos);
	}
	//This method checks if primary block exists.
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
		TileEntityGag tileEntity = (TileEntityGag)world.getTileEntity(pos);
		if (tileEntity != null){
			if(!tileEntity.all_placed)
				return;
			//No need to check if block's Id matches the Id of our primary block,
			//because if a player want to change a block, he needs to brake it first,
			//and in this case block will be set to Air (Id = 0)
			if(world.isAirBlock(new BlockPos(new Vec3(tileEntity.primary_x, tileEntity.primary_y, tileEntity.primary_z)))){
				((World) world).destroyBlock(pos, false);
				((World) world).removeTileEntity(pos);
			}
		}
	}
	//This makes our gag invisible.
	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
		return false;
	}
	//This tells minecraft to render surrounding blocks.
	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return  new TileEntityGag();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState blockstate, int fortune) {
		ArrayList drops = new ArrayList();
		drops.add(new ItemStack(Items.redstone, RANDOM.nextInt(2)));
		drops.add(new ItemStack(com.mcraichu.obeliskoflight.itemobelisk.StartupCommon.item_obelisk, 1));
		return drops;
	}
	
}
