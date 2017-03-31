package me.limeglass.openaudiomcskript.Elements;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.limeglass.openaudiomcskript.Utils.Syntax;
import net.openaudiomc.actions.command;

@Syntax("play loop[ing] audio [from] URL %string% (to|for) [player[s]] %players%")
public class EffPlayLoopingAudio extends Effect {
	
	private Expression<Player> players;
	private Expression<String> URL;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		URL = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "play loop[ing] audio [from] URL %string% (to|for) [player[s]] %players%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null) {
			for (Player player : players.getAll(e)) {
				command.playLoop(player.getName(), URL.getSingle(e));
			}
		}
	}
}