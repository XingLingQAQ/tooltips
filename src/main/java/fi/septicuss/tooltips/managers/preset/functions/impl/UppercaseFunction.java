package fi.septicuss.tooltips.managers.preset.functions.impl;

import fi.septicuss.tooltips.managers.condition.argument.Argument;
import fi.septicuss.tooltips.managers.preset.functions.Function;
import fi.septicuss.tooltips.managers.preset.functions.FunctionContext;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * $uppercase(str)
 */
public class UppercaseFunction implements Function {

    @Override
    public String handle(Player player, FunctionContext context, List<Argument> args) {
        if (args.isEmpty()) return "";
        final String str = args.get(0).process(player, context.context()).getAsString();
        return str.isEmpty() ? str : str.toLowerCase();
    }

}
