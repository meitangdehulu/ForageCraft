package com.theishiopian.foragecraft;

import com.theishiopian.foragecraft.config.*;
import com.theishiopian.foragecraft.handler.BlockForageHandler;
import com.theishiopian.foragecraft.handler.FuelHandler;
import com.theishiopian.foragecraft.handler.PotatoPlanter;
import com.theishiopian.foragecraft.handler.RecipeHandler;
import com.theishiopian.foragecraft.handler.SpawnHandler;
import com.theishiopian.foragecraft.init.ModBlocks;
import com.theishiopian.foragecraft.init.ModEntities;
import com.theishiopian.foragecraft.init.ModItems;
import com.theishiopian.foragecraft.proxy.CommonProxy;
import com.theishiopian.foragecraft.world.generation.FCMasterWorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/*
/  Logger based from Tinkers' Construct
/  https://github.com/SlimeKnights/TinkersConstruct/blob/08f7180399ca8ad4717493dd0aa5a63b7aa14584/src/main/java/slimeknights/tconstruct/TConstruct.java
*/

@Mod(modid = ForageCraftMod.MODID,
		name = ForageCraftMod.NAME,
		version = ForageCraftMod.VERSION,
		acceptedMinecraftVersions = ForageCraftMod.MC_VERSIONS,
		updateJSON = ForageCraftMod.UPDATE_JSON)

public class ForageCraftMod
{
	public static final String MODID = "foragecraft";
	public static final String MOD_NAME = "ForageCraft";
	public static final String NAME = "ForageCraft";
	public static final String VERSION = "1.13";
	public static final String MC_VERSIONS = "[1.12.1]";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/theishiopian/ForageCraft/master/update.json";
	public static final String CLIENTPROXY = "com.theishiopian.foragecraft.proxy.Client";
	public static final String SERVERPROXY = "com.theishiopian.foragecraft.proxy.Server";

	@SidedProxy(clientSide = ForageCraftMod.CLIENTPROXY, serverSide = ForageCraftMod.SERVERPROXY)
	public static CommonProxy proxy;

	@Instance
	public static ForageCraftMod instance;

	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.loadConfig(event);
		ConfigHandler.configWarnings();
		
		//TODO move a bunch of this shit to the proxy system.
		
		// Initialize Blocks
		ModBlocks.init(); ForageLogger.printDevelop("Blocks initialized successfully.");
		
		
		// Initialize Items
		ModItems.init(); ForageLogger.printDevelop("Items initialized successfully.");

		

		// Initialize Entities
		ModEntities.init(); ForageLogger.printDevelop("Entities initialized successfully.");
	}

	@SuppressWarnings("deprecation")
	@EventHandler // Time to Magic School Bus this shit
	public void Init(FMLInitializationEvent event)
	{
		// Let's Do This Thing
		proxy.init(event);

		MinecraftForge.EVENT_BUS.register(new PotatoPlanter());
		MinecraftForge.EVENT_BUS.register(new BlockForageHandler());
		MinecraftForge.EVENT_BUS.register(new SpawnHandler());
		RecipeHandler.Shapless();
		SeedLoader.seed();
		GameRegistry.registerFuelHandler(new FuelHandler());// deprecated,
															// alternatives?
		GameRegistry.registerWorldGenerator(new FCMasterWorldGenerator(), 10);

		// Getting fanceh here
		ForageLogger.printInfo("Ready to forage.");
	}
}
