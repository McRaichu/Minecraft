package com.mcraichu.obeliskoflight.itemobelisk;

import java.util.ArrayList;
import java.util.List;

import com.mcraichu.obeliskoflight.ObeliskOfLightMain;
import com.mcraichu.obeliskoflight.Reference;
import com.mcraichu.obeliskoflight.blockgag.BlockGag;
import com.mcraichu.obeliskoflight.blockgag.TileEntityGag;
import com.mcraichu.obeliskoflight.obelisk.Obelisk;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemObelisk extends Item {

	public static String unl_name = "item_obelisk";

	public ItemObelisk(){
		this.setMaxStackSize(3);
		this.setCreativeTab(ObeliskOfLightMain.tabobelisk);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
		//Prevents itemstack from decreasing when in creative mod
		if(hitY == 0.0f)
			return true;

		int x = 0, y = 0, z = 0;
		if(hitX == 0.0f){x = -1;}
		if(hitX == 1.0f){x =  1;}
		if(hitZ == 0.0f){z = -1;}
		if(hitZ == 1.0f){z =  1;}
		if(hitY == 1.0f){y =  1;}

		//Prevents from making changes in inactive world
		if (!worldIn.isRemote){
			//Increases y coordinate, so our block will be placed on top of the block you clicked, just as it should be
			BlockPos pos0 = new BlockPos(pos.getX() + x, (pos.getY() + y) , pos.getZ() + z);
			//Takes the player sight direction
			//This array will store information about the coordinates where we want to place our gags relatively to the primary block.
			//To add blocks, just add some more {rel_x, rel_y, rel_z} into the array.
			//Exactly this array will add three gag block from the side of the primary block.
			int[][] gagShift = {{0, 1, 0}, {0, 2, 0}};
			List<BlockPos> gag_positions = new ArrayList<BlockPos>();;
			gag_positions.clear();
			//This cycle will prevent us from placing block instead of other ones, more commonly, it checks if the places where we want
			//to place gags are empty.
			
			Item item = GameRegistry.findItem(Reference.MODID, Obelisk.unl_name);
			ItemStack obstack = new ItemStack(item);
			
			
			boolean canPlace = true;
			canPlace = canPlace && (worldIn.canBlockBePlaced(Blocks.stone, pos0, false, side, null, obstack));
			for(int i = 0; i < gagShift.length; i++){
				BlockPos temp = new BlockPos(pos0.getX() + gagShift[i][0], pos0.getY() + gagShift[i][1], pos0.getZ() + gagShift[i][2]);
				gag_positions.add(temp);
				
				//check if it can be placed
				canPlace = canPlace && worldIn.canBlockBePlaced(Blocks.stone, temp, false, side, null, obstack);
				
//				if(!worldIn.isAirBlock(new BlockPos(pos0.getX() + gagShift[i][0], pos0.getY() + gagShift[i][1], pos0.getZ() + gagShift[i][2]))){
//					canPlace = false;
//				}
			}
			//System.out.println("DEBUG-----------------------------------" + hitX + ", " + hitY + ", " + hitZ + " ");
			//If the check was successful
			if(canPlace){
				
				//placing the main block (obelisk) simply using the item ^^
				item.onItemUse(obstack, playerIn, worldIn, pos0, side, hitX, hitY, hitZ);

				
				Item item2 = GameRegistry.findItem(Reference.MODID, BlockGag.unl_name);

				//This code is placing gags one after another into the coordinates we've set.
				for(BlockPos gagPos : gag_positions){
					ItemStack gagstack = new ItemStack(item2);
					item2.onItemUse(gagstack, playerIn, worldIn, gagPos, side, hitX, hitY, hitZ);
					TileEntityGag tileGag = (TileEntityGag)worldIn.getTileEntity(gagPos);
					if(tileGag != null){
						tileGag.setValues(pos0.getX(),pos0.getY(),pos0.getZ());
					}

				}
				
				//setting the all_placed flag. now they onChangeNeighbor can be used since all gag blocks are placed
				for(BlockPos gagPos : gag_positions){
					
					TileEntityGag tileGag = (TileEntityGag)worldIn.getTileEntity(gagPos);
					if(tileGag != null){
						tileGag.all_placed = true;
					}

				}
				if (!playerIn.capabilities.isCreativeMode){
					--stack.stackSize;
				}
			}
			return true;
		}
		return false;
	}

}
