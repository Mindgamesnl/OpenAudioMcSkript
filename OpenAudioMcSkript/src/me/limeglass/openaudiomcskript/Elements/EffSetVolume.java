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

@Syntax("set [audio] volume (to|for|of) [player[s]] %players% [[with] [ID] %-string%] to %number%")
public class EffSetVolume extends Effect {
	
	private Expression<Player> players;
	private Expression<String> ID;
	private Expression<Number> volume;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		players = (Expression<Player>) e[0];
		ID = (Expression<String>) e[1];
		volume = (Expression<Number>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "set [audio] volume (to|for|of) [player[s]] %players% [[with] [ID] %-string%] to %number%";
	}
	@Override
	protected void execute(Event e) {
		if (players != null && volume != null) {
			for (Player player : players.getAll(e)) {
				if (ID != null) {
					command.setVolume(player.getName(), volume.getSingle(e).toString());
				} else {
					command.setVolumeID(player.getName(), ID.getSingle(e), volume.getSingle(e).toString());
				}
			}
		}
	}
}