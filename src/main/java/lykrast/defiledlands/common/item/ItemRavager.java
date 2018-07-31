package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.entity.projectile.EntityRavagerProjectile;
import lykrast.defiledlands.common.init.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRavager extends ItemGun {
	
	public ItemRavager(int durability)
	{
        super(durability);
	}

    @Override
	protected boolean isAmmo(ItemStack stack)
    {
        return stack.getItem() instanceof ItemPellet;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;
        ItemStack ammo = findAmmo(playerIn);

        if (!ammo.isEmpty() || flag)
        {
            if (ammo.isEmpty())
            {
            	ammo = new ItemStack(ModItems.pelletUmbrium);
            }
            
            worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            
            if (!worldIn.isRemote)
            {
            	Vec3d vec3d = playerIn.getLook(1.0F);
            	EntityRavagerProjectile projectile = new EntityRavagerProjectile(worldIn, playerIn, vec3d.x, vec3d.y, vec3d.z, getSharpshooterBonus(itemstack));
            	projectile.posY = playerIn.posY + (double)playerIn.getEyeHeight();
            	
            	if (ammo.getItem() instanceof IPellet)
            	{
            		((IPellet)ammo.getItem()).applyAttributes(projectile, ammo);
            	}
                
                worldIn.spawnEntity(projectile);

                itemstack.damageItem(1, playerIn);
            }
            
            if (!flag)
            {
            	consumeAmmo(itemstack, ammo, playerIn, worldIn.rand);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
        	return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
    	return repair.getItem() == ModItems.ravagingIngot;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

}
