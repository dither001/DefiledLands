package lykrast.defiledlands.common.world.biome;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class BiomeSwampDefiled extends BiomeDefiled {
	
	public static Biome.BiomeProperties properties = new Biome.BiomeProperties("Defiled Swamp");

    static {
        properties.setTemperature(Biomes.SWAMPLAND.getTemperature());
        properties.setRainfall(Biomes.SWAMPLAND.getRainfall());
        properties.setBaseHeight(Biomes.SWAMPLAND.getBaseHeight());
        properties.setHeightVariation(Biomes.SWAMPLAND.getHeightVariation());
    }
	
	public BiomeSwampDefiled()
	{
		super(properties);
        this.decorator.treesPerChunk = 2;
        this.decorator.clayPerChunk = 1;
        this.decorator.waterlilyPerChunk = 4;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
		this.scuronottePerChunk = 8;
	}

}
