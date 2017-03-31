package me.limeglass.openaudiomcskript.Elements;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.openaudiomc.actions.command;

public class EffStopRegion extends Effect {
	
	static {
		Skript.registerEffect(EffStopRegion.class, "stop all [audio] in [the] region %string%");
	}
	
	private Expression<String> region;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		region = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "stop all [audio] in [the] region %string%";
	}
	@Override
	protected void execute(Event e) {
		if (region != null) {
			command.stopRegion(region.getSingle(e));
		}
	}
}