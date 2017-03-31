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

@Syntax("stop audio (for|from|of) [player[s]] %players% [[with] [ID] %-string%]")
public class EffStopAudio extends Effect {
	
	private Expression<Player> players;
	private Expression<String> ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		players = (Expression<Player>) e[0];
		ID = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "stop audio (for|from|of) [player[s]] %players% [[with] [ID] %-string%]";
	}
	@Override
	protected void execute(Event e) {
		if (players != null) {
			for (Player player : players.getAll(e)) {
				if (ID != null) {
					command.StopID(player.getName(), ID.getSingle(e));
				} else {
					command.stop(player.getName());
				}
			}
		}
	}
}