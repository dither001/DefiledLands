package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.entity.passive.EntityBookWyrm;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;

public class ItemBookWyrmAnalyzer extends Item {
	
	public ItemBookWyrmAnalyzer()
	{
		this.setMaxStackSize(1);
	}
	

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, net.minecraft.entity.player.EntityPlayer player, EntityLivingBase entity, net.minecraft.util.EnumHand hand)
    {
        if (entity.world.isRemote)
        {
            return false;
        }
        if (entity instanceof EntityBookWyrm)
        {
        	EntityBookWyrm target = (EntityBookWyrm)entity;
        	
        	String base = "ui.book_wyrm_analyze.";
        	player.sendMessage(new TextComponentString(I18n.translateToLocal(String.format("%s%s", base, "digest_time")) + target.getDigestTime()));
        	player.sendMessage(new TextComponentString(I18n.translateToLocal(String.format("%s%s", base, "max_level")) + target.getMaxLevel()));
        	if (target.isGolden()) 
            	player.sendMessage(new TextComponentString(I18n.translateToLocal(String.format("%s%s", base, "golden"))));
        	
            return true;
        }
        
        return false;
    }

}