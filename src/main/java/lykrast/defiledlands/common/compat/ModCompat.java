package lykrast.defiledlands.common.compat;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

import net.minecraftforge.fml.common.Loader;

import java.util.HashSet;
import java.util.Map.Entry;

import lykrast.defiledlands.core.DefiledLands;

public abstract class ModCompat {
	public static HashMap<String, Class<? extends ModCompat>> compat = new HashMap<String, Class<? extends ModCompat>>();
	public static Set<ModCompat> compatLoaded = new HashSet<ModCompat>();
	
	static
	{
		compat.put("chisel", CompatChisel.class);
	}
	
	public static void preInitCompat()
	{
		for(Entry<String, Class<? extends ModCompat>> e : compat.entrySet())
		{
			if(Loader.isModLoaded(e.getKey()))
			{
				try
				{
					ModCompat c = e.getValue().newInstance();
					compatLoaded.add(c);
					c.preInit();
				}
				catch(Exception ex)
				{
					DefiledLands.logger.log(Level.WARNING, "Compat module for " + e.getKey() + " could not be pre-initialized");
				}
			}
		}
		
	}
	
	public static void initCompat()
	{
		for(ModCompat compat : compatLoaded)
		{
			try
			{
				compat.init();
			}
			catch(Exception ex)
			{
				DefiledLands.logger.log(Level.WARNING, "Compat module for " + compat + " could not be initialized");
			}
		}
	}
	
	public static void postInitCompat()
	{
		for(ModCompat compat : compatLoaded)
		{
			try
			{
				compat.postInit();
			}
			catch(Exception ex)
			{
				DefiledLands.logger.log(Level.WARNING, "Compat module for " + compat + " could not be post-initialized");
			}
		}		
	}
	
	public void preInit() {}
	public void init() {}
	public void postInit() {}

}
