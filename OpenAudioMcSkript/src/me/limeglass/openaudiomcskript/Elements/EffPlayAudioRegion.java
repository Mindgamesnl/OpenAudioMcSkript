package me.limeglass.openaudiomcskript.Elements;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.limeglass.openaudiomcskript.Utils.Syntax;
import net.openaudiomc.actions.command;

@Syntax("play audio [from] URL %string% (to|for|in) region %string%")
public class EffPlayAudioRegion extends Effect {
	
	private Expression<String> URL, Region;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		URL = (Expression<String>) e[0];
		Region = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "play audio [from] URL %string% (to|for|in) region %string%";
	}
	@Override
	protected void execute(Event e) {
		if (Region != null && URL != null) {
			command.playRegion(Region.getSingle(e), URL.getSingle(e));
		}
	}
}