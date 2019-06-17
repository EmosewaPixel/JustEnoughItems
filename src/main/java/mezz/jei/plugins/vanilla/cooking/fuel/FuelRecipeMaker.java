package mezz.jei.plugins.vanilla.cooking.fuel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.FurnaceTileEntity;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.runtime.IIngredientManager;

public final class FuelRecipeMaker {

	private FuelRecipeMaker() {
	}

	public static List<FuelRecipe> getFuelRecipes(IIngredientManager ingredientManager, IJeiHelpers helpers) {
		IGuiHelper guiHelper = helpers.getGuiHelper();
		List<ItemStack> fuelStacks = ingredientManager.getFuels();
		List<FuelRecipe> fuelRecipes = new ArrayList<>(fuelStacks.size());
		Map<Item, Integer> burnTimes = FurnaceTileEntity.getBurnTimes();
		for (ItemStack fuelStack : fuelStacks) {
			int burnTime = getItemBurnTime(fuelStack, burnTimes);
			fuelRecipes.add(new FuelRecipe(guiHelper, Collections.singleton(fuelStack), burnTime));
		}
		return fuelRecipes;
	}

	private static int getItemBurnTime(ItemStack stack, Map<Item, Integer> burnTimes) {
		if (stack.isEmpty()) {
			return 0;
		}
		int ret = stack.getBurnTime();
		if (ret == -1) {
			Item item = stack.getItem();
			ret = burnTimes.getOrDefault(item, 0);
		}
		return ForgeEventFactory.getItemBurnTime(stack, ret);
	}

}
